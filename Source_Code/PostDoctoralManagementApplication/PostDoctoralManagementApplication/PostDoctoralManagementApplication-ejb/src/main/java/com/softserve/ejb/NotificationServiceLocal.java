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
import javax.ejb.Local;
import javax.mail.MessagingException;

/**
 * This EJB handles the audit trail services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface NotificationServiceLocal {
    public void sendBatchNotifications(List<Notification> notifications, boolean sendEmail) throws Exception;
    public void sendNotification(Notification notification, boolean sendEmail) throws Exception; // TODO: Consider deleting this method...
    //public List<Notification> sendSystemNotification(String message, String subject, List<Person> recipients, Person sender) throws Exception;
    //public Notification sendSystemNotification(String message, String subject, Person recipient, Person sender) throws Exception;
    //public void sendEmail(String mess, String subject, List<Person> recipients, Person sender) throws MessagingException;
    public List<Notification> findAll();
    public List<Notification> findByNotificationID(Long nID);
    public List<Notification> findBySubject(String subject);
    public List<Notification> findByTimestamp(Timestamp tStamp);
    public List<Notification> findBetweenRange(Timestamp start, Timestamp end);
}
