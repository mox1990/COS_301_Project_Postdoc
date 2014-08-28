/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.constants.PersistenceConstants;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Singleton
public class ArchivalService implements ArchivalServiceLocal {
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.ARCHIVE_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emfArchive;
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emfWorking;
    
    private final String fs = System.getProperty("file.separator");
    private final String filepath = System.getProperty("user.home") + fs + "PostDocDatabase" + fs;
    
    AuditLog aLog;
    private UserGatewayLocal userGatewayLocal;
    private AuditTrailServiceLocal auditTrailServiceLocal;
    
    public ArchivalService(EntityManagerFactory emfW, EntityManagerFactory emfA )
    {
    }
    
    
    @Schedule(dayOfWeek="*", hour="2", info = "Daily backup of the database.")
    public void backupDatabase()
    {
        try
        {
            Timestamp time = new Timestamp(new Date().getTime());
            String filename = new SimpleDateFormat("dd/MM/yyyy").format(time) + ".sql";
            Process exec = Runtime.getRuntime().exec("mysqldump -u root -p" + "root" 
                    + PersistenceConstants.BACKUP_DB_PERSISTENCE_UNIT_NAME + " > " 
                    + filepath + filename);

            if(exec.waitFor()==0)
            {
                InputStream inputStream = exec.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                
                DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
                AuditTrailService auditTrailService = getAuditTrailServiceEJB();
                AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("System backup success: " + str, null); // TODO: System account
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
                AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("System backup fail: " + str, null);
                auditTrailService.logAction(auditLog);
            }
        }
        catch (IOException | InterruptedException ex) {
            Logger.getLogger(ArchivalService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ArchivalService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected UserGatewayLocal getUserGatewayServiceEJB()
    {
        return userGatewayLocal;
    }

    
    protected PersonJpaController getPersonDAO()
    {
        return new PersonJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emfWorking);
    }
    
    
    @Schedule(dayOfWeek="Sat", hour="2", info = "Daily backup of the database.")
    public void archiveOldInformation(Session session) throws Exception
    {
        // TODO: Does the archiving of old information not fit into the backing up?
        // Or do we now send it to a remote location? Email it to somewhere maybe.
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        
        PersonJpaController personJpaController = getPersonDAO();
        DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
        AuditTrailServiceLocal auditTrailService = getAuditTrailServiceEJB();
        
        //Find person object
   //     Person user = personJpaController.findPerson(systemID);
        
        //user.setAccountStatus(com.softserve.constants.PersistenceConstants.ACCOUNT_STATUS_DISABLED);
 //       personJpaController.edit(user);
        
        //Log action
     //   AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("Removed user account", session.getUser());
       // auditTrailService.logAction(auditLog);
         
    }
    
    
    
    protected void createArchivalDB() throws InterruptedException, IOException, Exception
    {
        try
        {
            Timestamp time = new Timestamp(new Date().getTime());
            String filename = new SimpleDateFormat("dd/MM/yyyy").format(time) + ".sql";
            Process exec = Runtime.getRuntime().exec("mysqldump -u root -p" + "root" 
                    + PersistenceConstants.ARCHIVE_DB_PERSISTENCE_UNIT_NAME + " > " 
                    + filepath + filename);

            //mysql -u someuser -p anotherdatabase < mydatabase.sql
            
            if(exec.waitFor()==0)
            {
                InputStream inputStream = exec.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                System.out.println("Archival Database created");
            }
              else
            {
                InputStream errorStream = exec.getErrorStream();
                byte[] buffer = new byte[errorStream.available()];
                errorStream.read(buffer);

                String str = new String(buffer);
                System.out.println("Archival Database not created");
            }
        }
        catch(IOException | InterruptedException ex)
        {
            Logger.getLogger(ArchivalService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(ArchivalService.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    
    }
    
    public void retrieveArchievedInformation(String filename) throws IOException
    {
        try
        {
            Process exec = Runtime.getRuntime().exec("mysql -u root -p" + "root" 
                    + "temporyPersistanceUnitHere" + " < " + filepath + filename + ".sql");

            if(exec.waitFor()==0)
            {
                InputStream inputStream = exec.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);

                DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
                AuditTrailService auditTrailService = getAuditTrailServiceEJB();
                AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("System restore success: " + str, null); // TODO: System account
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
                AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("System restore fail: " + str, null);
                auditTrailService.logAction(auditLog);
            }
        }
        catch (IOException | InterruptedException ex) {
            Logger.getLogger(ArchivalService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ArchivalService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void restoreDatabase()
    {
        try
        {
            Timestamp time = new Timestamp(new Date().getTime());
            String filename = new SimpleDateFormat("dd/MM/yyyy").format(time) + ".sql";
            Process exec = Runtime.getRuntime().exec("mysql --user" + "root" 
                    + PersistenceConstants.BACKUP_DB_PERSISTENCE_UNIT_NAME + " > " 
                    + filepath + filename);
            
             if(exec.waitFor()==0)
            {
                InputStream inputStream = exec.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                
                DBEntitiesFactory dBEntitiesFactory = getDBEntitiesFactory();
                AuditTrailService auditTrailService = getAuditTrailServiceEJB();
                AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("System restore success: " + str, null); // TODO: System account
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
                AuditLog auditLog = dBEntitiesFactory.createAduitLogEntitiy("System restore fail: " + str, null);
                auditTrailService.logAction(auditLog);
            }
        }
        catch(IOException | InterruptedException ex)
        {
            Logger.getLogger(ArchivalService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (Exception ex)
        {
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

    @Override
    public void archiveOldInformation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
