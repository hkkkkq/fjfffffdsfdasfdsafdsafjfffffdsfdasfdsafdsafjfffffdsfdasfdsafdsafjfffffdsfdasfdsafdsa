package com.henc.cdrs.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Exchange 연결정보를 설정하기 위한 Properties
 *
 * @author YongSang
 */
@ConfigurationProperties(MailProperties.PREFIX)
public class MailProperties  {

    public static final String PREFIX = "henc.exchange.sender";

	private String id;
	
	private String password;
	
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}