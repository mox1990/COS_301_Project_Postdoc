/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.AddressJpaController;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Application;
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
    
    protected PersonJpaController getPersonDAO()
    {
        return new PersonJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    public List<ApplicationStageStatus> getApplicationProgress(Session session, Application application)
    {
        //AuthenticUser(session, list of privliges)
        
        //Prep the DAOs
        PersonJpaController personJpaController = getPersonDAO();
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        List<ApplicationStageStatus> stageStatuses = new ArrayList<ApplicationStageStatus>();
        
        stageStatuses.add(new ApplicationStageStatus(application.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, application.getFellow()));
        /*stageStatuses.add(new ApplicationStageStatus(application.setRefereeReportListgetTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, application.getFellow()));
        stageStatuses.add(new ApplicationStageStatus(application.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, application.getFellow()));
        stageStatuses.add(new ApplicationStageStatus(application.getTimestamp(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN, application.getFellow()));*/
        
        return stageStatuses;
    }
}
