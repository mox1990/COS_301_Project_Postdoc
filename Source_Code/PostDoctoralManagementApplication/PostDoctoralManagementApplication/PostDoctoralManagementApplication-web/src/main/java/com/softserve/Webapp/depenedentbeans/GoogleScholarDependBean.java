/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.depenedentbeans;

import auto.softserve.XMLEntities.CV.Reference;
import com.softserve.DBEntities.Person;
import com.softserve.HTTPScrapers.GoogleScholarQuery;
import com.softserve.HTTPScrapers.GoogleScholarResult;
import com.softserve.Webapp.sessionbeans.SessionManagerBean;
import com.softserve.Webapp.util.ExceptionUtil;
import com.softserve.ejb.nonapplicationservices.GoogleScholarServicesLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "googleScholarDependBean")
@Dependent
public class GoogleScholarDependBean implements Serializable {
    
    @Inject
    private SessionManagerBean sessionManagerBean;
    
    @EJB
    private GoogleScholarServicesLocal googleScholarServicesLocal;
    
    private String title;
    private String author;
    
    private GoogleScholarResult selectedResult;
    private List<GoogleScholarResult> searchResults;
    
    
    /**
     * Creates a new instance of GoogleScholarRequestBean
     */
    public GoogleScholarDependBean() {
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GoogleScholarResult getSelectedResult() {
        return selectedResult;
    }

    public void setSelectedResult(GoogleScholarResult selectedResult) {
        this.selectedResult = selectedResult;
    }

    public List<GoogleScholarResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<GoogleScholarResult> searchResults) {
        this.searchResults = searchResults;
    }
    
    
            
    public void SearchResultsForTitle()
    {
        GoogleScholarQuery googleScholarQuery = new GoogleScholarQuery(title, "", "", "", "", "", 0, 0, true);
        
        try 
        {
            searchResults = googleScholarServicesLocal.searchGoogleScholarUsing(googleScholarQuery);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(null, ex);
            ExceptionUtil.logException(GoogleScholarDependBean.class, ex);
            searchResults = new ArrayList<GoogleScholarResult>();
        }
    }
    
    public void getSearchResultsForAuthors()
    {
        
        try 
        {
            if(author == null || author.equals(""))
            {
                Person person = sessionManagerBean.getSession().getUser();
                author = person.getFullName() + " " + person.getSurname();
            }
        
            GoogleScholarQuery googleScholarQuery = new GoogleScholarQuery("", "", "", "", author, "", 0, 0, true);
        
        
            searchResults = googleScholarServicesLocal.searchGoogleScholarUsing(googleScholarQuery);
        } 
        catch (Exception ex) 
        {
            ExceptionUtil.handleException(null, ex);
            ExceptionUtil.logException(GoogleScholarDependBean.class, ex);
            searchResults = new ArrayList<GoogleScholarResult>();
        }
    }
    
    public Reference getSelectedResultAsReference()
    {
        Reference reference = new Reference();
        
        GregorianCalendar calendar = new GregorianCalendar();
        XMLGregorianCalendar xMLGregorianCalendar = null;
        try
        {
            calendar.set(GregorianCalendar.YEAR, Integer.parseInt(selectedResult.getDatePublished()));
            xMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            reference.setPublicationDate(xMLGregorianCalendar);
        }
        catch(Exception ex)
        {
            ExceptionUtil.logException(GoogleScholarDependBean.class, ex);
            reference.setPublicationDate(null);
        }
        
        
        reference.setPublicationName(selectedResult.getTitle());
        reference.setPublicationMediumName(selectedResult.getPublishedIn());
        
        return reference;
    }
    
}
