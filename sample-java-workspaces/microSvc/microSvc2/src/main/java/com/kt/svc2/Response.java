package com.kt.svc2;

public class Response {
	
	private String id;
	private String name;
	private String security_number;
	
	private String resultCode;
	private String resultMsg;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecurity_number() {
		return security_number;
	}
	public void setSecurity_number(String security_number) {
		this.security_number = security_number;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	@Override
	public String toString() {
		return "Response [id=" + id + ", name=" + name + ", security_number=" + security_number + ", resultCode="
				+ resultCode + ", resultMsg=" + resultMsg + "]";
	}
	
	
	

}
