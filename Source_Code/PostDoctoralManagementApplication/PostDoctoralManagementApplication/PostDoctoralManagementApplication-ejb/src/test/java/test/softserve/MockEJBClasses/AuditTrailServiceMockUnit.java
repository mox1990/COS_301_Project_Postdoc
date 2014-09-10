/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.DBDAO.AuditLogJpaController;
import com.softserve.DBDAO.DAOFactory;
import com.softserve.ejb.AuditTrailService;
import com.softserve.transactioncontrollers.TransactionController;
import javax.persistence.EntityManager;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class AuditTrailServiceMockUnit extends AuditTrailService {
    
    private AuditLogJpaController aDAO = null;
    
    public void setADAO(AuditLogJpaController aDAO)
    {
        this.aDAO = aDAO;
    }
    
    @Override    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
    }   
    
    
}
