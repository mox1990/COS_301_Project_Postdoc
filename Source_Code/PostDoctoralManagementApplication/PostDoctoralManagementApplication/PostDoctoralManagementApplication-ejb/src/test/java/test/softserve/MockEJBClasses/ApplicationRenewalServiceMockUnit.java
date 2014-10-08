/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.softserve.MockEJBClasses;

import com.softserve.auxillary.factories.DAOFactory;
import com.softserve.ejb.applicationservices.ApplicationRenewalService;
import com.softserve.ejb.applicationservices.CVManagementServiceLocal;
import com.softserve.ejb.applicationservices.ProgressReportManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.auxillary.util.ApplicationServicesUtil;
import com.softserve.auxillary.factories.DBEntitiesFactory;
import com.softserve.auxillary.transactioncontrollers.TransactionController;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;

/**
 *
 * @author kgothatso
 */
public class ApplicationRenewalServiceMockUnit extends ApplicationRenewalService {
    private NotificationServiceLocal notificationServiceLocal;
    private CVManagementServiceLocal cVManagementServiceLocal;
    private ProgressReportManagementServiceLocal progressReportManagementServiceLocal;
    private DAOFactory dAOFactory;
    private TransactionController transactionController;
    private DBEntitiesFactory dBEntitiesFactory;
    private ApplicationServicesUtil applicationServicesUtil;
    private GregorianCalendar gregorianCalendar;
    private EntityManager em;

    public void setNotificationServiceLocal(NotificationServiceLocal notificationServiceLocal) {
        this.notificationServiceLocal = notificationServiceLocal;
    }

    public void setcVManagementServiceLocal(CVManagementServiceLocal cVManagementServiceLocal) {
        this.cVManagementServiceLocal = cVManagementServiceLocal;
    }

    public void setProgressReportManagementServiceLocal(ProgressReportManagementServiceLocal progressReportManagementServiceLocal) {
        this.progressReportManagementServiceLocal = progressReportManagementServiceLocal;
    }

    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }

    public void setTransactionController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    public void setdBEntitiesFactory(DBEntitiesFactory dBEntitiesFactory) {
        this.dBEntitiesFactory = dBEntitiesFactory;
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
    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return notificationServiceLocal;
    }
    
    @Override
    protected CVManagementServiceLocal getCVManagementServiceEJB()
    {
        return cVManagementServiceLocal;
    }
    
    @Override
    protected ProgressReportManagementServiceLocal getProgressReportManagementServiceEJB()
    {
        return progressReportManagementServiceLocal;
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
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return dBEntitiesFactory;
    }
        
    @Override
    protected ApplicationServicesUtil getApplicationServicesUtil(EntityManager em)
    {
        return applicationServicesUtil;
    }
    
    @Override
    protected GregorianCalendar getGregorianCalendarUTIL()
    {
        return gregorianCalendar;
    }
    
    @Override
    protected EntityManager createEntityManager()
    {
        return em;
    }
}
