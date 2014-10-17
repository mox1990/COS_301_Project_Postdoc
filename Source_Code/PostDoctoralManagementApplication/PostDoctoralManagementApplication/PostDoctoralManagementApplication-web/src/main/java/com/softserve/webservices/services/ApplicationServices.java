/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.webservices.services;

import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.util.HashUtil;
import com.softserve.ejb.applicationservices.ApplicationProgressViewerServiceLocal;
import com.softserve.ejb.nonapplicationservices.UserGatewayLocal;
import com.softserve.webservices.util.JSONConverterUtil;
import com.softserve.webservices.util.PayloadUtil;
import java.util.Objects;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Path("/ApplicationServices")
public class ApplicationServices {
    
    @Context
    private HttpServletRequest httpServletRequest;
    
    @EJB
    private ApplicationProgressViewerServiceLocal applicationProgressViewerServiceLocal;
    @EJB
    private UserGatewayLocal userGatewayLocal; 
    
    
    @Path("/applicationProgressViewerService_getAllApplications")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String applicationProgressViewerService_getAllApplications(@HeaderParam("session") String Session)
    {        
        try
        {
            Object[] payLoad = applicationProgressViewerServiceLocal.getAllApplications(JSONConverterUtil.JSONToObject_JACKSON(Session, Session.class)).toArray();
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception exception)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, exception);
        }
    }
    
    @Path("/login")
    @GET
    @Produces(MediaType.TEXT_PLAIN)    
    public String getHTMLText(@QueryParam("username") String username, @QueryParam("password") String password)
    {
        System.out.println("Rest login " + username + " " + password);
        
        HttpSession httpSession = httpServletRequest.getSession(false);
        
        
        if(httpSession == null)
        {
            httpSession = httpServletRequest.getSession(true);        
        }
        
        System.out.println(httpSession);
        
        try
        {
            httpSession.setAttribute("username",username);
            httpSession.setAttribute("password", HashUtil.hashInput(password));
            httpSession.setAttribute("status", Boolean.FALSE);
            
            Session session = userGatewayLocal.getSessionFromHttpSession(httpSession);        
            
            userGatewayLocal.login(session);
            
            Object[] payLoad = new Object[1];
            payLoad[0] = session;
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception ex)
        {        
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, ex);
        }
    }
    
}
