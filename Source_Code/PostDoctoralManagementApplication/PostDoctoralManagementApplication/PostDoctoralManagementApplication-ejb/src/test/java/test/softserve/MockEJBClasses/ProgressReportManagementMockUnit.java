/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.auxillary.util.ApplicationServicesUtil;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.applicationservices.ProgressReportManagementService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxillary.factories.DBEntitiesFactory;

/**
 *
 * @author User
 */
public class ProgressReportManagementMockUnit extends ProgressReportManagementService
{
    private UserGateway uEJB;
    private PersonJpaController pDAO;
    private NotificationService nEJB;
    private AuditTrailService aTEJB;
    private DBEntitiesFactory dBEntitities;
    private ApplicationServicesUtil aSEJB;
    private ApplicationJpaController aDAO;
       
    public void setaDAO(ApplicationJpaController aDAO)
    {
        this.aDAO = aDAO;
    }
    
    public void setaSEJB(ApplicationServicesUtil aSEJB) 
    {
        this.aSEJB = aSEJB;
    }
    
    public void setpDAO(PersonJpaController pDAO)
    {
        this.pDAO = pDAO;
    }

    public void setdBEntitities(DBEntitiesFactory dBEntitities)
    {
        this.dBEntitities = dBEntitities;
    }
    
    public void setuEJB(UserGateway uEJB)
    {
        this.uEJB = uEJB;
    }
    
     public void setnEJB(NotificationService nEJB)
    {
        this.nEJB = nEJB;
    }
     
     public void setaTEJB(AuditTrailService aTEJB)
     {
        this.aTEJB = aTEJB;
     }
   
    protected ApplicationJpaController getApplicationDAO()
    {
        return aDAO;
    }
    
    
    protected PersonJpaController getPersonDAO()
    {
        return pDAO;
    }
    
    @Override
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return dBEntitities;
    }

    
    protected NotificationService getNotificationServiceEJB()
    {
        return nEJB;
    }
    
   
    protected ApplicationServicesUtil getApplicationServicesUTIL()
    {
        return aSEJB;
    }
}
