/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.DBEntities;

import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class CvTest {
    
    public CvTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetAndGetCvID() 
    {
        Cv instance = new Cv();
        instance.setCvID(Long.MIN_VALUE);
        assertEquals(new Long(Long.MIN_VALUE),instance.getCvID());
    }

    @Test
    public void testSetAndGetIdNumber() 
    {
        Cv instance = new Cv();
        instance.setIdNumber("9301135556859");
        assertEquals("9301135556859",instance.getIdNumber());
    }

    @Test
    public void testSetAndGetDateOfBirth() 
    {
        Cv instance = new Cv();
        instance.setDateOfBirth(new Date(2014, 06, 11));
        assertEquals(new Date(2014, 06, 11),instance.getDateOfBirth());
    }

    @Test
    public void testSetAndGetGender() 
    {
        Cv instance = new Cv();
        instance.setGender("Male");
        assertEquals("Male",instance.getGender());
    }

    @Test
    public void testSetAndGetCitizenship() 
    {
        Cv instance = new Cv();
        instance.setCitizenship("South African");
        assertEquals("South African",instance.getCitizenship());
    }
    
    @Test
    public void testSetAndGetNrfRating() 
    {
        Cv instance = new Cv();
        instance.setNrfRating("A");
        assertEquals("A",instance.getNrfRating());
    }

    @Test
    public void testSetAndGetRace() 
    {
        Cv instance = new Cv();
        instance.setRace("Unknown");
        assertEquals("Unknown",instance.getRace());
    }

    @Test
    public void testSetAndGetRecentInstitution() 
    {
        Cv instance = new Cv();
        instance.setRecentInstitution("University of Pretoria");
        assertEquals("University of Pretoria",instance.getRecentInstitution());
    }

    @Test
    public void testSetAndGetResearchOutput() 
    {        
        Cv instance = new Cv();
        instance.setResearchOutput("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>");
        assertEquals("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>",instance.getResearchOutput());
    }

    @Test
    public void testSetAndGetOtherContributions() 
    {
        Cv instance = new Cv();
        instance.setOtherContributions("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>");
        assertEquals("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>",instance.getOtherContributions());
    }

    @Test
    public void testSetAndGetAdditionalInformation() 
    {
        Cv instance = new Cv();
        instance.setAdditionalInformation("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>");
        assertEquals("<?xml version=1.0?> \n <root> <project>WMD<project/><root/>",instance.getAdditionalInformation());
    }

    @Test
    public void testSetAndGetExperienceList() 
    {
        Cv instance = new Cv();
        instance.setExperienceList(new ArrayList<Experience>());
        Experience exp = new Experience(new Long(1));
        instance.getExperienceList().add(exp);
        assertEquals(exp, instance.getExperienceList().get(0));
        assertTrue((instance.getExperienceList().size() == 1));
    }

    @Test
    public void testSetAndGetOwnerID() 
    {
        Cv instance = new Cv();
        Person person = new Person("r12019837");
        instance.setOwnerID(person);
        assertEquals(person, instance.getOwnerID());
    }

    @Test
    public void testSetAndGetAcademicQualificationList() 
    {
        Cv instance = new Cv();
        instance.setAcademicQualificationList(new ArrayList<AcademicQualification>());
        AcademicQualification aq = new AcademicQualification(new Long(1));
        instance.getAcademicQualificationList().add(aq);
        assertEquals(aq, instance.getAcademicQualificationList().get(0));
        assertTrue((instance.getAcademicQualificationList().size() == 1));
    }

    @Test
    public void testEquals() 
    {
        Cv instance1 = new Cv(Long.MAX_VALUE);
        Cv instance2 = new Cv(Long.MAX_VALUE);
        assertTrue(instance1.equals(instance2));
    }

    @Test
    public void testToString() 
    {
        Cv instance = new Cv(new Long(1));
        assertEquals("com.softserve.DBEntities.Cv[ cvID=" + 1 + " ]", instance.toString());
    }
    
}
