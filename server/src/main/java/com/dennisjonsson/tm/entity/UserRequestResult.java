package com.dennisjonsson.tm.entity;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQuery;

@NamedStoredProcedureQuery(name = "getUserRequests", procedureName = "GET_USER_REQUESTS", resultClasses = {
		UserRequestResult.class })
@Entity
public class UserRequestResult extends RequestResult {

}
