/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import javax.ejb.Local;
import javax.mail.MessagingException;

/**
 * This EJB handles the audit trail services
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface NotificationServiceLocal {
    public void sendNotification(NotificationRequest nRequest) throws MessagingException;
}
