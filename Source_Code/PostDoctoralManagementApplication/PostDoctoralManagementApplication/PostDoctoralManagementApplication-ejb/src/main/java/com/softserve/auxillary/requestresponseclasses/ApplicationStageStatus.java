/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxillary.requestresponseclasses;

import com.softserve.persistence.DBEntities.Person;
import java.util.Date;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ApplicationStageStatus {
    private Date timestamp;
    private String status;
    private Person authority;

    public ApplicationStageStatus(Date timestamp, String status, Person authority) {
        this.timestamp = timestamp;
        this.status = status;
        this.authority = authority;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }
    
    public Person getAuthority() {
        return authority;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
