/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.Person;
import java.util.List;

/**
 * This EJB handles the notification services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
class NotificationRequest {
    public final static String EMAIL = "EMAIL";
    public final static String SYSTEM = "NOTIFICATION";
    
    public String message;
    public String subject;
    public String nType = SYSTEM;
    public List<Person> recipients; // Update the model diagram
    public Person sender;
    
    public NotificationRequest(String message, String nType, List<Person> recipients)
    {
        this.message = message;
        this.nType = nType;
        this.recipients = recipients;
    }
}