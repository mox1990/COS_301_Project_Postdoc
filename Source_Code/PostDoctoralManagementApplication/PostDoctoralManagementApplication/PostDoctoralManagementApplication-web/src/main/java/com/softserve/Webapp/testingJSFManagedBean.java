package com.softserve.Webapp;

/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

import com.softserve.DBDAO.*;
import com.softserve.DBEntities.*;
import com.softserve.XMLUtils.*;
import com.softserve.system.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.fluttercode.datafactory.impl.DataFactory;


/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "testingJSFManagedBean")
@RequestScoped
public class testingJSFManagedBean implements Serializable {
    
    
    
    /**
     * Creates a new instance of testingJSFManagedBean
     */
    public testingJSFManagedBean() {
        
    }
    
    public void createTestData()
    {
        ArrayList<Person> users = new ArrayList<Person>();
        ArrayList<EmployeeInformation> employeeInformation = new ArrayList<EmployeeInformation>();
        ArrayList<Address> addresses = new ArrayList<Address>();
        ArrayList<Cv> cvs = new ArrayList<Cv>();
        ArrayList<Application> Applications = new ArrayList<Application>();
        DataFactory dataFactory = new DataFactory();
        DBEntitiesFactory dBEntitiesFactory = new DBEntitiesFactory();
        
    }
    
    
}
