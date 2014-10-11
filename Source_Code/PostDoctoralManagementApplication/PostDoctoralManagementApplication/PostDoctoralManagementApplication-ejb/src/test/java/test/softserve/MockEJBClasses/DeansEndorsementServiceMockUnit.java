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
import com.softserve.ejb.applicationservices.DeansEndorsementService;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.EndorsementJpaController;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;

/**
 *
 * @author kgothatso
 */
public class DeansEndorsementServiceMockUnit extends DeansEndorsementService {
    private DBEntitiesFactory dBEntities;
    private NotificationService nEJB;
    private ApplicationServicesUtil aSEJB;
    private TransactionController transactionController;
    private DAOFactory dAOFactory;
    private GregorianCalendar gCal;
    private EntityManager em;

    public void setdBEntities(DBEntitiesFactory dBEntities) {
        this.dBEntities = dBEntities;
    }

    public void setnEJB(NotificationService nEJB) {
        this.nEJB = nEJB;
    }

    public void setaSEJB(ApplicationServicesUtil aSEJB) {
        this.aSEJB = aSEJB;
    }

    public void setTransactionController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }

    public void setgCal(GregorianCalendar gCal) {
        this.gCal = gCal;
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
        return dBEntities;
    }
    
    @Override
    protected NotificationService getNotificationServiceEJB()
    {
        return nEJB;
    }    
    
    @Override
    protected TransactionController getTransactionController()
    {
        return transactionController;
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
    
    protected EntityManager createEntityManager()
    {
        return em;
    }
}
