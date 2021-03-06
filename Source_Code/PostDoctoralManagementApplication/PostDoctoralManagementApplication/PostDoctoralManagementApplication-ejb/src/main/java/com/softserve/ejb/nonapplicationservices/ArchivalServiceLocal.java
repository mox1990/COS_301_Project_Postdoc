/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.nonapplicationservices;

import com.softserve.auxiliary.requestresponseclasses.Session;
import java.io.IOException;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface ArchivalServiceLocal {
    public void backupDatabase(Session session) throws Exception;
    public void restoreBackupToWorkingDatabase(Session session) throws Exception;
    public void archiveOldInformation(Session session) throws Exception;
    public void retrieveArchievedInformation(Session session) throws Exception;
    
    
    public byte[] createBackupScriptForWorkingDatabase(Session session) throws Exception;
    public void restoreBackupOfWorkingDatabaseFromScript(Session session, byte[] file) throws Exception;
}
