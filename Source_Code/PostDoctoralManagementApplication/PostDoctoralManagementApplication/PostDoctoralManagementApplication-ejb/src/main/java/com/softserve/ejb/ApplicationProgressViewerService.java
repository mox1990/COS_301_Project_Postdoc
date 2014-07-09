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
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;    
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected UserGateway getUserGatewayServiceEJB()
    {
        return new UserGateway(emf);
    }
    
    @Override
    public List<Application> getAllApplicationsWithFellow(Session session) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        return getApplicationDAO().findAllApplicationsWhosFellowIs(session.getUser());
    }
    
    @Override
    public List<Application> getAllApplicationsWithGrantHolder(Session session) throws AuthenticationException, Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        return getApplicationDAO().findAllApplicationsWhosGrantHolderIs(session.getUser());
    }
    
    @Override
    public List<ApplicationStageStatus> getApplicationProgress(Session session, Application application) throws AuthenticationException, Exception
    {
        UserGateway userGateway = getUserGatewayServiceEJB();
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
                
        List<ApplicationStageStatus> stageStatuses = new ArrayList<ApplicationStageStatus>();
        
        //Opening information
        stageStatuses.add(new ApplicationStageStatus(application.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, application.getFellow()));
        
        //Add referee information
        for(RefereeReport rr : application.getRefereeReportList())
        {
            stageStatuses.add(new ApplicationStageStatus(rr.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, rr.getRefereeID()));
        }
        stageStatuses.get(stageStatuses.size() - 1).setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED);
        
        //Still need to sort out date issue
        stageStatuses.add(new ApplicationStageStatus(application.getFinalisationDate(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED, application.getGrantHolderID()));
        
        //HOD recommendation information
        if(application.getRecommendationReportID() != null)
        {
            stageStatuses.add(new ApplicationStageStatus(application.getRecommendationReportID().getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED, application.getRecommendationReportID().getHodID()));
        }
        
        //Deans endorsement information
        if(application.getEndorsementID()!= null)
        {
            stageStatuses.add(new ApplicationStageStatus(application.getEndorsementID().getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED, application.getEndorsementID().getDeanID()));
        }
        
        //Eligiblity information information
        if(application.getEndorsementID()!= null)
        {
            stageStatuses.add(new ApplicationStageStatus(application.getEligiblityCheckDate(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE, null));
        }
        
        //Funding information information
        if(application.getFundingReportID()!= null)
        {
            stageStatuses.add(new ApplicationStageStatus(application.getFundingReportID().getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FUNDED, application.getFundingReportID().getDrisID()));
        }
        
        if(application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED))
        {
            stageStatuses.add(new ApplicationStageStatus(application.getFundingReportID().getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED, application.getFundingReportID().getDrisID()));
        }

        
        return stageStatuses;
    }
}
