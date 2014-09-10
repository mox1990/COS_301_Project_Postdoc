/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.DBDAO.AuditLogJpaController;
import com.softserve.DBDAO.NotificationJpaController;
import com.softserve.ejb.NotificationService;

/**
 *
 * @author kgothatso
 */
public class NotificationServiceMockUnit extends NotificationService {
    private NotificationJpaController nDAO = null;
    
    public void setNDAO(NotificationJpaController nDAO)
    {
        this.nDAO = nDAO;
    }
}
