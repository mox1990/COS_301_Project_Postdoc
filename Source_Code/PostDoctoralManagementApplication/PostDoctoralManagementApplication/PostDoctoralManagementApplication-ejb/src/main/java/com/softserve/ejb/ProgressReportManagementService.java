/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ProgressReportJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
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
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProgressReportManagementService implements ProgressReportManagementServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private AuditTrailServiceLocal auditTrailServiceLocal;
    @EJB
    private UserGatewayLocal userGatewayLocal;
    
    protected UserGatewayLocal getUserGatewayServiceEJB()
    {
        return userGatewayLocal;
    }
    
    protected AuditTrailServiceLocal getAuditTrailServiceEJB()
    {
        return auditTrailServiceLocal;
    }
    
    public ProgressReportManagementService() {
    }
    
    public ProgressReportManagementService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    protected ProgressReportJpaController getProgressReportDAO()
    {
        return new ProgressReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
        
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
     protected GregorianCalendar getGregorianCalendarUTIL()
    {
        return new GregorianCalendar();
    }
    
    public void createProgressReport(Session session, Application application, ProgressReport progressReport) throws AuthenticationException, Exception
    {
        UserGatewayLocal userGateway = getUserGatewayServiceEJB();
        try
        {
            //Authenticate user ownership of account
            userGateway.authenticateUserAsOwner(session, application.getFellow());
        } 
        catch(AuthenticationException ex)
        {
            //Authenticate user privliges
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            userGateway.authenticateUser(session, roles);
        }
        
        ProgressReportJpaController progressReportJpaController = getProgressReportDAO();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        progressReport.setApplication(application);
        progressReport.setTimestamp(getGregorianCalendarUTIL().getTime());
        progressReportJpaController.create(progressReport);
        
        AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Created progress report", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    public void updateProgressReport(Session session, ProgressReport progressReport) throws AuthenticationException, Exception
    {
        UserGatewayLocal userGateway = getUserGatewayServiceEJB();
        try
        {
            //Authenticate user ownership of account
            userGateway.authenticateUserAsOwner(session, progressReport.getApplication().getFellow());
        } 
        catch(AuthenticationException ex)
        {
            //Authenticate user privliges
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            userGateway.authenticateUser(session, roles);
        }
        
        ProgressReportJpaController progressReportJpaController = getProgressReportDAO();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        progressReportJpaController.edit(progressReport);
        
        AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("updated progress report", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    @Override
    public List<Application> allApplicationsWithPendingReportsForUser(Session session) throws Exception
    {
        
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        List<Application> output = new ArrayList<Application>();
        
        Person user = session.getUser();
        GregorianCalendar curCal = getGregorianCalendarUTIL();
        
        for(Application application : user.getApplicationList1())
        {        
            GregorianCalendar startCal = getGregorianCalendarUTIL();
            startCal.setTime(application.getStartDate());
            
            
            GregorianCalendar endCal = getGregorianCalendarUTIL();
            endCal.setTime(application.getEndDate());
            
            if(startCal.before(curCal) && endCal.after(curCal))
            {
                if(getNumberOfProgressReportsRequiredByApplication(application) > application.getProgressReportList().size())
                {
                    output.add(application);
                }
            }
        }
        
        return output;
    }
    
    @Override
    public boolean doesApplicationHaveFinalProgressReport(Application application)
    {
        return getNumberOfProgressReportsRequiredByApplication(application) == application.getProgressReportList().size();
    }
    
    @Override
    public int getNumberOfProgressReportsRequiredByApplication(Application application)
    {       
        GregorianCalendar startCal = getGregorianCalendarUTIL();
        startCal.setTime(application.getStartDate());

        GregorianCalendar endCal = getGregorianCalendarUTIL();
        endCal.setTime(application.getEndDate());
        
        GregorianCalendar diffCal = getGregorianCalendarUTIL();
        diffCal.setTimeInMillis(endCal.getTimeInMillis() - startCal.getTimeInMillis());
        System.out.println("============= " + diffCal.get(GregorianCalendar.YEAR));
        return diffCal.get(GregorianCalendar.YEAR);
    }
}