/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.util;

import java.io.Serializable;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class StorageItem implements Serializable {
    
    private String key;
    private Object object;

    public StorageItem(String key, Object object) {
        this.key = key;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof StorageItem))
        {
            return false;
        }
        else
        {
            return key.equals(((StorageItem) obj).getKey());
        }
    }

    @Override
    public String toString() {
        return key + " = " + object;
    }
    
}
