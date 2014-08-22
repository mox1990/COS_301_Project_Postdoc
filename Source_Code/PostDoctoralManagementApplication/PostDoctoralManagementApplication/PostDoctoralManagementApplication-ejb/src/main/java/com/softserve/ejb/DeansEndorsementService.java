/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.system.ApplicationServicesUtil;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.EndorsementJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Endorsement;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
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
public class DeansEndorsementService implements DeansEndorsementServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    @EJB
    private AuditTrailServiceLocal auditTrailServiceLocal;
    @EJB
    private UserGatewayLocal userGatewayLocal;
    
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
    
    public DeansEndorsementService() {
    }

    public DeansEndorsementService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected EndorsementJpaController getEndorsementDAO()
    {
        return new EndorsementJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected ApplicationServicesUtil getApplicationServicesUTIL()
    {
        return new ApplicationServicesUtil(emf);
    }
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    @Override
    public List<Application> loadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, StartIndex, maxNumberOfRecords);
    }
    
    @Override
    public int countTotalPendingApplications(Session session) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.getTotalNumberOfPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
    }
    
    @Override
    public void declineApplication(Session session, Application application, String reason) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        applicationServices.declineAppliction(session, application, reason);   
    }
    
    @Override
    public void endorseApplication(Session session, Application application, Endorsement endorsementReport) throws AuthenticationException, RollbackFailureException, NonexistentEntityException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        EndorsementJpaController endorsementJpaController = getEndorsementDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        NotificationServiceLocal notificationService = getNotificationServiceEJB();
        
        endorsementReport.setEndorsementID(application.getApplicationID());
        endorsementReport.setDean(session.getUser());
        endorsementReport.setTimestamp(getGregorianCalendar().getTime());
        endorsementReport.setApplication(application);
        endorsementJpaController.create(endorsementReport);
        
        application.setEndorsement(endorsementReport);        
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
        
        try
        {
            applicationJpaController.edit(application);
        }
        catch(Exception ex)
        {
            //If an error occurs during update of application the endorsement report must be removed as well
            endorsementJpaController.destroy(endorsementReport.getEndorsementID());
            throw ex;
        }
        
        //Log action  
        AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Endorsed application " + application.getApplicationID(), session.getUser());
        auditTrailService.logAction(auditLog);

        //Send notification to DRIS member(s)
        List<Person> DRISMembers = applicationJpaController.findAllDRISMembersWhoCanApproveApplication(application);
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        for(Person p : DRISMembers)
        {
            notifications.add(dBEntitiesFactory.buildNotificationEntity(session.getUser(), p, "Application endorsed", "The following application has been endorsed by " + session.getUser().getCompleteName() + ". Please review for eligbility."));
        }
        notificationService.sendBatchNotifications(notifications, true);
        
        
    }
}
