/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.Session;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Gives a generic implementation of the Report generationservice
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ReportServices implements ReportServicesLocal 
{    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB 
    private UserGatewayLocal userGateway;
    
    private final String fs = System.getProperty("file.separator");
    private final String filepath = "Reports" + fs;
    private final JasperReport personReport;
    private final JasperReport applicationReport;
    
    public ReportServices() throws JRException
    {
        personReport = JasperCompileManager.compileReport(System.getProperty("user.home") + fs + "Person.xml");
        applicationReport = JasperCompileManager.compileReport(System.getProperty("user.home") + fs + "Person.xml"); // TODO: Work an application report
    }
    /**
     *
     * @return
     */
    protected UserGatewayLocal getUserGatewayServiceEJB()
    {
        return userGateway;
    }
    
    /**
     *
     * @return
     */
    protected PersonJpaController getPersonDAO()
    {
        return new PersonJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *
     * @return
     */
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    @Override
    public List<Person> getAllPersons()
    {
        return getPersonDAO().findPersonEntities();
    }
    
    @Override
    public List<Application> getAllApplications()
    {
        return getApplicationDAO().findApplicationEntities();
    }
    
    @Override
    public byte[] exportPersonsToPdf(Session session, List<Person> persons) throws Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        Map parameters = new HashMap();
        
        parameters.put("Title", "Basic JasperReport"); // Dynamic data like user name and time goes here

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(persons);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(personReport, parameters, beanColDataSource);
        // You can use JasperPrint to create PDF
        
        return JasperExportManager.exportReportToPdf(jasperPrint); // Returns byte stream...
    }
    
    @Override
    public byte[] exportApplicationToPdf(Session session, List<Application> applications) throws Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        Map parameters = new HashMap();
        
        parameters.put("Title", "Basic JasperReport"); // Dynamic data like user name and time goes here

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(applications);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(applicationReport, parameters, beanColDataSource);
        // You can use JasperPrint to create PDF
        
        return JasperExportManager.exportReportToPdf(jasperPrint); // Returns byte stream...
    }
    
    @Override
    public byte[] exportPersonsToExcel(Session session, List<Person> persons) throws Exception
    {
        System.out.println("Working in: " + System.getProperty("user.home") );
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        Map parameters = new HashMap();
        
        //parameters.put("Title", "Basic JasperReport"); // Dynamic data like user name and time goes here

        // Create JasperPrint using fillReport() method which fills the report 
        // with data. This data is transperantly accessed via a SQL statement in the XML
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(getPersonDAO().findPersonEntities());
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(personReport, parameters, beanColDataSource);
        // You can use JasperPrint to create PDF

        JRXlsExporter exporter = new JRXlsExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "temp.xls");
        // TODO: Check for unique name...
        exporter.exportReport();
            
        Path path = Paths.get("temp.xls");
        
        return Files.readAllBytes(path); // Returns byte stream...
    }
    
    @Override
    public byte[] exportApplicationToExcel(Session session, List<Application> applications) throws Exception
    {
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        // Create a map of parameters to pass to the report.
        Map parameters = new HashMap();
        
        //parameters.put("Title", "Basic JasperReport"); // Dynamic data like user name and time goes here

        // Create JasperPrint using fillReport() method which fills the report 
        // with data. This data is transperantly accessed via a SQL statement in the XML
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(applications);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(applicationReport, parameters, beanColDataSource);
        // You can use JasperPrint to create PDF

        JRXlsExporter exporter = new JRXlsExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,"Person.xls");

        exporter.exportReport();
            
        Path path = Paths.get("Person.xls");
        
        return Files.readAllBytes(path); // Returns byte stream...
    }
}
