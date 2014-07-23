/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.RefereeReportJpaController;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]o
 */

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RefereeReportService implements RefereeReportServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    public RefereeReportService() {
    }
    
    public RefereeReportService(EntityManagerFactory emf) {
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
    protected RefereeReportJpaController getRefereeReportDAO()
    {
        return new RefereeReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
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
    
    /**
     *
     * @return
     */
    protected ApplicationServices getApplicationServicesUTIL()
    {
        return new ApplicationServices(emf);
    }
    
    /**
     *This function loads all the applications that need to approved/declined by the 
     * specified HOD
     * @param session The session object used to authenticate the user
     * @return A list of all the applications that user can Approved/declined
     * @throws com.softserve.Exceptions.AuthenticationException
     * @throws java.lang.Exception
     */
    @Override
    public List<Application> loadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServices applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED, StartIndex, maxNumberOfRecords);
    }
    
    @Override
    public int countTotalPendingApplications(Session session) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServices applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.getTotalNumberOfPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
    }
    
    /**
     *
     * @param session
     * @param application
     * @param refereeReport
     * @throws AuthenticationException
     * @throws RollbackFailureException
     * @throws Exception
     */
    @Override
    public void submitReferralReport(Session session, Application application, RefereeReport refereeReport) throws AuthenticationException, RollbackFailureException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        RefereeReportJpaController refereeReportJpaController = getRefereeReportDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        NotificationService notificationService = getNotificationServiceEJB();
        
        refereeReport.setApplicationID(application);
        refereeReportJpaController.create(refereeReport);
        
        //Log action        
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Application refereed" + application.getApplicationID(), session.getUser());
        auditTrailService.logAction(auditLog);
        
        if(application.getPersonList().size() == application.getRefereeReportList().size())
        {
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED);
        
            try
            {
                applicationJpaController.edit(application);
            }
            catch(Exception ex)
            {
                //If an error occurs during update of application the recommendation report must be removed as well
                refereeReportJpaController.destroy(refereeReport.getReportID());
                throw ex;
            }
            
            //Send notification to Grant holder
            notificationService.sendNotification(dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getGrantHolderID(), "Application refereed", "The following application has been refereed. Please review for finalisation."),true);
        }
    }
    
}
