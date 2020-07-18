package com.demo.kudo.models;

import java.io.Serializable;

public class GroupAuthenticationRequest implements Serializable {
	
	private String groupname;
	private String password;
		
	public GroupAuthenticationRequest() {
	}

	public GroupAuthenticationRequest(String groupname, String password) {
		this.groupname = groupname;
		this.password = password;
	}
	
	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
