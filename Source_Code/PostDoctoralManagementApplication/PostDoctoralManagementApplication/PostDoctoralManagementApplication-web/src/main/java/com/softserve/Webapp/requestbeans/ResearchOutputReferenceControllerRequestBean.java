/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "researchOutputReferenceControllerRequestBean")
@RequestScoped
public class ResearchOutputReferenceControllerRequestBean {

    /**
     * Creates a new instance of ResearchOutputReferenceControllerRequestBean
     */
    public ResearchOutputReferenceControllerRequestBean() {
    }
    
    public boolean doesReferenceHavePubliser(auto.softserve.XMLEntities.CV.Reference reference)
    {
        if(reference != null && reference.getStatus() != null && !reference.getStatus().equals(""))
        {            
            return !reference.getStatus().equals(com.softserve.auxillary.constants.PersistenceConstants.CV_REFERENCE_INPROGRESS);
        }
        else
        {
            return false;
        }
    }
    
    public boolean doesReferenceHavePublicationDate(auto.softserve.XMLEntities.CV.Reference reference)
    {
        if(reference != null && reference.getStatus() != null && !reference.getStatus().equals(""))
        {            
            return reference.getStatus().equals(com.softserve.auxillary.constants.PersistenceConstants.CV_REFERENCE_PUBLISHED);
        }
        else
        {
            return false;
        }
    }
    
    
}
