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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "kudos")
public class Kudos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="content")
	private String content;
	
	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	@JsonIgnoreProperties(value = {"kudos"})
	private User author;
	
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(
			name = "users_kudos",
			joinColumns = @JoinColumn(name = "kudo_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
			)
	@JsonIgnoreProperties(value = {"kudos"})
	private List<User> users;//users who can view this kudo
	
	
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(
			name="groups_kudos",
			joinColumns = @JoinColumn(name="kudo_id"),
			inverseJoinColumns = @JoinColumn(name="group_id")
			)	
	@JsonIgnoreProperties(value = {"kudos"})
	private List<Group> groups;
	
	public Kudos() {
		
	}
	public List<Group> getGroups(){
		return groups;
	}    

	public void setGroups(List<Group> groups){
		this.groups=groups;
	}
	public Kudos(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		if(users == null) {
			users = new ArrayList<>();
		}
		users.add(user);
	}
	

	@Override
	public String toString() {
		return "Kudos [id=" + id + ", content=" + content + ", author=" + author + ", users=" + users + "]";
	}

	
	
	
	
}
