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
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	int id;
	
	@Column(name="username",
			unique = true)
	String username;
	
	@Column(name="password")
	String password;
	
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(
			name = "users_kudos",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "kudo_id")
			)
	@JsonIgnoreProperties(value = {"users", "rooms"})
	private List<Kudos> kudos;

	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="users_rooms",
				joinColumns = @JoinColumn(name="user_id"),
				inverseJoinColumns = @JoinColumn(name="room_id"))	
	@JsonIgnoreProperties(value = {"users", "kudos"}) // to prevent serialization of data
	private List<Room> rooms;
	
	public User() {
		
	}
		
	public User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public void addRoom(Room room) {
		if(rooms==null)
			rooms=new ArrayList<Room>();
		
		if(!rooms.contains(room))
			rooms.add(room);

	}
	
	public List<Kudos> getKudos() {
		return kudos;
	}

	public void setKudos(List<Kudos> kudos) {
		this.kudos = kudos;
	}
	
	public void addKudo(Kudos kudo) {
		if(kudos == null) {
			kudos = new ArrayList<>();
		}
		
		kudos.add(kudo);
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	
	
}
