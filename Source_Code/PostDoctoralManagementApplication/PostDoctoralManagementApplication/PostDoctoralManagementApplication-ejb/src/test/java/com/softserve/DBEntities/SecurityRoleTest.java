/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.persistence.DBEntities.Person;
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
public class SecurityRoleTest {
    
    public SecurityRoleTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetRoleID() 
    {
        SecurityRole instance = new SecurityRole();
        instance.setRoleID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getRoleID());
    }

    @Test
    public void testSetAndGetName() 
    {
        SecurityRole instance = new SecurityRole();
        instance.setName("High level");
        assertEquals("High level",instance.getName());
    }

    @Test
    public void testSetAndGetRoleMask() 
    {
        SecurityRole instance = new SecurityRole();
        instance.setRoleMask(1);
        assertEquals(1,instance.getRoleMask());
    }

    @Test
    public void testSetAndGetPersonList() 
    {
        SecurityRole instance = new SecurityRole();
        instance.setPersonList(new ArrayList<Person>());
        Person person = new Person("r12019837");
        instance.getPersonList().add(person);
        assertEquals(person, instance.getPersonList().get(0));
        assertTrue((instance.getPersonList().size() == 1));
    }

    @Test
    public void testEquals() 
    {
        SecurityRole instance1 = new SecurityRole(Long.MAX_VALUE);
        SecurityRole instance2 = new SecurityRole(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        SecurityRole instance = new SecurityRole(new Long(1));
        assertEquals("com.softserve.DBEntities.SecurityRole[ roleID=" + 1 + " ]", instance.toString());
    }
    
}
