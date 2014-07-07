/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.constants.PersistenceConstants;
import java.io.IOException;
import java.util.Date;
import javax.ejb.Stateless;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class ArchivalService implements ArchivalServiceLocal {

    public void backupDatabase() throws IOException
    {
        // TODO: More form and function... add encryption.
        Runtime.getRuntime().exec("mysqldump -u root -p" + "passwordHere" 
                + PersistenceConstants.PERSISTENCE_UNIT_NAME + " > " + new Date().toString().replace(" ", "") + ".sql");
        
    }
    
    public void retrieveArchievedInformation() throws IOException
    {
        // TODO: More form and function...
        Runtime.getRuntime().exec("mysql -u root -p" + "passwordHere" 
                + "temporyPersistanceUnitHere" + " < " + "fileNameHere" + ".sql");
        
    }
    
    public void archiveOldInformation()
    {
        // TODO: Does the archiving of old information not fit into the backing up?
        // Or do we now send it to a remote location? Email it to somewhere maybe.
         
    }
}
