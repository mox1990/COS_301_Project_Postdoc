/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.annotationprocessors;

import auto.softserve.XMLEntities.PrePostConditional.Methodinfo;
import auto.softserve.XMLEntities.PrePostConditional.Prepostconditionalmethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.softserve.auxiliary.annotations.PrePostConditionalMethod;
import com.softserve.auxiliary.requestresponseclasses.MethodParameters;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.*;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@SupportedAnnotationTypes("com.softserve.auxiliary.annotations.PrePostConditionalMethod")
@SupportedSourceVersion(SourceVersion.RELEASE_7) 
public class PrePostConditionalAnnotationProcessor extends AbstractProcessor {
    
    public String TARGET_DIR = ".\\src\\main\\resources\\META-INF\\prepostconfig.xml";
    public final String SOURCE_DIR = "";
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) 
    {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Starting PrePostConditionalAnnotationProcessor...");
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        
        if(System.getProperty("user.dir").endsWith("ejb"))
        {
            TARGET_DIR = ".\\src\\main\\resources\\META-INF\\prepostconfig.xml";
        }
        else
        {
            TARGET_DIR = ".\\PostDoctoralManagementApplication-ejb\\src\\main\\resources\\META-INF\\prepostconfig.xml";
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
        
        List<Methodinfo> methodinfos = new ArrayList<Methodinfo>();
        methodinfos.addAll(prepostconditionalmethods.getMethod());
        
        for(Element element : roundEnv.getElementsAnnotatedWith(PrePostConditionalMethod.class))
        {            
            System.out.println("    Element name: " + element.getSimpleName().toString());
            System.out.println("    Class which method is in: " + element.getEnclosingElement().toString());
            System.out.println("    Element kind: " + element.getKind().toString());
            System.out.println("    Element modifiers: " + element.getModifiers().toString());
            ExecutableElement executableElement = (ExecutableElement) element;
            
            System.out.println("    Element parameters: " + executableElement.getParameters().toString());
            
            Methodinfo methodinfo = new Methodinfo();
            methodinfo.setClazz(element.getEnclosingElement().toString());
            methodinfo.setName(element.getSimpleName().toString());
            MethodParameters methodParameters = new MethodParameters();
            List<String> params = new ArrayList<String>();
            for(VariableElement variableElement : executableElement.getParameters())
            {
                params.add(variableElement.asType().toString());
            }
            String [] ps = new String[1];            
            methodParameters.setParameters(params.toArray(ps));
            
            ObjectMapper objectMapper = new ObjectMapper();
            try 
            {
                methodinfo.setParameters(objectMapper.writeValueAsString(methodParameters));
            } 
            catch (JsonProcessingException ex) 
            {
                Logger.getLogger(PrePostConditionalAnnotationProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            boolean found = false;
            System.out.println("    Checking if method exists");
            for(Methodinfo methodinfo1 : methodinfos)
            {
                if(methodinfo1.getClazz().equals(methodinfo.getClazz()) && methodinfo1.getName().equals(methodinfo.getName()) && methodinfo1.getParameters().equals(methodinfo.getParameters()))
                {
                    found = true;
                    break;
                }
            }
            
            if(!found)
            {
                methodinfos.add(methodinfo);
                System.out.println("    Added method");
            }
            
            
            
            System.out.println("");
        }
        
        System.out.println("=======================");
        
        try
        {  
            prepostconditionalmethods.getMethod().clear();
            prepostconditionalmethods.getMethod().addAll(methodinfos);
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
