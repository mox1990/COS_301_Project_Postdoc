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
public class EndorsementTest {
    
    public EndorsementTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetEndorsementID() 
    {
        Endorsement instance = new Endorsement();
        instance.setEndorsementID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getEndorsementID());
    }

    @Test
    public void testSetAndGetTimestamp() 
    {
        Endorsement instance = new Endorsement();
        instance.setTimestamp(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getTimestamp());
    }

    @Test
    public void testSetAndGetRank() 
    {
        Endorsement instance = new Endorsement();
        instance.setRank(1);
        assertEquals(1,instance.getRank());
    }

    @Test
    public void testSetAndGetMotivation() 
    {
        Endorsement instance = new Endorsement();
        instance.setMotivation("Very very very good");
        assertEquals("Very very very good",instance.getMotivation());
    }

    @Test
    public void testSetAndGetDean() 
    {
        Endorsement instance = new Endorsement();
        Person person = new Person("r12019837");
        instance.setDean(person);
        assertEquals(person, instance.getDean());
    }

    @Test
    public void testSetAndGetApplication() 
    {
        Endorsement instance = new Endorsement();
        Application application = new Application((long) 1);
        instance.setApplication(application);
        assertEquals(application, instance.getApplication());
    }

    @Test
    public void testEquals() 
    {
        Endorsement instance1 = new Endorsement(Long.MAX_VALUE);
        Endorsement instance2 = new Endorsement(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString()
    {
        Endorsement instance = new Endorsement(new Long(1));
        assertEquals("com.softserve.DBEntities.Endorsement[ endorsementID=" + 1 + " ]", instance.toString());
    }
    
}
