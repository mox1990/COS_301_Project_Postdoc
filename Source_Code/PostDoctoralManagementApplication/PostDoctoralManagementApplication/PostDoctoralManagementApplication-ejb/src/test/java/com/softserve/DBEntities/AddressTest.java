/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import java.util.ArrayList;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class AddressTest {
    
    public AddressTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetAddressID() 
    {
        Address instance = new Address();
        instance.setAddressID(new Long(1));
        assertEquals(new Long(1), instance.getAddressID());
    }

    @Test
    public void testSetAndGetCountry() 
    {
        Address instance = new Address();
        instance.setCountry("South Africa");
        assertEquals("South Africa", instance.getCountry());
    }

    @Test
    public void testSetAndGetProvince() 
    {
        Address instance = new Address();
        instance.setProvince("Western Cape");
        assertEquals("Western Cape", instance.getProvince());
    }

    @Test
    public void testSetAndGetTownCity() 
    {
        Address instance = new Address();
        instance.setTownCity("Cape Town");
        assertEquals("Cape Town", instance.getTownCity());
    }

    @Test
    public void testSetAndGetStreet() 
    {
        Address instance = new Address();
        instance.setStreet("Johnny Street");
        assertEquals("Johnny Street", instance.getStreet());
    }

    @Test
    public void testSetAndGetStreeNumber() 
    {
        Address instance = new Address();
        instance.setStreeNumber(Integer.MAX_VALUE);
        assertEquals(new Integer(Integer.MAX_VALUE), instance.getStreeNumber());
    }

    @Test
    public void testSetAndGetRoomNumber() 
    {
        Address instance = new Address();
        instance.setRoomNumber("IT 4-59");
        assertEquals("IT 4-59", instance.getRoomNumber());
    }

    @Test
    public void testSetAndGetZippostalCode() 
    {
        Address instance = new Address();
        instance.setZippostalCode("1142");
        assertEquals("1142", instance.getZippostalCode());
    }

    @Test
    public void testSetAndGetEmployeeInformationList() 
    {
        Address instance = new Address();
        instance.setEmployeeInformationList(new ArrayList<EmployeeInformation>());
        EmployeeInformation employeeInformation = new EmployeeInformation("u12019837");
        instance.getEmployeeInformationList().add(employeeInformation);
        assertEquals(employeeInformation, instance.getEmployeeInformationList().get(0));
        assertTrue((instance.getEmployeeInformationList().size() == 1));
    }

    @Test
    public void testSetAndGetPersonList() 
    {
        Address instance = new Address();
        instance.setPersonList(new ArrayList<Person>());
        Person person = new Person("u12019837");
        instance.getPersonList().add(person);
        assertEquals(person, instance.getPersonList().get(0));
        assertTrue((instance.getPersonList().size() == 1));
    }

    @Test
    public void testEquals() 
    {
        Address instance1 = new Address(new Long(1));
        Address instance2 = new Address(new Long(1));
        
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        Address instance = new Address(new Long(1));
        assertEquals("com.softserve.DBEntities.Address[ addressID=" + 1 + " ]", instance.toString());
    }
    
}
