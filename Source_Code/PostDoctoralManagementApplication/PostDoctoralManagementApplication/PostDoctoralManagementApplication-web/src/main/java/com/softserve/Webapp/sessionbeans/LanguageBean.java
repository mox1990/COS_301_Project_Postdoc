/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.Webapp.sessionbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

/**
 *
 * @author kgothatso
 */
@Named(value = "languageBean")
@SessionScoped
public class LanguageBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String localeCode;
    private Locale langauge;
    private static Map<String, Object> lang;
    static
    {
        lang = new LinkedHashMap<String, Object>();
        lang.put("English", Locale.ENGLISH);
        lang.put("Afrikaans", new Locale("af"));
        lang.put("Sesotho", new Locale("st"));
    }
    
    public Map<String, Object> getLangaugesInMap()
    {
        return lang;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localCode) {
        this.localeCode = localCode;
    }
    
    public Locale getLocale()
    {
        System.out.println("Locale set at: " + (Locale)lang.get(localeCode));
        return ((Locale)lang.get(localeCode));
    }

    public Locale getLangauge() {
        return langauge;
    }

    public void setLangauge(Locale langauge) {
        this.langauge = langauge;
    }
    
    
    /**
     * Creates a new instance of LanguageBean
     */
    public LanguageBean() {
        System.out.println("Setting as " + getLocale());
    }
    
    @PostConstruct
    public void init()
    {
        System.out.println("Is this the problem?");
        langauge = new Locale(Locale.ENGLISH.getLanguage());
    }
    
    
    public void langLocaleCodeChanged(ValueChangeEvent e)
    {
        String newLocaleValue = e.getNewValue().toString();
        for(Map.Entry<String, Object> entry: lang.entrySet())
        {
            if(entry.getValue().toString().equals(newLocaleValue))
            {
                langauge = (Locale) entry.getValue();
                FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
            }
        }
    }
    
}

