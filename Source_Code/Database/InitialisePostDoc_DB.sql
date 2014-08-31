
DROP DATABASE IF EXISTS PostDoc_DB;
CREATE DATABASE PostDoc_DB;

DROP DATABASE IF EXISTS PostDoc_Archive_DB;
CREATE DATABASE PostDoc_Archive_DB;

DROP DATABASE IF EXISTS PostDoc_BackUp_DB;
CREATE DATABASE PostDoc_BackUp_DB;

USE PostDoc_DB;

CREATE TABLE address (
	_addressID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_country VARCHAR(100),
	_province VARCHAR(100),
	_town_city VARCHAR(100),
	_suburb VARCHAR(100),
	_street VARCHAR(100),
	_streeNumber INT,
	_roomNumber VARCHAR(50),
	_zip_postalCode CHAR(6),
	
	PRIMARY KEY (_addressID)
) ENGINE=InnoDB;

CREATE TABLE institution (
	_institutionID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_name VARCHAR(250) NOT NULL,
	
	PRIMARY KEY (_institutionID)
) ENGINE=InnoDB;

CREATE TABLE faculty (
	_facultyID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_institution BIGINT UNSIGNED NOT NULL,
	_name VARCHAR(250) NOT NULL,
	
	PRIMARY KEY (_facultyID),
	FOREIGN KEY (_institution) REFERENCES institution(_institutionID)
) ENGINE=InnoDB;


CREATE TABLE department (
	_departmentID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_faculty BIGINT UNSIGNED NOT NULL,
	_name VARCHAR(250) NOT NULL,
	
	PRIMARY KEY (_departmentID),
	FOREIGN KEY (_faculty) REFERENCES faculty(_facultyID)
) ENGINE=InnoDB;

CREATE TABLE person (
	_systemID CHAR(9) NOT NULL,
	_password VARCHAR(50) NOT NULL,
	_title CHAR(10) NOT NULL,
	_fullName VARCHAR(250) NOT NULL,
	_surname VARCHAR(250) NOT NULL,
	_email VARCHAR(50) NOT NULL,
	_telephoneNumber CHAR(20),
	_workNumber CHAR(20),
	_faxNumber CHAR(20),
	_cellphoneNumber CHAR(20),	
	_addressLine1 BIGINT UNSIGNED,
	_upEmployee BOOLEAN NOT NULL,
	_accountStatus ENUM('active', 'pending', 'disabled', 'dorment'),
	
	
	PRIMARY KEY (_systemID),	
	FOREIGN KEY (_addressLine1) REFERENCES address(_addressID)
) ENGINE=InnoDB;

CREATE TABLE employee_information (
	_employeeID CHAR(9) NOT NULL,	
	_physicalAddress BIGINT UNSIGNED,
	_position VARCHAR(50),
	_dateOfAppointment DATE,
	_appointmentStatus VARCHAR(50),
	_department BIGINT UNSIGNED,
	
	PRIMARY KEY (_employeeID),
	FOREIGN KEY (_employeeID) REFERENCES person(_systemID),
	FOREIGN KEY (_department) REFERENCES department(_departmentID),
	FOREIGN KEY (_physicalAddress) REFERENCES address(_addressID)
) ENGINE=InnoDB;

CREATE TABLE research_fellow_information (
	_systemAssignedID CHAR(9) NOT NULL,	
	_institutionAssignedID CHAR(9),
	_institutionAssignedEmail CHAR(9),
	_department BIGINT UNSIGNED,
	
	PRIMARY KEY (_systemAssignedID),
	FOREIGN KEY (_systemAssignedID) REFERENCES person(_systemID),
	FOREIGN KEY (_department) REFERENCES department(_departmentID)
) ENGINE=InnoDB;

CREATE TABLE security_role (
	_roleID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_name VARCHAR(150) NOT NULL,
	_roleMask BIGINT UNSIGNED NOT NULL,
	
	PRIMARY KEY (_roleID)
) ENGINE=InnoDB;

