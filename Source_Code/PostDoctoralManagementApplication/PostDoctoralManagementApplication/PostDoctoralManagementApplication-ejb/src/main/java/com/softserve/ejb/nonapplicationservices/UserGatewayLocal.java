/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.nonapplicationservices;

import com.softserve.DBDAO.SecurityRoleJpaController;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.CommitteeMeeting;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.FundingReport;
import com.softserve.DBEntities.Person;
import com.softserve.DBEntities.ProgressReport;
import com.softserve.DBEntities.SecurityRole;
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
    public void authenticateUser(Session session, List<SecurityRole> allowedRoles) throws Exception;
    public void authenticateUserAsOwner(Session session, Person person) throws Exception;
    public void authenticateUserAsOwner(Session session, Application application) throws Exception;
    public void authenticateUserAsOwner(Session session, Cv cv) throws Exception;
    public void authenticateUserAsOwner(Session session, ProgressReport progressReport) throws Exception;
    public void authenticateUserAsOwner(Session session, CommitteeMeeting committeeMeeting) throws Exception;
    public void authenticateUserAsOwner(Session session, FundingReport fundingReport) throws Exception;
    public void login(Session httpSession) throws Exception;
    public void logout(Session session) throws Exception;
    public Session getSessionFromHttpSession(HttpSession httpSession) throws Exception;    
    
}
