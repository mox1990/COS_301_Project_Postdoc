/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.Webapp.sessionbeans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

/**
 *
 * @author kgothatso
 */
@Named(value = "languageBean")
@ConversationScoped
public class LanguageBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String localeCode;
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
    
    /**
     * Creates a new instance of LanguageBean
     */
    public LanguageBean() {
        System.out.println("Being born...");
    }
    
    public void langLocaleCodeChanged(ValueChangeEvent e)
    {
        String newLocaleValue = e.getNewValue().toString();
        for(Map.Entry<String, Object> entry: lang.entrySet())
        {
            if(entry.getValue().toString().equals(newLocaleValue))
            {
                FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
            }
        }
    }
    
}
