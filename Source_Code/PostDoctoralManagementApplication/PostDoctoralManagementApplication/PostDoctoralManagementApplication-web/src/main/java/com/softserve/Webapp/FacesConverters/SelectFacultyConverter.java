/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.FacesConverters;

import com.softserve.persistence.DBEntities.Faculty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@FacesConverter("com.softserve.Webapp.SelectFacultyConverter")
public class SelectFacultyConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try
        {
            return new Faculty(Long.parseLong(value));
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.out.println("Converting object to string " + value );
        if(value == null)
        {
            return "0";
        }
        return Long.toString(((Faculty) value).getFacultyID());
    }
    
}
