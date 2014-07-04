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
public class UpEmployeeInformationTest {
    
    public UpEmployeeInformationTest() {
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
        UpEmployeeInformation instance = new UpEmployeeInformation();
        instance.setEmployeeID("p12019838");
        assertEquals("p12019838",instance.getEmployeeID());
    }

    @Test
    public void testSetAndGetPosition() 
    {
        UpEmployeeInformation instance = new UpEmployeeInformation();
        instance.setPosition("Important");
        assertEquals("Important",instance.getPosition());
    }

    @Test
    public void testSetAndGetDateOfAppointment() 
    {
        UpEmployeeInformation instance = new UpEmployeeInformation();
        instance.setDateOfAppointment(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getDateOfAppointment());
    }

    @Test
    public void testSetAndGetAppointmentStatus() 
    {
        UpEmployeeInformation instance = new UpEmployeeInformation();
        instance.setAppointmentStatus("Employeed");
        assertEquals("Employeed",instance.getAppointmentStatus());
    }

    @Test
    public void testSetAndGetPerson() 
    {
        UpEmployeeInformation instance = new UpEmployeeInformation();
        Person person = new Person("r12019837");
        instance.setPerson(person);
        assertEquals(person, instance.getPerson());
    }

    @Test
    public void testSetAndGetPhysicalAddress() 
    {
        UpEmployeeInformation instance = new UpEmployeeInformation();
        Address address = new Address(new Long(1));
        instance.setPhysicalAddress(address);
        assertEquals(address, instance.getPhysicalAddress());
    }

    @Test
    public void testEquals() 
    {
        UpEmployeeInformation instance1 = new UpEmployeeInformation("p12019838");
        UpEmployeeInformation instance2 = new UpEmployeeInformation("p12019838");
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        UpEmployeeInformation instance = new UpEmployeeInformation("p12019838");
        assertEquals("com.softserve.DBEntities.UpEmployeeInformation[ employeeID=" + "p12019838" + " ]", instance.toString());  
    }
    
}
