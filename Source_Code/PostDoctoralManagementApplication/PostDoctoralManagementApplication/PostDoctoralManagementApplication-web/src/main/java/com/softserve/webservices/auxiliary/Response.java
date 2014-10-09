/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.webservices.auxiliary;

import com.softserve.persistence.DBEntities.Person;
import com.softserve.webservices.util.JSONConverterUtil;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class Response {
    private boolean success;
    private String exceptionMessage;
    private Object[] result;
    private String[] classes;

    public Response() {
    }

    public Response(boolean success, String exceptionMessage, Object[] result) {
        this.success = success;
        this.exceptionMessage = exceptionMessage;
        this.result = result;
    }

    public Object[] getResult() {
        return result;
    }

    public void setResult(Object[] result) {
        this.result = result;
    }
    
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
    
    public void populateObjects() throws Exception
    {
       result = JSONConverterUtil.covertLinkedListToObjectInstances(result, classes);
    }

    public void populateClasses()
    {
        classes = new String[result.length];
        for(int i = 0; i < result.length; i++)
        {
            classes[i] = result[i].getClass().getName();
        }
    }
    
    public void addObjectToResults(Object object)
    {
        Object[] temp;
        
        if(result == null || result.length < 1)
        {
            temp = new Object[1];
            temp[0] = object;
        }
        else
        {
            temp = new Object[result.length + 1];

            System.arraycopy(result, 0, temp, 0, result.length);

            temp[temp.length - 1] = object;
        }
        result = temp;
    }

    @Override
    public String toString() {
        return success + " " + exceptionMessage + " " + Arrays.asList(result).toString();
    }
    
    
}
