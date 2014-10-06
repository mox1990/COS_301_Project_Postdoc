/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.nonapplicationservices;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.core.layout.ListLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import com.softserve.ClassConverterUtils.EntityToListConverter;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.auxillary.DynamicReportCreationRequest;
import com.softserve.auxillary.DynamicReportExportRequest;
import com.softserve.auxillary.SelectedColumn;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.jasper.DynamicColumnDataSource;
import com.softserve.jasper.DynamicReportBuilder;
import com.softserve.system.Session;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.common.usermodel.LineStyle;
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

    
    /**
     *
     * @return
     */
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }
    
    protected EntityToListConverter getEntityToListConverterUTIL()
    {
        return new EntityToListConverter();
    }
    

    public ReportServices() {
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public List<Person> loadAllPersonEntities(Session session) throws Exception
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
    @AuditableMethod
    @Override
    public List<Application> loadAllApplicationEntities(Session session) throws Exception 
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

    @Override
    public List<String> getFieldNamesForEntity(Object entity) throws Exception 
    {
        EntityToListConverter entityToListConverter = getEntityToListConverterUTIL();
        
        return entityToListConverter.getEntityFieldNames(entity);
    }
        
    @Override
    public List<String> getConcatenatedFieldNamesForEntities(List<Object> entities) throws Exception
    {
        EntityToListConverter entityToListConverter = getEntityToListConverterUTIL();
        
        return entityToListConverter.getConcatenatedEntityFieldNames(entities);
    }
    
    @Override
    public List<List<String>> convertEntitiesToRowData(List<List<Object>> entities, List<SelectedColumn> propertyMaps) throws Exception
    {
        EntityToListConverter entityToListConverter = getEntityToListConverterUTIL();
        
        return entityToListConverter.convertConcatenatedEntitiesListToListString(entities, propertyMaps);
    }
       
    
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public DynamicReport createDynamicReport(Session session, DynamicReportCreationRequest dynamicReportCreationRequest) throws Exception 
    {
        
        
        FastReportBuilder builder = new FastReportBuilder();
        
        DynamicReport dynamicReport = null;

        for(SelectedColumn selectedColumn : dynamicReportCreationRequest.getSelectedColumns())
        {
            Style style = new Style();
            style.setBorder(new Border(1.0f, Border.BORDER_STYLE_SOLID, Color.black));
            Style hStyle = new Style();
            hStyle.setBorder(new Border(1.0f, Border.BORDER_STYLE_SOLID, Color.black));
            hStyle.setBackgroundColor(Color.gray);
            
            builder.addColumn(selectedColumn.getAlias(),selectedColumn.getActualname(), String.class.getName(), 30, style, hStyle);
        }        
        
        builder.setTitle(dynamicReportCreationRequest.getTitle());
        builder.setSubtitle(dynamicReportCreationRequest.getTitle());
        builder.setUseFullPageWidth(dynamicReportCreationRequest.isUseFullPageWidth());
        builder.setIgnorePagination(true);
        builder.setPrintColumnNames(true);
        
        builder.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y,AutoText.POSITION_FOOTER,AutoText.ALIGNMENT_CENTER);
        builder.addAutoText("Created by Softserve PostDoc", AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_LEFT);
        builder.addAutoText("Generated on " + (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()), AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT);
        dynamicReport = builder.build();
        
        return dynamicReport;
        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public String renderReportAsHTML(Session session, DynamicReport report, DynamicReportExportRequest dynamicReportExportRequest) throws Exception
    {
        HtmlExporter exporter = new HtmlExporter();
        
        JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(report, new ClassicLayoutManager(), dynamicReportExportRequest.getDataSourceForRowData());
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(byteArrayOutputStream));
        exporter.exportReport();

        return byteArrayOutputStream.toString();
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public byte[] renderReportAsPDF(Session session, DynamicReport report, DynamicReportExportRequest dynamicReportExportRequest) throws Exception
    {        
        JRPdfExporter exporter = new JRPdfExporter();
        
        JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(report, new ClassicLayoutManager(), dynamicReportExportRequest.getDataSourceForRowData());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        
        exporter.exportReport();
        
        return byteArrayOutputStream.toByteArray();
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public byte[] renderReportAsMSEXCELSpreadsheet(Session session, DynamicReport report, DynamicReportExportRequest dynamicReportExportRequest) throws Exception
    {
        JRXlsxExporter exporter = new JRXlsxExporter();
        
        JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(report, new ListLayoutManager(), dynamicReportExportRequest.getDataSourceForRowData());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        
        exporter.exportReport();
        
        return byteArrayOutputStream.toByteArray();
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
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
    }*/
}