CREATE TABLE person_security_role (
	_personID CHAR(9) NOT NULL,
	_roleID BIGINT UNSIGNED NOT NULL,
	
	PRIMARY KEY (_personID, _roleID),
	FOREIGN KEY (_personID) REFERENCES person(_systemID),
	FOREIGN KEY (_roleID) REFERENCES security_role(_roleID)
) ENGINE=InnoDB;

CREATE TABLE notification (
	_notificationID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_subject VARCHAR(200),
	_message TEXT,
	_emailStatus ENUM('sent', 'queued', 'disabled'),
	_timestamp DATETIME NOT NULL,
	_sender CHAR(9) NOT NULL,
	_reciever	CHAR(9) NOT NULL,
	
	PRIMARY KEY (_notificationID),
	FOREIGN KEY (_sender) REFERENCES person(_systemID),
	FOREIGN KEY (_reciever) REFERENCES person(_systemID)
) ENGINE=InnoDB;



CREATE TABLE application (
    _applicationID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    _type ENUM('new', 'renewal'),
    _status ENUM('open', 'submitted', 'declined', 'referred', 'finalised', 'recommended', 'endorsed', 'eligible', 'funded', 'completed', 'terminated'),
	_fundingType ENUM('UP PhD Postdoc', 'UP Postdoc', 'Externally funded'),
    _timestamp DATETIME NOT NULL,
	_submissionDate DATETIME,
    _finalisationDate DATETIME,    
    _startDate DATE,
    _endDate DATE,
    _projectTitle VARCHAR(250),
    _information TEXT,
    _fellow CHAR(9) NOT NULL,
    _grantHolder CHAR(9),
    PRIMARY KEY (_applicationID),
    FOREIGN KEY (_fellow) REFERENCES person (_systemID),
    FOREIGN KEY (_grantHolder) REFERENCES person (_systemID)
)  ENGINE=InnoDB;

CREATE TABLE application_review_request (
	_person CHAR(9) NOT NULL,
	_application BIGINT UNSIGNED NOT NULL,
	_type ENUM('HOD','DEAN'),

	PRIMARY KEY (_person, _application, _type),
	FOREIGN KEY (_person) REFERENCES person(_systemID),
	FOREIGN KEY (_application) REFERENCES application(_applicationID)
)  ENGINE=InnoDB;

CREATE TABLE eligiblity_report (
	_reportID BIGINT UNSIGNED NOT NULL,
	_eligiblityCheckDate DATETIME NOT NULL,
	_eligiblityChecker CHAR(9) NOT NULL,
	PRIMARY KEY (_reportID),
	FOREIGN KEY (_reportID) REFERENCES application(_applicationID),
	FOREIGN KEY (_eligiblityChecker) REFERENCES person (_systemID)
)  ENGINE=InnoDB;

CREATE TABLE decline_report (
	_reportID BIGINT UNSIGNED NOT NULL,
	_creator CHAR(9) NOT NULL,
	_timestamp DATETIME NOT NULL,
	_reason TEXT,
	PRIMARY KEY (_reportID),
	FOREIGN KEY (_reportID) REFERENCES application(_applicationID),
	FOREIGN KEY (_creator) REFERENCES person (_systemID)
) ENGINE=InnoDB;

CREATE TABLE ammend_request (
	_requestID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_application BIGINT UNSIGNED NOT NULL,
	_creator CHAR(9) NOT NULL,
	_timestamp DATETIME NOT NULL,
	_request TEXT,
	PRIMARY KEY (_requestID),
	FOREIGN KEY (_application) REFERENCES application(_applicationID),
	FOREIGN KEY (_creator) REFERENCES person (_systemID)
) ENGINE=InnoDB;

