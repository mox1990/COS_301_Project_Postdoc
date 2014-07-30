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
public class AmmendRequestTest {
    
    public AmmendRequestTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetRequestID() 
    {
        AmmendRequest instance = new AmmendRequest();
        instance.setRequestID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getRequestID());
    }

    @Test
    public void testSetAndGetTimestamp() 
    {
        AmmendRequest instance = new AmmendRequest();
        instance.setTimestamp(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getTimestamp());
    }

    @Test
    public void testSetAndGetRequest() 
    {
        DeclineReport instance = new DeclineReport();
        instance.setReason("NEED SOME HELP");
        assertEquals("NEED SOME HELP",instance.getReason());
    }

    @Test
    public void testSetAndGetApplication() 
    {
        AmmendRequest instance = new AmmendRequest();
        Application application = new Application((long) 1);
        instance.setApplication(application);
        assertEquals(application, instance.getApplication());
    }

    @Test
    public void testSetAndGetCreator() 
    {
        AmmendRequest instance = new AmmendRequest();
        Person person = new Person("r12019837");
        instance.setCreator(person);
        assertEquals(person, instance.getCreator());
    }

    @Test
    public void testEquals() 
    {
        AmmendRequest instance1 = new AmmendRequest(Long.MAX_VALUE);
        AmmendRequest instance2 = new AmmendRequest(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        AmmendRequest instance = new AmmendRequest(new Long(1));
        assertEquals("com.softserve.DBEntities.AmmendRequest[ requestID=" + 1 + " ]", instance.toString());
    }
    
}
