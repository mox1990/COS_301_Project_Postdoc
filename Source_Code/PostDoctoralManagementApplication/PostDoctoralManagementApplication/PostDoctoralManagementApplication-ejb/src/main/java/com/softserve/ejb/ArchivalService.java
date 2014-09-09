/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.AcademicQualificationJpaController;
import com.softserve.DBDAO.AddressJpaController;
import com.softserve.DBDAO.AmmendRequestJpaController;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.DBDAO.AuditLogJpaController;
import com.softserve.DBDAO.CommitteeMeetingJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBDAO.DeclineReportJpaController;
import com.softserve.DBDAO.DepartmentJpaController;
import com.softserve.DBDAO.EligiblityReportJpaController;
import com.softserve.DBDAO.EmployeeInformationJpaController;
import com.softserve.DBDAO.EndorsementJpaController;
import com.softserve.DBDAO.ExperienceJpaController;
import com.softserve.DBDAO.FacultyJpaController;
import com.softserve.DBDAO.ForwardAndRewindReportJpaController;
import com.softserve.DBDAO.FundingCostJpaController;
import com.softserve.DBDAO.FundingReportJpaController;
import com.softserve.DBDAO.InstitutionJpaController;
import com.softserve.DBDAO.MinuteCommentJpaController;
import com.softserve.DBDAO.NotificationJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.ProgressReportJpaController;
import com.softserve.DBDAO.RecommendationReportJpaController;
import com.softserve.DBDAO.RefereeReportJpaController;
import com.softserve.DBDAO.ResearchFellowInformationJpaController;
import com.softserve.DBDAO.SecurityRoleJpaController;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.AmmendRequest;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.ApplicationReviewRequest;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.DeclineReport;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.EligiblityReport;
import com.softserve.DBEntities.EmployeeInformation;
import com.softserve.DBEntities.Endorsement;
import com.softserve.DBEntities.Experience;
import com.softserve.DBEntities.Faculty;
import com.softserve.DBEntities.ForwardAndRewindReport;
import com.softserve.DBEntities.FundingCost;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Institution;
import com.softserve.DBEntities.MinuteComment;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.RecommendationReport;
import com.softserve.DBEntities.RefereeReport;
import com.softserve.DBEntities.ResearchFellowInformation;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.annotations.SecuredMethod;
import com.softserve.constants.PersistenceConstants;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.interceptors.TransactionInterceptor;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ArchivalService implements ArchivalServiceLocal {
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.ARCHIVE_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emfArchive;
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emfWorking;
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.BACKUP_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emfBackup;

    public ArchivalService() 
    {
    }
    
    public ArchivalService(EntityManagerFactory emfW, EntityManagerFactory emfA,  EntityManagerFactory emfB)
    {
    }
    
    protected DAOFactory getDAOFactoryWorking()
    {
        return new DAOFactory(emfWorking);
    }
    
    protected DAOFactory getDAOFactoryBackup()
    {
        return new DAOFactory(emfWorking);
    }
    
    //@Schedule(dayOfWeek="*", hour="2", info = "Daily backup of the database.")
    @Override
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    public void backupDatabase(Session session)
    {
        System.out.println("Running Backup...");
        // Setup Working DAOs...
        DAOFactory daoFactoryWorking = getDAOFactoryWorking();
        AcademicQualificationJpaController workingAcademicQualificationJpaController = daoFactoryWorking.createAcademicQualificationDAO();
        AddressJpaController workingAddressJpaController = daoFactoryWorking.createAddressDAO();
        AmmendRequestJpaController workingAmmendRequestJpaController = daoFactoryWorking.createAmmendRequestDAO();
        ApplicationJpaController workingApplicationJpaController = daoFactoryWorking.createApplicationDAO();
        ApplicationReviewRequestJpaController workingApplicationReviewRequestJpaController = daoFactoryWorking.createApplicationReviewRequestDAO();
        AuditLogJpaController workingAuditLogJpaController = daoFactoryWorking.createAuditLogDAO();
        CommitteeMeetingJpaController workingCommitteeMeetingJpaController = daoFactoryWorking.createCommitteeMeetingDAO();
        CvJpaController workingCvJpaController = daoFactoryWorking.createCvDAO();
        DeclineReportJpaController workingDeclineReportJpaController = daoFactoryWorking.createDeclineReportDAO();
        DepartmentJpaController workingDepartmentJpaController = daoFactoryWorking.createDepartmentDAO();
        EligiblityReportJpaController workingEligiblityReportJpaController = daoFactoryWorking.createEligiblityReportDAO();
        EmployeeInformationJpaController workingEmployeeInformationJpaController = daoFactoryWorking.createEmployeeInformationDAO();
        EndorsementJpaController workingEndorsementJpaController = daoFactoryWorking.createEndorsementDAO();
        ExperienceJpaController workingExperienceJpaController = daoFactoryWorking.createExperienceDAO();
        FacultyJpaController workingFacultyJpaController = daoFactoryWorking.createFacultyDAO();
        ForwardAndRewindReportJpaController workingForwardAndRewindReportJpaController = daoFactoryWorking.createForwardAndRewindReportDAO();
        FundingCostJpaController workingFundingCostJpaController = daoFactoryWorking.createFundingCostJpaController();
        FundingReportJpaController workingFundingReportJpaController = daoFactoryWorking.createFundingReportDAO();
        InstitutionJpaController workingInstitutionJpaController = daoFactoryWorking.createInstitutionDAO();
        MinuteCommentJpaController workingMinuteCommentJpaController = daoFactoryWorking.createMinuteCommentDAO();
        NotificationJpaController workingNotificationJpaController = daoFactoryWorking.createNotificationDAO();
        PersonJpaController workingPersonJpaController = daoFactoryWorking.createPersonDAO();
        ProgressReportJpaController workingProgressReportJpaController = daoFactoryWorking.createProgressReportDAO();
        RecommendationReportJpaController workingRecommendationReportJpaController = daoFactoryWorking.createRecommendationReportDAO();
        RefereeReportJpaController workingRefereeReportJpaController = daoFactoryWorking.createRefereeReportDAO();
        ResearchFellowInformationJpaController workingResearchFellowInformationJpaController = daoFactoryWorking.createResearchFellowInformationDAO();
        SecurityRoleJpaController workingSecurityRoleJpaController = daoFactoryWorking.createSecurityRoleJpaController();  
        
        DAOFactory daoFactoryBackup = getDAOFactoryBackup();
        AcademicQualificationJpaController backupAcademicQualificationJpaController = daoFactoryBackup.createAcademicQualificationDAO();
        AddressJpaController backupAddressJpaController = daoFactoryBackup.createAddressDAO();
        AmmendRequestJpaController backupAmmendRequestJpaController = daoFactoryBackup.createAmmendRequestDAO();
        ApplicationJpaController backupApplicationJpaController = daoFactoryBackup.createApplicationDAO();
        ApplicationReviewRequestJpaController backupApplicationReviewRequestJpaController = daoFactoryBackup.createApplicationReviewRequestDAO();
        AuditLogJpaController backupAuditLogJpaController = daoFactoryBackup.createAuditLogDAO();
        CommitteeMeetingJpaController backupCommitteeMeetingJpaController = daoFactoryBackup.createCommitteeMeetingDAO();
        CvJpaController backupCvJpaController = daoFactoryBackup.createCvDAO();
        DeclineReportJpaController backupDeclineReportJpaController = daoFactoryBackup.createDeclineReportDAO();
        DepartmentJpaController backupDepartmentJpaController = daoFactoryBackup.createDepartmentDAO();
        EligiblityReportJpaController backupEligiblityReportJpaController = daoFactoryBackup.createEligiblityReportDAO();
        EmployeeInformationJpaController backupEmployeeInformationJpaController = daoFactoryBackup.createEmployeeInformationDAO();
        EndorsementJpaController backupEndorsementJpaController = daoFactoryBackup.createEndorsementDAO();
        ExperienceJpaController backupExperienceJpaController = daoFactoryBackup.createExperienceDAO();
        FacultyJpaController backupFacultyJpaController = daoFactoryBackup.createFacultyDAO();
        ForwardAndRewindReportJpaController backupForwardAndRewindReportJpaController = daoFactoryBackup.createForwardAndRewindReportDAO();
        FundingCostJpaController backupFundingCostJpaController = daoFactoryBackup.createFundingCostJpaController();
        FundingReportJpaController backupFundingReportJpaController = daoFactoryBackup.createFundingReportDAO();
        InstitutionJpaController backupInstitutionJpaController = daoFactoryBackup.createInstitutionDAO();
        MinuteCommentJpaController backupMinuteCommentJpaController = daoFactoryBackup.createMinuteCommentDAO();
        NotificationJpaController backupNotificationJpaController = daoFactoryBackup.createNotificationDAO();
        PersonJpaController backupPersonJpaController = daoFactoryBackup.createPersonDAO();
        ProgressReportJpaController backupProgressReportJpaController = daoFactoryBackup.createProgressReportDAO();
        RecommendationReportJpaController backupRecommendationReportJpaController = daoFactoryBackup.createRecommendationReportDAO();
        RefereeReportJpaController backupRefereeReportJpaController = daoFactoryBackup.createRefereeReportDAO();
        ResearchFellowInformationJpaController backupResearchFellowInformationJpaController = daoFactoryBackup.createResearchFellowInformationDAO();
        SecurityRoleJpaController backupSecurityRoleJpaController = daoFactoryBackup.createSecurityRoleJpaController();  
        
   
        
        for(Person oldPerson: workingPersonJpaController.findPersonEntities())
        {
            Person p;
            if(backupPersonJpaController.findPerson(oldPerson.getSystemID()) == null)
            {
                p = new Person(oldPerson.getSystemID());
                
                //Might not need this but just doing it any way...
                p.setSecurityRoleList(new ArrayList<SecurityRole>());
                p.setApplicationList(new ArrayList<Application>());
                p.setCommitteeMeetingList(new ArrayList<CommitteeMeeting>());
                p.setDeclineReportList(new ArrayList<DeclineReport>());
                p.setEndorsementList(new ArrayList<Endorsement>());
                p.setApplicationReviewRequestList(new ArrayList<ApplicationReviewRequest>());
                p.setResearchFellowInformation(null);
                p.setRefereeReportList(new ArrayList<RefereeReport>());
                p.setEmployeeInformation(null);
                p.setNotificationList(new ArrayList<Notification>());
                p.setNotificationList1(new ArrayList<Notification>());
                p.setAmmendRequestList(new ArrayList<AmmendRequest>());
                p.setAuditLogList(new ArrayList<AuditLog>());
                p.setRecommendationReportList(new ArrayList<RecommendationReport>());
                p.setFundingReportList(new ArrayList<FundingReport>());
                p.setCv(null);
                p.setApplicationList1(new ArrayList<Application>());
                p.setApplicationList2(new ArrayList<Application>());
                p.setAddressLine1(null);
                p.setMinuteCommentList(new ArrayList<MinuteComment>());
                p.setEligiblityReportList(new ArrayList<EligiblityReport>());
                p.setForwardAndRewindReportList(new ArrayList<ForwardAndRewindReport>());
            }
            else
            {
                p = backupPersonJpaController.findPerson(oldPerson.getSystemID());
            }
            // Copy data over...
            
            p.setPassword(oldPerson.getPassword());
            p.setTitle(oldPerson.getTitle());
            p.setFullName(oldPerson.getFullName());
            p.setSurname(oldPerson.getSurname());
            p.setEmail(oldPerson.getEmail());
            p.setTelephoneNumber(oldPerson.getTelephoneNumber());
            p.setWorkNumber(oldPerson.getWorkNumber());
            p.setFaxNumber(oldPerson.getFaxNumber());
            p.setCellphoneNumber(oldPerson.getCellphoneNumber());
            p.setUpEmployee(oldPerson.getUpEmployee());
            p.setAccountStatus(oldPerson.getAccountStatus());
            
            
            // Release all other entities from Person...
            List<SecurityRole> securityRoleList = oldPerson.getSecurityRoleList();
            
            List<Application> applicationList = oldPerson.getApplicationList();
            
            List<CommitteeMeeting> committeeMeetingList = oldPerson.getCommitteeMeetingList();
            
            
            List<DeclineReport> declineReportList = oldPerson.getDeclineReportList();
            
            List<Endorsement> endorsementList = oldPerson.getEndorsementList();
            
            List<ApplicationReviewRequest> applicationReviewRequestList  = oldPerson.getApplicationReviewRequestList();
            
            ResearchFellowInformation researchFellowInformation = oldPerson.getResearchFellowInformation();
            
            List<RefereeReport> refereeReportList = oldPerson.getRefereeReportList();
            
            EmployeeInformation employeeInformation = oldPerson.getEmployeeInformation();
            
            List<Notification> notificationList = oldPerson.getNotificationList();
            
            List<Notification> notificationList1 = oldPerson.getNotificationList1();
            
            List<AmmendRequest> ammendRequestList = oldPerson.getAmmendRequestList();
            
            List<AuditLog> auditLogList = oldPerson.getAuditLogList();
            
            List<RecommendationReport> recommendationReportList = oldPerson.getRecommendationReportList();
            
            List<FundingReport> fundingReportList = oldPerson.getFundingReportList();
            
            Cv cv = oldPerson.getCv();
            
            List<Application> applicationList1 = oldPerson.getApplicationList1();
            
            List<Application> applicationList2 = oldPerson.getApplicationList2();
            
            Address addressLine1 = oldPerson.getAddressLine1();
            
            List<MinuteComment> minuteCommentList = oldPerson.getMinuteCommentList();
            
            List<EligiblityReport> eligiblityReportList = oldPerson.getEligiblityReportList();
            
            List<ForwardAndRewindReport> forwardAndRewindReportList= oldPerson.getForwardAndRewindReportList();
            
            try
            {
                if(backupPersonJpaController.findPerson(p.getSystemID()) == null)
                {
                    backupPersonJpaController.create(p);
                }
                
                // Return entities to Person while creating them if need be...
                List<SecurityRole> newSecurityRoleList = new ArrayList<SecurityRole>();
                for(SecurityRole s: securityRoleList)
                {
                    if(backupSecurityRoleJpaController.findSecurityRole(s.getRoleID()) == null)
                    {
                        SecurityRole tmp = new SecurityRole(s.getRoleID());
                        tmp.setName(s.getName());
                        tmp.setRoleMask(s.getRoleMask());
                        backupSecurityRoleJpaController.create(tmp);
                        securityRoleList.set(securityRoleList.indexOf(s), tmp);
                    }
                    else
                    {
                        securityRoleList.set(securityRoleList.indexOf(s), backupSecurityRoleJpaController.findSecurityRole(s.getRoleID()));
                    }
                }
                
                List<Application> newApplicationList = new ArrayList<Application>();
                for(Application a: applicationList)
                {
                    // TODO: Handle the dependecies of Application...
                    if(backupApplicationJpaController.findApplication(a.getApplicationID()) == null)
                    {
                        Application tmp = new Application(a.getApplicationID());
                        backupApplicationJpaController.create(tmp);
                        newApplicationList.add(tmp);
                    }
                    else
                    {
                        newApplicationList.add(backupApplicationJpaController.findApplication(a.getApplicationID()));
                    }
                }
                        
                List<CommitteeMeeting> newCommitteeMeetingList = new ArrayList<CommitteeMeeting>();
                for(CommitteeMeeting c: committeeMeetingList)
                {
                    if(backupCommitteeMeetingJpaController.findCommitteeMeeting(c.getMeetingID()) == null)
                    {
                        CommitteeMeeting tmp = new CommitteeMeeting(c.getMeetingID());
                        backupCommitteeMeetingJpaController.create(tmp);
                        newCommitteeMeetingList.add(tmp);
                    }
                    else
                    {
                        newCommitteeMeetingList.add(backupCommitteeMeetingJpaController.findCommitteeMeeting(c.getMeetingID()));
                    }
                }
                
                List<DeclineReport> newDeclineReportList = new ArrayList<DeclineReport>();
                for(DeclineReport d: declineReportList)
                {
                    if(backupDeclineReportJpaController.findDeclineReport(d.getReportID()) == null)
                    {
                        DeclineReport tmp = new DeclineReport(d.getReportID());
                        backupDeclineReportJpaController.create(tmp);
                        newDeclineReportList.add(tmp);
                    }
                    else
                    {
                        newDeclineReportList.add(backupDeclineReportJpaController.findDeclineReport(d.getReportID()));
                    }
                }
                
                List<Endorsement> newEndorsementList = new ArrayList<Endorsement>();
                for(Endorsement e: endorsementList)
                {
                    if(backupEndorsementJpaController.findEndorsement(e.getEndorsementID()) == null)
                    {
                        Endorsement tmp = new Endorsement(e.getEndorsementID());
                        backupEndorsementJpaController.create(tmp);
                        newEndorsementList.add(tmp);
                    }
                    else
                    {
                        newEndorsementList.add(backupEndorsementJpaController.findEndorsement(e.getEndorsementID()));
                    }
                }
                
                List<ApplicationReviewRequest> newApplicationReviewRequestList = new ArrayList<ApplicationReviewRequest>();
                for(ApplicationReviewRequest a: applicationReviewRequestList)
                {                        
                    if(backupApplicationReviewRequestJpaController.findApplicationReviewRequest(a.getApplicationReviewRequestPK()) == null)
                    {
                        ApplicationReviewRequest tmp = new ApplicationReviewRequest(a.getApplicationReviewRequestPK());
                        backupApplicationReviewRequestJpaController.create(tmp);
                        newApplicationReviewRequestList.add(tmp);
                    }
                    else
                    {
                        newApplicationReviewRequestList.add(backupApplicationReviewRequestJpaController.findApplicationReviewRequest(a.getApplicationReviewRequestPK()));
                    }
                }
                
                ResearchFellowInformation newResearchFellowInformation;
                if(researchFellowInformation == null)
                {
                    newResearchFellowInformation = null;
                }
                else if(backupResearchFellowInformationJpaController.findResearchFellowInformation(researchFellowInformation.getSystemAssignedID()) == null)
                {
                    ResearchFellowInformation tmp = new ResearchFellowInformation(researchFellowInformation.getSystemAssignedID());
                    backupResearchFellowInformationJpaController.create(tmp);
                    newResearchFellowInformation = tmp;
                }
                else
                {
                    newResearchFellowInformation = backupResearchFellowInformationJpaController.findResearchFellowInformation(researchFellowInformation.getSystemAssignedID());
                }
                
                List<RefereeReport> newRefereeReportList = new ArrayList<RefereeReport>();
                for(RefereeReport r: refereeReportList)
                {
                    if(backupRefereeReportJpaController.findRefereeReport(r.getReportID()) == null)
                    {
                        RefereeReport tmp = new RefereeReport(r.getReportID());
                        backupRefereeReportJpaController.create(tmp);
                        newRefereeReportList.add(tmp);
                    }
                    else
                    {
                        newRefereeReportList.add(backupRefereeReportJpaController.findRefereeReport(r.getReportID()));
                    }
                }
                
                EmployeeInformation newEmployeeInformation;
                if(employeeInformation == null)
                {
                    newEmployeeInformation = null;
                }
                else if(backupEmployeeInformationJpaController.findEmployeeInformation(employeeInformation.getEmployeeID()) == null)
                {
                    EmployeeInformation tmp = new EmployeeInformation(employeeInformation.getEmployeeID());
                    backupEmployeeInformationJpaController.create(tmp);
                    newEmployeeInformation = tmp;
                }
                else
                {
                    newEmployeeInformation = backupEmployeeInformationJpaController.findEmployeeInformation(employeeInformation.getEmployeeID());
                }
                
                List<Notification> newNotificationList = new ArrayList<Notification>();
                for(Notification n: notificationList)
                {
                    if(backupNotificationJpaController.findNotification(n.getNotificationID()) == null)
                    {
                        Notification tmp = new Notification(n.getNotificationID());
                        backupNotificationJpaController.create(tmp);
                        newNotificationList.add(tmp);
                    }
                    else
                    {
                        newNotificationList.add(backupNotificationJpaController.findNotification(n.getNotificationID()));
                    }
                }
                
                List<Notification> newNotificationList1 = new ArrayList<Notification>();
                for(Notification n: notificationList1)
                {
                    if(backupNotificationJpaController.findNotification(n.getNotificationID()) == null)
                    {
                        Notification tmp = new Notification(n.getNotificationID());
                        backupNotificationJpaController.create(tmp);
                        newNotificationList1.add(tmp);
                    }
                    else
                    {
                        newNotificationList1.add(backupNotificationJpaController.findNotification(n.getNotificationID()));
                    }
                }
                
                List<AmmendRequest> newAmmendRequestList = new ArrayList<AmmendRequest>();
                for(AmmendRequest a: ammendRequestList)
                {
                    if(backupAmmendRequestJpaController.findAmmendRequest(a.getRequestID()) == null)
                    {
                        AmmendRequest tmp = new AmmendRequest(a.getRequestID());
                        backupAmmendRequestJpaController.create(tmp);
                        newAmmendRequestList.add(tmp);
                    }
                    else
                    {
                        newAmmendRequestList.add(backupAmmendRequestJpaController.findAmmendRequest(a.getRequestID()));
                    }
                }
                
                List<AuditLog> newAuditLogList = new ArrayList<AuditLog>();
                for(AuditLog a: auditLogList)
                {
                    if(backupAuditLogJpaController.findAuditLog(a.getEntryID()) == null)
                    {
                        AuditLog tmp = new AuditLog(a.getEntryID());
                        tmp.setTimestamp(a.getTimestamp());
                        tmp.setAction(a.getAction());
                        if(backupPersonJpaController.findPerson(a.getPerson().getSystemID()) == null)
                        {
                            Person actor = new Person(a.getPerson().getSystemID());
                            actor.setPassword(a.getPerson().getPassword());
                            actor.setTitle(a.getPerson().getTitle());
                            actor.setFullName(a.getPerson().getFullName());
                            actor.setSurname(a.getPerson().getSurname());
                            actor.setEmail(a.getPerson().getEmail());
                            actor.setTelephoneNumber(a.getPerson().getTelephoneNumber());
                            actor.setWorkNumber(a.getPerson().getWorkNumber());
                            actor.setFaxNumber(a.getPerson().getFaxNumber());
                            actor.setCellphoneNumber(a.getPerson().getCellphoneNumber());
                            actor.setUpEmployee(a.getPerson().getUpEmployee());
                            actor.setAccountStatus(a.getPerson().getAccountStatus());
                            
                            tmp.setPerson(actor);
                        }
                        else
                        {
                            tmp.setPerson(backupPersonJpaController.findPerson(a.getPerson().getSystemID()));
                        }
                        backupAuditLogJpaController.create(tmp);
                        newAuditLogList.add(tmp);
                    }
                    else
                    {
                        newAuditLogList.add(backupAuditLogJpaController.findAuditLog(a.getEntryID()));
                    }
                }
                
                List<RecommendationReport> newRecommendationReportList = new ArrayList<RecommendationReport>();
                for(RecommendationReport r: recommendationReportList)
                {
                    if(backupRecommendationReportJpaController.findRecommendationReport(r.getReportID()) == null)
                    {
                        RecommendationReport tmp = new RecommendationReport(r.getReportID());
                        tmp.setTimestamp(r.getTimestamp());
                        tmp.setContent(r.getContent());
                        if(backupApplicationJpaController.findApplication(r.getApplication().getApplicationID()) == null)
                        {
                            Application app = new Application(r.getApplication().getApplicationID());
                            tmp.setApplication(app);
                        }
                        else
                        {
                            tmp.setApplication(backupApplicationJpaController.findApplication(r.getApplication().getApplicationID()));
                        }
                        
                        if(backupPersonJpaController.findPerson(r.getHod().getSystemID()) == null)
                        {
                            Person hod = new Person(r.getHod().getSystemID());
                            hod.setPassword(r.getHod().getPassword());
                            hod.setTitle(r.getHod().getTitle());
                            hod.setFullName(r.getHod().getFullName());
                            hod.setSurname(r.getHod().getSurname());
                            hod.setEmail(r.getHod().getEmail());
                            hod.setTelephoneNumber(r.getHod().getTelephoneNumber());
                            hod.setWorkNumber(r.getHod().getWorkNumber());
                            hod.setFaxNumber(r.getHod().getFaxNumber());
                            hod.setCellphoneNumber(r.getHod().getCellphoneNumber());
                            hod.setUpEmployee(r.getHod().getUpEmployee());
                            hod.setAccountStatus(r.getHod().getAccountStatus());
                            
                            tmp.setHod(hod);
                        }
                        else
                        {
                            tmp.setHod(backupPersonJpaController.findPerson(r.getHod().getSystemID()));
                        }
                        backupRecommendationReportJpaController.create(tmp);
                        newRecommendationReportList.add(tmp);
                    }
                    else
                    {
                        newRecommendationReportList.add(backupRecommendationReportJpaController.findRecommendationReport(r.getReportID()));
                    }
                }
                
                List<FundingReport> newFundingReportList = new ArrayList<FundingReport>();
                for(FundingReport f: fundingReportList)
                {
                    if(backupFundingReportJpaController.findFundingReport(f.getReportID()) == null)
                    {
                        FundingReport tmp = new FundingReport(f.getReportID());
                        tmp.setTimestamp(f.getTimestamp());
                        if(backupApplicationJpaController.findApplication(f.getApplication().getApplicationID()) == null)
                        {
                            Application app = new Application(f.getApplication().getApplicationID());
                            tmp.setApplication(app);
                        }
                        else
                        {
                            tmp.setApplication(backupApplicationJpaController.findApplication(f.getApplication().getApplicationID()));
                        }
                        
                        if(backupPersonJpaController.findPerson(f.getDris().getSystemID()) == null)
                        {
                            Person dris = new Person(f.getDris().getSystemID());
                            dris.setPassword(f.getDris().getPassword());
                            dris.setTitle(f.getDris().getTitle());
                            dris.setFullName(f.getDris().getFullName());
                            dris.setSurname(f.getDris().getSurname());
                            dris.setEmail(f.getDris().getEmail());
                            dris.setTelephoneNumber(f.getDris().getTelephoneNumber());
                            dris.setWorkNumber(f.getDris().getWorkNumber());
                            dris.setFaxNumber(f.getDris().getFaxNumber());
                            dris.setCellphoneNumber(f.getDris().getCellphoneNumber());
                            dris.setUpEmployee(f.getDris().getUpEmployee());
                            dris.setAccountStatus(f.getDris().getAccountStatus());
                            
                            tmp.setDris(dris);
                        }
                        else
                        {
                            tmp.setDris(backupPersonJpaController.findPerson(f.getDris().getSystemID()));
                        }
                        backupFundingReportJpaController.create(tmp);
                        newFundingReportList.add(tmp);
                    }
                    else
                    {
                        newFundingReportList.add(backupFundingReportJpaController.findFundingReport(f.getReportID()));
                    }
                }
                
                // TODO: CV extras...
                Cv newCv;
                if(cv == null)
                {
                    newCv = null;
                }
                else if(backupCvJpaController.findCv(cv.getCvID()) == null)
                {  
                    Cv tmp = new Cv(cv.getCvID());
                    tmp.setIdNumber(cv.getIdNumber());
                    tmp.setDateOfBirth(cv.getDateOfBirth());
                    
                    if(backupPersonJpaController.findPerson(cv.getPerson().getSystemID()) == null)
                        {
                            Person person = new Person(cv.getPerson().getSystemID());
                            person.setPassword(cv.getPerson().getPassword());
                            person.setTitle(cv.getPerson().getTitle());
                            person.setFullName(cv.getPerson().getFullName());
                            person.setSurname(cv.getPerson().getSurname());
                            person.setEmail(cv.getPerson().getEmail());
                            person.setTelephoneNumber(cv.getPerson().getTelephoneNumber());
                            person.setWorkNumber(cv.getPerson().getWorkNumber());
                            person.setFaxNumber(cv.getPerson().getFaxNumber());
                            person.setCellphoneNumber(cv.getPerson().getCellphoneNumber());
                            person.setUpEmployee(cv.getPerson().getUpEmployee());
                            person.setAccountStatus(cv.getPerson().getAccountStatus());
                            
                            tmp.setPerson(person);
                        }
                        else
                        {
                            tmp.setPerson(backupPersonJpaController.findPerson(cv.getPerson().getSystemID()));
                        }
                    
                    backupCvJpaController.create(tmp);
                    newCv = tmp;
                }
                else
                {
                    newCv = backupCvJpaController.findCv(cv.getCvID());
                }
                
                List<Application> newApplicationList1 = new ArrayList<Application>();
                for(Application a: applicationList1)
                {
                    if(backupApplicationJpaController.findApplication(a.getApplicationID()) == null)
                    {
                        Application tmp = new Application(a.getApplicationID());
                        backupApplicationJpaController.create(tmp);
                        newApplicationList1.add(tmp);
                    }
                    else
                    {
                        newApplicationList1.add(backupApplicationJpaController.findApplication(a.getApplicationID()));
                    }
                }
                
                List<Application> newApplicationList2 = new ArrayList<Application>();
                for(Application a: applicationList2)
                {
                    if(backupApplicationJpaController.findApplication(a.getApplicationID()) == null)
                    {
                        Application tmp = new Application(a.getApplicationID());
                        backupApplicationJpaController.create(tmp);
                        newApplicationList2.add(tmp);
                    }
                    else
                    {
                        newApplicationList2.add(backupApplicationJpaController.findApplication(a.getApplicationID()));
                    }
                }
                    
                Address newAddressLine1;
                if(addressLine1 == null)
                {
                    newAddressLine1 = null;
                }
                else if(backupAddressJpaController.findAddress(addressLine1.getAddressID()) == null)
                {
                    Address tmp = new Address(addressLine1.getAddressID());
                    backupAddressJpaController.create(tmp);
                    newAddressLine1 = tmp;
                }
                else
                {
                    newAddressLine1 = backupAddressJpaController.findAddress(addressLine1.getAddressID());
                }
                
                List<MinuteComment> newMinuteCommentList = new ArrayList<MinuteComment>();
                for(MinuteComment m: minuteCommentList)
                {
                    if(backupMinuteCommentJpaController.findMinuteComment(m.getCommentID()) == null)
                    {
                        MinuteComment tmp = new MinuteComment(m.getCommentID());
                        tmp.setTimestamp(m.getTimestamp());
                        tmp.setComment(m.getComment());
                        backupMinuteCommentJpaController.create(tmp);
                        newMinuteCommentList.add(tmp);
                    }
                    else
                    {
                        newMinuteCommentList.add(backupMinuteCommentJpaController.findMinuteComment(m.getCommentID()));
                    }
                }
                
                List<EligiblityReport> newEligiblityReportList = new ArrayList<EligiblityReport>();
                for(EligiblityReport e: eligiblityReportList)
                {
                    if(backupEligiblityReportJpaController.findEligiblityReport(e.getReportID()) == null)
                    {
                        EligiblityReport tmp = new EligiblityReport(e.getReportID());
                        tmp.setEligiblityCheckDate(e.getEligiblityCheckDate());
                        backupEligiblityReportJpaController.create(tmp);
                        newEligiblityReportList.add(tmp);
                    }
                    else
                    {
                        newEligiblityReportList.add(backupEligiblityReportJpaController.findEligiblityReport(e.getReportID()));
                    }
                }
                
                List<ForwardAndRewindReport> newForwardAndRewindReportList = new ArrayList<ForwardAndRewindReport>();
                for(ForwardAndRewindReport f: forwardAndRewindReportList)
                {
                    if(backupForwardAndRewindReportJpaController.findForwardAndRewindReport(f.getReportID()) == null)
                    {
                        ForwardAndRewindReport tmp = new ForwardAndRewindReport(f.getReportID());
                        tmp.setTimestamp(f.getTimestamp());
                        tmp.setType(f.getType());
                        backupForwardAndRewindReportJpaController.create(tmp);
                        newForwardAndRewindReportList.add(tmp);
                    }
                    else
                    {
                        newForwardAndRewindReportList.add(backupForwardAndRewindReportJpaController.findForwardAndRewindReport(f.getReportID()));
                    }
                }
                
                // Return it all to person...
                p.setSecurityRoleList(newSecurityRoleList);
                p.setApplicationList(newApplicationList);
                p.setCommitteeMeetingList(newCommitteeMeetingList);
                p.setCv(newCv);
                p.setDeclineReportList(newDeclineReportList);
                p.setEndorsementList(newEndorsementList);
                p.setApplicationReviewRequestList(newApplicationReviewRequestList);
                p.setResearchFellowInformation(newResearchFellowInformation);
                p.setRefereeReportList(newRefereeReportList);
                p.setEmployeeInformation(newEmployeeInformation);
                p.setNotificationList(newNotificationList);
                p.setNotificationList1(newNotificationList1);
                p.setAmmendRequestList(newAmmendRequestList);
                p.setAuditLogList(newAuditLogList);
                p.setRecommendationReportList(newRecommendationReportList);
                p.setFundingReportList(newFundingReportList);
                p.setApplicationList1(newApplicationList1);
                p.setApplicationList2(newApplicationList2);
                p.setAddressLine1(newAddressLine1);
                p.setMinuteCommentList(newMinuteCommentList);
                p.setEligiblityReportList(newEligiblityReportList);
                p.setForwardAndRewindReportList(newForwardAndRewindReportList);
                
                // Edit person
                backupPersonJpaController.edit(p);
            }
            catch (Exception ex)
            {
                backUpFailed(p, ex);
            }
        }
        
        for(Application oldApplication: workingApplicationJpaController.findApplicationEntities())
        {
            Application a;
            if(backupApplicationJpaController.findApplication(oldApplication.getApplicationID()) == null)
            {
                a = new Application(oldApplication.getApplicationID());
            }
            else
            {
                a = backupApplicationJpaController.findApplication(oldApplication.getApplicationID());
            }
            // Copy data over...
            
            a.setType(oldApplication.getType());
            a.setStatus(oldApplication.getStatus());
            a.setFundingType(oldApplication.getFundingType());
            a.setTimestamp(oldApplication.getTimestamp());
            a.setSubmissionDate(oldApplication.getSubmissionDate());
            a.setFinalisationDate(oldApplication.getFinalisationDate());
            a.setStartDate(oldApplication.getStartDate());
            a.setEndDate(oldApplication.getEndDate());
            a.setProjectTitle(oldApplication.getProjectTitle());
            a.setInformation(oldApplication.getInformation());
            
            // Release all other entities from Person...                        
            List<Person> personList = oldApplication.getPersonList();
            
            List<CommitteeMeeting> committeeMeetingList = oldApplication.getCommitteeMeetingList();
            
            DeclineReport declineReport = oldApplication.getDeclineReport();
            
            Endorsement endorsement = oldApplication.getEndorsement();
            
            List<ApplicationReviewRequest> applicationReviewRequestList  = oldApplication.getApplicationReviewRequestList();
            
            List<RefereeReport> refereeReportList = oldApplication.getRefereeReportList();
            
            List<AmmendRequest> ammendRequestList = oldApplication.getAmmendRequestList();
            
            RecommendationReport recommendationReport = oldApplication.getRecommendationReport();
                   
            FundingReport fundingReport = oldApplication.getFundingReport();
            
            Person fellow = oldApplication.getFellow();
            
            Person grantHolder = oldApplication.getGrantHolder();
            
            List<ProgressReport> progressReportList = oldApplication.getProgressReportList();
            
            EligiblityReport eligiblityReport = oldApplication.getEligiblityReport();
            
            List<ForwardAndRewindReport> forwardAndRewindReportList= oldApplication.getForwardAndRewindReportList();
            
            try
            {
                if(backupApplicationJpaController.findApplication(a.getApplicationID()) == null)
                {
                    backupApplicationJpaController.create(a);
                }
                
                List<Person> newPersonList = new ArrayList<Person>();
                for(Person p: personList)
                {
                    if(backupPersonJpaController.findPerson(p.getSystemID()) == null)
                    {
                        Person tmp = new Person(p.getSystemID());
                        
                        tmp.setPassword(p.getPassword());
                        tmp.setTitle(p.getTitle());
                        tmp.setFullName(p.getFullName());
                        tmp.setSurname(p.getSurname());
                        tmp.setEmail(p.getEmail());
                        tmp.setTelephoneNumber(p.getTelephoneNumber());
                        tmp.setWorkNumber(p.getWorkNumber());
                        tmp.setFaxNumber(p.getFaxNumber());
                        tmp.setCellphoneNumber(p.getCellphoneNumber());
                        tmp.setUpEmployee(p.getUpEmployee());
                        tmp.setAccountStatus(p.getAccountStatus());
                    
                        backupPersonJpaController.create(tmp);
                        newPersonList.add(tmp);
                    }
                    else
                    {
                        newPersonList.add(backupPersonJpaController.findPerson(p.getSystemID()));
                    }
                }
                
                List<CommitteeMeeting> newCommitteeMeetingList = new ArrayList<CommitteeMeeting>();
                for(CommitteeMeeting c: committeeMeetingList)
                {
                    if(backupCommitteeMeetingJpaController.findCommitteeMeeting(c.getMeetingID()) == null)
                    {
                        CommitteeMeeting tmp = new CommitteeMeeting(c.getMeetingID());
                        backupCommitteeMeetingJpaController.create(tmp);
                        newCommitteeMeetingList.add(tmp);
                    }
                    else
                    {
                        newCommitteeMeetingList.add(backupCommitteeMeetingJpaController.findCommitteeMeeting(c.getMeetingID()));
                    }
                }
                
                DeclineReport newDeclineReport;
                if(declineReport == null)
                {
                    newDeclineReport = null;
                }
                else if(backupDeclineReportJpaController.findDeclineReport(declineReport.getReportID()) == null)
                {
                    DeclineReport tmp = new DeclineReport(declineReport.getReportID());
                    tmp.setTimestamp(declineReport.getTimestamp());
                    backupDeclineReportJpaController.create(tmp);
                    newDeclineReport = tmp;
                }
                else
                {
                    newDeclineReport = backupDeclineReportJpaController.findDeclineReport(declineReport.getReportID());
                }
                
                Endorsement newEndorsement;
                if(endorsement == null)
                {
                    newEndorsement = null;
                }
                else if(backupEndorsementJpaController.findEndorsement(endorsement.getEndorsementID()) == null)
                {
                    Endorsement tmp = new Endorsement(endorsement.getEndorsementID());
                    
                    tmp.setTimestamp(endorsement.getTimestamp());
                    tmp.setRank(endorsement.getRank());
                    tmp.setMotivation(endorsement.getMotivation());
                    
                    backupEndorsementJpaController.create(tmp);
                    newEndorsement = tmp;
                }
                else
                {
                    newEndorsement = backupEndorsementJpaController.findEndorsement(endorsement.getEndorsementID());
                }
                
                List<ApplicationReviewRequest> newApplicationReviewRequestList = new ArrayList<ApplicationReviewRequest>();
                for(ApplicationReviewRequest aR: applicationReviewRequestList)
                {
                    if(backupApplicationReviewRequestJpaController.findApplicationReviewRequest(aR.getApplicationReviewRequestPK()) == null)
                    {
                        ApplicationReviewRequest tmp = new ApplicationReviewRequest(aR.getApplicationReviewRequestPK());
                        backupApplicationReviewRequestJpaController.create(tmp);
                        newApplicationReviewRequestList.add(tmp);
                    }
                    else
                    {
                        newApplicationReviewRequestList.add(backupApplicationReviewRequestJpaController.findApplicationReviewRequest(aR.getApplicationReviewRequestPK()));
                    }
                }
                
                List<RefereeReport> newRefereeReportList = new ArrayList<RefereeReport>();
                for(RefereeReport r: refereeReportList)
                {
                    if(backupRefereeReportJpaController.findRefereeReport(r.getReportID()) == null)
                    {
                        RefereeReport tmp = new RefereeReport(r.getReportID());
                        tmp.setTimestamp(r.getTimestamp());
                        tmp.setContent(r.getContent());
                        backupRefereeReportJpaController.create(tmp);
                        
                        newRefereeReportList.add(tmp);
                    }
                    else
                    {
                        newRefereeReportList.add(backupRefereeReportJpaController.findRefereeReport(r.getReportID()));
                    }
                }
                
                List<AmmendRequest> newAmmendRequestList = new ArrayList<AmmendRequest>();
                for(AmmendRequest aR: ammendRequestList)
                {
                    if(backupAmmendRequestJpaController.findAmmendRequest(aR.getRequestID()) == null)
                    {
                        AmmendRequest tmp = new AmmendRequest(aR.getRequestID());
                        tmp.setTimestamp(aR.getTimestamp());
                        backupAmmendRequestJpaController.create(tmp);
                        newAmmendRequestList.add(tmp);
                    }
                    else
                    {
                        newAmmendRequestList.add(backupAmmendRequestJpaController.findAmmendRequest(aR.getRequestID()));
                    }
                }
                
                RecommendationReport newRecommendationReport;
                if(recommendationReport == null)
                {
                    newRecommendationReport = null;
                }
                else if(backupRecommendationReportJpaController.findRecommendationReport(recommendationReport.getReportID()) == null)
                {
                    RecommendationReport tmp = new RecommendationReport(recommendationReport.getReportID());
                    tmp.setTimestamp(recommendationReport.getTimestamp());
                    tmp.setContent(recommendationReport.getContent());
                    backupRecommendationReportJpaController.create(tmp);
                    newRecommendationReport = tmp;
                }
                else
                {
                    newRecommendationReport = backupRecommendationReportJpaController.findRecommendationReport(recommendationReport.getReportID());
                }
                
                FundingReport newFundingReport;
                if(fundingReport == null)
                {
                    newFundingReport = null;
                }
                else if(backupFundingReportJpaController.findFundingReport(fundingReport.getReportID()) == null)
                {
                    FundingReport tmp = new FundingReport(fundingReport.getReportID());
                    tmp.setTimestamp(fundingReport.getTimestamp());
                    backupFundingReportJpaController.create(tmp);
                    newFundingReport = tmp;
                }
                else
                {
                    newFundingReport = backupFundingReportJpaController.findFundingReport(fundingReport.getReportID());
                }
                
                Person newFellow;
                if(fellow == null)
                {
                    newFellow = null;
                }
                else if(backupPersonJpaController.findPerson(fellow.getSystemID()) == null)
                {
                    Person tmp = new Person(fellow.getSystemID());
                    
                    tmp.setPassword(fellow.getPassword());
                    tmp.setTitle(fellow.getTitle());
                    tmp.setFullName(fellow.getFullName());
                    tmp.setSurname(fellow.getSurname());
                    tmp.setEmail(fellow.getEmail());
                    tmp.setTelephoneNumber(fellow.getTelephoneNumber());
                    tmp.setWorkNumber(fellow.getWorkNumber());
                    tmp.setFaxNumber(fellow.getFaxNumber());
                    tmp.setCellphoneNumber(fellow.getCellphoneNumber());
                    tmp.setUpEmployee(fellow.getUpEmployee());
                    tmp.setAccountStatus(fellow.getAccountStatus());
                            
                    backupPersonJpaController.create(tmp);
                    newFellow = tmp;
                }
                else
                {
                    newFellow = backupPersonJpaController.findPerson(fellow.getSystemID());
                }
                
                Person newGrantHolder;
                if(grantHolder == null)
                {
                    newGrantHolder = null;
                }
                else if(backupPersonJpaController.findPerson(grantHolder.getSystemID()) == null)
                {
                    Person tmp = new Person(grantHolder.getSystemID());
                    
                    tmp.setPassword(grantHolder.getPassword());
                    tmp.setTitle(grantHolder.getTitle());
                    tmp.setFullName(grantHolder.getFullName());
                    tmp.setSurname(grantHolder.getSurname());
                    tmp.setEmail(grantHolder.getEmail());
                    tmp.setTelephoneNumber(grantHolder.getTelephoneNumber());
                    tmp.setWorkNumber(grantHolder.getWorkNumber());
                    tmp.setFaxNumber(grantHolder.getFaxNumber());
                    tmp.setCellphoneNumber(grantHolder.getCellphoneNumber());
                    tmp.setUpEmployee(grantHolder.getUpEmployee());
                    tmp.setAccountStatus(grantHolder.getAccountStatus());
                            
                    backupPersonJpaController.create(tmp);
                    newGrantHolder = tmp;
                }
                else
                {
                    newGrantHolder = backupPersonJpaController.findPerson(grantHolder.getSystemID());
                }
                
                List<ProgressReport> newProgressReportList = new ArrayList<ProgressReport>();
                for(ProgressReport p: progressReportList)
                {
                    if(backupProgressReportJpaController.findProgressReport(p.getReportID()) == null)
                    {
                        ProgressReport tmp = new ProgressReport(p.getReportID());
                        tmp.setTimestamp(p.getTimestamp());
                        tmp.setContent(p.getContent());
                        backupProgressReportJpaController.create(tmp);
                        newProgressReportList.add(tmp);
                    }
                    else
                    {
                        newProgressReportList.add(backupProgressReportJpaController.findProgressReport(p.getReportID()));
                    }
                }
                
                EligiblityReport newEligiblityReport;
                if(eligiblityReport == null)
                {
                    newEligiblityReport = null;
                }
                else if(backupEligiblityReportJpaController.findEligiblityReport(eligiblityReport.getReportID()) == null)
                {
                    EligiblityReport tmp = new EligiblityReport(eligiblityReport.getReportID());
                    tmp.setEligiblityCheckDate(eligiblityReport.getEligiblityCheckDate());
                    backupEligiblityReportJpaController.create(tmp);
                    newEligiblityReport = tmp;
                }
                else
                {
                    newEligiblityReport = backupEligiblityReportJpaController.findEligiblityReport(eligiblityReport.getReportID());
                }
                
                List<ForwardAndRewindReport> newForwardAndRewindReportList = new ArrayList<ForwardAndRewindReport>();
                for(ForwardAndRewindReport f: forwardAndRewindReportList)
                {
                    if(backupForwardAndRewindReportJpaController.findForwardAndRewindReport(f.getReportID()) == null)
                    {
                        ForwardAndRewindReport tmp = new ForwardAndRewindReport(f.getReportID());
                        tmp.setTimestamp(f.getTimestamp());
                        tmp.setType(f.getType());
                        backupForwardAndRewindReportJpaController.create(tmp);
                        newForwardAndRewindReportList.add(tmp);
                    }
                    else
                    {
                        newForwardAndRewindReportList.add(backupForwardAndRewindReportJpaController.findForwardAndRewindReport(f.getReportID()));
                    }
                }
                
                // Return it all to person...
                a.setPersonList(newPersonList);
                a.setCommitteeMeetingList(newCommitteeMeetingList);
                a.setDeclineReport(newDeclineReport);
                a.setEndorsement(newEndorsement);
                a.setApplicationReviewRequestList(newApplicationReviewRequestList);
                a.setRefereeReportList(newRefereeReportList);
                a.setAmmendRequestList(newAmmendRequestList);
                a.setRecommendationReport(newRecommendationReport);
                a.setEligiblityReport(newEligiblityReport);
                a.setForwardAndRewindReportList(newForwardAndRewindReportList);
                
                // Edit person
                backupApplicationJpaController.edit(a);
            }
            catch (Exception ex)
            {
                backUpFailed(a, ex);
            }
        }
    }    
    
    //@Schedule(dayOfWeek="Sat", hour="2", info = "Daily backup of the database.")
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @Override
    public void archiveOldInformation(Session session)
    {
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @Override
    public void retrieveArchievedInformation(Session session)
    {
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @Override
    public void restoreBackupToWorkingDatabase(Session session)
    {
        // Setup Working DAOs...
        DAOFactory daoFactoryWorking = getDAOFactoryWorking();
        AcademicQualificationJpaController workingAcademicQualificationJpaController = daoFactoryWorking.createAcademicQualificationDAO();
        AddressJpaController workingAddressJpaController = daoFactoryWorking.createAddressDAO();
        AmmendRequestJpaController workingAmmendRequestJpaController = daoFactoryWorking.createAmmendRequestDAO();
        ApplicationJpaController workingApplicationJpaController = daoFactoryWorking.createApplicationDAO();
        ApplicationReviewRequestJpaController workingApplicationReviewRequestJpaController = daoFactoryWorking.createApplicationReviewRequestDAO();
        AuditLogJpaController workingAuditLogJpaController = daoFactoryWorking.createAuditLogDAO();
        CommitteeMeetingJpaController workingCommitteeMeetingJpaController = daoFactoryWorking.createCommitteeMeetingDAO();
        CvJpaController workingCvJpaController = daoFactoryWorking.createCvDAO();
        DeclineReportJpaController workingDeclineReportJpaController = daoFactoryWorking.createDeclineReportDAO();
        DepartmentJpaController workingDepartmentJpaController = daoFactoryWorking.createDepartmentDAO();
        EligiblityReportJpaController workingEligiblityReportJpaController = daoFactoryWorking.createEligiblityReportDAO();
        EmployeeInformationJpaController workingEmployeeInformationJpaController = daoFactoryWorking.createEmployeeInformationDAO();
        EndorsementJpaController workingEndorsementJpaController = daoFactoryWorking.createEndorsementDAO();
        ExperienceJpaController workingExperienceJpaController = daoFactoryWorking.createExperienceDAO();
        FacultyJpaController workingFacultyJpaController = daoFactoryWorking.createFacultyDAO();
        ForwardAndRewindReportJpaController workingForwardAndRewindReportJpaController = daoFactoryWorking.createForwardAndRewindReportDAO();
        FundingCostJpaController workingFundingCostJpaController = daoFactoryWorking.createFundingCostJpaController();
        FundingReportJpaController workingFundingReportJpaController = daoFactoryWorking.createFundingReportDAO();
        InstitutionJpaController workingInstitutionJpaController = daoFactoryWorking.createInstitutionDAO();
        MinuteCommentJpaController workingMinuteCommentJpaController = daoFactoryWorking.createMinuteCommentDAO();
        NotificationJpaController workingNotificationJpaController = daoFactoryWorking.createNotificationDAO();
        PersonJpaController workingPersonJpaController = daoFactoryWorking.createPersonDAO();
        ProgressReportJpaController workingProgressReportJpaController = daoFactoryWorking.createProgressReportDAO();
        RecommendationReportJpaController workingRecommendationReportJpaController = daoFactoryWorking.createRecommendationReportDAO();
        RefereeReportJpaController workingRefereeReportJpaController = daoFactoryWorking.createRefereeReportDAO();
        ResearchFellowInformationJpaController workingResearchFellowInformationJpaController = daoFactoryWorking.createResearchFellowInformationDAO();
        SecurityRoleJpaController workingSecurityRoleJpaController = daoFactoryWorking.createSecurityRoleJpaController();  
        
        DAOFactory daoFactoryBackup = getDAOFactoryBackup();
        AcademicQualificationJpaController backupAcademicQualificationJpaController = daoFactoryBackup.createAcademicQualificationDAO();
        AddressJpaController backupAddressJpaController = daoFactoryBackup.createAddressDAO();
        AmmendRequestJpaController backupAmmendRequestJpaController = daoFactoryBackup.createAmmendRequestDAO();
        ApplicationJpaController backupApplicationJpaController = daoFactoryBackup.createApplicationDAO();
        ApplicationReviewRequestJpaController backupApplicationReviewRequestJpaController = daoFactoryBackup.createApplicationReviewRequestDAO();
        AuditLogJpaController backupAuditLogJpaController = daoFactoryBackup.createAuditLogDAO();
        CommitteeMeetingJpaController backupCommitteeMeetingJpaController = daoFactoryBackup.createCommitteeMeetingDAO();
        CvJpaController backupCvJpaController = daoFactoryBackup.createCvDAO();
        DeclineReportJpaController backupDeclineReportJpaController = daoFactoryBackup.createDeclineReportDAO();
        DepartmentJpaController backupDepartmentJpaController = daoFactoryBackup.createDepartmentDAO();
        EligiblityReportJpaController backupEligiblityReportJpaController = daoFactoryBackup.createEligiblityReportDAO();
        EmployeeInformationJpaController backupEmployeeInformationJpaController = daoFactoryBackup.createEmployeeInformationDAO();
        EndorsementJpaController backupEndorsementJpaController = daoFactoryBackup.createEndorsementDAO();
        ExperienceJpaController backupExperienceJpaController = daoFactoryBackup.createExperienceDAO();
        FacultyJpaController backupFacultyJpaController = daoFactoryBackup.createFacultyDAO();
        ForwardAndRewindReportJpaController backupForwardAndRewindReportJpaController = daoFactoryBackup.createForwardAndRewindReportDAO();
        FundingCostJpaController backupFundingCostJpaController = daoFactoryBackup.createFundingCostJpaController();
        FundingReportJpaController backupFundingReportJpaController = daoFactoryBackup.createFundingReportDAO();
        InstitutionJpaController backupInstitutionJpaController = daoFactoryBackup.createInstitutionDAO();
        MinuteCommentJpaController backupMinuteCommentJpaController = daoFactoryBackup.createMinuteCommentDAO();
        NotificationJpaController backupNotificationJpaController = daoFactoryBackup.createNotificationDAO();
        PersonJpaController backupPersonJpaController = daoFactoryBackup.createPersonDAO();
        ProgressReportJpaController backupProgressReportJpaController = daoFactoryBackup.createProgressReportDAO();
        RecommendationReportJpaController backupRecommendationReportJpaController = daoFactoryBackup.createRecommendationReportDAO();
        RefereeReportJpaController backupRefereeReportJpaController = daoFactoryBackup.createRefereeReportDAO();
        ResearchFellowInformationJpaController backupResearchFellowInformationJpaController = daoFactoryBackup.createResearchFellowInformationDAO();
        SecurityRoleJpaController backupSecurityRoleJpaController = daoFactoryBackup.createSecurityRoleJpaController();  
        
        
    }
    
    private void backUpFailed(Object object, Exception ex)
    {
        System.out.println("Failed to back up: " + object.toString());
        ex.printStackTrace();
        // TODO: Log it in a more suitable manner...
    }
    
    private void restoreFailed(Object object)
    {
        System.out.println("Failed to restore: " + object.toString());
        // TODO: Log it in a more suitable manner...
    }
}
