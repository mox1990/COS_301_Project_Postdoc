-- MySQL dump 10.15  Distrib 10.0.13-MariaDB, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: PostDoc_DB
-- ------------------------------------------------------
-- Server version	10.0.13-MariaDB-1~trusty

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `academic_qualification`
--

DROP TABLE IF EXISTS `academic_qualification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `academic_qualification` (
  `_qualificationID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_cv` char(9) NOT NULL,
  `_name` varchar(100) DEFAULT NULL,
  `_fieldOfStudy` varchar(100) DEFAULT NULL,
  `_institution` varchar(100) DEFAULT NULL,
  `_yearObtained` date DEFAULT NULL,
  `_distinctions` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`_qualificationID`),
  KEY `_cv` (`_cv`),
  CONSTRAINT `academic_qualification_ibfk_1` FOREIGN KEY (`_cv`) REFERENCES `cv` (`_cvID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academic_qualification`
--

LOCK TABLES `academic_qualification` WRITE;
/*!40000 ALTER TABLE `academic_qualification` DISABLE KEYS */;
INSERT INTO `academic_qualification` VALUES (1,'f14000001','PhD','Open','Open','2014-09-02',0),(2,'f14000002','PhD','Referred','Open','2014-09-08',0),(3,'f14000003','PhD','Finalised','Open','2006-09-12',0),(4,'u12019837','PhD','Grant Holding','Testing','2006-09-19',6),(5,'f14000004','PhD','Open','Open','1994-09-10',0),(6,'f14000005','PhD','Endorsement','HarKnox','2014-09-02',0),(7,'f14000006','PhD','Work','HardKnox','2013-09-10',0),(8,'f14000007','PhD','Funds','Funds','2014-09-09',0),(9,'f14000008','PhD','Complete','Complete','2014-09-17',0),(10,'f14000009','PhD','Terminate','Term','2014-09-03',0);
/*!40000 ALTER TABLE `academic_qualification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `_addressID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_country` varchar(100) DEFAULT NULL,
  `_province` varchar(100) DEFAULT NULL,
  `_town_city` varchar(100) DEFAULT NULL,
  `_suburb` varchar(100) DEFAULT NULL,
  `_street` varchar(100) DEFAULT NULL,
  `_streeNumber` int(11) DEFAULT NULL,
  `_roomNumber` varchar(50) DEFAULT NULL,
  `_zip_postalCode` char(6) DEFAULT NULL,
  PRIMARY KEY (`_addressID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'TestMainia',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'TestUniversity',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(4,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(5,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(6,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(7,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(8,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(9,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(10,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(11,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ammend_request`
--

DROP TABLE IF EXISTS `ammend_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ammend_request` (
  `_requestID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_application` bigint(20) unsigned NOT NULL,
  `_creator` char(9) NOT NULL,
  `_timestamp` datetime NOT NULL,
  `_request` text,
  PRIMARY KEY (`_requestID`),
  KEY `_application` (`_application`),
  KEY `_creator` (`_creator`),
  CONSTRAINT `ammend_request_ibfk_1` FOREIGN KEY (`_application`) REFERENCES `application` (`_applicationID`),
  CONSTRAINT `ammend_request_ibfk_2` FOREIGN KEY (`_creator`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ammend_request`
--

LOCK TABLES `ammend_request` WRITE;
/*!40000 ALTER TABLE `ammend_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `ammend_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcement` (
  `_announcementID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_timestamp` datetime NOT NULL,
  `_message` text NOT NULL,
  `_startDate` datetime DEFAULT NULL,
  `_endDate` datetime DEFAULT NULL,
  PRIMARY KEY (`_announcementID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
/*!40000 ALTER TABLE `announcement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `_applicationID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_type` enum('New','Renewal') DEFAULT NULL,
  `_status` enum('Open','Submitted','Declined','Referred','Finalised','Recommended','Endorsed','Eligible','Funded','Completed','Terminated') DEFAULT NULL,
  `_fundingType` enum('UP PhD Postdoc','UP Postdoc','Externally funded') DEFAULT NULL,
  `_timestamp` datetime NOT NULL,
  `_submissionDate` datetime DEFAULT NULL,
  `_finalisationDate` datetime DEFAULT NULL,
  `_startDate` date DEFAULT NULL,
  `_endDate` date DEFAULT NULL,
  `_projectTitle` varchar(250) DEFAULT NULL,
  `_information` text,
  `_fellow` char(9) NOT NULL,
  `_grantHolder` char(9) DEFAULT NULL,
  `_parentApplication` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`_applicationID`),
  KEY `_fellow` (`_fellow`),
  KEY `_grantHolder` (`_grantHolder`),
  KEY `_parentApplication` (`_parentApplication`),
  CONSTRAINT `application_ibfk_1` FOREIGN KEY (`_fellow`) REFERENCES `person` (`_systemID`),
  CONSTRAINT `application_ibfk_2` FOREIGN KEY (`_grantHolder`) REFERENCES `person` (`_systemID`),
  CONSTRAINT `application_ibfk_3` FOREIGN KEY (`_parentApplication`) REFERENCES `application` (`_applicationID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (1,'New','Submitted','UP Postdoc','2014-09-21 20:35:18','2014-09-21 20:36:35',NULL,NULL,NULL,'Open Test','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Staying alive</projectMotivation><projectAims><aim>Getting tested</aim></projectAims><researchMethodology>Tested</researchMethodology><researchWorkPlan>Testing</researchWorkPlan><expectedOutcomes><outcome>Tried and tested</outcome></expectedOutcomes><projectInfrastructureFunding>Needed</projectInfrastructureFunding><teamMembers><member><title>Ms.</title><fullName>Closed</fullName><surname>Tester</surname><position>TeamLeader</position><institution>Chilled</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Alive</selfEvaluation></applicationInformation>','f14000001','u12019837',NULL),(2,'New','Referred','UP PhD Postdoc','2014-09-21 20:42:48','2014-09-21 20:42:57',NULL,NULL,NULL,'Referred test','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>staying alive</projectMotivation><projectAims><aim>Getting referred</aim></projectAims><researchMethodology>referring to?</researchMethodology><researchWorkPlan>Getting it done</researchWorkPlan><expectedOutcomes><outcome>getting referred</outcome></expectedOutcomes><projectInfrastructureFunding>needed</projectInfrastructureFunding><teamMembers><member><title>Miss.</title><fullName>Closed</fullName><surname>Tester</surname><position>TeamLeader</position><institution>Chilled</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Alive</selfEvaluation></applicationInformation>','f14000002','u12019837',NULL),(3,'New','Finalised','UP PhD Postdoc','2014-09-21 21:00:04','2014-09-21 21:00:12','2014-09-21 21:05:25',NULL,NULL,'Finalised Test','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>alive</projectMotivation><projectAims><aim>The Sky</aim></projectAims><researchMethodology>finally</researchMethodology><researchWorkPlan>FINAL</researchWorkPlan><expectedOutcomes><outcome>The Floor</outcome></expectedOutcomes><projectInfrastructureFunding>needed</projectInfrastructureFunding><teamMembers><member><title>Miss.</title><fullName>Closed</fullName><surname>Tester</surname><position>TeamLeader</position><institution>Chilled</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>alive</selfEvaluation></applicationInformation>','f14000003','u12019837',NULL),(4,'New','Finalised','UP PhD Postdoc','2014-09-21 21:15:31','2014-09-21 21:15:38','2014-09-21 21:16:22',NULL,NULL,'Recommended','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>reqwg</projectMotivation><projectAims><aim>recommended</aim></projectAims><researchMethodology>rec</researchMethodology><researchWorkPlan>rec</researchWorkPlan><expectedOutcomes><outcome>recommended</outcome></expectedOutcomes><projectInfrastructureFunding>rec</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Closed</fullName><surname>Tester</surname><position>TeamLeader</position><institution>Chilled</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>rec</selfEvaluation></applicationInformation>','f14000004','u12019837',NULL),(5,'New','Referred','UP PhD Postdoc','2014-09-26 02:08:54','2014-09-26 02:09:15',NULL,NULL,NULL,'Endorsement Test','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Staying Alive</projectMotivation><projectAims><aim>The Sky</aim></projectAims><researchMethodology>Endorsing it</researchMethodology><researchWorkPlan>Endorsing everything</researchWorkPlan><expectedOutcomes><outcome>The stars</outcome></expectedOutcomes><projectInfrastructureFunding>Livid</projectInfrastructureFunding><teamMembers><member><title>Ms.</title><fullName>Closed</fullName><surname>Tester</surname><position>Closer</position><institution>Work</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Alive</selfEvaluation></applicationInformation>','f14000005','u12019837',NULL),(6,'New','Referred','UP PhD Postdoc','2014-09-26 02:18:45','2014-09-26 02:18:59',NULL,NULL,NULL,'Eligible Test','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Getting it done</projectMotivation><projectAims><aim>High</aim></projectAims><researchMethodology>Meth? Not even once.</researchMethodology><researchWorkPlan>Here </researchWorkPlan><expectedOutcomes><outcome>Low</outcome></expectedOutcomes><projectInfrastructureFunding>There</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Closed</fullName><surname>Tester</surname><position>There</position><institution>Here</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Everywhere</selfEvaluation></applicationInformation>','f14000006','u12019837',NULL),(7,'New','Referred','UP PhD Postdoc','2014-09-26 02:28:11','2014-09-26 02:28:28',NULL,NULL,NULL,'Funded Test','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Doing it big</projectMotivation><projectAims><aim>Slim</aim></projectAims><researchMethodology>Searched</researchMethodology><researchWorkPlan>Asked</researchWorkPlan><expectedOutcomes><outcome>Plan</outcome></expectedOutcomes><projectInfrastructureFunding>Funds required</projectInfrastructureFunding><teamMembers><member><title>Ms.</title><fullName>Closed</fullName><surname>Tester</surname><position>There</position><institution>Here</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Capable</selfEvaluation></applicationInformation>','f14000007','u12019837',NULL),(8,'New','Referred','UP PhD Postdoc','2014-09-26 02:36:12','2014-09-26 02:36:25',NULL,NULL,NULL,'Complete Test','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Staying alive</projectMotivation><projectAims><aim>Soar</aim></projectAims><researchMethodology>Completeness Thoerem</researchMethodology><researchWorkPlan>D\'oh</researchWorkPlan><expectedOutcomes><outcome>Sour</outcome></expectedOutcomes><projectInfrastructureFunding>Completing it</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Closed</fullName><surname>Tester</surname><position>There</position><institution>Here</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Alive </selfEvaluation></applicationInformation>','f14000008','u12019837',NULL),(9,'New','Referred','UP PhD Postdoc','2014-09-26 02:43:22','2014-09-26 02:43:34',NULL,NULL,NULL,'Terminated Test','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Gate</projectMotivation><projectAims><aim>Stay up</aim></projectAims><researchMethodology>Chilled</researchMethodology><researchWorkPlan>No substances</researchWorkPlan><expectedOutcomes><outcome>Stay... up</outcome></expectedOutcomes><projectInfrastructureFunding>For real</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Closed</fullName><surname>Tester</surname><position>There</position><institution>Here</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Why</selfEvaluation></applicationInformation>','f14000009','u12019837',NULL);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_review_request`
--

DROP TABLE IF EXISTS `application_review_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_review_request` (
  `_person` char(9) NOT NULL,
  `_application` bigint(20) unsigned NOT NULL,
  `_type` enum('HOD','DEAN') NOT NULL DEFAULT 'HOD',
  PRIMARY KEY (`_person`,`_application`,`_type`),
  KEY `_application` (`_application`),
  CONSTRAINT `application_review_request_ibfk_1` FOREIGN KEY (`_person`) REFERENCES `person` (`_systemID`),
  CONSTRAINT `application_review_request_ibfk_2` FOREIGN KEY (`_application`) REFERENCES `application` (`_applicationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_review_request`
--

LOCK TABLES `application_review_request` WRITE;
/*!40000 ALTER TABLE `application_review_request` DISABLE KEYS */;
INSERT INTO `application_review_request` VALUES ('u12019837',3,'HOD'),('u12019837',4,'HOD');
/*!40000 ALTER TABLE `application_review_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendence_list`
--

DROP TABLE IF EXISTS `attendence_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendence_list` (
  `_meetingID` bigint(20) unsigned NOT NULL,
  `_attendeeID` char(9) NOT NULL,
  PRIMARY KEY (`_meetingID`,`_attendeeID`),
  KEY `_attendeeID` (`_attendeeID`),
  CONSTRAINT `attendence_list_ibfk_1` FOREIGN KEY (`_meetingID`) REFERENCES `committee_meeting` (`_meetingID`),
  CONSTRAINT `attendence_list_ibfk_2` FOREIGN KEY (`_attendeeID`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendence_list`
--

LOCK TABLES `attendence_list` WRITE;
/*!40000 ALTER TABLE `attendence_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendence_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_log`
--

DROP TABLE IF EXISTS `audit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit_log` (
  `_entryID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_person` char(9) NOT NULL,
  `_timestamp` datetime NOT NULL,
  `_action` varchar(500) NOT NULL,
  PRIMARY KEY (`_entryID`),
  KEY `_person` (`_person`),
  CONSTRAINT `audit_log_ibfk_1` FOREIGN KEY (`_person`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB AUTO_INCREMENT=461 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_log`
--

LOCK TABLES `audit_log` WRITE;
/*!40000 ALTER TABLE `audit_log` DISABLE KEYS */;
INSERT INTO `audit_log` VALUES (1,'u12019837','2014-09-21 20:30:43',' [ Method = login ] [ Parameters: com.softserve.system.Session@b72e38; ]'),(2,'u12019837','2014-09-21 20:30:43',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@74ef4e; ]'),(3,'u12019837','2014-09-21 20:30:43',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@15de68; ]'),(4,'u12019837','2014-09-21 20:30:43',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@17d35cd; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(5,'u12019837','2014-09-21 20:30:43',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@e15eb7; ]'),(6,'u12019837','2014-09-21 20:30:47',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@29d73c; ]'),(7,'u12019837','2014-09-21 20:30:47',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@cbf769; ]'),(8,'u12019837','2014-09-21 20:30:47',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1e7252f; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(9,'u12019837','2014-09-21 20:30:47',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@9da5d4; ]'),(10,'u12019837','2014-09-21 20:30:47',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.system.Session@1ec3c1c; ]'),(11,'u12019837','2014-09-21 20:30:49',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1a8311a; ]'),(12,'u12019837','2014-09-21 20:30:49',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1b7c109; ]'),(13,'u12019837','2014-09-21 20:30:49',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@cc71b2; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(14,'u12019837','2014-09-21 20:30:49',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@646480; ]'),(15,'u12019837','2014-09-21 20:30:55',' [ Method = logout ] [ Parameters: com.softserve.system.Session@f8683; ]'),(16,'f14000001','2014-09-21 20:31:45','Created new user account'),(17,'f14000001','2014-09-21 20:31:45',' [ Method = login ] [ Parameters: com.softserve.system.Session@317ce; ]'),(18,'f14000001','2014-09-21 20:31:52',' [ Method = getOpenApplication ] [ Parameters: com.softserve.system.Session@1f7b04a; ]'),(19,'f14000001','2014-09-21 20:34:03',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.system.Session@149bc4f; com.softserve.DBEntities.Cv[ cvID=f14000001 ]; ]'),(20,'f14000001','2014-09-21 20:34:03','Created CV [ Method = createCV ] [ Parameters: com.softserve.system.Session@149bc4f; com.softserve.DBEntities.Cv[ cvID=f14000001 ]; ]'),(21,'f14000001','2014-09-21 20:34:03','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.system.Session@149bc4f; com.softserve.DBEntities.Cv[ cvID=f14000001 ]; ]'),(22,'f14000001','2014-09-21 20:35:19','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.system.Session@425efd; com.softserve.DBEntities.Application[ applicationID=1 ]; ]'),(23,'f14000001','2014-09-21 20:35:32','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.system.Session@1130992; com.softserve.DBEntities.Application[ applicationID=1 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(24,'f14000001','2014-09-21 20:36:30','Linked referee to new application [ Method = linkRefereeToApplication ] [ Parameters: com.softserve.system.Session@176ba92; com.softserve.DBEntities.Application[ applicationID=1 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(25,'f14000001','2014-09-21 20:36:30','Linked referee to new application [ Method = linkRefereeToApplication ] [ Parameters: com.softserve.system.Session@de6d91; com.softserve.DBEntities.Application[ applicationID=1 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(26,'f14000001','2014-09-21 20:36:35','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.system.Session@11fcbef; com.softserve.DBEntities.Application[ applicationID=1 ]; ]'),(27,'f14000001','2014-09-21 20:36:54',' [ Method = logout ] [ Parameters: com.softserve.system.Session@9cec11; ]'),(28,'u12019837','2014-09-21 20:37:00',' [ Method = login ] [ Parameters: com.softserve.system.Session@1a389e2; ]'),(29,'u12019837','2014-09-21 20:37:01',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@188c817; ]'),(30,'u12019837','2014-09-21 20:37:01',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1a153c0; ]'),(31,'u12019837','2014-09-21 20:37:01',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@7e3211; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(32,'u12019837','2014-09-21 20:37:01',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@18f6fdd; ]'),(33,'u12019837','2014-09-21 20:37:08',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1561dba; ]'),(34,'u12019837','2014-09-21 20:37:08',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@bd7dea; ]'),(35,'u12019837','2014-09-21 20:37:08',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@233edc; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(36,'u12019837','2014-09-21 20:37:08',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@9c5733; ]'),(37,'u12019837','2014-09-21 20:37:11',' [ Method = getAllApplications ] [ Parameters: com.softserve.system.Session@16d3eba; ]'),(38,'u12019837','2014-09-21 20:37:11',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@8d1a4e; ]'),(39,'u12019837','2014-09-21 20:37:11',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1d5afc0; ]'),(40,'u12019837','2014-09-21 20:37:11',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@cd097c; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(41,'u12019837','2014-09-21 20:37:11',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@15bb31e; ]'),(42,'u12019837','2014-09-21 20:37:57',' [ Method = logout ] [ Parameters: com.softserve.system.Session@1f287b7; ]'),(43,'f14000002','2014-09-21 20:38:53','Created new user account'),(44,'f14000002','2014-09-21 20:38:53',' [ Method = login ] [ Parameters: com.softserve.system.Session@1b41f5; ]'),(45,'f14000002','2014-09-21 20:39:20',' [ Method = getOpenApplication ] [ Parameters: com.softserve.system.Session@bd0ad4; ]'),(46,'f14000002','2014-09-21 20:41:04',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.system.Session@6b0d88; com.softserve.DBEntities.Cv[ cvID=f14000002 ]; ]'),(47,'f14000002','2014-09-21 20:41:04','Created CV [ Method = createCV ] [ Parameters: com.softserve.system.Session@6b0d88; com.softserve.DBEntities.Cv[ cvID=f14000002 ]; ]'),(48,'f14000002','2014-09-21 20:41:04','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.system.Session@6b0d88; com.softserve.DBEntities.Cv[ cvID=f14000002 ]; ]'),(49,'f14000002','2014-09-21 20:42:48','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.system.Session@dfe17c; com.softserve.DBEntities.Application[ applicationID=2 ]; ]'),(50,'f14000002','2014-09-21 20:42:54','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.system.Session@158c60b; com.softserve.DBEntities.Application[ applicationID=2 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(51,'f14000002','2014-09-21 20:42:57','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.system.Session@1e89e77; com.softserve.DBEntities.Application[ applicationID=2 ]; ]'),(52,'f14000002','2014-09-21 20:45:45',' [ Method = logout ] [ Parameters: com.softserve.system.Session@cc186; ]'),(53,'u12019837','2014-09-21 20:45:50',' [ Method = login ] [ Parameters: com.softserve.system.Session@1c509f6; ]'),(54,'u12019837','2014-09-21 20:45:50',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@13f2fe8; ]'),(55,'u12019837','2014-09-21 20:45:51',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@f56e83; ]'),(56,'u12019837','2014-09-21 20:45:51',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@17ac022; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(57,'u12019837','2014-09-21 20:45:51',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@ee9288; ]'),(58,'u12019837','2014-09-21 20:45:52',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@b3d496; ]'),(59,'u12019837','2014-09-21 20:45:52',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1e85ad0; ]'),(60,'u12019837','2014-09-21 20:45:52',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@13422c6; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(61,'u12019837','2014-09-21 20:45:53',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1a35909; ]'),(62,'u12019837','2014-09-21 20:48:18',' [ Method = getAllApplications ] [ Parameters: com.softserve.system.Session@1d2d9a3; ]'),(63,'u12019837','2014-09-21 20:48:18',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@afd0d8; ]'),(64,'u12019837','2014-09-21 20:48:18',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@101887; ]'),(65,'u12019837','2014-09-21 20:48:18',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@19f9b62; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(66,'u12019837','2014-09-21 20:48:18',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@16bc6d7; ]'),(67,'f14000003','2014-09-21 20:55:54','Created new user account'),(68,'f14000003','2014-09-21 20:55:54',' [ Method = login ] [ Parameters: com.softserve.system.Session@10cd191; ]'),(69,'f14000003','2014-09-21 20:56:05',' [ Method = getOpenApplication ] [ Parameters: com.softserve.system.Session@10e3072; ]'),(70,'f14000003','2014-09-21 20:57:58',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.system.Session@e06ddb; com.softserve.DBEntities.Cv[ cvID=f14000003 ]; ]'),(71,'f14000003','2014-09-21 20:57:59','Created CV [ Method = createCV ] [ Parameters: com.softserve.system.Session@e06ddb; com.softserve.DBEntities.Cv[ cvID=f14000003 ]; ]'),(72,'f14000003','2014-09-21 20:57:59','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.system.Session@e06ddb; com.softserve.DBEntities.Cv[ cvID=f14000003 ]; ]'),(73,'f14000003','2014-09-21 21:00:04','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.system.Session@2efe0c; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(74,'f14000003','2014-09-21 21:00:09','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.system.Session@1c8a59; com.softserve.DBEntities.Application[ applicationID=3 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(75,'f14000003','2014-09-21 21:00:12','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.system.Session@15a6bf6; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(76,'u12019837','2014-09-21 21:02:44',' [ Method = login ] [ Parameters: com.softserve.system.Session@520f1e; ]'),(77,'u12019837','2014-09-21 21:02:44',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1a15c19; ]'),(78,'u12019837','2014-09-21 21:02:44',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@14f0165; ]'),(79,'u12019837','2014-09-21 21:02:44',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@7f3c45; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(80,'u12019837','2014-09-21 21:02:44',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@5e1d45; ]'),(81,'u12019837','2014-09-21 21:02:47',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@79fbea; ]'),(82,'u12019837','2014-09-21 21:02:48',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@da276e; ]'),(83,'u12019837','2014-09-21 21:02:48',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@9f861b; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(84,'u12019837','2014-09-21 21:02:48',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@193b414; ]'),(85,'u12019837','2014-09-21 21:02:50',' [ Method = getAllApplications ] [ Parameters: com.softserve.system.Session@fcab87; ]'),(86,'u12019837','2014-09-21 21:02:50',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1c0215a; ]'),(87,'u12019837','2014-09-21 21:02:50',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1daf7e9; ]'),(88,'u12019837','2014-09-21 21:02:50',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1fec67; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(89,'u12019837','2014-09-21 21:02:50',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@19e454b; ]'),(90,'u12019837','2014-09-21 21:03:05',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1e14895; ]'),(91,'u12019837','2014-09-21 21:03:06',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@b1ff3d; ]'),(92,'u12019837','2014-09-21 21:03:06',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@d99ea5; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(93,'u12019837','2014-09-21 21:03:06',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@fa9fac; ]'),(94,'u12019837','2014-09-21 21:03:06',' [ Method = getApplicationProgress ] [ Parameters: com.softserve.system.Session@1d1ce41; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(95,'u12019837','2014-09-21 21:03:22',' [ Method = getApplicationProgress ] [ Parameters: com.softserve.system.Session@72b21c; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(96,'u12019837','2014-09-21 21:03:22',' [ Method = getApplicationProgress ] [ Parameters: com.softserve.system.Session@acc3e9; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(97,'u12019837','2014-09-21 21:03:22',' [ Method = getApplicationProgress ] [ Parameters: com.softserve.system.Session@4b148a; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(98,'u12019837','2014-09-21 21:03:22',' [ Method = getApplicationProgress ] [ Parameters: com.softserve.system.Session@74cd76; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(99,'u12019837','2014-09-21 21:03:22',' [ Method = getApplicationProgress ] [ Parameters: com.softserve.system.Session@1214280; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(100,'u12019837','2014-09-21 21:03:22',' [ Method = getApplicationProgress ] [ Parameters: com.softserve.system.Session@92e1d0; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(101,'u12019837','2014-09-21 21:03:23',' [ Method = getAllApplications ] [ Parameters: com.softserve.system.Session@89ffcb; ]'),(102,'u12019837','2014-09-21 21:03:23',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1068fdb; ]'),(103,'u12019837','2014-09-21 21:03:23',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1a23337; ]'),(104,'u12019837','2014-09-21 21:03:23',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@102c6c1; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(105,'u12019837','2014-09-21 21:03:23',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1de2737; ]'),(106,'u12019837','2014-09-21 21:03:26',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@19833f4; ]'),(107,'u12019837','2014-09-21 21:03:26',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@9e0695; ]'),(108,'u12019837','2014-09-21 21:03:26',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@208882; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(109,'u12019837','2014-09-21 21:03:26',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@10fc241; ]'),(110,'u12019837','2014-09-21 21:03:29',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.system.Session@977e24; 0; 2147483647; ]'),(111,'u12019837','2014-09-21 21:03:30',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@36edfc; ]'),(112,'u12019837','2014-09-21 21:03:30',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@392a6c; ]'),(113,'u12019837','2014-09-21 21:03:30',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1a32e2b; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(114,'u12019837','2014-09-21 21:03:30',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1e866f0; ]'),(115,'u12019837','2014-09-21 21:03:49',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@a59341; ]'),(116,'u12019837','2014-09-21 21:03:49',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@5d1f9d; ]'),(117,'u12019837','2014-09-21 21:03:49',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@12d2d72; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(118,'u12019837','2014-09-21 21:03:49',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1345ec4; ]'),(119,'u12019837','2014-09-21 21:03:49',' [ Method = getHODsOfApplication ] [ Parameters: com.softserve.system.Session@1e3bdce; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(120,'u12019837','2014-09-21 21:05:02',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.system.Session@789a73; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(121,'u12019837','2014-09-21 21:05:03','Created CV [ Method = createCV ] [ Parameters: com.softserve.system.Session@789a73; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(122,'u12019837','2014-09-21 21:05:03',' [ Method = createGrantHolderCV ] [ Parameters: com.softserve.system.Session@789a73; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(123,'u12019837','2014-09-21 21:05:20',' [ Method = saveChangesToApplication ] [ Parameters: com.softserve.system.Session@15aba7f; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(124,'u12019837','2014-09-21 21:05:25',' [ Method = requestSpecificHODtoReview ] [ Parameters: com.softserve.system.Session@1f27cbe; com.softserve.DBEntities.Application[ applicationID=3 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(125,'u12019837','2014-09-21 21:05:25',' [ Method = finaliseApplication ] [ Parameters: com.softserve.system.Session@1f27cbe; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(126,'u12019837','2014-09-21 21:05:25',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.system.Session@92ee2c; 0; 2147483647; ]'),(127,'u12019837','2014-09-21 21:05:25',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@2e9b88; ]'),(128,'u12019837','2014-09-21 21:05:25',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1552841; ]'),(129,'u12019837','2014-09-21 21:05:25',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1b4ece5; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(130,'u12019837','2014-09-21 21:05:25',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@f2ac97; ]'),(131,'u12019837','2014-09-21 21:05:34',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@12552fa; ]'),(132,'u12019837','2014-09-21 21:05:34',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1655bfa; ]'),(133,'u12019837','2014-09-21 21:05:34',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@48c146; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(134,'u12019837','2014-09-21 21:05:34',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@ba4f36; ]'),(135,'u12019837','2014-09-21 21:05:36',' [ Method = getAllApplications ] [ Parameters: com.softserve.system.Session@febe4b; ]'),(136,'u12019837','2014-09-21 21:05:36',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1dbb65; ]'),(137,'u12019837','2014-09-21 21:05:36',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@192dafc; ]'),(138,'u12019837','2014-09-21 21:05:36',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1ec5a26; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(139,'u12019837','2014-09-21 21:05:36',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@70ff42; ]'),(140,'u12019837','2014-09-21 21:06:15',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@78c97f; ]'),(141,'u12019837','2014-09-21 21:06:15',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@f6cb34; ]'),(142,'u12019837','2014-09-21 21:06:15',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@148b187; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(143,'u12019837','2014-09-21 21:06:15',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@71f294; ]'),(144,'u12019837','2014-09-21 21:06:16',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1f75078; ]'),(145,'u12019837','2014-09-21 21:06:16',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@a2fc87; ]'),(146,'u12019837','2014-09-21 21:06:16',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@f7c69; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(147,'u12019837','2014-09-21 21:06:16',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1488bb; ]'),(148,'u12019837','2014-09-21 21:06:20',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1b91235; ]'),(149,'u12019837','2014-09-21 21:06:20',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@cf44c3; ]'),(150,'u12019837','2014-09-21 21:06:20',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@af8d2f; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(151,'u12019837','2014-09-21 21:06:20',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1b9a7b3; ]'),(152,'u12019837','2014-09-21 21:06:20',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.system.Session@190bea7; ]'),(153,'u12019837','2014-09-21 21:06:22',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1f03346; ]'),(154,'u12019837','2014-09-21 21:06:22',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@13e2b20; ]'),(155,'u12019837','2014-09-21 21:06:22',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@11bbaa1; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(156,'u12019837','2014-09-21 21:06:22',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@228feb; ]'),(157,'u12019837','2014-09-21 21:06:29',' [ Method = logout ] [ Parameters: com.softserve.system.Session@fc370e; ]'),(158,'f14000004','2014-09-21 21:08:20','Created new user account'),(159,'f14000004','2014-09-21 21:08:20',' [ Method = login ] [ Parameters: com.softserve.system.Session@9bc1d8; ]'),(160,'f14000001','2014-09-21 21:09:59',' [ Method = login ] [ Parameters: com.softserve.system.Session@1aa92fe; ]'),(161,'f14000001','2014-09-21 21:10:38',' [ Method = logout ] [ Parameters: com.softserve.system.Session@f72bce; ]'),(162,'u12019837','2014-09-21 21:10:47',' [ Method = login ] [ Parameters: com.softserve.system.Session@1f0b6e3; ]'),(163,'u12019837','2014-09-21 21:10:47',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@3efffc; ]'),(164,'u12019837','2014-09-21 21:10:47',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@e59ac1; ]'),(165,'u12019837','2014-09-21 21:10:47',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@a5f969; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(166,'u12019837','2014-09-21 21:10:47',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@d7b6f9; ]'),(167,'u12019837','2014-09-21 21:10:49',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@f0d798; ]'),(168,'u12019837','2014-09-21 21:10:49',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@a26c5b; ]'),(169,'u12019837','2014-09-21 21:10:49',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@104f3e3; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(170,'u12019837','2014-09-21 21:10:49',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1d3fc8f; ]'),(171,'u12019837','2014-09-21 21:10:51',' [ Method = getAllApplications ] [ Parameters: com.softserve.system.Session@10ae94c; ]'),(172,'u12019837','2014-09-21 21:10:51',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@c78565; ]'),(173,'u12019837','2014-09-21 21:10:52',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@d977f2; ]'),(174,'u12019837','2014-09-21 21:10:52',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@70c559; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(175,'u12019837','2014-09-21 21:10:52',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1d42cdd; ]'),(176,'u12019837','2014-09-21 21:11:50',' [ Method = login ] [ Parameters: com.softserve.system.Session@13fe06; ]'),(177,'u12019837','2014-09-21 21:11:51',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@df67b4; ]'),(178,'u12019837','2014-09-21 21:11:51',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@c508d8; ]'),(179,'u12019837','2014-09-21 21:11:51',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@cfa371; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(180,'u12019837','2014-09-21 21:11:52',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@46ec47; ]'),(181,'u12019837','2014-09-21 21:11:54',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@ddc485; ]'),(182,'u12019837','2014-09-21 21:11:54',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@fc5ec9; ]'),(183,'u12019837','2014-09-21 21:11:54',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1e218c5; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(184,'u12019837','2014-09-21 21:11:54',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1c665c9; ]'),(185,'u12019837','2014-09-21 21:12:00',' [ Method = getAllApplications ] [ Parameters: com.softserve.system.Session@1a1bf4e; ]'),(186,'u12019837','2014-09-21 21:12:00',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1efdf06; ]'),(187,'u12019837','2014-09-21 21:12:01',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@10d2dc0; ]'),(188,'u12019837','2014-09-21 21:12:01',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@b2847f; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(189,'u12019837','2014-09-21 21:12:01',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@17480f0; ]'),(190,'u12019837','2014-09-21 21:12:10',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@7fea74; ]'),(191,'u12019837','2014-09-21 21:12:10',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@18aa992; ]'),(192,'u12019837','2014-09-21 21:12:10',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@19e18f2; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(193,'u12019837','2014-09-21 21:12:10',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@192e8f5; ]'),(194,'u12019837','2014-09-21 21:12:18',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@142dffb; ]'),(195,'u12019837','2014-09-21 21:12:19',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@17cd416; ]'),(196,'u12019837','2014-09-21 21:12:20',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@8b3660; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(197,'u12019837','2014-09-21 21:12:20',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@193c972; ]'),(198,'u12019837','2014-09-21 21:12:22',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@a7edc0; ]'),(199,'u12019837','2014-09-21 21:12:22',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1422072; ]'),(200,'u12019837','2014-09-21 21:12:22',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1af8e77; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(201,'u12019837','2014-09-21 21:12:23',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@59a8cf; ]'),(202,'u12019837','2014-09-21 21:12:23',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.system.Session@af2701; ]'),(203,'u12019837','2014-09-21 21:12:25',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@95c99a; ]'),(204,'u12019837','2014-09-21 21:12:25',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@f9177d; ]'),(205,'u12019837','2014-09-21 21:12:25',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@44a233; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(206,'u12019837','2014-09-21 21:12:25',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@23a362; ]'),(207,'u12019837','2014-09-21 21:12:29',' [ Method = logout ] [ Parameters: com.softserve.system.Session@6d2153; ]'),(208,'f14000004','2014-09-21 21:12:41',' [ Method = login ] [ Parameters: com.softserve.system.Session@18d029c; ]'),(209,'f14000004','2014-09-21 21:12:58',' [ Method = getOpenApplication ] [ Parameters: com.softserve.system.Session@1ecf284; ]'),(210,'f14000004','2014-09-21 21:14:30',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.system.Session@864e6b; com.softserve.DBEntities.Cv[ cvID=f14000004 ]; ]'),(211,'f14000004','2014-09-21 21:14:30','Created CV [ Method = createCV ] [ Parameters: com.softserve.system.Session@864e6b; com.softserve.DBEntities.Cv[ cvID=f14000004 ]; ]'),(212,'f14000004','2014-09-21 21:14:30','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.system.Session@864e6b; com.softserve.DBEntities.Cv[ cvID=f14000004 ]; ]'),(213,'f14000004','2014-09-21 21:15:31','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.system.Session@b98cf7; com.softserve.DBEntities.Application[ applicationID=4 ]; ]'),(214,'f14000004','2014-09-21 21:15:35','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.system.Session@1b8c2eb; com.softserve.DBEntities.Application[ applicationID=4 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(215,'f14000004','2014-09-21 21:15:39','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.system.Session@1aa0dce; com.softserve.DBEntities.Application[ applicationID=4 ]; ]'),(216,'f14000004','2014-09-21 21:15:53',' [ Method = logout ] [ Parameters: com.softserve.system.Session@18f5727; ]'),(217,'u12019837','2014-09-21 21:15:57',' [ Method = login ] [ Parameters: com.softserve.system.Session@16f5734; ]'),(218,'u12019837','2014-09-21 21:15:57',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@f384b9; ]'),(219,'u12019837','2014-09-21 21:15:57',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1b748c8; ]'),(220,'u12019837','2014-09-21 21:15:57',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@9883e0; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(221,'u12019837','2014-09-21 21:15:57',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1a19a8f; ]'),(222,'u12019837','2014-09-21 21:16:00',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1417c50; ]'),(223,'u12019837','2014-09-21 21:16:00',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@cf13dd; ]'),(224,'u12019837','2014-09-21 21:16:00',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1260e9c; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(225,'u12019837','2014-09-21 21:16:00',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@18dc4c8; ]'),(226,'u12019837','2014-09-21 21:16:03',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.system.Session@1fca6e3; 0; 2147483647; ]'),(227,'u12019837','2014-09-21 21:16:03',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@150f4a3; ]'),(228,'u12019837','2014-09-21 21:16:03',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1ce14ef; ]'),(229,'u12019837','2014-09-21 21:16:03',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@e59407; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(230,'u12019837','2014-09-21 21:16:03',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1cb4168; ]'),(231,'u12019837','2014-09-21 21:16:10',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@100c542; ]'),(232,'u12019837','2014-09-21 21:16:10',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@103eede; ]'),(233,'u12019837','2014-09-21 21:16:10',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1c6591c; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(234,'u12019837','2014-09-21 21:16:10',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@2386cb; ]'),(235,'u12019837','2014-09-21 21:16:11',' [ Method = getHODsOfApplication ] [ Parameters: com.softserve.system.Session@c5de93; com.softserve.DBEntities.Application[ applicationID=4 ]; ]'),(236,'u12019837','2014-09-21 21:16:15',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.system.Session@1e276b0; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(237,'u12019837','2014-09-21 21:16:15','Updated CV [ Method = updateCV ] [ Parameters: com.softserve.system.Session@1e276b0; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(238,'u12019837','2014-09-21 21:16:15',' [ Method = createGrantHolderCV ] [ Parameters: com.softserve.system.Session@1e276b0; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(239,'u12019837','2014-09-21 21:16:16',' [ Method = saveChangesToApplication ] [ Parameters: com.softserve.system.Session@eeaa7e; com.softserve.DBEntities.Application[ applicationID=4 ]; ]'),(240,'u12019837','2014-09-21 21:16:22',' [ Method = requestSpecificHODtoReview ] [ Parameters: com.softserve.system.Session@1d647b5; com.softserve.DBEntities.Application[ applicationID=4 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(241,'u12019837','2014-09-21 21:16:22',' [ Method = finaliseApplication ] [ Parameters: com.softserve.system.Session@1d647b5; com.softserve.DBEntities.Application[ applicationID=4 ]; ]'),(242,'u12019837','2014-09-21 21:16:22',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.system.Session@2faff5; 0; 2147483647; ]'),(243,'u12019837','2014-09-21 21:16:22',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@6b8cb3; ]'),(244,'u12019837','2014-09-21 21:16:22',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@50caf4; ]'),(245,'u12019837','2014-09-21 21:16:22',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@b6c02c; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(246,'u12019837','2014-09-21 21:16:23',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@a7f607; ]'),(247,'u12019837','2014-09-21 21:16:27',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1f6b82; ]'),(248,'u12019837','2014-09-21 21:16:27',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@eebc9e; ]'),(249,'u12019837','2014-09-21 21:16:27',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1e7ed75; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(250,'u12019837','2014-09-21 21:16:27',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@984d11; ]'),(251,'u12019837','2014-09-21 21:16:30',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.system.Session@bc8d45; 0; 2147483647; ]'),(252,'u12019837','2014-09-21 21:16:30',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@cc5765; ]'),(253,'u12019837','2014-09-21 21:16:30',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@d4443a; ]'),(254,'u12019837','2014-09-21 21:16:30',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@3e7120; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(255,'u12019837','2014-09-21 21:16:30',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@d2c4a4; ]'),(256,'u12019837','2014-09-21 21:16:34',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@102c6d1; ]'),(257,'u12019837','2014-09-21 21:16:34',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@debc2f; ]'),(258,'u12019837','2014-09-21 21:16:34',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@9ba882; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(259,'u12019837','2014-09-21 21:16:35',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@ebf264; ]'),(260,'u12019837','2014-09-21 21:16:41',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1d68327; ]'),(261,'u12019837','2014-09-21 21:16:41',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1a47529; ]'),(262,'u12019837','2014-09-21 21:16:41',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@99a82a; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(263,'u12019837','2014-09-21 21:16:42',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@18b216b; ]'),(264,'u12019837','2014-09-21 21:16:42',' [ Method = getDeansOfApplication ] [ Parameters: com.softserve.system.Session@94643b; com.softserve.DBEntities.Application[ applicationID=4 ]; ]'),(265,'u12019837','2014-09-21 21:17:00',' [ Method = requestSpecificDeanToReview ] [ Parameters: com.softserve.system.Session@376270; com.softserve.DBEntities.Application[ applicationID=4 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ] [ Exception = java.lang.Exception: You cannot, nor the fellow, nor the referees of your application can be requested to review application]'),(266,'u12019837','2014-09-21 21:17:54',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@635c8e; ]'),(267,'u12019837','2014-09-21 21:17:54',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@15be8f; ]'),(268,'u12019837','2014-09-21 21:17:54',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@b0e6fa; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(269,'u12019837','2014-09-21 21:17:54',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1fd70ed; ]'),(270,'u12019837','2014-09-21 21:18:43',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1c8bf84; ]'),(271,'u12019837','2014-09-21 21:18:43',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@4c8a21; ]'),(272,'u12019837','2014-09-21 21:18:44',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@d97bc8; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(273,'u12019837','2014-09-21 21:18:45',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@7d3abd; ]'),(274,'u12019837','2014-09-22 00:00:00',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@11952aa; ]'),(275,'u12019837','2014-09-22 00:00:00',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1dba65a; ]'),(276,'u12019837','2014-09-22 00:00:01',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@16e9a1; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(277,'u12019837','2014-09-22 00:00:01',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@16cb416; ]'),(278,'u12019837','2014-09-26 00:27:23',' [ Method = login ] [ Parameters: com.softserve.system.Session@15e34b3; ]'),(279,'u12019837','2014-09-26 00:27:26',' [ Method = login ] [ Parameters: com.softserve.system.Session@19c009f; ]'),(280,'u12019837','2014-09-26 00:27:30',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1f2644d; ]'),(281,'u12019837','2014-09-26 00:27:30',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@274ed4; ]'),(282,'u12019837','2014-09-26 00:27:30',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1e8e39c; ]'),(283,'u12019837','2014-09-26 00:27:30',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@183a7bb; ]'),(284,'u12019837','2014-09-26 00:27:30',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@13615e9; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(285,'u12019837','2014-09-26 00:27:30',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@e025f2; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(286,'u12019837','2014-09-26 00:27:31',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1ac0093; ]'),(287,'u12019837','2014-09-26 00:27:31',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@13c300f; ]'),(288,'u12019837','2014-09-26 00:28:05',' [ Method = logout ] [ Parameters: com.softserve.system.Session@1937f3a; ]'),(289,'u12019837','2014-09-26 00:57:47',' [ Method = login ] [ Parameters: com.softserve.system.Session@1f8eb78; ]'),(290,'u12019837','2014-09-26 00:57:50',' [ Method = login ] [ Parameters: com.softserve.system.Session@b04dc; ]'),(291,'u12019837','2014-09-26 00:57:51',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1bfbd7c; ]'),(292,'u12019837','2014-09-26 00:57:51',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1f57ffc; ]'),(293,'u12019837','2014-09-26 00:57:51',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@4bffb1; ]'),(294,'u12019837','2014-09-26 00:57:51',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@cb61d7; ]'),(295,'u12019837','2014-09-26 00:57:51',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1ba6451; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(296,'u12019837','2014-09-26 00:57:51',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@69a177; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(297,'u12019837','2014-09-26 00:57:51',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1d045cc; ]'),(298,'u12019837','2014-09-26 00:57:51',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@113914f; ]'),(299,'u12019837','2014-09-26 00:58:10',' [ Method = logout ] [ Parameters: com.softserve.system.Session@12213db; ]'),(300,'u12019837','2014-09-26 00:58:19',' [ Method = login ] [ Parameters: com.softserve.system.Session@57878b; ]'),(301,'u12019837','2014-09-26 00:58:19',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@142016f; ]'),(302,'u12019837','2014-09-26 00:58:19',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@17beb8; ]'),(303,'u12019837','2014-09-26 00:58:19',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@175f41b; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(304,'u12019837','2014-09-26 00:58:19',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@f78846; ]'),(305,'u12019837','2014-09-26 00:58:22',' [ Method = logout ] [ Parameters: com.softserve.system.Session@19a306d; ]'),(306,'u12019837','2014-09-26 00:59:29',' [ Method = login ] [ Parameters: com.softserve.system.Session@1dbe4e; ]'),(307,'u12019837','2014-09-26 00:59:31',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@b61da8; ]'),(308,'u12019837','2014-09-26 00:59:31',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@75138e; ]'),(309,'u12019837','2014-09-26 00:59:31',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@e127a8; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(310,'u12019837','2014-09-26 00:59:31',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1ad1f56; ]'),(311,'u12019837','2014-09-26 00:59:46',' [ Method = logout ] [ Parameters: com.softserve.system.Session@5a41d9; ]'),(312,'u12019837','2014-09-26 01:02:06',' [ Method = login ] [ Parameters: com.softserve.system.Session@1ff9cf9; ]'),(313,'u12019837','2014-09-26 01:02:06',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@19e6b0d; ]'),(314,'u12019837','2014-09-26 01:02:06',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@a368e7; ]'),(315,'u12019837','2014-09-26 01:02:06',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@13fcb7f; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(316,'u12019837','2014-09-26 01:02:06',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@11ceebe; ]'),(317,'u12019837','2014-09-26 01:05:00',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1d2594f; ]'),(318,'u12019837','2014-09-26 01:05:01',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@158e1d8; ]'),(319,'u12019837','2014-09-26 01:05:01',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1ce0920; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(320,'u12019837','2014-09-26 01:05:01',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@e68b2c; ]'),(321,'u12019837','2014-09-26 01:06:09',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1d64ed0; ]'),(322,'u12019837','2014-09-26 01:06:10',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@141f0ba; ]'),(323,'u12019837','2014-09-26 01:06:10',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@6f1d5; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(324,'u12019837','2014-09-26 01:06:10',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1c3b04a; ]'),(325,'u12019837','2014-09-26 01:06:26',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@fd7ab8; ]'),(326,'u12019837','2014-09-26 01:06:27',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@c5263d; ]'),(327,'u12019837','2014-09-26 01:06:27',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@1574f35; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(328,'u12019837','2014-09-26 01:06:27',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@142c2e7; ]'),(329,'u12019837','2014-09-26 01:06:53',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1d669a9; ]'),(330,'u12019837','2014-09-26 01:06:54',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1be8104; ]'),(331,'u12019837','2014-09-26 01:06:54',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@3455c7; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(332,'u12019837','2014-09-26 01:06:54',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1688736; ]'),(333,'u12019837','2014-09-26 01:07:11',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@e8a0f6; ]'),(334,'u12019837','2014-09-26 01:07:12',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@120f299; ]'),(335,'u12019837','2014-09-26 01:07:12',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@721967; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(336,'u12019837','2014-09-26 01:07:12',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@5294f9; ]'),(337,'u12019837','2014-09-26 01:07:46',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@695cbc; ]'),(338,'u12019837','2014-09-26 01:07:46',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@13b439; ]'),(339,'u12019837','2014-09-26 01:07:47',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@9dc23c; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(340,'u12019837','2014-09-26 01:07:47',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@114dfd4; ]'),(341,'u12019837','2014-09-26 01:07:49',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@b1b1ae; ]'),(342,'u12019837','2014-09-26 01:07:50',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@13ead6b; ]'),(343,'u12019837','2014-09-26 01:07:50',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@cd7752; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(344,'u12019837','2014-09-26 01:07:50',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@5e551d; ]'),(345,'u12019837','2014-09-26 01:08:08',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@161f2ce; ]'),(346,'u12019837','2014-09-26 01:08:08',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@7996c5; ]'),(347,'u12019837','2014-09-26 01:08:08',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@18d526e; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(348,'u12019837','2014-09-26 01:08:08',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@b3ef28; ]'),(349,'u12019837','2014-09-26 01:09:18',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@12342b6; ]'),(350,'u12019837','2014-09-26 01:09:18',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1532f22; ]'),(351,'u12019837','2014-09-26 01:09:18',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@12cfcef; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(352,'u12019837','2014-09-26 01:09:18',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1f53501; ]'),(353,'u12019837','2014-09-26 01:09:24',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@179e62; ]'),(354,'u12019837','2014-09-26 01:09:24',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@7bbdbd; ]'),(355,'u12019837','2014-09-26 01:09:24',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@152645d; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(356,'u12019837','2014-09-26 01:09:24',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1af6761; ]'),(357,'u12019837','2014-09-26 01:47:30',' [ Method = login ] [ Parameters: com.softserve.system.Session@7d0b09; ]'),(358,'u12019837','2014-09-26 01:47:31',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1f1ab12; ]'),(359,'u12019837','2014-09-26 01:47:32',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@701fb; ]'),(360,'u12019837','2014-09-26 01:47:32',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@dcb5a4; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(361,'u12019837','2014-09-26 01:47:32',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@12579bb; ]'),(362,'u12019837','2014-09-26 01:47:35',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@3e718d; ]'),(363,'u12019837','2014-09-26 01:47:35',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@5555c7; ]'),(364,'u12019837','2014-09-26 01:47:35',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@154231f; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(365,'u12019837','2014-09-26 01:47:35',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1016efa; ]'),(366,'u12019837','2014-09-26 01:47:38',' [ Method = getAllApplications ] [ Parameters: com.softserve.system.Session@520d58; ]'),(367,'u12019837','2014-09-26 01:47:38',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@f30cdc; ]'),(368,'u12019837','2014-09-26 01:47:38',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1db7858; ]'),(369,'u12019837','2014-09-26 01:47:38',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@16de211; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(370,'u12019837','2014-09-26 01:47:38',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1bad7a3; ]'),(371,'u12019837','2014-09-26 01:48:15',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@14bd620; ]'),(372,'u12019837','2014-09-26 01:48:15',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@7a1d2; ]'),(373,'u12019837','2014-09-26 01:48:15',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@14d458b; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(374,'u12019837','2014-09-26 01:48:15',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@195d4d8; ]'),(375,'u12019837','2014-09-26 01:48:25',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@b55589; ]'),(376,'u12019837','2014-09-26 01:48:26',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@9c1b04; ]'),(377,'u12019837','2014-09-26 01:48:26',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@dbb9f; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(378,'u12019837','2014-09-26 01:48:26',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@4f9252; ]'),(379,'u12019837','2014-09-26 01:48:32',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@1bad153; ]'),(380,'u12019837','2014-09-26 01:48:32',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1e8f363; ]'),(381,'u12019837','2014-09-26 01:48:32',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@14270f7; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(382,'u12019837','2014-09-26 01:48:32',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@12e14; ]'),(383,'u12019837','2014-09-26 01:48:32',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.system.Session@8495a2; ]'),(384,'u12019837','2014-09-26 01:48:35',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@8effec; ]'),(385,'u12019837','2014-09-26 01:48:35',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@e3eeb3; ]'),(386,'u12019837','2014-09-26 01:48:35',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@16f9ff1; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(387,'u12019837','2014-09-26 01:48:35',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@1890633; ]'),(388,'u12019837','2014-09-26 01:49:41',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@cc5868; ]'),(389,'u12019837','2014-09-26 01:49:41',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@16d7baa; ]'),(390,'u12019837','2014-09-26 01:49:41',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@ee6c33; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(391,'u12019837','2014-09-26 01:49:41',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@6b3702; ]'),(392,'u12019837','2014-09-26 01:54:23',' [ Method = login ] [ Parameters: com.softserve.system.Session@3ae99a; ]'),(393,'u12019837','2014-09-26 01:54:24',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@bf10ae; ]'),(394,'u12019837','2014-09-26 01:54:24',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@1722138; ]'),(395,'u12019837','2014-09-26 01:54:24',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@51b629; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(396,'u12019837','2014-09-26 01:54:24',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@7dc9d0; ]'),(397,'u12019837','2014-09-26 01:54:27',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@d5b995; ]'),(398,'u12019837','2014-09-26 01:54:27',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@ff5c0a; ]'),(399,'u12019837','2014-09-26 01:54:27',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@afe8c0; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(400,'u12019837','2014-09-26 01:54:27',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@e1894f; ]'),(401,'u12019837','2014-09-26 01:54:29',' [ Method = getAllApplications ] [ Parameters: com.softserve.system.Session@5b39e7; ]'),(402,'u12019837','2014-09-26 01:54:29',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.system.Session@c238a4; ]'),(403,'u12019837','2014-09-26 01:54:29',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.system.Session@df1919; ]'),(404,'u12019837','2014-09-26 01:54:29',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.system.Session@124b595; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(405,'u12019837','2014-09-26 01:54:29',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.system.Session@b9ddc8; ]'),(406,'u12019837','2014-09-26 01:54:46',' [ Method = logout ] [ Parameters: com.softserve.system.Session@1560a9b; ]'),(407,'f14000005','2014-09-26 01:57:30','Created new user account'),(408,'f14000005','2014-09-26 01:57:30',' [ Method = login ] [ Parameters: com.softserve.system.Session@6e13af; ]'),(409,'f14000005','2014-09-26 01:58:23',' [ Method = getOpenApplication ] [ Parameters: com.softserve.system.Session@18dd529; ]'),(410,'f14000005','2014-09-26 02:04:39',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.system.Session@1039402; com.softserve.DBEntities.Cv[ cvID=f14000005 ]; ]'),(411,'f14000005','2014-09-26 02:04:39','Created CV [ Method = createCV ] [ Parameters: com.softserve.system.Session@1039402; com.softserve.DBEntities.Cv[ cvID=f14000005 ]; ]'),(412,'f14000005','2014-09-26 02:04:39','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.system.Session@1039402; com.softserve.DBEntities.Cv[ cvID=f14000005 ]; ]'),(413,'f14000005','2014-09-26 02:08:55','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.system.Session@9d43c3; com.softserve.DBEntities.Application[ applicationID=5 ]; ]'),(414,'f14000005','2014-09-26 02:09:05','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.system.Session@566e97; com.softserve.DBEntities.Application[ applicationID=5 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(415,'f14000005','2014-09-26 02:09:15','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.system.Session@1dc0a29; com.softserve.DBEntities.Application[ applicationID=5 ]; ]'),(416,'f14000005','2014-09-26 02:09:40',' [ Method = logout ] [ Parameters: com.softserve.system.Session@1f3afa; ]'),(417,'f14000006','2014-09-26 02:10:44','Created new user account'),(418,'f14000006','2014-09-26 02:10:44',' [ Method = login ] [ Parameters: com.softserve.system.Session@1ea6574; ]'),(419,'f14000006','2014-09-26 02:10:51',' [ Method = getOpenApplication ] [ Parameters: com.softserve.system.Session@1eec8c7; ]'),(420,'f14000006','2014-09-26 02:13:07',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.system.Session@15e6280; com.softserve.DBEntities.Cv[ cvID=f14000006 ]; ]'),(421,'f14000006','2014-09-26 02:13:08','Created CV [ Method = createCV ] [ Parameters: com.softserve.system.Session@15e6280; com.softserve.DBEntities.Cv[ cvID=f14000006 ]; ]'),(422,'f14000006','2014-09-26 02:13:08','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.system.Session@15e6280; com.softserve.DBEntities.Cv[ cvID=f14000006 ]; ]'),(423,'f14000006','2014-09-26 02:16:00',' [ Method = login ] [ Parameters: com.softserve.system.Session@3c3c46; ]'),(424,'f14000006','2014-09-26 02:16:12',' [ Method = getOpenApplication ] [ Parameters: com.softserve.system.Session@8fbf75; ]'),(425,'f14000006','2014-09-26 02:16:19',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.system.Session@14986ab; com.softserve.DBEntities.Cv[ cvID=f14000006 ]; ]'),(426,'f14000006','2014-09-26 02:16:19','Updated CV [ Method = updateCV ] [ Parameters: com.softserve.system.Session@14986ab; com.softserve.DBEntities.Cv[ cvID=f14000006 ]; ]'),(427,'f14000006','2014-09-26 02:16:19','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.system.Session@14986ab; com.softserve.DBEntities.Cv[ cvID=f14000006 ]; ]'),(428,'f14000006','2014-09-26 02:18:45','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.system.Session@b08038; com.softserve.DBEntities.Application[ applicationID=6 ]; ]'),(429,'f14000006','2014-09-26 02:18:55','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.system.Session@1103d40; com.softserve.DBEntities.Application[ applicationID=6 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(430,'f14000006','2014-09-26 02:18:59','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.system.Session@1d2929c; com.softserve.DBEntities.Application[ applicationID=6 ]; ]'),(431,'f14000006','2014-09-26 02:19:04',' [ Method = logout ] [ Parameters: com.softserve.system.Session@1606ba7; ]'),(432,'f14000007','2014-09-26 02:20:53','Created new user account'),(433,'f14000007','2014-09-26 02:20:53',' [ Method = login ] [ Parameters: com.softserve.system.Session@f111cd; ]'),(434,'f14000007','2014-09-26 02:21:15',' [ Method = getOpenApplication ] [ Parameters: com.softserve.system.Session@f3653c; ]'),(435,'f14000007','2014-09-26 02:24:53',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.system.Session@442995; com.softserve.DBEntities.Cv[ cvID=f14000007 ]; ]'),(436,'f14000007','2014-09-26 02:24:53','Created CV [ Method = createCV ] [ Parameters: com.softserve.system.Session@442995; com.softserve.DBEntities.Cv[ cvID=f14000007 ]; ]'),(437,'f14000007','2014-09-26 02:24:53','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.system.Session@442995; com.softserve.DBEntities.Cv[ cvID=f14000007 ]; ]'),(438,'f14000007','2014-09-26 02:28:11','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.system.Session@8130da; com.softserve.DBEntities.Application[ applicationID=7 ]; ]'),(439,'f14000007','2014-09-26 02:28:25','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.system.Session@1b2e48; com.softserve.DBEntities.Application[ applicationID=7 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(440,'f14000007','2014-09-26 02:28:29','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.system.Session@992db3; com.softserve.DBEntities.Application[ applicationID=7 ]; ]'),(441,'f14000007','2014-09-26 02:28:40',' [ Method = logout ] [ Parameters: com.softserve.system.Session@1f05f1f; ]'),(442,'f14000008','2014-09-26 02:31:00','Created new user account'),(443,'f14000008','2014-09-26 02:31:01',' [ Method = login ] [ Parameters: com.softserve.system.Session@7d8580; ]'),(444,'f14000008','2014-09-26 02:31:13',' [ Method = getOpenApplication ] [ Parameters: com.softserve.system.Session@3d0b6b; ]'),(445,'f14000008','2014-09-26 02:34:37',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.system.Session@d0b5c; com.softserve.DBEntities.Cv[ cvID=f14000008 ]; ]'),(446,'f14000008','2014-09-26 02:34:38','Created CV [ Method = createCV ] [ Parameters: com.softserve.system.Session@d0b5c; com.softserve.DBEntities.Cv[ cvID=f14000008 ]; ]'),(447,'f14000008','2014-09-26 02:34:38','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.system.Session@d0b5c; com.softserve.DBEntities.Cv[ cvID=f14000008 ]; ]'),(448,'f14000008','2014-09-26 02:36:12','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.system.Session@1d9d885; com.softserve.DBEntities.Application[ applicationID=8 ]; ]'),(449,'f14000008','2014-09-26 02:36:21','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.system.Session@1d494b2; com.softserve.DBEntities.Application[ applicationID=8 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(450,'f14000008','2014-09-26 02:36:25','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.system.Session@17ee922; com.softserve.DBEntities.Application[ applicationID=8 ]; ]'),(451,'f14000008','2014-09-26 02:37:01',' [ Method = logout ] [ Parameters: com.softserve.system.Session@2209eb; ]'),(452,'f14000009','2014-09-26 02:40:35','Created new user account'),(453,'f14000009','2014-09-26 02:40:35',' [ Method = login ] [ Parameters: com.softserve.system.Session@c9a6d1; ]'),(454,'f14000009','2014-09-26 02:40:46',' [ Method = getOpenApplication ] [ Parameters: com.softserve.system.Session@15003ab; ]'),(455,'f14000009','2014-09-26 02:42:04',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.system.Session@2d286f; com.softserve.DBEntities.Cv[ cvID=f14000009 ]; ]'),(456,'f14000009','2014-09-26 02:42:04','Created CV [ Method = createCV ] [ Parameters: com.softserve.system.Session@2d286f; com.softserve.DBEntities.Cv[ cvID=f14000009 ]; ]'),(457,'f14000009','2014-09-26 02:42:04','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.system.Session@2d286f; com.softserve.DBEntities.Cv[ cvID=f14000009 ]; ]'),(458,'f14000009','2014-09-26 02:43:22','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.system.Session@1579753; com.softserve.DBEntities.Application[ applicationID=9 ]; ]'),(459,'f14000009','2014-09-26 02:43:31','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.system.Session@12b0d0e; com.softserve.DBEntities.Application[ applicationID=9 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(460,'f14000009','2014-09-26 02:43:34','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.system.Session@8e36b1; com.softserve.DBEntities.Application[ applicationID=9 ]; ]');
/*!40000 ALTER TABLE `audit_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `committee_meeting`
--

DROP TABLE IF EXISTS `committee_meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `committee_meeting` (
  `_meetingID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_organiser` char(9) NOT NULL,
  `_name` varchar(100) DEFAULT NULL,
  `_venue` varchar(100) DEFAULT NULL,
  `_startDate` datetime DEFAULT NULL,
  `_endDate` datetime DEFAULT NULL,
  PRIMARY KEY (`_meetingID`),
  KEY `_organiser` (`_organiser`),
  CONSTRAINT `committee_meeting_ibfk_1` FOREIGN KEY (`_organiser`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `committee_meeting`
--

LOCK TABLES `committee_meeting` WRITE;
/*!40000 ALTER TABLE `committee_meeting` DISABLE KEYS */;
/*!40000 ALTER TABLE `committee_meeting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `committee_meetings_applications`
--

DROP TABLE IF EXISTS `committee_meetings_applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `committee_meetings_applications` (
  `_meetingID` bigint(20) unsigned NOT NULL,
  `_applicationID` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`_meetingID`,`_applicationID`),
  KEY `_applicationID` (`_applicationID`),
  CONSTRAINT `committee_meetings_applications_ibfk_1` FOREIGN KEY (`_meetingID`) REFERENCES `committee_meeting` (`_meetingID`),
  CONSTRAINT `committee_meetings_applications_ibfk_2` FOREIGN KEY (`_applicationID`) REFERENCES `application` (`_applicationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `committee_meetings_applications`
--

LOCK TABLES `committee_meetings_applications` WRITE;
/*!40000 ALTER TABLE `committee_meetings_applications` DISABLE KEYS */;
/*!40000 ALTER TABLE `committee_meetings_applications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cv`
--

DROP TABLE IF EXISTS `cv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv` (
  `_cvID` char(9) NOT NULL,
  `_idNumber` char(30) NOT NULL,
  `_dateOfBirth` date NOT NULL,
  `_gender` enum('Male','Female','Other') DEFAULT NULL,
  `_citizenship` varchar(100) DEFAULT NULL,
  `_nrfRating` char(4) DEFAULT NULL,
  `_race` char(20) DEFAULT NULL,
  `_recentInstitution` varchar(100) DEFAULT NULL,
  `_researchOutput` text,
  `_otherContributions` text,
  `_additionalInformation` text,
  PRIMARY KEY (`_cvID`),
  CONSTRAINT `cv_ibfk_1` FOREIGN KEY (`_cvID`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cv`
--

LOCK TABLES `cv` WRITE;
/*!40000 ALTER TABLE `cv` DISABLE KEYS */;
INSERT INTO `cv` VALUES ('f14000001','9309175578083','2004-09-01','Male','South African','A','Black',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>The world as seen through the open</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Been there done that</name><desciption>Happening</desciption><location>Above the clouds</location><date>2014-09-30+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Open test data</information></additionalInformation>'),('f14000002','9309175578083','2014-09-01','Male','South African','A','Black',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Getting referred is time consuming</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Been there done that</name><desciption>Happening</desciption><location>Above the clouds</location><date>2014-09-30+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>A referred applicant</information></additionalInformation>'),('f14000003','9309175578083','1994-09-02','Male','South African','A','Black',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Getting finalised is time consuming</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Been there done that</name><desciption>Happening</desciption><location>Above the clouds</location><date>2014-09-02+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Finanlised</information></additionalInformation>'),('f14000004','9309175578083','2000-09-08','Male','South African','A','Other',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Technical/Policy reports</type><status>In progress</status><publicationName>The world as seen through the recommended</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Been there done that</name><desciption>Happening</desciption><location>Above the clouds</location><date>2014-09-02+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Recommended</information></additionalInformation>'),('f14000005','9309175578083','1996-09-05','Male','South African','N/A','White',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Yizo</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Item</name><desciption>Works</desciption><location>Item</location><date>2014-09-18+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Trying to be endorsed</information></additionalInformation>'),('f14000006','9309175578083','2014-09-09','Male','South African','N/A','Black',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Wonderfilled</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Item</name><desciption>Works</desciption><location>Item</location><date>2014-09-02+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Falling asleep</information></additionalInformation>'),('f14000007','9309175578083','2014-09-02','Male','South African','N/A','Black',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Yizo</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Item</name><desciption>asrg</desciption><location>Item</location><date>2014-09-04+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Run</information></additionalInformation>'),('f14000008','9309175578083','2014-09-02','Male','South African','N/A','Black',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Yizo</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Item</name><desciption>asrg</desciption><location>Item</location><date>2014-09-02+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>I\'m complete</information></additionalInformation>'),('f14000009','9309175578083','2014-09-09','Female','South African','C2','White',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Yizo</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Item</name><desciption>asrg</desciption><location>Item</location><date>2014-09-01+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Kill</information></additionalInformation>'),('u12019837','9309175578083','2014-09-08','Male','South African','A','White',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Yizo</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Saved the world</name><desciption>Did it</desciption><location>Here</location><date>2014-09-08+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Grant holder</information></additionalInformation>');
/*!40000 ALTER TABLE `cv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `decline_report`
--

DROP TABLE IF EXISTS `decline_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `decline_report` (
  `_reportID` bigint(20) unsigned NOT NULL,
  `_creator` char(9) NOT NULL,
  `_timestamp` datetime NOT NULL,
  `_reason` text,
  PRIMARY KEY (`_reportID`),
  KEY `_creator` (`_creator`),
  CONSTRAINT `decline_report_ibfk_1` FOREIGN KEY (`_reportID`) REFERENCES `application` (`_applicationID`),
  CONSTRAINT `decline_report_ibfk_2` FOREIGN KEY (`_creator`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `decline_report`
--

LOCK TABLES `decline_report` WRITE;
/*!40000 ALTER TABLE `decline_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `decline_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `_departmentID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_faculty` bigint(20) unsigned NOT NULL,
  `_name` varchar(250) NOT NULL,
  PRIMARY KEY (`_departmentID`),
  KEY `_faculty` (`_faculty`),
  CONSTRAINT `department_ibfk_1` FOREIGN KEY (`_faculty`) REFERENCES `faculty` (`_facultyID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,1,'Computer Science'),(2,1,'Informatics'),(3,1,'Computer and Electronic Engineers'),(4,2,'Philosophy'),(5,3,'Dean');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eligiblity_report`
--

DROP TABLE IF EXISTS `eligiblity_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eligiblity_report` (
  `_reportID` bigint(20) unsigned NOT NULL,
  `_eligiblityCheckDate` datetime NOT NULL,
  `_eligiblityChecker` char(9) NOT NULL,
  PRIMARY KEY (`_reportID`),
  KEY `_eligiblityChecker` (`_eligiblityChecker`),
  CONSTRAINT `eligiblity_report_ibfk_1` FOREIGN KEY (`_reportID`) REFERENCES `application` (`_applicationID`),
  CONSTRAINT `eligiblity_report_ibfk_2` FOREIGN KEY (`_eligiblityChecker`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eligiblity_report`
--

LOCK TABLES `eligiblity_report` WRITE;
/*!40000 ALTER TABLE `eligiblity_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `eligiblity_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_information`
--

DROP TABLE IF EXISTS `employee_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_information` (
  `_employeeID` char(9) NOT NULL,
  `_physicalAddress` bigint(20) unsigned DEFAULT NULL,
  `_position` varchar(50) DEFAULT NULL,
  `_dateOfAppointment` date DEFAULT NULL,
  `_appointmentStatus` varchar(50) DEFAULT NULL,
  `_department` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`_employeeID`),
  KEY `_department` (`_department`),
  KEY `_physicalAddress` (`_physicalAddress`),
  CONSTRAINT `employee_information_ibfk_1` FOREIGN KEY (`_employeeID`) REFERENCES `person` (`_systemID`),
  CONSTRAINT `employee_information_ibfk_2` FOREIGN KEY (`_department`) REFERENCES `department` (`_departmentID`),
  CONSTRAINT `employee_information_ibfk_3` FOREIGN KEY (`_physicalAddress`) REFERENCES `address` (`_addressID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_information`
--

LOCK TABLES `employee_information` WRITE;
/*!40000 ALTER TABLE `employee_information` DISABLE KEYS */;
INSERT INTO `employee_information` VALUES ('u12019837',2,'HOD','2001-01-20','full time',1);
/*!40000 ALTER TABLE `employee_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endorsement`
--

DROP TABLE IF EXISTS `endorsement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endorsement` (
  `_endorsementID` bigint(20) unsigned NOT NULL,
  `_dean` char(9) NOT NULL,
  `_timestamp` datetime NOT NULL,
  `_rank` int(10) unsigned NOT NULL,
  `_motivation` text NOT NULL,
  PRIMARY KEY (`_endorsementID`),
  KEY `_dean` (`_dean`),
  CONSTRAINT `endorsement_ibfk_1` FOREIGN KEY (`_endorsementID`) REFERENCES `application` (`_applicationID`),
  CONSTRAINT `endorsement_ibfk_2` FOREIGN KEY (`_dean`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endorsement`
--

LOCK TABLES `endorsement` WRITE;
/*!40000 ALTER TABLE `endorsement` DISABLE KEYS */;
/*!40000 ALTER TABLE `endorsement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experience`
--

DROP TABLE IF EXISTS `experience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `experience` (
  `_experienceID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_cv` char(9) NOT NULL,
  `_capacity` varchar(100) DEFAULT NULL,
  `_organisation` varchar(100) DEFAULT NULL,
  `_startDate` datetime NOT NULL,
  `_endDate` datetime NOT NULL,
  PRIMARY KEY (`_experienceID`),
  KEY `_cv` (`_cv`),
  CONSTRAINT `experience_ibfk_1` FOREIGN KEY (`_cv`) REFERENCES `cv` (`_cvID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experience`
--

LOCK TABLES `experience` WRITE;
/*!40000 ALTER TABLE `experience` DISABLE KEYS */;
INSERT INTO `experience` VALUES (1,'f14000001','Tester for new Feature','Testing','2014-09-02 02:00:00','2014-09-30 02:00:00'),(2,'f14000001','Tester','Testing','2014-09-02 02:00:00','2014-09-30 02:00:00'),(3,'f14000002','Tester','Testing','2014-09-01 02:00:00','2014-09-02 02:00:00'),(4,'f14000003','Tester','Testing','2009-09-08 02:00:00','2014-09-09 02:00:00'),(5,'u12019837','Lecture','CS','2014-09-03 02:00:00','2014-09-25 02:00:00'),(6,'f14000004','Tester','Testing','2014-09-29 02:00:00','2014-09-30 02:00:00'),(7,'f14000005','Endorsement','Endo','2007-09-18 02:00:00','2014-09-17 02:00:00'),(8,'f14000006','Tester','Test','2014-09-01 02:00:00','2014-09-30 02:00:00'),(9,'f14000007','Funded','Fund','2014-09-09 02:00:00','2014-09-30 02:00:00'),(10,'f14000008','Funded','Fund','2014-09-02 02:00:00','2014-09-09 02:00:00'),(11,'f14000009','Funded','Fund','2014-09-02 02:00:00','2014-09-16 02:00:00');
/*!40000 ALTER TABLE `experience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faculty` (
  `_facultyID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_institution` bigint(20) unsigned NOT NULL,
  `_name` varchar(250) NOT NULL,
  PRIMARY KEY (`_facultyID`),
  KEY `_institution` (`_institution`),
  CONSTRAINT `faculty_ibfk_1` FOREIGN KEY (`_institution`) REFERENCES `institution` (`_institutionID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (1,1,'EBIT'),(2,1,'Humanities'),(3,2,'Health sciences');
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forward_and_rewind_report`
--

DROP TABLE IF EXISTS `forward_and_rewind_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forward_and_rewind_report` (
  `_reportID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_application` bigint(20) unsigned NOT NULL,
  `_dris` char(9) NOT NULL,
  `_timestamp` datetime NOT NULL,
  `_type` enum('Forward','Rewind') NOT NULL,
  `_reason` text,
  `_fromStatus` enum('Open','Submitted','Declined','Referred','Finalised','Recommended','Endorsed','Eligible','Funded','Completed','Terminated') DEFAULT NULL,
  `_toStatus` enum('Open','Submitted','Declined','Referred','Finalised','Recommended','Endorsed','Eligible','Funded','Completed','Terminated') DEFAULT NULL,
  PRIMARY KEY (`_reportID`),
  KEY `_application` (`_application`),
  KEY `_dris` (`_dris`),
  CONSTRAINT `forward_and_rewind_report_ibfk_1` FOREIGN KEY (`_application`) REFERENCES `application` (`_applicationID`),
  CONSTRAINT `forward_and_rewind_report_ibfk_2` FOREIGN KEY (`_dris`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forward_and_rewind_report`
--

LOCK TABLES `forward_and_rewind_report` WRITE;
/*!40000 ALTER TABLE `forward_and_rewind_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `forward_and_rewind_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funding_cost`
--

DROP TABLE IF EXISTS `funding_cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `funding_cost` (
  `_costID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_fundingReport` bigint(20) unsigned NOT NULL,
  `_amount` float DEFAULT NULL,
  `_provider` varchar(100) DEFAULT NULL,
  `_type` enum('Fellowship','Running','Travel','Equipment','Operating','Conference') DEFAULT NULL,
  PRIMARY KEY (`_costID`),
  KEY `_fundingReport` (`_fundingReport`),
  CONSTRAINT `funding_cost_ibfk_1` FOREIGN KEY (`_fundingReport`) REFERENCES `funding_report` (`_reportID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funding_cost`
--

LOCK TABLES `funding_cost` WRITE;
/*!40000 ALTER TABLE `funding_cost` DISABLE KEYS */;
/*!40000 ALTER TABLE `funding_cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funding_report`
--

DROP TABLE IF EXISTS `funding_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `funding_report` (
  `_reportID` bigint(20) unsigned NOT NULL,
  `_dris` char(9) NOT NULL,
  `_timestamp` datetime NOT NULL,
  PRIMARY KEY (`_reportID`),
  KEY `_dris` (`_dris`),
  CONSTRAINT `funding_report_ibfk_1` FOREIGN KEY (`_reportID`) REFERENCES `application` (`_applicationID`),
  CONSTRAINT `funding_report_ibfk_2` FOREIGN KEY (`_dris`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funding_report`
--

LOCK TABLES `funding_report` WRITE;
/*!40000 ALTER TABLE `funding_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `funding_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `institution`
--

DROP TABLE IF EXISTS `institution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `institution` (
  `_institutionID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_name` varchar(250) NOT NULL,
  PRIMARY KEY (`_institutionID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institution`
--

LOCK TABLES `institution` WRITE;
/*!40000 ALTER TABLE `institution` DISABLE KEYS */;
INSERT INTO `institution` VALUES (1,'University of Pretoria'),(2,'University of Cape Town');
/*!40000 ALTER TABLE `institution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `minute_comment`
--

DROP TABLE IF EXISTS `minute_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `minute_comment` (
  `_commentID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_meeting` bigint(20) unsigned NOT NULL,
  `_timestamp` datetime NOT NULL,
  `_attendee` char(9) NOT NULL,
  `_comment` varchar(500) NOT NULL,
  PRIMARY KEY (`_commentID`),
  KEY `_meeting` (`_meeting`),
  KEY `_attendee` (`_attendee`),
  CONSTRAINT `minute_comment_ibfk_1` FOREIGN KEY (`_meeting`) REFERENCES `committee_meeting` (`_meetingID`),
  CONSTRAINT `minute_comment_ibfk_2` FOREIGN KEY (`_attendee`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `minute_comment`
--

LOCK TABLES `minute_comment` WRITE;
/*!40000 ALTER TABLE `minute_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `minute_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `_notificationID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_subject` varchar(200) DEFAULT NULL,
  `_message` text,
  `_emailStatus` enum('Sent','Queued','Failed','Disabled') DEFAULT NULL,
  `_emailRetryCount` int(11) DEFAULT NULL,
  `_timestamp` datetime NOT NULL,
  `_sender` char(9) DEFAULT NULL,
  `_reciever` char(9) NOT NULL,
  PRIMARY KEY (`_notificationID`),
  KEY `_sender` (`_sender`),
  KEY `_reciever` (`_reciever`),
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`_sender`) REFERENCES `person` (`_systemID`),
  CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`_reciever`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,'Created account','Account has been created for you','Sent',0,'2014-09-21 20:31:45','f14000001','f14000001'),(2,'Created account','Account has been created for you','Sent',0,'2014-09-21 20:38:53','f14000002','f14000002'),(3,'Created account','Account has been created for you','Sent',0,'2014-09-21 20:55:54','f14000003','f14000003'),(4,'Application finalised','The following application has been finalised by Mr Test Tester. Please review for endorsement.','Sent',0,'2014-09-21 21:05:25','u12019837','u12019837'),(5,'Created account','Account has been created for you','Sent',0,'2014-09-21 21:08:20','f14000004','f14000004'),(6,'Application finalised','The following application has been finalised by Mr Test Tester. Please review for endorsement.','Sent',0,'2014-09-21 21:16:22','u12019837','u12019837'),(7,'Created account','Account has been created for you','Sent',0,'2014-09-26 01:57:30','f14000005','f14000005'),(8,'Created account','Account has been created for you','Sent',0,'2014-09-26 02:10:44','f14000006','f14000006'),(9,'Created account','Account has been created for you','Sent',0,'2014-09-26 02:20:53','f14000007','f14000007'),(10,'Created account','Account has been created for you','Sent',0,'2014-09-26 02:31:01','f14000008','f14000008'),(11,'Created account','Account has been created for you','Sent',0,'2014-09-26 02:40:35','f14000009','f14000009');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `_systemID` char(9) NOT NULL,
  `_password` varchar(50) NOT NULL,
  `_title` char(10) NOT NULL,
  `_fullName` varchar(250) NOT NULL,
  `_surname` varchar(250) NOT NULL,
  `_email` varchar(50) NOT NULL,
  `_telephoneNumber` char(20) DEFAULT NULL,
  `_workNumber` char(20) DEFAULT NULL,
  `_faxNumber` char(20) DEFAULT NULL,
  `_cellphoneNumber` char(20) DEFAULT NULL,
  `_addressLine1` bigint(20) unsigned DEFAULT NULL,
  `_upEmployee` tinyint(1) NOT NULL,
  `_accountStatus` enum('Active','Pending','Disabled','Dorment') DEFAULT NULL,
  PRIMARY KEY (`_systemID`),
  KEY `_addressLine1` (`_addressLine1`),
  CONSTRAINT `person_ibfk_1` FOREIGN KEY (`_addressLine1`) REFERENCES `address` (`_addressID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES ('f14000001','open','Mr.','Open','Tester','open@gmail.com','0123456789','0123456789','0123456789','0123456789',3,0,'Active'),('f14000002','referred','Mr.','Referred','Tester','referred@gmail.com','0123456789','0123456789','0123456789','0123456789',4,0,'Active'),('f14000003','finalised','Mr.','Finalised','Tester','finalised@gmail.com','0123456789','0123456789','0123456789','0123456789',5,0,'Active'),('f14000004','recommended','Mr.','Recommended','Tester','recommended@gmail.com','0123456789','0123456789','0123456789','0123456789',6,0,'Active'),('f14000005','endorsed','Mr.','Endorsed','Tester','endorsed@gmail.com','0123456789','0123456789','0123456789','0123456789',7,0,'Active'),('f14000006','eligible','Ms.','Eligible','Tester','eligible@gmail.com','0123456789','0123456789','0123456789','0123456789',8,0,'Active'),('f14000007','funded','Mr.','Funded','Tester','funded@gmail.com','0123456789','0123456789','0123456789','0123456789',9,0,'Active'),('f14000008','completed','Mr.','Completed','Tester','completed@gmail.com','0123456789','0123456789','0123456789','0123456789',10,0,'Active'),('f14000009','terminated','Mr.','Terminated','Tester','terminated@gmail.com','0123456789','0123456789','0123456789','0123456789',11,0,'Active'),('u12019837','test','Mr','Test','Tester','test@gmail.com',NULL,NULL,NULL,NULL,1,1,'Active');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_security_role`
--

DROP TABLE IF EXISTS `person_security_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_security_role` (
  `_personID` char(9) NOT NULL,
  `_roleID` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`_personID`,`_roleID`),
  KEY `_roleID` (`_roleID`),
  CONSTRAINT `person_security_role_ibfk_1` FOREIGN KEY (`_personID`) REFERENCES `person` (`_systemID`),
  CONSTRAINT `person_security_role_ibfk_2` FOREIGN KEY (`_roleID`) REFERENCES `security_role` (`_roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_security_role`
--

LOCK TABLES `person_security_role` WRITE;
/*!40000 ALTER TABLE `person_security_role` DISABLE KEYS */;
INSERT INTO `person_security_role` VALUES ('f14000001',1),('f14000002',1),('f14000003',1),('f14000004',1),('f14000005',1),('f14000006',1),('f14000007',1),('f14000008',1),('f14000009',1),('u12019837',1),('u12019837',2),('u12019837',3),('u12019837',4),('u12019837',5),('u12019837',6),('u12019837',7),('u12019837',8),('u12019837',9);
/*!40000 ALTER TABLE `person_security_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `progress_report`
--

DROP TABLE IF EXISTS `progress_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `progress_report` (
  `_reportID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_application` bigint(20) unsigned NOT NULL,
  `_timestamp` datetime NOT NULL,
  `_content` text NOT NULL,
  PRIMARY KEY (`_reportID`),
  KEY `_application` (`_application`),
  CONSTRAINT `progress_report_ibfk_1` FOREIGN KEY (`_application`) REFERENCES `application` (`_applicationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progress_report`
--

LOCK TABLES `progress_report` WRITE;
/*!40000 ALTER TABLE `progress_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `progress_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recommendation_report`
--

DROP TABLE IF EXISTS `recommendation_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recommendation_report` (
  `_reportID` bigint(20) unsigned NOT NULL,
  `_hod` char(9) NOT NULL,
  `_timestamp` datetime NOT NULL,
  `_content` text NOT NULL,
  PRIMARY KEY (`_reportID`),
  KEY `_hod` (`_hod`),
  CONSTRAINT `recommendation_report_ibfk_1` FOREIGN KEY (`_reportID`) REFERENCES `application` (`_applicationID`),
  CONSTRAINT `recommendation_report_ibfk_2` FOREIGN KEY (`_hod`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recommendation_report`
--

LOCK TABLES `recommendation_report` WRITE;
/*!40000 ALTER TABLE `recommendation_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `recommendation_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `referee_application`
--

DROP TABLE IF EXISTS `referee_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `referee_application` (
  `_refereeID` char(9) NOT NULL,
  `_applicationID` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`_refereeID`,`_applicationID`),
  KEY `_applicationID` (`_applicationID`),
  CONSTRAINT `referee_application_ibfk_1` FOREIGN KEY (`_refereeID`) REFERENCES `person` (`_systemID`),
  CONSTRAINT `referee_application_ibfk_2` FOREIGN KEY (`_applicationID`) REFERENCES `application` (`_applicationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referee_application`
--

LOCK TABLES `referee_application` WRITE;
/*!40000 ALTER TABLE `referee_application` DISABLE KEYS */;
/*!40000 ALTER TABLE `referee_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `referee_report`
--

DROP TABLE IF EXISTS `referee_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `referee_report` (
  `_reportID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_referee` char(9) NOT NULL,
  `_applicationID` bigint(20) unsigned NOT NULL,
  `_timestamp` datetime NOT NULL,
  `_content` text NOT NULL,
  PRIMARY KEY (`_reportID`),
  KEY `_applicationID` (`_applicationID`),
  KEY `_referee` (`_referee`),
  CONSTRAINT `referee_report_ibfk_1` FOREIGN KEY (`_applicationID`) REFERENCES `application` (`_applicationID`),
  CONSTRAINT `referee_report_ibfk_2` FOREIGN KEY (`_referee`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referee_report`
--

LOCK TABLES `referee_report` WRITE;
/*!40000 ALTER TABLE `referee_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `referee_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `research_fellow_information`
--

DROP TABLE IF EXISTS `research_fellow_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `research_fellow_information` (
  `_systemAssignedID` char(9) NOT NULL,
  `_institutionAssignedID` char(9) DEFAULT NULL,
  `_institutionAssignedEmail` varchar(50) DEFAULT NULL,
  `_department` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`_systemAssignedID`),
  KEY `_department` (`_department`),
  CONSTRAINT `research_fellow_information_ibfk_1` FOREIGN KEY (`_systemAssignedID`) REFERENCES `person` (`_systemID`),
  CONSTRAINT `research_fellow_information_ibfk_2` FOREIGN KEY (`_department`) REFERENCES `department` (`_departmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `research_fellow_information`
--

LOCK TABLES `research_fellow_information` WRITE;
/*!40000 ALTER TABLE `research_fellow_information` DISABLE KEYS */;
/*!40000 ALTER TABLE `research_fellow_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `security_role`
--

DROP TABLE IF EXISTS `security_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `security_role` (
  `_roleID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_name` varchar(150) NOT NULL,
  `_roleMask` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`_roleID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_role`
--

LOCK TABLES `security_role` WRITE;
/*!40000 ALTER TABLE `security_role` DISABLE KEYS */;
INSERT INTO `security_role` VALUES (1,'Prospective fellow',0),(2,'Referee',1),(3,'Research fellow',2),(4,'Grant holder',3),(5,'HOD',4),(6,'Dean\'s office member',5),(7,'DRIS member',6),(8,'Post doctoral Committee member',7),(9,'System administrator',8);
/*!40000 ALTER TABLE `security_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-26  6:07:19
