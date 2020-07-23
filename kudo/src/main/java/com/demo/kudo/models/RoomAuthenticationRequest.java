package com.demo.kudo.models;

import java.io.Serializable;

public class RoomAuthenticationRequest implements Serializable {
	
	private String roomname;
	private String password;
		
	public RoomAuthenticationRequest() {
	}

	public RoomAuthenticationRequest(String roomname, String password) {
		this.roomname = roomname;
		this.password = password;
	}
	
	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "RoomAuthenticationRequest [roomname=" + roomname + ", password=" + password + "]";
	}
	
	
}
