/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.requestWrappers;

import com.softserve.ClassConverterUtils.CSVSerializer;
import com.softserve.ClassConverterUtils.ClassSerializer;
import com.softserve.ClassConverterUtils.ConvertProperty;
import com.softserve.ClassConverterUtils.JSONSerializer;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Department;
import com.softserve.DBEntities.Person;
import java.util.List;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ExportRequest {
    
    private String type;
    
    private String CSVDelim;
    
    private List<Application> applicationsToBeExported;
    private List<ConvertProperty> applicationExportProperties;
    private List<Cv> cvsToBeExported;
    private List<ConvertProperty> cvExportProperties;
    private List<Department> departmentsToBeExported;
    private List<ConvertProperty> departmentExportProperties;
    private List<Person> personsToBeExported;
    private List<ConvertProperty> personExportProperties;

    public ExportRequest() {
    }
            
    public ExportRequest(List<Application> applicationsToBeExported, List<Cv> cvsToBeExported, List<Department> departmentsToBeExported, List<Person> personsToBeExported) {
        this.applicationsToBeExported = applicationsToBeExported;
        this.cvsToBeExported = cvsToBeExported;
        this.departmentsToBeExported = departmentsToBeExported;
        this.personsToBeExported = personsToBeExported;
    }

    public ExportRequest(List<Application> applicationsToBeExported, List<ConvertProperty> applicationExportProperties, List<Cv> cvsToBeExported, List<ConvertProperty> cvExportProperties, List<Department> departmentsToBeExported, List<ConvertProperty> departmentExportProperties, List<Person> personsToBeExported, List<ConvertProperty> personExportProperties) {
        this.applicationsToBeExported = applicationsToBeExported;
        this.applicationExportProperties = applicationExportProperties;
        this.cvsToBeExported = cvsToBeExported;
        this.cvExportProperties = cvExportProperties;
        this.departmentsToBeExported = departmentsToBeExported;
        this.departmentExportProperties = departmentExportProperties;
        this.personsToBeExported = personsToBeExported;
        this.personExportProperties = personExportProperties;
    }

    public List<Application> getApplicationsToBeExported() {
        return applicationsToBeExported;
    }

    public void setApplicationsToBeExported(List<Application> applicationsToBeExported) {
        this.applicationsToBeExported = applicationsToBeExported;
    }

    public List<Cv> getCvsToBeExported() {
        return cvsToBeExported;
    }

    public void setCvsToBeExported(List<Cv> cvsToBeExported) {
        this.cvsToBeExported = cvsToBeExported;
    }

    public List<Department> getDepartmentsToBeExported() {
        return departmentsToBeExported;
    }

    public void setDepartmentsToBeExported(List<Department> departmentsToBeExported) {
        this.departmentsToBeExported = departmentsToBeExported;
    }

    public List<Person> getPersonsToBeExported() {
        return personsToBeExported;
    }

    public void setPersonsToBeExported(List<Person> personsToBeExported) {
        this.personsToBeExported = personsToBeExported;
    }

    public List<ConvertProperty> getApplicationExportProperties() {
        return applicationExportProperties;
    }

    public void setApplicationExportProperties(List<ConvertProperty> applicationExportProperties) {
        this.applicationExportProperties = applicationExportProperties;
    }

    public List<ConvertProperty> getCvExportProperties() {
        return cvExportProperties;
    }

    public void setCvExportProperties(List<ConvertProperty> cvExportProperties) {
        this.cvExportProperties = cvExportProperties;
    }

    public List<ConvertProperty> getDepartmentExportProperties() {
        return departmentExportProperties;
    }

    public void setDepartmentExportProperties(List<ConvertProperty> departmentExportProperties) {
        this.departmentExportProperties = departmentExportProperties;
    }

    public List<ConvertProperty> getPersonExportProperties() {
        return personExportProperties;
    }

    public void setPersonExportProperties(List<ConvertProperty> personExportProperties) {
        this.personExportProperties = personExportProperties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCSVDelim(String CSVDelim) {
        this.CSVDelim = CSVDelim;
    }

    public String getCSVDelim() {
        return CSVDelim;
    }
    
    public ClassSerializer getClassSerializerForExport()
    {
        if(type.equals("CSV"))
        {
            return new CSVSerializer(CSVDelim);
        }
        else if (type.equals("JSON"))
        {
           return new JSONSerializer();  
        }

        return null;

    }
    
}
