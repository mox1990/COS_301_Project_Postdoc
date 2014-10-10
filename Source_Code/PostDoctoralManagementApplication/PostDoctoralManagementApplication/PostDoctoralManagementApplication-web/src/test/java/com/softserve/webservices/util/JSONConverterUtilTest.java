/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.webservices.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.persistence.DBEntities.SecurityRole;
import com.softserve.webservices.auxiliary.Response;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class JSONConverterUtilTest {
    
    public JSONConverterUtilTest() {
    }

    @Test
    public void testObjectToJSON() throws Exception {
        
        Response response = new Response(true, "dsfsdfsdfsdgsgvsv", new Object[0]);
        Person person = new Person("u12019837", "pass", null, "M", "Johb sdadh", "mox.1990@gmail.com", true);
        person.setApplicationList(new ArrayList<Application>());
        person.getApplicationList().add(new Application((long) 1));
        person.getApplicationList().get(0).setPersonList(new ArrayList<Person>());
        person.getApplicationList().get(0).getPersonList().add(person);
        response.addObjectToResults(person);
        
        response.populateClasses();
        System.out.println(JSONConverterUtil.objectToJSON_JACKSON(response));
    }

    @Test
    public void testJSONToObject() throws Exception {
        
        Response response = new Response(true, "dsfsdfsdfsdgsgvsv", new Object[0]);
        Person person = new Person("u12019837", "pass", null, "M", "Johb sdadh", "mox.1990@gmail.com", true);
        person.setApplicationList(new ArrayList<Application>());
        person.getApplicationList().add(new Application((long) 1));
        person.getApplicationList().get(0).setPersonList(new ArrayList<Person>());
        person.getApplicationList().get(0).getPersonList().add(person);
        response.addObjectToResults(person);
        
        response.populateClasses();
        
        Response response1 = JSONConverterUtil.JSONToObject_JACKSON(JSONConverterUtil.objectToJSON_JACKSON(response),Response.class);
        System.out.println("Response " + response1.getResult()[0].getClass());
        response1.populateObjects(); 
        System.out.println(response1.getResult()[0].getClass());
        System.out.println("Response " + JSONConverterUtil.objectToJSON_JACKSON(response1.getResult()));
        System.out.println("Response " + Arrays.asList(response1.getResult()));
    }
    
}
