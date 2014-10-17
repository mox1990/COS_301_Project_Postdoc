/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.webservices.services;

import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.auxiliary.requestresponseclasses.Session;
import com.softserve.auxiliary.util.HashUtil;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.ejb.nonapplicationservices.UserGatewayLocal;
import com.softserve.persistence.DBEntities.Person;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Path("/test")
public class Test {
    
    @EJB
    private UserGatewayLocal userGatewayLocal; 
    
    @Context
    private HttpServletRequest httpServletRequest;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText()
    {
        return "TEST COMPLETE";
    }
    
    @Path("/login")
    @GET
    @Produces(MediaType.TEXT_HTML)    
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
            System.out.println("EJB:" + userGatewayLocal);
            Session session = userGatewayLocal.getSessionFromHttpSession(httpSession);
        
            System.out.println("Login: " + httpSession.toString());
            
            userGatewayLocal.login(session);
            
            System.out.println("Login: Account status = " + session.getUser().getAccountStatus());
            
            
            
            return "<!DOCTYPE html><html><body>Login as" + session.getUser().getCompleteName() + "</body></html>";
        }
        catch(Exception ex)
        {        
            return "<!DOCTYPE html><html><body>" + ex +"</body></html>";
        }
    }
    
    @Path("/testjson")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public content getContent(@QueryParam("username") String username, @QueryParam("password") String password)
    {
        content c = new content();
            c.setName(username);
            c.setPass(password);
        return c;
    }
    
    @Path("/loginxml")
    @GET
    @Produces(MediaType.APPLICATION_XML)    
    public JAXBElement<content> getXML(@QueryParam("username") String username, @QueryParam("password") String password)
    {
        System.out.println("Rest xml login " + username + " " + password);
        
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
            System.out.println("EJB:" + userGatewayLocal);
            Session session = userGatewayLocal.getSessionFromHttpSession(httpSession);
        
            System.out.println("Login: " + httpSession.toString());
            
            userGatewayLocal.login(session);
            
            System.out.println("Login: Account status = " + session.getUser().getAccountStatus());
            
            content c = new content();
            c.setName(username);
            c.setPass(password);
            
            return new JAXBElement<content>(new QName("content"),content.class,c);
        }
        catch(Exception ex)
        {        
            return new JAXBElement<content>(new QName("content"),content.class,new content());
        }
    }
    
    public class content
    {
        private String name;
        private String pass;
        
        public content() {
        }

        public String getName() {
            return name;
        }

        public String getPass() {
            return pass;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }
        
        
        
    }
}
