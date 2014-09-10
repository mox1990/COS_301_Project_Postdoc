
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.Notification;
import com.softserve.DBEntities.Person;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.Session;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Local;
import javax.mail.MessagingException;

/**
 * This EJB handles the audit trail services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface NotificationServiceLocal {
    public void sendBatchNotifications(com.softserve.system.Session session, List<Notification> notifications, boolean sendEmail) throws Exception;
    public void sendNotification(com.softserve.system.Session session, Notification notification, boolean sendEmail) throws Exception;
    public void sendOnlyEmail(com.softserve.system.Session session, Notification notification);
    public List<Notification> getAllNotificationsForPerson(Session session, Person person) throws AuthenticationException, Exception;
    public List<Notification> getAllNotificationsFromPerson(com.softserve.system.Session session, Person person) throws AuthenticationException, Exception;
    

}
