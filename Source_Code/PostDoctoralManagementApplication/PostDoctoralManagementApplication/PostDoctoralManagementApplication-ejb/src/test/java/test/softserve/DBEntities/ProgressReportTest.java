/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package test.softserve.DBEntities;

import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.ProgressReport;
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
public class ProgressReportTest {
    
    public ProgressReportTest() {
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
        ProgressReport instance = new ProgressReport();
        instance.setReportID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getReportID());
    }

    @Test
    public void testSetAndGetTimestamp() 
    {
        ProgressReport instance = new ProgressReport();
        instance.setTimestamp(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getTimestamp());
    }

    @Test
    public void testSetAndGetContent() 
    {
        ProgressReport instance = new ProgressReport();
        instance.setContent("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>");
        assertEquals("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>",instance.getContent());
    }

    @Test
    public void testSetAndGetApplication() 
    {
        ProgressReport instance = new ProgressReport();
        Application app = new Application(Long.MIN_VALUE);
        instance.setApplication(app);
        assertEquals(app, instance.getApplication());
    }

    @Test
    public void testEquals() 
    {
        ProgressReport instance1 = new ProgressReport(Long.MAX_VALUE);
        ProgressReport instance2 = new ProgressReport(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        ProgressReport instance = new ProgressReport(new Long(1));
        assertEquals("com.softserve.DBEntities.ProgressReport[ reportID=" + 1 + " ]", instance.toString());
    }
    
}
