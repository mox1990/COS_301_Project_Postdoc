/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.annotationprocessors;

import auto.softserve.XMLEntities.PrePostConditional.Methodinfo;
import auto.softserve.XMLEntities.PrePostConditional.Prepostconditionalmethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class PrePostConditionalAnnotationProcessorTest {
    
    public PrePostConditionalAnnotationProcessorTest() {
    }

    @Test
    public void testProcess() throws JsonProcessingException, IOException {
        Prepostconditionalmethods prepostconditionalmethods = new Prepostconditionalmethods();   
        List<Methodinfo> methodinfos = prepostconditionalmethods.getMethod();
        
        XmlMapper xmlMapper = new XmlMapper();
        Methodinfo methodinfo = new Methodinfo();
        methodinfo.setClazz("class");
        methodinfo.setName("Method");
        methodinfo.setPostcode("s");
        methodinfo.setPrecode("d");

        methodinfos.add(methodinfo);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String msg = xmlMapper.writeValueAsString(prepostconditionalmethods);
        System.out.println(msg);
        prepostconditionalmethods = xmlMapper.readValue(msg, Prepostconditionalmethods.class);
    
    }

    @Test
    public void testGetSupportedSourceVersion() {
    }
    
}
