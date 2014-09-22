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
        System.out.println("=============Conversation manager: Starting manager");
        
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
            System.out.println("=============Conversation manager: Cannot find as Conversation at index " + index + " is not being tracked");
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    public void startConversation(int index) throws ArrayIndexOutOfBoundsException
    {
        if(index > -1 && index < trackedConversations.size())
        {
            if(!isConversationActive(index))
            {
                System.out.println("=============Conversation manager: Starting conversation (cid = " + trackedConversations.get(index).getId() + ") at index " +  index + "...");
                trackedConversations.get(index).begin();
                System.out.println("=============Conversation manager: Conversation (cid = " + trackedConversations.get(index).getId() + ") at index " +  index + " started");
            }
            else
            {
                System.out.println("=============Conversation manager: Conversation (cid = " + trackedConversations.get(index).getId() + ") at index " +  index + " is already active");
            }
        }
        else
        {
            System.out.println("=============Conversation manager: Cannot start as Conversation at index " + index + " is not being tracked");
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
                System.out.println("=============Conversation manager: Stopping conversation (cid = " + trackedConversations.get(index).getId() + ") at index " +  index + "...");
                trackedConversations.get(index).end();
                System.out.println("=============Conversation manager: Conversation (cid = " + trackedConversations.get(index).getId() + ") at index " +  index + " stopped");
            }
            else
            {
                System.out.println("=============Conversation manager: Conversation (cid = " + trackedConversations.get(index).getId() + ") at index " +  index + " has already been stopped");
            }
        }
        else
        {
            System.out.println("=============Conversation manager:  Cannot stop as  Conversation at index " + index + " is not being tracked");
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
            System.out.println("=============Conversation manager: Cannot check activity as Conversation at index " + index + " is not being tracked");
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    public void registerConversation(Conversation conversation) throws ArrayIndexOutOfBoundsException
    {
        if(!isConversationRegistered(conversation))
        {
            System.out.println("=============Conversation manager: Registering conversation (cid = " + conversation.getId() + ")...");
            trackedConversations.add(conversation);
            System.out.println("=============Conversation manager: Conversation (cid = " + trackedConversations.get(trackedConversations.size() - 1).getId() + ") at index " +  (trackedConversations.size() - 1) + " registered");
        }
        else
        {
            System.out.println("=============Conversation manager: Conversation (cid = " + conversation.getId() + ") has already been registered");
        }
    }
        
    public void deregisterConversation(int index) throws ArrayIndexOutOfBoundsException
    {
        
        if(index > -1 && index < trackedConversations.size())
        {
             
            if(isConversationActive(index))
            {
                stopConversation(index);
            }
            System.out.println("=============Conversation manager: Deregistering conversation (cid = " + trackedConversations.get(index).getId() + ") at index " +  index + "...");
            Conversation c = trackedConversations.get(index);
            trackedConversations.remove(index);
            System.out.println("=============Conversation manager: Conversation (cid = " + c.getId() + ") at index " +  index + " dergistered");
        }
        else
        {
            System.out.println("=============Conversation manager: Cannot deregister as Conversation at index " + index + " is not being tracked");
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
    
    public boolean isConversationRegistered(Conversation conversation)
    {
        return trackedConversations.contains(conversation);
    }
       
}
