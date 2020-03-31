package com.xiaoju.uemc.tinyid.client.factory.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.xiaoju.uemc.tinyid.base.factory.AbstractIdGeneratorFactory;
import com.xiaoju.uemc.tinyid.base.generator.IdGenerator;
import com.xiaoju.uemc.tinyid.base.generator.impl.CachedIdGenerator;
import com.xiaoju.uemc.tinyid.client.config.TinyIdClientConfig;
import com.xiaoju.uemc.tinyid.client.service.impl.HttpSegmentIdServiceImpl;
import com.xiaoju.uemc.tinyid.client.utils.TinyIdNumberUtils;
import com.xiaoju.uemc.tinyid.client.vo.TinyIdClientVo;

/**
 * @author du_imba
 */
public class IdGeneratorFactoryClient extends AbstractIdGeneratorFactory {

    private static final Logger logger = Logger.getLogger(IdGeneratorFactoryClient.class.getName());

    private static IdGeneratorFactoryClient idGeneratorFactoryClient = null;

    private static final int DEFAULT_TIME_OUT = 5000;

    private static String serverUrl = "http://{0}/tinyid/id/nextSegmentIdSimple?token={1}&bizType=";

    private IdGeneratorFactoryClient() {
    }

    public static IdGeneratorFactoryClient getInstance() {
        if (idGeneratorFactoryClient == null) {
        	return new IdGeneratorFactoryClient();
        }
        return idGeneratorFactoryClient;
    }

    public static boolean init(TinyIdClientVo tinyIdClientVo) {
        String tinyIdToken = tinyIdClientVo.getTinyIdToken();
        String tinyIdServer = tinyIdClientVo.getTinyIdServer();
        String readTimeout = tinyIdClientVo.getReadTimeout();
        String connectTimeout = tinyIdClientVo.getConnectTimeout();

        if (tinyIdToken == null || "".equals(tinyIdToken.trim())
                || tinyIdServer == null || "".equals(tinyIdServer.trim())) {
            throw new RuntimeException("cannot get tinyIdToken and tinyIdServer");
        }

        TinyIdClientConfig tinyIdClientConfig = TinyIdClientConfig.getInstance();
        tinyIdClientConfig.setTinyIdServer(tinyIdServer);
        tinyIdClientConfig.setTinyIdToken(tinyIdToken);
        tinyIdClientConfig.setReadTimeout(TinyIdNumberUtils.toInt(readTimeout, DEFAULT_TIME_OUT));
        tinyIdClientConfig.setConnectTimeout(TinyIdNumberUtils.toInt(connectTimeout, DEFAULT_TIME_OUT));

        String[] tinyIdServers = tinyIdServer.split(",");
        List<String> serverList = new ArrayList<>(tinyIdServers.length);
        for (String server : tinyIdServers) {
            String url = MessageFormat.format(serverUrl, server, tinyIdToken);
            serverList.add(url);
        }
        logger.info("init tinyId client success url info:" + serverList);
        tinyIdClientConfig.setServerList(serverList);
        return true;
    }

    @Override
    protected IdGenerator createIdGenerator(String bizType) {
        return new CachedIdGenerator(bizType, new HttpSegmentIdServiceImpl());
    }

}
