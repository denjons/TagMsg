package com.dennisjonsson.tm.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    long id;

    @NotNull
    String tag;

    public Tag() {
	super();
	// TODO Auto-generated constructor stub
    }

    public Tag(String tag) {
	super();
	this.tag = tag;
    }

    public String getTag() {
	return tag;
    }

    public void setTag(String tag) {
	this.tag = tag;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

}
