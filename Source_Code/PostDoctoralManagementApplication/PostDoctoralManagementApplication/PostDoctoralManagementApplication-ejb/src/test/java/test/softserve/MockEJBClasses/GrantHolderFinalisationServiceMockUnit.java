/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.persistence.DBDAO.AmmendRequestJpaController;
import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.persistence.DBDAO.CvJpaController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.applicationservices.CVManagementService;
import com.softserve.ejb.applicationservices.GrantHolderFinalisationService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import com.softserve.auxiliary.transactioncontrollers.TransactionController;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class GrantHolderFinalisationServiceMockUnit extends GrantHolderFinalisationService {
    private DBEntitiesFactory dBEntitities;
    private NotificationService nEJB;
    private CVManagementService cVEJB;
    private ApplicationServicesUtil aSEJB;
    private GregorianCalendar gCal;
    private DAOFactory dAOFactory;
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    private TransactionController transactionController;
    private EntityManager entityManager;

    public void setTransactionController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }

    public void setUserAccountManagementServiceLocal(UserAccountManagementServiceLocal userAccountManagementServiceLocal) {
        this.userAccountManagementServiceLocal = userAccountManagementServiceLocal;
    }
    
    public void setgCal(GregorianCalendar gCal) {
        this.gCal = gCal;
    }

    public void setdBEntitities(DBEntitiesFactory dBEntitities) {
        this.dBEntitities = dBEntitities;
    }

    public void setnEJB(NotificationService nEJB) {
        this.nEJB = nEJB;
    }

    public void setcVEJB(CVManagementService cVEJB) {
        this.cVEJB = cVEJB;
    }

    public void setaSEJB(ApplicationServicesUtil aSEJB) {
        this.aSEJB = aSEJB;
    }
    
    @Override    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return dAOFactory;
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
    protected CVManagementService getCVManagementServiceEJB()
    {
        return cVEJB;
    }
    
    @Override
    protected GregorianCalendar getGregorianCalendar()
    {
        return gCal;
    }
    
    @Override
    protected UserAccountManagementServiceLocal getUserAccountManagementServiceEJB()
    {
        return userAccountManagementServiceLocal;
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

