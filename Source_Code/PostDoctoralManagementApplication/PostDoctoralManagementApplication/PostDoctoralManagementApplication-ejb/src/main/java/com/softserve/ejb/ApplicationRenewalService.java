/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import com.softserve.system.Session;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author K
 */
@Stateless
public class ApplicationRenewalService implements ApplicationRenewalServiceLocal { // TODO: Finalize the local or remote spec

    @PersistenceContext(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManager em;
    
    public AuditLog logAction(Person user, String action)
    {
        AuditLog aLog = new AuditLog();
        aLog.setAction(action);
        aLog.setPersonID(user);
        
        aLog.setTimestamp(new Timestamp(new Date().getTime()));
        
        em.persist(aLog);
        
        return aLog;
    }
    
    public List<AuditLog> findAll()
    {
        return em.createNamedQuery("AuditLog.findAll", AuditLog.class).getResultList();
    }
    
    public List<AuditLog> findByTimestamp(Timestamp tStamp)
    {
        return em.createNamedQuery("AuditLog.findByTimestamp", AuditLog.class).setParameter("timestamp", tStamp).getResultList();
    }
    
    public List<AuditLog> findBetweenRange(Timestamp tStamp)
    {
        // TODO: reiterate the new namedQuery
        return em.createNamedQuery("AuditLog.findByTimestamp", AuditLog.class).setParameter("timestamp", tStamp).getResultList();
    }
    
    public List<AuditLog> findByEntryID(Long eID)
    {
        return em.createNamedQuery("AuditLog.findByEntryID", AuditLog.class).setParameter("entryID", eID).getResultList();
    }
    
    public List<AuditLog> findByAction(String action)
    {
        return em.createNamedQuery("AuditLog.findByAction", AuditLog.class).setParameter("action", action).getResultList();
    }

    @Override
    public void createProgressReport(Session session, Application application, String report) throws NonexistentEntityException, RollbackFailureException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void submitProgressReport(Session session, Application application, String report) throws NonexistentEntityException, RollbackFailureException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
