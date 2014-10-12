/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.RecommendationReportJpaController;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.applicationservices.HODRecommendationServices;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class HODRecommendationServicesMockUnit extends HODRecommendationServices {
    private DBEntitiesFactory dBEntities;
    private NotificationService nEJB;
    private ApplicationServicesUtil aSEJB;
    private GregorianCalendar gCal;
    private DAOFactory dAOFactory;
    private TransactionController transactionController;
    private EntityManager entityManager;

    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }

    public void setTransactionController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setgCal(GregorianCalendar gCal) {
        this.gCal = gCal;
    }

    public void setdBEntities(DBEntitiesFactory dBEntities) {
        this.dBEntities = dBEntities;
    }

    public void setnEJB(NotificationService nEJB) {
        this.nEJB = nEJB;
    }

    public void setaSEJB(ApplicationServicesUtil aSEJB) {
        this.aSEJB = aSEJB;
    }
    
    @Override
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return dBEntities;
    }
    
    @Override
    protected NotificationService getNotificationServiceEJB()
    {
        return nEJB;
    }
    
    @Override    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return dAOFactory;
    }
     
    @Override
    protected GregorianCalendar getGregorianCalendar()
    {
        return gCal;
    }
    
    @Override
    protected ApplicationServicesUtil getApplicationServicesUTIL(EntityManager em)
    {
        return aSEJB;
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
}
