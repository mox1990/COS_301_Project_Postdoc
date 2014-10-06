/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.applicationservices;

import com.softserve.DBEntities.Cv;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.Session;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface CVManagementServiceLocal {
    public void createCV(Session session, Cv cv) throws AuthenticationException, Exception;
    public void updateCV(Session session, Cv cv) throws AuthenticationException, Exception;
    public boolean hasCV(Session session);
    
}
