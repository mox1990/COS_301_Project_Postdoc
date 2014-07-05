/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import com.softserve.DBEntities.AuditLog;
import com.softserve.DBEntities.Person;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author K
 */
@Local
public interface AuditTrailServiceLocal {
    public void logAction(AuditLog auditLog) throws Exception;
    public List<AuditLog> findAll();
    public List<AuditLog> findByTimestamp(Timestamp tStamp);
    public List<AuditLog> findBetweenRange(Timestamp start, Timestamp end);
    public AuditLog findByEntryID(Long eID);
    public List<AuditLog> findByAction(String action);
}
