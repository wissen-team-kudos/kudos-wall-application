package com.demo.kudo.entity;

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
@Table(name="groups")
public class Group {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	int id;
	
	@Column(name="groupname")
	String groupname;
	
	@Column(name="password")
	String password;
	
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="groups_kudos",
				joinColumns = @JoinColumn(name="group_id"),
				inverseJoinColumns = @JoinColumn(name="kudo_id"))	
	@JsonIgnoreProperties(value = {"kudos"}) // to prevent serialization of data
	private List<Kudos> kudos;
	
	public Group() {
		
	}

	public Group(int id, String groupname, String password) {
		this.id = id;
		this.groupname = groupname;
		this.password = password;
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
	
	public List<Kudos> getKudos() {
		return kudos;
	}

	public void setUsers(List<Kudos> kudos) {
		this.kudos = kudos;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", groupname=" + groupname + ", password=" + password + "]";
	}
	
}
