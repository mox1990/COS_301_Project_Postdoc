/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.system.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Gives a generic implementation of the Report generationservice
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class ReportServices implements ReportServicesLocal 
{    
    protected Connection getConnection() throws SQLException, ClassNotFoundException
    {
        // This checks to see if the MySQL driver is avaible to use
        Class.forName("com.mysql.jdbc.Driver");
         
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/PostDoc_DB?zeroDateTimeBehavior=convertToNull" 
                + "&user=root&password=root");
    }
    
    // Just for Demo purposes for now
    public void exportPersonsToPdf(Session session) throws JRException, ClassNotFoundException, SQLException, InterruptedException
    {
        JasperReport jasperReport = JasperCompileManager.compileReport("Person.xml"); // Still need to locate file in Netbeans... (I put it in the source code folder for easy access... gonna move it soon)
        createReportInPdf(jasperReport);
    }
    
    private byte[] createReportInPdf(JasperReport jasperReport) throws JRException, SQLException, ClassNotFoundException, InterruptedException
    {
        // Create a map of parameters to pass to the report.
        Map parameters = new HashMap();
        
        //parameters.put("ReportTitle", "Basic JasperReport"); // Dynamic data like user name and time goes here

        // Create JasperPrint using fillReport() method which fills the report 
        // with data. This data is transperantly accessed via a SQL statement in the XML
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, getConnection());
        // You can use JasperPrint to create PDF
        
        JasperViewer.viewReport(jasperPrint); // Testing purposes
        Thread.sleep(5000); // To allow view of the report
        return JasperExportManager.exportReportToPdf(jasperPrint); // Returns byte stream...
        
    }
}
