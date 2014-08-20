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
public class ForwardAndRewindReportTest {
    
    public ForwardAndRewindReportTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetReportID() 
    {
        ForwardAndRewindReport instance = new ForwardAndRewindReport(Long.MIN_VALUE);
        instance.setReportID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getReportID());
    }

    @Test
    public void testSetAndGetTimestamp() 
    {
        ForwardAndRewindReport instance = new ForwardAndRewindReport();
        instance.setTimestamp(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getTimestamp());
    }

    @Test
    public void testSetAndGetType() 
    {
        ForwardAndRewindReport instance = new ForwardAndRewindReport();
        instance.setType(com.softserve.constants.PersistenceConstants.FORWARDREWINREPORT_TYPE_FORWARD);
        assertEquals(com.softserve.constants.PersistenceConstants.FORWARDREWINREPORT_TYPE_FORWARD,instance.getType());
    }

    @Test
    public void testSetAndGetReason() 
    {
        ForwardAndRewindReport instance = new ForwardAndRewindReport();
        instance.setReason("Hello ss");
        assertEquals("Hello ss",instance.getReason());
    }

    @Test
    public void testSetAndGetApplication() 
    {
        ForwardAndRewindReport instance = new ForwardAndRewindReport();
        Application app = new Application(Long.MIN_VALUE);
        instance.setApplication(app);
        assertEquals(app, instance.getApplication());
    }

    @Test
    public void testSetAndGetDris() 
    {
        ForwardAndRewindReport instance = new ForwardAndRewindReport();
        Person person = new Person("r12019837");
        instance.setDris(person);
        assertEquals(person, instance.getDris());
    }

    @Test
    public void testEquals() 
    {
        ForwardAndRewindReport instance1 = new ForwardAndRewindReport(Long.MAX_VALUE);
        ForwardAndRewindReport instance2 = new ForwardAndRewindReport(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        ForwardAndRewindReport instance = new ForwardAndRewindReport(new Long(1));
        assertEquals("com.softserve.DBEntities.ForwardAndRewindReport[ reportID=" + 1 + " ]", instance.toString());
    }
    
}
