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
@Table(name="kudos")
public class Kudos {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	int id;
	
	@Column(name="content")
	String content;
	
	@Column(name="uid")
	int uid;
	
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="groups_kudos",
				joinColumns = @JoinColumn(name="kudo_id"),
				inverseJoinColumns = @JoinColumn(name="group_id"))	
	@JsonIgnoreProperties(value = {"kudos"})
	private List<Group> groups;
	
	public Kudos() {
		
	}

	public Kudos(int id, String content, int uid) {
		this.id = id;
		this.content = content;
		this.uid = uid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKudosContent() {
		return content;
	}

	public void setKudosContent(String content) {
		this.content = content;
	}

	public int getUID() {
		return uid;
	}

	public void setUID(int uid) {
		this.uid = uid;
	}
	
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return "Kudos [id=" + id + ", content=" + content + ", uid=" + uid + "]";
	}
	
}

