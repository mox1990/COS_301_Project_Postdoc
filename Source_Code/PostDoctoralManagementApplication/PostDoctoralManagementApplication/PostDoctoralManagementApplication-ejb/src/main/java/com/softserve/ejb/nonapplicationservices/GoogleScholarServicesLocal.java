/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb.nonapplicationservices;

import com.softserve.auxiliary.HTTPScrapers.GoogleScholarQuery;
import com.softserve.auxiliary.HTTPScrapers.GoogleScholarResult;
import com.softserve.auxiliary.requestresponseclasses.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface GoogleScholarServicesLocal {
    public List<GoogleScholarResult> searchGoogleScholarUsing(Session session, GoogleScholarQuery googleScholarQuery) throws Exception;
    
}
