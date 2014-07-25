/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.Application;
import com.softserve.DBEntities.Cv;
import com.softserve.DBEntities.Person;
import com.softserve.Exceptions.AuthenticationException;
import com.softserve.Exceptions.CVAlreadExistsException;
import com.softserve.Exceptions.UserAlreadyExistsException;
import com.softserve.system.Session;
import javax.ejb.Local;

/**
 * 
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */

@Local
public interface NewApplicationServiceLocal
{    
    public void createProspectiveFellowCV(Session session, Cv cv) throws AuthenticationException, CVAlreadExistsException, Exception;
    public void createNewApplication(Session session, Application application) throws AuthenticationException, Exception;
    public void linkGrantHolderToApplication(Session session, Application application, Person grantHolder) throws AuthenticationException, UserAlreadyExistsException, Exception;
    public void linkRefereeToApplication(Session session, Application application, Person referee) throws AuthenticationException, UserAlreadyExistsException, Exception;
    public void submitApplication(Session session, Application application) throws Exception;
    public boolean canFellowOpenANewApplication(Person fellow);
    public Application getOpenApplication(Session session) throws AuthenticationException, Exception;
}
