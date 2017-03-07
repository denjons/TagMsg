package com.dennisjonsson.tm.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "REQUESTS")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "uu_id")
    private String uu_id;

    @NotNull
    @Column(name = "user")
    private int user;

    @NotNull
    @Column(name = "content")
    private String content;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "date")
    private Date date;

    @Transient
    List<RequestTagRelation> tagsRelations;

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

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public int getUser() {
	return user;
    }

    public void setUser(int user) {
	this.user = user;
    }

    public List<RequestTagRelation> getTagsRelations() {
	return tagsRelations;
    }

    public void setTagsRelations(List<RequestTagRelation> tagsRelations) {
	this.tagsRelations = tagsRelations;
    }

}
