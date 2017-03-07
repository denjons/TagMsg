package com.dennisjonsson.tm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;

@NamedStoredProcedureQuery(name = "getEligibleRequests", procedureName = "GET_ELIGIBLE_REQUESTS", resultClasses = {
		EligibleRequestsResult.class })
@Entity
public class EligibleRequestsResult extends RequestResult {
	

}
