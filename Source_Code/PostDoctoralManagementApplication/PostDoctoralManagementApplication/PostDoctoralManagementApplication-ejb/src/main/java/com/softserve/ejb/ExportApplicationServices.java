/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.Application;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author kgothatso
 */
@Stateless
public class ExportApplicationServices implements ExportApplicationServicesLocal {
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    public ByteArrayOutputStream ExportCSVApplicationData(List<Application> applications) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        Field[] fields = applications.get(0).getClass().getFields();
        // Set up headers...
        for(Field field: fields)
        {
            baos.write(field.getName().getBytes());
            baos.write(',');
        }
        
        // Write applications to file
        for(Application app: applications)
        {
            for(Field field: fields)
            {
                try
                {
                    // TODO: Handle the calls to other tables...
                    baos.write(field.get(app).toString().getBytes());
                }
                catch(IllegalAccessException ex)
                {
                    baos.write(' ');
                }
                baos.write(',');
            }
        }
        return baos;
    }
    
    public ByteArrayOutputStream ExportExcelApplicationData(Timestamp start, Timestamp end) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Application");
         

        List<Application> applications = emf.createEntityManager().createNamedQuery("Application.findByTimestampBetweenRange", Application.class).setParameter("start", start).setParameter("end", end).getResultList();
        
        // Setup headers
        int rIndex = 0;
        Field[] fields = Application.class.getDeclaredFields();
        Row row = sheet.createRow(rIndex++);
        int cIndex = 0;
        
        for(Field field: fields)
        {
            Cell cell = row.createCell(cIndex++);
            cell.setCellValue(field.getName());
        }
        
        // Enter data...
        for(Application application: applications)
        {
            row = sheet.createRow(rIndex++);
            cIndex = 0;

            for(Field field: fields)
            {
                try {
                    // TODO: cater for non genric types...
                    Cell cell = row.createCell(cIndex++);
                    cell.setCellValue(field.get(application).toString());
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Cell cell = row.createCell(cIndex++);
                    cell.setCellValue(" ");
                    Logger.getLogger(ExportApplicationServices.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }   
        
        // TODO: Make calls to export other relevent tables like Address and reports of applications...
        workbook.write(baos);
        return baos;
    }
}
