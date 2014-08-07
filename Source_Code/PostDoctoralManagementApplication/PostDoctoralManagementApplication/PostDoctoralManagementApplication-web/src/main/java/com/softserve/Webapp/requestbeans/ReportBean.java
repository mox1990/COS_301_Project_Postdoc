/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.ejb.ReportServicesLocal;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author kgothatso
 */
@Named(value = "reportBean")
@RequestScoped
public class ReportBean {
    @EJB
    private ReportServicesLocal reportEJB;
    /**
     * Creates a new instance of ReportBean
     */
    public ReportBean() {
    }
    
    public StreamedContent getApplicationReport() // TODO: Add report filters...
    {
        InputStream stream = null;
        try 
        {
            stream = new ByteArrayInputStream(reportEJB.exportPersonsToPdf());
            return new DefaultStreamedContent(stream, "application/pdf", "Report-About-Date.pdf");
        } 
        catch (JRException | ClassNotFoundException |SQLException | InterruptedException ex) 
        {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally 
        {
            try 
            {
                if(stream!=null) stream.close();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
