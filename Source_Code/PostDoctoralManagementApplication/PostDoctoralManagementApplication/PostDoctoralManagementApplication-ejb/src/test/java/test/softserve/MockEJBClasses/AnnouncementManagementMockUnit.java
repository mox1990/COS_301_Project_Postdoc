/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.MockEJBClasses;

import com.softserve.auxillary.factories.DAOFactory;
import com.softserve.ejb.nonapplicationservices.AnnouncementManagementService;
import com.softserve.auxillary.factories.DBEntitiesFactory;
import com.softserve.auxillary.transactioncontrollers.TransactionController;
import javax.persistence.EntityManager;

/**
 *
 * @author kgothatso
 */
public class AnnouncementManagementMockUnit extends AnnouncementManagementService {
    DAOFactory dAOFactory;
    DBEntitiesFactory dBEntitiesFactory;
    TransactionController transactionController;
    EntityManager em;

    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }

    public void setdBEntitiesFactory(DBEntitiesFactory dBEntitiesFactory) {
        this.dBEntitiesFactory = dBEntitiesFactory;
    }

    public void setTransactionController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    @Override
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return dAOFactory;
    }
    
    @Override
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return dBEntitiesFactory;
    }
    
    @Override
    protected TransactionController getTransactionController()
    {
        return transactionController;
    }
    
    @Override
    protected EntityManager createEntityManager()
    {
        return em;
    }
}