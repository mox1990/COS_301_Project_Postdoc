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
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlo
 */
@Local
public interface UserGatewayLocal 
{
    public int authenticateUser(Session session ,Person user, List<SecurityRole> role);
    public void generateOnDemandAccount(Session session, boolean useManualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo);
    public void activateOnDemandAccount(Session session ,Person user);
}
