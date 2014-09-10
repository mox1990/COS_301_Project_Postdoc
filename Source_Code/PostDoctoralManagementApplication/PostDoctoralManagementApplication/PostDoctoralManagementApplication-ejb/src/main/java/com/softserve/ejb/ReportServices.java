/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.annotations.SecuredMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.jasper.DynamicColumnDataSource;
import com.softserve.jasper.DynamicReportBuilder;
import com.softserve.system.Session;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Gives a generic implementation of the Report generationservice
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ReportServices implements ReportServicesLocal 
{    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB 
    private UserGatewayLocal userGateway;
    
    private final String fs = System.getProperty("file.separator");
    private final String filepath = "Reports" + fs;
    private final JasperDesign dynamicPersonReport;
    
    public ReportServices() throws Exception
    {
        //personReport = null;
        //applicationReport = null;
//        InputStream personInputStream = new ByteArrayInputStream(com.softserve.constants.JasperReportTemplateStrings.PERSON.getBytes("UTF-8"));
//        InputStream allPersonInputStream = new ByteArrayInputStream(com.softserve.constants.JasperReportTemplateStrings.ALL_PERSONS.getBytes("UTF-8"));
//        InputStream applicationInputStream = new ByteArrayInputStream(com.softserve.constants.JasperReportTemplateStrings.APPLICATION.getBytes("UTF-8"));
//        InputStream allApplicationInputStream = new ByteArrayInputStream(com.softserve.constants.JasperReportTemplateStrings.ALL_APPLICATIONS.getBytes("UTF-8"));
        InputStream dynamicPersonInputStream = new ByteArrayInputStream(com.softserve.constants.JasperReportTemplateStrings.DYNAMIC_PERSON.getBytes("UTF-8"));
        
//        personReport = JasperCompileManager.compileReport(System.getProperty("user.home") + fs + "Person.jrxml");
//        allPersonReport = JasperCompileManager.compileReport(System.getProperty("user.home") + fs + "AllPersons.jrxml");
//        applicationReport = JasperCompileManager.compileReport(System.getProperty("user.home") + fs + "Application.jrxml"); // TODO: Work an application report
//        allApplicationReport = JasperCompileManager.compileReport(System.getProperty("user.home") + fs + "AllApplications.jrxml");
//        
//        personReport = JasperCompileManager.compileReport(personInputStream);
//        allPersonReport = JasperCompileManager.compileReport(allPersonInputStream);
//        applicationReport = JasperCompileManager.compileReport(applicationInputStream); // TODO: Work an application report
//        allApplicationReport = JasperCompileManager.compileReport(allApplicationInputStream);
        dynamicPersonReport = JRXmlLoader.load(dynamicPersonInputStream);
    }
    
    /**
     *
     * @return
     */
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @Override
    public List<Person> getAllPersons(Session session)
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getDAOFactory(em).createPersonDAO().findPersonEntities();
        }
        finally
        {
            em.close();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @Override
    public List<Person> getAllPersonsWithSecurityRole(Session session, Long role)
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getDAOFactory(em).createPersonDAO().findUserBySecurityRoleWithAccountStatus(new SecurityRole(role), com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_ACTIVE);
        }
        finally
        {
            em.close();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @Override
    public List<Application> getAllApplications(Session session)
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getDAOFactory(em).createApplicationDAO().findApplicationEntities();
        }
        finally
        {
            em.close();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @Override
    public List<Application> getAllApplicationsWithStatus(Session session, String status)
    {
        EntityManager em = emf.createEntityManager();

        try
        {
            return getDAOFactory(em).createApplicationDAO().findAllApplicationsWithStatus(status, 0, Integer.MAX_VALUE);
        }
        finally
        {
            em.close();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @Override
    public byte[] dynamicReport(Session session, List<String> columnHeaders, List<List<String>> rows, String title) throws Exception 
    {
        DynamicReportBuilder reportBuilder = new DynamicReportBuilder(dynamicPersonReport, columnHeaders.size());
        reportBuilder.addDynamicColumns();

        JasperReport jasperReport = JasperCompileManager.compileReport(dynamicPersonReport);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("REPORT_TITLE", title);
        DynamicColumnDataSource pdfDataSource = new DynamicColumnDataSource(columnHeaders, rows);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, pdfDataSource);
        
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @Override
    public byte[] exportSpreadsheetReport(Session session, List<String> columnHeaders, List<List<String>> rows, String title) throws IOException
    {
        // TODO: Write code with a clear head

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();
        Sheet applicationSheet = workbook.createSheet(title);
        
        // Setup headers
        setupSheetHeaders(applicationSheet, columnHeaders);
        
        // Enter data...
        int rIndex = 1; // Adding values from the second row onwards.
        for(List<String> aRow: rows)
        {
            Row row = applicationSheet.createRow(rIndex++);
            addEntityToWorksheetRow(row, aRow);
        }   
        workbook.write(baos);
        return baos.toByteArray();   
    }
    
    private void setupSheetHeaders(Sheet sheet, List<String> columnHeaders)
    {
        Row row = sheet.createRow(0);
        int cIndex = 0;
        
        for(String header: columnHeaders)
        {
            Cell cell = row.createCell(cIndex++);
            cell.setCellValue(header);
        }
    }
    
    private void addEntityToWorksheetRow(Row row, List<String> rows)
    {
        int cIndex = 0;
        
        for(String item: rows)
        {
            Cell cell = row.createCell(cIndex++);
            cell.setCellValue(item);
        }
    }
}
