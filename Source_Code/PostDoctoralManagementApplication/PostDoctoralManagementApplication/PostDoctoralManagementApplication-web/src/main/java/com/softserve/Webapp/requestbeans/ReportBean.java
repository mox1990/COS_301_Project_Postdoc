/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.ReportServicesLocal;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author kgothatso
 */
@Named(value = "reportBean")
@RequestScoped
public class ReportBean {
    private String reportType;
    private Date startDate;
    private Date endDate;
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject 
    private NavigationManagerBean navigationManagerBean;
    
    @EJB
    private ReportServicesLocal reportEJB;
    
    /**
     * Creates a new instance of ReportBean
     */
    public ReportBean() {
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public StreamedContent getReportPDF() // TODO: Add report filters...
    {
        InputStream stream = null;
        try 
        {
            switch(reportType)
            {
                case "application":
                    // Use the session manager sessionManagerBean.getSystemLevelSessionForCurrentSession()
                    stream = new ByteArrayInputStream(reportEJB.exportPersonsToPdf(sessionManagerBean.getSession(), null));
                    break;
                case "person":
                    // Use the session manager sessionManagerBean.getSystemLevelSessionForCurrentSession()
                    stream = new ByteArrayInputStream(reportEJB.exportPersonsToPdf(sessionManagerBean.getSession(), null));
                    break;
                default:
                    throw new Exception("Didn't Specify valid report type.");
            }
            
            return new DefaultStreamedContent(stream, "application/pdf", "Report-About-Date.pdf");
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ReportBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        } 
        finally 
        {
            try 
            {
                if(stream!=null) stream.close();
            } 
            catch (IOException ex) 
            {
                ExceptionUtil.logException(ReportBean.class, ex);
                ExceptionUtil.handleException(null, ex);
            }
        }
        return null;
    }
    
    public StreamedContent getReportExcel() // TODO: Add report filters...
    {
        InputStream stream = null;
        try 
        {
            switch(reportType)
            {
                case "application":
                    // Use the session manager sessionManagerBean.getSystemLevelSessionForCurrentSession()
                    stream = new ByteArrayInputStream(reportEJB.exportPersonsToExcel(sessionManagerBean.getSession(), null));
                    break;
                case "person":
                    // Use the session manager sessionManagerBean.getSystemLevelSessionForCurrentSession()
                    stream = new ByteArrayInputStream(reportEJB.exportPersonsToExcel(sessionManagerBean.getSession(), null));
                    break;
                default:
                    throw new Exception("Didn't Specify valid report type.");
            }
            return new DefaultStreamedContent(stream, "application/vnd.ms-excel", "Report-About-Date.xls");
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(ReportBean.class, ex);
            ExceptionUtil.handleException(null, ex);
        } 
        finally 
        {
            try 
            {
                if(stream!=null) stream.close();
            } 
            catch (IOException ex) 
            {
                ExceptionUtil.logException(ReportBean.class, ex);
                ExceptionUtil.handleException(null, ex);
            }
        }
        return null;
    }
}
