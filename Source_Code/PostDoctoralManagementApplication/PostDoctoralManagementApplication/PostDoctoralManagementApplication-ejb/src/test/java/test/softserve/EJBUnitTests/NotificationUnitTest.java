/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.EJBUnitTests;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.persistence.DBDAO.NotificationJpaController;
import com.softserve.persistence.DBEntities.Notification;
import com.softserve.persistence.DBEntities.Person;
import java.util.List;
import java.util.Properties;
import javax.ejb.embeddable.EJBContainer;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import test.softserve.MockEJBClasses.NotificationServiceMockUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NotificationUnitTest {
    private NotificationServiceMockUnit instance;
    
    private DAOFactory mockDAOFactory;
    private TransactionController mockTransactionController;
    private UserGateway mockUserGateway;
    private Properties mockProperties;
    private javax.mail.Session mockSession;
    private javax.mail.Authenticator mockAuthenticator;
    private InternetAddress mockInternetAddress;
    private MimeMessage mockMimeMessage;
    private EntityManager mockEntityManager;
    private NotificationJpaController mockNotificationJpaController;
    
    public NotificationUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        instance = new NotificationServiceMockUnit();
        
        mockDAOFactory = mock(DAOFactory.class);
        mockTransactionController = mock(TransactionController.class);
        mockUserGateway = mock(UserGateway.class);
        mockProperties = mock(Properties.class);
        // TODO: Sort this out
        //mockSession = mock(javax.mail.Session.class);
        //mockAuthenticator = mock(javax.mail.Authenticator.class);
        mockInternetAddress = mock(InternetAddress.class);
        mockMimeMessage = mock(MimeMessage.class);
        mockEntityManager = mock(EntityManager.class);
        mockNotificationJpaController = mock(NotificationJpaController.class);
        
        instance.setdAOFactory(mockDAOFactory);
        instance.setTransactionController(mockTransactionController);
        instance.setUserGateway(mockUserGateway);
        instance.setProperties(mockProperties);
        instance.setAuthenticator(mockAuthenticator);
        instance.setInternetAddress(mockInternetAddress);
        instance.setMimeMessage(mockMimeMessage);
        instance.setEntityManager(mockEntityManager);
        
        when(mockDAOFactory.createNotificationDAO()).thenReturn(mockNotificationJpaController);
        when(mockTransactionController.getDAOFactoryForTransaction()).thenReturn(mockDAOFactory);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sendBatchNotifications method, of class NotificationService.
     */
    @Test
    public void testSendBatchNotifications() throws Exception {
        
    }

    /**
     * Test of sendNotification method, of class NotificationService.
     */
    @Test
    public void testSendNotification() throws Exception {
        
    }

    /**
     * Test of sendOnlyEmail method, of class NotificationService.
     */
    @Test
    public void testSendOnlyEmail() throws Exception {
        
    }

    /**
     * Test of getAllNotificationsForPerson method, of class NotificationService.
     */
    @Test
    public void testGetAllNotificationsForPerson() throws Exception {
        
    }

    /**
     * Test of getAllNotificationsFromPerson method, of class NotificationService.
     */
    @Test
    public void testGetAllNotificationsFromPerson() throws Exception {
        
    }

    /**
     * Test of sendAllEmailsOfQueuedNotifications method, of class NotificationService.
     */
    @Test
    public void testSendAllEmailsOfQueuedNotifications() throws Exception {
        
    }
    
}
