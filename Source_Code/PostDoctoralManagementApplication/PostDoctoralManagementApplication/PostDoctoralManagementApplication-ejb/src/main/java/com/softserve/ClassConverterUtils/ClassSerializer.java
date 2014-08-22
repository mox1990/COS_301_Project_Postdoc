/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ClassConverterUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public abstract class ClassSerializer {
    
    protected final List<Class> classWrappers = new ArrayList<Class>() {{
                                                                           add(Integer.class); 
                                                                           add(Long.class); 
                                                                           add(Float.class); 
                                                                           add(Double.class); 
                                                                           add(Boolean.class); 
                                                                           add(Byte.class); 
                                                                           add(Character.class); 
                                                                           add(Short.class);
                                                                    }};
    
    public abstract String convertClassToSerial(Object object, List<ConvertProperty> properties);
    public abstract String convertMembersToHeading(Object object, List<ConvertProperty> properties);
}
