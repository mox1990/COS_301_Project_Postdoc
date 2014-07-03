/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ApplicationServices;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ApplicationServices {
    
    private EntityManagerFactory emf;

    public ApplicationServices(EntityManagerFactory emf) 
    {
        this.emf = emf;
    }
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    public List<Application> loadPendingApplications(Person user, String applicationStatusGroup)
    {
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        List<Application> output = applicationJpaController.findAllApplicationsWithStatus(applicationStatusGroup);
        
        
        if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_OPEN))
        {   
            //Possible optimization. Can use user.getApplicationListX() to get list
            for(int i = 0; i < output.size(); i++)
            {
                if(!output.get(i).getRefereeList().contains(user))
                {
                    output.remove(i);
                    i--;
                }
            }
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED))
        {
            for(int i = 0; i < output.size(); i++)
            {
                if(output.get(i).getGrantHolderID() != user)
                {
                    output.remove(i);
                    i--;
                }
            }
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            String userDepartment = user.getLocationID().getDepartment();
            
            for(int i = 0; i < output.size(); i++)
            {
                if(output.get(i).getGrantHolderID().getLocationID().getDepartment().equals(userDepartment))
                {
                    output.remove(i);
                    i--;
                }
            }
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            String userFaculty = user.getLocationID().getFaculty();
            
            for(int i = 0; i < output.size(); i++)
            {
                if(output.get(i).getGrantHolderID().getLocationID().getFaculty().equals(userFaculty))
                {
                    output.remove(i);
                    i--;
                }
            }
        }
        
        return output;
    }
    
    
    
}
