/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.AnnouncementJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBEntities.Announcement;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.interceptors.*;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import com.softserve.transactioncontrollers.TransactionController;
import java.util.Date;
import java.util.List;
import javax.ejb.Asynchronous;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuditTrailInterceptor.class, AuthenticationInterceptor.class})
@TransactionManagement(TransactionManagementType.BEAN)
@Stateless
public class AnnouncementManagementService implements AnnouncementManagementServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected TransactionController getTransactionController()
    {
        return new TransactionController(emf);
    }
    
    @Override
    public List<Announcement> loadAllActiveAnnouncements() throws Exception 
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            return getDAOFactory(em).createAnnouncementDAO().findAllActiveAnnouncements();
        }
        finally
        {
            em.close();
        }
    }

    @Override
    public List<Announcement> loadAllPendingAnnouncements() throws Exception 
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            return getDAOFactory(em).createAnnouncementDAO().findPendingAnnouncements();
        }
        finally
        {
            em.close();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR} )
    @AuditableMethod 
    @Override
    public void createAnnouncement(Session session, Announcement announcement) throws Exception 
    {
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            announcement.setTimestamp(new Date());
            dAOFactory.createAnnouncementDAO().create(announcement);
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR} )
    @AuditableMethod 
    @Override
    public void updateAnnouncement(Session session, Announcement announcement) throws Exception 
    {
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            dAOFactory.createAnnouncementDAO().edit(announcement);
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR} )
    @AuditableMethod 
    @Override
    public void removeAnnouncement(Session session, Announcement announcement) throws Exception 
    {
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            dAOFactory.createAnnouncementDAO().destroy(announcement.getAnnouncementID());
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
    
    @Schedule(minute = "*", hour = "*")
    @Asynchronous 
    private void removeAllExpiredAnnouncements() throws Exception
    {
        
        EntityManager em = emf.createEntityManager();
        
        try
        {
            AnnouncementJpaController announcementJpaController = getDAOFactory(em).createAnnouncementDAO();
            
            List<Announcement> announcements = announcementJpaController.findAllEndedAnnouncements();
            
            for(Announcement announcement : announcements)
            {
                removeAnnouncement(new Session(null, null, Boolean.TRUE), announcement);
            }
        }
        finally
        {
            em.close();
        }
    }
    

}
