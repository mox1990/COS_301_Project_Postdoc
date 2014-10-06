/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
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
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import com.softserve.transactioncontrollers.TransactionController;
import java.util.ArrayList;
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
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
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
    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }

    protected TransactionController getTransactionController()
    {
        return new TransactionController(emf);
    }

    protected ApplicationServicesUtil getApplicationServicesUTIL(EntityManager em)
    {
        return new ApplicationServicesUtil(em);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
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
        EntityManager em = emf.createEntityManager();

        try
        {      
            return getApplicationServicesUTIL(em).loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, StartIndex, maxNumberOfRecords);
        }
        finally
        {
            em.close();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DEANS_OFFICE_MEMBER})
    @AuditableMethod
    @Override
    public int countTotalPendingApplications(Session session) throws AuthenticationException, Exception
    {        
        EntityManager em = emf.createEntityManager();

        try
        {
            return getApplicationServicesUTIL(em).getTotalNumberOfPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
        }
        finally
        {
            em.close();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DEANS_OFFICE_MEMBER})
    @AuditableMethod(message = "Dean declined")
    @Override
    public void declineApplication(Session session, Application application, String reason) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            ApplicationServicesUtil applicationServices = getApplicationServicesUTIL(transactionController.StartTransaction());
            applicationServices.declineAppliction(session, application, reason);
            
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        
            List<Notification> notifications = new ArrayList<Notification>();

            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application endorsement declined", "Please note that the endorsement of the application '" + application.getProjectTitle() + "' has been declined for which you are the fellow of. The reason for this is as follows: " + reason));
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Application endorsement declined", "Please note that the endorsement of the application '" + application.getProjectTitle() + "' has been declined for which you are the grant holder of. The reason for this is as follows: " + reason));

            transactionController.CommitTransaction();
            
            getNotificationServiceEJB().sendBatchNotifications(new Session(session.getHttpSession(),session.getUser(),true),notifications, true);
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DEANS_OFFICE_MEMBER})
    @AuditableMethod(message = "Dean endorsed application")
    @Override
    public void endorseApplication(Session session, Application application, Endorsement endorsementReport) throws AuthenticationException, RollbackFailureException, NonexistentEntityException, Exception
    {        
        
        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
            EndorsementJpaController endorsementJpaController = dAOFactory.createEndorsementDAO();
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();        
            ArrayList<Notification> notifications = new ArrayList<Notification>();
            

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
            
            for(Person p : DRISMembers)
            {
                notifications.add(dBEntitiesFactory.createNotificationEntity(session.getUser(), p, "Application endorsed", "The application " + application.getProjectTitle() + " has been endorsed by " + session.getUser().getCompleteName() + ". Please review application for eligbility."));
            }
            
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application endorsed", "Please note that the application '" + application.getProjectTitle() + "' has been endorsed for which you are the fellow of. "));
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Application endorsed", "Please note that the application '" + application.getProjectTitle() + "' has been endorsed for which you are the grant holder of."));        

            transactionController.CommitTransaction();            
            
            getNotificationServiceEJB().sendBatchNotifications(new Session(session.getHttpSession(),session.getUser(),true),notifications, true); 
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
