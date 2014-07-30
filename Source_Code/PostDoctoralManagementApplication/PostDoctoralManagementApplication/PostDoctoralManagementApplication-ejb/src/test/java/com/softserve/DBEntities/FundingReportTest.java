/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

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
    public void testSetAndGetFellowshipCost() 
    {
        FundingReport instance = new FundingReport();
        instance.setFellowshipCost(1000f);
        assertEquals(new Float(1000f),instance.getFellowshipCost());
    }

    @Test
    public void testSetAndGetTravelCost() 
    {
        FundingReport instance = new FundingReport();
        instance.setTravelCost(1000f);
        assertEquals(new Float(1000f),instance.getTravelCost());
    }

    @Test
    public void testSetAndGetRunningCost() 
    {
        FundingReport instance = new FundingReport();
        instance.setRunningCost(1000f);
        assertEquals(new Float(1000f),instance.getRunningCost());
    }

    @Test
    public void testSetAndGetOperatingCost() 
    {
        FundingReport instance = new FundingReport();
        instance.setOperatingCost(1000f);
        assertEquals(new Float(1000f),instance.getOperatingCost());
    }

    @Test
    public void testSetAndGetEquipmentCost() 
    {
        FundingReport instance = new FundingReport();
        instance.setEquipmentCost(1000f);
        assertEquals(new Float(1000f),instance.getEquipmentCost());
    }

    @Test
    public void testSetAndGetConferenceCost() 
    {
        FundingReport instance = new FundingReport();
        instance.setConferenceCost(1000f);
        assertEquals(new Float(1000f),instance.getConferenceCost());
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
