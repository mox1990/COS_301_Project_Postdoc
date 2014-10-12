/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.MockEJBClasses;

import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.ejb.applicationservices.ForwardAndRewindServices;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class ForwardAndRewindServicesMockUnit extends ForwardAndRewindServices {
    private DBEntitiesFactory dBEntitiesFactory;
    private DAOFactory dAOFactory;
    private TransactionController transactionController;
    private ApplicationServicesUtil applicationServicesUtil;
    private GregorianCalendar gregorianCalendar;
    private EntityManager em;    

    public void setdBEntitiesFactory(DBEntitiesFactory dBEntitiesFactory) {
        this.dBEntitiesFactory = dBEntitiesFactory;
    }

    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }

    public void setTransactionController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    public void setApplicationServicesUtil(ApplicationServicesUtil applicationServicesUtil) {
        this.applicationServicesUtil = applicationServicesUtil;
    }

    public void setGregorianCalendar(GregorianCalendar gregorianCalendar) {
        this.gregorianCalendar = gregorianCalendar;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    @Override
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return dBEntitiesFactory;
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
    protected ApplicationServicesUtil getApplicationServicesUTIL(EntityManager em)
    {
        return applicationServicesUtil;
    }
    
    @Override
    protected GregorianCalendar getGregorianCalendar()
    {
        return gregorianCalendar;
    }
    
    @Override
    protected EntityManager createEntityManager()
    {
        return em;
    }
}
