/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.DBDAO.AddressJpaController;
import com.softserve.DBDAO.PersonJpaController;
import com.softserve.DBDAO.EmployeeInformationJpaController;
import com.softserve.ejb.nonapplicationservices.AuditTrailService;
import com.softserve.ejb.nonapplicationservices.UserGateway;
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
    private EmployeeInformationJpaController uDAO = null;
    private DBEntitiesFactory dbFactory = null;
    private AuditTrailService atsEJB = null;
    private GregorianCalendar cal = null;

    public void setpDAO(PersonJpaController pDAO) {
        this.pDAO = pDAO;
    }

    public void setaDAO(AddressJpaController aDAO) {
        this.aDAO = aDAO;
    }

    public void setuDAO(EmployeeInformationJpaController uDAO) {
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
    
}
