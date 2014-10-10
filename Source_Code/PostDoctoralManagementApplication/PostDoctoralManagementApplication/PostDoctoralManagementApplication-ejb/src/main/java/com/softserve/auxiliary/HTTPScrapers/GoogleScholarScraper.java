/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.auxiliary.HTTPScrapers;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.jsoup.Jsoup;
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
    
    
    public List<GoogleScholarResult> getResultsFromQuery(GoogleScholarQuery googleScholarQuery) throws Exception
    {
        
        System.setProperty("http.agent", "");
        
        System.out.println("Google scholar: About to connect using " + googleScholarQuery.generateQueryURL());
        Document document = Jsoup.connect(googleScholarQuery.generateQueryURL()).userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0").cookie("GOOGLE_ABUSE_EXEMPTION", "ID=7d4f53bdd7b788a6:TM=1411726594:C=c:IP=197.76.186.148-:S=APGng0uE695Q69xpETxxDEsPjVGPpKNoFg").get();
        System.out.println("Google scholar: Page retrived and DOM constructed for " + googleScholarQuery.generateQueryURL());
        
        ArrayList<GoogleScholarResult> searchResults = new ArrayList<GoogleScholarResult>();  
        
        System.out.println("Google scholar: Parsing results");
        for(Element element : document.getElementsByAttributeValue("class", "gs_r"))
        {
            GoogleScholarResult googleScholarResult = new GoogleScholarResult();
            
            if(element.getElementsByClass("gs_rt").size() > 0)
            {
                googleScholarResult.setTitle(element.getElementsByClass("gs_rt").get(0).getElementsByTag("a").text().trim());
                googleScholarResult.setLink(element.getElementsByClass("gs_rt").get(0).getElementsByTag("a").attr("href").trim());
            }

            if(element.getElementsByClass("gs_a").size() > 0)
            {
                String gs_a = element.getElementsByClass("gs_a").get(0).text();
                //Authors
                if(gs_a.contains(" -"))
                {
                    googleScholarResult.setAuthors(gs_a.substring(0, gs_a.indexOf(" -")).trim());
                }
                
                
                int endIndex = gs_a.lastIndexOf(" - ");
                int startIndex = gs_a.lastIndexOf(" - ",endIndex - 1) + 3;
                int subIndex = -1;
                
                
                //Publised in && year
                if(startIndex > -1 &&  endIndex > -1 && startIndex <= endIndex)
                {
                    String publicationString = gs_a.substring(startIndex, endIndex).trim();
                    
                    subIndex = publicationString.lastIndexOf(",");
                    System.out.println(publicationString);
                    if(subIndex > -1)
                    {
                        System.out.println("Scanning for both: " + publicationString);
                        googleScholarResult.setPublishedIn(publicationString.substring(0, subIndex).trim());
                        googleScholarResult.setDatePublished(publicationString.substring(subIndex + 1).trim());
                    }
                    else
                    {                        
                        System.out.println("Scanning for one: " + publicationString);
                        if(Character.isDigit(publicationString.charAt(0)) && publicationString.length() < 5)
                        {
                            googleScholarResult.setDatePublished(publicationString);
                        }
                        else
                        {
                            googleScholarResult.setPublishedIn(publicationString);
                        }
                    }
                }
            }
            
            searchResults.add(googleScholarResult);
        }
        
        System.out.println("Google scholar: Results parsed");
        System.out.println("Google scholar: Results " + searchResults.toString());
        
        return searchResults;        
    }

    
}
