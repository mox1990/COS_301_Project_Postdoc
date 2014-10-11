/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.persistence.DBDAO.AuditLogJpaController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.NotificationJpaController;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import javax.persistence.EntityManager;

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
    
   @Override    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    } 
}
