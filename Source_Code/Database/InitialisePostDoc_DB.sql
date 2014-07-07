
DROP DATABASE IF EXISTS PostDoc_DB;
CREATE DATABASE PostDoc_DB;

USE PostDoc_DB;

CREATE TABLE ADDRESS (
	_addressID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_country VARCHAR(50),
	_province VARCHAR(50),
	_town_city VARCHAR(50),
	_street VARCHAR(50),
	_streeNumber INT,
	_roomNumber VARCHAR(50),
	_zip_postalCode CHAR(6),
	
	PRIMARY KEY (_addressID)
) ENGINE=InnoDB;

CREATE TABLE LOCATION (
	_locationID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_institution VARCHAR(250) NOT NULL,
	_faculty VARCHAR(250) NOT NULL,
	_department VARCHAR(250) NOT NULL,
	
	PRIMARY KEY (_locationID)
) ENGINE=InnoDB;

CREATE TABLE PERSON (
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
	_addressLine1 BIGINT UNSIGNED NOT NULL,
	_upEmployee BOOLEAN NOT NULL,
	_accountStatus ENUM('active','disabled','dorment'),
	_locationID BIGINT UNSIGNED,
	
	PRIMARY KEY (_systemID),
	FOREIGN KEY (_locationID) REFERENCES LOCATION(_locationID),
	FOREIGN KEY (_addressLine1) REFERENCES ADDRESS(_addressID)
) ENGINE=InnoDB;

CREATE TABLE UP_EMPLOYEE_INFORMATION (
	_employeeID CHAR(9) NOT NULL,	
	_physicalAddress BIGINT UNSIGNED NOT NULL,
	_position VARCHAR(50) NOT NULL,
	_dateOfAppointment DATE NOT NULL,
	_appointmentStatus VARCHAR(50) NOT NULL,
	
	PRIMARY KEY (_employeeID),
	FOREIGN KEY (_employeeID) REFERENCES PERSON(_systemID),
	FOREIGN KEY (_physicalAddress) REFERENCES ADDRESS(_addressID)
) ENGINE=InnoDB;

CREATE TABLE SECURITY_ROLE (
	_roleID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_name VARCHAR(150) NOT NULL,
	_roleMask BIGINT UNSIGNED NOT NULL,
	
	PRIMARY KEY (_roleID)
) ENGINE=InnoDB;

CREATE TABLE PERSON_SECURITY_ROLE (
	_personID CHAR(9) NOT NULL,
	_roleID BIGINT UNSIGNED NOT NULL,
	
	PRIMARY KEY (_personID, _roleID),
	FOREIGN KEY (_personID) REFERENCES PERSON(_systemID),
	FOREIGN KEY (_roleID) REFERENCES SECURITY_ROLE(_roleID)
) ENGINE=InnoDB;

CREATE TABLE NOTIFICATION (
	_notificationID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_subject VARCHAR(200),
	_message TEXT,
	_timestamp DATETIME NOT NULL,
	_senderID CHAR(9) NOT NULL,
	_recieverID	CHAR(9) NOT NULL,
	
	PRIMARY KEY (_notificationID),
	FOREIGN KEY (_senderID) REFERENCES PERSON(_systemID),
	FOREIGN KEY (_recieverID) REFERENCES PERSON(_systemID)
) ENGINE=InnoDB;

CREATE TABLE RECOMMENDATION_REPORT (
	_reportID BIGINT UNSIGNED NOT NULL,
	_hodID CHAR(9) NOT NULL,
	_timestamp DATETIME NOT NULL,
	_content TEXT NOT NULL,
	
	PRIMARY KEY (_reportID),
	FOREIGN KEY (_hodID) REFERENCES PERSON(_systemID)
) ENGINE=InnoDB;

CREATE TABLE ENDORSEMENT (
	_endorsementID BIGINT UNSIGNED NOT NULL,
	_deanID CHAR(9) NOT NULL,
	_timestamp DATETIME NOT NULL,
	_rank INT UNSIGNED NOT NULL,
	_motivation TEXT NOT NULL,
	
	PRIMARY KEY (_endorsementID),
	FOREIGN KEY (_deanID) REFERENCES PERSON(_systemID)
) ENGINE=InnoDB;

CREATE TABLE FUNDING_REPORT (
	_reportID BIGINT UNSIGNED NOT NULL,
	_drisID CHAR(9) NOT NULL,
	_timestamp DATETIME NOT NULL,
	_fellowshipCost FLOAT,
	_travelCost FLOAT,
	_runningCost FLOAT,
	_operatingCost FLOAT,
	_equipmentCost FLOAT,
	_conferenceCost FLOAT,
	
	PRIMARY KEY (_reportID),
	FOREIGN KEY (_drisID) REFERENCES PERSON(_systemID)
) ENGINE=InnoDB;

