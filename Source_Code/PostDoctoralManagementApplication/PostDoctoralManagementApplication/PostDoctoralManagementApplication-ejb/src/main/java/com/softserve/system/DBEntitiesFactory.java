/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.system;

import com.softserve.DBEntities.*;
import java.util.Date;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class DBEntitiesFactory {
    
    public Address buildAddressEntitiy(String country, String province, String townCity, String street, Integer streetNum, String roomNum, String zipPostalCode)
    {
        Address address = new Address();
        
        address.setCountry(country);
        address.setProvince(province);
        address.setTownCity(townCity);
        address.setStreet(street);
        address.setStreeNumber(streetNum);
        address.setRoomNumber(roomNum);
        address.setZippostalCode(zipPostalCode);
        
        return address;
    }
    
    public AuditLog buildAduitLogEntitiy(String action, Person person)
    {
        AuditLog auditLog = new AuditLog();
        
        auditLog.setAction(action);
        auditLog.setPerson(person);
        
        return auditLog;
    }
    
    public Notification buildNotificationEntity(Person sender, Person reciever, String subject, String message)
    {
        Notification notification = new Notification();
        
        notification.setSender(sender);
        notification.setReciever(reciever);
        notification.setMessage(message);
        notification.setSubject(subject);
        
        return notification;
    }
    
    public SecurityRole bulidSecurityRoleEntity(Long id, String roleName, Long roleMask)
    {
        SecurityRole securityRole= new SecurityRole();
        
        securityRole.setRoleID(id);
        securityRole.setName(roleName);
        securityRole.setRoleID(roleMask);
        
        return securityRole;
    }
    
    public ProgressReport bulidProgressReportEntity(Application application, String content, Date timestamp)
    {
        ProgressReport progressReport = new ProgressReport();
        
        progressReport.setApplication(application);
        progressReport.setContent(content);
        progressReport.setTimestamp(timestamp);
        
        return progressReport;
    }
    
    public AmmendRequest bulidAmmendRequestEntity(Application application, Person creator, String request, Date timestamp)
    {
        AmmendRequest ammendRequest = new AmmendRequest();
        ammendRequest.setCreator(creator);
        ammendRequest.setApplication(application);
        ammendRequest.setTimestamp(timestamp);
        ammendRequest.setRequest(request);
        
        return ammendRequest;        
    }
    
    public DeclineReport bulidDeclineReportEntity(Application application, Person creator, String reason, Date timestamp)
    {
        DeclineReport declineReport = new DeclineReport();
        
        declineReport.setReportID(application.getApplicationID());
        declineReport.setApplication(application);
        declineReport.setCreator(creator);
        declineReport.setTimestamp(timestamp);
        declineReport.setReason(reason);
        
        return declineReport;        
    }
    
    public EligiblityReport bulidEligiblityReportEntity(Application application, Person checker, Date timestamp)
    {
        EligiblityReport eligiblityReport = new EligiblityReport();
        
        eligiblityReport.setReportID(application.getApplicationID());
        eligiblityReport.setApplication(application);
        eligiblityReport.setEligiblityCheckDate(timestamp);
        eligiblityReport.setEligiblityChecker(checker);
        
        return eligiblityReport;        
    }
    //Rest of entities: Add as required
}
