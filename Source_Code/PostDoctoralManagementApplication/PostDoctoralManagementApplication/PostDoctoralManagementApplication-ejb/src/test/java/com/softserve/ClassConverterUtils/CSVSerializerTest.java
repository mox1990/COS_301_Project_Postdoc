/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ClassConverterUtils;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import java.util.ArrayList;
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
public class CSVSerializerTest {
    
    public CSVSerializerTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetCSVDeliminator() {
    }

    @Test
    public void testGetCSVDeliminator() {
    }

    @Test
    public void testGetClassCSVHeader() {
    }

    @Test
    public void testConvertClassToCSV() 
    {
        Person person = new Person("u12019837", "passowrdd", "John jim", "joe", "mr", "mox.1990@gmail.com", true);
        Application application1 = new Application(new Long(1), new Date());
        application1.setFundingType("Funded");
        person.setApplicationList1(new ArrayList<Application>());
        person.getApplicationList1().add(application1);
        application1.setFellow(person);
        CSVSerializer cSVSerializer = new CSVSerializer(";");
        System.out.println(cSVSerializer.getClassCSVHeader(person, new ArrayList<ConvertProperty>()));
        System.out.println(cSVSerializer.convertClassToCSV(person, new ArrayList<ConvertProperty>(), new  ArrayList<Object>()));
    }
    
}
