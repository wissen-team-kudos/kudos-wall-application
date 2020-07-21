package com.demo.kudo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="rooms_of_user")
public class Room {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	int id;
	
	@Column(name="roomname",
			unique = true)
	String roomname;
	
	@Column(name="password")
	String password;
	
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="users_rooms",
				joinColumns = @JoinColumn(name="room_id"),
				inverseJoinColumns = @JoinColumn(name="user_id"))	
	@JsonIgnoreProperties(value = {"rooms", "kudos"}) // to prevent serialization of data
	private List<User> users;
	
	
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(
			name="rooms_kudos",
			joinColumns = @JoinColumn(name="room_id"),
			inverseJoinColumns = @JoinColumn(name="kudo_id")
			)	
	@JsonIgnoreProperties(value = {"kudos", "rooms"})
	private List<Kudos> kudos;

	public Room() {
		
	}

	public Room(int id, String roomname, String password) {
		this.id = id;
		this.roomname = roomname;
		this.password = password;
	}
	public List<Kudos> getKudos(){
		return kudos;
	}
	
	public void setKudos(List<Kudos> kudos){
		this.kudos=kudos;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		if(this.users == null) {
			this.users = new ArrayList<User>();
		}
		this.users.add(user);
	}
	
	public void addKudos(Kudos kudos) {
		if(this.kudos == null) {
			this.kudos = new ArrayList<>();
		}
		this.kudos.add(kudos);
	}
	
	@Override
	public String toString() {
		return "room [id=" + id + ", roomname=" + roomname + ", password=" + password + "]";
	}
	
}