CREATE TABLE recommendation_report (
	_reportID BIGINT UNSIGNED NOT NULL,
	_hod CHAR(9) NOT NULL,
	_timestamp DATETIME NOT NULL,
	_content TEXT NOT NULL,

	PRIMARY KEY (_reportID),
	FOREIGN KEY (_reportID) REFERENCES application(_applicationID),
	FOREIGN KEY (_hod) REFERENCES person(_systemID)
) ENGINE=InnoDB;

CREATE TABLE endorsement (
	_endorsementID BIGINT UNSIGNED NOT NULL,
	_dean CHAR(9) NOT NULL,
	_timestamp DATETIME NOT NULL,
	_rank INT UNSIGNED NOT NULL,
	_motivation TEXT NOT NULL,

	PRIMARY KEY (_endorsementID),
	FOREIGN KEY (_endorsementID) REFERENCES application(_applicationID),
	FOREIGN KEY (_dean) REFERENCES person(_systemID)
) ENGINE=InnoDB;

CREATE TABLE funding_report (
	_reportID BIGINT UNSIGNED NOT NULL,
	_dris CHAR(9) NOT NULL,
	_timestamp DATETIME NOT NULL,

	PRIMARY KEY (_reportID),
	FOREIGN KEY (_reportID) REFERENCES application(_applicationID),
	FOREIGN KEY (_dris) REFERENCES person(_systemID)
) ENGINE=InnoDB;

CREATE TABLE funding_cost (
	_costID BIGINT UNSIGNED AUTO_INCREMENT,
	_fundingReport BIGINT UNSIGNED NOT NULL,
	_amount FLOAT,
	_provider VARCHAR(100),
	_type ENUM('fellowship','running','travel','equipment','operating','conference'),

	PRIMARY KEY (_costID),
	FOREIGN KEY (_fundingReport) REFERENCES funding_report(_reportID)
) ENGINE=InnoDB;

CREATE TABLE referee_application (
	_refereeID CHAR(9) NOT NULL,
	_applicationID BIGINT UNSIGNED NOT NULL,
	
	PRIMARY KEY (_refereeID, _applicationID),
	FOREIGN KEY (_refereeID) REFERENCES person(_systemID),
	FOREIGN KEY (_applicationID) REFERENCES application(_applicationID)
) ENGINE=InnoDB;
#CREATE TABLE NEW_applicationS (
#	_applicationID BIGINT UNSIGNED NOT NULL,
#	
#	PRIMARY KEY (_applicationID),
#	FOREIGN KEY (_applicationID) REFERENCES applicationS(_applicationID)
#);

CREATE TABLE referee_report (
	_reportID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_referee CHAR(9) NOT NULL,
	_applicationID BIGINT UNSIGNED NOT NULL,
	_timestamp DATETIME NOT NULL,
	_content TEXT NOT NULL,
	
	PRIMARY KEY (_reportID),
	FOREIGN KEY (_applicationID) REFERENCES application(_applicationID),
	FOREIGN KEY (_referee) REFERENCES person(_systemID)
) ENGINE=InnoDB;

CREATE TABLE progress_report (
	_reportID BIGINT UNSIGNED AUTO_INCREMENT,
	_application BIGINT UNSIGNED NOT NULL,
	_timestamp DATETIME NOT NULL,
	_content TEXT NOT NULL,
	
	PRIMARY KEY (_reportID),
	FOREIGN KEY (_application) REFERENCES application(_applicationID)
) ENGINE=InnoDB;

CREATE TABLE forward_and_rewind_report (
	_reportID BIGINT UNSIGNED AUTO_INCREMENT,
	_application BIGINT UNSIGNED NOT NULL,
	_dris CHAR(9) NOT NULL,
	_timestamp DATETIME NOT NULL,
	_type ENUM('forward', 'rewind') NOT NULL,
	_reason TEXT,
	_fromStatus ENUM('open', 'submitted', 'declined', 'referred', 'finalised', 'recommended', 'endorsed', 'eligible', 'funded', 'completed', 'terminated'),
	_toStatus ENUM('open', 'submitted', 'declined', 'referred', 'finalised', 'recommended', 'endorsed', 'eligible', 'funded', 'completed', 'terminated'),

	PRIMARY KEY (_reportID),
	FOREIGN KEY (_application) REFERENCES application(_applicationID),
	FOREIGN KEY (_dris) REFERENCES person(_systemID)
) ENGINE=InnoDB;

