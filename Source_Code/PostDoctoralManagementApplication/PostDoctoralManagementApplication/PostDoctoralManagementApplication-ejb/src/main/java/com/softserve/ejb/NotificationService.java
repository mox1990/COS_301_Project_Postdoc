/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This EJB handles the notification services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
public class NotificationService implements NotificationServiceLocal { // TODO: Decide on the local, ermote and what not
    @PersistenceContext(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManager em;
    
    public void sendNotification(NotificationRequest nRequest) throws MessagingException
    {
        switch(nRequest.nType)
        {
            case NotificationRequest.EMAIL:
                sendEmail(nRequest.message, nRequest.subject, nRequest.recipients, nRequest.sender);
                break;
            case NotificationRequest.SYSTEM:
                for(Person recipient: nRequest.recipients)
                {
                    sendSystemNotification(nRequest.message, nRequest.subject, recipient, nRequest.sender);
                }
                break;
            default:
                throw new MessagingException("Cannot construct such a notification.");
        }
    }
    
    public void sendEmail(String mess, String subject, List<Person> recipients, Person sender) throws MessagingException
    {
        final String username = "iterativeKak@gmail.com";
        final String password = "********";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() 
          {
            protected PasswordAuthentication getPasswordAuthentication() 
            {
                    return new PasswordAuthentication(username, password);
            }
          });

        
        int rSize = recipients.size();
        Address[] addresses = new Address[rSize];
        for(int i = 0; i < rSize; i++)
        {
            addresses[i] = new InternetAddress(recipients.get(i).getEmail());
        }

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender.getEmail()));
        message.setSubject(subject);
        message.setRecipients(Message.RecipientType.TO, addresses);
        message.setText(mess);

        Transport.send(message);
        
    }
    
    public Notification sendSystemNotification(String message, String subject, Person recipient, Person sender)
    {
        Notification notification = new Notification();
        
        notification.setRecieverID(recipient);
        notification.setSenderID(sender);
        notification.setMessage(message);
        
        em.persist(notification);
        
        return notification;
    }
    
    public List<Notification> findAll()
    {
        return em.createNamedQuery("Notification.findAll", Notification.class).getResultList();
    }
    
    public List<Notification> findByNotificationID(Long nID)
    {
        return em.createNamedQuery("Notification.findByNotificationID", Notification.class).setParameter("entryID", nID).getResultList();
    }
    
    public List<Notification> findBySubject(String subject)
    {
        return em.createNamedQuery("Notification.findBySubject", Notification.class).setParameter("subject", subject).getResultList();
    }
    
    public List<Notification> findByTimestamp(Timestamp tStamp)
    {
        return em.createNamedQuery("Notification.findByTimestamp", Notification.class).setParameter("timestamp", tStamp).getResultList();
    }
    
    public List<Notification> findBetweenRange(Timestamp start, Timestamp end)
    {
        // TODO: create the new namedQuery
        return em.createNamedQuery("Notification.findByTimestamp", Notification.class).setParameter("timestamp", start).getResultList();
    }
}
