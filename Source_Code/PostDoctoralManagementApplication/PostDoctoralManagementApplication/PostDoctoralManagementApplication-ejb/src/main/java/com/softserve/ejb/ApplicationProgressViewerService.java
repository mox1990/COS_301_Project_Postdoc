/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.ApplicationStageStatus;
import com.softserve.system.Session;
import java.util.ArrayList;
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
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected List<ApplicationStageStatus> getApplicationStageStatus()
    {
        return new ArrayList<ApplicationStageStatus>();
    }
    
    private int getOrderIndexOfApplicationStatus(String status)
    {
        if(status.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN))
        {
            return 0;
        }
        else if(status.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            return 1;
        }
        else if(status.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            return 2;
        }
        else if(status.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            return 3;
        }
        else if(status.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            return 4;
        }
        else if(status.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
        {
            return 5;
        }
        else if(status.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE))
        {
            return 6;
        }
        else if(status.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED))
        {
            return 7;
        }
        else if(status.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED))
        {
            return 8;
        }
        else if(status.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED))
        {
            return 9;
        }
        else if(status.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED))
        {
            return 9;
        }
        
        return 20;
    }
    
    private boolean hasApplicationAchivedThisStatus(Application application, String status)
    {
        return getOrderIndexOfApplicationStatus(application.getStatus()) >= getOrderIndexOfApplicationStatus(status);
    }
    
    
    @Override
    public List<Application> getAllApplications(Session session) throws AuthenticationException, Exception {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        return getApplicationDAO().findApplicationEntities();
    }
    
    
    
    @Override
    public List<ApplicationStageStatus> getApplicationProgress(Session session, Application application) throws AuthenticationException, Exception
    {
        UserGatewayLocal userGateway = getUserGatewayServiceEJB();
        try
        {
            //Authenticate user ownership of account
            userGateway.authenticateUserAsOwner(session, application.getFellow());
        } 
        catch(AuthenticationException ex)
        {
            //Authenticate user privliges
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            userGateway.authenticateUser(session, roles);
        }
                
        List<ApplicationStageStatus> stageStatuses = getApplicationStageStatus();
        
        //Opening information
        stageStatuses.add(new ApplicationStageStatus(application.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, application.getFellow()));
        
        if(hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {
            stageStatuses.add(new ApplicationStageStatus(application.getSubmissionDate(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED, application.getFellow()));
            
            //Add referee information
            for(RefereeReport rr : application.getRefereeReportList())
            {
                stageStatuses.add(new ApplicationStageStatus(rr.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED, rr.getReferee()));
            }
        }
        
        if(hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            if(application.getRefereeReportList().size() == application.getPersonList().size())
            {
                stageStatuses.get(stageStatuses.size() - 1).setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
            }
        }
        
        if(hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            //Still need to sort out date issue
            if(application.getFinalisationDate() != null)
            {
                stageStatuses.add(new ApplicationStageStatus(application.getFinalisationDate(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED, application.getGrantHolder()));
            }
        }
        
        if(hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            //HOD recommendation information
            if(application.getRecommendationReport()!= null)
            {
                stageStatuses.add(new ApplicationStageStatus(application.getRecommendationReport().getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, application.getRecommendationReport().getHod()));
            }
        }
        
        if(hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED))
        {
            //Deans endorsement information
            if(application.getEndorsement()!= null)
            {
                stageStatuses.add(new ApplicationStageStatus(application.getEndorsement().getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED, application.getEndorsement().getDean()));
            }
        }
        
        if(hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE))
        {
            //Eligiblity information information
            if(application.getEligiblityReport() != null)
            {
                stageStatuses.add(new ApplicationStageStatus(application.getEligiblityReport().getEligiblityCheckDate(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE, application.getEligiblityReport().getEligiblityChecker()));
            }
        }
        
        if(hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED))
        {
            //Eligiblity information information
            if(application.getDeclineReport()!= null)
            {
                stageStatuses.add(new ApplicationStageStatus(application.getDeclineReport().getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED, application.getDeclineReport().getCreator()));
            }
        }
        
        if(hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED))
        {
            //Funding information information
            if(application.getFundingReport()!= null)
            {
                stageStatuses.add(new ApplicationStageStatus(application.getFundingReport().getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED, application.getFundingReport().getDris()));
            }
        }
        
        if(hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED))
        {
            stageStatuses.add(new ApplicationStageStatus(application.getEndDate(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_COMPLETED, application.getFundingReport().getDris()));

        }
        
        if(hasApplicationAchivedThisStatus(application, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED))
        {
            stageStatuses.add(new ApplicationStageStatus(application.getEndDate(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_TERMINATED, application.getFundingReport().getDris()));

        }
        
        return stageStatuses;
    }
}
