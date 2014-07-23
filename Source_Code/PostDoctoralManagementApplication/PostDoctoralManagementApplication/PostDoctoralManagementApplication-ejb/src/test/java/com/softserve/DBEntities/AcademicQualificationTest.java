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
public class AcademicQualificationTest {
    
    public AcademicQualificationTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetQualificationID() 
    {
        AcademicQualification instance = new AcademicQualification();
        instance.setQualificationID(new Long(1));
        assertEquals(new Long(1), instance.getQualificationID());
    }

    @Test
    public void testSetAndGetName() 
    {
        AcademicQualification instance = new AcademicQualification();
        instance.setName("BSc (Computer Science)");
        assertEquals("BSc (Computer Science)", instance.getName());
    }

    @Test
    public void testSetAndGetFieldOfStudy() 
    {
        AcademicQualification instance = new AcademicQualification();
        instance.setFieldOfStudy("Information technology");
        assertEquals("Information technology", instance.getFieldOfStudy());
    }

    @Test
    public void testSetAndGetInstituation() 
    {
        AcademicQualification instance = new AcademicQualification();
        instance.setInstituation("University of Pretoria");
        assertEquals("University of Pretoria", instance.getInstituation());
    }

    @Test
    public void testSetAndGetYearObtained() 
    {
        AcademicQualification instance = new AcademicQualification();
        instance.setYearObtained(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11), instance.getYearObtained());
    }

    @Test
    public void testSetAndGetDistinctions()
    {
        AcademicQualification instance = new AcademicQualification();
        instance.setDistinctions((short) 8);
        assertEquals(new Short((short)8), instance.getDistinctions());
    }

    @Test
    public void testSetAndGetCvID() 
    {
        AcademicQualification instance = new AcademicQualification();
        instance.setCvID(new Cv("u12019837"));
        assertEquals(new Cv("u12019837"), instance.getCvID());
    }

    @Test
    public void testEquals() 
    {
        AcademicQualification instance1 = new AcademicQualification(new Long(1));
        AcademicQualification instance2 = new AcademicQualification(new Long(1));
        
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        AcademicQualification instance = new AcademicQualification(new Long(1));
        assertEquals("com.softserve.DBEntities.AcademicQualification[ qualificationID=" + 1 + " ]", instance.toString());
    }
    
}
