/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.DBDAO.AmmendRequestJpaController;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.RecommendationReportJpaController;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.HODRecommendationServices;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import java.util.GregorianCalendar;

/**
 *
 * @author kgothatso
 */
public class HODRecommendationServicesMockUnit extends HODRecommendationServices {
    private ApplicationJpaController aDAO;
    private RecommendationReportJpaController rRDAO;
    private DBEntitiesFactory dBEntities;
    private UserGateway uEJB;
    private NotificationService nEJB;
    private AuditTrailService aTEJB;
    private ApplicationServicesUtil aSEJB;
    private AmmendRequestJpaController aRDAO;
    private GregorianCalendar gCal;

    public void setgCal(GregorianCalendar gCal) {
        this.gCal = gCal;
    }

    public void setaRDAO(AmmendRequestJpaController aRDAO) {
        this.aRDAO = aRDAO;
    }

    public void setaDAO(ApplicationJpaController aDAO) {
        this.aDAO = aDAO;
    }

    public void setrRDAO(RecommendationReportJpaController rRDAO) {
        this.rRDAO = rRDAO;
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
    protected GregorianCalendar getGregorianCalendar()
    {
        return gCal;
    }
}
