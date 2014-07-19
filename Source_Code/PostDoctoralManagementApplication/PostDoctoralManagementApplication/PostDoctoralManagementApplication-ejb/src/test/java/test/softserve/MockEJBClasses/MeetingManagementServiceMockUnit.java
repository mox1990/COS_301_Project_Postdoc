/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.DBDAO.CommitteeMeetingJpaController;
import com.softserve.DBDAO.MinuteCommentJpaController;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.MeetingManagementService;
import com.softserve.ejb.NotificationService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;

/**
 *
 * @author kgothatso
 */
public class MeetingManagementServiceMockUnit extends MeetingManagementService {
    private CommitteeMeetingJpaController cMDAO = null;
    private MinuteCommentJpaController mCDAO = null;
    private DBEntitiesFactory dBEnitities = null;
    private UserGateway uGateway = null;
    private NotificationService nService = null;
    private AuditTrailService aService = null;
    
    public void setCMDAO(CommitteeMeetingJpaController cMDAO)
    {
        this.cMDAO = cMDAO;
    }
    
    public void setMCDAO(MinuteCommentJpaController mCDAO)
    {
        this.mCDAO = mCDAO;
    }
    
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
    /**
     *This function creates an instance of the PersonJpaController. 
     * Note this function's secondary goal is to simplify the subclass mocking 
     * of the UserAccountManagementServices in the unit testing 
     * @return An instance of PersonJpaController
     */
    @Override
    protected CommitteeMeetingJpaController getCommitteeMeetingDAO()
    {
        return cMDAO;
    }
    
    /**
     *
     * @return
     */
    @Override
    protected MinuteCommentJpaController getMinuteCommentDAO()
    {
        return mCDAO;
    }
    
    /**
     *
     * @return
     */
    @Override
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return dBEnitities;
    }
    
    /**
     *
     * @return
     */
    @Override
    protected UserGateway getUserGatewayServiceEJB()
    {
        return uGateway;
    }
    
    /**
     *
     * @return
     */
    @Override
    protected NotificationService getNotificationServiceEJB()
    {
        return nService;
    }
    
    /**
     *
     * @return
     */
    @Override
    protected AuditTrailService getAuditTrailServiceEJB()
    {
        return aService;
    }
}