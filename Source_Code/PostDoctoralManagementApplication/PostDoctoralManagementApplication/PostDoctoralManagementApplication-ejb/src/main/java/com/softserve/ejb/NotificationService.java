/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.NotificationJpaController;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.constraints.Future;

/**
 * This EJB handles the notification services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class NotificationService implements NotificationServiceLocal { // TODO: Decide on the local, ermote and what not <- should be local
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    public NotificationService() {
    }
    
    public NotificationService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    protected NotificationJpaController getNotificationDAO()
    {
        return new NotificationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    /**
     *
     * @return
     */
    protected UserGateway getUserGatewayServiceEJB()
    {
        return new UserGateway(emf);
    }
    
    
    protected Properties getPropertiesJMAIL()
    {
        return new Properties();
    }
    
    protected Session getSessionJMAIL(Properties properties, javax.mail.Authenticator authenticator)
    {
        return Session.getInstance(properties, authenticator);
    }
    
    protected javax.mail.Authenticator getAuthenticatorJMAIL()
    {
        javax.mail.Authenticator authenticator = new javax.mail.Authenticator()
                                                {
                                                  @Override
                                                  protected PasswordAuthentication getPasswordAuthentication() 
                                                  {
                                                          return new PasswordAuthentication(com.softserve.constants.SystemConstants.MAIL_USERNAME, com.softserve.constants.SystemConstants.MAIL_PASSWORD);
                                                  }
                                                };
        return authenticator;        
    }
    
    protected InternetAddress getInternetAddressJMAIL(String email) throws AddressException
    {
        return new InternetAddress(email);
    }
    
    protected MimeMessage getMimeMessageJMAIL(Session session)
    {
        return new MimeMessage(session);
    }
    
    protected void sendMessageJMAIL(Message message) throws MessagingException
    {
        Transport.send(message);
    }
    
    
    @Override
    public void sendBatchNotifications(List<Notification> notifications, boolean sendEmail) throws Exception
    {
        for(Notification n : notifications)
        {
            sendNotification(n, sendEmail);
        }
    }
    
    @Override
    @Asynchronous
    public void sendNotification(Notification notification, boolean sendEmail)
    {   
        try 
        {
            NotificationJpaController notificationJpaController = getNotificationDAO();
            
            //Set as current time
            notification.setTimestamp(new Timestamp(new Date().getTime()));
            
            //Send system notification
            notificationJpaController.create(notification);
            
            if(sendEmail)
            {
                try
                {
                    sendEmail(notification);
                    notification.setEmailStatus(com.softserve.constants.PersistenceConstants.NOTIFICATION_EMAIL_STATUS_SENT);
                }
                catch(MessagingException ex)
                {
                    notification.setEmailStatus(com.softserve.constants.PersistenceConstants.NOTIFICATION_EMAIL_STATUS_SENDING);
                }
            }
            else
            {
                notification.setEmailStatus(com.softserve.constants.PersistenceConstants.NOTIFICATION_EMAIL_STATUS_DISABLED);
            }
            
            notificationJpaController.edit(notification);            
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    @Asynchronous
    public void sendOnlyEmail(Notification notification)
    {        
        try
        {
            sendEmail(notification);
        }
        catch(MessagingException ex)
        {

        }
    }    
    
    @Asynchronous
    public void sendEmail(Notification notification) throws MessagingException
    {
        Properties props = getPropertiesJMAIL();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = getSessionJMAIL(props, getAuthenticatorJMAIL());
        
        String subjectText = "UP Postdoc Notification: ";
        String headerText = "Dear " + notification.getReciever().getCompleteName() + "\n\n";
        String footerText = "\n\n" + "This message is an email sent from the UP Post Doctoral Management System. Please do not reply to this email.";
        
        Address[] addresses = new Address[1];
        addresses[0] = getInternetAddressJMAIL(com.softserve.constants.SystemConstants.MAIL_USERNAME);

        Message message = getMimeMessageJMAIL(session);
        message.setFrom(getInternetAddressJMAIL(com.softserve.constants.SystemConstants.MAIL_USERNAME));
        message.setSubject(subjectText + notification.getSubject());
        message.setRecipients(Message.RecipientType.TO, addresses);
        message.setText(headerText + notification.getMessage() + footerText);
        sendMessageJMAIL(message);     

    }
    
    @Override
    public List<Notification> getAllNotificationsForPerson(com.softserve.system.Session session, Person person) throws AuthenticationException, Exception
    {
        UserGateway userGateway = getUserGatewayServiceEJB();
        try
        {
            //Authenticate user ownership of account
            userGateway.authenticateUserAsOwner(session, person);
        } 
        catch(AuthenticationException ex)
        {
            //Authenticate user privliges
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            userGateway.authenticateUser(session, roles);
        } 
        
        NotificationJpaController notificationJpaController = getNotificationDAO();
        
        return notificationJpaController.findAllNotificationsWhosRecieverIs(person);
    }
    
    @Override
    public List<Notification> getAllNotificationsFromPerson(com.softserve.system.Session session, Person person) throws AuthenticationException, Exception
    {
        UserGateway userGateway = getUserGatewayServiceEJB();
        try
        {
            //Authenticate user ownership of account
            userGateway.authenticateUserAsOwner(session, person);
        } 
        catch(AuthenticationException ex)
        {
            //Authenticate user privliges
            ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
            roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
            userGateway.authenticateUser(session, roles);
        } 
        
        NotificationJpaController notificationJpaController = getNotificationDAO();
        
        return notificationJpaController.findAllNotificationsWhosSenderIs(person);
    }
    
    //Note this function is not percisly to the spec. It should be that a notification sends a notification plus an email or not
    //Also the notification entity renders the notification request class a bit useless. 
    /*@Override
    public void sendNotification(NotificationRequest nRequest) throws Exception
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
    }*/
    
    
    
    //This is unessary modularity
    /*
    private void sendSystemNotification(Notification notification) throws Exception
    {           
        getNotificationDAO().create(notification);
        
    }*/
    
    //Use the notification object it will work better and plus it already contains the data
    //The service shouldn't provide multi recipient ids since one notification represents only one email
    /*
    @Override
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
            @Override
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
        
    }*/
    
    
    
    //Not part of specification. Remember this EJB is to carry out the business logic not create the entities required for the business logic thats the managed beans job
    /*
    @Override
    public List<Notification> sendSystemNotification(String message, String subject, List<Person> recipients, Person sender) throws Exception
    {
        List<Notification> notifications = new ArrayList<>();
        
        for(Person recipient: recipients)
        {
            notifications.add(sendSystemNotification(message, subject, recipient, sender));
        }
        
        return notifications;
    }*/
    
   
    
    
    @Override
    public List<Notification> findAll()
    {
        return emf.createEntityManager().createNamedQuery("Notification.findAll", Notification.class).getResultList();
    }
    
    @Override
    public List<Notification> findByNotificationID(Long nID)
    {
        return emf.createEntityManager().createNamedQuery("Notification.findByNotificationID", Notification.class).setParameter("entryID", nID).getResultList();
    }
    
    @Override
    public List<Notification> findBySubject(String subject)
    {
        return emf.createEntityManager().createNamedQuery("Notification.findBySubject", Notification.class).setParameter("subject", subject).getResultList();
    }
    
    @Override
    public List<Notification> findByTimestamp(Timestamp tStamp)
    {
        return emf.createEntityManager().createNamedQuery("Notification.findByTimestamp", Notification.class).setParameter("timestamp", tStamp).getResultList();
    }
    
    @Override
    public List<Notification> findBetweenRange(Timestamp start, Timestamp end)
    {
        return emf.createEntityManager().createNamedQuery("Notification.findBetweenRange", Notification.class).setParameter("start", start).setParameter("end", start).getResultList();
    }
}
