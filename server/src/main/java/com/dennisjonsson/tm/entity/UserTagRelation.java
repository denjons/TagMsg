package com.dennisjonsson.tm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;

import com.google.common.base.Objects;

@Entity
@IdClass(UserTagRelation.class)
public class UserTagRelation implements Serializable {

    @Id
    @NotNull
    @Column(name = "user", nullable = false)
    private String user;

    @Id
    @NotNull
    @Column(name = "tag", nullable = false)
    private String Tag;

    public UserTagRelation() {
    }

    public UserTagRelation(String user, String tag) {
	super();
	this.user = user;
	Tag = tag;
    }

    public String getUser() {
	return user;
    }

    public void setUser(String user) {
	this.user = user;
    }

    public String getTag() {
	return Tag;
    }

    public void setTag(String tag) {
	Tag = tag;

    }

    @Override
    public int hashCode() {
	return Objects.hashCode(user, Tag);
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof UserTagRelation)) {
	    return false;
	}

	UserTagRelation utr = (UserTagRelation) obj;
	if (!utr.Tag.equals(this.Tag) || !utr.user.equals(this.user)) {
	    return false;
	}

	return true;
    }

}
