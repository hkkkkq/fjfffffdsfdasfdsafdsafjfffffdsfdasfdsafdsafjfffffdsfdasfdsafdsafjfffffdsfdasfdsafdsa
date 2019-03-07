package com.kt.svc1;

public class Request {
	
	private String id;
	private String name;
	private String security_number;
	
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
	
	@Override
	public String toString() {
		return "Request [id=" + id + ", name=" + name + ", security_number=" + security_number + "]";
	}
	
}
