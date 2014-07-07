/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.Address;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.UpEmployeeInformation;
import com.softserve.system.Session;
import javax.ejb.Local;

/**
 *
 * @author Carlo
 */
@Local
public interface UserGatewayLocal 
{
    public void authenticateUser(Person user, UpEmployeeInformation userUPInfo);
    public void getRole(Person user);
    public void generateOnDemandAccount(Session session, boolean useManualSystemIDSpecification, Person user, Address userAddress, UpEmployeeInformation userUPInfo);
    public void activateOnDemandAccount(Person user, boolean active);
}
