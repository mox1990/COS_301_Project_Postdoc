/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.nonapplicationservices;

import auto.softserve.XMLEntities.PrePostConditional.Prepostconditionalmethods;
import com.softserve.auxiliary.requestresponseclasses.Session;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface PrePostConditionalManagementServicesLocal {
    public Prepostconditionalmethods loadPrePostConditionalMethodsConfiguration(Session session) throws Exception;
    public void updatePrePostConditionalMethodsConfiguration(Session session, Prepostconditionalmethods prepostconditionalmethods) throws Exception;
}