#CREATE TABLE RENEWAL_applicationS (
#	_applicationID BIGINT UNSIGNED NOT NULL,
	
#	PRIMARY KEY (_applicationID),
#	FOREIGN KEY (_applicationID) REFERENCES applicationS(_applicationID)
#);

CREATE TABLE committee_meeting (
	_meetingID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_name VARCHAR(100),
	_venue VARCHAR(100),
	_startDate DATETIME,
	_endDate DATETIME,
	
	PRIMARY KEY (_meetingID)
) ENGINE=InnoDB;

CREATE TABLE minute_comment (
	_commentID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_meeting BIGINT UNSIGNED NOT NULL,
	_timestamp DATETIME NOT NULL,
	_attendee CHAR(9) NOT NULL,
	_comment VARCHAR(500) NOT NULL,
	
	PRIMARY KEY (_commentID),
	FOREIGN KEY (_meeting) REFERENCES committee_meeting(_meetingID),
	FOREIGN KEY (_attendee) REFERENCES person(_systemID)
) ENGINE=InnoDB;

#CREATE TABLE NEW_applicationS_COMMITT_MEETINGS (
#	_meetingID BIGINT UNSIGNED NOT NULL,
#	_newApplicationID BIGINT UNSIGNED NOT NULL,
#	
#	PRIMARY KEY (_meetingID, _newApplicationID),
#	FOREIGN KEY (_meetingID) REFERENCES committee_meetingS(_meetingID),
#	FOREIGN KEY (_newApplicationID) REFERENCES NEW_applicationS(_applicationID)
#);

#CREATE TABLE RENEWAL_applicationS_COMMITT_MEETINGS (
#	_meetingID BIGINT UNSIGNED NOT NULL,
#	_renewalApplicationID BIGINT UNSIGNED NOT NULL,
	
#	PRIMARY KEY (_meetingID, _renewalApplicationID),
#	FOREIGN KEY (_meetingID) REFERENCES committee_meetingS(_meetingID),
#	FOREIGN KEY (_renewalApplicationID) REFERENCES RENEWAL_applicationS(_applicationID)
#);

CREATE TABLE attendence_list (	
	_meetingID BIGINT UNSIGNED NOT NULL,
	_attendeeID CHAR(9) NOT NULL,
	
	PRIMARY KEY (_meetingID, _attendeeID),
	FOREIGN KEY (_meetingID) REFERENCES committee_meeting(_meetingID),
	FOREIGN KEY (_attendeeID) REFERENCES person(_systemID)
) ENGINE=InnoDB;

CREATE TABLE committee_meetings_applications (
	_meetingID BIGINT UNSIGNED NOT NULL,
	_applicationID BIGINT UNSIGNED NOT NULL,
	
	PRIMARY KEY (_meetingID, _applicationID),
	FOREIGN KEY (_meetingID) REFERENCES committee_meeting(_meetingID),
	FOREIGN KEY (_applicationID) REFERENCES application(_applicationID)
) ENGINE=InnoDB;


CREATE TABLE audit_log (
	_entryID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_person CHAR(9) NOT NULL,
	_timestamp DATETIME NOT NULL,
	_action VARCHAR(100) NOT NULL,
	
	PRIMARY KEY (_entryID),
	FOREIGN KEY (_person) REFERENCES person(_systemID)
) ENGINE=InnoDB;

