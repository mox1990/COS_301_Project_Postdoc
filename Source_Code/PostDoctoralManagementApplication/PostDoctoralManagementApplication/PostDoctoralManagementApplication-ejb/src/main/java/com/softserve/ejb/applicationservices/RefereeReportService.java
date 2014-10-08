/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.auxillary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.RefereeReportJpaController;
import com.softserve.persistence.DBDAO.exceptions.RollbackFailureException;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.RefereeReport;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.auxillary.Exceptions.AuthenticationException;
import com.softserve.auxillary.annotations.AuditableMethod;
import com.softserve.auxillary.annotations.SecuredMethod;
import com.softserve.auxillary.interceptors.AuditTrailInterceptor;
import com.softserve.auxillary.interceptors.AuthenticationInterceptor;
import com.softserve.auxillary.util.ApplicationServicesUtil;
import com.softserve.auxillary.factories.DBEntitiesFactory;
import com.softserve.auxillary.requestresponseclasses.Session;
import com.softserve.auxillary.transactioncontrollers.TransactionController;
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
 * Ngako (12236731) Tokologo Machaba (12078027) ]o
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RefereeReportService implements RefereeReportServiceLocal {

    @PersistenceUnit(unitName = com.softserve.auxillary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;

    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }
    
    public RefereeReportService() {
    }
    
    public RefereeReportService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    /**
     *
     * @return
     */
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
    
    /**
     *
     * @return
     */
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    /**
     *This function loads all the applications that need to approved/declined by the 
     * specified HOD
     * @param session The session object used to authenticate the user
     * @return A list of all the applications that user can Approved/declined
     * @throws com.softserve.Exceptions.AuthenticationException
     * @throws java.lang.Exception
     */
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_REFEREE}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public List<Application> loadPendingApplications(Session session, int StartIndex, int maxNumberOfRecords) throws AuthenticationException, Exception
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getApplicationServicesUTIL(em).loadPendingApplications(session.getUser(), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED, StartIndex, maxNumberOfRecords);
        }
        finally
        {
            em.close();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_REFEREE}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public int countTotalPendingApplications(Session session) throws AuthenticationException, Exception
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getApplicationServicesUTIL(em).getTotalNumberOfPendingApplications(session.getUser(), com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
        }
        finally
        {
            em.close();
        }
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
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_REFEREE}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public void submitReferralReport(Session session, Application application, RefereeReport refereeReport) throws AuthenticationException, RollbackFailureException, Exception
    {
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
            RefereeReportJpaController refereeReportJpaController = dAOFactory.createRefereeReportDAO();
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();

            refereeReport.setTimestamp(getGregorianCalendar().getTime());
            refereeReport.setApplicationID(application);
            refereeReportJpaController.create(refereeReport);


            application = applicationJpaController.findApplication(application.getApplicationID());

            List<Notification> notifications = new ArrayList<Notification>();
           
            
            if(application.getPersonList().size() == application.getRefereeReportList().size())
            {
                application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);

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
                notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application referred", "The application '" + application.getProjectTitle() + "' has been referred for which you are the fellow of."));
                notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Application referred", "The application '" + application.getProjectTitle() + "' has been referred. Please review for finalisation."));
            }
            
            transactionController.CommitTransaction();
            getNotificationServiceEJB().sendBatchNotifications(new Session(session.getHttpSession(),session.getUser(),true),notifications,true);
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
