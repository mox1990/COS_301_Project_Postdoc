/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.system;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.DeclineReportJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.DeclineReport;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ApplicationServicesUtil {
    
    private EntityManagerFactory emf;

    public ApplicationServicesUtil(EntityManagerFactory emf) 
    {
        this.emf = emf;
    }
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *
     * @return
     */
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    /**
     *
     * @return
     */
    protected UserGateway getUserGatewayServiceEJB()
    {
        return new UserGateway(emf);
    }
    
    /**
     *
     * @return
     */
    protected NotificationService getNotificationServiceEJB()
    {
        return new NotificationService(emf);
    }
    
    /**
     *
     * @return
     */
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return new AuditTrailService(emf);
    }
    
    protected DeclineReportJpaController getDeclineReportDAO()
    {
        return new DeclineReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    public List<Application> loadPendingApplications(Person user, String applicationStatusGroup, int StartIndex, int maxNumberOfRecords)
    {
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        
        List<Application> output = new ArrayList<Application>();        
        
        if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {   
            output = applicationJpaController.findAllApplicationsWithStatusAndReferee(applicationStatusGroup,user,StartIndex,maxNumberOfRecords);            
            System.out.println("===============" + output.toString());
            System.out.println("===============" + user.toString());
            List<Application> temp = new ArrayList<Application>();
//Possible optimization. Can use user.getApplicationListX() to get list
            for(int i = 0; i < output.size(); i++)
            {
                boolean found = false;
                
                for(int j = 0; j < output.get(i).getRefereeReportList().size(); j++)
                {
                    System.out.println("===============" + output.get(i).getRefereeReportList().get(j).getReferee());
                    if(output.get(i).getRefereeReportList().get(j).getReferee().equals(user))
                    {
                        found = true;
                    }                               
                }
                
                if(!found)
                {
                    temp.add(output.get(i));
                }
            }
            
            output = temp;
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
            String userDepartment = user.getEmployeeInformation().getLocation().getDepartment();
            
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
            String userFaculty = user.getEmployeeInformation().getLocation().getFaculty();
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
        
        long output = 0;
        
        
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
            String userDepartment = user.getEmployeeInformation().getLocation().getDepartment();            
            output = applicationJpaController.countAllApplicationsWithStatusAndDepartment(applicationStatusGroup,userDepartment);
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            String userFaculty = user.getEmployeeInformation().getLocation().getFaculty();
            output = applicationJpaController.countAllApplicationsWithStatusAndFaculty(applicationStatusGroup,userFaculty);
        }
        else
        {
            output = applicationJpaController.countAllApplicationsWithStatus(applicationStatusGroup);
        }
        
        return (int) output;
    }
    
    public void declineAppliction(Session session, Application application, String reason) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {
        
        ApplicationJpaController applicationJpaController = getApplicationDAO();
        DeclineReportJpaController declineReportJpaController = getDeclineReportDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailService auditTrailService = getAuditTrailServiceEJB();
        NotificationService notificationService = getNotificationServiceEJB();
        
        if(!application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED))
        {
            //Deny application
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED);        
            applicationJpaController.edit(application);

            DeclineReport declineReport = dBEntitiesFactory.bulidDeclineReportEntity(application, reason, getGregorianCalendar().getTime());
            declineReportJpaController.create(declineReport);

            //Log action  
            AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("Declined application " + application.getApplicationID(), session.getUser());
            auditTrailService.logAction(auditLog);

            //Send notification to grant holder and applicatantD
            Notification notification = dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getFellow(), "Application declined", "The following application has been declined by " + session.getUser().getCompleteName() + ". For the following reasons: " + reason);
            notificationService.sendNotification(notification, true);

            notification = dBEntitiesFactory.buildNotificationEntity(session.getUser(), application.getGrantHolder(), "Application declined", "The following application has been declined by " + session.getUser().getCompleteName() + ". For the following reasons: " + reason);
            notificationService.sendNotification(notification, true);
        }
        else
        {
            throw new Exception("Application has already been declined");
        }
               
    }
    
}