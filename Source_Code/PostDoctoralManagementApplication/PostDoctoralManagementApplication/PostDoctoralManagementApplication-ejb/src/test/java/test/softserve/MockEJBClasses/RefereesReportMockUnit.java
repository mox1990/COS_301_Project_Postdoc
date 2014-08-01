/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.RefereeReportService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import java.util.GregorianCalendar;

/**
 *
 * @author User
 */
public class RefereesReportMockUnit extends RefereeReportService
{
    private UserGateway uEJB;
    private ApplicationJpaController aDAO;
    private PersonJpaController pDAO;
    private GregorianCalendar cal = null;
    private NotificationService nEJB;
    private AuditTrailService aTEJB;
    private DBEntitiesFactory dBEntitities;
    private ApplicationServices aSEJB;
    
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
   
    protected PersonJpaController getPersonDAO()
    {
        return pDAO;
    }
    
    @Override
    protected ApplicationJpaController getApplicationDAO()
    {
        return aDAO;
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
    
    @Override
    protected NotificationService getNotificationServiceEJB()
    {
        return nEJB;
    }
    
    @Override
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return aTEJB;
    }
    
    @Override
    protected ApplicationServices getApplicationServicesUTIL()
    {
        return aSEJB;
    }

    public void setaDAO(ApplicationJpaController mockApplicationJpaController)
    {
       this.setaDAO(mockApplicationJpaController);
    }

    public void setdBEntities(DBEntitiesFactory mockDBEntitiesFactory) 
    {
        this.setdBEntitities(mockDBEntitiesFactory);
    }
}
