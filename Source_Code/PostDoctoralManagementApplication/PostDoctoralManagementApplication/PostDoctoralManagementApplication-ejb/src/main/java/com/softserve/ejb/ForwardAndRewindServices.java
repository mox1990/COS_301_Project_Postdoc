/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.AmmendRequestJpaController;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.EligiblityReportJpaController;
import com.softserve.DBDAO.EndorsementJpaController;
import com.softserve.DBDAO.ForwardAndRewindReportJpaController;
import com.softserve.DBDAO.FundingCostJpaController;
import com.softserve.DBDAO.FundingReportJpaController;
import com.softserve.DBDAO.RecommendationReportJpaController;
import com.softserve.DBDAO.RefereeReportJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.ForwardAndRewindReport;
import com.softserve.DBEntities.FundingCost;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class ForwardAndRewindServices implements ForwardAndRewindServicesLocal {

    @PersistenceUnit(unitName=com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
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
    
    @Override
    public void forwardApplication(Session session, Application application, String toStatus, String reason) throws Exception 
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        ForwardAndRewindReport forwardAndRewindReport = dBEntitiesFactory.createForwardAndRewindReport(application, session.getUser(), getGregorianCalendar().getTime(), reason, com.softserve.constants.PersistenceConstants.FORWARDREWINREPORT_TYPE_FORWARD, toStatus, application.getStatus());
        
        if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            forwardApplicationToSubmittedStatus(application);
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
            throw new Exception("The status specified to forward to does not exist");
        }
        
        getApplicationDAO().edit(application);
        getForwardAndRewindReportDAO().create(forwardAndRewindReport);
        
        AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Application forwarded " + application.getApplicationID(), session.getUser());
        auditTrailServiceLocal.logAction(auditLog);
    }

    @Override
    public void rewindApplication(Session session, Application application, String toStatus, String reason) throws Exception {
        
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        ForwardAndRewindReport forwardAndRewindReport = dBEntitiesFactory.createForwardAndRewindReport(application, session.getUser(), getGregorianCalendar().getTime(), reason, com.softserve.constants.PersistenceConstants.FORWARDREWINREPORT_TYPE_REWIND, toStatus, application.getStatus());
        
        if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN))
        {
            rewindApplicationToOpenStatus(application);
        }
        else if(toStatus.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            rewindApplicationToSubmittedStatus(application);
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
        
        AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Application rewinded " + application.getApplicationID(), session.getUser());
        auditTrailServiceLocal.logAction(auditLog);
    }
    
}
