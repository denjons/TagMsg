package com.dennisjonsson.tm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "uu_id")
	private String uu_id;
	
	@Column(name = "date")
	private Date date;

	public User() {
		super();
	}

	public User(String uu_id, Date date) {
		super();
		this.uu_id = uu_id;
		this.date = date;
	}

	public User(String uu_id) {
		super();
		this.uu_id = uu_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUu_id() {
		return uu_id;
	}

	public void setUu_id(String uu_id) {
		this.uu_id = uu_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
}
