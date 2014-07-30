/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ProgressReportJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
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
    
    protected UserGateway getUserGatewayServiceEJB()
    {
        return new UserGateway(emf);
    }
    
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return new AuditTrailService(emf);
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
        UserGateway userGateway = getUserGatewayServiceEJB();
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
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        progressReport.setApplication(application);
        progressReport.setTimestamp(getGregorianCalendarUTIL().getTime());
        progressReportJpaController.create(progressReport);
        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Created progress report", session.getUser());
        auditTrailService.logAction(auditLog);
    }
    
    public void updateProgressReport(Session session, ProgressReport progressReport) throws AuthenticationException, Exception
    {
        UserGateway userGateway = getUserGatewayServiceEJB();
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
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
        progressReportJpaController.edit(progressReport);
        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("updated progress report", session.getUser());
        auditTrailService.logAction(auditLog);
    }
}
