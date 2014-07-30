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
public class LocationTest {
    
    public LocationTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetLocationID() 
    {
        Location instance = new Location(Long.MIN_VALUE);
        instance.setLocationID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getLocationID());
    }

    @Test
    public void testSetAndGetInstitution() 
    {
        Location instance = new Location();
        instance.setInstitution("University of Cape Town");
        assertEquals("University of Cape Town",instance.getInstitution());
    }

    @Test
    public void testSetAndGetFaculty() 
    {
        Location instance = new Location();
        instance.setFaculty("EBIT");
        assertEquals("EBIT",instance.getFaculty());
    }

    @Test
    public void testSetAndGetDepartment() 
    {
        Location instance = new Location();
        instance.setDepartment("Department of Computer Science");
        assertEquals("Department of Computer Science",instance.getDepartment());
    }

    @Test
    public void testSetAndGetEmployeeInformationList() 
    {
        Location instance = new Location();
        instance.setEmployeeInformationList(new ArrayList<EmployeeInformation>());
        EmployeeInformation employeeInformation = new EmployeeInformation("r12019837");
        instance.getEmployeeInformationList().add(employeeInformation);
        assertEquals(employeeInformation, instance.getEmployeeInformationList().get(0));
        assertTrue((instance.getEmployeeInformationList().size() == 1));
    }

    @Test
    public void testEquals() 
    {
        Location instance1 = new Location(Long.MAX_VALUE);
        Location instance2 = new Location(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        Location instance = new Location(new Long(1));
        assertEquals("com.softserve.DBEntities.Location[ locationID=" + 1 + " ]", instance.toString());
    }
    
}
