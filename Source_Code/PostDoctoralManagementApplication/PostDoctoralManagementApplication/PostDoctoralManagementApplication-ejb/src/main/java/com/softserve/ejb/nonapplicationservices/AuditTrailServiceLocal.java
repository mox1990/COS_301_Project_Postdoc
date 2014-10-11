/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb.nonapplicationservices;

import com.softserve.persistence.DBEntities.AuditLog;
import com.softserve.persistence.DBEntities.Person;
import com.softserve.auxiliary.requestresponseclasses.Session;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author K
 */
@Local
public interface AuditTrailServiceLocal {
    public void logAction(Session session, AuditLog auditLog) throws Exception;
    public List<AuditLog> loadAllAuditLogEntries(Session session) throws Exception;
}
