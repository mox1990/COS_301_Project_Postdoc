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

import com.softserve.system.Session;
import java.io.ByteArrayOutputStream;
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
    
    private List<Class<?>> entities = new ArrayList<>();
    
    public void addClassToReport(Class<?> entity)
    {
        if(!entities.contains(entity))
            entities.add(entity);
    }
    
    protected ApplicationJpaController getApplicationDAO()
    {
        return new ApplicationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    public ByteArrayOutputStream exportSpreadsheetReport(Timestamp start, Timestamp end)
    {
        // TODO: Write code with a clear head
        /*Workbook workbook = new XSSFWorkbook();
         
        for(Class<?> entity: entities)
        {
            Sheet sheet = workbook.createSheet(entity.getCanonicalName());

            List<?> items = em.createNamedQuery(entity.getCanonicalName() + ".findByTimestamp", entity).setParameter("timestamp", start).getResultList();
            int rIndex = 0;
            
            for(Object item: items)
            {
                Row row = sheet.createRow(rIndex++);
                int cIndex = 0;
                
                Cell cell0 = row.createCell(cIndex++);
                cell0.setCellValue(item.getName());
                Cell cell1 = row.createCell(cIndex++);
                cell1.setCellValue(item.getShortCode());
            }            
        }*/
        
        return null;
    }

    public ByteArrayOutputStream exportPDFReport(Timestamp start, Timestamp end) throws DocumentException
    {
        EntityManager em = emf.createEntityManager();
        
        Document doc = new Document();
        ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
        PdfWriter docWriter = PdfWriter.getInstance(doc, baosPDF);

        doc.add(new Paragraph("Created on: " + new java.util.Date()));
        // TODO: Might wanna add more information about who is creating the report
        
        for(Class<?> entity: entities)
        {
            System.out.println("Type: " + entity.getCanonicalName());
            PdfPTable table = setupTable(entity);
            
            List<?> items = em.createNamedQuery(entity.getCanonicalName() + ".findByTimestamp", entity).setParameter("timestamp", start).getResultList();
            
            for(Object item: items)
            {
                table = addItemToTable(table, item);
            }
            doc.add(table);
        }
        
        doc.close();
        docWriter.close();
        
        entities = new ArrayList<>();
        
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
    
    private PdfPTable addItemToTable(PdfPTable table, Object entity)
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
