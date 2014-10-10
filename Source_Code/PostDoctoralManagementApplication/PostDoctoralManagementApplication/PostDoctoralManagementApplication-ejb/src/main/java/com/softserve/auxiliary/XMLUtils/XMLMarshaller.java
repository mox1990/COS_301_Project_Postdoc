/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.XMLUtils;


import auto.softserve.XMLEntities.CV.ProgressReportContent;
import auto.softserve.XMLEntities.CV.*;
import auto.softserve.XMLEntities.HOD.*;
import auto.softserve.XMLEntities.application.*;
import auto.softserve.XMLEntities.referee.*;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class XMLMarshaller {
    
    public String marshalApplicationInformationObject(ApplicationInformation applicationInformation) throws JAXBException
    {
        JAXBContext jaxbc = JAXBContext.newInstance("auto.softserve.XMLEntities.application");
        Marshaller marshaller = jaxbc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        StringWriter stringWriter = new StringWriter();
        
        marshaller.marshal(applicationInformation,stringWriter);      
        return stringWriter.toString();
    }
    
    public String marshalRecommendationReportContentObject(RecommendationReportContent recommendationReportContent) throws JAXBException
    {
        JAXBContext jaxbc = JAXBContext.newInstance("auto.softserve.XMLEntities.HOD");
        Marshaller marshaller = jaxbc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        StringWriter stringWriter = new StringWriter();
        
        marshaller.marshal(recommendationReportContent,stringWriter);      
        return stringWriter.toString();
    }
    
    public String marshalReferalReportObject(ReferalReport referalReport) throws JAXBException
    {
        JAXBContext jaxbc = JAXBContext.newInstance("auto.softserve.XMLEntities.referee");
        Marshaller marshaller = jaxbc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        StringWriter stringWriter = new StringWriter();
        
        marshaller.marshal(referalReport,stringWriter);      
        return stringWriter.toString();
    }
    
    public String marshalProgressReportContentObject(ProgressReportContent progressReportContent) throws JAXBException
    {
        JAXBContext jaxbc = JAXBContext.newInstance("auto.softserve.XMLEntities.CV");
        Marshaller marshaller = jaxbc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        StringWriter stringWriter = new StringWriter();
        
        marshaller.marshal(progressReportContent,stringWriter);      
        return stringWriter.toString();
    }
    
    public String marshalResearchOutputObject(ResearchOutput researchOutput) throws JAXBException
    {
        JAXBContext jaxbc = JAXBContext.newInstance("auto.softserve.XMLEntities.CV");
        Marshaller marshaller = jaxbc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        StringWriter stringWriter = new StringWriter();
        
        marshaller.marshal(researchOutput,stringWriter);      
        return stringWriter.toString();
    }
    
    public String marshalOtherContributionsObject(OtherContributions otherContributions) throws JAXBException
    {
        JAXBContext jaxbc = JAXBContext.newInstance("auto.softserve.XMLEntities.CV");
        Marshaller marshaller = jaxbc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        StringWriter stringWriter = new StringWriter();
        
        marshaller.marshal(otherContributions,stringWriter);      
        return stringWriter.toString();
    }
    
    public String marshalAdditionalInformationObject(AdditionalInformation additionalInformation) throws JAXBException
    {
        JAXBContext jaxbc = JAXBContext.newInstance("auto.softserve.XMLEntities.CV");
        Marshaller marshaller = jaxbc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        StringWriter stringWriter = new StringWriter();
        
        marshaller.marshal(additionalInformation,stringWriter);      
        return stringWriter.toString();
    }
}
