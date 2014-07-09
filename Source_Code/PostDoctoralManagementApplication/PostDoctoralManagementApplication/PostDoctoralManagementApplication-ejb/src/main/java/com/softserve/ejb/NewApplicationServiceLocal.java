/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBDAO.exceptions.NonexistentEntityException;
import com.softserve.DBDAO.exceptions.RollbackFailureException;
import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Person;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Exceptions.CVAlreadExistsException;
import com.softserve.Exceptions.UserAlreadyExistsException;
import com.softserve.system.Session;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Carlo
 */

@Local
public interface NewApplicationServiceLocal
{    
    public void createProspectiveFellowCV(Session session, Cv cv) throws AuthenticationException, CVAlreadExistsException, Exception;
    public void createNewApplication(Session session, Application application) throws AuthenticationException, Exception;
    public void linkGrantHolderToApplication(Session session, Application application, Person grantHolder) throws AuthenticationException, UserAlreadyExistsException, Exception;
    public void linkRefereeToApplication(Session session, Application application, Person referee) throws AuthenticationException, UserAlreadyExistsException, Exception;
    public void submitApplication(Session session, Application application) throws Exception;
}
