/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.experimental.converters;

import java.util.Objects;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ConvertProperty {
    private String fieldName;
    private Boolean included;

    public ConvertProperty() {
    }
    
    public ConvertProperty(String fieldName) {
        this.fieldName = fieldName;
    }
    
    public ConvertProperty(String fieldName, Boolean included) {
        this.fieldName = fieldName;
        this.included = included;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setIncluded(Boolean included) {
        this.included = included;
    }

    public Boolean isIncluded() {
        return included;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ConvertProperty)
        {
            if(fieldName.equals(((ConvertProperty)obj).fieldName))
            {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.fieldName);
        hash = 23 * hash + Objects.hashCode(this.included);
        return hash;
    }
    
    
    
}
