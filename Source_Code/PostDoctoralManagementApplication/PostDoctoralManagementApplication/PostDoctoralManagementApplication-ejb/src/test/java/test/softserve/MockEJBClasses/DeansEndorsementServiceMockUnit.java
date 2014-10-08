/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.auxillary.util.ApplicationServicesUtil;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.auxillary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.EndorsementJpaController;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.applicationservices.DeansEndorsementService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxillary.factories.DBEntitiesFactory;
import javax.persistence.EntityManager;

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
    protected DAOFactory getDAOFactory(EntityManager em)
    {
        return new DAOFactory(em);
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
}
