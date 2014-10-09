/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.nonapplicationservices;

import com.softserve.persistence.DBDAO.AuditLogJpaController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.auxiliary.annotations.AuditableMethod;
import com.softserve.auxiliary.annotations.SecuredMethod;
import com.softserve.auxiliary.interceptors.AuditTrailInterceptor;
import com.softserve.auxiliary.interceptors.AuthenticationInterceptor;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuditTrailInterceptor.class, AuthenticationInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AuditTrailService implements AuditTrailServiceLocal {

    @PersistenceUnit(unitName = com.softserve.auxiliary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    public AuditTrailService() {
    }
    
    public AuditTrailService(EntityManagerFactory emf) 
    {
        this.emf = emf;
    }
    
    protected DAOFactory getDAOFactory(EntityManager em)
{
	return new DAOFactory(em);
}

    protected TransactionController getTransactionController()
    {
        return new TransactionController(emf);
    }
    
    protected EntityManager createEntityManager()
    {
        return emf.createEntityManager();
    }
    
    //Just changed it so that it recieves the auditLog object not creates which should be hanadled by the calling function
    @SecuredMethod(AllowedSecurityRoles = {})
    @Override
    public void logAction(Session session, AuditLog auditLog) throws Exception
    {        
        TransactionController transactionController = getTransactionController();
	transactionController.StartTransaction();        
        try
	{                        
            auditLog.setTimestamp(new Timestamp(new Date().getTime()));
            if(auditLog.getAction().length() > 500)
            {
                auditLog.setAction(auditLog.getAction().substring(0, 500));
            }
            
            transactionController.getDAOFactoryForTransaction().createAuditLogDAO().create(auditLog);

            transactionController.CommitTransaction();
	}
	catch(Exception ex)
	{
            transactionController.RollbackTransaction();
            throw ex;
	}
	finally
	{
            transactionController.CloseEntityManagerForTransaction();
	}
        
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public List<AuditLog> loadAllAuditLogEntries(Session session) throws Exception
    {
        EntityManager em = emf.createEntityManager();

	try
	{
            return getDAOFactory(em).createAuditLogDAO().findAuditLogEntities();
	}
	finally
	{
            em.close();
	}
        
        
    }

}
