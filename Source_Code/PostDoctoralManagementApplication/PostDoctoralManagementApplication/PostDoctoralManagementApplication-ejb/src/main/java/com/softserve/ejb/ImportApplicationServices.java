/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.EndorsementJpaController;
import com.softserve.DBDAO.FundingReportJpaController;
import com.softserve.DBDAO.ProgressReportJpaController;
import com.softserve.DBDAO.RecommendationReportJpaController;
import com.softserve.DBDAO.RefereeReportJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Endorsement;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.RecommendationReport;
import com.softserve.DBEntities.RefereeReport;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Generically methods to import data.
 * @author kgothatso
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ImportApplicationServices implements ImportApplicationServicesLocal {
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    protected EntityManagerFactory emf;
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected RefereeReportJpaController getRefereeReportDAO()
    {
        return new RefereeReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected ProgressReportJpaController getProgressReportDAO()
    {
        return new ProgressReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected RecommendationReportJpaController getRecommendationReportDAO()
    {
        return new RecommendationReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected EndorsementJpaController getEndorsementDAO()
    {
        return new EndorsementJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected FundingReportJpaController getFundingReportDAO()
    {
        return new FundingReportJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    public void importCSVApplicationData(BufferedReader csvInput) throws IOException, IllegalAccessException, Exception
    {
        List<String> headers = new ArrayList<>();
        
        if(csvInput.ready())
        {
            String line = csvInput.readLine();
            headers.addAll(Arrays.asList(line.split(",")));

            while(csvInput.ready())
            {
                line = csvInput.readLine();
                importCSVApplicationEntityData(line, headers);
            }
        }
    }
    
    protected void importCSVApplicationEntityData(String row, List<String> headers) throws Exception  
    {
        Application application = new Application();
        Field[] fields = application.getClass().getFields();
        String[] attrib = row.split(",");
        
        for(Field field: fields)
        {
            try {
                field.set(application, attrib[headers.indexOf(field.getName())]);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ImportApplicationServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        getApplicationDAO().create(application);
    }
    
    public void importCSVRefereeReportData(BufferedReader csvInput) throws IOException, IllegalAccessException, Exception
    {
        List<String> headers = new ArrayList<>();
        
        if(csvInput.ready())
        {
            String line = csvInput.readLine();
            headers.addAll(Arrays.asList(line.split(",")));

            while(csvInput.ready())
            {
                line = csvInput.readLine();
                importCSVRefereeReportEntityData(line, headers);
            }
        }
    }
    
    protected void importCSVRefereeReportEntityData(String row, List<String> headers) throws Exception  
    {
        // TODO: work with the xml spec of the referee report...
        RefereeReport entity = new RefereeReport();
        Field[] fields = entity.getClass().getFields();
        String[] attrib = row.split(",");
        
        for(Field field: fields)
        {
            try {
                field.set(entity, attrib[headers.indexOf(field.getName())]);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ImportApplicationServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        getRefereeReportDAO().create(entity);
    }
    
    public void importCSVProgressReportData(BufferedReader csvInput) throws IOException, IllegalAccessException, Exception
    {
        List<String> headers = new ArrayList<>();
        
        if(csvInput.ready())
        {
            String line = csvInput.readLine();
            headers.addAll(Arrays.asList(line.split(",")));

            while(csvInput.ready())
            {
                line = csvInput.readLine();
                importCSVProgressReportEntityData(line, headers);
            }
        }
    }
    
    protected void importCSVProgressReportEntityData(String row, List<String> headers) throws Exception  
    {
        // TODO: work with the xml spec of the referee report...
        ProgressReport entity = new ProgressReport();
        Field[] fields = entity.getClass().getFields();
        String[] attrib = row.split(",");
        
        for(Field field: fields)
        {
            try {
                field.set(entity, attrib[headers.indexOf(field.getName())]);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ImportApplicationServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        getProgressReportDAO().create(entity);
    }
    
    public void importCSVRecommendationReportData(BufferedReader csvInput) throws IOException, IllegalAccessException, Exception
    {
        List<String> headers = new ArrayList<>();
        
        if(csvInput.ready())
        {
            String line = csvInput.readLine();
            headers.addAll(Arrays.asList(line.split(",")));

            while(csvInput.ready())
            {
                line = csvInput.readLine();
                importCSVRecommendationReportEntityData(line, headers);
            }
        }
    }
    
    protected void importCSVRecommendationReportEntityData(String row, List<String> headers) throws Exception  
    {
        // TODO: work with the xml spec of the referee report...
        RecommendationReport entity = new RecommendationReport();
        Field[] fields = entity.getClass().getFields();
        String[] attrib = row.split(",");
        
        for(Field field: fields)
        {
            try {
                field.set(entity, attrib[headers.indexOf(field.getName())]);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ImportApplicationServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        getRecommendationReportDAO().create(entity);
    }
    
    public void importCSVEndorsementData(BufferedReader csvInput) throws IOException, IllegalAccessException, Exception
    {
        List<String> headers = new ArrayList<>();
        
        if(csvInput.ready())
        {
            String line = csvInput.readLine();
            headers.addAll(Arrays.asList(line.split(",")));

            while(csvInput.ready())
            {
                line = csvInput.readLine();
                importCSVEndorsementEntityData(line, headers);
            }
        }
    }
    
    protected void importCSVEndorsementEntityData(String row, List<String> headers) throws Exception  
    {
        // TODO: work with the xml spec of the referee report...
        Endorsement entity = new Endorsement();
        Field[] fields = entity.getClass().getFields();
        String[] attrib = row.split(",");
        
        for(Field field: fields)
        {
            try {
                field.set(entity, attrib[headers.indexOf(field.getName())]);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ImportApplicationServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        getEndorsementDAO().create(entity);
    }
    
    // TODO: Clarify the DRIS Approval Data...
    
    public void importCSVFundingReportData(BufferedReader csvInput) throws IOException, IllegalAccessException, Exception
    {
        List<String> headers = new ArrayList<>();
        
        if(csvInput.ready())
        {
            String line = csvInput.readLine();
            headers.addAll(Arrays.asList(line.split(",")));

            while(csvInput.ready())
            {
                line = csvInput.readLine();
                importCSVFundingReportEntityData(line, headers);
            }
        }
    }
    
    protected void importCSVFundingReportEntityData(String row, List<String> headers) throws Exception  
    {
        // TODO: work with the xml spec of the referee report...
        FundingReport entity = new FundingReport();
        Field[] fields = entity.getClass().getFields();
        String[] attrib = row.split(",");
        
        for(Field field: fields)
        {
            try {
                field.set(entity, attrib[headers.indexOf(field.getName())]);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ImportApplicationServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        getFundingReportDAO().create(entity);
    }
    
    // TODO: Excel document imports...
}
