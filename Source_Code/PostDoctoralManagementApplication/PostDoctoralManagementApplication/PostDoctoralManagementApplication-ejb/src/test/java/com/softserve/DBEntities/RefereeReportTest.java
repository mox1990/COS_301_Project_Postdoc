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
public class RefereeReportTest {
    
    public RefereeReportTest() {
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
        RefereeReport instance = new RefereeReport();
        instance.setReportID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getReportID());
    }

    @Test
    public void testSetAndGetTimestamp() 
    {
        RefereeReport instance = new RefereeReport();
        instance.setTimestamp(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getTimestamp());
    }

    @Test
    public void testSetAndGetContent() 
    {
        RefereeReport instance = new RefereeReport();
        instance.setContent("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>");
        assertEquals("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>",instance.getContent());
    }

    @Test
    public void testSetAndGetApplicationID() 
    {
        RefereeReport instance = new RefereeReport();
        Application app = new Application(Long.MIN_VALUE);
        instance.setApplicationID(app);
        assertEquals(app, instance.getApplicationID());
    }

    @Test
    public void testSetAndGetRefereeID() 
    {
        RefereeReport instance = new RefereeReport();
        Person person = new Person("r12019837");
        instance.setRefereeID(person);
        assertEquals(person, instance.getRefereeID());
    }

    @Test
    public void testEquals() 
    {
        RefereeReport instance1 = new RefereeReport(Long.MAX_VALUE);
        RefereeReport instance2 = new RefereeReport(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        RefereeReport instance = new RefereeReport(new Long(1));
        assertEquals("com.softserve.DBEntities.RefereeReport[ reportID=" + 1 + " ]", instance.toString());
    }
    
}
