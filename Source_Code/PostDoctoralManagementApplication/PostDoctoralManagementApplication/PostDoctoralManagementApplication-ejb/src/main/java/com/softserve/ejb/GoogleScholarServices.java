/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.HTTPScrapers.GoogleScholarQuery;
import com.softserve.HTTPScrapers.GoogleScholarResult;
import com.softserve.HTTPScrapers.GoogleScholarScraper;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class GoogleScholarServices implements GoogleScholarServicesLocal {

    @Override
    public List<GoogleScholarResult> searchGoogleScholarUsing(GoogleScholarQuery googleScholarQuery) throws Exception 
    {
        GoogleScholarScraper googleScholarScraper = new GoogleScholarScraper();
        
        return googleScholarScraper.getResultsFromQuery(googleScholarQuery);
    }

}
