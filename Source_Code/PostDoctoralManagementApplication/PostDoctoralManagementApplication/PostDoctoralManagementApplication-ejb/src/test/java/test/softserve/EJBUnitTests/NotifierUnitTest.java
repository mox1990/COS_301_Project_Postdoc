/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.requestresponseclasses.Issue;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.ejb.applicationservices.ApplicationRenewalServiceLocal;
import com.softserve.ejb.applicationservices.MeetingManagementServiceLocal;
import com.softserve.ejb.applicationservices.ProgressReportManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import test.softserve.MockEJBClasses.NotifierServicesMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NotifierUnitTest {
    private NotifierServicesMockUnit instance;
    
    private DBEntitiesFactory mockDBEntities;
    private NotificationServiceLocal mockNotificationServiceLocal;
    private ProgressReportManagementServiceLocal mockProgressReportManagementServiceLocal;
    private ApplicationRenewalServiceLocal mockApplicationRenewalServiceLocal;
    private MeetingManagementServiceLocal mockMeetingManagementServiceLocal;
    private UserAccountManagementServiceLocal mockUserAccountManagementServiceLocal;
    private ApplicationServicesUtil mockApplicationServicesUtil;
    private DAOFactory mockDAOFactory;
    private EntityManager mockEntityManager;
    
    public NotifierUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new NotifierServicesMockUnit();
        
        mockDBEntities = mock(DBEntitiesFactory.class);
        mockNotificationServiceLocal = mock(NotificationServiceLocal.class);
        mockProgressReportManagementServiceLocal = mock(ProgressReportManagementServiceLocal.class);
        mockApplicationRenewalServiceLocal = mock(ApplicationRenewalServiceLocal.class);
        mockMeetingManagementServiceLocal = mock(MeetingManagementServiceLocal.class);
        mockUserAccountManagementServiceLocal = mock(UserAccountManagementServiceLocal.class);
        mockApplicationServicesUtil = mock(ApplicationServicesUtil.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockEntityManager = mock(EntityManager.class);
        
        instance.setdBEntities(mockDBEntities);
        instance.setnEJB(mockNotificationServiceLocal);
        instance.setApplicationRenewalServiceLocal(mockApplicationRenewalServiceLocal);
        instance.setMeetingManagementServiceLocal(mockMeetingManagementServiceLocal);
        instance.setUserAccountManagementServiceLocal(mockUserAccountManagementServiceLocal);
        instance.setaSEJB(mockApplicationServicesUtil);
        instance.setdAOFactory(mockDAOFactory);
        instance.setEntityManager(mockEntityManager);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadAllPendingIssuesForSession method, of class NotifierServices.
     */
    @Test
    public void testLoadAllPendingIssuesForSession() throws Exception 
    {
        Session mockSession = mock(Session.class);
        Person mockPerson = mock(Person.class);
        
        List<SecurityRole> secRole = new ArrayList<>();
        secRole.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        when(mockPerson.getSecurityRoleList()).thenReturn(secRole);
        when(mockApplicationServicesUtil.getTotalNumberOfPendingApplications(mockPerson, com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED)).thenReturn(Integer.MAX_VALUE);
        when(mockSession.getUser()).thenReturn(mockPerson);
        
        Issue issue = new Issue("Finalisations", "You have pending applications for finalisation", 0);
        issue.setCount(Integer.MAX_VALUE);
        
        List<Issue> expectedIssues = Arrays.asList(issue);
        
        try
        {
            List<Issue> computedIssues = instance.loadAllPendingIssuesForSession(mockSession);
            
            verify(mockApplicationServicesUtil).getTotalNumberOfPendingApplications(mockPerson, com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntities);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockProgressReportManagementServiceLocal);
            verifyNoMoreInteractions(mockApplicationRenewalServiceLocal);
            verifyNoMoreInteractions(mockMeetingManagementServiceLocal);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
        
            assertEquals(expectedIssues.size(), computedIssues.size());
            for(int i = 0; i < expectedIssues.size(); i++)
            {
                assertEquals(expectedIssues.get(i).getCount(), computedIssues.get(i).getCount());
                assertEquals(expectedIssues.get(i).getMessage(), computedIssues.get(i).getMessage());
                assertEquals(expectedIssues.get(i).getType(), computedIssues.get(i).getType());
            }
            // TO Come from beneath...
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of loadAllPendingIssuesForUser method, of class NotifierServices.
     */
    @Test
    public void testLoadAllPendingIssuesForUserAsGrantHolder() throws Exception {
        Session mockSession = mock(Session.class);
        Person mockPerson = mock(Person.class);
        
        List<SecurityRole> secRole = new ArrayList<>();
        secRole.add(com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_GRANT_HOLDER);
        
        when(mockPerson.getSecurityRoleList()).thenReturn(secRole);
        when(mockApplicationServicesUtil.getTotalNumberOfPendingApplications(mockPerson, com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED)).thenReturn(Integer.MAX_VALUE);
        
        Issue issue = new Issue("Finalisations", "You have pending applications for finalisation", 0);
        issue.setCount(Integer.MAX_VALUE);
        
        List<Issue> expectedIssues = Arrays.asList(issue);
        
        try
        {
            List<Issue> computedIssues = instance.loadAllPendingIssuesForUser(mockSession, mockPerson);
            
            verify(mockApplicationServicesUtil).getTotalNumberOfPendingApplications(mockPerson, com.softserve.auxiliary.constants.PersistenceConstants.APPLICATION_STATUS_REFERRED);
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockDBEntities);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockProgressReportManagementServiceLocal);
            verifyNoMoreInteractions(mockApplicationRenewalServiceLocal);
            verifyNoMoreInteractions(mockMeetingManagementServiceLocal);
            verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
        
            assertEquals(expectedIssues.size(), computedIssues.size());
            for(int i = 0; i < expectedIssues.size(); i++)
            {
                assertEquals(expectedIssues.get(i).getCount(), computedIssues.get(i).getCount());
                assertEquals(expectedIssues.get(i).getMessage(), computedIssues.get(i).getMessage());
                assertEquals(expectedIssues.get(i).getType(), computedIssues.get(i).getType());
            }
            // TO Come from beneath...
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    // TODO: Write more test cases for this...
    /**
     * Test of sendDailyReminders method, of class NotifierServices.
     */
    @Test
    public void testSendDailyReminders() throws Exception {
        try
        {
            instance.sendDailyReminders();
            
            // TODO: verify(mockUserAccountManagementServiceLocal).viewAllUserAccounts(new Session(null, null, true));
            
            verifyNoMoreInteractions(mockDBEntities);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockProgressReportManagementServiceLocal);
            verifyNoMoreInteractions(mockApplicationRenewalServiceLocal);
            verifyNoMoreInteractions(mockMeetingManagementServiceLocal);
            // TODO verifyNoMoreInteractions(mockUserAccountManagementServiceLocal);
            verifyNoMoreInteractions(mockApplicationServicesUtil);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            // TO Come from beneath...
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
}
