/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxillary;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class Issue {
    
    private String type;
    private String message;
    private Integer Count;

    public Issue() {
    }

        
    public Issue(String type, String message, Integer Count) {
        this.type = type;
        this.message = message;
        this.Count = Count;
    }

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer Count) {
        this.Count = Count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }    
    
    
}
