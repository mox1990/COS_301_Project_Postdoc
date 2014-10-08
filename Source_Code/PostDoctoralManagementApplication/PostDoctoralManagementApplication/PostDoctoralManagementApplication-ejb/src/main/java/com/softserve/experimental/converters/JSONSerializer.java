/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.experimental.converters;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class JSONSerializer extends ClassSerializer{

    @Override
    public String convertMembersToHeading(Object object, List<ConvertProperty> properties) 
    {
        return "";
    }
    
    @Override
    public String convertClassToSerial(Object object, List<ConvertProperty> properties) 
    {
        return convertClassToJSON(object, properties, new ArrayList<Object>());
    }
    
    public String convertClassToJSON(Object object, List<ConvertProperty> properties, List<Object> alreadyScanned)
    {
        if(object == null)
        {
            return "";
        }
        
        alreadyScanned.add(object);
        String output = "{";

        for(int i = 0; i < object.getClass().getDeclaredFields().length; i++)
        {
            
            Field field  = object.getClass().getDeclaredFields()[i];
            field.setAccessible(true);
            //System.out.println(i + ":" + field.getName());
            int index = properties.indexOf(new ConvertProperty(field.getName()));
            
            if(index > -1)
            {
                if(properties.get(index).isIncluded())
                {
                    try 
                    {
                        output = output + convertMember(field, object, alreadyScanned);
                    }
                    catch (Exception ex) 
                    {
                        Logger.getLogger(JSONSerializer.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            }
            else
            {
                try 
                {
                    output = output + convertMember(field, object, alreadyScanned);
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(JSONSerializer.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            
            if(i < object.getClass().getDeclaredFields().length - 1)
            {
                output = output + ",";
            }            
        }
        
        return output + "}";
    }
    
    private String convertMember(Field field, Object object, List<Object> alreadyScanned) throws Exception
    {           
        return "\"" + field.getName() + "\":" + convertObjectToValue(field.get(object), alreadyScanned);
    }
    
    private Object[] getArrayFromObject(Object object)
    {
        Object[] arrayOut = new Object[Array.getLength(object)];
        
        for(int i = 0; i < arrayOut.length; i++)
        {
            arrayOut[i] = Array.get(object, i);
        }
        
        return arrayOut;
    }
    
    private String convertObjectToValue(Object object, List<Object> alreadyScanned)
    {
        //System.out.println(object.getClass().getName());
        if(object == null)
        {
            return "null";
        }
        else if(alreadyScanned.contains(object))
        {
            return object.toString();
        }   
        else if(object.getClass().isPrimitive() || classWrappers.contains(object.getClass()))
        {            
            return object.toString();
        }
        else if(object.getClass() == String.class)
        {
            return "\"" + object.toString() + "\"";
        }
        else if(object.getClass() == Date.class)
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return "\"" + dateFormat.format((Date)object) + "\"";
        }
        else if(object instanceof  Collection)
        {           
            Iterator iterator = ((Collection) object).iterator();

            String output = "[";
            
            int size = ((Collection) object).size();
            int count = 0;
            while(iterator.hasNext())
            {
                output += convertObjectToValue(iterator.next(),alreadyScanned);
                
                if(count++ < size - 1)
                {
                    output = output + ",";
                }
            }
            
            return output + "]";        
        }    
        else if (object.getClass().isArray())
        {
            System.out.println("Array");
            Object[] array = getArrayFromObject(object);

            String output = "[";
            
            for(int i = 0; i < array.length; i++)
            {
                output += convertObjectToValue(array[i],alreadyScanned);
                
                if(i < array.length - 1)
                {
                    output = output + ",";
                }
            }
            
            return output + "]";
        }
        else
        {
            return object.toString();
            //alreadyScanned.add(object);
            //return convertClassToJSON(object, new ArrayList<ConvertProperty>(), alreadyScanned);            
        }
    }

    
    
}
