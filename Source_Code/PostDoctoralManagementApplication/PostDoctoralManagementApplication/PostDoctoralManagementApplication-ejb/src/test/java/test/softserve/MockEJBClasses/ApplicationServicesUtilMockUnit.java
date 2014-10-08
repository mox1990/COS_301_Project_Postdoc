/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.auxillary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.DeclineReportJpaController;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxillary.util.ApplicationServicesUtil;
import com.softserve.auxillary.factories.DBEntitiesFactory;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kgothatso
 */
public class ApplicationServicesUtilMockUnit extends ApplicationServicesUtil {
    private ApplicationJpaController aDAO;
    private DBEntitiesFactory dBEntities;
    private UserGateway uEJB;
    private NotificationService nEJB;
    private AuditTrailService aTEJB;
    private DeclineReportJpaController dRDAO;
    private GregorianCalendar gCal;

    public ApplicationServicesUtilMockUnit() {
        super(null);
    }

    public ApplicationServicesUtilMockUnit(EntityManager em) {
        super(em);
    }

    public void setaDAO(ApplicationJpaController aDAO) {
        this.aDAO = aDAO;
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

    public void setdRDAO(DeclineReportJpaController dRDAO) {
        this.dRDAO = dRDAO;
    }

    public void setgCal(GregorianCalendar gCal) {
        this.gCal = gCal;
    }
    
    @Override    
    protected DAOFactory getDAOFactory()
    {
        return super.getDAOFactory();
    }    
    
    /**
     *
     * @return
     */
    @Override
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return dBEntities;
    }

    
    @Override
    protected GregorianCalendar getGregorianCalendar()
    {
        return gCal;
    }
}
