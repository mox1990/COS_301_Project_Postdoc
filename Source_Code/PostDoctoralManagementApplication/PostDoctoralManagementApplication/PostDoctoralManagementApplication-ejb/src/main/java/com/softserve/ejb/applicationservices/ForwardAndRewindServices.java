/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.DBDAO.AmmendRequestJpaController;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBDAO.EligiblityReportJpaController;
import com.softserve.DBDAO.EndorsementJpaController;
import com.softserve.DBDAO.ForwardAndRewindReportJpaController;
import com.softserve.DBDAO.FundingCostJpaController;
import com.softserve.DBDAO.FundingReportJpaController;
import com.softserve.DBDAO.RecommendationReportJpaController;
import com.softserve.DBDAO.RefereeReportJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.ApplicationReviewRequest;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.ForwardAndRewindReport;
import com.softserve.DBEntities.FundingCost;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.DBEntities.SecurityRole;
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
public class ForwardAndRewindServices implements ForwardAndRewindServicesLocal {

    @PersistenceUnit(unitName=com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
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
    
    
    protected void rewindApplicationToOpenStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            rewindApplicationToSubmittedStatus(transactionController,application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN);
            
            application.setSubmissionDate(null);
        }
    }
    
    protected void rewindApplicationToSubmittedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            rewindApplicationToReferredStatus(transactionController,application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
            
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
        if(getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            rewindApplicationToFinalisedStatus(transactionController,application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
            
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            application.setFinalisationDate(null);
            
            if(application.getApplicationReviewRequestList() != null)
            {
                ApplicationReviewRequestJpaController applicationReviewRequestJpaController = dAOFactory.createApplicationReviewRequestDAO();
                for(ApplicationReviewRequest applicationReviewRequest : application.getApplicationReviewRequestList())
                {
                    if(applicationReviewRequest.getApplicationReviewRequestPK().getType().equals(com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD))
                    {
                        applicationReviewRequestJpaController.destroy(applicationReviewRequest.getApplicationReviewRequestPK());
                    }
                }
            }
        }
    }
    
    protected void rewindApplicationToFinalisedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            rewindApplicationToRecommendedStatus(transactionController,application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
            
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
                    if(applicationReviewRequest.getApplicationReviewRequestPK().getType().equals(com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_DEAN))
                    {
                        applicationReviewRequestJpaController.destroy(applicationReviewRequest.getApplicationReviewRequestPK());
                    }
                }
            }
        }
    }
    
    protected void rewindApplicationToRecommendedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
        {
            rewindApplicationToEndorsedStatus(transactionController,application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
            
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
        if(getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE))
        {
            DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
            
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
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
        if(!getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
            application.setSubmissionDate(getGregorianCalendar().getTime());
        }
    }
    
    protected void forwardApplicationToReferredStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(!getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            forwardApplicationToSubmittedStatus(transactionController,application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
        }
    }
    
    protected void forwardApplicationToFinalisedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(!getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            forwardApplicationToReferredStatus(transactionController,application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
            application.setFinalisationDate(getGregorianCalendar().getTime());
        }
    }
    
    protected void forwardApplicationToRecommendedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(!getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            forwardApplicationToFinalisedStatus(transactionController,application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
        }
    }
    
    protected void forwardApplicationToEndorsedStatus(TransactionController transactionController, Application application) throws Exception
    {
        if(!getApplicationServicesUTIL(transactionController.getEntityManager()).hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
        {
            forwardApplicationToRecommendedStatus(transactionController,application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
        }
    }
        
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
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
            ForwardAndRewindReport forwardAndRewindReport = dBEntitiesFactory.createForwardAndRewindReport(application, session.getUser(), getGregorianCalendar().getTime(), reason, com.softserve.constants.PersistenceConstants.FORWARDREWINREPORT_TYPE_FORWARD, toStatus, application.getStatus());

            if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
            {
                forwardApplicationToSubmittedStatus(transactionController,application);
                if(application.getPersonList().isEmpty())
                {
                    forwardApplicationToReferredStatus(transactionController,application);
                }
            }
            else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
            {
                forwardApplicationToReferredStatus(transactionController,application);
            }
            else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
            {
                forwardApplicationToFinalisedStatus(transactionController,application);
            }
            else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
            {
                forwardApplicationToRecommendedStatus(transactionController,application);
            }
            else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
            {
                forwardApplicationToEndorsedStatus(transactionController,application);
            }
            else
            {
                throw new Exception("The status " + toStatus + " specified to forward to does not exist");
            }

            dAOFactory.createApplicationDAO().edit(application);
            dAOFactory.createForwardAndRewindReportDAO().create(forwardAndRewindReport);

            transactionController.CommitTransaction();
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
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
            ForwardAndRewindReport forwardAndRewindReport = dBEntitiesFactory.createForwardAndRewindReport(application, session.getUser(), getGregorianCalendar().getTime(), reason, com.softserve.constants.PersistenceConstants.FORWARDREWINREPORT_TYPE_REWIND, toStatus, application.getStatus());
        
            if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN))
            {
                rewindApplicationToOpenStatus(transactionController,application);
            }
            else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
            {
                rewindApplicationToSubmittedStatus(transactionController,application);
                if(application.getPersonList().isEmpty())
                {
                    forwardApplicationToReferredStatus(transactionController,application);
                }
            }
            else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
            {
                rewindApplicationToReferredStatus(transactionController,application);
            }
            else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
            {
                rewindApplicationToFinalisedStatus(transactionController,application);
            }
            else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
            {
                rewindApplicationToRecommendedStatus(transactionController,application);
            }
            else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
            {
                rewindApplicationToEndorsedStatus(transactionController,application);
            }
            else
            {
                throw new Exception("The status specified to forward to does not exist");
            }

            dAOFactory.createApplicationDAO().edit(application);
            dAOFactory.createForwardAndRewindReportDAO().create(forwardAndRewindReport);

            transactionController.CommitTransaction();
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Application> loadMovableApplications(Session session) throws Exception
    {
        
        EntityManager em = emf.createEntityManager();

        try
        {
            List<Application> applications = new ArrayList<Application>();
            ApplicationJpaController applicationJpaController = getDAOFactory(em).createApplicationDAO();

            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE, 0, Integer.MAX_VALUE));
            applications.addAll(applicationJpaController.findAllApplicationsWithStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED, 0, Integer.MAX_VALUE));

            return applications;
        }
        finally
        {
            em.close();
        }
        
    }
    
}
