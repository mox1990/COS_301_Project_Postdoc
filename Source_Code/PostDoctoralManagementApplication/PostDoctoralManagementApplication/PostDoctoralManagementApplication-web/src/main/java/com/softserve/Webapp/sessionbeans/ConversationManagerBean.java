/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.Webapp.sessionbeans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Named(value = "conversationManagerBean")
@SessionScoped
public class ConversationManagerBean implements Serializable {
    
    private ArrayList<Conversation> trackedConversations;
    
    /**
     * Creates a new instance of conversationManagerBean
     */
    public ConversationManagerBean() {
    }
    
    @PostConstruct
    public void init()
    {
        trackedConversations = new ArrayList<Conversation>();
    }
    
    public Conversation getConversationAt(int index) throws ArrayIndexOutOfBoundsException
    {
        if(index > -1 && index < trackedConversations.size())
        {
            return trackedConversations.get(index);
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    public void startConversation(int index) throws ArrayIndexOutOfBoundsException
    {
        if(index > -1 && index < trackedConversations.size())
        {
            if(!isConversationActive(index))
            {
                trackedConversations.get(index).begin();
            }
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    public void startConversation(Conversation conversation) throws ArrayIndexOutOfBoundsException
    {
        startConversation(getIndexOfConversation(conversation));
    }
    
    public void stopConversation(int index) throws ArrayIndexOutOfBoundsException
    {
        if(index > -1 && index < trackedConversations.size())
        {
            if(isConversationActive(index))
            {
                trackedConversations.get(index).end();
            }
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    public void stopConversation(Conversation conversation) throws ArrayIndexOutOfBoundsException
    {
        stopConversation(getIndexOfConversation(conversation));
    }
    
    public boolean isConversationActive(int index) throws ArrayIndexOutOfBoundsException
    {
        if(index > -1 && index < trackedConversations.size())
        {
            return !trackedConversations.get(index).isTransient();
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    public void registerConversation(Conversation conversation) throws ArrayIndexOutOfBoundsException
    {
        trackedConversations.add(conversation);
    }
        
    public void deregisterConversation(int index) throws ArrayIndexOutOfBoundsException
    {
        System.out.println("=================next");
        if(index > -1 && index < trackedConversations.size())
        {
             
            if(isConversationActive(index))
            {
                stopConversation(index);
            }
            
            trackedConversations.remove(index);
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    public void deregisterConversation(Conversation conversation) throws ArrayIndexOutOfBoundsException
    {
        deregisterConversation(getIndexOfConversation(conversation));
    }
    
    public void deregisterAllConverstations() throws ArrayIndexOutOfBoundsException
    {
        for(int i = 0; i < trackedConversations.size(); i++)
        {
            deregisterConversation(i);
        }
    }
    
    public void stopAllConverstations() throws ArrayIndexOutOfBoundsException
    {
        for(int i = 0; i < trackedConversations.size(); i++)
        {
            stopConversation(i);
        }
    }
    
    public int getNumberOfTrackedConversations() 
    {
        return trackedConversations.size();
    }    
    
    public int getIndexOfConversation(Conversation conversation) throws ArrayIndexOutOfBoundsException
    {
        return trackedConversations.indexOf(conversation);
    }
       
}
