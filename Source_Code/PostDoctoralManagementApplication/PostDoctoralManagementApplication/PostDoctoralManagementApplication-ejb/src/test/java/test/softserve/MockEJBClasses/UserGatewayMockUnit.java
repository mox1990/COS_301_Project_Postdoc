/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.DBDAO.AddressJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.UpEmployeeInformationJpaController;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.UserGateway;
import com.softserve.system.DBEntitiesFactory;
import java.util.GregorianCalendar;

/**
 *
 * @author User
 */
public class UserGatewayMockUnit extends UserGateway
{
    private PersonJpaController pDAO = null;
    private AddressJpaController aDAO = null;
    private UpEmployeeInformationJpaController uDAO = null;
    private DBEntitiesFactory dbFactory = null;
    private AuditTrailService atsEJB = null;
    private GregorianCalendar cal = null;

    public void setpDAO(PersonJpaController pDAO) {
        this.pDAO = pDAO;
    }

    public void setaDAO(AddressJpaController aDAO) {
        this.aDAO = aDAO;
    }

    public void setuDAO(UpEmployeeInformationJpaController uDAO) {
        this.uDAO = uDAO;
    }

    public void setDbFactory(DBEntitiesFactory dbFactory) {
        this.dbFactory = dbFactory;
    }

    public void setAtsEJB(AuditTrailService atsEJB) {
        this.atsEJB = atsEJB;
    }

    public void setCal(GregorianCalendar cal) {
        this.cal = cal;
    }
    
    @Override
    protected GregorianCalendar getGregorianCalendar() {
        return cal;
    }
    

    @Override
    protected PersonJpaController getPersonDAO()
    {
        return pDAO;
    }
    
    @Override
    protected AddressJpaController getAddressDAO()
    {
        return aDAO;
    }
    
  
    @Override
    protected UpEmployeeInformationJpaController getUPEmployeeInfoDAO()
    {
        return uDAO;
    }

    @Override
    protected AuditTrailService getAuditTrailServiceEJB() 
    {
        return atsEJB;
    }

    @Override
    protected DBEntitiesFactory getDBEntitiesFactory() 
    {
        return dbFactory;
    }
}
