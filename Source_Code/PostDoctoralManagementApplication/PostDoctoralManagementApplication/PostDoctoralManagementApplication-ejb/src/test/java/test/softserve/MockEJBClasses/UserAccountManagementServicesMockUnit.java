/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.DBDAO.*;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserAccountManagementService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import java.util.GregorianCalendar;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */

public class UserAccountManagementServicesMockUnit extends UserAccountManagementService {     
    
     private PersonJpaController pDAO = null;
    private AddressJpaController aDAO = null;
    private EmployeeInformationJpaController uDAO = null;
    private DBEntitiesFactory dBEnitities = null;
    private UserGateway uGateway = null;
    private NotificationService nService = null;
    private AuditTrailService aService = null;
    private GregorianCalendar cal = null;

    public void setDBEntitiesFactory(DBEntitiesFactory dBEntities)
    {
        this.dBEnitities = dBEntities;
    }
    
    public void setUserGateway(UserGateway uGateway)
    {
        this.uGateway = uGateway;
    }
    
    public void setNotificationService(NotificationService nService)
    {
        this.nService = nService;
    }
    
    public void setAuditTrailService(AuditTrailService aService)
    {
        this.aService = aService;
    }

    @Override
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return dBEnitities;
    }

    @Override
    protected UserGateway getUserGatewayServiceEJB()
    {
        return uGateway;
    }
 
    @Override
    protected NotificationService getNotificationServiceEJB()
    {
        return nService;
    }
 
    @Override
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return aService;
    }
}
