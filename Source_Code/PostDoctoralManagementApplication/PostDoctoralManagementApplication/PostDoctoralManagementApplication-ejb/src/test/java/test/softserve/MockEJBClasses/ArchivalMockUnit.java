/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

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
import com.softserve.ejb.ArchivalService;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kgothatso
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
