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
public class SelectedColumn {
    private String alias;
    private String actualname;
    private String entityClassType;

    public SelectedColumn() {
    }
        
    public SelectedColumn(String alias, String actualname) {
        this.alias = alias;
        this.actualname = actualname;
    }

    public SelectedColumn(String alias, String actualname, String entityClassType) {
        this.alias = alias;
        this.actualname = actualname;
        this.entityClassType = entityClassType;
    }
    
    

    public String getActualname() {
        return actualname;
    }

    public String getAlias() {
        return alias;
    }

    public void setActualname(String actualname) {
        this.actualname = actualname;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEntityClassType() {
        return entityClassType;
    }

    public void setEntityClassType(String entityClassType) {
        this.entityClassType = entityClassType;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SelectedColumn)
        {
            SelectedColumn o = (SelectedColumn) obj;
            return o.actualname.equals(this.actualname) && o.entityClassType.equals(this.entityClassType);
        }
        
        return false;
    }
    
    
    
    
}
