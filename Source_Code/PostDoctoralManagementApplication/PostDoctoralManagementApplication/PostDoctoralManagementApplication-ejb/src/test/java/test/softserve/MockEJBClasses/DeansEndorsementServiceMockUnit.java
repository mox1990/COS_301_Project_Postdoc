/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.system.ApplicationServicesUtil;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.EndorsementJpaController;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.DeansEndorsementService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;

/**
 *
 * @author kgothatso
 */
public class DeansEndorsementServiceMockUnit extends DeansEndorsementService {
    private ApplicationJpaController aDAO;
    private EndorsementJpaController eDAO;
    private DBEntitiesFactory dBEntities;
    private UserGateway uEJB;
    private NotificationService nEJB;
    private AuditTrailService aTEJB;
    private ApplicationServicesUtil aSEJB;

    public void setaDAO(ApplicationJpaController aDAO) {
        this.aDAO = aDAO;
    }

    public void seteDAO(EndorsementJpaController eDAO) {
        this.eDAO = eDAO;
    }

    public void setdBEntities(DBEntitiesFactory dBEntities) {
        this.dBEntities = dBEntities;
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

    public void setaSEJB(ApplicationServicesUtil aSEJB) {
        this.aSEJB = aSEJB;
    }
    
    @Override
    protected ApplicationJpaController getApplicationDAO()
    {
        return aDAO;
    }
    
    @Override
    protected EndorsementJpaController getEndorsementDAO()
    {
        return eDAO;
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
    protected ApplicationServicesUtil getApplicationServicesUTIL()
    {
        return aSEJB;
    }
}
