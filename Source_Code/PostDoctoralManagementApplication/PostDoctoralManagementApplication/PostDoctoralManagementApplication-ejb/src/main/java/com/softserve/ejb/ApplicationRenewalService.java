/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.RecommendationReportJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author K
 */
@Stateless
public class ApplicationRenewalService implements ApplicationRenewalServiceLocal { // TODO: Finalize the local or remote spec

    @PersistenceContext(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManager em;
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

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
    
    /**
     *
     * @return
     */
    protected UserGateway getUserGatewayServiceEJB()
    {
        return new UserGateway(emf);
    }
    
    /**
     *
     * @return
     */
    protected NotificationService getNotificationServiceEJB()
    {
        return new NotificationService(emf);
    }
    
    /**
     *
     * @return
     */
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return new AuditTrailService(emf);
    }  
    
    protected GregorianCalendar getGregorianCalendarUTIL()
    {
        return new GregorianCalendar();
    }
    
    @Override
    public List<Application> getRenewableApplicationsForFellow(Session session, Person fellow) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        UserGateway userGateway = getUserGatewayServiceEJB();
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
    
    public List<AuditLog> findByTimestamp(Timestamp tStamp)
    {
        return em.createNamedQuery("AuditLog.findByTimestamp", AuditLog.class).setParameter("timestamp", tStamp).getResultList();
    }
    
    @Override
    public boolean doesApplicationHaveFinalProgressReport(Session session, Application application) throws AuthenticationException, Exception
    {
        GregorianCalendar gregorianCalendar = getGregorianCalendarUTIL();
        gregorianCalendar.setTimeInMillis(application.getEndDate().getTime() - application.getStartDate().getTime());
        
        int noOfYears = gregorianCalendar.get(GregorianCalendar.YEAR) + 1;
        
        return (application.getProgressReportList().size() == noOfYears);
    }
        
    @Override
    public void createFinalProgressReportForApplication(Session session, Application application, String report) throws AuthenticationException, Exception
    {
        ProgressReportManagementService progressReportManagementService = getProgressReportMangementEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        ProgressReport progressReport = dBEntitiesFactory.bulidProgressReportEntity(null, report, null);        
        
        progressReportManagementService.createProgressReport(session, application, progressReport);
    }
    
    @Override
    public void createRenewalApplication(Session session, Application oldApplication, Application application) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        UserGateway userGateway = getUserGatewayServiceEJB();
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
        userGateway.authenticateUser(session, roles);
        //Authenticate user ownership of application
        userGateway.authenticateUserAsOwner(session, oldApplication.getFellow());
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        
        application.setType(com.softserve.constants.PersistenceConstants.APPLICATION_TYPE_RENEWAL);
        application.setFellow(oldApplication.getFellow());
        application.setGrantHolderID(oldApplication.getGrantHolderID());
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED);
        
        applicationJpaController.create(application);
        
        //Log action
        AuditLog auditLog = getDBEntitiesFactory().buildAduitLogEntitiy("Opened a renewal application", session.getUser());
        auditTrailService.logAction(auditLog);
    }
}
