/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.DAOFactory;
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
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.annotations.TransactionMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.interceptors.TransactionInterceptor;
import com.softserve.system.ApplicationServicesUtil;
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
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class, TransactionInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DeansEndorsementService implements DeansEndorsementServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;

    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }
    
    public DeansEndorsementService() {
    }

    public DeansEndorsementService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    protected DAOFactory getDAOFactory()
    {
        return new DAOFactory(emf.createEntityManager());
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected ApplicationServicesUtil getApplicationServicesUTIL()
    {
        return new ApplicationServicesUtil(emf.createEntityManager());
    }
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DEANS_OFFICE_MEMBER})
    @AuditableMethod
    @Override
    public List<Application> loadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords) throws AuthenticationException, Exception
    {        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, StartIndex, maxNumberOfRecords);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DEANS_OFFICE_MEMBER})
    @AuditableMethod
    @Override
    public int countTotalPendingApplications(Session session) throws AuthenticationException, Exception
    {        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        
        return applicationServices.getTotalNumberOfPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DEANS_OFFICE_MEMBER})
    @AuditableMethod(message = "Dean declined")
    @TransactionMethod
    @Override
    public void declineApplication(Session session, Application application, String reason) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {        
        ApplicationServicesUtil applicationServices = getApplicationServicesUTIL();
        applicationServices.declineAppliction(session, application, reason);   
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DEANS_OFFICE_MEMBER})
    @AuditableMethod(message = "Dean endorsed application")
    @TransactionMethod
    @Override
    public void endorseApplication(Session session, Application application, Endorsement endorsementReport) throws AuthenticationException, RollbackFailureException, NonexistentEntityException, Exception
    {        
        DAOFactory dAOFactory = getDAOFactory();
        ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
        EndorsementJpaController endorsementJpaController = dAOFactory.createEndorsementDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
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
        
        //Send notification to DRIS member(s)
        List<Person> DRISMembers = applicationJpaController.findAllDRISMembersWhoCanApproveApplication(application);
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        for(Person p : DRISMembers)
        {
            notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), p, "Application endorsed", "The following application has been endorsed by " + session.getUser().getCompleteName() + ". Please review for eligbility."));
        }
        notificationService.sendBatchNotifications(notifications, true); 
    }
}