CREATE TABLE APPLICATION (
	_applicationID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_type ENUM('new', 'renewal'),
	_status ENUM('open', 'declined','refereed','finalised','recommended','endorsed','eligible','funded', 'completed', 'terminated'),
	_timestamp DATETIME NOT NULL,
	_finalisationDate DATETIME,
	_eligiblityCheckDate DATETIME,
	_eligiblityChecker CHAR(9),
	_startDate DATE,
	_endDate DATE,
	_projectTitle VARCHAR(250),
	_information TEXT,
	_fellow CHAR(9) NOT NULL,	
	_grantHolderID CHAR(9) NOT NULL,
	_recommendationReportID BIGINT UNSIGNED,
	_endorsementID BIGINT UNSIGNED,
	_fundingReportID BIGINT UNSIGNED,
	
	
	PRIMARY KEY (_applicationID),
	FOREIGN KEY (_fellow) REFERENCES PERSON(_systemID),
	FOREIGN KEY (_grantHolderID) REFERENCES PERSON(_systemID),
	FOREIGN KEY (_eligiblityChecker) REFERENCES PERSON(_systemID),
	FOREIGN KEY (_recommendationReportID) REFERENCES RECOMMENDATION_REPORT(_reportID),
	FOREIGN KEY (_endorsementID) REFERENCES ENDORSEMENT(_endorsementID),
	FOREIGN KEY (_fundingReportID) REFERENCES FUNDING_REPORT(_reportID)
) ENGINE=InnoDB;

CREATE TABLE REFEREE_APPLICATION (
	_refereeID CHAR(9) NOT NULL,
	_applicationID BIGINT UNSIGNED NOT NULL,
	
	PRIMARY KEY (_refereeID, _applicationID),
	FOREIGN KEY (_refereeID) REFERENCES PERSON(_systemID),
	FOREIGN KEY (_applicationID) REFERENCES APPLICATION(_applicationID)
) ENGINE=InnoDB;
#CREATE TABLE NEW_APPLICATIONS (
#	_applicationID BIGINT UNSIGNED NOT NULL,
#	
#	PRIMARY KEY (_applicationID),
#	FOREIGN KEY (_applicationID) REFERENCES APPLICATIONS(_applicationID)
#);

CREATE TABLE REFEREE_REPORT (
	_reportID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_refereeID CHAR(9) NOT NULL,
	_applicationID BIGINT UNSIGNED NOT NULL,
	_timestamp DATETIME NOT NULL,
	_content TEXT NOT NULL,
	
	PRIMARY KEY (_reportID),
	FOREIGN KEY (_applicationID) REFERENCES APPLICATION(_applicationID),
	FOREIGN KEY (_refereeID) REFERENCES PERSON(_systemID)
) ENGINE=InnoDB;

CREATE TABLE PROGRESS_REPORT (
	_reportID BIGINT UNSIGNED AUTO_INCREMENT,
	_applicationID BIGINT UNSIGNED NOT NULL,
	_timestamp DATETIME NOT NULL,
	_content TEXT NOT NULL,
	
	PRIMARY KEY (_reportID),
	FOREIGN KEY (_applicationID) REFERENCES APPLICATION(_applicationID)
) ENGINE=InnoDB;

#CREATE TABLE RENEWAL_APPLICATIONS (
#	_applicationID BIGINT UNSIGNED NOT NULL,
	
#	PRIMARY KEY (_applicationID),
#	FOREIGN KEY (_applicationID) REFERENCES APPLICATIONS(_applicationID)
#);

CREATE TABLE COMMITTEE_MEETING (
	_meetingID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_startDate DATETIME NOT NULL,
	_endDate DATETIME NOT NULL,
	PRIMARY KEY (_meetingID)
) ENGINE=InnoDB;

CREATE TABLE MINUTE_COMMENT (
	_commentID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_meetingID BIGINT UNSIGNED NOT NULL,
	_timestamp DATETIME NOT NULL,
	_attendeeID CHAR(9) NOT NULL,
	_comment VARCHAR(500) NOT NULL,
	
	PRIMARY KEY (_commentID),
	FOREIGN KEY (_meetingID) REFERENCES COMMITTEE_MEETING(_meetingID),
	FOREIGN KEY (_attendeeID) REFERENCES PERSON(_systemID)
) ENGINE=InnoDB;

