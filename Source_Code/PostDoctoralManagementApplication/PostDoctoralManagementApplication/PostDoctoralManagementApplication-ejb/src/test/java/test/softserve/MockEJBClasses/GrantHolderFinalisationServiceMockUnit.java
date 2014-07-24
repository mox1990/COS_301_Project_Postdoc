/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.CVManagementService;
import com.softserve.ejb.GrantHolderFinalisationService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;

/**
 *
 * @author kgothatso
 */
public class GrantHolderFinalisationServiceMockUnit extends GrantHolderFinalisationService {
    private PersonJpaController pDAO;
    private CvJpaController cVDAO;
    private ApplicationJpaController aDAO;
    private DBEntitiesFactory dBEntitities;
    private UserGateway uEJB;
    private NotificationService nEJB;
    private AuditTrailService aTEJB;
    private CVManagementService cVEJB;
    private ApplicationServices aSEJB;

    public void setpDAO(PersonJpaController pDAO) {
        this.pDAO = pDAO;
    }

    public void setcVDAO(CvJpaController cVDAO) {
        this.cVDAO = cVDAO;
    }

    public void setaDAO(ApplicationJpaController aDAO) {
        this.aDAO = aDAO;
    }

    public void setdBEntitities(DBEntitiesFactory dBEntitities) {
        this.dBEntitities = dBEntitities;
    }

    public void setuEJB(UserGateway uEJB) {
        this.uEJB = uEJB;
    }

    public void setnEJB(NotificationService nEJB) {
        this.nEJB = nEJB;
    }

    public void setaTEJB(AuditTrailService aTEJB) {
        this.aTEJB = aTEJB;
    }

    public void setcVEJB(CVManagementService cVEJB) {
        this.cVEJB = cVEJB;
    }

    public void setaSEJB(ApplicationServices aSEJB) {
        this.aSEJB = aSEJB;
    }
    
    @Override
    protected PersonJpaController getPersonDAO()
    {
        return pDAO;
    }
    
    @Override
    protected CvJpaController getCVDAO()
    {
        return cVDAO;
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
    protected CVManagementService getCVManagementServiceEJB()
    {
        return cVEJB;
    }
    
    @Override
    protected ApplicationServices getApplicationServicesUTIL()
    {
        return aSEJB;
    }
}
