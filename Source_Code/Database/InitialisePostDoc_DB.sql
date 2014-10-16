
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
	_password VARCHAR(130) NOT NULL,
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
	_accountStatus VARCHAR(40),
	
	
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
	_institutionAssignedEmail  VARCHAR(50),
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
	_emailStatus VARCHAR(40),
	_emailRetryCount INT,
	_timestamp DATETIME NOT NULL,
	_sender CHAR(9),
	_reciever	CHAR(9) NOT NULL,
	
	PRIMARY KEY (_notificationID),
	FOREIGN KEY (_sender) REFERENCES person(_systemID),
	FOREIGN KEY (_reciever) REFERENCES person(_systemID)
) ENGINE=InnoDB;



CREATE TABLE application (
    _applicationID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    _imported BOOLEAN, 
	_type VARCHAR(40),
    _status VARCHAR(40),
	_fundingType VARCHAR(40),
    _timestamp DATETIME NOT NULL,
	_submissionDate DATETIME,
    _finalisationDate DATETIME,    
    _startDate DATE,
    _endDate DATE,
    _projectTitle VARCHAR(250),
    _information TEXT,
    _fellow CHAR(9) NOT NULL,
    _grantHolder CHAR(9),
	_parentApplication BIGINT UNSIGNED,
    PRIMARY KEY (_applicationID),
    FOREIGN KEY (_fellow) REFERENCES person(_systemID),
    FOREIGN KEY (_grantHolder) REFERENCES person(_systemID),
	FOREIGN KEY (_parentApplication) REFERENCES application(_applicationID)
)  ENGINE=InnoDB;

CREATE TABLE application_review_request (
	_person CHAR(9) NOT NULL,
	_application BIGINT UNSIGNED NOT NULL,
	_type VARCHAR(40),

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
	_type VARCHAR(40),

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
	_type VARCHAR(40) NOT NULL,
	_reason TEXT,
	_fromStatus VARCHAR(40),
	_toStatus VARCHAR(40),

	PRIMARY KEY (_reportID),
	FOREIGN KEY (_application) REFERENCES application(_applicationID),
	FOREIGN KEY (_dris) REFERENCES person(_systemID)
) ENGINE=InnoDB;

CREATE TABLE committee_meeting (
	_meetingID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_organiser CHAR(9) NOT NULL,
	_name VARCHAR(100),
	_venue VARCHAR(100),
	_startDate DATETIME,
	_endDate DATETIME,
	
	PRIMARY KEY (_meetingID),
	FOREIGN KEY (_organiser) REFERENCES person(_systemID)
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
	_action VARCHAR(500) NOT NULL,
	
	PRIMARY KEY (_entryID),
	FOREIGN KEY (_person) REFERENCES person(_systemID)
) ENGINE=InnoDB;

