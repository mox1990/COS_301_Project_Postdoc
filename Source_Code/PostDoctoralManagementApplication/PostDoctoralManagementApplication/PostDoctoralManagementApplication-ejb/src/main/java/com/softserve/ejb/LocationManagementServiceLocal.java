/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.Location;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
@Local
public interface LocationManagementServiceLocal {
    public void createLocation(Session session, Location location) throws AuthenticationException, Exception;
    public void updateLocation(Session session, Location location) throws AuthenticationException, Exception;
    public List<Location> getAllLocations() throws AuthenticationException, Exception;
    public Location getLocationIDForLocation(Location location) throws AuthenticationException, Exception;
    public List<Location> getAllLocationsInFaculty(String faculty) throws AuthenticationException, Exception;
    public List<String> getAllDepartmentsInFacultyInInstitution(String institution, String faculty) throws AuthenticationException, Exception;
    public List<String> getAllFacultiesInInstitution(String institution) throws AuthenticationException, Exception;
    public List<String> getAllInstitutions() throws AuthenticationException, Exception;
}
