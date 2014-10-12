/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.persistence.DBDAO.AcademicQualificationJpaController;
import com.softserve.persistence.DBDAO.AddressJpaController;
import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.persistence.DBDAO.AuditLogJpaController;
import com.softserve.persistence.DBDAO.CommitteeMeetingJpaController;
import com.softserve.persistence.DBDAO.CvJpaController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.DeclineReportJpaController;
import com.softserve.persistence.DBDAO.DepartmentJpaController;
import com.softserve.persistence.DBDAO.EligiblityReportJpaController;
import com.softserve.persistence.DBDAO.EmployeeInformationJpaController;
import com.softserve.persistence.DBDAO.EndorsementJpaController;
import com.softserve.persistence.DBDAO.ExperienceJpaController;
import com.softserve.persistence.DBDAO.FacultyJpaController;
import com.softserve.persistence.DBDAO.ForwardAndRewindReportJpaController;
import com.softserve.persistence.DBDAO.FundingCostJpaController;
import com.softserve.persistence.DBDAO.FundingReportJpaController;
import com.softserve.persistence.DBDAO.InstitutionJpaController;
import com.softserve.persistence.DBDAO.MinuteCommentJpaController;
import com.softserve.persistence.DBDAO.NotificationJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBDAO.ProgressReportJpaController;
import com.softserve.persistence.DBDAO.RecommendationReportJpaController;
import com.softserve.persistence.DBDAO.RefereeReportJpaController;
import com.softserve.persistence.DBDAO.ResearchFellowInformationJpaController;
import com.softserve.persistence.DBDAO.SecurityRoleJpaController;
import com.softserve.ejb.nonapplicationservices.ArchivalService;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ArchivalMockUnit extends ArchivalService 
{
    AcademicQualificationJpaController academicQualificationJpaController;
    AddressJpaController addressJpaController;
    AmmendRequestJpaController ammendRequestJpaController;
    ApplicationJpaController applicationJpaController;
    ApplicationReviewRequestJpaController applicationReviewRequestJpaController;
    AuditLogJpaController auditLogJpaController;
    CommitteeMeetingJpaController committeeMeetingJpaController;
    CvJpaController cvJpaController;
    DeclineReportJpaController declineReportJpaController;
    DepartmentJpaController departmentJpaController;
    EligiblityReportJpaController eligiblityReportJpaController;
    EmployeeInformationJpaController employeeInformationJpaController;
    EndorsementJpaController endorsementJpaController;
    ExperienceJpaController experienceJpaController;
    FacultyJpaController facultyJpaController;
    ForwardAndRewindReportJpaController forwardAndRewindReportJpaController;
    FundingCostJpaController fundingCostJpaController;
    FundingReportJpaController fundingReportJpaController;
    InstitutionJpaController institutionJpaController;
    MinuteCommentJpaController minuteCommentJpaController;
    NotificationJpaController notificationJpaController;
    PersonJpaController personJpaController;
    ProgressReportJpaController progressReportJpaController;
    RecommendationReportJpaController recommendationReportJpaController;
    RefereeReportJpaController refereeReportJpaController;
    ResearchFellowInformationJpaController researchFellowInformationJpaController;
    SecurityRoleJpaController securityRoleJpaController;

    public void setAcademicQualificationJpaController(AcademicQualificationJpaController academicQualificationJpaController) {
        this.academicQualificationJpaController = academicQualificationJpaController;
    }

    public void setAddressJpaController(AddressJpaController addressJpaController) {
        this.addressJpaController = addressJpaController;
    }

    public void setAmmendRequestJpaController(AmmendRequestJpaController ammendRequestJpaController) {
        this.ammendRequestJpaController = ammendRequestJpaController;
    }

    public void setApplicationJpaController(ApplicationJpaController applicationJpaController) {
        this.applicationJpaController = applicationJpaController;
    }

    public void setApplicationReviewRequestJpaController(ApplicationReviewRequestJpaController applicationReviewRequestJpaController) {
        this.applicationReviewRequestJpaController = applicationReviewRequestJpaController;
    }

    public void setAuditLogJpaController(AuditLogJpaController auditLogJpaController) {
        this.auditLogJpaController = auditLogJpaController;
    }

    public void setCommitteeMeetingJpaController(CommitteeMeetingJpaController committeeMeetingJpaController) {
        this.committeeMeetingJpaController = committeeMeetingJpaController;
    }

    public void setCvJpaController(CvJpaController cvJpaController) {
        this.cvJpaController = cvJpaController;
    }

    public void setDeclineReportJpaController(DeclineReportJpaController declineReportJpaController) {
        this.declineReportJpaController = declineReportJpaController;
    }

    public void setDepartmentJpaController(DepartmentJpaController departmentJpaController) {
        this.departmentJpaController = departmentJpaController;
    }

    public void setEligiblityReportJpaController(EligiblityReportJpaController eligiblityReportJpaController) {
        this.eligiblityReportJpaController = eligiblityReportJpaController;
    }

    public void setEmployeeInformationJpaController(EmployeeInformationJpaController employeeInformationJpaController) {
        this.employeeInformationJpaController = employeeInformationJpaController;
    }

    public void setEndorsementJpaController(EndorsementJpaController endorsementJpaController) {
        this.endorsementJpaController = endorsementJpaController;
    }

    public void setExperienceJpaController(ExperienceJpaController experienceJpaController) {
        this.experienceJpaController = experienceJpaController;
    }

    public void setFacultyJpaController(FacultyJpaController facultyJpaController) {
        this.facultyJpaController = facultyJpaController;
    }

    public void setForwardAndRewindReportJpaController(ForwardAndRewindReportJpaController forwardAndRewindReportJpaController) {
        this.forwardAndRewindReportJpaController = forwardAndRewindReportJpaController;
    }

    public void setFundingCostJpaController(FundingCostJpaController fundingCostJpaController) {
        this.fundingCostJpaController = fundingCostJpaController;
    }

    public void setFundingReportJpaController(FundingReportJpaController fundingReportJpaController) {
        this.fundingReportJpaController = fundingReportJpaController;
    }

    public void setInstitutionJpaController(InstitutionJpaController institutionJpaController) {
        this.institutionJpaController = institutionJpaController;
    }

    public void setMinuteCommentJpaController(MinuteCommentJpaController minuteCommentJpaController) {
        this.minuteCommentJpaController = minuteCommentJpaController;
    }

    public void setNotificationJpaController(NotificationJpaController notificationJpaController) {
        this.notificationJpaController = notificationJpaController;
    }

    public void setPersonJpaController(PersonJpaController personJpaController) {
        this.personJpaController = personJpaController;
    }

    public void setProgressReportJpaController(ProgressReportJpaController progressReportJpaController) {
        this.progressReportJpaController = progressReportJpaController;
    }

    public void setRecommendationReportJpaController(RecommendationReportJpaController recommendationReportJpaController) {
        this.recommendationReportJpaController = recommendationReportJpaController;
    }

    public void setRefereeReportJpaController(RefereeReportJpaController refereeReportJpaController) {
        this.refereeReportJpaController = refereeReportJpaController;
    }

    public void setResearchFellowInformationJpaController(ResearchFellowInformationJpaController researchFellowInformationJpaController) {
        this.researchFellowInformationJpaController = researchFellowInformationJpaController;
    }

    public void setSecurityRoleJpaController(SecurityRoleJpaController securityRoleJpaController) {
        this.securityRoleJpaController = securityRoleJpaController;
    }
    
    
}
