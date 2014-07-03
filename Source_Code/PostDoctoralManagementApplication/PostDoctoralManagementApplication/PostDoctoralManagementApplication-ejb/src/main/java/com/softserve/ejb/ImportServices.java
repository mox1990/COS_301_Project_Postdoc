/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBEntities.Application;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Generically methods to import data.
 * @author kgothatso
 */
@Stateless
public class ImportServices implements ImportServicesLocal {
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    public void importCSVData(BufferedReader csvInput, Class<?> entity) throws IOException, IllegalAccessException, Exception
    {
        List<String> headers = new ArrayList<>();
        
        if(csvInput.ready())
        {
            String line = csvInput.readLine();
            headers.addAll(Arrays.asList(line.split(",")));

            while(csvInput.ready())
            {
                line = csvInput.readLine();
                importCSVEntityData(entity, line, headers);
            }
        }
    }
    
    private void importCSVEntityData(Class<?> entity, String row, List<String> headers) throws IllegalArgumentException, IllegalAccessException, Exception 
    {
        Field[] fields = entity.getFields();
        Application application = new Application();
        String[] attrib = row.split(",");
        
        for(Field field: fields)
        {
            field.set(application, attrib[headers.indexOf(field.getName())]);
        }
        
        // TODO: Generic way to gain a controller...
        getApplicationDAO().create(application);
    }
    
    // TODO: Excel document addition...
}
