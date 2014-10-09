/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.webservices.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class JSONConverterUtil {

    public JSONConverterUtil() {
    }
    
    public static String objectToJSON_GSON(Object object) throws Exception
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();    
        return gson.toJson(object);
        
    }
    
    public static <T> T JSONToObject_GSON(String json, Class<T> toClass) throws Exception
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, toClass);
    }
    
    //WHEN USING JACKSON ANNOTATE CLASSES WITH @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
    //TO PREVENT CYCLIC REFERENCES
    public static String objectToJSON_JACKSON(Object object) throws Exception
    {        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, true);
        objectMapper.configure(MapperFeature.AUTO_DETECT_FIELDS, true);
        objectMapper.configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibilityChecker(objectMapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.NONE));
        return objectMapper.writeValueAsString(object);        
    }
    
    public static <T> T JSONToObject_JACKSON(String json, Class<T> toClass) throws Exception
    {        
        
        ObjectMapper objectMapper = new ObjectMapper(); 
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, true);
        objectMapper.configure(MapperFeature.AUTO_DETECT_FIELDS, true);
        objectMapper.configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibilityChecker(objectMapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.NONE));
        return objectMapper.readValue(json, toClass);
    }
    
    public static Object[] covertLinkedListToObjectInstances(Object[] linkedLists, String[] classnames) throws Exception
    {
        if(linkedLists.length != classnames.length)
        {
            throw new Exception("Linked list and class name sizes don't match");
        }
        
        Object[] objects = new Object[classnames.length];
        
        for(int i = 0; i < classnames.length; i++)
        {
            Class<?> c = Class.forName(classnames[i]);
            objects[i] = JSONConverterUtil.JSONToObject_JACKSON(JSONConverterUtil.objectToJSON_JACKSON(linkedLists[i]),c);
        }
        
        return objects;
    }
}
