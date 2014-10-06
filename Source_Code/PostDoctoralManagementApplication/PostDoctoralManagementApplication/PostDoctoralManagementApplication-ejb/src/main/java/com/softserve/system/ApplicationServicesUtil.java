/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.system;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBDAO.DeclineReportJpaController;
import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.ApplicationReviewRequest;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.DeclineReport;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ApplicationServicesUtil {
    
    private EntityManager em;

    public ApplicationServicesUtil(EntityManager em) 
    {
        this.em = em;
    }
    
    protected DAOFactory getDAOFactory()
    {
        return new DAOFactory(em);
    }
    
    /**
     *
     * @return
     */
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }

    
    protected GregorianCalendar getGregorianCalendar()
    {
        return new GregorianCalendar();
    }
    
    public List<Application> loadPendingApplications(Person user, String applicationStatusGroup, int StartIndex, int maxNumberOfRecords)
    {
        DAOFactory dAOFactory = getDAOFactory();
        ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
        
        List<Application> output;        
        
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
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            output = applicationJpaController.findAllApplicationsWithStatusAndGrantHolder(applicationStatusGroup,user,StartIndex,maxNumberOfRecords);
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            Department userDepartment = user.getEmployeeInformation().getDepartment();
            List<Application> tempOutput = new ArrayList<Application>(); 
            output = dAOFactory.createApplicationReviewRequestDAO().findAllApplicationsRequestForPersonAs(user, com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD);
            for(Application application : output)
            {
                if(application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
                {
                    tempOutput.add(application);
                }
            }
            
            output = tempOutput;
            
            if(output.size() < 1)
            {
                List<Application> temp = applicationJpaController.findAllApplicationsWithStatusAndDepartment(applicationStatusGroup,userDepartment,StartIndex,maxNumberOfRecords);
                for(Application application: temp)
                {
                    boolean found = false;
                    for(ApplicationReviewRequest reviewRequest : application.getApplicationReviewRequestList())
                    {
                        if(reviewRequest.getApplicationReviewRequestPK().getType().equals(com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD))
                        {                            
                            found = true;
                            break;
                        }
                    }
                    
                    if(!found)
                    {
                        output.add(application);
                    }
                }
            }
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            Faculty userFaculty = user.getEmployeeInformation().getDepartment().getFaculty();
            List<Application> tempOutput = new ArrayList<Application>(); 
            output = dAOFactory.createApplicationReviewRequestDAO().findAllApplicationsRequestForPersonAs(user, com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_DEAN);
            for(Application application : output)
            {
                if(application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
                {
                    tempOutput.add(application);
                }
            }
            
            output = tempOutput;
            
            if(output.size() < 1)
            {
                List<Application> temp = applicationJpaController.findAllApplicationsWithStatusAndFaculty(applicationStatusGroup,userFaculty,StartIndex,maxNumberOfRecords);
                for(Application application: temp)
                {
                    boolean found = false;
                    for(ApplicationReviewRequest reviewRequest : application.getApplicationReviewRequestList())
                    {
                        if(reviewRequest.getApplicationReviewRequestPK().getType().equals(com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_DEAN))
                        {                            
                            found = true;
                            break;
                        }
                    }
                    
                    if(!found)
                    {
                        output.add(application);
                    }
                }
            }
        }
        else
        {
            output = applicationJpaController.findAllApplicationsWithStatus(applicationStatusGroup,StartIndex,maxNumberOfRecords);
        }
        
        if(output == null)
        {
            output = new ArrayList<Application>();
        }
        
        return output;
    }
    
    public int getTotalNumberOfPendingApplications(Person user, String applicationStatusGroup)
    {
        ApplicationJpaController applicationJpaController = getDAOFactory().createApplicationDAO();
        
        long output = 0;
        
        
        if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED))
        {   
            output = applicationJpaController.countAllApplicationsWithStatusAndReferee(applicationStatusGroup,user);

        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED))
        {
            output = applicationJpaController.countAllApplicationsWithStatusAndGrantHolder(applicationStatusGroup,user);
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
        {
            Department userDepartment = user.getEmployeeInformation().getDepartment();
            List<Application> tempOutput = new ArrayList<Application>(); 
            List<Application> Output = getDAOFactory().createApplicationReviewRequestDAO().findAllApplicationsRequestForPersonAs(user, com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD);
            for(Application application : Output)
            {
                if(application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED))
                {
                    tempOutput.add(application);
                }
            }
            
            output = tempOutput.size();
            
            if(output < 1)
            {
                Output.clear();
                List<Application> temp = applicationJpaController.findAllApplicationsWithStatusAndDepartment(applicationStatusGroup,userDepartment,0,Integer.MAX_VALUE);
                for(Application application: temp)
                {
                    boolean found = false;
                    for(ApplicationReviewRequest reviewRequest : application.getApplicationReviewRequestList())
                    {
                        if(reviewRequest.getApplicationReviewRequestPK().getType().equals(com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_HOD))
                        {                            
                            found = true;
                            break;
                        }
                    }
                    
                    if(!found)
                    {
                        Output.add(application);
                    }
                }
                output = Output.size();
            }
        }
        else if(applicationStatusGroup.equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
        {
            Faculty userFaculty = user.getEmployeeInformation().getDepartment().getFaculty();
            List<Application> tempOutput = new ArrayList<Application>(); 
            List<Application> Output = getDAOFactory().createApplicationReviewRequestDAO().findAllApplicationsRequestForPersonAs(user, com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_DEAN);
            for(Application application : Output)
            {
                if(application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED))
                {
                    tempOutput.add(application);
                }
            }
            
            output = tempOutput.size();
            
            if(output < 1)
            {
                Output.clear();
                List<Application> temp = applicationJpaController.findAllApplicationsWithStatusAndFaculty(applicationStatusGroup,userFaculty,0,Integer.MAX_VALUE);
                for(Application application: temp)
                {
                    boolean found = false;
                    for(ApplicationReviewRequest reviewRequest : application.getApplicationReviewRequestList())
                    {
                        if(reviewRequest.getApplicationReviewRequestPK().getType().equals(com.softserve.constants.PersistenceConstants.APPLICATION_REVIEW_TYPE_DEAN))
                        {                            
                            found = true;
                            break;
                        }
                    }
                    
                    if(!found)
                    {
                        Output.add(application);
                    }
                }
                
                output = Output.size();
            }
        }
        else
        {
            output = applicationJpaController.countAllApplicationsWithStatus(applicationStatusGroup);
        }
        
        return (int) output;
    }
    
    public void declineAppliction(Session session, Application application, String reason) throws AuthenticationException, NonexistentEntityException, RollbackFailureException, Exception
    {
        
        DAOFactory dAOFactory = getDAOFactory();
        ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
        DeclineReportJpaController declineReportJpaController = dAOFactory.createDeclineReportDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();

        
        if(!application.getStatus().equals(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED))
        {
            //Deny application
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_DECLINED);        
            applicationJpaController.edit(application);

            DeclineReport declineReport = dBEntitiesFactory.createDeclineReportEntity(application,session.getUser(), reason, getGregorianCalendar().getTime());
            declineReportJpaController.create(declineReport);
        }
        else
        {
            throw new Exception("Application has already been declined");
        }
               
    }
    
    public void submitApplication(Application application) throws Exception
    {
        if(application == null)
        {
            throw new Exception("Application is not valid");
        }
        
        DAOFactory dAOFactory = getDAOFactory();
        ApplicationJpaController applicationJpaController = dAOFactory.createApplicationDAO();
        
        application = applicationJpaController.findApplication(application.getApplicationID());
        
        //Set application status
        application.setSubmissionDate(getGregorianCalendar().getTime());
        
        if(application.getType().equals(com.softserve.constants.PersistenceConstants.APPLICATION_TYPE_RENEWAL) || !application.getFundingType().equals(com.softserve.constants.PersistenceConstants.APPLICATION_FUNDINGTYPE_UPPOSTDOC) || application.getRefereeReportList().size() == application.getPersonList().size())
        {
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
        }
        else
        {
            application.setStatus(com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED);
        }
        applicationJpaController.edit(application);
    }
    
    public int getOrderIndexOfApplicationStatus(String status)
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
    
    public boolean hasApplicationAchivedThisStatus(Application application, String status)
    {
        return getOrderIndexOfApplicationStatus(application.getStatus()) >= getOrderIndexOfApplicationStatus(status);
    }
    
}
