/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Exceptions.CVAlreadExistsException;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
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
    
    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }
    
    protected CVManagementServiceLocal getCVManagementServiceEJB()
    {
        return cVManagementServiceLocal;
    }

    public ApplicationRenewalService() {
    }
    
    public ApplicationRenewalService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /**
     *
     * @return
     */
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *
     * @return
     */
    protected ProgressReportManagementService getProgressReportMangementEJB()
    {
        return new ProgressReportManagementService(emf);
    }
    
    /**
     *
     * @return
     */
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
        
    protected ApplicationServicesUtil getApplicationServicesUtil()
    {
        return new ApplicationServicesUtil(emf);
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
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        List<Application> applications = applicationJpaController.findAllRenewalApplicationsWithStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, 0, Integer.MAX_VALUE);
        if(applications.isEmpty())
        {
            GregorianCalendar startDate = getGregorianCalendarUTIL();
            GregorianCalendar endDate = getGregorianCalendarUTIL();

            final int NUMBER_OF_DAYS = 365;

            endDate.add(GregorianCalendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
        
            return applicationJpaController.getAllNewApplicationsForFellowWithEndDateInBetween(fellow, startDate.getTime(), endDate.getTime());
        }
        else
        {
            return applications;
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW})
    @AuditableMethod
    @Override
    public boolean doesApplicationHaveFinalProgressReport(Session session,Application application) throws Exception
    {
        return getProgressReportMangementEJB().doesApplicationHaveFinalProgressReport(session,application);
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
        ProgressReportManagementService progressReportManagementService = getProgressReportMangementEJB();
        
        if(progressReportManagementService.getNumberOfProgressReportsRequiredByApplication(session,application) == application.getProgressReportList().size() - 1)
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
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        application.setTimestamp(getGregorianCalendarUTIL().getTime());
        application.setType(com.softserve.constants.PersistenceConstants.APPLICATION_TYPE_RENEWAL);
        application.setFellow(oldApplication.getFellow());
        application.setGrantHolder(oldApplication.getGrantHolder());
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN);
        
        applicationJpaController.create(application);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void submitApplication(Session session, Application application) throws Exception
    {  
        getApplicationServicesUtil().submitApplication(application);
    }
}
