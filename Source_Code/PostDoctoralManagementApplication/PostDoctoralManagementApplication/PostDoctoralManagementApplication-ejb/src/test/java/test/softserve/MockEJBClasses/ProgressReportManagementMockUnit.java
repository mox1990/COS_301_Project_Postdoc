/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.ProgressReportManagementService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;

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
    private ApplicationServices aSEJB;
    private Application aDAO;
       
    public void setaDAO(Application aDAO)
    {
        this.aDAO = aDAO;
    }
    
    public void setaSEJB(ApplicationServices aSEJB) 
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
   
    protected Application getApplicationDAO()
    {
        return aDAO;
    }
    
     @Override
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return aTEJB;
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
    
    @Override
    protected UserGateway getUserGatewayServiceEJB()
    {
        return uEJB;
    }
    
    protected NotificationService getNotificationServiceEJB()
    {
        return nEJB;
    }
    
   
    protected ApplicationServices getApplicationServicesUTIL()
    {
        return aSEJB;
    }
}
