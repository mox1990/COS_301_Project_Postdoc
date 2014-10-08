/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.auxillary.HTTPScrapers.GoogleScholarQuery;
import com.softserve.auxillary.HTTPScrapers.GoogleScholarScraper;
import com.softserve.Webapp.sessionbeans.NavigationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "indexBean")
@RequestScoped
public class IndexBean {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject
    private NavigationManagerBean navigationManagerBean;

    private String usernameOrEmail = "";
    private String password = "";    
    
    /**
     * Creates a new instance of indexBean
     */
    public IndexBean() {
    }
    
    @PostConstruct
    public void init() 
    {
       /* GoogleScholarScraper googleScholarScrubber = new GoogleScholarScraper();
        
        try {
            googleScholarScrubber.getResultsFromQuery(new GoogleScholarQuery("liquid", "john", "journal", 2010, 2012));
        } catch (IOException ex) {
            Logger.getLogger(IndexBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(IndexBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(IndexBean.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }   
    
    public void setupIndexPage()
    {
        sessionManagerBean.resetSession();
        navigationManagerBean.clearBreadCrumbsTo(0);
    }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String performLoginRequest() throws NoSuchAlgorithmException 
    {
        return sessionManagerBean.login(usernameOrEmail,password);
    }
}
