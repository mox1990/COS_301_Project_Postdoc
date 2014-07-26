/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.FacesConverters;

import com.softserve.Webapp.util.ExceptionUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@FacesConverter("com.softserve.Webapp.DateTimeToXMLGregorianCalendarConverter")
public class DateTimeToXMLGregorianCalendarConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) 
    {        
        
        DateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        Date date = new Date();
        
        try 
        {
            date = format.parse(value);
        } 
        catch (ParseException ex) 
        {
            ExceptionUtil.logException(DateTimeToXMLGregorianCalendarConverter.class, ex);
        }
        
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        
        XMLGregorianCalendar xMLGregorianCalendar = null;
        try 
        {
            xMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } 
        catch (DatatypeConfigurationException ex) 
        {
            ExceptionUtil.logException(DateTimeToXMLGregorianCalendarConverter.class, ex);
        }
        
        return xMLGregorianCalendar;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) 
    {        
        XMLGregorianCalendar xMLGregorianCalendar = (XMLGregorianCalendar) value;
        DateFormat format = new SimpleDateFormat( "yyyy-MM-dd");
        return format.format(new Date(xMLGregorianCalendar.toGregorianCalendar().getTimeInMillis()));        
    }
    
}
