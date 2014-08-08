/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.SecurityRole;
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
import java.util.Map;
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
    
    private final String fs = System.getProperty("file.separator");
    private final String filepath = "Reports" + fs;
    
    protected Connection getConnection() throws SQLException, ClassNotFoundException
    {
        // This checks to see if the MySQL driver is avaible to use
        Class.forName("com.mysql.jdbc.Driver");
         
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/PostDoc_DB?zeroDateTimeBehavior=convertToNull" 
                + "&user=root&password=root");
    }
    
    /**
     *
     * @return
     */
    protected UserGateway getUserGatewayServiceEJB()
    {
        return new UserGateway(emf);
    }
    
    /**
     *
     * @return
     */
    protected PersonJpaController getPersonDAO()
    {
        return new PersonJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    // Just for Demo purposes for now
    @Override
    public byte[] exportPersonsToPdf() throws JRException, ClassNotFoundException, SQLException, InterruptedException
    {
        System.out.println("Working in: " + System.getProperty("user.home") );
        //Authenticate user privliges
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_HOD);
        //getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        JasperReport jasperReport = JasperCompileManager.compileReport(System.getProperty("user.home") + fs + "Person.xml"); // Still need to locate file in Netbeans... (I put it in the source code folder for easy access... gonna move it soon)
        // Create a map of parameters to pass to the report.
        Map parameters = new HashMap();
        
        parameters.put("Title", "Basic JasperReport"); // Dynamic data like user name and time goes here

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(getPersonDAO().findPersonEntities());
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
        // You can use JasperPrint to create PDF

        //JasperViewer.viewReport(jasperPrint);
        //Thread.sleep(5000);
        
        return JasperExportManager.exportReportToPdf(jasperPrint); // Returns byte stream...
    }
    
    @Override
    public byte[] exportPersonsToExcel() throws JRException, ClassNotFoundException, SQLException, InterruptedException, IOException
    {
        System.out.println("Working in: " + System.getProperty("user.home") );
        JasperReport jasperReport = JasperCompileManager.compileReport(System.getProperty("user.home") + fs + "Person.xml"); // Still need to locate file in Netbeans... (I put it in the source code folder for easy access... gonna move it soon)
        return createReportInExcel(jasperReport);
    }
    
    private byte[] createReportInExcel(JasperReport jasperReport) throws JRException, SQLException, ClassNotFoundException, InterruptedException, IOException
    {
        // Create a map of parameters to pass to the report.
        Map parameters = new HashMap();
        
        //parameters.put("Title", "Basic JasperReport"); // Dynamic data like user name and time goes here

        // Create JasperPrint using fillReport() method which fills the report 
        // with data. This data is transperantly accessed via a SQL statement in the XML
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, getConnection());
        // You can use JasperPrint to create PDF

        JRXlsExporter exporter = new JRXlsExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.getProperty("user.home") + fs + "Person.xls");

        exporter.exportReport();
            
        Path path = Paths.get(System.getProperty("user.home") + fs + "Person.xls");
        
        return Files.readAllBytes(path); // Returns byte stream...
    }
}
