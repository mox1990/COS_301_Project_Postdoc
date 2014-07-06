/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBEntities.Application;

import com.softserve.system.Session;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
@Stateless
public class ReportServices implements ReportServicesLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    public ByteArrayOutputStream exportApplicationSpreadsheetReport(Timestamp start, Timestamp end) throws IOException
    {
        // TODO: Write code with a clear head

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();
        Sheet applicationSheet = workbook.createSheet("Application");
        
        List<String> sheetNames = new ArrayList<>(); // For embedded table
        List<List<Long>> embeddedIds = new ArrayList<>(); // purposes.
        
        EntityManager em = emf.createEntityManager();
        
        // Setup headers
        setupSheetHeaders(applicationSheet, Application.class);
        
        // Enter data...
        int rIndex = 1; // Adding values from the second row onwards.
        List<Application> applications = em.createNamedQuery("Application.findByTimestampBetweenRange", Application.class).setParameter("start", start).setParameter("end", end).getResultList(); // Need to get this more generic
        for(Application application: applications)
        {
            Row row = applicationSheet.createRow(rIndex++);
            addEntityToWorksheetRow(row, application, sheetNames, embeddedIds, workbook);
        }   
        
        // TODO: Make calls to export embedded tables...
        workbook.write(baos);
        return baos;   
    }
    
    private void setupSheetHeaders(Sheet sheet, Class<?> entity)
    {
        Field[] fields = entity.getDeclaredFields();
        Row row = sheet.createRow(0);
        int cIndex = 0;
        
        for(Field field: fields)
        {
            Cell cell = row.createCell(cIndex++);
            cell.setCellValue(field.getName());
        }
    }
    
    private void addEntityToWorksheetRow(Row row, Object entity, List<String> sheetNames, List<List<Long>> ids, Workbook workbook)
    {
        int cIndex = 0;
        Field[] fields = entity.getClass().getDeclaredFields();
        
        for(Field field: fields)
        {
            try 
            {
                Cell cell = row.createCell(cIndex++);
                
                // Doing this for other tables generation...
                String attrib = field.get(entity).getClass().getCanonicalName();
                String value = field.get(entity).toString();
                
                if(value.contains("com.softserve.DBEntities."))
                {
                    value = value.substring(value.indexOf("="), value.indexOf("]"));
                    if(sheetNames.contains(attrib))
                    {
                        ids.get(sheetNames.indexOf(attrib)).add(Long.parseLong(value));
                    }
                    else
                    {
                        sheetNames.add(attrib.getClass().getCanonicalName());
                        ids.get(sheetNames.indexOf(attrib)).add(Long.parseLong(value));
                    }
                }
                // What is basically required...
                cell.setCellValue(value);
            } 
            catch (IllegalArgumentException | IllegalAccessException ex) 
            {
                Cell cell = row.createCell(cIndex++);
                cell.setCellValue(" ");
                Logger.getLogger(ExportApplicationServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ByteArrayOutputStream exportPDFReport(Timestamp start, Timestamp end) throws DocumentException
    {
        EntityManager em = emf.createEntityManager();
        
        Document doc = new Document();
        ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
        PdfWriter docWriter = PdfWriter.getInstance(doc, baosPDF);

        doc.add(new Paragraph("Created on: " + new java.util.Date()));
        // TODO: Might wanna add more information about who is creating the report
        
        
        PdfPTable applicationTable = setupTable(Application.class);

        List<Application> entities = em.createNamedQuery("Application.findByTimestampBetweenRange", Application.class).setParameter("start", start).setParameter("end", end).getResultList();

        for(Application entity: entities)
        {            
            applicationTable = addEntityToTable(applicationTable, entity);
        }
        doc.add(applicationTable);
        
        doc.close();
        docWriter.close();
        
        return baosPDF;
    }
    
    private PdfPTable setupTable(Class<?> entity)
    {
        Field[] fields = entity.getDeclaredFields();
        
        PdfPTable table = new PdfPTable(fields.length);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase(entity.getClass().getCanonicalName()));
        cell.setColspan(fields.length);
        table.addCell(cell);
        
        for (Field field : fields) 
        {
            table.addCell(field.getName()); //field.getType().getSimpleName() this one returns data type
        }
        
        return table;
    }
    
    private PdfPTable addEntityToTable(PdfPTable table, Object entity)
    {
        Field[] fields = entity.getClass().getDeclaredFields();
        
        for (Field field : fields) 
        {
            try {
                table.addCell(field.get(entity).toString()); //field.getType().getSimpleName() this one returns data type
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                // Means object was not accessable so it is a blank field.
                table.addCell("");
                Logger.getLogger(ReportServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return table;
    }
    
}
