/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NotificationTest {
    
    public NotificationTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetNotificationID() 
    {
        Notification instance = new Notification();
        instance.setNotificationID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getNotificationID());
    }

    @Test
    public void testSetAndGetSubject() 
    {
        Notification instance = new Notification();
        instance.setSubject("Message spam");
        assertEquals("Message spam",instance.getSubject());
    }

    @Test
    public void testSetAndGetMessage() 
    {
        Notification instance = new Notification();
        instance.setMessage("bacon spam");
        assertEquals("bacon spam",instance.getMessage());
    }

    @Test
    public void testSetAndGetTimestamp() 
    {
        Notification instance = new Notification();
        instance.setTimestamp(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getTimestamp());
    }

    @Test
    public void testSetAndGetSenderID() 
    {
        Notification instance = new Notification();
        Person person = new Person("r12019837");
        instance.setSenderID(person);
        assertEquals(person, instance.getSenderID());
    }

    @Test
    public void testSetAndGetRecieverID() 
    {
        Notification instance = new Notification();
        Person person = new Person("r12019837");
        instance.setRecieverID(person);
        assertEquals(person, instance.getRecieverID());
    }

    @Test
    public void testEquals() 
    {
        Notification instance1 = new Notification(Long.MAX_VALUE);
        Notification instance2 = new Notification(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        Notification instance = new Notification(new Long(1));
        assertEquals("com.softserve.DBEntities.Notification[ notificationID=" + 1 + " ]", instance.toString());
    }
    
}
