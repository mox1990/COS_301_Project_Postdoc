/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Person;
import com.softserve.Exceptions.CVAlreadExistsException;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class GrantHolderFinalisationService implements GrantHolderFinalisationServiceLocal {
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    protected PersonJpaController getPersonDAO()
    {
        return new PersonJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected CvJpaController getCVDAO()
    {
        return new CvJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    private boolean hasCV(Session session)
    {
        return (session.getUser().getCvList().size() != 0);
    }
    
    /**
     *This function is used to create a CV for a grant holder
     * @param session The session which is used to authenticate the user
     * @param cv The CV object containing the cv data to be added
     * @throws NonexistentEntityException If the session user does not exist
     * @throws CVAlreadExistsException If the grant holder already has a CV
     * @throws Exception If an unknown error occurs
     */
    public void createGrantHolderCV(Session session, Cv cv) throws NonexistentEntityException, CVAlreadExistsException, Exception
    {
        //AuthenticUser(session, list of privliges)
        
        if(!hasCV(session))
        {
            CvJpaController cvJpaController = getCVDAO();
            PersonJpaController personJpaController = getPersonDAO();

            cvJpaController.create(cv);

            Person user = session.getUser();
            List<Cv> cvList = user.getCvList();        
            cvList.add(cv);
            user.setCvList(cvList);

            personJpaController.edit(user);  
        }
        else
        {
            throw new CVAlreadExistsException("The grant holder already has a CV");
        }
    }
    
    /**
     *This function loads all the applications that need to finalised by the 
     * specified grant holder
     * @param session The session object used to authenticate the user
     * @return A list of all the applications that user can finalise
     */
    public List<Application> loadPendingApplications(Session session)
    {
        //AuthenticUser(session, list of privliges)
        
        ApplicationServices applicationServices = new ApplicationServices();
        
        return applicationServices.loadPendingApplications(session.getUser(), com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED);
    }
    
    /**
     *This function is used to finalise an applications content and change its 
     * status to finalised.
     * @param session The session object used to authenticate the user
     * @param application The application that needs to be finalised
     * @throws NonexistentEntityException If the application does not exist
     * @throws RollbackFailureException If an error occured while rolling back the entry in the database
     * @throws Exception If an unknown error occured
     */
    public void finaliseApplication(Session session, Application application) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        //AuthenticUser(session, list of privliges)
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED);
        
        applicationJpaController.edit(application);
        
        //Log action
        
        //Send notification to appropriate HOD        
    }
        
}