#CREATE TABLE NEW_APPLICATIONS_COMMITT_MEETINGS (
#	_meetingID BIGINT UNSIGNED NOT NULL,
#	_newApplicationID BIGINT UNSIGNED NOT NULL,
#	
#	PRIMARY KEY (_meetingID, _newApplicationID),
#	FOREIGN KEY (_meetingID) REFERENCES COMMITTEE_MEETINGS(_meetingID),
#	FOREIGN KEY (_newApplicationID) REFERENCES NEW_APPLICATIONS(_applicationID)
#);

#CREATE TABLE RENEWAL_APPLICATIONS_COMMITT_MEETINGS (
#	_meetingID BIGINT UNSIGNED NOT NULL,
#	_renewalApplicationID BIGINT UNSIGNED NOT NULL,
	
#	PRIMARY KEY (_meetingID, _renewalApplicationID),
#	FOREIGN KEY (_meetingID) REFERENCES COMMITTEE_MEETINGS(_meetingID),
#	FOREIGN KEY (_renewalApplicationID) REFERENCES RENEWAL_APPLICATIONS(_applicationID)
#);

CREATE TABLE ATTENDENCE_LIST (	
	_meetingID BIGINT UNSIGNED NOT NULL,
	_attendeeID CHAR(9) NOT NULL,
	
	PRIMARY KEY (_meetingID, _attendeeID),
	FOREIGN KEY (_meetingID) REFERENCES COMMITTEE_MEETING(_meetingID),
	FOREIGN KEY (_attendeeID) REFERENCES PERSON(_systemID)
) ENGINE=InnoDB;

CREATE TABLE COMMITTEE_MEETINGS_APPLICATIONS (
	_meetingID BIGINT UNSIGNED NOT NULL,
	_applicationID BIGINT UNSIGNED NOT NULL,
	
	PRIMARY KEY (_meetingID, _applicationID),
	FOREIGN KEY (_meetingID) REFERENCES COMMITTEE_MEETING(_meetingID),
	FOREIGN KEY (_applicationID) REFERENCES APPLICATION(_applicationID)
) ENGINE=InnoDB;


CREATE TABLE AUDIT_LOG (
	_entryID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_personID CHAR(9) NOT NULL,
	_timestamp DATETIME NOT NULL,
	_action VARCHAR(100) NOT NULL,
	
	PRIMARY KEY (_entryID),
	FOREIGN KEY (_personID) REFERENCES PERSON(_systemID)
) ENGINE=InnoDB;

CREATE TABLE CV (
	_cvID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_ownerID CHAR(9) NOT NULL,
	_idNumber CHAR(20) NOT NULL,
	_dateOfBirth DATE NOT NULL,
	_gender ENUM('male','female'),
	_citizenship CHAR(50),
	_nrfRating CHAR(4),
	_race CHAR(20),
	_recentInstitution VARCHAR(50),
	_researchOutput TEXT,
	_otherContributions TEXT,
	_additionalInformation TEXT,

	PRIMARY KEY(_cvID),
	FOREIGN KEY (_ownerID) REFERENCES PERSON(_systemID)
) ENGINE=InnoDB;

CREATE TABLE ACADEMIC_QUALIFICATION (
	_qualificationID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_cvID BIGINT UNSIGNED NOT NULL,
	_name VARCHAR(100),
	_fieldOfStudy VARCHAR(100),
	_instituation VARCHAR(100),
	_yearObtained DATE,
	_distinctions TINYINT UNSIGNED,

	PRIMARY KEY (_qualificationID),
	FOREIGN KEY (_cvID) REFERENCES CV(_cvID)
) ENGINE=InnoDB;

CREATE TABLE EXPERIENCE (
	_experienceID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_cvID BIGINT UNSIGNED NOT NULL,
	_capacity VARCHAR(100),
	_organisation VARCHAR(100),
	_startDate DATETIME NOT NULL,
	_endDate DATETIME NOT NULL,
	
	PRIMARY KEY (_experienceID),
	FOREIGN KEY (_cvID) REFERENCES CV(_cvID)
) ENGINE=InnoDB;


INSERT INTO SECURITY_ROLE (_name, _roleMask) VALUES("Prospective fellow",0), 
								("Referee",1),
								("Research fellow",2),
								("Grant holder",3),
								("HOD", 4),
								("Dean's office member",5),
								("DRIS member", 6), 
								("Post doctoral Committee member",7), 
								("System administrator",8);









