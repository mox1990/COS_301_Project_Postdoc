/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBDAO;

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
    
    public SecurityRoleJpaController createSecurityRoleJpaController()
    {
        return new SecurityRoleJpaController(em);
    }
    
}
