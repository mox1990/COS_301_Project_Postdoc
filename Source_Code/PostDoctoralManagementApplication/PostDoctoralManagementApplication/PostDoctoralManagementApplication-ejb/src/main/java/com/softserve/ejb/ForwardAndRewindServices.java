/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.AmmendRequestJpaController;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.ApplicationReviewRequestJpaController;
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
    
    protected ApplicationServicesUtil getApplicationServicesUTIL()
    {
        return new ApplicationServicesUtil(emf);
        
    }
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected RefereeReportJpaController getRefereeReportDAO()
    {
        return new RefereeReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected RecommendationReportJpaController getRecommmendationReportDAO()
    {
        return new RecommendationReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected AmmendRequestJpaController getAmmendRequestDAO()
    {
        return new AmmendRequestJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected EndorsementJpaController getEndorsementDAO()
    {
        return new EndorsementJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected FundingReportJpaController getFundingReportDAO()
    {
        return new FundingReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected FundingCostJpaController getFundingCostDAO()
    {
        return new FundingCostJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected EligiblityReportJpaController getEligiblityReportDAO()
    {
        return new EligiblityReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ForwardAndRewindReportJpaController getForwardAndRewindReportDAO()
    {
        return new ForwardAndRewindReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ApplicationReviewRequestJpaController getApplicationReviewRequestDAO()
    {
        return new ApplicationReviewRequestJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    
    protected void rewindApplicationToOpenStatus(Application application) throws Exception
    {
        if(getApplicationServicesUTIL().hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            rewindApplicationToSubmittedStatus(application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN);
            
            application.setSubmissionDate(null);
        }
    }
    
    protected void rewindApplicationToSubmittedStatus(Application application) throws Exception
    {
        if(getApplicationServicesUTIL().hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            rewindApplicationToReferredStatus(application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
            
            RefereeReportJpaController refereeReportJpaController = getRefereeReportDAO();
            
            for(RefereeReport refereeReport : application.getRefereeReportList())
            {
                refereeReportJpaController.destroy(refereeReport.getReportID());
            }
            
            application.setRefereeReportList(new ArrayList<RefereeReport>());
                        
        }
    }
    
    protected void rewindApplicationToReferredStatus(Application application) throws Exception
    {
        if(getApplicationServicesUTIL().hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            rewindApplicationToFinalisedStatus(application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
            
            application.setFinalisationDate(null);
            
            if(application.getApplicationReviewRequestList() != null)
            {
                ApplicationReviewRequestJpaController applicationReviewRequestJpaController = getApplicationReviewRequestDAO();
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
    
    protected void rewindApplicationToFinalisedStatus(Application application) throws Exception
    {
        if(getApplicationServicesUTIL().hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            rewindApplicationToRecommendedStatus(application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
            
            if(application.getRecommendationReport() != null)
            {
                getRecommmendationReportDAO().destroy(application.getRecommendationReport().getReportID());
                application.setRecommendationReport(null);
            }
            
            if(application.getApplicationReviewRequestList() != null)
            {
                ApplicationReviewRequestJpaController applicationReviewRequestJpaController = getApplicationReviewRequestDAO();
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
    
    protected void rewindApplicationToRecommendedStatus(Application application) throws Exception
    {
        if(getApplicationServicesUTIL().hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
        {
            rewindApplicationToEndorsedStatus(application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
            
            if(application.getEndorsement() != null)
            {
                getEndorsementDAO().destroy(application.getEndorsement().getEndorsementID());
                application.setEndorsement(null);
            }
        }
    }
    
    protected void rewindApplicationToEndorsedStatus(Application application) throws Exception
    {
        if(getApplicationServicesUTIL().hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE))
        {
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
            if(application.getFundingReport() != null)
            {
                FundingCostJpaController fundingCostJpaController = getFundingCostDAO();
                for(FundingCost fundingCost : application.getFundingReport().getFundingCostList())
                {
                    fundingCostJpaController.destroy(fundingCost.getCostID());
                }
                
                getFundingReportDAO().destroy(application.getFundingReport().getReportID());
                application.setFundingReport(null);
            }
            if(application.getEligiblityReport() != null)
            {
                getEligiblityReportDAO().destroy(application.getEligiblityReport().getReportID());
                application.setEligiblityReport(null);                
            }           
            
        }
    }    
    
        
    protected void forwardApplicationToSubmittedStatus(Application application) throws Exception
    {
        if(!getApplicationServicesUTIL().hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
            application.setSubmissionDate(getGregorianCalendar().getTime());
        }
    }
    
    protected void forwardApplicationToReferredStatus(Application application) throws Exception
    {
        if(!getApplicationServicesUTIL().hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            forwardApplicationToSubmittedStatus(application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
        }
    }
    
    protected void forwardApplicationToFinalisedStatus(Application application) throws Exception
    {
        if(!getApplicationServicesUTIL().hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            forwardApplicationToReferredStatus(application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
            application.setFinalisationDate(getGregorianCalendar().getTime());
        }
    }
    
    protected void forwardApplicationToRecommendedStatus(Application application) throws Exception
    {
        if(!getApplicationServicesUTIL().hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            forwardApplicationToFinalisedStatus(application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
        }
    }
    
    protected void forwardApplicationToEndorsedStatus(Application application) throws Exception
    {
        if(!getApplicationServicesUTIL().hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
        {
            forwardApplicationToRecommendedStatus(application);
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED);
        }
    }
        
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod(message = "Application forwared")
    @Override
    public void forwardApplication(Session session, Application application, String toStatus, String reason) throws Exception 
    {        
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        ForwardAndRewindReport forwardAndRewindReport = dBEntitiesFactory.createForwardAndRewindReport(application, session.getUser(), getGregorianCalendar().getTime(), reason, com.softserve.constants.PersistenceConstants.FORWARDREWINREPORT_TYPE_FORWARD, toStatus, application.getStatus());
        
        if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            forwardApplicationToSubmittedStatus(application);
            if(application.getPersonList().isEmpty())
            {
                forwardApplicationToReferredStatus(application);
            }
        }
        else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            forwardApplicationToReferredStatus(application);
        }
        else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            forwardApplicationToFinalisedStatus(application);
        }
        else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            forwardApplicationToRecommendedStatus(application);
        }
        else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
        {
            forwardApplicationToEndorsedStatus(application);
        }
        else
        {
            throw new Exception("The status " + toStatus + " specified to forward to does not exist");
        }
        
        getApplicationDAO().edit(application);
        getForwardAndRewindReportDAO().create(forwardAndRewindReport);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod(message = "Application rewinded")
    @Override
    public void rewindApplication(Session session, Application application, String toStatus, String reason) throws Exception 
    {   
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        ForwardAndRewindReport forwardAndRewindReport = dBEntitiesFactory.createForwardAndRewindReport(application, session.getUser(), getGregorianCalendar().getTime(), reason, com.softserve.constants.PersistenceConstants.FORWARDREWINREPORT_TYPE_REWIND, toStatus, application.getStatus());
        
        if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN))
        {
            rewindApplicationToOpenStatus(application);
        }
        else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            rewindApplicationToSubmittedStatus(application);
            if(application.getPersonList().isEmpty())
            {
                forwardApplicationToReferredStatus(application);
            }
        }
        else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            rewindApplicationToReferredStatus(application);
        }
        else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            rewindApplicationToFinalisedStatus(application);
        }
        else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            rewindApplicationToRecommendedStatus(application);
        }
        else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
        {
            rewindApplicationToEndorsedStatus(application);
        }
        else
        {
            throw new Exception("The status specified to forward to does not exist");
        }
        
        getApplicationDAO().edit(application);
        getForwardAndRewindReportDAO().create(forwardAndRewindReport);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Application> loadMovableApplications(Session session) throws Exception
    {
        List<Application> applications = new ArrayList<Application>();
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
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
    
}
