/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.persistence.DBDAO.AcademicQualificationJpaController;
import com.softserve.persistence.DBDAO.AddressJpaController;
import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
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
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.auxiliary.requestresponseclasses.Session;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import test.softserve.MockEJBClasses.ArchivalMockUnit;
import test.softserve.MockEJBClasses.AuditTrailServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ArchivalServiceTest {
    private ArchivalMockUnit instance;
    
    private AcademicQualificationJpaController mockAcademicQualificationJpaController;
    private AddressJpaController mockAddressJpaController;
    private AmmendRequestJpaController mockAmmendRequestJpaController;
    private ApplicationJpaController mockApplicationJpaController;
    private ApplicationReviewRequestJpaController mockApplicationReviewRequestJpaController;
    private AuditLogJpaController mockAuditLogJpaController;
    private CommitteeMeetingJpaController mockCommitteeMeetingJpaController;
    private CvJpaController mockCvJpaController;
    private DeclineReportJpaController mockDeclineReportJpaController;
    private DepartmentJpaController mockDepartmentJpaController;
    private EligiblityReportJpaController mockEligiblityReportJpaController;
    private EmployeeInformationJpaController mockEmployeeInformationJpaController;
    private EndorsementJpaController mockEndorsementJpaController;
    private ExperienceJpaController mockExperienceJpaController;
    private FacultyJpaController mockFacultyJpaController;
    private ForwardAndRewindReportJpaController mockForwardAndRewindReportJpaController;
    private FundingCostJpaController mockFundingCostJpaController;
    private FundingReportJpaController mockFundingReportJpaController;
    private InstitutionJpaController mockInstitutionJpaController;
    private MinuteCommentJpaController mockMinuteCommentJpaController;
    private NotificationJpaController mockNotificationJpaController;
    private PersonJpaController mockPersonJpaController;
    private ProgressReportJpaController mockProgressReportJpaController;
    private RecommendationReportJpaController mockRecommendationReportJpaController;
    private RefereeReportJpaController mockRefereeReportJpaController;
    private ResearchFellowInformationJpaController mockResearchFellowInformationJpaController;
    private SecurityRoleJpaController mockSecurityRoleJpaController;
    
    public ArchivalServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new ArchivalMockUnit();
        
        mockAcademicQualificationJpaController = mock(AcademicQualificationJpaController.class);
        mockAddressJpaController = mock(AddressJpaController.class);
        mockAmmendRequestJpaController = mock(AmmendRequestJpaController.class);
        mockApplicationJpaController = mock(ApplicationJpaController.class);
        mockApplicationReviewRequestJpaController = mock(ApplicationReviewRequestJpaController.class);
        mockAuditLogJpaController = mock(AuditLogJpaController.class);
        mockCommitteeMeetingJpaController = mock(CommitteeMeetingJpaController.class);
        mockCvJpaController = mock(CvJpaController.class);
        mockDeclineReportJpaController = mock(DeclineReportJpaController.class);
        mockDepartmentJpaController = mock(DepartmentJpaController.class);
        mockEligiblityReportJpaController = mock(EligiblityReportJpaController.class);
        mockEmployeeInformationJpaController = mock(EmployeeInformationJpaController.class);
        mockEndorsementJpaController = mock(EndorsementJpaController.class);
        mockExperienceJpaController = mock(ExperienceJpaController.class);
        mockFacultyJpaController = mock(FacultyJpaController.class);
        mockForwardAndRewindReportJpaController = mock(ForwardAndRewindReportJpaController.class);
        mockFundingCostJpaController = mock(FundingCostJpaController.class);
        mockFundingReportJpaController = mock(FundingReportJpaController.class);
        mockInstitutionJpaController = mock(InstitutionJpaController.class);
        mockMinuteCommentJpaController = mock(MinuteCommentJpaController.class);
        mockNotificationJpaController = mock(NotificationJpaController.class);
        mockPersonJpaController = mock(PersonJpaController.class);
        mockProgressReportJpaController = mock(ProgressReportJpaController.class);
        mockRecommendationReportJpaController = mock(RecommendationReportJpaController.class);
        mockRefereeReportJpaController = mock(RefereeReportJpaController.class);
        mockResearchFellowInformationJpaController = mock(ResearchFellowInformationJpaController.class);
        mockSecurityRoleJpaController = mock(SecurityRoleJpaController.class);
        
        instance.setAcademicQualificationJpaController(mockAcademicQualificationJpaController);
        instance.setAddressJpaController(mockAddressJpaController);
        instance.setAmmendRequestJpaController(mockAmmendRequestJpaController);
        instance.setApplicationJpaController(mockApplicationJpaController);
        instance.setApplicationReviewRequestJpaController(mockApplicationReviewRequestJpaController);
        instance.setAuditLogJpaController(mockAuditLogJpaController);
        instance.setCommitteeMeetingJpaController(mockCommitteeMeetingJpaController);
        instance.setCvJpaController(mockCvJpaController);
        instance.setDeclineReportJpaController(mockDeclineReportJpaController);
        instance.setDepartmentJpaController(mockDepartmentJpaController);
        instance.setEligiblityReportJpaController(mockEligiblityReportJpaController);
        instance.setEmployeeInformationJpaController(mockEmployeeInformationJpaController);
        instance.setEndorsementJpaController(mockEndorsementJpaController);
        instance.setExperienceJpaController(mockExperienceJpaController);
        instance.setFacultyJpaController(mockFacultyJpaController);
        instance.setForwardAndRewindReportJpaController(mockForwardAndRewindReportJpaController);
        instance.setFundingCostJpaController(mockFundingCostJpaController);
        instance.setFundingReportJpaController(mockFundingReportJpaController);
        instance.setInstitutionJpaController(mockInstitutionJpaController);
        instance.setMinuteCommentJpaController(mockMinuteCommentJpaController);
        instance.setNotificationJpaController(mockNotificationJpaController);
        instance.setPersonJpaController(mockPersonJpaController);
        instance.setProgressReportJpaController(mockProgressReportJpaController);
        instance.setRecommendationReportJpaController(mockRecommendationReportJpaController);
        instance.setRefereeReportJpaController(mockRefereeReportJpaController);
        instance.setResearchFellowInformationJpaController(mockResearchFellowInformationJpaController);
        instance.setSecurityRoleJpaController(mockSecurityRoleJpaController);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of backupDatabase method, of class ArchivalService.
     */
    @Test
    public void testBackupDatabase() throws Exception {
        Session mockSession = mock(Session.class);
        when(mockSession.getUser()).thenReturn(new Person("u12236731"));
        
        List<Person> p = new ArrayList<Person>();
        p.add(new Person("u12236731"));
        p.add(new Person("u12345678"));
        
        when(mockPersonJpaController.findPersonEntities()).thenReturn(p);
        try
        {
            //instance.backupDatabase(mockSession);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //fail("An exception occured");
        }
    }

    /**
     * Test of archiveOldInformation method, of class ArchivalService.
     */
    @Test
    public void testArchiveOldInformation() throws Exception {
        
    }

    /**
     * Test of retrieveArchievedInformation method, of class ArchivalService.
     */
    @Test
    public void testRetrieveArchievedInformation() throws Exception {
        
    }

    /**
     * Test of restoreBackupToWorkingDatabase method, of class ArchivalService.
     */
    @Test
    public void testRestoreBackupToWorkingDatabase() throws Exception {
        
    }
    
}
