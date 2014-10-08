/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import com.softserve.persistence.DBEntities.FundingCost;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.FundingReport;
import java.util.ArrayList;
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
public class FundingReportTest {
    
    public FundingReportTest() {
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
        FundingReport instance = new FundingReport(Long.MIN_VALUE);
        instance.setReportID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getReportID());
    }

    @Test
    public void testSetAndGetTimestamp() 
    {
        FundingReport instance = new FundingReport();
        instance.setTimestamp(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getTimestamp());
    }

    @Test
    public void testSetAndGetFundingCostList() 
    {
        FundingReport instance = new FundingReport();        
        instance.setFundingCostList(new ArrayList<FundingCost>());
        FundingCost c = new FundingCost(new Long(1));
        instance.getFundingCostList().add(c);
        assertEquals(c, instance.getFundingCostList().get(0));
        assertTrue((instance.getFundingCostList().size() == 1));
    }


    @Test
    public void testSetAndGetDris() 
    {
        FundingReport instance = new FundingReport();
        Person person = new Person("r12019837");
        instance.setDris(person);
        assertEquals(person, instance.getDris());
    }

    @Test
    public void testSetAndGetApplication() 
    {
        FundingReport instance = new FundingReport();
        Application application = new Application((long) 1);
        instance.setApplication(application);
        assertEquals(application, instance.getApplication());
    }

    @Test
    public void testEquals() 
    {
        FundingReport instance1 = new FundingReport(Long.MAX_VALUE);
        FundingReport instance2 = new FundingReport(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        FundingReport instance = new FundingReport(new Long(1));
        assertEquals("com.softserve.DBEntities.FundingReport[ reportID=" + 1 + " ]", instance.toString());
    }
    
}
