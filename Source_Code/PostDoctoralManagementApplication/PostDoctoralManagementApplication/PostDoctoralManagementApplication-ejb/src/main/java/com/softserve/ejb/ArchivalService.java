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
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
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
    
    protected AcademicQualificationJpaController getAcademicQualificationJpaController(EntityManagerFactory emf)
    {
        return new AcademicQualificationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected AddressJpaController getAddressJpaController(EntityManagerFactory emf)
    {
        return new AddressJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected AmmendRequestJpaController getAmmendRequestJpaController(EntityManagerFactory emf)
    {
        return new AmmendRequestJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ApplicationJpaController getApplicationJpaController(EntityManagerFactory emf)
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ApplicationReviewRequestJpaController getApplicationReviewRequestJpaController(EntityManagerFactory emf)
    {
        return new ApplicationReviewRequestJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected AuditLogJpaController getAuditLogJpaController(EntityManagerFactory emf)
    {
        return new AuditLogJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected CommitteeMeetingJpaController getCommitteeMeetingJpaController(EntityManagerFactory emf)
    {
        return new CommitteeMeetingJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected CvJpaController getCvJpaController(EntityManagerFactory emf)
    {
        return new CvJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected DeclineReportJpaController getDeclineReportJpaController(EntityManagerFactory emf)
    {
        return new DeclineReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected DepartmentJpaController getDepartmentJpaController(EntityManagerFactory emf)
    {
        return new DepartmentJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected EligiblityReportJpaController getEligiblityReportJpaController(EntityManagerFactory emf)
    {
        return new EligiblityReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected EmployeeInformationJpaController getEmployeeInformationJpaController(EntityManagerFactory emf)
    {
        return new EmployeeInformationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected EndorsementJpaController getEndorsementJpaController(EntityManagerFactory emf)
    {
        return new EndorsementJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ExperienceJpaController getExperienceJpaController(EntityManagerFactory emf)
    {
        return new ExperienceJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected FacultyJpaController getFacultyJpaController(EntityManagerFactory emf)
    {
        return new FacultyJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ForwardAndRewindReportJpaController getForwardAndRewindReportJpaController(EntityManagerFactory emf)
    {
        return new ForwardAndRewindReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected FundingCostJpaController getFundingCostJpaController(EntityManagerFactory emf)
    {
        return new FundingCostJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected FundingReportJpaController getFundingReportJpaController(EntityManagerFactory emf)
    {
        return new FundingReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected InstitutionJpaController getInstitutionJpaController(EntityManagerFactory emf)
    {
        return new InstitutionJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected MinuteCommentJpaController getMinuteCommentJpaController(EntityManagerFactory emf)
    {
        return new MinuteCommentJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected NotificationJpaController getNotificationJpaController(EntityManagerFactory emf)
    {
        return new NotificationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected PersonJpaController getPersonJpaController(EntityManagerFactory emf)
    {
        return new PersonJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ProgressReportJpaController getProgressReportJpaController(EntityManagerFactory emf)
    {
        return new ProgressReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected RecommendationReportJpaController getRecommendationReportJpaController(EntityManagerFactory emf)
    {
        return new RecommendationReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected RefereeReportJpaController getRefereeReportJpaController(EntityManagerFactory emf)
    {
        return new RefereeReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ResearchFellowInformationJpaController getResearchFellowInformationJpaController(EntityManagerFactory emf)
    {
        return new ResearchFellowInformationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected SecurityRoleJpaController getSecurityRoleJpaController(EntityManagerFactory emf)
    {
        return new SecurityRoleJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    //@Schedule(dayOfWeek="*", hour="2", info = "Daily backup of the database.")
    @Override
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    public void backupDatabase(Session session)
    {
        System.out.println("Running Backup...");
        // Setup Working DAOs...
        AcademicQualificationJpaController workingAcademicQualificationJpaController = getAcademicQualificationJpaController(emfWorking);
        AddressJpaController workingAddressJpaController = getAddressJpaController(emfWorking);
        AmmendRequestJpaController workingAmmendRequestJpaController = getAmmendRequestJpaController(emfWorking);
        ApplicationJpaController workingApplicationJpaController = getApplicationJpaController(emfWorking);
        ApplicationReviewRequestJpaController workingApplicationReviewRequestJpaController = getApplicationReviewRequestJpaController(emfWorking);
        AuditLogJpaController workingAuditLogJpaController = getAuditLogJpaController(emfWorking);
        CommitteeMeetingJpaController workingCommitteeMeetingJpaController = getCommitteeMeetingJpaController(emfWorking);
        CvJpaController workingCvJpaController = getCvJpaController(emfWorking);
        DeclineReportJpaController workingDeclineReportJpaController = getDeclineReportJpaController(emfWorking);
        DepartmentJpaController workingDepartmentJpaController = getDepartmentJpaController(emfWorking);
        EligiblityReportJpaController workingEligiblityReportJpaController = getEligiblityReportJpaController(emfWorking);
        EmployeeInformationJpaController workingEmployeeInformationJpaController = getEmployeeInformationJpaController(emfWorking);
        EndorsementJpaController workingEndorsementJpaController = getEndorsementJpaController(emfWorking);
        ExperienceJpaController workingExperienceJpaController = getExperienceJpaController(emfWorking);
        FacultyJpaController workingFacultyJpaController = getFacultyJpaController(emfWorking);
        ForwardAndRewindReportJpaController workingForwardAndRewindReportJpaController = getForwardAndRewindReportJpaController(emfWorking);
        FundingCostJpaController workingFundingCostJpaController = getFundingCostJpaController(emfWorking);
        FundingReportJpaController workingFundingReportJpaController = getFundingReportJpaController(emfWorking);
        InstitutionJpaController workingInstitutionJpaController = getInstitutionJpaController(emfWorking);
        MinuteCommentJpaController workingMinuteCommentJpaController = getMinuteCommentJpaController(emfWorking);
        NotificationJpaController workingNotificationJpaController = getNotificationJpaController(emfWorking);
        PersonJpaController workingPersonJpaController = getPersonJpaController(emfWorking);
        ProgressReportJpaController workingProgressReportJpaController = getProgressReportJpaController(emfWorking);
        RecommendationReportJpaController workingRecommendationReportJpaController = getRecommendationReportJpaController(emfWorking);
        RefereeReportJpaController workingRefereeReportJpaController = getRefereeReportJpaController(emfWorking);
        ResearchFellowInformationJpaController workingResearchFellowInformationJpaController = getResearchFellowInformationJpaController(emfWorking);
        SecurityRoleJpaController workingSecurityRoleJpaController = getSecurityRoleJpaController(emfWorking);  
        
        // Setup Back DAOs...
        AcademicQualificationJpaController backupAcademicQualificationJpaController = getAcademicQualificationJpaController(emfBackup);
        AddressJpaController backupAddressJpaController = getAddressJpaController(emfBackup);
        AmmendRequestJpaController backupAmmendRequestJpaController = getAmmendRequestJpaController(emfBackup);
        ApplicationJpaController backupApplicationJpaController = getApplicationJpaController(emfBackup);
        ApplicationReviewRequestJpaController backupApplicationReviewRequestJpaController = getApplicationReviewRequestJpaController(emfBackup);
        AuditLogJpaController backupAuditLogJpaController = getAuditLogJpaController(emfBackup);
        CommitteeMeetingJpaController backupCommitteeMeetingJpaController = getCommitteeMeetingJpaController(emfBackup);
        CvJpaController backupCvJpaController = getCvJpaController(emfBackup);
        DeclineReportJpaController backupDeclineReportJpaController = getDeclineReportJpaController(emfBackup);
        DepartmentJpaController backupDepartmentJpaController = getDepartmentJpaController(emfBackup);
        EligiblityReportJpaController backupEligiblityReportJpaController = getEligiblityReportJpaController(emfBackup);
        EmployeeInformationJpaController backupEmployeeInformationJpaController = getEmployeeInformationJpaController(emfBackup);
        EndorsementJpaController backupEndorsementJpaController = getEndorsementJpaController(emfBackup);
        ExperienceJpaController backupExperienceJpaController = getExperienceJpaController(emfBackup);
        FacultyJpaController backupFacultyJpaController = getFacultyJpaController(emfBackup);
        ForwardAndRewindReportJpaController backupForwardAndRewindReportJpaController = getForwardAndRewindReportJpaController(emfBackup);
        FundingCostJpaController backupFundingCostJpaController = getFundingCostJpaController(emfBackup);
        FundingReportJpaController backupFundingReportJpaController = getFundingReportJpaController(emfBackup);
        InstitutionJpaController backupInstitutionJpaController = getInstitutionJpaController(emfBackup);
        MinuteCommentJpaController backupMinuteCommentJpaController = getMinuteCommentJpaController(emfBackup);
        NotificationJpaController backupNotificationJpaController = getNotificationJpaController(emfBackup);
        PersonJpaController backupPersonJpaController = getPersonJpaController(emfBackup);
        ProgressReportJpaController backupProgressReportJpaController = getProgressReportJpaController(emfBackup);
        RecommendationReportJpaController backupRecommendationReportJpaController = getRecommendationReportJpaController(emfBackup);
        RefereeReportJpaController backupRefereeReportJpaController = getRefereeReportJpaController(emfBackup);
        ResearchFellowInformationJpaController backupResearchFellowInformationJpaController = getResearchFellowInformationJpaController(emfBackup);
        SecurityRoleJpaController backupSecurityRoleJpaController = getSecurityRoleJpaController(emfBackup);
        
        for(Person oldPerson: workingPersonJpaController.findPersonEntities())
        {
            Person p;
            if(backupPersonJpaController.findPerson(oldPerson.getSystemID()) == null)
            {
                p = new Person(oldPerson.getSystemID());
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
            p.setSecurityRoleList(new ArrayList<SecurityRole>());
            
            List<Application> applicationList = oldPerson.getApplicationList();
            p.setApplicationList(new ArrayList<Application>());
            
            List<CommitteeMeeting> committeeMeetingList = oldPerson.getCommitteeMeetingList();
            p.setCommitteeMeetingList(new ArrayList<CommitteeMeeting>());
            
            List<DeclineReport> declineReportList = oldPerson.getDeclineReportList();
            p.setDeclineReportList(new ArrayList<DeclineReport>());
            
            List<Endorsement> endorsementList = oldPerson.getEndorsementList();
            p.setEndorsementList(new ArrayList<Endorsement>());
            
            List<ApplicationReviewRequest> applicationReviewRequestList  = oldPerson.getApplicationReviewRequestList();
            p.setApplicationReviewRequestList(new ArrayList<ApplicationReviewRequest>());
            
            ResearchFellowInformation researchFellowInformation = oldPerson.getResearchFellowInformation();
            p.setResearchFellowInformation(null);
            
            List<RefereeReport> refereeReportList = oldPerson.getRefereeReportList();
            p.setRefereeReportList(new ArrayList<RefereeReport>());
            
            EmployeeInformation employeeInformation = oldPerson.getEmployeeInformation();
            p.setEmployeeInformation(null);
            
            List<Notification> notificationList = oldPerson.getNotificationList();
            p.setNotificationList(new ArrayList<Notification>());
            
            List<Notification> notificationList1 = oldPerson.getNotificationList1();
            p.setNotificationList1(new ArrayList<Notification>());
            
            List<AmmendRequest> ammendRequestList = oldPerson.getAmmendRequestList();
            p.setAmmendRequestList(new ArrayList<AmmendRequest>());
            
            List<AuditLog> auditLogList = oldPerson.getAuditLogList();
            p.setAuditLogList(new ArrayList<AuditLog>());
            
            List<RecommendationReport> recommendationReportList = oldPerson.getRecommendationReportList();
            p.setRecommendationReportList(new ArrayList<RecommendationReport>());
            
            List<FundingReport> fundingReportList = oldPerson.getFundingReportList();
            p.setFundingReportList(new ArrayList<FundingReport>());
            
            Cv cv = oldPerson.getCv();
            p.setCv(null);
            
            List<Application> applicationList1 = oldPerson.getApplicationList1();
            p.setApplicationList1(new ArrayList<Application>());
            
            List<Application> applicationList2 = oldPerson.getApplicationList2();
            p.setApplicationList2(new ArrayList<Application>());
            
            Address addressLine1 = oldPerson.getAddressLine1();
            p.setAddressLine1(null);
            
            List<MinuteComment> minuteCommentList = oldPerson.getMinuteCommentList();
            p.setMinuteCommentList(new ArrayList<MinuteComment>());
            
            List<EligiblityReport> eligiblityReportList = oldPerson.getEligiblityReportList();
            p.setEligiblityReportList(new ArrayList<EligiblityReport>());
            
            List<ForwardAndRewindReport> forwardAndRewindReportList= oldPerson.getForwardAndRewindReportList();
            p.setForwardAndRewindReportList(new ArrayList<ForwardAndRewindReport>());
            
            try
            {
                if(backupPersonJpaController.findPerson(p.getSystemID()) == null)
                {
                    backupPersonJpaController.create(p);
                }
                else
                {
                    backupPersonJpaController.edit(p);
                }
                
                // Return entities to Person while creating them if need be...
                List<SecurityRole> newSecurityRoleList = new ArrayList<SecurityRole>();
                for(SecurityRole s: securityRoleList)
                {
                    if(backupSecurityRoleJpaController.findSecurityRole(s.getRoleID()) == null)
                    {
                        SecurityRole tmp = new SecurityRole(s.getRoleID());
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
        
        // Enter all entities not related to Person
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
        AcademicQualificationJpaController workingAcademicQualificationJpaController = getAcademicQualificationJpaController(emfWorking);
        AddressJpaController workingAddressJpaController = getAddressJpaController(emfWorking);
        AmmendRequestJpaController workingAmmendRequestJpaController = getAmmendRequestJpaController(emfWorking);
        ApplicationJpaController workingApplicationJpaController = getApplicationJpaController(emfWorking);
        ApplicationReviewRequestJpaController workingApplicationReviewRequestJpaController = getApplicationReviewRequestJpaController(emfWorking);
        AuditLogJpaController workingAuditLogJpaController = getAuditLogJpaController(emfWorking);
        CommitteeMeetingJpaController workingCommitteeMeetingJpaController = getCommitteeMeetingJpaController(emfWorking);
        CvJpaController workingCvJpaController = getCvJpaController(emfWorking);
        DeclineReportJpaController workingDeclineReportJpaController = getDeclineReportJpaController(emfWorking);
        DepartmentJpaController workingDepartmentJpaController = getDepartmentJpaController(emfWorking);
        EligiblityReportJpaController workingEligiblityReportJpaController = getEligiblityReportJpaController(emfWorking);
        EmployeeInformationJpaController workingEmployeeInformationJpaController = getEmployeeInformationJpaController(emfWorking);
        EndorsementJpaController workingEndorsementJpaController = getEndorsementJpaController(emfWorking);
        ExperienceJpaController workingExperienceJpaController = getExperienceJpaController(emfWorking);
        FacultyJpaController workingFacultyJpaController = getFacultyJpaController(emfWorking);
        ForwardAndRewindReportJpaController workingForwardAndRewindReportJpaController = getForwardAndRewindReportJpaController(emfWorking);
        FundingCostJpaController workingFundingCostJpaController = getFundingCostJpaController(emfWorking);
        FundingReportJpaController workingFundingReportJpaController = getFundingReportJpaController(emfWorking);
        InstitutionJpaController workingInstitutionJpaController = getInstitutionJpaController(emfWorking);
        MinuteCommentJpaController workingMinuteCommentJpaController = getMinuteCommentJpaController(emfWorking);
        NotificationJpaController workingNotificationJpaController = getNotificationJpaController(emfWorking);
        PersonJpaController workingPersonJpaController = getPersonJpaController(emfWorking);
        ProgressReportJpaController workingProgressReportJpaController = getProgressReportJpaController(emfWorking);
        RecommendationReportJpaController workingRecommendationReportJpaController = getRecommendationReportJpaController(emfWorking);
        RefereeReportJpaController workingRefereeReportJpaController = getRefereeReportJpaController(emfWorking);
        ResearchFellowInformationJpaController workingResearchFellowInformationJpaController = getResearchFellowInformationJpaController(emfWorking);
        SecurityRoleJpaController workingSecurityRoleJpaController = getSecurityRoleJpaController(emfWorking);  
        
        // Setup Back DAOs...
        AcademicQualificationJpaController backupAcademicQualificationJpaController = getAcademicQualificationJpaController(emfBackup);
        AddressJpaController backupAddressJpaController = getAddressJpaController(emfBackup);
        AmmendRequestJpaController backupAmmendRequestJpaController = getAmmendRequestJpaController(emfBackup);
        ApplicationJpaController backupApplicationJpaController = getApplicationJpaController(emfBackup);
        ApplicationReviewRequestJpaController backupApplicationReviewRequestJpaController = getApplicationReviewRequestJpaController(emfBackup);
        AuditLogJpaController backupAuditLogJpaController = getAuditLogJpaController(emfBackup);
        CommitteeMeetingJpaController backupCommitteeMeetingJpaController = getCommitteeMeetingJpaController(emfBackup);
        CvJpaController backupCvJpaController = getCvJpaController(emfBackup);
        DeclineReportJpaController backupDeclineReportJpaController = getDeclineReportJpaController(emfBackup);
        DepartmentJpaController backupDepartmentJpaController = getDepartmentJpaController(emfBackup);
        EligiblityReportJpaController backupEligiblityReportJpaController = getEligiblityReportJpaController(emfBackup);
        EmployeeInformationJpaController backupEmployeeInformationJpaController = getEmployeeInformationJpaController(emfBackup);
        EndorsementJpaController backupEndorsementJpaController = getEndorsementJpaController(emfBackup);
        ExperienceJpaController backupExperienceJpaController = getExperienceJpaController(emfBackup);
        FacultyJpaController backupFacultyJpaController = getFacultyJpaController(emfBackup);
        ForwardAndRewindReportJpaController backupForwardAndRewindReportJpaController = getForwardAndRewindReportJpaController(emfBackup);
        FundingCostJpaController backupFundingCostJpaController = getFundingCostJpaController(emfBackup);
        FundingReportJpaController backupFundingReportJpaController = getFundingReportJpaController(emfBackup);
        InstitutionJpaController backupInstitutionJpaController = getInstitutionJpaController(emfBackup);
        MinuteCommentJpaController backupMinuteCommentJpaController = getMinuteCommentJpaController(emfBackup);
        NotificationJpaController backupNotificationJpaController = getNotificationJpaController(emfBackup);
        PersonJpaController backupPersonJpaController = getPersonJpaController(emfBackup);
        ProgressReportJpaController backupProgressReportJpaController = getProgressReportJpaController(emfBackup);
        RecommendationReportJpaController backupRecommendationReportJpaController = getRecommendationReportJpaController(emfBackup);
        RefereeReportJpaController backupRefereeReportJpaController = getRefereeReportJpaController(emfBackup);
        ResearchFellowInformationJpaController backupResearchFellowInformationJpaController = getResearchFellowInformationJpaController(emfBackup);
        SecurityRoleJpaController backupSecurityRoleJpaController = getSecurityRoleJpaController(emfBackup);
        
        
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
