package com.xiaoju.uemc.tinyid.client.vo;

import java.io.Serializable;

public class TinyIdClientVo implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

    private String tinyIdServer;

    private String tinyIdToken;

    private String readTimeout;

    private String connectTimeout;

	public String getTinyIdServer() {
		return tinyIdServer;
	}

	public void setTinyIdServer(String tinyIdServer) {
		this.tinyIdServer = tinyIdServer;
	}

	public String getTinyIdToken() {
		return tinyIdToken;
	}

	public void setTinyIdToken(String tinyIdToken) {
		this.tinyIdToken = tinyIdToken;
	}

	public String getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(String readTimeout) {
		this.readTimeout = readTimeout;
	}

	public String getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(String connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

}