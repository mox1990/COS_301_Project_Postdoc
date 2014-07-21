/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.conversationbeans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "conversationManagerBean")
@ConversationScoped
public class conversationManagerBean implements Serializable {
    
    @Inject
    private Conversation conversation;
    
    private ArrayList<Object> converstationStroage;
    
    /**
     * Creates a new instance of conversationManagerBean
     */
    public conversationManagerBean() {
    }
    
    public Conversation getConversation()
    {
        return  conversation;
    }
    
    public void startConversation()
    {
        conversation.begin();
        converstationStroage = new ArrayList<Object>();
    }
    
    public void stopConversation()
    {
        clearConversationStorage();
        converstationStroage = null;
        conversation.end();
    }
    
    public <T> T getObjectFromStroage(int index, Class<T> objectClass)
    {
        try
        {
            return objectClass.cast(converstationStroage.get(index));
        }
        catch(ClassCastException ex)
        {
            return null;
        }
    }
    
    public void addObjectToStorage(Object object)
    {
        converstationStroage.add(object);
    }
    
    public void removeObjectFromStorageAt(int index)
    {
        converstationStroage.remove(index);
    }
    
    public void clearConversationStorage()
    {
        converstationStroage.clear();
    }
}
