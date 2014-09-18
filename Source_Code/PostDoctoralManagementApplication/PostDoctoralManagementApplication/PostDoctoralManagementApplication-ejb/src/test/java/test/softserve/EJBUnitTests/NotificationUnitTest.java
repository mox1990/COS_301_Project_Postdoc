/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.EJBUnitTests;

import com.softserve.DBDAO.NotificationJpaController;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    
    //@Test
    public void testSendBatchNotifications() {
        NotificationServiceMockUnit instance = new NotificationServiceMockUnit();
        
        NotificationJpaController mockNotificationJpaController = mock(NotificationJpaController.class);
        
        instance.setNDAO(mockNotificationJpaController);
        
        Notification mockNotification = mock(Notification.class);
        when(mockNotification.getReciever()).thenReturn(new Person("u12236731"));
        List<Notification> mockNotifications = Arrays.asList(mockNotification);
        
        try
        {
            //instance.sendBatchNotifications(mockNotifications, true);            
            verify(mockNotificationJpaController, times(3)).create(mockNotification);
        }
        catch (Exception ex)
        {
            //fail("An exception occured");
        }
    }
    //@Test
    public void testSendNotificationWithoutEmail() {
        NotificationServiceMockUnit instance = new NotificationServiceMockUnit();
        
        NotificationJpaController mockNotificationJpaController = mock(NotificationJpaController.class);
        
        instance.setNDAO(mockNotificationJpaController);
        
        Notification mockNotification = mock(Notification.class);
        
        try
        {
            //instance.sendNotification(mockNotification, false);
            verify(mockNotificationJpaController).create(mockNotification);
        }
        catch (Exception ex)
        {
            //fail("An exception occured");
        }
    }
    
     //@Test
    public void testSendNotificationWithEmail() {
        NotificationServiceMockUnit instance = new NotificationServiceMockUnit();
        
        NotificationJpaController mockNotificationJpaController = mock(NotificationJpaController.class);
        
        instance.setNDAO(mockNotificationJpaController);
        
        Notification mockNotification = mock(Notification.class);
        when(mockNotification.getReciever()).thenReturn(new Person("u12236731"));
        try
        {
            //instance.sendNotification(mockNotification, true);
            verify(mockNotificationJpaController).create(mockNotification);
        }
        catch (Exception ex)
        {
           //fail("An exception occured");
        }
    }
    
    public void testFindAll()
    {
         NotificationServiceMockUnit instance = new NotificationServiceMockUnit();
        
        NotificationJpaController mockNotificationJpaController = mock(NotificationJpaController.class);
        
        instance.setNDAO(mockNotificationJpaController);
        
        Notification mockNotification = mock(Notification.class);
        
        try
        {
            //instance.findAll();
            //verify(mockNotificationJpaController).create(mockNotification);
        }
        catch (Exception ex)
        {
            //fail("An exception occured");
        }
    }
    
    
    public void testFindByID()
    {
         NotificationServiceMockUnit instance = new NotificationServiceMockUnit();
        
        NotificationJpaController mockNotificationJpaController = mock(NotificationJpaController.class);
        
        instance.setNDAO(mockNotificationJpaController);
        
        Notification mockNotification = mock(Notification.class);
        
        
        try 
        {
            //instance.
        }
        catch(Exception ex)
        {
            
        }
    }
    
    
    public void testFindBySubject()
    {
         NotificationServiceMockUnit instance = new NotificationServiceMockUnit();
        
        NotificationJpaController mockNotificationJpaController = mock(NotificationJpaController.class);
        
        instance.setNDAO(mockNotificationJpaController);
        
        Notification mockNotification = mock(Notification.class);
        
        
        try
        {
            //instance.findBySubject(mockNotification.getSubject());
         //   verify(mockNotificationJpaController).create(mockNotification);
        }
        catch (Exception ex)
        {
            //fail("An exception occured");
        }
    }
    
    
    public void testFindByTimestamp()
    {
         NotificationServiceMockUnit instance = new NotificationServiceMockUnit();
        
        NotificationJpaController mockNotificationJpaController = mock(NotificationJpaController.class);
        
        instance.setNDAO(mockNotificationJpaController);
        
        Notification mockNotification = mock(Notification.class);
        
        Timestamp time = mock(Timestamp.class);
        try
        {
            //instance.findByTimestamp(time);
            //verify(mockNotificationJpaController).create(mockNotification);
        }
        catch (Exception ex)
        {
            //fail("An exception occured");
        }
    }
    
   public void testFindByRange()
   {
        NotificationServiceMockUnit instance = new NotificationServiceMockUnit();
        
        NotificationJpaController mockNotificationJpaController = mock(NotificationJpaController.class);
        
        instance.setNDAO(mockNotificationJpaController);
        
        Notification mockNotification = mock(Notification.class);
        Timestamp timeS = mock(Timestamp.class);
        Timestamp timeE = mock(Timestamp.class);
        try
        {
            //instance.findBetweenRange(timeS, timeE);
        }
        catch (Exception ex)
        {
            //fail("An exception occured");
        } 
    }
    
    
}
