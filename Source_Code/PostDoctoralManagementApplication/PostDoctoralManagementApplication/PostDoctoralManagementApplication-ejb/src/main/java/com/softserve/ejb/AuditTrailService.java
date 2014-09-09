/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.AuditLogJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBEntities.AuditLog;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.annotations.TransactionMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.interceptors.TransactionInterceptor;
import com.softserve.system.Session;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuditTrailInterceptor.class, AuthenticationInterceptor.class,TransactionInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AuditTrailService implements AuditTrailServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    public AuditTrailService() {
    }
    
    public AuditTrailService(EntityManagerFactory emf) 
    {
        this.emf = emf;
    }
    
    protected DAOFactory getDAOFactory()
    {
        return new DAOFactory(emf);
    } 
    
    //Just changed it so that it recieves the auditLog object not creates which should be hanadled by the calling function
    @TransactionMethod
    @Override
    public void logAction(AuditLog auditLog) throws Exception
    {        
        auditLog.setTimestamp(new Timestamp(new Date().getTime()));
        if(auditLog.getAction().length() > 500)
        {
            auditLog.setAction(auditLog.getAction().substring(0, 500));
        }
        getDAOFactory().createAuditLogDAO().create(auditLog);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public List<AuditLog> loadAllAuditLogEntries(Session session) throws Exception
    {
        return getDAOFactory().createAuditLogDAO().findAuditLogEntities();
    }

}
