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
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;

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
    private ApplicationServicesUtil aSEJB;
    
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
    protected NotificationService getNotificationServiceEJB()
    {
        return nEJB;
    }
    
    @Override    
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
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
