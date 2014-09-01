/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.system.ApplicationServicesUtil;
import com.softserve.DBDAO.ApplicationJpaController;
import com.softserve.DBDAO.EligiblityReportJpaController;
import com.softserve.DBDAO.FundingCostJpaController;
import com.softserve.DBDAO.FundingReportJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBEntities.AcademicQualification;
import com.softserve.DBEntities.Application;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.DRISApprovalService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import java.util.GregorianCalendar;
import java.util.List;

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
    protected ApplicationJpaController getApplicationDAO()
    {
        return aDAO;
    }
    
    @Override
    protected FundingReportJpaController getFundingReportDAO()
    {
        return fRDAO;
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
    protected ApplicationServicesUtil getApplicationServicesUTIL()
    {
        return aSEJB;
    }
    
    @Override
    protected EligiblityReportJpaController getEligiblityReportDAO()
    {
        return eDAO;
    }
    
    @Override
    protected GregorianCalendar getGregorianCalendar()
    {
        return gCal;
    }
    
    @Override
    protected PersonJpaController getPersonDAO()
    {
        return pDAO;
    }
    
    @Override
    protected FundingCostJpaController getFundingCostDAO()
    {
        return fCDAO;
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