CREATE TABLE cv (
	_cvID CHAR(9) NOT NULL,
	_idNumber CHAR(30) NOT NULL,
	_dateOfBirth DATE NOT NULL,
	_gender ENUM('Male','Female','Other'),
	_citizenship VARCHAR(100),
	_nrfRating CHAR(4),
	_race CHAR(20),
	_recentInstitution VARCHAR(100),
	_researchOutput TEXT,
	_otherContributions TEXT,
	_additionalInformation TEXT,

	PRIMARY KEY(_cvID),
	FOREIGN KEY (_cvID) REFERENCES person(_systemID)
) ENGINE=InnoDB;

CREATE TABLE academic_qualification (
	_qualificationID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_cv CHAR(9) NOT NULL,
	_name VARCHAR(100),
	_fieldOfStudy VARCHAR(100),
	_institution VARCHAR(100),
	_yearObtained DATE,
	_distinctions TINYINT UNSIGNED,

	PRIMARY KEY (_qualificationID),
	FOREIGN KEY (_cv) REFERENCES cv(_cvID)
) ENGINE=InnoDB;

CREATE TABLE experience (
	_experienceID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_cv CHAR(9) NOT NULL,
	_capacity VARCHAR(100),
	_organisation VARCHAR(100),
	_startDate DATETIME NOT NULL,
	_endDate DATETIME NOT NULL,
	
	PRIMARY KEY (_experienceID),
	FOREIGN KEY (_cv) REFERENCES cv(_cvID)
) ENGINE=InnoDB;


INSERT INTO postdoc_db.security_role (_name, _roleMask) VALUES("Prospective fellow",0), 
															("Referee",1),
															("Research fellow",2),
															("Grant holder",3),
															("HOD", 4),
															("Dean's office member",5),
															("DRIS member", 6), 
															("Post doctoral Committee member",7), 
															("System administrator",8);

INSERT INTO postdoc_db.institution (_name) VALUES ('University of Pretoria'),
												('University of Cape Town');

INSERT INTO postdoc_db.faculty (_institution,_name) VALUES (1,'EBIT'),
												(1,'Humanities'),
												(2,'Health sciences');

INSERT INTO postdoc_db.department (_faculty,_name) VALUES (1,'Computer Science'),
												(1,'Informatics'),
												(1,'Computer and Electronic Engineers'),
												(2,'Philosophy'),
												(3,'Dean');

INSERT INTO postdoc_db.address (_country) VALUES("TestMainia");
INSERT INTO postdoc_db.address (_country) VALUES("TestUniversity");

INSERT INTO postdoc_db.person (_systemID,_fullName,_password,_title,_surname,_email,_addressLine1,_UpEmployee,_accountStatus) VALUES ('u12019837','Test','test','Mr','Tester','test@gmail.com',1,true, 'active');
INSERT INTO postdoc_db.employee_information (_employeeID, _physicalAddress, _position, _dateOfAppointment, _appointmentStatus, _department) VALUES('u12019837',2,'HOD','2001/01/20','full time',1);

INSERT INTO postdoc_db.person_security_role (_personID, _roleID) VALUES ('u12019837',1),
																		('u12019837',2),
																		('u12019837',3),
																		('u12019837',4),
																		('u12019837',5),
																		('u12019837',6),
																		('u12019837',7),
																		('u12019837',8),
																		('u12019837',9);


USE PostDoc_Archive_DB;

CREATE TABLE address  LIKE postdoc_db.address;

CREATE TABLE institution  LIKE postdoc_db.institution;

CREATE TABLE faculty  LIKE postdoc_db.faculty;

CREATE TABLE department  LIKE postdoc_db.department;

CREATE TABLE person  LIKE postdoc_db.person;

CREATE TABLE employee_information  LIKE postdoc_db.employee_information;

CREATE TABLE research_fellow_information  LIKE postdoc_db.research_fellow_information;

CREATE TABLE security_role  LIKE postdoc_db.security_role;

CREATE TABLE person_security_role  LIKE postdoc_db.person_security_role;

CREATE TABLE notification  LIKE postdoc_db.notification;

