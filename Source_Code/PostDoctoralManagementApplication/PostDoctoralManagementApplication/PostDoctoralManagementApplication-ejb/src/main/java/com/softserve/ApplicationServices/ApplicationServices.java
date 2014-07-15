/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ApplicationServices;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import java.util.ArrayList;
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
    
    public List<Application> loadPendingApplications(Person user, String applicationStatusGroup, int StartIndex, int maxNumberOfRecords)
    {
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        List<Application> output = new ArrayList<Application>();
        
        
        if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {   
            output = applicationJpaController.findAllApplicationsWithStatusAndReferee(applicationStatusGroup,user,StartIndex,maxNumberOfRecords);

//Possible optimization. Can use user.getApplicationListX() to get list
            /*for(int i = 0; i < output.size(); i++)
            {
                if(!output.get(i).getRefereeList().contains(user))
                {
                    output.remove(i);
                    i--;
                }
            }*/
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED))
        {
            output = applicationJpaController.findAllApplicationsWithStatusAndGrantHolder(applicationStatusGroup,user,StartIndex,maxNumberOfRecords);
            /*for(int i = 0; i < output.size(); i++)
            {
                if(output.get(i).getGrantHolderID() != user)
                {
                    output.remove(i);
                    i--;
                }
            }*/
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            String userDepartment = user.getLocationID().getDepartment();
            
            output = applicationJpaController.findAllApplicationsWithStatusAndDepartment(applicationStatusGroup,userDepartment,StartIndex,maxNumberOfRecords);
            
            /*for(int i = 0; i < output.size(); i++)
            {
                if(output.get(i).getGrantHolderID().getLocationID().getDepartment().equals(userDepartment))
                {
                    output.remove(i);
                    i--;
                }
            }*/
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            String userFaculty = user.getLocationID().getFaculty();
            output = applicationJpaController.findAllApplicationsWithStatusAndFaculty(applicationStatusGroup,userFaculty,StartIndex,maxNumberOfRecords);
            /*for(int i = 0; i < output.size(); i++)
            {
                if(output.get(i).getGrantHolderID().getLocationID().getFaculty().equals(userFaculty))
                {
                    output.remove(i);
                    i--;
                }
            }*/
        }
        else
        {
            output = applicationJpaController.findAllApplicationsWithStatus(applicationStatusGroup,StartIndex,maxNumberOfRecords);
        }
        
        return output;
    }
    
    public int getTotalNumberOfPendingApplications(Person user, String applicationStatusGroup)
    {
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        int output = 0;
        
        
        if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {   
            output = applicationJpaController.countAllApplicationsWithStatusAndReferee(applicationStatusGroup,user);

        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFEREED))
        {
            output = applicationJpaController.countAllApplicationsWithStatusAndGrantHolder(applicationStatusGroup,user);
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            String userDepartment = user.getLocationID().getDepartment();            
            output = applicationJpaController.countAllApplicationsWithStatusAndDepartment(applicationStatusGroup,userDepartment);
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            String userFaculty = user.getLocationID().getFaculty();
            output = applicationJpaController.countAllApplicationsWithStatusAndFaculty(applicationStatusGroup,userFaculty);
        }
        else
        {
            output = applicationJpaController.countAllApplicationsWithStatus(applicationStatusGroup);
        }
        
        return output;
    }
    
    
    
}
