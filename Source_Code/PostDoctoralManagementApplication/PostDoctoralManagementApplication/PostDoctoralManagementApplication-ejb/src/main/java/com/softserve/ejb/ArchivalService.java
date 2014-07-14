/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.AuditLog;
import com.softserve.constants.PersistenceConstants;
import com.softserve.system.DBEntitiesFactory;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Singleton
public class ArchivalService implements ArchivalServiceLocal {
    private final String fs = System.getProperty("file.separator");
    private final String filepath = System.getProperty("user.home") + fs + "PostDocDatabase" + fs;
    
    AuditLog aLog;
    @Schedule(dayOfWeek="*", hour="2", info = "Daily backup of the database.")
    public void backupDatabase()
    {
        try
        {
            Timestamp time = new Timestamp(new Date().getTime());
            String filename = new SimpleDateFormat("dd/MM/yyyy").format(time) + ".sql";
            Process exec = Runtime.getRuntime().exec("mysqldump -u root -p" + "passwordHere" 
                    + PersistenceConstants.PERSISTENCE_UNIT_NAME + " > " 
                    + filepath + filename);

            if(exec.waitFor()==0)
            {
                InputStream inputStream = exec.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                
                DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
                AuditTrailService auditTrailService = getAuditTrailServiceEJB();
                AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("System backup success: " + str, null); // TODO: System account
                auditTrailService.logAction(auditLog);
            }
            else
            {
                InputStream errorStream = exec.getErrorStream();
                byte[] buffer = new byte[errorStream.available()];
                errorStream.read(buffer);

                String str = new String(buffer);
                
                DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
                AuditTrailService auditTrailService = getAuditTrailServiceEJB();
                AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("System backup fail: " + str, null);
                auditTrailService.logAction(auditLog);
            }
        }
        catch (IOException | InterruptedException ex) {
            Logger.getLogger(ArchivalService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ArchivalService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    @Schedule(dayOfWeek="Sat", hour="2", info = "Daily backup of the database.")
    public void archiveOldInformation()
    {
        // TODO: Does the archiving of old information not fit into the backing up?
        // Or do we now send it to a remote location? Email it to somewhere maybe.
         
    }
    
    public void retrieveArchievedInformation(String filename) throws IOException
    {
        try
        {
            Process exec = Runtime.getRuntime().exec("mysql -u root -p" + "passwordHere" 
                    + "temporyPersistanceUnitHere" + " < " + filepath + filename + ".sql");

            if(exec.waitFor()==0)
            {
                InputStream inputStream = exec.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);

                DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
                AuditTrailService auditTrailService = getAuditTrailServiceEJB();
                AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("System restore success: " + str, null); // TODO: System account
                auditTrailService.logAction(auditLog);
            }
            else
            {
                InputStream errorStream = exec.getErrorStream();
                byte[] buffer = new byte[errorStream.available()];
                errorStream.read(buffer);

                String str = new String(buffer);

                DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
                AuditTrailService auditTrailService = getAuditTrailServiceEJB();
                AuditLog auditLog = dBEntitiesFactory.buildAduitLogEntitiy("System restore fail: " + str, null);
                auditTrailService.logAction(auditLog);
            }
        }
        catch (IOException | InterruptedException ex) {
            Logger.getLogger(ArchivalService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ArchivalService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected String executeCommand(String command)
    {
        return null;
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return new AuditTrailService();
    }
}
