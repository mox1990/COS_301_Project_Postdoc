/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.annotationprocessors;

import auto.softserve.XMLEntities.PrePostConditional.Methodinfo;
import auto.softserve.XMLEntities.PrePostConditional.Prepostconditionalmethods;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.softserve.auxiliary.annotations.PrePostConditionalMethod;
import java.io.File;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.*;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@SupportedAnnotationTypes("com.softserve.auxiliary.annotations.PrePostConditionalMethod")
@SupportedSourceVersion(SourceVersion.RELEASE_7) 
public class PrePostConditionalAnnotationProcessor extends AbstractProcessor {
    
    public final String TARGET_DIR = "..\\PostDoctoralManagementApplication-web\\src\\main\\resources\\META-INF\\prepostconfig.xml";
    public final String SOURCE_DIR = "";
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) 
    {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Starting PrePostConditionalAnnotationProcessor...");
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        
        if(!System.getProperty("user.dir").endsWith("ejb"))
        {
            return false;
        }
        
        System.out.println("");
        
        Messager messager = processingEnv.getMessager();
        
        String config = "";
        Prepostconditionalmethods prepostconditionalmethods = null;
        
        try
        { 
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
            //xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            prepostconditionalmethods = xmlMapper.readValue(new File(TARGET_DIR), Prepostconditionalmethods.class);
        }
        catch(Exception ex)
        {
            System.out.println("PrePostConditionalAnnotationProcessor cannot find or access config file. Exception: " + ex.toString());
            System.out.println("Will rewrite contents at end of pass...");
        }
        
        if(prepostconditionalmethods == null)
        {
            prepostconditionalmethods = new Prepostconditionalmethods();
        }
        
        System.out.println("");
       
        System.out.println("Elements found:");
        System.out.println("=======================");
        
        List<Methodinfo> methodinfos = prepostconditionalmethods.getMethod();
        
        for(Element element : roundEnv.getElementsAnnotatedWith(PrePostConditionalMethod.class))
        {            
            System.out.println("    Element name: " + element.getSimpleName().toString());
            System.out.println("    Class which method is in: " + element.getEnclosingElement().toString());
            System.out.println("    Element kind: " + element.getKind().toString());
            System.out.println("    Element modifiers: " + element.getModifiers().toString());
            
            Methodinfo methodinfo = new Methodinfo();
            methodinfo.setClazz(element.getEnclosingElement().toString());
            methodinfo.setName(element.getSimpleName().toString());
            methodinfo.setPostcode("");
            methodinfo.setPrecode("");
            
            methodinfos.add(methodinfo);
            
            System.out.println("    Added method");
            
            System.out.println("");
        }
        
        System.out.println("=======================");
        
        try
        {  
            
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
            //xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            System.out.println("XML Config produced: " + xmlMapper.writeValueAsString(prepostconditionalmethods));
            xmlMapper.writeValue(new File(TARGET_DIR), prepostconditionalmethods);            
            System.out.println("Target directory: " + TARGET_DIR);
        }
        catch(Exception ex)
        {
            System.out.println("PrePostConditionalAnnotationProcessor cannot write to config file. Exception: " + ex.toString());
        }
        
        System.out.println("PrePostConditionalAnnotationProcessor stopped");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("");
        
        return true;
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() 
    {
      return SourceVersion.latestSupported();
    }
}
