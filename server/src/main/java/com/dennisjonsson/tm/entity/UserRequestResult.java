package com.dennisjonsson.tm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;

@NamedStoredProcedureQuery(name = "getUserRequests", procedureName = "GET_USER_REQUESTS", resultClasses = {
	UserRequestResult.class })
@Entity
public class UserRequestResult {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "uu_id")
    private String uu_id;

    @Column(name = "content")
    private String content;

    @Column(name = "tags")
    private String tags;

    @Column(name = "date")
    private String date;

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

    public String getTags() {
	return tags;
    }

    public void setTags(String tags) {
	this.tags = tags;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

}
