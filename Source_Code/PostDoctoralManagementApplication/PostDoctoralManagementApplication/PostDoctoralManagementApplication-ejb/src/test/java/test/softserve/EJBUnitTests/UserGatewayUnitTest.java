/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.ejb.nonapplicationservices.UserGatewayLocal;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.CommitteeMeeting;
import com.softserve.persistence.DBEntities.Cv;
import com.softserve.persistence.DBEntities.FundingReport;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.ProgressReport;
import com.softserve.persistence.DBEntities.SecurityRole;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import test.softserve.MockEJBClasses.UserGatewayMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class UserGatewayUnitTest {
    private UserGatewayMockUnit instance;
    
    private NotificationServiceLocal mockNotificationServiceLocal;
    private DAOFactory mockDAOFactory;
    private EntityManager mockEntityManager;
    private PersonJpaController mockPersonJpaController;
    
    public UserGatewayUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new UserGatewayMockUnit();
        
        mockNotificationServiceLocal = mock(NotificationServiceLocal.class);
        mockDAOFactory = mock(DAOFactory.class);
        mockEntityManager = mock(EntityManager.class);
        mockPersonJpaController = mock(PersonJpaController.class);
        
        instance.setNotificationServiceLocal(mockNotificationServiceLocal);
        instance.setdAOFactory(mockDAOFactory);
        instance.setEntityManager(mockEntityManager);
        
        when(mockDAOFactory.createPersonDAO()).thenReturn(mockPersonJpaController);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of login method, of class UserGateway.
     */
    @Test
    public void testLogin() throws Exception {
        Session mockSession = mock(Session.class);
        
        when(mockSession.isUserAccountActive()).thenReturn(Boolean.FALSE);
        when(mockSession.doesHttpSessionUsernameMatchUserEmail()).thenReturn(Boolean.TRUE);
        when(mockSession.doesHttpSessionUsernameMatchUserEmail()).thenReturn(Boolean.TRUE);
        when(mockSession.doesHttpSessionPasswordMatchUserPassword()).thenReturn(Boolean.TRUE);
        try
        {
            instance.login(mockSession);
            verify(mockSession).isUserAccountDisabled();
            verify(mockSession).doesHttpSessionUsernameMatchUserEmail();
            verify(mockSession).doesHttpSessionUsernameMatchUserUsername();
            verify(mockSession).doesHttpSessionPasswordMatchUserPassword();
            verify(mockSession).setLoggedInStatus(Boolean.TRUE);
                    
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockPersonJpaController);
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of logout method, of class UserGateway.
     */
    @Test
    public void testLogout() throws Exception {
        Session mockSession = mock(Session.class);
        
        try
        {
            instance.logout(mockSession);
            
            verify(mockSession).setLoggedInStatus(Boolean.FALSE);
            verify(mockSession).setHttpSessionPassword("");
            verify(mockSession).setHttpSessionUsername("");
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockPersonJpaController);
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of getSessionFromHttpSession method, of class UserGateway.
     */
    @Test
    public void testGetSessionFromHttpSession() throws Exception {
        Session mockSession = mock(Session.class);
        HttpSession mockHttpSession = mock(HttpSession.class);
        
        when(mockHttpSession.getAttribute("username")).thenReturn("u12236731");
        when(mockPersonJpaController.findUserBySystemIDOrEmail("u12236731")).thenReturn(new Person("u12236731"));
        try
        {
            Session result = instance.getSessionFromHttpSession(mockHttpSession);
            
            verify(mockDAOFactory).createPersonDAO();
            verify(mockPersonJpaController).findUserBySystemIDOrEmail("u12236731");
            verify(mockHttpSession).getAttribute("username");
            verify(mockEntityManager).close();
            
            verifyNoMoreInteractions(mockHttpSession);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockPersonJpaController);
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of authenticateUser method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserIsSystem() throws Exception {
        Session mockSession = mock(Session.class);
        
        when(mockSession.isSystem()).thenReturn(Boolean.TRUE);
        try
        {
            instance.authenticateUser(mockSession, null);
            
            verify(mockSession).isSystem();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockPersonJpaController);
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of authenticateUserAsOwner method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserAsOwner_Session_Person() throws Exception {
        Session mockSession = mock(Session.class);
        Person mockPerson = mock(Person.class);
        
        when(mockSession.getUser()).thenReturn(mockPerson);
        try
        {
            instance.authenticateUserAsOwner(mockSession, mockPerson);
            
            verify(mockSession, times(2)).getUser();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockPersonJpaController);
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of authenticateUserAsOwner method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserAsOwner_Session_Application() throws Exception {
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        Person mockPerson = mock(Person.class);
        
        when(mockSession.getUser()).thenReturn(mockPerson);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        try
        {
            instance.authenticateUserAsOwner(mockSession, mockApplication);
            
            verify(mockApplication).getFellow();
            verify(mockSession, times(2)).getUser();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockPersonJpaController);
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of authenticateUserAsOwner method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserAsOwner_Session_Cv() throws Exception {
        Session mockSession = mock(Session.class);
        Cv mockCv = mock(Cv.class);
        Person mockPerson = mock(Person.class);
        
        when(mockSession.getUser()).thenReturn(mockPerson);
        when(mockCv.getPerson()).thenReturn(mockPerson);
        try
        {
            instance.authenticateUserAsOwner(mockSession, mockCv);
            
            verify(mockCv).getPerson();
            verify(mockSession, times(2)).getUser();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockPersonJpaController);
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of authenticateUserAsOwner method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserAsOwner_Session_ProgressReport() throws Exception {
        Session mockSession = mock(Session.class);
        Application mockApplication = mock(Application.class);
        ProgressReport mockProgressReport = mock(ProgressReport.class);
        Person mockPerson = mock(Person.class);
        
        when(mockSession.getUser()).thenReturn(mockPerson);
        when(mockApplication.getFellow()).thenReturn(mockPerson);
        when(mockProgressReport.getApplication()).thenReturn(mockApplication);
        try
        {
            instance.authenticateUserAsOwner(mockSession, mockProgressReport);
            
            verify(mockApplication).getFellow();
            verify(mockSession, times(2)).getUser();
            verify(mockProgressReport).getApplication();
            
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockPersonJpaController);
        }   
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }

    /**
     * Test of authenticateUserAsOwner method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserAsOwner_Session_CommitteeMeeting() throws Exception {
        Session mockSession = mock(Session.class);
        CommitteeMeeting mockCommitteeMeeting = mock(CommitteeMeeting.class);
        Person mockPerson = mock(Person.class);
        
        when(mockSession.getUser()).thenReturn(mockPerson);
        when(mockCommitteeMeeting.getOrganiser()).thenReturn(mockPerson);
        try
        {
            instance.authenticateUserAsOwner(mockSession, mockCommitteeMeeting);
            
            verify(mockSession, times(2)).getUser();
            
            verify(mockCommitteeMeeting).getOrganiser();
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockPersonJpaController);
        }   
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }

    /**
     * Test of authenticateUserAsOwner method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserAsOwner_Session_FundingReport() throws Exception {
        Session mockSession = mock(Session.class);
        FundingReport mockFundingReport = mock(FundingReport.class);
        Person mockPerson = mock(Person.class);
        
        when(mockSession.getUser()).thenReturn(mockPerson);
        when(mockFundingReport.getDris()).thenReturn(mockPerson);
        try
        {
            instance.authenticateUserAsOwner(mockSession, mockFundingReport);
            
            verify(mockSession, times(2)).getUser();
            
            verify(mockFundingReport).getDris();
            verifyNoMoreInteractions(mockSession);
            verifyNoMoreInteractions(mockNotificationServiceLocal);
            verifyNoMoreInteractions(mockDAOFactory);
            verifyNoMoreInteractions(mockEntityManager);
            verifyNoMoreInteractions(mockPersonJpaController);
        }   
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail("An exception occured");
        }
    }
    
}
