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
    
    private void backUpFailed(Object object)
    {
        System.out.println("Failed to back up: " + object.toString());
        // TODO: Log it in a more suitable manner...
    }
    
    private void restoreFailed(Object object)
    {
        System.out.println("Failed to restore: " + object.toString());
        // TODO: Log it in a more suitable manner...
    }
}
