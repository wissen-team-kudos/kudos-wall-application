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
@Table(name="groups_of_user")
public class Group {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	int id;
	
	@Column(name="groupname",
			unique = true)
	String groupname;
	
	@Column(name="password")
	String password;
	
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="users_groups",
				joinColumns = @JoinColumn(name="group_id"),
				inverseJoinColumns = @JoinColumn(name="user_id"))	
	@JsonIgnoreProperties(value = {"groups", "kudos"}) // to prevent serialization of data
	private List<User> users;
	
	
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(
			name="groups_kudos",
			joinColumns = @JoinColumn(name="group_id"),
			inverseJoinColumns = @JoinColumn(name="kudo_id")
			)	
	@JsonIgnoreProperties(value = {"kudos", "groups"})
	private List<Kudos> kudos;

	public Group() {
		
	}

	public Group(int id, String groupname, String password) {
		this.id = id;
		this.groupname = groupname;
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
		return "Group [id=" + id + ", groupname=" + groupname + ", password=" + password + "]";
	}
	
}
