/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.auxillary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.EligiblityReportJpaController;
import com.softserve.persistence.DBDAO.EndorsementJpaController;
import com.softserve.persistence.DBDAO.ForwardAndRewindReportJpaController;
import com.softserve.persistence.DBDAO.FundingCostJpaController;
import com.softserve.persistence.DBDAO.FundingReportJpaController;
import com.softserve.persistence.DBDAO.RecommendationReportJpaController;
import com.softserve.persistence.DBDAO.RefereeReportJpaController;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.ApplicationReviewRequest;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.ForwardAndRewindReport;
import com.softserve.persistence.DBEntities.FundingCost;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.RefereeReport;
import com.softserve.persistence.DBEntities.SecurityRole;
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
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ForwardAndRewindServices implements ForwardAndRewindServicesLocal {

    @PersistenceUnit(unitName=com.softserve.auxillary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;

    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }

    public ForwardAndRewindServices() {
    }
    
    public ForwardAndRewindServices(EntityManagerFactory emf) 
    {
        this.emf = emf;
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
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
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    protected EntityManager createEntityManager()
    {
        return emf.createEntityManager();
    }
    
    protected void rewindApplicationToOpenStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            rewindApplicationToSubmittedStatus(transactionController,application);
            application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_OPEN);
            
            application.setSubmissionDate(null);
        }
    }
    
    protected void rewindApplicationToSubmittedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            rewindApplicationToReferredStatus(transactionController,application);
            application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
            
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            RefereeReportJpaController refereeReportJpaController = dAOFactory.createRefereeReportDAO();
            
            for(RefereeReport refereeReport : application.getRefereeReportList())
            {
                refereeReportJpaController.destroy(refereeReport.getReportID());
            }
            
            application.setRefereeReportList(new ArrayList<RefereeReport>());
                        
        }
    }
    
    protected void rewindApplicationToReferredStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            rewindApplicationToFinalisedStatus(transactionController,application);
            application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
            
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            application.setFinalisationDate(null);
            
            if(application.getApplicationReviewRequestList() != null)
            {
                ApplicationReviewRequestJpaController applicationReviewRequestJpaController = dAOFactory.createApplicationReviewRequestDAO();
                for(ApplicationReviewRequest applicationReviewRequest : application.getApplicationReviewRequestList())
                {
                    if(applicationReviewRequest.getApplicationReviewRequestPK().getType().equals(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD))
                    {
                        applicationReviewRequestJpaController.destroy(applicationReviewRequest.getApplicationReviewRequestPK());
                    }
                }
            }
        }
    }
    
    protected void rewindApplicationToFinalisedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            rewindApplicationToRecommendedStatus(transactionController,application);
            application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
            
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            if(application.getRecommendationReport() != null)
            {
                dAOFactory.createRecommendationReportDAO().destroy(application.getRecommendationReport().getReportID());
                application.setRecommendationReport(null);
            }
            
            if(application.getApplicationReviewRequestList() != null)
            {
                ApplicationReviewRequestJpaController applicationReviewRequestJpaController = dAOFactory.createApplicationReviewRequestDAO();
                for(ApplicationReviewRequest applicationReviewRequest : application.getApplicationReviewRequestList())
                {
                    if(applicationReviewRequest.getApplicationReviewRequestPK().getType().equals(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_DEAN))
                    {
                        applicationReviewRequestJpaController.destroy(applicationReviewRequest.getApplicationReviewRequestPK());
                    }
                }
            }
        }
    }
    
    protected void rewindApplicationToRecommendedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
        {
            rewindApplicationToEndorsedStatus(transactionController,application);
            application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
            
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            if(application.getEndorsement() != null)
            {
                dAOFactory.createEndorsementDAO().destroy(application.getEndorsement().getEndorsementID());
                application.setEndorsement(null);
            }
        }
    }
    
    protected void rewindApplicationToEndorsedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE))
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
            if(application.getFundingReport() != null)
            {
                FundingCostJpaController fundingCostJpaController = dAOFactory.createFundingCostJpaController();
                for(FundingCost fundingCost : application.getFundingReport().getFundingCostList())
                {
                    fundingCostJpaController.destroy(fundingCost.getCostID());
                }
                
                dAOFactory.createFundingReportDAO().destroy(application.getFundingReport().getReportID());
                application.setFundingReport(null);
            }
            if(application.getEligiblityReport() != null)
            {
                dAOFactory.createEligiblityReportDAO().destroy(application.getEligiblityReport().getReportID());
                application.setEligiblityReport(null);                
            }           
            
        }
    }    
    
        
    protected void forwardApplicationToSubmittedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(!getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
            application.setSubmissionDate(getGregorianCalendar().getTime());
        }
    }
    
    protected void forwardApplicationToReferredStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(!getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            forwardApplicationToSubmittedStatus(transactionController,application);
            application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
        }
    }
    
    protected void forwardApplicationToFinalisedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(!getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            forwardApplicationToReferredStatus(transactionController,application);
            application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
            application.setFinalisationDate(getGregorianCalendar().getTime());
        }
    }
    
    protected void forwardApplicationToRecommendedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(!getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            forwardApplicationToFinalisedStatus(transactionController,application);
            application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
        }
    }
    
    protected void forwardApplicationToEndorsedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(!getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
        {
            forwardApplicationToRecommendedStatus(transactionController,application);
            application.setStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod(message = "Application forwared")
    @Override
    public void forwardApplication(Session session, Application application, String toStatus, String reason) throws Exception 
    {        
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
            ForwardAndRewindReport forwardAndRewindReport = dBEntitiesFactory.createForwardAndRewindReport(application, session.getUser(), getGregorianCalendar().getTime(), reason, com.softserve.auxillary.constants.PersistenceConstants.FORWARDREWINREPORT_TYPE_FORWARD, toStatus, application.getStatus());

            switch (toStatus) 
            {
                case com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED:
                    forwardApplicationToSubmittedStatus(transactionController,application);
                    if(application.getPersonList().isEmpty())
                    {
                        forwardApplicationToReferredStatus(transactionController,application);
                    }   break;
                case com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED:
                    forwardApplicationToReferredStatus(transactionController,application);
                    break;
                case com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED:
                    forwardApplicationToFinalisedStatus(transactionController,application);
                    break;
                case com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED:
                    forwardApplicationToRecommendedStatus(transactionController,application);
                    break;
                case com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED:
                    forwardApplicationToEndorsedStatus(transactionController,application);
                    break;
                default:
                    throw new Exception("The status " + toStatus + " specified to forward to does not exist");
            }

            dAOFactory.createApplicationDAO().edit(application);
            dAOFactory.createForwardAndRewindReportDAO().create(forwardAndRewindReport);
            
            List<Notification> notifications = new ArrayList<Notification>();
        
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application forwarded", "Please note that the application '" + application.getProjectTitle() + "' has been forwarded to " + toStatus + " for which you are the fellow of. The reason for this is as follows: " + reason));
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Application forwarded", "Please note that the application '" + application.getProjectTitle() + "' has been forwarded to " + toStatus + " for which you are the grant holder of. The reason for this is as follows: " + reason));

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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod(message = "Application rewinded")
    @Override
    public void rewindApplication(Session session, Application application, String toStatus, String reason) throws Exception 
    {   
        TransactionController transactionController = getTransactionController();
        transactionController.StartTransaction();        
        try
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
            ForwardAndRewindReport forwardAndRewindReport = dBEntitiesFactory.createForwardAndRewindReport(application, session.getUser(), getGregorianCalendar().getTime(), reason, com.softserve.auxillary.constants.PersistenceConstants.FORWARDREWINREPORT_TYPE_REWIND, toStatus, application.getStatus());
        
            switch (toStatus) 
            {
                case com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_OPEN:
                    rewindApplicationToOpenStatus(transactionController,application);
                    break;
                case com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED:
                    rewindApplicationToSubmittedStatus(transactionController,application);
                    if(application.getPersonList().isEmpty())
                    {
                        forwardApplicationToReferredStatus(transactionController,application);
                    }   break;
                case com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED:
                    rewindApplicationToReferredStatus(transactionController,application);
                    break;
                case com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED:
                    rewindApplicationToFinalisedStatus(transactionController,application);
                    break;
                case com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED:
                    rewindApplicationToRecommendedStatus(transactionController,application);
                    break;
                case com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED:
                    rewindApplicationToEndorsedStatus(transactionController,application);
                    break;
                default:
                    throw new Exception("The status specified to forward to does not exist");
            }

            dAOFactory.createApplicationDAO().edit(application);
            dAOFactory.createForwardAndRewindReportDAO().create(forwardAndRewindReport);
        
            List<Notification> notifications = new ArrayList<Notification>();

            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getFellow(), "Application rewinded", "Please note that the application '" + application.getProjectTitle() + "' has been rewinded to " + toStatus + " for which you are the fellow of. The reason for this is as follows: " + reason));
            notifications.add(dBEntitiesFactory.createNotificationEntity(null, application.getGrantHolder(), "Application rewinded", "Please note that the application '" + application.getProjectTitle() + "' has been rewinded to " + toStatus + " for which you are the grant holder of. The reason for this is as follows: " + reason));

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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.auxillary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Application> loadMovableApplications(Session session) throws Exception
    {
        
        EntityManager em = createEntityManager();

        try
        {
            List<Application> applications = new ArrayList<Application>();
            ApplicationJpaController applicationJpaController = getDAOFactory(em).createApplicationDAO();

            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.auxillary.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED, 0, Integer.MAX_VALUE));

            return applications;
        }
        finally
        {
            em.close();
        }
        
    }
    
}
