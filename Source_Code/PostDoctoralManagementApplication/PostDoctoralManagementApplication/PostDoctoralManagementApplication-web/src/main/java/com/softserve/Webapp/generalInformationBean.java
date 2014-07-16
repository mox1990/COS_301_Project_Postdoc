/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "generalInformationBean")
@ApplicationScoped
public class generalInformationBean {
    
    private List<SelectItem> titleSelectItems;
    
    /**
     * Creates a new instance of generalInformationBean
     */
    public generalInformationBean() {
    }
    
     @PostConstruct
    public void init()
    {
        
        titleSelectItems = new ArrayList<SelectItem>();
        titleSelectItems.add(new SelectItem("Mr","Mr."));
        titleSelectItems.add(new SelectItem("Ms","Ms."));
        titleSelectItems.add(new SelectItem("Miss","Miss."));
        titleSelectItems.add(new SelectItem("Mrs","Mrs."));
        titleSelectItems.add(new SelectItem("Prof","Prof."));
        titleSelectItems.add(new SelectItem("Dr","Dr."));
        
    }

    public List<SelectItem> getTitleSelectItems() {
        return titleSelectItems;
    }
   

    
    
    
    
}
