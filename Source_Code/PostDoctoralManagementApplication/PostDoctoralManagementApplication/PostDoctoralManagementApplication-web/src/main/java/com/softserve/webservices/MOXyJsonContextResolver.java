/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.webservices;
import javax.ws.rs.ext.*;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
 
@Provider
public class MOXyJsonContextResolver implements ContextResolver<MoxyJsonConfig> {
 
    private final MoxyJsonConfig config;
 
    public MOXyJsonContextResolver() {
        System.out.println("MOXY CONSTRUCT=======================");
        config = new MoxyJsonConfig()
            .setAttributePrefix("")
            .setValueWrapper("value")
            .property(JAXBContextProperties.JSON_WRAPPER_AS_ARRAY_NAME, true);
    }
 
    @Override
    public MoxyJsonConfig getContext(Class<?> objectType) {
        System.out.println("MOXY CONTEXT=======================");
        return config;
    }
 
}
