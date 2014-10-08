/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import com.softserve.persistence.DBEntities.FundingCost;
import com.softserve.persistence.DBEntities.FundingReport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class FundingCostTest {
    
    public FundingCostTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetCostID() 
    {
        FundingCost instance = new FundingCost(Long.MIN_VALUE);
        instance.setCostID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getCostID());
    }

    @Test
    public void testSetAndGetAmount() 
    {
        FundingCost instance = new FundingCost(Long.MIN_VALUE);
        instance.setAmount(new Float(1025.54));
        assertEquals(new Float(1025.54),instance.getAmount());
    }

    @Test
    public void testSetAndGetProvider() 
    {
        FundingCost instance = new FundingCost(Long.MIN_VALUE);
        instance.setProvider("Man on the moon");
        assertEquals("Man on the moon",instance.getProvider());
    }

    @Test
    public void testSetAndGetType() 
    {
        FundingCost instance = new FundingCost(Long.MIN_VALUE);
        instance.setType(com.softserve.auxillary.constants.PersistenceConstants.FUNDINGCOST_TYPE_CONFERENCE);
        assertEquals(com.softserve.auxillary.constants.PersistenceConstants.FUNDINGCOST_TYPE_CONFERENCE,instance.getType());
    }

    @Test
    public void testSetAndGetFundingReport() 
    {
        FundingCost instance = new FundingCost(Long.MIN_VALUE);
        instance.setFundingReport(new FundingReport(Long.MIN_VALUE));
        assertEquals(new FundingReport(Long.MIN_VALUE),instance.getFundingReport());
    }

    @Test
    public void testEquals() 
    {
        FundingCost instance1 = new FundingCost(Long.MAX_VALUE);
        FundingCost instance2 = new FundingCost(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        FundingCost instance = new FundingCost(new Long(1));
        assertEquals("com.softserve.DBEntities.FundingCost[ costID=" + 1 + " ]", instance.toString());
    }
    
}