CREATE TABLE cv (
	_cvID CHAR(9) NOT NULL,
	_idNumber CHAR(30) NOT NULL,
	_dateOfBirth DATE NOT NULL,
	_gender VARCHAR(40),
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

CREATE TABLE announcement (
	_announcementID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_timestamp DATETIME NOT NULL,
	_headline VARCHAR(100),
	_message TEXT NOT NULL,
	_startDate DATETIME,
	_endDate DATETIME,
	_image BLOB,

	PRIMARY KEY (_announcementID)
	
) ENGINE=InnoDB;

CREATE TABLE neural_network (
	_neuralnetworkID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_timestamp DATETIME NOT NULL,
	_defaultNetwork BOOLEAN,
	_name VARCHAR(100),	
	_type VARCHAR(100),
	_learningRate DOUBLE,
	_momentum DOUBLE,
	_bias_threshold DOUBLE,
	_smoothingParameterT DOUBLE,
	_lowerCertaintyBound DOUBLE,
	_upperCertaintyBound DOUBLE,

	PRIMARY KEY (_neuralnetworkID)
	
) ENGINE=InnoDB;

CREATE TABLE neuron (
	_neuronID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_neuralnetwork BIGINT UNSIGNED NOT NULL,
	_neuronOrderIndex BIGINT UNSIGNED,
	_value DOUBLE,
	_error DOUBLE,
	_biasNeuron BOOLEAN,

	PRIMARY KEY (_neuronID),
	FOREIGN KEY (_neuralnetwork) REFERENCES neural_network(_neuralnetworkID)
) ENGINE=InnoDB;

CREATE TABLE synapse (
	_synapseID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_neuralnetwork BIGINT UNSIGNED NOT NULL,
	_srcNeuron BIGINT UNSIGNED NOT NULL,
	_destNeuron BIGINT UNSIGNED NOT NULL,
	_weight DOUBLE,
	_previousWeightChange DOUBLE,

	PRIMARY KEY (_synapseID),
	FOREIGN KEY (_neuralnetwork) REFERENCES neural_network(_neuralnetworkID),
	FOREIGN KEY (_srcNeuron) REFERENCES neuron(_neuronID),
	FOREIGN KEY (_destNeuron) REFERENCES neuron(_neuronID)
) ENGINE=InnoDB;

CREATE TABLE pre_post_condition_method (
	_prePostConditionMethodID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	_methodName VARCHAR(250),	
	_methodClassName VARCHAR(250),
	_methodParameters TEXT,
	_scriptLangName VARCHAR(250),
	_preConditionScript TEXT,
	_postConditionScript TEXT,
	
	PRIMARY KEY (_prePostConditionMethodID)
	
) ENGINE=InnoDB;

CREATE TABLE resourceentity (
	il8n_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	i18n_key VARCHAR(250) NOT NULL,
	i18n_value VARCHAR(250) NOT NULL,
	i18n_locale VARCHAR(250) NOT NULL,
	
	PRIMARY KEY (il8n_id)
) ENGINE=InnoDB;

INSERT INTO PostDoc_DB.resourceentity (i18n_key, i18n_value, i18n_locale) VALUES ('welcome.title', 'You must know', 'en'), ('welcome.name', 'Master', 'en'), ('welcome.db', 'PostDoc', 'en'), ('welcome.language', 'English', 'en');

INSERT INTO PostDoc_DB.security_role (_name, _roleMask) VALUES("Prospective fellow",0), 
															("Referee",1),
															("Research fellow",2),
															("Grant holder",3),
															("HOD", 4),
															("Dean's office member",5),
															("DRIS member", 6), 
															("Post doctoral Committee member",7), 
															("System administrator",8);

INSERT INTO PostDoc_DB.institution (_name) VALUES ('University of Pretoria'),
												('University of Cape Town');

INSERT INTO PostDoc_DB.faculty (_institution,_name) VALUES (1,'EBIT'),
												(1,'Humanities'),
												(2,'Health sciences');

INSERT INTO PostDoc_DB.department (_faculty,_name) VALUES (1,'Computer Science'),
												(1,'Informatics'),
												(1,'Computer and Electronic Engineers'),
												(2,'Philosophy'),
												(3,'Dean');

INSERT INTO PostDoc_DB.address (_country) VALUES("TestMainia");
INSERT INTO PostDoc_DB.address (_country) VALUES("TestUniversity");

INSERT INTO PostDoc_DB.person (_systemID,_fullName,_password,_title,_surname,_email,_addressLine1,_UpEmployee,_accountStatus) VALUES ('u12019837','Test','ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff','Mr','Tester','test@gmail.com',1,true, 'Active');
INSERT INTO PostDoc_DB.employee_information (_employeeID, _physicalAddress, _position, _dateOfAppointment, _appointmentStatus, _department) VALUES('u12019837',2,'HOD','2001/01/20','full time',1);
/*
INSERT INTO PostDoc_DB.person_security_role (_personID, _roleID) VALUES ('u12019837',1),
																		('u12019837',2),
																		('u12019837',3),
																		('u12019837',4),
																		('u12019837',5),
																		('u12019837',6),
																		('u12019837',7),
																		('u12019837',8),
																		('u12019837',9);

*/
USE PostDoc_Archive_DB;

CREATE TABLE address  LIKE PostDoc_DB.address;

CREATE TABLE institution  LIKE PostDoc_DB.institution;

CREATE TABLE faculty  LIKE PostDoc_DB.faculty;

CREATE TABLE department  LIKE PostDoc_DB.department;

CREATE TABLE person  LIKE PostDoc_DB.person;

CREATE TABLE employee_information  LIKE PostDoc_DB.employee_information;

CREATE TABLE research_fellow_information  LIKE PostDoc_DB.research_fellow_information;

CREATE TABLE security_role  LIKE PostDoc_DB.security_role;

CREATE TABLE person_security_role  LIKE PostDoc_DB.person_security_role;

CREATE TABLE notification  LIKE PostDoc_DB.notification;

CREATE TABLE application  LIKE PostDoc_DB.application;

CREATE TABLE eligiblity_report  LIKE PostDoc_DB.eligiblity_report;

CREATE TABLE decline_report  LIKE PostDoc_DB.decline_report;

CREATE TABLE ammend_request  LIKE PostDoc_DB.ammend_request;

CREATE TABLE recommendation_report  LIKE PostDoc_DB.recommendation_report;

CREATE TABLE endorsement  LIKE PostDoc_DB.endorsement;

CREATE TABLE funding_report  LIKE PostDoc_DB.funding_report;

CREATE TABLE funding_cost  LIKE PostDoc_DB.funding_cost;

CREATE TABLE referee_application  LIKE PostDoc_DB.referee_application;

CREATE TABLE referee_report  LIKE PostDoc_DB.referee_report;

CREATE TABLE progress_report  LIKE PostDoc_DB.progress_report;

CREATE TABLE forward_and_rewind_report  LIKE PostDoc_DB.forward_and_rewind_report;

CREATE TABLE committee_meeting  LIKE PostDoc_DB.committee_meeting;

CREATE TABLE minute_comment  LIKE PostDoc_DB.minute_comment;

CREATE TABLE attendence_list  LIKE PostDoc_DB.attendence_list;

CREATE TABLE committee_meetings_applications  LIKE PostDoc_DB.committee_meetings_applications;

CREATE TABLE audit_log  LIKE PostDoc_DB.audit_log;

CREATE TABLE cv  LIKE PostDoc_DB.cv;

CREATE TABLE academic_qualification  LIKE PostDoc_DB.academic_qualification;

CREATE TABLE experience LIKE PostDoc_DB.experience;

CREATE TABLE announcement LIKE PostDoc_DB.announcement;

CREATE TABLE neuralnetwork LIKE PostDoc_DB.neural_network;

CREATE TABLE synapse LIKE PostDoc_DB.synapse;

CREATE TABLE neuron LIKE PostDoc_DB.neuron;

CREATE TABLE pre_post_condition_method LIKE PostDoc_DB.pre_post_condition_method;

USE PostDoc_BackUp_DB;

CREATE TABLE address  LIKE PostDoc_DB.address;

CREATE TABLE institution  LIKE PostDoc_DB.institution;

CREATE TABLE faculty  LIKE PostDoc_DB.faculty;

CREATE TABLE department  LIKE PostDoc_DB.department;

CREATE TABLE person  LIKE PostDoc_DB.person;

CREATE TABLE employee_information  LIKE PostDoc_DB.employee_information;

CREATE TABLE research_fellow_information  LIKE PostDoc_DB.research_fellow_information;

CREATE TABLE security_role  LIKE PostDoc_DB.security_role;

CREATE TABLE person_security_role  LIKE PostDoc_DB.person_security_role;

CREATE TABLE notification  LIKE PostDoc_DB.notification;

CREATE TABLE application  LIKE PostDoc_DB.application;

CREATE TABLE eligiblity_report  LIKE PostDoc_DB.eligiblity_report;

CREATE TABLE decline_report  LIKE PostDoc_DB.decline_report;

CREATE TABLE ammend_request  LIKE PostDoc_DB.ammend_request;

CREATE TABLE recommendation_report  LIKE PostDoc_DB.recommendation_report;

CREATE TABLE endorsement  LIKE PostDoc_DB.endorsement;

CREATE TABLE funding_report  LIKE PostDoc_DB.funding_report;

CREATE TABLE funding_cost  LIKE PostDoc_DB.funding_cost;

CREATE TABLE referee_application  LIKE PostDoc_DB.referee_application;

CREATE TABLE referee_report  LIKE PostDoc_DB.referee_report;

CREATE TABLE progress_report  LIKE PostDoc_DB.progress_report;

CREATE TABLE forward_and_rewind_report  LIKE PostDoc_DB.forward_and_rewind_report;

CREATE TABLE committee_meeting  LIKE PostDoc_DB.committee_meeting;

CREATE TABLE minute_comment  LIKE PostDoc_DB.minute_comment;

CREATE TABLE attendence_list  LIKE PostDoc_DB.attendence_list;

CREATE TABLE committee_meetings_applications  LIKE PostDoc_DB.committee_meetings_applications;

CREATE TABLE audit_log  LIKE PostDoc_DB.audit_log;

CREATE TABLE cv  LIKE PostDoc_DB.cv;

CREATE TABLE academic_qualification  LIKE PostDoc_DB.academic_qualification;

CREATE TABLE experience LIKE PostDoc_DB.experience;

CREATE TABLE announcement LIKE PostDoc_DB.announcement;

CREATE TABLE neuralnetwork LIKE PostDoc_DB.neural_network;

CREATE TABLE synapse LIKE PostDoc_DB.synapse;

CREATE TABLE neuron LIKE PostDoc_DB.neuron;

CREATE TABLE pre_post_condition_method LIKE PostDoc_DB.pre_post_condition_method;

