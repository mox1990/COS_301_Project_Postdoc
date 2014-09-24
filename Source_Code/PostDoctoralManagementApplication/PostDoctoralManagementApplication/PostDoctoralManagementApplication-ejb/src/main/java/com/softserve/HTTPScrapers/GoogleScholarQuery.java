/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.HTTPScrapers;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class GoogleScholarQuery {
    private final String URL = "http://scholar.google.com/scholar";
    
    private final String WITH_WORD = "as_q=";
    private final String WITH_EXACT_PHRASE = "as_epq=";
    private final String WITH_AT_LEAST_ONE_SUCH_WORD = "as_op=";
    private final String WITHOUT_WORDS = "as_eq=";
    private final String WHERE_WORDS_OCCUR = "as_occt=";
    
    private final String WITH_AUTHOR = "as_sauthors=";
    private final String PUBLISHED_IN = "as_publication=";
    private final String WHERE_YEAR_START = "as_yin=";
    private final String WHERE_YEAR_END = "as_yhi=";  
    
    private final String LANG = "hl=";
    private final String STOPPER = "&btnG=&as_sdt=0%2C5";
    
    private final String CONCATENATE = "&";
    
    private String title;
    private String authors;
    private String publishedIn;
    private int betweenYearStart;
    private int betweenYearEnd;

    public GoogleScholarQuery(String title, String authors, String publishedIn, int betweenYearStart, int betweenYearEnd) {
        this.title = title;
        this.authors = authors;
        this.publishedIn = publishedIn;
        this.betweenYearStart = betweenYearStart;
        this.betweenYearEnd = betweenYearEnd;
    }
    //http://scholar.google.co.za/scholar?as_q=liquid&as_epq=&as_oq=&as_eq=&as_occt=title&as_sauthors=John&as_publication=Journal&as_ylo=1996&as_yhi=2001&btnG=&hl=en&as_sdt=0%2C5
    public String generateQueryURL()
    {
        String outputURL = URL + "?";
        
        outputURL += WITH_WORD + title + CONCATENATE;
        outputURL += WITH_EXACT_PHRASE + CONCATENATE;
        outputURL += WITH_AT_LEAST_ONE_SUCH_WORD + CONCATENATE;
        outputURL += WITHOUT_WORDS + CONCATENATE;
        outputURL += WHERE_WORDS_OCCUR + "title" + CONCATENATE;
        outputURL += WITH_AUTHOR + authors + CONCATENATE;
        outputURL += PUBLISHED_IN + publishedIn + CONCATENATE;
        outputURL += WHERE_YEAR_START + betweenYearStart + CONCATENATE;
        outputURL += WHERE_YEAR_END + betweenYearEnd + CONCATENATE;
        outputURL += LANG + "en" + CONCATENATE + STOPPER;
        
        return outputURL;
    }
    
}
