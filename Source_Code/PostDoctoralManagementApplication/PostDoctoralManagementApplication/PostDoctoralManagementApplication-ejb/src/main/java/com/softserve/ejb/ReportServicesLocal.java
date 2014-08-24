/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.itextpdf.text.DocumentException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Person;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.Session;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.Local;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author K
 */
@Local
public interface ReportServicesLocal {
    
    public List<Person> getAllPersons();
    public List<Person> getAllPersonsWithSecurityRole(Long role);
    public List<Application> getAllApplications();
    public List<Application> getAllApplicationsWithStatus(String status);
    /**
     * Export PDF of report made up of persons
     * @param session
     * @param persons
     * @return
     * @throws JRException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InterruptedException 
     */
    public byte[] exportPersonsToPdf(Session session, List<Person> persons) throws Exception;
    public byte[] exportAllPersonsToPdf(Session session) throws Exception;
    
    /**
     * Export PDF of report made up of applications
     * @param session
     * @param applications
     * @return
     * @throws JRException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InterruptedException 
     */
    public byte[] exportApplicationToPdf(Session session, List<Application> applications) throws Exception;
    public byte[] exportAllApplicationToPdf(Session session) throws Exception;
    public byte[] exportPersonsToExcel(Session session, List<Person> application) throws Exception;
    public byte[] exportAllPersonsToExcel(Session session) throws Exception;
    public byte[] exportApplicationToExcel(Session session, List<Application> applications) throws Exception;
    public byte[] exportAllApplicationToExcel(Session session) throws Exception;
    public byte[] dynamicReport(List<String> columnHeaders, List<List<String>> rows) throws Exception;
}
