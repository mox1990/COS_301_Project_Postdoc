/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.ApplicationServices.ApplicationServices;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.RecommendationReportJpaController;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.HODRecommendationServices;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;

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
    private ApplicationServices aSEJB;
    
    @Override
    protected ApplicationJpaController getApplicationDAO()
    {
        return aDAO;
    }
    
    @Override
    protected RecommendationReportJpaController getRecommmendationReportDAO()
    {
        return rRDAO;
    }
    
    @Override
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return dBEntities;
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
}
