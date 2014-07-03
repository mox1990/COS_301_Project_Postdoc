/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.EndorsementJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Endorsement;
import com.softserve.system.Session;
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
public class DeansEndorsementService implements DeansEndorsementServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected EndorsementJpaController getEndorsementDAO()
    {
        return new EndorsementJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    public List<Application> loadPendingApplications(Session session)
    {
        //AuthenticUser(session, list of privliges)
        
        ApplicationServices applicationServices = new ApplicationServices(emf);
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED);
    }
    
    public void denyApplication(Session session, Application application, String reason) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        //AuthenticUser(session, list of privliges)
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED);
        
        applicationJpaController.edit(application);
        
        //Log action
        
        //Send notification to grant holder and applicatant
    }
    
    public void endorseApplication(Session session, Application application, Endorsement endorsementReport) throws RollbackFailureException, NonexistentEntityException, Exception
    {
        //AuthenticUser(session, list of privliges)
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        EndorsementJpaController endorsementJpaController = getEndorsementDAO();
        
        endorsementJpaController.create(endorsementReport);
        
        application.setEndorsementID(endorsementReport);        
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
        
        //Log action
        
        //Send notification to DRIS member(s)
    }
}
