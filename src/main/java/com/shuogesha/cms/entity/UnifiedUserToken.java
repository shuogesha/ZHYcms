package com.shuogesha.cms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shuogesha.api.model.Token;
import com.shuogesha.api.web.util.MD5Util;

@Document
public class UnifiedUserToken {
	@Id
	private String id;
	private String appid;
	private String encryptSignature;
	private Token token;

	public String getEncryptSignature() {
		return encryptSignature;
	}

	public void setEncryptSignature(String encryptSignature) {
		this.encryptSignature = encryptSignature;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public UnifiedUserToken(String appid, String encryptSignature, Token token) {
		super();
		this.id=MD5Util.MD5(token.toString());
		this.appid =appid;
		this.encryptSignature = MD5Util.MD5(encryptSignature);
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
