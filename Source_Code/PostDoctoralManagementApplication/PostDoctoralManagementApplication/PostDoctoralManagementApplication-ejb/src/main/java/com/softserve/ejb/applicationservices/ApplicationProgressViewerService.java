/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.ejb.nonapplicationservices.UserGatewayLocal;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.auxillary.ApplicationStageStatus;
import com.softserve.system.Session;
import java.util.ArrayList;
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
public class ApplicationProgressViewerService implements ApplicationProgressViewerServiceLocal {

    /**
     * This injection provides a container-managed entitymanagerfactory. This
     * is used to give the DAOs the ability to use application managed 
     * entity managers in JTA context so that manual transaction demarcation.
     */
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private UserGatewayLocal userGatewayLocal;
    
    
    protected UserGatewayLocal getUserGatewayServiceEJB()
    {
        return userGatewayLocal;
    }

    public ApplicationProgressViewerService() {
    }

    public ApplicationProgressViewerService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }
    
    protected List<ApplicationStageStatus> getApplicationStageStatus()
    {
        return new ArrayList<ApplicationStageStatus>();
    }
    
    protected ApplicationServicesUtil getApplicationServicesUTIL(EntityManager em)
    {
        return new ApplicationServicesUtil(em);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public List<Application> getAllApplications(Session session) throws Exception 
    {   
        EntityManager em = emf.createEntityManager();
        DAOFactory dAOFactory = getDAOFactory(em);
        try
        {
            List<Application> applications = dAOFactory.createApplicationDAO().findApplicationEntities();
            return applications;
        }
        finally
        {
            em.close();
        }       
    }
    
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER}, ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public List<ApplicationStageStatus> getApplicationProgress(Session session, Application application) throws AuthenticationException, Exception
    { 
        
        
        List<ApplicationStageStatus> stageStatuses = getApplicationStageStatus();
        
        //Opening information
        stageStatuses.add(new ApplicationStageStatus(application.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, application.getFellow()));
        ApplicationServicesUtil applicationServicesUtil = getApplicationServicesUTIL(null);
        if(applicationServicesUtil.hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            stageStatuses.add(new ApplicationStageStatus(application.getSubmissionDate(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED, application.getFellow()));
            
            //Add referee information
            for(RefereeReport rr : application.getRefereeReportList())
            {
                stageStatuses.add(new ApplicationStageStatus(rr.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED, rr.getReferee()));
            }
        }
        
        if(applicationServicesUtil.hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            if(application.getRefereeReportList().size() == application.getPersonList().size())
            {
                stageStatuses.get(stageStatuses.size() - 1).setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
            }
        }
        
        if(applicationServicesUtil.hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            //Still need to sort out date issue
            if(application.getFinalisationDate() != null)
            {
                stageStatuses.add(new ApplicationStageStatus(application.getFinalisationDate(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED, application.getGrantHolder()));
            }
        }
        
        if(applicationServicesUtil.hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            //HOD recommendation information
            if(application.getRecommendationReport()!= null)
            {
                stageStatuses.add(new ApplicationStageStatus(application.getRecommendationReport().getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, application.getRecommendationReport().getHod()));
            }
        }
        
        if(applicationServicesUtil.hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
        {
            //Deans endorsement information
            if(application.getEndorsement()!= null)
            {
                stageStatuses.add(new ApplicationStageStatus(application.getEndorsement().getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED, application.getEndorsement().getDean()));
            }
        }
        
        if(applicationServicesUtil.hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE))
        {
            //Eligiblity information information
            if(application.getEligiblityReport() != null)
            {
                stageStatuses.add(new ApplicationStageStatus(application.getEligiblityReport().getEligiblityCheckDate(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE, application.getEligiblityReport().getEligiblityChecker()));
            }
        }
        
        if(applicationServicesUtil.hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED))
        {
            //Eligiblity information information
            if(application.getDeclineReport()!= null)
            {
                stageStatuses.add(new ApplicationStageStatus(application.getDeclineReport().getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED, application.getDeclineReport().getCreator()));
            }
        }
        
        if(applicationServicesUtil.hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED))
        {
            //Funding information information
            if(application.getFundingReport()!= null)
            {
                stageStatuses.add(new ApplicationStageStatus(application.getFundingReport().getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED, application.getFundingReport().getDris()));
            }
        }
        
        if(applicationServicesUtil.hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED))
        {
            stageStatuses.add(new ApplicationStageStatus(application.getEndDate(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED, application.getFundingReport().getDris()));

        }
        
        if(applicationServicesUtil.hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED))
        {
            stageStatuses.add(new ApplicationStageStatus(application.getEndDate(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED, application.getFundingReport().getDris()));

        }
        
        return stageStatuses;
    }
}
