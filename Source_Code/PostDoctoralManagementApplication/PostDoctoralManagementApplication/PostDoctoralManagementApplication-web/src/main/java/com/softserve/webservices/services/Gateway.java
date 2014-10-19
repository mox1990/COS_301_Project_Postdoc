/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.webservices.services;

import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.util.HashUtil;
import com.softserve.ejb.nonapplicationservices.UserGatewayLocal;
import com.softserve.webservices.util.JSONConverterUtil;
import com.softserve.webservices.util.PayloadUtil;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Path("/Gateway")
public class Gateway {
    @Context
    private HttpServletRequest httpServletRequest;
    
    @EJB
    private UserGatewayLocal userGatewayLocal;
    
    @Path("/login")
    @POST
    @Produces(MediaType.TEXT_PLAIN)    
    public String gateway_login(@HeaderParam("username") String username, @HeaderParam("password") String password)
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
    
    
    @Path("/logout")
    @POST
    @Produces(MediaType.TEXT_PLAIN)    
    public String gateway_logout(@HeaderParam("session") String session)
    {
        HttpSession httpSession = httpServletRequest.getSession(false);
        
        try
        {            
            userGatewayLocal.logout(JSONConverterUtil.JSONToObject_JACKSON(session, Session.class));        
            httpSession.invalidate();
            
            Object[] payLoad = new Object[1];
            
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }
        catch(Exception ex)
        {        
            Object[] payLoad = new Object[1];
            return PayloadUtil.createSuccesPayLoad(payLoad);
        }        
    }
    
    @Path("/login")
    @POST
    @Produces(MediaType.TEXT_PLAIN)    
    public String gateway_getSessionFromHttpSession()
    {
        HttpSession httpSession = httpServletRequest.getSession(false);
        
        
        if(httpSession != null)
        {
            return PayloadUtil.createFailurePayLoad(ApplicationServices.class, new Exception("No session active"));   
        }
        
        System.out.println(httpSession);
        
        try
        {
            
            Session session = userGatewayLocal.getSessionFromHttpSession(httpSession);
            
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
