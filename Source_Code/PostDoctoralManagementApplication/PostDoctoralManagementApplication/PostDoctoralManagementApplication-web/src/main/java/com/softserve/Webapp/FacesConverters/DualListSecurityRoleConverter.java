/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.FacesConverters;

import com.softserve.persistence.DBEntities.SecurityRole;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@FacesConverter("com.softserve.Webapp.DualListSecurityRoleConverter")
public class DualListSecurityRoleConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) 
    {        
        
        System.out.println("Converting string " + value);
        if(value.equals(""))
        {
            System.out.println("Converting string " + value + " to null" );
            return null;
        }
        else
        {
        
            SecurityRole role = new SecurityRole();
            role.setRoleID(Long.parseLong(value));
            role.setName(com.softserve.auxillary.constants.PersistenceConstants.getSecurityRoleName(role.getRoleID()));
            //role.setRoleID(Long.parseLong(value.substring(0, value.indexOf(" "))));
            //role.setName(value.substring(value.indexOf(" ") + 1));
            System.out.println("Converting string " + value + " to " + role.getName() + " " + role.toString());
            return role;
        }
        
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        //String s = Long.toString(((SecurityRole) value).getRoleID()) + " " + ((SecurityRole) value).getName();
        String s = "";
        
        if(value == null)
        {
            return s;
        }
        else
        {
            s = String.valueOf(((SecurityRole) value).getRoleID());            
        }
        
        System.out.println("Converting object " + value.toString() + " to " + s);
        return s;
    }
    
}
