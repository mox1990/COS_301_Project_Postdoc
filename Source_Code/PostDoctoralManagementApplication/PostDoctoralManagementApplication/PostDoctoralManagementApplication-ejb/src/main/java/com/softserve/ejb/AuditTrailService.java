/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.AuditLogJpaController;
import com.softserve.DBEntities.AuditLog;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
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
    
    protected AuditLogJpaController getAuditLogDAO()
    {
        return new AuditLogJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }  
    
    //Just changed it so that it recieves the auditLog object not creates which should be hanadled by the calling function
    @Override
    public void logAction(AuditLog auditLog) throws Exception
    {        
        auditLog.setTimestamp(new Timestamp(new Date().getTime()));
        
        getAuditLogDAO().create(auditLog);
    }
    
    @Override
    public List<AuditLog> findAll()
    {
        return getAuditLogDAO().findAuditLogEntities();
    }
    
    @Override
    public List<AuditLog> findByTimestamp(Timestamp tStamp)
    {
        return emf.createEntityManager().createNamedQuery("AuditLog.findByTimestamp", AuditLog.class).setParameter("timestamp", tStamp).getResultList();
    }
    
    @Override
    public List<AuditLog> findBetweenRange(Timestamp start, Timestamp end)
    {
        return emf.createEntityManager().createNamedQuery("AuditLog.findBetweenRange", AuditLog.class).setParameter("start", start).setParameter("end", end).getResultList();
    }
    
    @Override
    public AuditLog findByEntryID(Long eID)
    {
        return emf.createEntityManager().createNamedQuery("AuditLog.findByEntryID", AuditLog.class).setParameter("entryID", eID).getSingleResult();
    }
    
    @Override
    public List<AuditLog> findByAction(String action)
    {
        return emf.createEntityManager().createNamedQuery("AuditLog.findByAction", AuditLog.class).setParameter("action", action).getResultList();
    }
}
