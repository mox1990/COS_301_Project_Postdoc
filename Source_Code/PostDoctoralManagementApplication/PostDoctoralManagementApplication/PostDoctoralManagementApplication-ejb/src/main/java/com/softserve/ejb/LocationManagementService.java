/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.LocationJpaController;
import com.softserve.DBEntities.Location;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.DBEntitiesFactory;
import com.softserve.system.Session;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class LocationManagementService implements LocationManagementServiceLocal {

    @PersistenceUnit(unitName = com.softserve.constants.PersistenceConstants.PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    
    @EJB
    private UserGatewayLocal userGatewayLocal;
    
    protected UserGatewayLocal getUserGatewayServiceEJB()
    {
        return userGatewayLocal;
    }

    public LocationManagementService() {
    }

    public LocationManagementService(EntityManagerFactory emf) {
        this.emf = emf;
    }
            
    protected LocationJpaController getLocationDAO()
    {
        return new LocationJpaController(com.softserve.constants.PersistenceConstants.getUserTransaction(), emf);
    }
    
    protected DBEntitiesFactory getDBEntitiesFactory()
    {
        return new DBEntitiesFactory();
    }
    
    
    @Override
    public void createLocation(Session session, Location location) throws AuthenticationException, Exception
    {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        LocationJpaController locationJpaController = getLocationDAO();
        
        locationJpaController.create(location);
    }
    
    @Override
    public void updateLocation(Session session, Location location) throws AuthenticationException, Exception
    {
        ArrayList<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(com.softserve.constants.PersistenceConstants.SECURITY_ROLE_SYSTEM_ADMINISTRATOR);
        getUserGatewayServiceEJB().authenticateUser(session, roles);
        
        LocationJpaController locationJpaController = getLocationDAO();
        
        locationJpaController.edit(location);
    }
    
    @Override
    public List<Location> getAllLocations() throws AuthenticationException, Exception
    {
        LocationJpaController locationJpaController = getLocationDAO();
        
        return locationJpaController.findLocationEntities();
    }
    
    @Override
    public Location getLocationIDForLocation(Location location) throws AuthenticationException, Exception
    {
        LocationJpaController locationJpaController = getLocationDAO();
        
        return locationJpaController.getLocationFromComponents(location.getInstitution(), location.getFaculty(), location.getDepartment());
    }
    
    @Override
    public List<Location> getAllLocationsInFaculty(String faculty) throws AuthenticationException, Exception
    {
        LocationJpaController locationJpaController = getLocationDAO();
        
        return locationJpaController.findLocationEntities();
    }
    
    @Override
    public List<String> getAllDepartmentsInFacultyInInstitution(String institution, String faculty) throws AuthenticationException, Exception
    {
        LocationJpaController locationJpaController = getLocationDAO();
        
        return locationJpaController.getAllDepartmentsInFacultyInInstitution(institution, faculty);
    }
    
    @Override
    public List<String> getAllFacultiesInInstitution(String institution) throws AuthenticationException, Exception
    {
        LocationJpaController locationJpaController = getLocationDAO();
        
        return locationJpaController.getAllFacultiesInInstitution(institution);
    }
    
    @Override
    public List<String> getAllInstitutions() throws AuthenticationException, Exception
    {
        LocationJpaController locationJpaController = getLocationDAO();
        
        return locationJpaController.getAllInstitutions();
    }
}
