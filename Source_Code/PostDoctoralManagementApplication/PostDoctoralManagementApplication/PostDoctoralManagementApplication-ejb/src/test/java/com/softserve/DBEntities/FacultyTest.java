/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class FacultyTest {
    
    public FacultyTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetFacultyID() 
    {
        Faculty instance = new Faculty(Long.MIN_VALUE);
        instance.setFacultyID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getFacultyID());
    }
    
    @Test
    public void testSetAndGetName() 
    {
        Faculty instance = new Faculty();
        instance.setName("EBIT");
        assertEquals("EBIT",instance.getName());
    }

    @Test
    public void testSetAndGetInstitution() 
    {
        Faculty instance = new Faculty(Long.MIN_VALUE);
        instance.setInstitution(new Institution(Long.MIN_VALUE));
        assertEquals(new Institution(Long.MIN_VALUE),instance.getInstitution());
    }

    @Test
    public void testSetAndGetDepartmentList() 
    {
        Faculty instance = new Faculty();        
        instance.setDepartmentList(new ArrayList<Department>());
        Department c = new Department(Long.MAX_VALUE);
        instance.getDepartmentList().add(c);
        assertEquals(c, instance.getDepartmentList().get(0));
        assertTrue((instance.getDepartmentList().size() == 1));
    }

    @Test
    public void testEquals() 
    {
        Faculty instance1 = new Faculty(Long.MAX_VALUE);
        Faculty instance2 = new Faculty(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        Faculty instance = new Faculty(new Long(1));
        assertEquals("com.softserve.DBEntities.Faculty[ facultyID=" + 1 + " ]", instance.toString());
    }
    
}
