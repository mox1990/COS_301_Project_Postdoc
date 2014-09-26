/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.auxillary.Issue;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@TransactionManagement(TransactionManagementType.BEAN)
@Stateless
public class NotifierServices implements NotifierServicesLocal {
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private NotificationServiceLocal notificationServiceLocal;
    @EJB
    private ProgressReportManagementServiceLocal progressReportManagementServiceLocal;    
    @EJB
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    @EJB
    private ApplicationRenewalServiceLocal applicationRenewalServiceLocal;
    @EJB
    private MeetingManagementServiceLocal meetingManagementServiceLocal;
            
    protected ProgressReportManagementServiceLocal getProgressReportManagementServiceEJB()
    {
        return progressReportManagementServiceLocal;
    }

    protected ApplicationRenewalServiceLocal getApplicationRenewalServiceEJB() 
    {
        return applicationRenewalServiceLocal;
    }

    protected MeetingManagementServiceLocal getMeetingManagementServiceEJB() 
    {
        return meetingManagementServiceLocal;
    }

    protected UserAccountManagementServiceLocal getUserAccountManagementServiceEJB() 
    {
        return userAccountManagementServiceLocal;
    }
    
    protected NotificationServiceLocal getNotificationServiceEJB() 
    {
        return notificationServiceLocal;
    }
    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected ApplicationServicesUtil getApplicationServicesUTIL(EntityManager em)
    {
        return new ApplicationServicesUtil(em);
    }
    
    @Override
    public List<Issue> loadAllPendingIssuesForSession(Session session) throws Exception 
    {
        return loadAllPendingIssuesForUser(session, session.getUser());
    }

    @Override
    public List<Issue> loadAllPendingIssuesForUser(Session session, Person person) throws Exception 
    {
        EntityManager em = emf.createEntityManager();
        
        try
        {
            List<Issue> issues = new ArrayList<Issue>();
            
            
            ApplicationServicesUtil applicationServicesUtil = getApplicationServicesUTIL(em);
            
            if(person.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_REFEREE))
            {
                Issue issue = new Issue("Referral reports", "You have pending referral reports", 0);
                issue.setCount(applicationServicesUtil.getTotalNumberOfPendingApplications(person, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_SUBMITTED));
                if(issue.getCount() > 0)
                {
                    issues.add(issue);
                }
            }
            
            if(person.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER))
            {
                Issue issue = new Issue("Referral reports", "You have pending applications for finalisation", 0);
                issue.setCount(applicationServicesUtil.getTotalNumberOfPendingApplications(person, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED));
                if(issue.getCount() > 0)
                {
                    issues.add(issue);
                }
            }
            
            if(person.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD))
            {
                Issue issue = new Issue("Recommendations", "You have pending recommendations", 0);
                issue.setCount(applicationServicesUtil.getTotalNumberOfPendingApplications(person, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_FINALISED));
                if(issue.getCount() > 0)
                {
                    issues.add(issue);
                }
            }
            
            if(person.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DEANS_OFFICE_MEMBER))
            {
                Issue issue = new Issue("Endorsements", "You have pending endorsements", 0);
                issue.setCount(applicationServicesUtil.getTotalNumberOfPendingApplications(person, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_RECOMMENDED));
                if(issue.getCount() > 0)
                {
                    issues.add(issue);
                }
            }
            
            if(person.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER))
            {
                Issue issue = new Issue("Application eligibility checks", "You have pending eligiblity checks", 0);
                issue.setCount(applicationServicesUtil.getTotalNumberOfPendingApplications(person, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ENDORSED));
                if(issue.getCount() > 0)
                {
                    issues.add(issue);
                }
            }
            
            if(person.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER))
            {
                Issue issue = new Issue("Funding reports", "You have pending funding reports", 0);
                issue.setCount(applicationServicesUtil.getTotalNumberOfPendingApplications(person, com.softserve.constants.PersistenceConstants.APPLICATION_STATUS_ELIGIBLE));
                if(issue.getCount() > 0)
                {
                    issues.add(issue);
                }
            }
            
            if(person.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_POSTDOCTORAL_COMMITTEE_MEMBER) || person.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_DRIS_MEMBER))
            {
                Issue issue = new Issue("Active post doctoral committe meeting", "You are currently attending post doctoral committe meetings", 0);
                issue.setCount(getMeetingManagementServiceEJB().getAllActiveMeetingsForWhichUserIsToAttend(new Session(null, person, true)).size());
                if(issue.getCount() > 0)
                {
                    issues.add(issue);
                }
            }
            
            if(person.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_RESEARCH_FELLOW))
            {
                Issue issue = new Issue("Progress reports", "You have pending progress reports", 0);
                issue.setCount(getProgressReportManagementServiceEJB().allApplicationsWithPendingReportsForUser(new Session(null, person, true)).size());
                if(issue.getCount() > 0)
                {
                    issues.add(issue);
                }
                
                issue = new Issue("Possible renewals", "You possible renewals waiting for you", 0);
                issue.setCount(getApplicationRenewalServiceEJB().getRenewableApplicationsForFellow(new Session(null, person, true),person).size());
                if(issue.getCount() > 0)
                {
                    issues.add(issue);
                }
            }
            
            if(person.getSecurityRoleList().contains(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR))
            {
                Issue issue = new Issue("Account approval", "You have pending funding reports", 0);
                issue.setCount(getUserAccountManagementServiceEJB().loadAllPendingOnDemandAccounts(new Session(null, person, true)).size());
                if(issue.getCount() > 0)
                {
                    issues.add(issue);
                }
            }
                      
            
            return issues;
        }
        finally
        {
            em.close();
        }
    }
    
    @Schedule(dayOfMonth = "*", month = "*", year = "*")
    @Asynchronous
    public void sendDailyReminders()
    {
        try
        {
           List<Person> accounts = getUserAccountManagementServiceEJB().viewAllUserAccounts(new Session(null, null, true));
           
           for(Person person : accounts)
           {
               if(person.getAccountStatus().equals(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_ACTIVE))
               {
                   List<Issue> issues = loadAllPendingIssuesForUser(new Session(null, null, true), person);
                   
                   if(issues.size() > 0)
                   {
                        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
                        String message = "This messages serves as a reminder for you of the outsanding tasks you have:\n\n===========================================================\n\n";
                        
                        for(Issue issue : issues)
                        {
                            message += "Description: " + issue.getMessage() + " Number: " + issue.getCount() + "\n";
                        }
                        
                        Notification notification = dBEntitiesFactory.createNotificationEntity(null, person, "Reminder", message);
                       
                        getNotificationServiceEJB().sendOnlyEmail(new Session(null, null, true),notification);
                        
                   }                   
                   
               }
           }
        }
        catch(Exception ex)
        {
            Logger.getLogger(NotifierServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
