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
import com.softserve.ejb.applicationservices.ApplicationRenewalServiceLocal;
import com.softserve.ejb.applicationservices.MeetingManagementServiceLocal;
import com.softserve.ejb.applicationservices.ProgressReportManagementServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.NotificationServiceLocal;
import com.softserve.ejb.nonapplicationservices.NotifierServices;
import com.softserve.ejb.nonapplicationservices.UserAccountManagementServiceLocal;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class NotifierServicesMockUnit extends NotifierServices {
    private DBEntitiesFactory dBEntities;
    private NotificationServiceLocal nEJB;
    private ProgressReportManagementServiceLocal progressReportManagementServiceLocal;
    private ApplicationRenewalServiceLocal applicationRenewalServiceLocal;
    private MeetingManagementServiceLocal meetingManagementServiceLocal;
    private UserAccountManagementServiceLocal userAccountManagementServiceLocal;
    private ApplicationServicesUtil aSEJB;
    private DAOFactory dAOFactory;
    private EntityManager entityManager;

    public void setdAOFactory(DAOFactory dAOFactory) {
        this.dAOFactory = dAOFactory;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setdBEntities(DBEntitiesFactory dBEntities) {
        this.dBEntities = dBEntities;
    }

    public void setnEJB(NotificationServiceLocal nEJB) {
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
    protected NotificationServiceLocal getNotificationServiceEJB()
    {
        return nEJB;
    }
    
    @Override    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return dAOFactory;
    }
    
    @Override
    protected ApplicationServicesUtil getApplicationServicesUTIL(EntityManager em)
    {
        return aSEJB;
    }
    
    @Override
    public EntityManager createEntityManager()
    {
        return entityManager;
    }
    
    @Override
    protected ProgressReportManagementServiceLocal getProgressReportManagementServiceEJB()
    {
        return progressReportManagementServiceLocal;
    }

    @Override
    protected ApplicationRenewalServiceLocal getApplicationRenewalServiceEJB() 
    {
        return applicationRenewalServiceLocal;
    }

    @Override
    protected MeetingManagementServiceLocal getMeetingManagementServiceEJB() 
    {
        return meetingManagementServiceLocal;
    }

    @Override
    protected UserAccountManagementServiceLocal getUserAccountManagementServiceEJB() 
    {
        return userAccountManagementServiceLocal;
    }
    
}
