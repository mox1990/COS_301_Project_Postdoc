/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.DBDAO.AmmendRequestJpaController;
import com.softserve.system.ApplicationServicesUtil;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.ApplicationReviewRequestJpaController;
import com.softserve.DBDAO.CvJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.CVManagementService;
import com.softserve.ejb.GrantHolderFinalisationService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import java.util.GregorianCalendar;

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
    private ApplicationServicesUtil aSEJB;
    private GregorianCalendar gCal;
    private ApplicationReviewRequestJpaController aRDAO;

    public void setaRDAO(ApplicationReviewRequestJpaController aRDAO) {
        this.aRDAO = aRDAO;
    }
    
    public void setgCal(GregorianCalendar gCal) {
        this.gCal = gCal;
    }

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

    public void setaSEJB(ApplicationServicesUtil aSEJB) {
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
    protected NotificationService getNotificationServiceEJB()
    {
        return nEJB;
    }
    
    @Override
    protected CVManagementService getCVManagementServiceEJB()
    {
        return cVEJB;
    }
    
    @Override
    protected ApplicationServicesUtil getApplicationServicesUTIL()
    {
        return aSEJB;
    }
    
    @Override
    protected GregorianCalendar getGregorianCalendar()
    {
        return gCal;
    }
    
    @Override
    protected ApplicationReviewRequestJpaController getApplicationReviewRequestDAO()
    {
        return aRDAO;
    }
}
