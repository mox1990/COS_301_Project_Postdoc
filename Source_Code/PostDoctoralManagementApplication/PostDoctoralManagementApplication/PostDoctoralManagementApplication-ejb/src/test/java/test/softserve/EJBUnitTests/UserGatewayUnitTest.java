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
        
    }

    /**
     * Test of logout method, of class UserGateway.
     */
    @Test
    public void testLogout() throws Exception {
        
    }

    /**
     * Test of getSessionFromHttpSession method, of class UserGateway.
     */
    @Test
    public void testGetSessionFromHttpSession() throws Exception {
        
    }

    /**
     * Test of authenticateUser method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUser() throws Exception {
        
    }

    /**
     * Test of authenticateUserAsOwner method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserAsOwner_Session_Person() throws Exception {
        
    }

    /**
     * Test of authenticateUserAsOwner method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserAsOwner_Session_Application() throws Exception {
        
    }

    /**
     * Test of authenticateUserAsOwner method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserAsOwner_Session_Cv() throws Exception {
        
    }

    /**
     * Test of authenticateUserAsOwner method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserAsOwner_Session_ProgressReport() throws Exception {
        
    }

    /**
     * Test of authenticateUserAsOwner method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserAsOwner_Session_CommitteeMeeting() throws Exception {
        
    }

    /**
     * Test of authenticateUserAsOwner method, of class UserGateway.
     */
    @Test
    public void testAuthenticateUserAsOwner_Session_FundingReport() throws Exception {
        
    }
    
}
