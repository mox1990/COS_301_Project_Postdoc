/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.nonapplicationservices;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.ListLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import com.softserve.auxiliary.converters.EntityToListConverter;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.auxiliary.annotations.AuditableMethod;
import com.softserve.auxiliary.annotations.SecuredMethod;
import com.softserve.auxiliary.requestresponseclasses.DynamicReportCreationRequest;
import com.softserve.auxiliary.requestresponseclasses.DynamicReportExportRequest;
import com.softserve.auxiliary.requestresponseclasses.SelectedColumn;
import com.softserve.auxiliary.interceptors.AuditTrailInterceptor;
import com.softserve.auxiliary.interceptors.AuthenticationInterceptor;
import com.softserve.auxiliary.requestresponseclasses.Session;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

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
    @PersistenceUnit(unitName = com.softserve.auxiliary.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
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
    
    protected EntityManager createEntityManager()
    {
        return emf.createEntityManager();
    }
    
    public ReportServices() {
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR})
    @AuditableMethod
    @Override
    public List<AuditLog> loadAllAuditLogEntries(Session session) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Person> loadAllPersonEntities(Session session) throws Exception
    {
        EntityManager em = createEntityManager();

        try
        {
            return getDAOFactory(em).createPersonDAO().findPersonEntities();
        }
        finally
        {
            em.close();
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
    @AuditableMethod
    @Override
    public List<Application> loadAllApplicationEntities(Session session) throws Exception 
    {
        EntityManager em = createEntityManager();
       
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
       
    
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR, com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_DRIS_MEMBER})
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
