/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.EligiblityReport;
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
public class EligiblityReportTest {
    
    public EligiblityReportTest() {
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
        EligiblityReport instance = new EligiblityReport();
        instance.setReportID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getReportID());
    }

    @Test
    public void testSetAndGetEligiblityCheckDate() 
    {
        EligiblityReport instance = new EligiblityReport();
        instance.setEligiblityCheckDate(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getEligiblityCheckDate());
    }

    @Test
    public void testSetAndGetApplication() 
    {
        EligiblityReport instance = new EligiblityReport();
        Application application = new Application((long) 1);
        instance.setApplication(application);
        assertEquals(application, instance.getApplication());
    }

    @Test
    public void testSetAndGetEligiblityChecker() 
    {
        EligiblityReport instance = new EligiblityReport();
        Person person = new Person("r12019837");
        instance.setEligiblityChecker(person);
        assertEquals(person, instance.getEligiblityChecker());
    }

    @Test
    public void testEquals() 
    {
        EligiblityReport instance1 = new EligiblityReport(Long.MAX_VALUE);
        EligiblityReport instance2 = new EligiblityReport(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        EligiblityReport instance = new EligiblityReport(new Long(1));
        assertEquals("com.softserve.DBEntities.EligiblityReport[ reportID=" + 1 + " ]", instance.toString());
    }
    
}
