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
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author K
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ApplicationRenewalService implements ApplicationRenewalServiceLocal { // TODO: Finalize the local or remote spec

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    @EJB
    private AuditTrailServiceLocal auditTrailServiceLocal;
    @EJB
    private UserGatewayLocal userGatewayLocal;
    @EJB
    private CVManagementServiceLocal cVManagementServiceLocal;
    
    protected UserGatewayLocal getUserGatewayServiceEJB()
    {
        return userGatewayLocal;
    }

    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }
    
    protected AuditTrailServiceLocal getAuditTrailServiceEJB()
    {
        return auditTrailServiceLocal;
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
    
    
    @Override
    public List<Application> getRenewableApplicationsForFellow(Session session, Person fellow) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        UserGatewayLocal userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
        userGateway.authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        GregorianCalendar startDate = getGregorianCalendarUTIL();
        GregorianCalendar endDate = getGregorianCalendarUTIL();
        
        final int NUMBER_OF_DAYS = 365;
        
        endDate.add(GregorianCalendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
        
        return applicationJpaController.getAllApplicationsForFellowWithEndDateInBetween(fellow, startDate.getTime(), endDate.getTime());
    }
    
    @Override
    public boolean doesApplicationHaveFinalProgressReport(Application application)
    {
        return getProgressReportMangementEJB().doesApplicationHaveFinalProgressReport(application);
    }
    
    @Override
    public void updateResearchFellowCV(Session session, Cv cv) throws AuthenticationException, CVAlreadExistsException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
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
        
    @Override
    public void createFinalProgressReportForApplication(Session session, Application application, ProgressReport progressReport) throws AuthenticationException, Exception
    {
        ProgressReportManagementService progressReportManagementService = getProgressReportMangementEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        if(getProgressReportMangementEJB().getNumberOfProgressReportsRequiredByApplication(application) == application.getProgressReportList().size() - 1)
        {
            progressReportManagementService.createProgressReport(session, application, progressReport);
        }
        else
        {
            throw new Exception("This is not the final progress report");
        }
    }
    
    @Override
    public void createRenewalApplication(Session session, Application oldApplication, Application application) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        UserGatewayLocal userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
        userGateway.authenticateUser(session, roles);
        //Authenticate user ownership of application
        userGateway.authenticateUserAsOwner(session, oldApplication.getFellow());
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        
        application.setTimestamp(getGregorianCalendarUTIL().getTime());
        application.setType(com.softserve.constants.PersistenceConstants.APPLICATION_TYPE_RENEWAL);
        application.setFellow(oldApplication.getFellow());
        application.setGrantHolder(oldApplication.getGrantHolder());
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED);
        
        applicationJpaController.create(application);
        
        //Log action
        AuditLog auditLog = getDBEntitiesFactory().buildAduitLogEntitiy("Opened a renewal application", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    @Override
    public void submitApplication(Session session, Application application) throws Exception
    {
        //Authenticate user privliges
        UserGatewayLocal userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_PROSPECTIVE_FELLOW);
        userGateway.authenticateUser(session, roles);
        //Authenticate user ownership of application
        userGateway.authenticateUserAsOwner(session, application.getFellow());
        
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        getApplicationServicesUtil().submitApplication(application);
        
        //Log action
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Submitted renewal application", session.getUser());
        auditTrailService.logAction(auditLog);
    }
}
