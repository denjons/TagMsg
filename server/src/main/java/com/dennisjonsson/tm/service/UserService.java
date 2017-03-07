package com.dennisjonsson.tm.service;

import java.util.ArrayList;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import com.dennisjonsson.tm.client.UserTagListDTO;
import com.dennisjonsson.tm.data.UserTransformer;
import com.dennisjonsson.tm.entity.User;
import com.dennisjonsson.tm.entity.UserTagRelation;

@ApplicationScoped
public class UserService extends DataSource {

    public User createUser() throws CSTServiceException {
	User user = null;

	beginTransaction();

	user = new User(new Date());

	em.persist(user);

	em.flush();

	endTransaction();

	return user;
    }

    public void addTagForUser(UserTagListDTO userTagDTO, String id) throws CSTServiceException {

	ArrayList<UserTagRelation> relations = new ArrayList<>();
	UserTransformer.toUserTagRelations(relations, userTagDTO, id);

	beginTransaction();

	relations.stream().forEach(this::addTagForUser);

	endTransaction();

    }

    private void addTagForUser(UserTagRelation relation) {

	StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("ADD_USER_TAG")
		.registerStoredProcedureParameter(0, String.class, ParameterMode.IN)
		.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);

	storedProcedure.setParameter(0, relation.getUser()).setParameter(1, relation.getTag());

	storedProcedure.execute();

    }

    public void removeTagForUser(UserTagListDTO userTagDTO, String id) throws CSTServiceException {

	ArrayList<UserTagRelation> relations = new ArrayList<>();
	UserTransformer.toUserTagRelations(relations, userTagDTO, id);

	beginTransaction();

	relations.stream().forEach(this::removeTagForUser);

	endTransaction();
    }

    private void removeTagForUser(UserTagRelation relation) {

	StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("REMOVE_USER_TAG")
		.registerStoredProcedureParameter(0, String.class, ParameterMode.IN)
		.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);

	storedProcedure.setParameter(0, relation.getUser()).setParameter(1, relation.getTag());

	storedProcedure.execute();

    }

}
