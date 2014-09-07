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
        
        for(Person p: workingPersonJpaController.findPersonEntities())
        {
            // Release all other entities from Person...
            List<SecurityRole> securityRoleList = p.getSecurityRoleList();
            p.setSecurityRoleList(new ArrayList<SecurityRole>());
            
            List<Application> applicationList = p.getApplicationList();
            p.setApplicationList(new ArrayList<Application>());
            
            List<CommitteeMeeting> committeeMeetingList = p.getCommitteeMeetingList();
            p.setCommitteeMeetingList(new ArrayList<CommitteeMeeting>());
            
            List<DeclineReport> declineReportList = p.getDeclineReportList();
            p.setDeclineReportList(new ArrayList<DeclineReport>());
            
            List<Endorsement> endorsementList = p.getEndorsementList();
            p.setEndorsementList(new ArrayList<Endorsement>());
            
            List<ApplicationReviewRequest> applicationReviewRequestList  = p.getApplicationReviewRequestList();
            p.setApplicationReviewRequestList(new ArrayList<ApplicationReviewRequest>());
            
            ResearchFellowInformation researchFellowInformation = p.getResearchFellowInformation();
            p.setResearchFellowInformation(null);
            
            List<RefereeReport> refereeReportList = p.getRefereeReportList();
            p.setRefereeReportList(new ArrayList<RefereeReport>());
            
            EmployeeInformation employeeInformation = p.getEmployeeInformation();
            p.setEmployeeInformation(null);
            
            List<Notification> notificationList = p.getNotificationList();
            p.setNotificationList(new ArrayList<Notification>());
            
            List<Notification> notificationList1 = p.getNotificationList1();
            p.setNotificationList1(new ArrayList<Notification>());
            
            List<AmmendRequest> ammendRequestList = p.getAmmendRequestList();
            p.setAmmendRequestList(new ArrayList<AmmendRequest>());
            
            List<AuditLog> auditLogList = p.getAuditLogList();
            p.setAuditLogList(new ArrayList<AuditLog>());
            
            List<RecommendationReport> recommendationReportList = p.getRecommendationReportList();
            p.setRecommendationReportList(new ArrayList<RecommendationReport>());
            
            List<FundingReport> fundingReportList = p.getFundingReportList();
            p.setFundingReportList(new ArrayList<FundingReport>());
            
            Cv cv = p.getCv();
            p.setCv(null);
            
            List<Application> applicationList1 = p.getApplicationList1();
            p.setApplicationList1(new ArrayList<Application>());
            
            List<Application> applicationList2 = p.getApplicationList2();
            p.setApplicationList2(new ArrayList<Application>());
            
            Address addressLine1 = p.getAddressLine1();
            p.setAddressLine1(null);
            
            List<MinuteComment> minuteCommentList = p.getMinuteCommentList();
            p.setMinuteCommentList(new ArrayList<MinuteComment>());
            
            List<EligiblityReport> eligiblityReportList = p.getEligiblityReportList();
            p.setEligiblityReportList(new ArrayList<EligiblityReport>());
            
            List<ForwardAndRewindReport> forwardAndRewindReportList= p.getForwardAndRewindReportList();
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
                for(SecurityRole s: securityRoleList)
                {
                    if(backupSecurityRoleJpaController.findSecurityRole(s.getRoleID()) == null)
                    {
                        s.setPersonList(new ArrayList<Person>());
                        backupSecurityRoleJpaController.create(s);
                    }
                    p.getSecurityRoleList().add(s);
                }
                
                for(Application a: applicationList)
                {
                    // TODO: Handle the dependecies of Application...
                    Application tmpA = backupApplicationJpaController.findApplication(a.getApplicationID());
                    if(tmpA == null)
                    {
                        a.setPersonList(new ArrayList<Person>());
                        backupApplicationJpaController.create(a);
                    }
                    else
                    {
                        a.setPersonList(tmpA.getPersonList());
                        backupApplicationJpaController.edit(a);
                    }
                    p.getApplicationList().add(a);
                }
                           
                for(CommitteeMeeting c: committeeMeetingList)
                {
                    CommitteeMeeting tmpC = backupCommitteeMeetingJpaController.findCommitteeMeeting(c.getMeetingID());
                    if(tmpC == null)
                    {
                        c.setPersonList(new ArrayList<Person>());
                        backupCommitteeMeetingJpaController.create(c);
                    }
                    else
                    {
                        c.setPersonList(tmpC.getPersonList());
                        backupCommitteeMeetingJpaController.edit(c);
                    }
                    p.getCommitteeMeetingList().add(c);
                }
                
                for(DeclineReport d: declineReportList)
                {
                    Person tmp = new Person(d.getCreator().getSystemID());
                    if(backupPersonJpaController.findPerson(tmp.getSystemID()) == null)
                    {
                        backupPersonJpaController.create(tmp);
                        d.setCreator(tmp);
                    }
                        
                    DeclineReport tmpD = backupDeclineReportJpaController.findDeclineReport(d.getReportID());
                    if(tmpD == null)
                    {
                        backupDeclineReportJpaController.create(d);
                    }
                    else
                    {
                        backupDeclineReportJpaController.edit(d);
                    }
                    p.getDeclineReportList().add(d);
                }
                
                for(Endorsement e: endorsementList)
                {
                    Person tmp = new Person(e.getDean().getSystemID());
                    if(backupPersonJpaController.findPerson(tmp.getSystemID()) == null)
                    {
                        backupPersonJpaController.create(tmp);
                        e.setDean(tmp);
                    }
                        
                    Endorsement tmpE = backupEndorsementJpaController.findEndorsement(e.getEndorsementID());
                    if(tmpE == null)
                    {
                        backupEndorsementJpaController.create(e);
                    }
                    else
                    {
                        backupEndorsementJpaController.edit(e);
                    }
                    p.getEndorsementList().add(e);
                }
                
                for(ApplicationReviewRequest a: applicationReviewRequestList)
                {                        
                    Person tmp = new Person(a.getPerson1().getSystemID());
                    if(backupPersonJpaController.findPerson(tmp.getSystemID()) == null)
                    {
                        backupPersonJpaController.create(tmp);
                        a.setPerson1(tmp);
                    }
                    
                    if(backupApplicationReviewRequestJpaController.findApplicationReviewRequest(a.getApplicationReviewRequestPK()) == null)
                    {
                        backupApplicationReviewRequestJpaController.create(a);
                    }
                    else
                    {
                        backupApplicationReviewRequestJpaController.edit(a);
                    }
                }
                
                    Department d = researchFellowInformation.getDepartment();

                    // TODO: Department Depandecies...
                    if(backupDepartmentJpaController.findDepartment(d.getDepartmentID()) == null)
                    {
                        backupDepartmentJpaController.create(d);
                    }
                
                if(backupResearchFellowInformationJpaController.findResearchFellowInformation(researchFellowInformation.getSystemAssignedID()) == null)
                {
                    backupResearchFellowInformationJpaController.create(researchFellowInformation);
                }
                else
                {
                    backupResearchFellowInformationJpaController.edit(researchFellowInformation);
                }
                
                for(RefereeReport r: refereeReportList)
                {
                        Person tmp = new Person(r.getReferee().getSystemID());
                        if(backupPersonJpaController.findPerson(tmp.getSystemID()) == null)
                        {
                            backupPersonJpaController.create(tmp);
                        }
                        
                    if(backupRefereeReportJpaController.findRefereeReport(r.getReportID()) == null)
                    {
                        backupRefereeReportJpaController.create(r);
                    }
                    else
                    {
                        backupRefereeReportJpaController.edit(r);
                    }
                }
                
                if(backupEmployeeInformationJpaController.findEmployeeInformation(employeeInformation.getEmployeeID()) == null)
                {
                    backupEmployeeInformationJpaController.create(employeeInformation);
                }
                else
                {
                    backupEmployeeInformationJpaController.edit(employeeInformation);
                }
                
                for(Notification n: notificationList)
                {
                    if(backupNotificationJpaController.findNotification(n.getNotificationID()) == null)
                    {
                        backupNotificationJpaController.create(n);
                    }
                    else
                    {
                        backupNotificationJpaController.edit(n);
                    }
                }
                
                for(Notification n: notificationList1)
                {
                    if(backupNotificationJpaController.findNotification(n.getNotificationID()) == null)
                    {
                        backupNotificationJpaController.create(n);
                    }
                    else
                    {
                        backupNotificationJpaController.edit(n);
                    }
                }
                
                for(AmmendRequest a: ammendRequestList)
                {
                    if(backupAmmendRequestJpaController.findAmmendRequest(a.getRequestID()) == null)
                    {
                        backupAmmendRequestJpaController.create(a);
                    }
                    else
                    {
                        backupAmmendRequestJpaController.edit(a);
                    }
                }
                
                for(AuditLog a: auditLogList)
                {
                    if(backupAuditLogJpaController.findAuditLog(a.getEntryID()) == null)
                    {
                        backupAuditLogJpaController.create(a);
                    }
                    else
                    {
                        backupAuditLogJpaController.edit(a);
                    }
                }
                
                for(RecommendationReport r: recommendationReportList)
                {
                    if(backupRecommendationReportJpaController.findRecommendationReport(r.getReportID()) == null)
                    {
                        backupRecommendationReportJpaController.create(r);
                    }
                    else
                    {
                        backupRecommendationReportJpaController.edit(r);
                    }
                }
                
                for(FundingReport f: fundingReportList)
                {
                    if(backupFundingReportJpaController.findFundingReport(f.getReportID()) == null)
                    {
                        backupFundingReportJpaController.create(f);
                    }
                    else
                    {
                        backupFundingReportJpaController.edit(f);
                    }
                }
                
                // TODO: CV extras...
                if(backupCvJpaController.findCv(cv.getCvID()) == null)
                {
                    backupCvJpaController.create(cv);
                }
                else
                {
                    backupCvJpaController.edit(cv);
                }
                
                for(Application a: applicationList1)
                {
                    // TODO: Handle the dependecies of Application...
                    Application tmpA = backupApplicationJpaController.findApplication(a.getApplicationID());
                    if(tmpA == null)
                    {
                        a.setPersonList(new ArrayList<Person>());
                        backupApplicationJpaController.create(a);
                    }
                    else
                    {
                        a.setPersonList(tmpA.getPersonList());
                        backupApplicationJpaController.edit(a);
                    }
                    p.getApplicationList().add(a);
                }
                
                for(Application a: applicationList2)
                {
                    // TODO: Handle the dependecies of Application...
                    Application tmpA = backupApplicationJpaController.findApplication(a.getApplicationID());
                    if(tmpA == null)
                    {
                        a.setPersonList(new ArrayList<Person>());
                        backupApplicationJpaController.create(a);
                    }
                    else
                    {
                        a.setPersonList(tmpA.getPersonList());
                        backupApplicationJpaController.edit(a);
                    }
                    p.getApplicationList().add(a);
                }
                
                    for(EmployeeInformation e: addressLine1.getEmployeeInformationList())
                    {
                            Department tmpD = e.getDepartment();
                            if(backupDepartmentJpaController.findDepartment(tmpD.getDepartmentID()) == null)
                            {
                                tmpD.setEmployeeInformationList(new ArrayList<EmployeeInformation>());
                                backupDepartmentJpaController.create(tmpD);
                                e.setDepartment(tmpD);
                            }
                            
                            Person tmpP = new Person(e.getPerson().getSystemID());
                            if(backupPersonJpaController.findPerson(tmpP.getSystemID()) == null)
                            {
                                backupPersonJpaController.create(tmpP);
                                e.setPerson(tmpP);
                            }
                            
                            // TODO: Complete dependencies
                        if(backupEmployeeInformationJpaController.findEmployeeInformation(e.getEmployeeID()) == null)
                        {
                            backupEmployeeInformationJpaController.create(e);
                        }
                    }
                    
                if(backupAddressJpaController.findAddress(addressLine1.getAddressID()) == null)
                {
                    backupAddressJpaController.create(addressLine1);
                }
                else
                {
                    backupAddressJpaController.edit(addressLine1);
                }
                
                for(MinuteComment m: minuteCommentList)
                {
                        Person tmpA = new Person(m.getAttendee().getSystemID());
                        if(backupPersonJpaController.findPerson(tmpA.getSystemID()) == null)
                        {
                            backupPersonJpaController.create(tmpA);
                            m.setAttendee(tmpA);
                        }
                        
                        CommitteeMeeting tmpC = new CommitteeMeeting(m.getMeeting().getMeetingID());
                        if(backupCommitteeMeetingJpaController.findCommitteeMeeting(tmpC.getMeetingID()) == null)
                        {
                            backupCommitteeMeetingJpaController.create(tmpC);
                            m.setMeeting(tmpC);
                        }
                        
                    if(backupMinuteCommentJpaController.findMinuteComment(m.getCommentID()) == null)
                    {
                        backupMinuteCommentJpaController.create(m);
                    }
                    else
                    {
                        backupMinuteCommentJpaController.edit(m);
                    }
                }
                
                for(EligiblityReport e: eligiblityReportList)
                {
                        Person tmp = new Person(e.getEligiblityChecker().getSystemID());
                        if(backupPersonJpaController.findPerson(tmp.getSystemID()) == null)
                        {
                            backupPersonJpaController.create(tmp);
                            e.setEligiblityChecker(tmp);
                        }
                        
                    if(backupEligiblityReportJpaController.findEligiblityReport(e.getReportID()) == null)
                    {
                        backupEligiblityReportJpaController.create(e);
                    }
                    else
                    {
                        backupEligiblityReportJpaController.edit(e);
                    }
                }
                
                for(ForwardAndRewindReport f: forwardAndRewindReportList)
                {
                        Person tmp = new Person(f.getDris().getSystemID());
                        if(backupPersonJpaController.findPerson(tmp.getSystemID()) == null)
                        {
                            backupPersonJpaController.create(tmp);
                            f.setDris(tmp);
                        }
                        
                    if(backupForwardAndRewindReportJpaController.findForwardAndRewindReport(f.getReportID()) == null)
                    {
                        backupForwardAndRewindReportJpaController.create(f);
                    }
                    else
                    {
                        backupForwardAndRewindReportJpaController.edit(f);
                    }
                }
                
                // Return it all to person...
                p.setSecurityRoleList(securityRoleList);
                p.setApplicationList(applicationList);
                p.setCommitteeMeetingList(committeeMeetingList);
                p.setDeclineReportList(declineReportList);
                p.setEndorsementList(endorsementList);
                p.setApplicationReviewRequestList(applicationReviewRequestList);
                p.setResearchFellowInformation(researchFellowInformation);
                p.setRefereeReportList(refereeReportList);
                p.setEmployeeInformation(employeeInformation);
                p.setNotificationList(notificationList);
                p.setNotificationList1(notificationList1);
                p.setAmmendRequestList(ammendRequestList);
                p.setAuditLogList(auditLogList);
                p.setRecommendationReportList(recommendationReportList);
                p.setFundingReportList(fundingReportList);
                p.setApplicationList1(applicationList1);
                p.setApplicationList2(applicationList2);
                p.setAddressLine1(addressLine1);
                p.setMinuteCommentList(minuteCommentList);
                p.setEligiblityReportList(eligiblityReportList);
                p.setForwardAndRewindReportList(forwardAndRewindReportList);
                
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
