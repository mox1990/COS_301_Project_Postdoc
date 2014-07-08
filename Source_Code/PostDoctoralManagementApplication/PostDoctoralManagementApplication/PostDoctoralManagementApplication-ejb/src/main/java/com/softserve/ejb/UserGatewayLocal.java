/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.SecurityRoleJpaController;
import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.SecurityRole;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carlo
 */
@Local
public interface UserGatewayLocal 
{
    public void authenticateUser(Session session, List<SecurityRole> allowedRoles) throws AuthenticationException, Exception;
    public Session login(HttpSession httpSession) throws AuthenticationException, Exception;
    public void logout(Session session) throws Exception;
    public Session getSessionFromHttpSession(HttpSession httpSession) throws AuthenticationException;    
    public void generateOnDemandAccount(Session session, String reason, boolean useManualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo) throws Exception;
    public void activateOnDemandAccount(Session session, Person user) throws Exception;
}
