/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.NotificationJpaController;
import com.softserve.DBEntities.Notification;
import com.softserve.ejb.NotificationServiceLocal;
import java.sql.Timestamp;
import java.util.Arrays;
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
import test.softserve.MockEJBClasses.NotificationServiceMockUnit;

/**
 *
 * @author kgothatso
 */
public class NotificationUnitTest {
    
    public NotificationUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sendBatchNotifications method, of class NotificationService.
     */
    /*@Test
    public void testSendBatchNotifications() {
        NotificationServiceMockUnit instance = new NotificationServiceMockUnit();
        
        NotificationJpaController mockNotificationJpaController = mock(NotificationJpaController.class);
        
        instance.setNDAO(mockNotificationJpaController);
        
        Notification mockNotification = mock(Notification.class);
        List<Notification> mockNotifications = Arrays.asList(mockNotification, mockNotification, mockNotification);;
        
        try
        {
            instance.sendBatchNotifications(mockNotifications, true);
            
            for(Notification aMockNotification: mockNotifications)
            {
                verify(mockNotificationJpaController).create(aMockNotification);
            }
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }*/

    /**
     * Test of sendNotification method, of class NotificationService.
     */
    @Test
    public void testSendNotificationWithoutEmail() {
        NotificationServiceMockUnit instance = new NotificationServiceMockUnit();
        
        NotificationJpaController mockNotificationJpaController = mock(NotificationJpaController.class);
        
        instance.setNDAO(mockNotificationJpaController);
        
        Notification mockNotification = mock(Notification.class);
        
        try
        {
            instance.sendNotification(mockNotification, false);
            verify(mockNotificationJpaController).create(mockNotification);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }
    
    /**
     * Test of sendNotification method, of class NotificationService.
     */
   /* @Test
    public void testSendNotificationWithEmail() {
        NotificationServiceMockUnit instance = new NotificationServiceMockUnit();
        
        NotificationJpaController mockNotificationJpaController = mock(NotificationJpaController.class);
        
        instance.setNDAO(mockNotificationJpaController);
        
        Notification mockNotification = mock(Notification.class);
        
        try
        {
            instance.sendNotification(mockNotification, true);
            verify(mockNotificationJpaController).create(mockNotification);
        }
        catch (Exception ex)
        {
            fail("An exception occured");
        }
    }*/
    
}
