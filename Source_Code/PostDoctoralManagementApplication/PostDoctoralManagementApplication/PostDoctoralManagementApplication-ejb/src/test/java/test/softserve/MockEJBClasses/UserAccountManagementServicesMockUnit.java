/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package test.softserve.MockEJBClasses;

import com.softserve.DBDAO.*;
import com.softserve.ejb.AuditTrailService;
import com.softserve.ejb.UserAccountManagementService;
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
    
    /**
     *This function creates an instance of the PersonJpaController. 
     * Note this function's secondary goal is to simplify the subclass mocking 
 of the UserAccountManagementService in the unit testing 
     * @return An instance of PersonJpaController
     */
    @Override
    protected PersonJpaController getPersonDAO()
    {
        return pDAO;
    }
    
    /**
     *This function creates an instance of the AddressJpaController. 
     * Note this function's secondary goal is to simplify the subclass mocking 
 of the UserAccountManagementService in the unit testing 
     * @return An instance of AddressJpaController
     */
    @Override
    protected AddressJpaController getAddressDAO()
    {
        return aDAO;
    }
    
    /**
     *This function creates an instance of the UpEmployeeInformationJpaController. 
     * Note this function's secondary goal is to simplify the subclass mocking 
 of the UserAccountManagementService in the unit testing 
     * @return An instance of UpEmployeeInformationJpaController
     */
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
    
    public String Mock_generateSystemID(char prefix)
    {
        return generateSystemID(prefix);
    }
    
}
