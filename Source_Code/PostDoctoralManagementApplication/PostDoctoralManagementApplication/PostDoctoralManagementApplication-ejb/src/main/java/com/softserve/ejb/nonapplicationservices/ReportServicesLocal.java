/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.nonapplicationservices;

import ar.com.fdvs.dj.domain.DynamicReport;
import com.itextpdf.text.DocumentException;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.auxiliary.Exceptions.AuthenticationException;
import com.softserve.auxiliary.requestresponseclasses.DynamicReportCreationRequest;
import com.softserve.auxiliary.requestresponseclasses.DynamicReportExportRequest;
import com.softserve.auxiliary.requestresponseclasses.SelectedColumn;
import com.softserve.auxiliary.requestresponseclasses.Session;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author K
 */
@Local
public interface ReportServicesLocal {
    
    public List<AuditLog> loadAllAuditLogEntries(Session session) throws Exception;
    public List<Person> loadAllPersonEntities(Session session) throws Exception;
    public List<Application> loadAllApplicationEntities(Session session) throws Exception;
    
    public List<String> getFieldNamesForEntity(Object entity) throws Exception;
    public List<String> getConcatenatedFieldNamesForEntities(List<Object> entities) throws Exception;
    public List<List<String>> convertEntitiesToRowData(List<List<Object>> entities, List<SelectedColumn> propertyMaps) throws Exception;
    public DynamicReport createDynamicReport(Session session, DynamicReportCreationRequest dynamicReportCreationRequest) throws Exception;
    public String renderReportAsHTML(Session session, DynamicReport report, DynamicReportExportRequest dynamicReportExportRequest) throws Exception;
    public byte[] renderReportAsPDF(Session session, DynamicReport report, DynamicReportExportRequest dynamicReportExportRequest) throws Exception;
    public byte[] renderReportAsMSEXCELSpreadsheet(Session session, DynamicReport report, DynamicReportExportRequest dynamicReportExportRequest) throws Exception;
    
}
