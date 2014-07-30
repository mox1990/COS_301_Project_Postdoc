/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import java.util.Date;
import javax.validation.constraints.AssertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class AuditLogTest {
    
    public AuditLogTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetEntryID() 
    {
        AuditLog instance = new AuditLog();
        instance.setEntryID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE), instance.getEntryID());
    }

    @Test
    public void testSetAndGetTimestamp() 
    {
        AuditLog instance = new AuditLog();
        instance.setTimestamp(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11), instance.getTimestamp());
    }

    @Test
    public void testSetAndGetAction() 
    {
        AuditLog instance = new AuditLog();
        instance.setAction("Updated database");
        assertEquals("Updated database", instance.getAction());
    }

    @Test
    public void testSetAndGetPersonID() 
    {
        AuditLog instance = new AuditLog();
        Person person = new Person("r12019837");
        instance.setPerson(person);
        assertEquals(person, instance.getPerson());
    }

    @Test
    public void testEquals() 
    {
        AuditLog instance1 = new AuditLog(Long.MAX_VALUE);
        AuditLog instance2 = new AuditLog(Long.MAX_VALUE);
        
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        AuditLog instance = new AuditLog(new Long(1));
        assertEquals("com.softserve.DBEntities.AuditLog[ entryID=" + 1 + " ]", instance.toString());
    }
    
}
