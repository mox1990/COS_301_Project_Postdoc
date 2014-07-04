/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.AuditLogJpaController;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author K
 */
@Stateless
public class AuditTrailService implements AuditTrailServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    protected AuditLogJpaController getAuditLogDAO()
    {
        return new AuditLogJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    @Override
    public AuditLog logAction(Person user, String action) throws Exception
    {
        AuditLog aLog = new AuditLog();
        aLog.setAction(action);
        aLog.setPersonID(user);
        
        aLog.setTimestamp(new Timestamp(new Date().getTime()));
        
        getAuditLogDAO().create(aLog);
        
        return aLog;
    }
    
    @Override
    public List<AuditLog> findAll()
    {
        return emf.createEntityManager().createNamedQuery("AuditLog.findAll", AuditLog.class).getResultList();
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
