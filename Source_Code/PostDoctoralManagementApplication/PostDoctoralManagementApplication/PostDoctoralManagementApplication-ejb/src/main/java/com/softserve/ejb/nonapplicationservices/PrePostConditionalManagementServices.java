/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.nonapplicationservices;

import auto.softserve.XMLEntities.PrePostConditional.Prepostconditionalmethods;
import com.softserve.auxiliary.interceptors.AuditTrailInterceptor;
import com.softserve.auxiliary.interceptors.AuthenticationInterceptor;
import com.softserve.auxiliary.requestresponseclasses.Session;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuditTrailInterceptor.class, AuthenticationInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class PrePostConditionalManagementServices implements PrePostConditionalManagementServicesLocal {

    public PrePostConditionalManagementServices() {
    }

    @Override
    public Prepostconditionalmethods loadPrePostConditionalMethodsConfiguration(Session session) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updatePrePostConditionalMethodsConfiguration(Session session, Prepostconditionalmethods prepostconditionalmethods) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
