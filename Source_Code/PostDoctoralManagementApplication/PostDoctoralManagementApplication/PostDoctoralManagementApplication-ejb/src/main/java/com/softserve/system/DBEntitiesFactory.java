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
    
    public Address createAddressEntitiy(String country, String province, String townCity, String street, Integer streetNum, String roomNum, String zipPostalCode)
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
    
    public AuditLog createAduitLogEntitiy(String action, Person person)
    {
        AuditLog auditLog = new AuditLog();
        
        auditLog.setAction(action);
        auditLog.setPerson(person);
        
        return auditLog;
    }
    
    public Notification createNotificationEntity(Person sender, Person reciever, String subject, String message)
    {
        Notification notification = new Notification();
        
        notification.setSender(sender);
        notification.setReciever(reciever);
        notification.setMessage(message);
        notification.setSubject(subject);
        
        return notification;
    }
    
    public SecurityRole createSecurityRoleEntity(Long id, String roleName, Long roleMask)
    {
        SecurityRole securityRole= new SecurityRole();
        
        securityRole.setRoleID(id);
        securityRole.setName(roleName);
        securityRole.setRoleID(roleMask);
        
        return securityRole;
    }
    
    public ProgressReport createProgressReportEntity(Application application, String content, Date timestamp)
    {
        ProgressReport progressReport = new ProgressReport();
        
        progressReport.setApplication(application);
        progressReport.setContent(content);
        progressReport.setTimestamp(timestamp);
        
        return progressReport;
    }
    
    public AmmendRequest createAmmendRequestEntity(Application application, Person creator, String request, Date timestamp)
    {
        AmmendRequest ammendRequest = new AmmendRequest();
        ammendRequest.setCreator(creator);
        ammendRequest.setApplication(application);
        ammendRequest.setTimestamp(timestamp);
        ammendRequest.setRequest(request);
        
        return ammendRequest;        
    }
    
    public DeclineReport createDeclineReportEntity(Application application, Person creator, String reason, Date timestamp)
    {
        DeclineReport declineReport = new DeclineReport();
        
        declineReport.setReportID(application.getApplicationID());
        declineReport.setApplication(application);
        declineReport.setCreator(creator);
        declineReport.setTimestamp(timestamp);
        declineReport.setReason(reason);
        
        return declineReport;        
    }
    
    public EligiblityReport createEligiblityReportEntity(Application application, Person checker, Date timestamp)
    {
        EligiblityReport eligiblityReport = new EligiblityReport();
        
        eligiblityReport.setReportID(application.getApplicationID());
        eligiblityReport.setApplication(application);
        eligiblityReport.setEligiblityCheckDate(timestamp);
        eligiblityReport.setEligiblityChecker(checker);
        
        return eligiblityReport;        
    }
    
    public ForwardAndRewindReport createForwardAndRewindReport(Application application, Person dris, Date timestamp, String reason, String type, String toStatus, String fromStatus)
    {
        ForwardAndRewindReport forwardAndRewindReport = new ForwardAndRewindReport();
        
        forwardAndRewindReport.setApplication(application);
        forwardAndRewindReport.setDris(dris);
        forwardAndRewindReport.setReason(reason);
        forwardAndRewindReport.setType(type);
        forwardAndRewindReport.setTimestamp(timestamp);
        forwardAndRewindReport.setToStatus(toStatus);
        forwardAndRewindReport.setFromStatus(fromStatus);
        
        return forwardAndRewindReport;        
    }
    
    public ApplicationReviewRequest createApplicationReviewRequest(Application application, Person person, String type)
    {
        ApplicationReviewRequest applicationReviewRequest = new ApplicationReviewRequest();
        
        ApplicationReviewRequestPK applicationReviewRequestPK = new ApplicationReviewRequestPK();
        applicationReviewRequestPK.setApplication(application.getApplicationID());
        applicationReviewRequestPK.setPerson(person.getSystemID());
        applicationReviewRequestPK.setType(type);
        
        applicationReviewRequest.setApplicationReviewRequestPK(applicationReviewRequestPK);
        applicationReviewRequest.setApplication1(application);
        applicationReviewRequest.setPerson1(person);
        
        return applicationReviewRequest;       
    }
    //Rest of entities: Add as required
}
