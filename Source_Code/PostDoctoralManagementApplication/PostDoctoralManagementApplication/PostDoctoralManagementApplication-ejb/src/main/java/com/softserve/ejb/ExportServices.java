/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Person;
import com.softserve.requestWrappers.ExportRequest;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class ExportServices implements ExportServicesLocal {

    @PersistenceUnit(unitName=com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @Override
    public byte[] exportData(ExportRequest exportRequest) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String exportCVDataToJSON(Cv cv, List<Boolean> exportOptions) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();        
        String cvData = objectMapper.writeValueAsString(cv);
        
        return cvData;
    }
    
    public String exportApplicationDataToJSON(Application application, List<Boolean> exportOptions)
    {
        return "";
    }
    
    public String exportPersonDataToJSON(Person person, List<Boolean> exportOptions)
    {
        return "";
    }
    
    /*
    public String exportDepartmentDataToJSON(Department department, List<Boolean> exportOptions)
    {
        return "";
    }*/
}
