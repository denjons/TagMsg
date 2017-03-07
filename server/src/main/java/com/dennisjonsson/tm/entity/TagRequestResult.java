package com.dennisjonsson.tm.entity;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQuery;

@NamedStoredProcedureQuery(name = "getRequestsFromTags", procedureName = "GET_REQUESTS_FROM_TAGS", resultClasses = {
		TagRequestResult.class })
@Entity
public class TagRequestResult extends RequestResult{

}
