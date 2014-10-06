/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Exceptions.CVAlreadExistsException;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import com.softserve.transactioncontrollers.TransactionController;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author K
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ApplicationRenewalService implements ApplicationRenewalServiceLocal { // TODO: Finalize the local or remote spec

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    @EJB
    private CVManagementServiceLocal cVManagementServiceLocal;
    @EJB
    private ProgressReportManagementServiceLocal progressReportManagementServiceLocal;
    
    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }
    
    protected CVManagementServiceLocal getCVManagementServiceEJB()
    {
        return cVManagementServiceLocal;
    }
    
    protected ProgressReportManagementServiceLocal getProgressReportManagementServiceEJB()
    {
        return progressReportManagementServiceLocal;
    }

    public ApplicationRenewalService() {
    }
    
    public ApplicationRenewalService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }
    /**
     *
     * @return
     */
    protected TransactionController getTransactionController()
    {
        return new TransactionController(emf);
    }
    
    /**
     *
     * @return
     */
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
        
    protected ApplicationServicesUtil getApplicationServicesUtil(EntityManager em)
    {
        return new ApplicationServicesUtil(em);
    }
    
    protected GregorianCalendar getGregorianCalendarUTIL()
    {
        return new GregorianCalendar();
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW})
    @AuditableMethod
    @Override
    public List<Application> getRenewableApplicationsForFellow(Session session, Person fellow) throws Exception
    {
        EntityManager em = emf.createEntityManager();
        
        try
        {
            DAOFactory dAOFactory = getDAOFactory(em);
            
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();

            List<Application> applications  = applicationJpaController.findAllRenewalApplicationsWithStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, 0, Integer.MAX_VALUE);
            
            if(applications.isEmpty())
            {
                GregorianCalendar startDate = getGregorianCalendarUTIL();
                GregorianCalendar endDate = getGregorianCalendarUTIL();

                final int NUMBER_OF_DAYS = 365;

                endDate.add(GregorianCalendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
                applications = applicationJpaController.getAllNewApplicationsForFellowWithEndDateInBetween(fellow, startDate.getTime(), endDate.getTime());
            }
            
            return applications;            
        }
        finally
        {
            em.close();
        }
        
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW})
    @AuditableMethod
    @Override
    public boolean doesApplicationHaveFinalProgressReport(Session session,Application application) throws Exception
    {
        return getProgressReportManagementServiceEJB().doesApplicationHaveFinalProgressReport(session,application);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW})
    @AuditableMethod
    @Override
    public void updateResearchFellowCV(Session session, Cv cv) throws Exception
    {        
        if(cv == null)
        {
            throw new Exception("CV is not valid");
        }
        
        CVManagementServiceLocal cVManagementService = getCVManagementServiceEJB();
        if(cVManagementService.hasCV(session))
        {
            cVManagementService.updateCV(session, cv);
        }
        else
        {
            throw new Exception("CV does not exist valid");
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW})
    @AuditableMethod
    @Override
    public void createFinalProgressReportForApplication(Session session, Application application, ProgressReport progressReport) throws AuthenticationException, Exception
    {
        ProgressReportManagementServiceLocal progressReportManagementService = getProgressReportManagementServiceEJB();
        
        if(progressReportManagementService.getNumberOfProgressReportsRequiredByApplication(session,application) - 1 == application.getProgressReportList().size())
        {
            progressReportManagementService.createProgressReport(session, application, progressReport);
        }
        else
        {
            throw new Exception("This is not the final progress report");
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void createRenewalApplication(Session session, Application oldApplication, Application application) throws AuthenticationException, Exception
    {
        TransactionController transactionController = getTransactionController();
        try
        {
            DAOFactory dAOFactory = getDAOFactory(transactionController.StartTransaction());
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
            
            if(application.getApplicationID() == null || applicationJpaController.findApplication(application.getApplicationID()) == null)
            {

                application.setTimestamp(getGregorianCalendarUTIL().getTime());
                application.setType(com.softserve.constants.PersistenceConstants.APPLICATION_TYPE_RENEWAL);
                application.setFellow(oldApplication.getFellow());
                application.setGrantHolder(oldApplication.getGrantHolder());
                application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN);

                applicationJpaController.create(application);
            }
            else
            {
                applicationJpaController.edit(application);
            }
            
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void submitApplication(Session session, Application application) throws Exception
    {  
        TransactionController transactionController = getTransactionController();
        try
        {
            getApplicationServicesUtil(transactionController.StartTransaction()).submitApplication(application);
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
            List<Notification> notifications = new ArrayList<Notification>();

            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Renewal application submitted", "Please note that the renewal application '" + application.getProjectTitle() + "' has been submitted for which you are the fellow of."));
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Renewal application submitted", "Please note that the renewal application '" + application.getProjectTitle() + "' has been submitted for which you are the grant holder of."));
            
            transactionController.CommitTransaction();
            getNotificationServiceEJB().sendBatchNotifications(session, notifications, true);
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
}
