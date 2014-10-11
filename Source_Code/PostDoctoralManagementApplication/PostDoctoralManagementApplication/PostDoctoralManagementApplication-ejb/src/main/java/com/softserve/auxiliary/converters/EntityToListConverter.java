/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.converters;

import com.softserve.auxiliary.requestresponseclasses.SelectedColumn;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class EntityToListConverter {
    
    public List<String> convertEntityToListString(Object entity, List<SelectedColumn> propertymap)
    {
        List<String> output = new ArrayList<String>();
        
        Field[] members = entity.getClass().getDeclaredFields();
        
        for(Field member : members)
        {
            Annotation entityAnnotation = member.getAnnotation(Column.class);
            
            if(entityAnnotation != null && propertymap.contains(new SelectedColumn(null,((Column) entityAnnotation).name(), entity.getClass().getName())) )
            {
                try 
                {
                    member.setAccessible(true);
                    output.add(String.valueOf(member.get(entity)));
                } 
                catch (IllegalArgumentException ex) 
                {
                    Logger.getLogger(EntityToListConverter.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (IllegalAccessException ex) 
                {
                    Logger.getLogger(EntityToListConverter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return output;
    }
    
    public List<List<String>> convertEntityListToListString(List<Object> entities, List<SelectedColumn> propertymap)
    {
        List<List<String>> lists = new ArrayList<List<String>>();
        
        for(Object entity : entities)
        {
            lists.add(convertEntityToListString(entity, propertymap));
        }
        
        return lists;
    }
    
    public List<List<String>> convertConcatenatedEntitiesListToListString(List<List<Object>> entities, List<SelectedColumn> propertymaps)
    {
        List<List<String>> lists = new ArrayList<List<String>>();
        
        for(List<Object> entitiesToConcatenate : entities)
        {
            for(Object entity : entitiesToConcatenate)
            {
                lists.add(convertEntityToListString(entity, propertymaps));
            }
        }
        
        return lists;
    }
    
    public List<String> getConcatenatedEntityFieldNames(List<Object> entities)
    {
        List<String> output = new ArrayList<String>();
        
        for(Object entity : entities)
        {
            output.addAll(getEntityFieldNames(entity));
        }
        
        return output;
    }
    
    
    public List<String> getEntityFieldNames(Object entity)
    {
        List<String> output = new ArrayList<String>();
        
        Field[] members = entity.getClass().getDeclaredFields();
        
        for(Field member : members)
        {
            Annotation entityAnnotation = member.getAnnotation(Column.class);
            
            if(entityAnnotation != null)
            {
                output.add(((Column) entityAnnotation).name());
            }
        }
        
        return output;
    }
}
