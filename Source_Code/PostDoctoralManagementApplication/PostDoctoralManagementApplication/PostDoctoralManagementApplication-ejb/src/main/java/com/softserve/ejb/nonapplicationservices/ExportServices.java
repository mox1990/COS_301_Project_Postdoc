/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.nonapplicationservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.ClassConverterUtils.CSVSerializer;
import com.softserve.ClassConverterUtils.ClassSerializer;
import com.softserve.ClassConverterUtils.ConvertProperty;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Experience;
import com.softserve.DBEntities.Person;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.requestWrappers.ExportRequest;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
@Stateless
public class ExportServices implements ExportServicesLocal {

    @PersistenceUnit(unitName=com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @Override
    public byte[] exportData(ExportRequest exportRequest) throws Exception 
    {
        String output = "=======================Applications==================\n\r";
        
        for(Application application : exportRequest.getApplicationsToBeExported())
        {
            output += exportApplication(application, exportRequest.getApplicationExportProperties(), exportRequest.getClassSerializerForExport());
        }
        
        output += "\n\r=======================CVS==================\n\r";
        
        for(Cv cv : exportRequest.getCvsToBeExported())
        {
            output += exportCV(cv, exportRequest.getCvExportProperties(), exportRequest.getClassSerializerForExport());
        }
        
        output += "\n\r=======================CVS==================\n\r";
        
        for(Person person : exportRequest.getPersonsToBeExported())
        {
            output += exportPerson(person, exportRequest.getPersonExportProperties(), exportRequest.getClassSerializerForExport());
        }
        
        output += "\n\r=======================Locations==================\n\r";
        
        for(Department department : exportRequest.getDepartmentsToBeExported())
        {
            output += exportDepartment(department, exportRequest.getDepartmentExportProperties(), exportRequest.getClassSerializerForExport());
        }
        
        return output.getBytes();
    }
    
    private String exportApplication(Application application, List<ConvertProperty> convertPropertys, ClassSerializer classSerializer)
    {
        String output = "";
        
        output += classSerializer.convertMembersToHeading(new Application(), convertPropertys) + "\n\r";
        
        output += classSerializer.convertClassToSerial(application, convertPropertys) + "\n\r"; 
        
        
        
        return output;
    }
    
    private String exportCV(Cv cv, List<ConvertProperty> convertPropertys, ClassSerializer classSerializer)
    {
        String output = "";
        
        output += classSerializer.convertMembersToHeading(new Cv(), convertPropertys) + "\n\r";
        
        output += classSerializer.convertClassToSerial(cv, convertPropertys) + "\n\r"; 
        
        output += classSerializer.convertMembersToHeading(new AcademicQualification(), new ArrayList<ConvertProperty>()) + "\n\r";
        
        for (AcademicQualification academicQualification : cv.getAcademicQualificationList())
        {
            output += classSerializer.convertClassToSerial(academicQualification, new ArrayList<ConvertProperty>()) + "\n\r";
        }
        
        output += classSerializer.convertMembersToHeading(new Experience(), new ArrayList<ConvertProperty>()) + "\n\r";
        
        for (Experience experience : cv.getExperienceList())
        {
            output += classSerializer.convertClassToSerial(experience, new ArrayList<ConvertProperty>()) + "\n\r";
        }
        
        return output;
    }
    
    private String exportPerson(Person person, List<ConvertProperty> convertPropertys, ClassSerializer classSerializer)
    {
        String output = "";
        
        output += classSerializer.convertMembersToHeading(new Person(), convertPropertys) + "\n\r";
        
        output += classSerializer.convertClassToSerial(person, convertPropertys) + "\n\r";        
        
        return output;
    }
    
    private String exportDepartment(Department department, List<ConvertProperty> convertPropertys, ClassSerializer classSerializer)
    {
        String output = "";
        
        output += classSerializer.convertClassToSerial(department, convertPropertys) + "\n\r";        
        
        return output;
    }
    
    /*
    public String exportDepartmentDataToJSON(Department department, List<ConvertProperty> exportOptions)
    {
        return "";
    }*/
}
