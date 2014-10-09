/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package test.softserve.DBEntities;

import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.DeclineReport;
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
public class DeclineReportTest {
    
    public DeclineReportTest() {
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
        DeclineReport instance = new DeclineReport();
        instance.setReportID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getReportID());
    }

    @Test
    public void testSetAndGetTimestamp() 
    {
        DeclineReport instance = new DeclineReport();
        instance.setTimestamp(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getTimestamp());
    }

    @Test
    public void testSetAndGetReason() 
    {
        DeclineReport instance = new DeclineReport();
        instance.setReason("VERY BAD");
        assertEquals("VERY BAD",instance.getReason());
    }

    @Test
    public void testSetAndGetApplication() 
    {
        DeclineReport instance = new DeclineReport();
        Application application = new Application((long) 1);
        instance.setApplication(application);
        assertEquals(application, instance.getApplication());
    }

    @Test
    public void testSetAndGetCreator() 
    {
        DeclineReport instance = new DeclineReport();
        Person person = new Person("r12019837");
        instance.setCreator(person);
        assertEquals(person, instance.getCreator());
    }

    @Test
    public void testEquals() 
    {
        DeclineReport instance1 = new DeclineReport(Long.MAX_VALUE);
        DeclineReport instance2 = new DeclineReport(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        DeclineReport instance = new DeclineReport(new Long(1));
        assertEquals("com.softserve.DBEntities.DeclineReport[ reportID=" + 1 + " ]", instance.toString());
    }
    
}
