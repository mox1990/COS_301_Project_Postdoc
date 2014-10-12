/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.MockEJBClasses;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.ejb.nonapplicationservices.NeuralNetworkManagementServices;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NeuralNetworkManagementServiceMockUnit extends NeuralNetworkManagementServices {
    private DBEntitiesFactory dBEntities;
    private DAOFactory dAOFactory;
    private TransactionController transactionController;
    private EntityManager entityManager;
    private GregorianCalendar gCal;

    public void setgCal(GregorianCalendar gCal) {
        this.gCal = gCal;
    }

    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }

    public void setTransactionController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setdBEntities(DBEntitiesFactory dBEntities) {
        this.dBEntities = dBEntities;
    }
    @Override
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return dBEntities;
    }
    
    @Override    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return dAOFactory;
    }
    
    @Override
    protected TransactionController getTransactionController()
    {
        return transactionController;
    }
    
    @Override
    public EntityManager createEntityManager()
    {
        return entityManager;
    }
    
    @Override
    protected GregorianCalendar getGregorianCalendar()
    {
        return gCal;
    }
}
