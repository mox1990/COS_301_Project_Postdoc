/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.auxiliary.util.ApplicationServicesUtil;
import com.softserve.persistence.DBDAO.ApplicationJpaController;
import com.softserve.auxiliary.factories.DAOFactory;
import com.softserve.persistence.DBDAO.EligiblityReportJpaController;
import com.softserve.persistence.DBDAO.FundingCostJpaController;
import com.softserve.persistence.DBDAO.FundingReportJpaController;
import com.softserve.persistence.DBDAO.PersonJpaController;
import com.softserve.persistence.DBEntities.AcademicQualification;
import com.softserve.persistence.DBEntities.Application;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.applicationservices.DRISApprovalService;
import com.softserve.ejb.nonapplicationservices.NotificationService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
import com.softserve.auxiliary.factories.DBEntitiesFactory;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author kgothatso
 */
public class DRISApprovalServiceMockUnit extends DRISApprovalService {
    private ApplicationJpaController aDAO;
    private FundingReportJpaController fRDAO;
    private DBEntitiesFactory dBEntities;
    private UserGateway uEJB;
    private NotificationService nEJB;
    private AuditTrailService aTEJB;
    private ApplicationServicesUtil aSEJB;
    private EligiblityReportJpaController eDAO;
    private GregorianCalendar gCal;
    private PersonJpaController pDAO;
    private FundingCostJpaController fCDAO;

    public void setfCDAO(FundingCostJpaController fCDAO) {
        this.fCDAO = fCDAO;
    }
    
    public void setpDAO(PersonJpaController pDAO) {
        this.pDAO = pDAO;
    }
    

    public void setgCal(GregorianCalendar gCal) {
        this.gCal = gCal;
    }
    
    public void seteDAO(EligiblityReportJpaController eDAO) {
        this.eDAO = eDAO;
    }
    
    public void setaDAO(ApplicationJpaController aDAO) {
        this.aDAO = aDAO;
    }

    public void setfRDAO(FundingReportJpaController fRDAO) {
        this.fRDAO = fRDAO;
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
    
    

    
    /** TODO: Implement work around...
    protected boolean hasPhD(Application application)
    {
        List<AcademicQualification> aqList = application.getFellow().getCv().getAcademicQualificationList();
        
        for(AcademicQualification aq : aqList)
        {
            if(aq.getName().toUpperCase().contains("PHD"))
            {
                return true;
            }
        }
        
        return false;
    }
    
    protected boolean hasObtainedPhDInLast5Years(Application application)
    {     
        
        List<AcademicQualification> aqList = application.getFellow().getCv().getAcademicQualificationList();
        
        GregorianCalendar curCal = new GregorianCalendar();
        GregorianCalendar obtainCal = new GregorianCalendar();
        
        
        for(AcademicQualification aq : aqList)
        {
            if(aq.getName().toUpperCase().contains("PHD"))
            {
                obtainCal.setTimeInMillis(aq.getYearObtained().getTime());
                if(curCal.get(GregorianCalendar.YEAR) - obtainCal.get(GregorianCalendar.YEAR) <= 5)
                {
                    return true;
                }
            }
        }
        
        return false;
    }*/
    
}
