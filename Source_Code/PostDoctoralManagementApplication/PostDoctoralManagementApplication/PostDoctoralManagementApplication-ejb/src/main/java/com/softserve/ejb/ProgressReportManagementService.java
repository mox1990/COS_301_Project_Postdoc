/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.ProgressReportJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
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
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProgressReportManagementService implements ProgressReportManagementServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
        
    public ProgressReportManagementService() {
    }
    
    public ProgressReportManagementService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    protected ProgressReportJpaController getProgressReportDAO()
    {
        return new ProgressReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
        
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
     protected GregorianCalendar getGregorianCalendarUTIL()
    {
        return new GregorianCalendar();
    }
     
     
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void createProgressReport(Session session, Application application, ProgressReport progressReport) throws Exception
    {        
        ProgressReportJpaController progressReportJpaController = getProgressReportDAO();
        
        progressReport.setApplication(application);
        progressReport.setTimestamp(getGregorianCalendarUTIL().getTime());
        progressReportJpaController.create(progressReport);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void updateProgressReport(Session session, ProgressReport progressReport) throws Exception
    {        
        ProgressReportJpaController progressReportJpaController = getProgressReportDAO();
        
        progressReportJpaController.edit(progressReport);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW})
    @AuditableMethod
    @Override
    public List<Application> allApplicationsWithPendingReportsForUser(Session session) throws Exception
    {        
        List<Application> output = new ArrayList<Application>();
        
        List<Application> applications = getApplicationDAO().findAllApplicationsWhosFellowIs(session.getUser());
        GregorianCalendar curCal = getGregorianCalendarUTIL();
        
        for(Application application : applications)
        {        
            GregorianCalendar startCal = getGregorianCalendarUTIL();
            startCal.setTime(application.getStartDate());            
            
            GregorianCalendar endCal = getGregorianCalendarUTIL();
            endCal.setTime(application.getEndDate());
            
            System.out.println("start date " + startCal.toString() + " end date" + endCal.toString() + " cur date" + curCal.toString() + " " + startCal.before(curCal) + " " + endCal.after(curCal));
            
            if(startCal.before(curCal) && endCal.after(curCal))
            {
                System.out.println("True " + getNumberOfProgressReportsRequiredByApplication(session,application) + " " + application.getProgressReportList().size() );
                if(getNumberOfProgressReportsRequiredByApplication(session,application) > application.getProgressReportList().size())
                {
                    System.out.println("Added" + application.toString());
                    output.add(application);
                }
            }
        }
        System.out.println("====" + output.toString());
        return output;
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW})
    @AuditableMethod
    @Override
    public boolean doesApplicationHaveFinalProgressReport(Session session, Application application) throws Exception
    {
        return getNumberOfProgressReportsRequiredByApplication(session,application) == application.getProgressReportList().size();
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW})
    @AuditableMethod
    @Override
    public int getNumberOfProgressReportsRequiredByApplication(Session session, Application application) throws Exception
    {       
        GregorianCalendar startCal = getGregorianCalendarUTIL();
        startCal.setTime(application.getStartDate());

        GregorianCalendar endCal = getGregorianCalendarUTIL();
        endCal.setTime(application.getEndDate());
        
        GregorianCalendar diffCal = getGregorianCalendarUTIL();
        diffCal.setTimeInMillis(endCal.getTimeInMillis() - startCal.getTimeInMillis());
        System.out.println("============= " + (diffCal.get(GregorianCalendar.YEAR) - 1970));
        return diffCal.get(GregorianCalendar.YEAR) - 1970;
    }
}