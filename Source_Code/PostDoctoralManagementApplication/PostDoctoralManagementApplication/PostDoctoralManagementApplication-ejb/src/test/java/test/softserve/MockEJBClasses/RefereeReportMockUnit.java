/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.applicationservices.RefereeReportService;
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
public class RefereeReportMockUnit extends RefereeReportService
{
    private GregorianCalendar cal;
    private NotificationService nEJB;
    private DBEntitiesFactory dBEntitities;
    private ApplicationServicesUtil aSEJB;
    private DAOFactory dAOFactory;
    private TransactionController transactionController;
    private EntityManager entityManager;

    public void setCal(GregorianCalendar cal) {
        this.cal = cal;
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
    
    public void setaSEJB(ApplicationServicesUtil aSEJB)
    {
        this.aSEJB = aSEJB;
    }

    public void setdBEntitities(DBEntitiesFactory dBEntitities)
    {
        this.dBEntitities = dBEntitities;
    }

    public void setnEJB(NotificationService nEJB)
    {
        this.nEJB = nEJB;
    }
    
    @Override
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return dBEntitities;
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
        return cal;
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
    protected EntityManager createEntityManager()
    {
        return entityManager;
    }
}
