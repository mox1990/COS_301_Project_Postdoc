/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package test.softserve.DBEntities;

import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.RecommendationReport;
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
public class RecommendationReportTest {
    
    public RecommendationReportTest() {
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
        RecommendationReport instance = new RecommendationReport();
        instance.setReportID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getReportID());
    }

    @Test
    public void testSetAndGetTimestamp() 
    {
        RecommendationReport instance = new RecommendationReport();
        instance.setTimestamp(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getTimestamp());
    }

    @Test
    public void testSetAndGetContent() 
    {
        RecommendationReport instance = new RecommendationReport();
        instance.setContent("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>");
        assertEquals("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>",instance.getContent());
    }

    @Test
    public void testSetAndGetHodID() 
    {
        RecommendationReport instance = new RecommendationReport();
        Person person = new Person("r12019837");
        instance.setHod(person);
        assertEquals(person, instance.getHod());
    }

    @Test
    public void testEquals() 
    {
        RecommendationReport instance1 = new RecommendationReport(Long.MAX_VALUE);
        RecommendationReport instance2 = new RecommendationReport(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        RecommendationReport instance = new RecommendationReport(new Long(1));
        assertEquals("com.softserve.DBEntities.RecommendationReport[ reportID=" + 1 + " ]", instance.toString());
    }
    
}
