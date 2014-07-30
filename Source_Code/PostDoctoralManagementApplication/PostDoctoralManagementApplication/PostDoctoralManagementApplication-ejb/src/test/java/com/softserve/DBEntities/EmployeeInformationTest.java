/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

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
public class EmployeeInformationTest {
    
    public EmployeeInformationTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetEmployeeID() 
    {
        EmployeeInformation instance = new EmployeeInformation();
        instance.setEmployeeID("p12019838");
        assertEquals("p12019838",instance.getEmployeeID());
    }

    @Test
    public void testSetAndGetPosition() 
    {
        EmployeeInformation instance = new EmployeeInformation();
        instance.setPosition("Important");
        assertEquals("Important",instance.getPosition());
    }

    @Test
    public void testSetAndGetDateOfAppointment() 
    {
        EmployeeInformation instance = new EmployeeInformation();
        instance.setDateOfAppointment(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getDateOfAppointment());
    }

    @Test
    public void testSetAndGetAppointmentStatus() 
    {
        EmployeeInformation instance = new EmployeeInformation();
        instance.setAppointmentStatus("Employeed");
        assertEquals("Employeed",instance.getAppointmentStatus());
    }

    @Test
    public void testSetAndGetPerson() 
    {
        EmployeeInformation instance = new EmployeeInformation();
        Person person = new Person("r12019837");
        instance.setPerson(person);
        assertEquals(person, instance.getPerson());
    }
    
    @Test
    public void testSetAndGetLocation() 
    {
        EmployeeInformation instance = new EmployeeInformation();
        Location location = new Location(Long.MAX_VALUE);
        instance.setLocation(location);
        assertEquals(location, instance.getLocation());
    }

    @Test
    public void testSetAndGetPhysicalAddress() 
    {
        EmployeeInformation instance = new EmployeeInformation();
        Address address = new Address(new Long(1));
        instance.setPhysicalAddress(address);
        assertEquals(address, instance.getPhysicalAddress());
    }

    @Test
    public void testEquals() 
    {
        EmployeeInformation instance1 = new EmployeeInformation("p12019838");
        EmployeeInformation instance2 = new EmployeeInformation("p12019838");
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        EmployeeInformation instance = new EmployeeInformation("p12019838");
        assertEquals("com.softserve.DBEntities.EmployeeInformation[ employeeID=" + "p12019838" + " ]", instance.toString());  
    }
    
}
