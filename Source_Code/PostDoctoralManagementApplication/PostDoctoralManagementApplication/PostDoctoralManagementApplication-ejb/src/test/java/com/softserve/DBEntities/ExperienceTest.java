/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import com.softserve.persistence.DBEntities.Experience;
import com.softserve.persistence.DBEntities.Cv;
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
public class ExperienceTest {
    
    public ExperienceTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetExperienceID() 
    {
        Experience instance = new Experience(Long.MIN_VALUE);
        instance.setExperienceID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getExperienceID());
    }

    @Test
    public void testSetAndGetCapacity() 
    {
        Experience instance = new Experience();
        instance.setCapacity("Sales clark");
        assertEquals("Sales clark",instance.getCapacity());
    }

    @Test
    public void testSetAndGetOrganisation() 
    {
        Experience instance = new Experience();
        instance.setOrganisation("Harry's CIA");
        assertEquals("Harry's CIA",instance.getOrganisation());
    }

    @Test
    public void testSetAndGetStartDate() 
    {
        Experience instance = new Experience();
        instance.setStartDate(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getStartDate());
    }

    @Test
    public void testSetAndGetEndDate() 
    {
        Experience instance = new Experience();
        instance.setEndDate(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getEndDate());
    }

    @Test
    public void testSetAndGetCv() 
    {
        Experience instance = new Experience();
        Cv cv = new Cv("u12019837");
        instance.setCv(cv);
        assertEquals(cv, instance.getCv());
    }

    @Test
    public void testEquals() 
    {
        Experience instance1 = new Experience(Long.MAX_VALUE);
        Experience instance2 = new Experience(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        Experience instance = new Experience(new Long(1));
        assertEquals("com.softserve.DBEntities.Experience[ experienceID=" + 1 + " ]", instance.toString());
    }
    
}