CREATE TABLE application  LIKE postdoc_db.application;

CREATE TABLE eligiblity_report  LIKE postdoc_db.eligiblity_report;

CREATE TABLE decline_report  LIKE postdoc_db.decline_report;

CREATE TABLE ammend_request  LIKE postdoc_db.ammend_request;

CREATE TABLE recommendation_report  LIKE postdoc_db.recommendation_report;

CREATE TABLE endorsement  LIKE postdoc_db.endorsement;

CREATE TABLE funding_report  LIKE postdoc_db.funding_report;

CREATE TABLE funding_cost  LIKE postdoc_db.funding_cost;

CREATE TABLE referee_application  LIKE postdoc_db.referee_application;

CREATE TABLE referee_report  LIKE postdoc_db.referee_report;

CREATE TABLE progress_report  LIKE postdoc_db.progress_report;

CREATE TABLE forward_and_rewind_report  LIKE postdoc_db.forward_and_rewind_report;

CREATE TABLE committee_meeting  LIKE postdoc_db.committee_meeting;

CREATE TABLE minute_comment  LIKE postdoc_db.minute_comment;

CREATE TABLE attendence_list  LIKE postdoc_db.attendence_list;

CREATE TABLE committee_meetings_applications  LIKE postdoc_db.committee_meetings_applications;

CREATE TABLE audit_log  LIKE postdoc_db.audit_log;

CREATE TABLE cv  LIKE postdoc_db.cv;

CREATE TABLE academic_qualification  LIKE postdoc_db.academic_qualification;

CREATE TABLE experience LIKE postdoc_db.experience;

USE PostDoc_BackUp_DB;

CREATE TABLE address  LIKE postdoc_db.address;

CREATE TABLE institution  LIKE postdoc_db.institution;

CREATE TABLE faculty  LIKE postdoc_db.faculty;

CREATE TABLE department  LIKE postdoc_db.department;

CREATE TABLE person  LIKE postdoc_db.person;

CREATE TABLE employee_information  LIKE postdoc_db.employee_information;

CREATE TABLE research_fellow_information  LIKE postdoc_db.research_fellow_information;

CREATE TABLE security_role  LIKE postdoc_db.security_role;

CREATE TABLE person_security_role  LIKE postdoc_db.person_security_role;

CREATE TABLE notification  LIKE postdoc_db.notification;

CREATE TABLE application  LIKE postdoc_db.application;

CREATE TABLE eligiblity_report  LIKE postdoc_db.eligiblity_report;

CREATE TABLE decline_report  LIKE postdoc_db.decline_report;

CREATE TABLE ammend_request  LIKE postdoc_db.ammend_request;

CREATE TABLE recommendation_report  LIKE postdoc_db.recommendation_report;

CREATE TABLE endorsement  LIKE postdoc_db.endorsement;

CREATE TABLE funding_report  LIKE postdoc_db.funding_report;

CREATE TABLE funding_cost  LIKE postdoc_db.funding_cost;

CREATE TABLE referee_application  LIKE postdoc_db.referee_application;

CREATE TABLE referee_report  LIKE postdoc_db.referee_report;

CREATE TABLE progress_report  LIKE postdoc_db.progress_report;

CREATE TABLE forward_and_rewind_report  LIKE postdoc_db.forward_and_rewind_report;

CREATE TABLE committee_meeting  LIKE postdoc_db.committee_meeting;

CREATE TABLE minute_comment  LIKE postdoc_db.minute_comment;

CREATE TABLE attendence_list  LIKE postdoc_db.attendence_list;

CREATE TABLE committee_meetings_applications  LIKE postdoc_db.committee_meetings_applications;

CREATE TABLE audit_log  LIKE postdoc_db.audit_log;

CREATE TABLE cv  LIKE postdoc_db.cv;

CREATE TABLE academic_qualification  LIKE postdoc_db.academic_qualification;

CREATE TABLE experience LIKE postdoc_db.experience;

