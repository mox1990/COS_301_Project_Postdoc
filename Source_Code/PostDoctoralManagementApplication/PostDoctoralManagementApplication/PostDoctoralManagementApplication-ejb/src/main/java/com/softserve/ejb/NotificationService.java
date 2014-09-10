/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.DAOFactory;
import com.softserve.DBDAO.NotificationJpaController;
import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.annotations.AuditableMethod;
import com.softserve.annotations.SecuredMethod;
import com.softserve.interceptors.AuditTrailInterceptor;
import com.softserve.interceptors.AuthenticationInterceptor;
import com.softserve.transactioncontrollers.TransactionController;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
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
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.constraints.Future;

/**
 * This EJB handles the notification services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Interceptors({AuthenticationInterceptor.class, AuditTrailInterceptor.class})
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class NotificationService implements NotificationServiceLocal { // TODO: Decide on the local, ermote and what not <- should be local
    
    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.WORKING_DB_PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    public NotificationService() {
    }
    
    public NotificationService(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }

    protected TransactionController getTransactionController()
    {
        return new TransactionController(emf);
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
    @Asynchronous
    public void sendBatchNotifications(com.softserve.system.Session session, List<Notification> notifications, boolean sendEmail) throws Exception
    {
        for(Notification n : notifications)
        {
            sendNotification(session, n, sendEmail);
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {})
    @Override
    @Asynchronous
    public void sendNotification(com.softserve.system.Session session, Notification notification, boolean sendEmail)
    {   
        try 
        {
            TransactionController transactionController = getTransactionController();
            transactionController.StartTransaction();        
            try
            {
                DAOFactory dAOFactory = transactionController.getDAOFactoryForTransaction();
                
                EntityManager em = emf.createEntityManager();
            
                NotificationJpaController notificationJpaController = dAOFactory.createNotificationDAO();

                //Set as current time
                notification.setTimestamp(new Timestamp(new Date().getTime()));

                //Send system notification
                

                if(sendEmail)
                {
                    try
                    {
                        sendEmail(notification);
                        notification.setEmailStatus(com.softserve.constants.PersistenceConstants.NOTIFICATION_EMAIL_STATUS_SENT);
                    }
                    catch(MessagingException ex)
                    {
                        notification.setEmailStatus(com.softserve.constants.PersistenceConstants.NOTIFICATION_EMAIL_STATUS_QUEUED);
                    }
                }
                else
                {
                    notification.setEmailStatus(com.softserve.constants.PersistenceConstants.NOTIFICATION_EMAIL_STATUS_DISABLED);
                }

                notificationJpaController.create(notification);

                transactionController.CommitTransaction();
            }
            catch(Exception ex)
            {
                transactionController.RollbackTransaction();
                throw ex;
            }
            finally
            {
                transactionController.CloseEntityManagerForTransaction();
            }

                        
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SecuredMethod(AllowedSecurityRoles = {})
    @Override
    @Asynchronous
    public void sendOnlyEmail(com.softserve.system.Session session, Notification notification)
    {        
        try
        {
            sendEmail(notification);
        }
        catch(Exception ex)
        {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void sendEmail(Notification notification) throws MessagingException
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
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR},ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public List<Notification> getAllNotificationsForPerson(com.softserve.system.Session session, Person person) throws AuthenticationException, Exception
    {
        EntityManager em = emf.createEntityManager();

        try
        {        
            return getDAOFactory(em).createNotificationDAO().findAllNotificationsWhosRecieverIs(person);
        }
        finally
        {
            em.close();
        }        
    }
    
    @SecuredMethod(AllowedSecurityRoles = {com.softserve.constants.PersistenceConstants.SECURITY_ROLE_ID_SYSTEM_ADMINISTRATOR},ownerAuthentication = true, ownerParameterIndex = 1)
    @AuditableMethod
    @Override
    public List<Notification> getAllNotificationsFromPerson(com.softserve.system.Session session, Person person) throws AuthenticationException, Exception
    {

        EntityManager em = emf.createEntityManager();

        try
        {        
            return getDAOFactory(em).createNotificationDAO().findAllNotificationsWhosSenderIs(person);
        }
        finally
        {
            em.close();
        }        
    }
    
    @Schedule(second = "*/10")
    @Asynchronous
    public void sendAllEmailsOfQueuedNotifications()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            System.out.println("==================================SENDING QUEDED EMAILS");
            try
            {
                List<Notification> notifications = getDAOFactory(em).createNotificationDAO().findAllQueuedNotifications();
                for(Notification notification : notifications)
                {
                    sendEmail(notification);
                }
            }
            finally
            {
                em.close();
            }
        }
        catch(Exception ex)
        {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    
    
}
