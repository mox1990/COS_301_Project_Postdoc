/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.HTTPScrapers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class GoogleScholarScraper {

    
    
    public GoogleScholarScraper() 
    {        
        
    }
    
    
    public List<GoogleScholarResult> getResultsFromQuery(GoogleScholarQuery googleScholarQuery) throws IOException, ParserConfigurationException, SAXException
    {
        
        System.setProperty("http.agent", "");
 
        Document document = Jsoup.connect(googleScholarQuery.generateQueryURL()).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/31.0").get();
        
        ArrayList<GoogleScholarResult> searchResults = new ArrayList<GoogleScholarResult>();        
        for(Element element : document.getElementsByAttributeValue("class", "gs_r"))
        {
            GoogleScholarResult googleScholarResult = new GoogleScholarResult();
            
            googleScholarResult.setTitle(element.getElementsByClass("gs_rt").get(0).getElementsByTag("a").text());
            googleScholarResult.setLink(element.getElementsByClass("gs_rt").get(0).getElementsByTag("a").attr("href"));

            if(element.getElementsByClass("gs_a").size() > 0)
            {
                String gs_ra = element.getElementsByClass("gs_a").get(0).text();
                if(gs_ra.contains(" -"))
                {
                    googleScholarResult.setAuthors(gs_ra.substring(0, gs_ra.indexOf(" -")));
                }
                
                if(gs_ra.contains(" - ") && gs_ra.contains(","))
                {
                    googleScholarResult.setPublishedIn(gs_ra.substring(gs_ra.indexOf(" - ") + 3, gs_ra.indexOf(",", gs_ra.indexOf(" - ") + 3)));
                }
                
                if(gs_ra.contains(" - ") && gs_ra.contains(","))
                {
                    googleScholarResult.setDatePublished(gs_ra.substring(gs_ra.indexOf(",", gs_ra.indexOf(" - ") + 3) + 2, gs_ra.lastIndexOf(" - ")));
                }
            }
            
            searchResults.add(googleScholarResult);
        }
        
        System.out.println(searchResults.toString());
        
        return searchResults;        
    }

    
}
