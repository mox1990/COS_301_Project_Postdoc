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
import com.softserve.DBEntities.Application;
import com.softserve.system.Session;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
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
public class ReportServices implements ReportServicesLocal 
{    
    @Override
    public ByteArrayOutputStream exportApplicationSpreadsheetReport(Session session, List<Application> applications) throws IOException
    {
        //AuthenticUser(session, list of privliges)
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();
        Sheet applicationSheet = workbook.createSheet("Application");

        Row row = applicationSheet.createRow(0);
        int cIndex = 0;
        
        Cell cell = row.createCell(cIndex++);
        cell.setCellValue("applicationID");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("fellow");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("grantHolderID");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("projectTitle");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("information");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("startDate");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("endDate");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("status");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("eligiblityCheckDate");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("endorsementID");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("finalisationDate");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("fundingReportID");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("recommendationReportID");
        
        cell = row.createCell(cIndex++);
        cell.setCellValue("type");
        
        int rIndex = 1; 
        for(Application application: applications)
        {
            row = applicationSheet.createRow(rIndex++);
            cIndex = 0;
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getApplicationID());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getFellow().getCompleteName());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getGrantHolder().getCompleteName());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getProjectTitle());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getInformation());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getStartDate());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getEndDate());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getStatus());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getEligiblityCheckDate());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getEndorsement().getDean().getCompleteName() + ": " + application.getEndorsement().getMotivation());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getFinalisationDate());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getFundingReport().toString());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getRecommendationReport().getHod().getCompleteName() + ": " + application.getRecommendationReport().getContent());
            
            cell = row.createCell(cIndex++);
            cell.setCellValue(application.getType());
        }   
        
        workbook.write(baos);
        return baos;   
    }

    @Override
    public ByteArrayOutputStream exportPDFReport(Session session, List<Class<?>> entities) throws DocumentException
    {
        //AuthenticUser(session, list of privliges)        
        Document doc = new Document();
        ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
        PdfWriter docWriter = PdfWriter.getInstance(doc, baosPDF);

        doc.add(new Paragraph("Created on: " + new java.util.Date()));
        // TODO: Might wanna add more information about who is creating the report
        
        
        PdfPTable applicationTable = setupTable(Application.class);
        
        for(Object entity: entities)
        {            
            applicationTable = addEntityToTable(applicationTable, entity);
        }
        doc.add(applicationTable);
        
        doc.close();
        docWriter.close();
        
        return baosPDF;
    }
    
    private PdfPTable setupTable(Object entity)
    {
        Field[] fields = entity.getClass().getDeclaredFields();
        
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
