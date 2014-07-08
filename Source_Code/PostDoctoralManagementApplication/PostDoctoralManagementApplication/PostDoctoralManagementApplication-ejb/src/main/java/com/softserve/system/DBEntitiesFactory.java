/*
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

package com.softserve.system;

import com.softserve.DBEntities.*;

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
        auditLog.setPersonID(person);
        
        return auditLog;
    }
    
    public Notification buildNotificationEntity(Person sender, Person reciever, String subject, String message)
    {
        Notification notification = new Notification();
        
        notification.setSenderID(sender);
        notification.setRecieverID(reciever);
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
    
    //Rest of entities: Add as required
}
