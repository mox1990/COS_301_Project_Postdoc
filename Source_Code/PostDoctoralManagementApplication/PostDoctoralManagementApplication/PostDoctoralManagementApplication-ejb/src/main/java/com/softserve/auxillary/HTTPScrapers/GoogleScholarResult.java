/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxillary.HTTPScrapers;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class GoogleScholarResult {
    private String type;
    private String title;
    private String authors;
    private String content;
    private String link;
    private String publishedIn;
    private String datePublished;

    public GoogleScholarResult() {
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthors() {
        return authors;
    }

    public String getContent() {
        return content;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getPublishedIn() {
        return publishedIn;
    }

    public void setPublishedIn(String publishedIn) {
        this.publishedIn = publishedIn;
    }

    @Override
    public String toString() {
        return "[ Authors = " + authors + ", Title = " + title + ", link = " + link + ", Published in = " + publishedIn + ", published date = " + datePublished + " ]";  
    }
    
    
    
}
