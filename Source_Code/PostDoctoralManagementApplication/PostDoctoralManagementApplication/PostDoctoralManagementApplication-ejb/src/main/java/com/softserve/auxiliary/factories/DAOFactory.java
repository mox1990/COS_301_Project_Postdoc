/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.factories;

import com.softserve.persistence.DBDAO.AcademicQualificationJpaController;
import com.softserve.persistence.DBDAO.AddressJpaController;
import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.persistence.DBDAO.AnnouncementJpaController;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.persistence.DBDAO.AuditLogJpaController;
import com.softserve.persistence.DBDAO.CommitteeMeetingJpaController;
import com.softserve.persistence.DBDAO.CvJpaController;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class DAOFactory {
    private EntityManager em = null;
    
    public DAOFactory(EntityManager em) 
    {
        this.em = em;
    }
    
    public AnnouncementJpaController createAnnouncementDAO()
    {
        return new AnnouncementJpaController(em);
    }
    
    public AcademicQualificationJpaController createAcademicQualificationDAO()
    {
        return new AcademicQualificationJpaController(em);
    }
    
    public AddressJpaController createAddressDAO()
    {
        return new AddressJpaController(em);
    }
    
    public AmmendRequestJpaController createAmmendRequestDAO()
    {
        return new AmmendRequestJpaController(em);
    }
    
    public ApplicationJpaController createApplicationDAO()
    {
        return new ApplicationJpaController(em);
    }
    
    public ApplicationReviewRequestJpaController createApplicationReviewRequestDAO()
    {
        return new ApplicationReviewRequestJpaController(em);
    }
    
    public AuditLogJpaController createAuditLogDAO()
    {
        return new AuditLogJpaController(em);
    }
    
    public CommitteeMeetingJpaController createCommitteeMeetingDAO()
    {
        return new CommitteeMeetingJpaController(em);
    }
    
    public CvJpaController createCvDAO()
    {
        return new CvJpaController(em);
    }
    
    public DeclineReportJpaController createDeclineReportDAO()
    {
        return new DeclineReportJpaController(em);
    }
    
    public DepartmentJpaController createDepartmentDAO()
    {
        return new DepartmentJpaController(em);
    }
    
    public EligiblityReportJpaController createEligiblityReportDAO()
    {
        return new EligiblityReportJpaController(em);
    }
    
    public EmployeeInformationJpaController createEmployeeInformationDAO()
    {
        return new EmployeeInformationJpaController(em);
    }
    
    public EndorsementJpaController createEndorsementDAO()
    {
        return new EndorsementJpaController(em);
    }
    
    public ExperienceJpaController createExperienceDAO()
    {
        return new ExperienceJpaController(em);
    }
    
    public FacultyJpaController createFacultyDAO()
    {
        return new FacultyJpaController(em);
    }
    
    public ForwardAndRewindReportJpaController createForwardAndRewindReportDAO()
    {
        return new ForwardAndRewindReportJpaController(em);
    }
    
    public FundingCostJpaController createFundingCostJpaController()
    {
        return new FundingCostJpaController(em);
    }
    
    public FundingReportJpaController createFundingReportDAO()
    {
        return new FundingReportJpaController(em);
    }
    
    public InstitutionJpaController createInstitutionDAO()
    {
        return new InstitutionJpaController(em);
    }
    
    public MinuteCommentJpaController createMinuteCommentDAO()
    {
        return new MinuteCommentJpaController(em);
    }
    
    public NotificationJpaController createNotificationDAO()
    {
        return new NotificationJpaController(em);
    }
    
    public PersonJpaController createPersonDAO()
    {
        return new PersonJpaController(em);
    }
    
    public ProgressReportJpaController createProgressReportDAO()
    {
        return new ProgressReportJpaController(em);
    }
    
    public RecommendationReportJpaController createRecommendationReportDAO()
    {
        return new RecommendationReportJpaController(em);
    }
    
    public RefereeReportJpaController createRefereeReportDAO()
    {
        return new RefereeReportJpaController(em);
    }
    
    public ResearchFellowInformationJpaController createResearchFellowInformationDAO()
    {
        return new ResearchFellowInformationJpaController(em);
    }
    
    public SecurityRoleJpaController createSecurityRoleDAO()
    {
        return new SecurityRoleJpaController(em);
    }
    
}
