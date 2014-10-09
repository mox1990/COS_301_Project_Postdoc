/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.annotationprocessors;

import com.softserve.auxiliary.annotations.PrePostConditionalMethod;
import java.lang.reflect.Method;
import java.util.Arrays;
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

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) 
    {
       
        Messager messager = processingEnv.getMessager();
        
        for(Element element : roundEnv.getElementsAnnotatedWith(PrePostConditionalMethod.class))
        {
            
            System.out.println(element.getEnclosingElement().toString());
            System.out.println(element.getEnclosedElements().toString());
            System.out.println(element.getKind().toString());
            System.out.println(element.getModifiers().toString());
            System.out.println(element.getSimpleName().toString());
            
            ExecutableElement methodElement = (ExecutableElement) element;
            System.out.println(methodElement.getParameters().get(0).getSimpleName().toString());
            System.out.println(methodElement.getParameters().get(0).asType().toString());
            
            messager.printMessage(Diagnostic.Kind.NOTE, element.getSimpleName(),element);
        }
        
        
        return true;
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() 
    {
      return SourceVersion.latestSupported();
    }
}
