/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.requestbeans;

import com.softserve.Webapp.sessionbeans.ConversationManagerBean;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.LocationManagementServiceLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "locationFinderRequestBean")
@ConversationScoped
public class LocationFinderRequestBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    @Inject
    private ConversationManagerBean conversationManagerBean;
    @Inject
    private Conversation conversation;
    
    @EJB
    private LocationManagementServiceLocal locationManagementServiceLocal;
    
    
    
    private List<String> AllAllFacultiesInInstitution;
    private List<String> AllAllDeparmentsInFacultyInInstitution;
    
    /**
     * Creates a new instance of LocationFinderRequestBean
     */
    public LocationFinderRequestBean() {
    }
    
    @PostConstruct
    public void init()
    {
        conversationManagerBean.registerConversation(conversation);
        conversationManagerBean.startConversation(conversation);
    }
    
    public List<String> getAllInstitutions()
    {
        System.out.println("============" + "Called");
        try 
        {
            return locationManagementServiceLocal.getAllInstitutions();
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(LocationFinderRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            return new ArrayList<String>();
        }
    }
    
    public void getAllFacultiesInInstitution(String insititution)
    {
        System.out.println("============" + insititution);
        try 
        {
            AllAllFacultiesInInstitution = locationManagementServiceLocal.getAllFacultiesInInstitution(insititution);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(LocationFinderRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            AllAllFacultiesInInstitution = new ArrayList<String>();
        }
    }
    
    public void getAllDeparmentsInFacultyInInstitution(String insititution, String faculty)
    {
        System.out.println("============" + insititution);
        System.out.println("============" + faculty);
        try 
        {
            AllAllDeparmentsInFacultyInInstitution = locationManagementServiceLocal.getAllDepartmentsInFacultyInInstitution(insititution,faculty);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.logException(LocationFinderRequestBean.class, ex);
            ExceptionUtil.handleException(null, ex);
            AllAllDeparmentsInFacultyInInstitution = new ArrayList<String>();
        }
    }

    public List<String> getAllAllFacultiesInInstitution() {
        return AllAllFacultiesInInstitution;
    }

    public List<String> getAllAllDeparmentsInFacultyInInstitution() {
        return AllAllDeparmentsInFacultyInInstitution;
    }
    
}
