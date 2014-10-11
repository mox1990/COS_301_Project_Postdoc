/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.nonapplicationservices;

import com.softserve.auxiliary.HTTPScrapers.GoogleScholarQuery;
import com.softserve.auxiliary.HTTPScrapers.GoogleScholarResult;
import com.softserve.auxiliary.HTTPScrapers.GoogleScholarScraper;
import com.softserve.auxiliary.annotations.AuditableMethod;
import com.softserve.auxiliary.annotations.SecuredMethod;
import com.softserve.auxiliary.interceptors.AuditTrailInterceptor;
import com.softserve.auxiliary.interceptors.AuthenticationInterceptor;
import com.softserve.auxiliary.requestresponseclasses.Session;
import java.util.List;
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
public class GoogleScholarServices implements GoogleScholarServicesLocal {
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_PROSPECTIVE_FELLOW, com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_GRANT_HOLDER, com.softserve.auxiliary.constants.PersistenceConstants.SECURITY_ROLE_ID_RESEARCH_FELLOW})
    @AuditableMethod
    @Override
    public List<GoogleScholarResult> searchGoogleScholarUsing(Session session, GoogleScholarQuery googleScholarQuery) throws Exception 
    {
        GoogleScholarScraper googleScholarScraper = new GoogleScholarScraper();
        
        return googleScholarScraper.getResultsFromQuery(googleScholarQuery);
    }

}
