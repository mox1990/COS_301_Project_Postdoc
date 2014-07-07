/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.*;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.PreexistingEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.Exceptions.AutomaticSystemIDGenerationException;
import com.softserve.system.Session;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpSession;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Carlo
 */

public class UserGateway implements UserGatewayLocal
 { // TODO: Finalize the local or remote spec

    @PersistenceContext(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManager em;
    
    
    @Override
    public void authenticateUser(Person user, UpEmployeeInformation userUPInfo)
    {
        
    }
    
    @Override
    public void getRole(Person user)
    {
        
        
    }
    
    
    @Override
    public void generateOnDemandAccount(Session session, boolean useManualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo)
    {
        
    
    }
    
    /**
     *
     * @param user
     * @param active
     */
    @Override
    public void activateOnDemandAccount(Person user, boolean active)
    {
    
    }
    
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
}
