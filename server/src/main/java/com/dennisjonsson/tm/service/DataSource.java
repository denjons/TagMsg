package com.dennisjonsson.tm.service;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@ApplicationScoped
public class DataSource {
	
	@PersistenceContext(unitName = "jta_unit")
	protected EntityManager em;
	
	@Resource
	protected UserTransaction userTransaction;
	
	protected void beginTransaction() throws CSTServiceException{
		
		try {
			userTransaction.begin();
		} catch (NotSupportedException | SystemException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getMessage());
		}
	
	}

	protected void endTransaction() throws CSTServiceException{
	
		try {
			userTransaction.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException e) {
			e.printStackTrace();
			throw new CSTServiceException(e.getMessage());
			
		}
	
		
}
}
