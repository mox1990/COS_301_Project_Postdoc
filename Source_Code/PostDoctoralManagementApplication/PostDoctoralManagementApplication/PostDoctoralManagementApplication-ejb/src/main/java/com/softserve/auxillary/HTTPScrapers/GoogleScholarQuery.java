/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxillary.HTTPScrapers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

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
    private final String BTNG = "btnG=";
    private final String PATENTNVIS = "as_std=";
    private final String CITATIONVIS = "as_vis=";
    
    private final String CONCATENATE = "&";
    
    private String wordsToLookFor;
    private String WordsToMatchExact;
    private String wordsThatOccurAtleasetOnce;
    private String wordsToNotLookFor;
    
    
    private String authors;
    private String publishedIn;
    private int betweenYearStart;
    private int betweenYearEnd;
    private boolean lookinTitle;
    private boolean patentsVisable;
    private boolean citationsVisable;
    private String langToUse;

    
    public GoogleScholarQuery(String wordsToLookFor, String WordsToMatchExact, String wordsThatOccurAtleasetOnce, String wordsToNotLookFor, String authors, String publishedIn, int betweenYearStart, int betweenYearEnd, boolean lookinTitle) {
        this.wordsToLookFor = wordsToLookFor;
        this.WordsToMatchExact = WordsToMatchExact;
        this.wordsThatOccurAtleasetOnce = wordsThatOccurAtleasetOnce;
        this.wordsToNotLookFor = wordsToNotLookFor;
        this.authors = authors;
        this.publishedIn = publishedIn;
        this.betweenYearStart = betweenYearStart;
        this.betweenYearEnd = betweenYearEnd;
        this.lookinTitle = lookinTitle;
        this.langToUse = "en";
        this.citationsVisable = false;
        this.patentsVisable = false;
    }
    
    
    public GoogleScholarQuery(String wordsToLookFor, String WordsToMatchExact, String wordsThatOccurAtleasetOnce, String wordsToNotLookFor, String authors, String publishedIn, int betweenYearStart, int betweenYearEnd, boolean lookinTitle, String langToUse) {
        this.wordsToLookFor = wordsToLookFor;
        this.WordsToMatchExact = WordsToMatchExact;
        this.wordsThatOccurAtleasetOnce = wordsThatOccurAtleasetOnce;
        this.wordsToNotLookFor = wordsToNotLookFor;
        this.authors = authors;
        this.publishedIn = publishedIn;
        this.betweenYearStart = betweenYearStart;
        this.betweenYearEnd = betweenYearEnd;
        this.lookinTitle = lookinTitle;
        this.langToUse = langToUse;
        this.citationsVisable = false;
        this.patentsVisable = false;
    }

    public GoogleScholarQuery(String wordsToLookFor, String WordsToMatchExact, String wordsThatOccurAtleasetOnce, String wordsToNotLookFor, String authors, String publishedIn, int betweenYearStart, int betweenYearEnd, boolean lookinTitle, boolean patentsVisable, boolean citationsVisable, String langToUse) {
        this.wordsToLookFor = wordsToLookFor;
        this.WordsToMatchExact = WordsToMatchExact;
        this.wordsThatOccurAtleasetOnce = wordsThatOccurAtleasetOnce;
        this.wordsToNotLookFor = wordsToNotLookFor;
        this.authors = authors;
        this.publishedIn = publishedIn;
        this.betweenYearStart = betweenYearStart;
        this.betweenYearEnd = betweenYearEnd;
        this.lookinTitle = lookinTitle;
        this.patentsVisable = patentsVisable;
        this.citationsVisable = citationsVisable;
        this.langToUse = langToUse;
    }
    
    

    

    
    
    

    
    //http://scholar.google.co.za/scholar?as_q=liquid&as_epq=&as_oq=&as_eq=&as_occt=title&as_sauthors=John&as_publication=Journal&as_ylo=1996&as_yhi=2001&btnG=&hl=en&as_sdt=0%2C5
    //http://scholar.google.co.za/scholar?as_q=1&as_epq=2&as_oq=3&as_eq=4&as_occt=any&as_sauthors=5&as_publication=6&as_ylo=7&as_yhi=8&btnG=&hl=en&as_sdt=0%2C5
    //http://scholar.google.co.za/scholar?start=20&q=hh&hl=en&as_publication=6&as_sdt=0,5
    //http://scholar.google.co.za/scholar?hl=en&as_publication=6&as_sdt=0,5&q=hh&scisbd=1
    //http://scholar.google.com/scholar?as_q=1&as_epq=2&as_oq=3&as_eq=4&as_occt=any&as_sauthors=5&as_publication=6&as_ylo=7&as_yhi=8&btnG=&hl=en&as_sdt=0%2C5&as_vis=1
    public String generateQueryURL() throws UnsupportedEncodingException
    {
        String outputURL = URL + "?";
        
        ArrayList<String> parts = new ArrayList<String>();
        Random generator = new Random(System.currentTimeMillis());

        parts.add(PATENTNVIS + ((patentsVisable)?"0":"1") + ((!patentsVisable)?",5":"%2C5") + CONCATENATE + WITH_WORD + URLEncoder.encode(wordsToLookFor,"UTF-8"));
        parts.add(WITH_EXACT_PHRASE + URLEncoder.encode(WordsToMatchExact,"UTF-8"));
        parts.add(WITH_AT_LEAST_ONE_SUCH_WORD + URLEncoder.encode(wordsThatOccurAtleasetOnce,"UTF-8"));
        parts.add(WITHOUT_WORDS + URLEncoder.encode(wordsToNotLookFor,"UTF-8"));
        parts.add(WHERE_WORDS_OCCUR + ((lookinTitle)?"title":"any"));
        parts.add(WITH_AUTHOR + URLEncoder.encode(authors,"UTF-8"));
        parts.add(PUBLISHED_IN + URLEncoder.encode(publishedIn,"UTF-8"));
        parts.add(WHERE_YEAR_START + ((betweenYearStart < 1)?"":betweenYearStart));
        parts.add(WHERE_YEAR_END + ((betweenYearEnd < 1)?"":betweenYearEnd));
        parts.add(BTNG);
        parts.add(LANG + langToUse);
        
        if(!(patentsVisable && citationsVisable))
        {
            parts.add(CITATIONVIS + ((citationsVisable)?"0":"1"));
        }
        
        
        while(!parts.isEmpty())
        {
            int index = (int) (generator.nextFloat() * parts.size());
            outputURL += parts.get(index);
            parts.remove(index);
            if(parts.size() > 0)
            {
                outputURL += CONCATENATE;
            }
        }
        
        return outputURL;
    }
    
}
