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
-- Table structure for table `RESOURCEENTITY`
--

DROP TABLE IF EXISTS `RESOURCEENTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESOURCEENTITY` (
  `il8n_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `i18n_key` varchar(255) DEFAULT NULL,
  `i18n_locale` varchar(255) DEFAULT NULL,
  `i18n_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`il8n_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESOURCEENTITY`
--

LOCK TABLES `RESOURCEENTITY` WRITE;
/*!40000 ALTER TABLE `RESOURCEENTITY` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESOURCEENTITY` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academic_qualification`
--

LOCK TABLES `academic_qualification` WRITE;
/*!40000 ALTER TABLE `academic_qualification` DISABLE KEYS */;
INSERT INTO `academic_qualification` VALUES (1,'f14000001','PhD','Endorsement','HardKnox','2014-10-02',0),(2,'f14000002','PhD','Endorsement','HardKnox','2014-10-15',0),(3,'f14000003','PhD','Open','HardKnox','2014-10-07',0),(4,'u01234567','PhD','Maths','UP','2010-10-05',0),(5,'f14000004','PhD','Open','Home School','2014-10-14',0),(6,'f14000005','PhD','Open','Home School','2014-10-07',0),(7,'f14000006','PhD','Open','HardKnox','2014-10-14',0),(8,'f14000007','PhD','Broad','Home School','2014-10-21',0),(9,'f14000008','PhD','Broad','Home School','2014-10-15',0),(10,'u12019837','PhD','Maths','UP','2014-10-13',0),(11,'f14000009','PhD','Open','Home School','2014-10-06',0),(12,'f14000010','PhD','Broad','Home School','2014-10-06',0),(13,'f14000011','PhD','Open','Home School','2014-10-13',0),(14,'f14000012','PhD','Open','Home School','2014-10-07',0),(15,'f14000013','PhD','Open','HardKnox','2014-10-06',0),(16,'f14000014','PhD','Open','Home School','2014-10-06',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'TestMainia',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'TestUniversity',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(4,'Testmania','Testonia','Testoniaville','Hatfield','Duncan',661,'72','0159'),(5,'Testmania','Testonia','Testoniaville','SemesterTest','ClassTest',120,'100','1234'),(6,'South Africa','Gauteng','Pretoria','Hatfield','1012',1012,'1231','1262'),(7,'Testmania','Testonia','Testoniaville','SemesterTest','ClassTest',120,'100','1234'),(8,'South Africa','Gauteng','Pretoria','Hatfield','1012',1012,'1231','1262'),(9,'Testmania','Testonia','Testoniaville','SemesterTest','ClassTest',120,'100','1234'),(10,'South Africa','Gauteng','Pretoria','Hatfield','1012',1012,'1231','1262'),(11,'Testmania','Testonia','Testoniaville','SemesterTest','ClassTest',120,'100','1234'),(12,'South Africa','Gauteng','Pretoria','Hatfield','1012',1012,'1231','1262'),(13,'Testmania','Testonia','Testoniaville','SemesterTest','ClassTest',120,'100','1234'),(14,'Testmania','Testonia','Testoniaville','SemesterTest','ClassTest',120,'100','1234'),(15,'South Africa','Gauteng','Pretoria','Hatfield','1012',1012,'1231','1262'),(16,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(17,'Testmania','Gauteng','Testoniaville','SemTest','ClassTest',661,'72','0159'),(18,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(19,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(20,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(21,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(22,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(23,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(24,'Testmania','Testonia','Testoniaville','SemTest','Duncan',661,'72','0159'),(25,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'72','0159'),(26,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',661,'111','0159'),(27,'Testmania','Testonia','Testoniaville','SemTest','ClassTest',11,'72','0159');
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
  `_headline` varchar(100) DEFAULT NULL,
  `_message` text NOT NULL,
  `_startDate` datetime DEFAULT NULL,
  `_endDate` datetime DEFAULT NULL,
  `_image` blob,
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
  `_imported` tinyint(1) DEFAULT NULL,
  `_type` varchar(40) DEFAULT NULL,
  `_status` varchar(40) DEFAULT NULL,
  `_fundingType` varchar(40) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (1,NULL,'New','Open','UP Postdoc','2014-10-15 00:47:06',NULL,NULL,NULL,NULL,'Prospect','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Well</projectMotivation><projectAims><aim>High</aim></projectAims><researchMethodology>Clean</researchMethodology><researchWorkPlan>Mind</researchWorkPlan><expectedOutcomes><outcome>High</outcome></expectedOutcomes><projectInfrastructureFunding>state</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Works</selfEvaluation></applicationInformation>','f14000002','u12019837',NULL),(2,NULL,'New','Funded','Externally funded','2014-10-15 00:53:01','2014-10-15 00:53:16','2014-10-15 01:38:38','2014-10-06','2016-10-06','Fellowship','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>While</projectMotivation><projectAims><aim>Proper</aim></projectAims><researchMethodology>Work</researchMethodology><researchWorkPlan>Needed</researchWorkPlan><expectedOutcomes><outcome>Proper</outcome></expectedOutcomes><projectInfrastructureFunding>yes</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Good</selfEvaluation></applicationInformation>','f14000001','u12019837',NULL),(3,NULL,'New','Referred','Externally funded','2014-10-15 01:04:04','2014-10-15 01:04:24',NULL,NULL,NULL,'Open(To Be Edited)','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Need to solve this problem</projectMotivation><projectAims><aim>Need to get it done</aim></projectAims><researchMethodology>Works</researchMethodology><researchWorkPlan>For me to know</researchWorkPlan><expectedOutcomes><outcome>Sleepless nights</outcome></expectedOutcomes><projectInfrastructureFunding>No flex zone</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Good</selfEvaluation></applicationInformation>','f14000003','u01234567',NULL),(4,NULL,'New','Referred','Externally funded','2014-10-15 01:15:15','2014-10-15 01:15:31',NULL,NULL,NULL,'Open(To Be Ammended)','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Gaining speed</projectMotivation><projectAims><aim>Knocking it out the park</aim></projectAims><researchMethodology>Works quite well</researchMethodology><researchWorkPlan>Misspleet</researchWorkPlan><expectedOutcomes><outcome>Home run</outcome></expectedOutcomes><projectInfrastructureFunding>I seee what you did there</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Slow</selfEvaluation></applicationInformation>','f14000004','u01234567',NULL),(5,NULL,'New','Referred','Externally funded','2014-10-15 01:18:35','2014-10-15 01:18:52',NULL,NULL,NULL,'Open(To Be Finalised)','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Good</projectMotivation><projectAims><aim>Surpurb</aim></projectAims><researchMethodology>Open</researchMethodology><researchWorkPlan>Mind</researchWorkPlan><expectedOutcomes><outcome>Jazz</outcome></expectedOutcomes><projectInfrastructureFunding>Thought</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Clean</selfEvaluation></applicationInformation>','f14000005','u01234567',NULL),(6,NULL,'New','Finalised','Externally funded','2014-10-15 01:24:22','2014-10-15 01:24:34','2014-10-15 01:36:09',NULL,NULL,'Recommend(To be approved)','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Busy</projectMotivation><projectAims><aim>Easy</aim></projectAims><researchMethodology>Recommendations</researchMethodology><researchWorkPlan>Chilled</researchWorkPlan><expectedOutcomes><outcome>Spreading out</outcome></expectedOutcomes><projectInfrastructureFunding>Broad</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Proper</selfEvaluation></applicationInformation>','f14000006','u12019837',NULL),(7,NULL,'New','Finalised','Externally funded','2014-10-15 01:27:53','2014-10-15 01:28:07','2014-10-15 01:37:50',NULL,NULL,'Recommend(To be denied)','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Grades</projectMotivation><projectAims><aim>Free</aim></projectAims><researchMethodology>Spread</researchMethodology><researchWorkPlan>Focus</researchWorkPlan><expectedOutcomes><outcome>Lost</outcome></expectedOutcomes><projectInfrastructureFunding>Pride</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Random</selfEvaluation></applicationInformation>','f14000007','u12019837',NULL),(8,NULL,'New','Finalised','Externally funded','2014-10-15 01:32:21','2014-10-15 01:32:34','2014-10-15 01:38:05',NULL,NULL,'Recommend(To be Ammended)','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Thought</projectMotivation><projectAims><aim>Free</aim></projectAims><researchMethodology>Head of Department</researchMethodology><researchWorkPlan>Understanding</researchWorkPlan><expectedOutcomes><outcome>In caged</outcome></expectedOutcomes><projectInfrastructureFunding>Independent</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Idea</selfEvaluation></applicationInformation>','f14000008','u12019837',NULL),(9,NULL,'New','Recommended','Externally funded','2014-10-15 01:49:28','2014-10-15 01:49:44','2014-10-15 02:00:36',NULL,NULL,'Dean Approval (To be approved)','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Splendid</projectMotivation><projectAims><aim>Okay</aim></projectAims><researchMethodology>Okay</researchMethodology><researchWorkPlan>Good Music and rusks</researchWorkPlan><expectedOutcomes><outcome>Okay</outcome></expectedOutcomes><projectInfrastructureFunding>Freeze</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Okay</selfEvaluation></applicationInformation>','f14000009','u12019837',NULL),(10,NULL,'New','Recommended','Externally funded','2014-10-15 01:52:59','2014-10-15 01:53:08','2014-10-15 02:00:20',NULL,NULL,'Fellowship','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Peace</projectMotivation><projectAims><aim>File</aim></projectAims><researchMethodology>Breathe</researchMethodology><researchWorkPlan>Bass</researchWorkPlan><expectedOutcomes><outcome>Size</outcome></expectedOutcomes><projectInfrastructureFunding>Snare</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Kick</selfEvaluation></applicationInformation>','f14000010','u12019837',NULL),(11,NULL,'New','Recommended','Externally funded','2014-10-15 01:56:27','2014-10-15 01:56:36','2014-10-15 02:00:49',NULL,NULL,'Dean Approval (To be Ammended)','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Okay</projectMotivation><projectAims><aim>Hit</aim></projectAims><researchMethodology>Okay</researchMethodology><researchWorkPlan>Slipped  a bit</researchWorkPlan><expectedOutcomes><outcome>Flow</outcome></expectedOutcomes><projectInfrastructureFunding>Listen</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Chris Tucker</selfEvaluation></applicationInformation>','f14000011','u12019837',NULL),(12,NULL,'New','Eligible','Externally funded','2014-10-15 02:20:50','2014-10-15 02:20:59','2014-10-15 02:31:01',NULL,NULL,'Eligibilty(To be approved)','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Success</projectMotivation><projectAims><aim>High</aim></projectAims><researchMethodology>Here</researchMethodology><researchWorkPlan>Fluid</researchWorkPlan><expectedOutcomes><outcome>Rooibos tea</outcome></expectedOutcomes><projectInfrastructureFunding>Repeating</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Tired</selfEvaluation></applicationInformation>','f14000012','u12019837',NULL),(13,NULL,'New','Endorsed','Externally funded','2014-10-15 02:24:23','2014-10-15 02:24:32','2014-10-15 02:31:13',NULL,NULL,'Eligibilty(To be denied)','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>Standing</projectMotivation><projectAims><aim>Okay</aim></projectAims><researchMethodology>Local</researchMethodology><researchWorkPlan>Job </researchWorkPlan><expectedOutcomes><outcome>Realise</outcome></expectedOutcomes><projectInfrastructureFunding>Work Ethic</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>Leading</selfEvaluation></applicationInformation>','f14000013','u12019837',NULL),(14,NULL,'New','Endorsed','Externally funded','2014-10-15 02:30:25','2014-10-15 02:30:34','2014-10-15 02:31:25',NULL,NULL,'Eligibilty(To be given eligibilty)','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><applicationInformation><projectMotivation>High</projectMotivation><projectAims><aim>High</aim></projectAims><researchMethodology>Medium</researchMethodology><researchWorkPlan>High</researchWorkPlan><expectedOutcomes><outcome>High</outcome></expectedOutcomes><projectInfrastructureFunding>Low</projectInfrastructureFunding><teamMembers><member><title>Mr.</title><fullName>Open</fullName><surname>Tester</surname><position>OPEN</position><institution>UP</institution><degreeType>Doctoral (PhD)</degreeType></member></teamMembers><selfEvaluation>High</selfEvaluation></applicationInformation>','f14000014','u12019837',NULL);
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
  `_type` varchar(40) NOT NULL DEFAULT '',
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
INSERT INTO `application_review_request` VALUES ('u12019837',6,'HOD'),('u12019837',7,'HOD'),('u12019837',8,'HOD'),('u12019837',9,'HOD'),('u12019837',10,'HOD'),('u12019837',11,'HOD');
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
) ENGINE=InnoDB AUTO_INCREMENT=354 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_log`
--

LOCK TABLES `audit_log` WRITE;
/*!40000 ALTER TABLE `audit_log` DISABLE KEYS */;
INSERT INTO `audit_log` VALUES (1,'u12019837','2014-10-14 23:01:11',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@193eb4d; ]'),(2,'u12019837','2014-10-14 23:11:11',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1c82f43; ]'),(3,'u12019837','2014-10-14 23:11:15',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@f4f9c7; ]'),(4,'u12019837','2014-10-14 23:14:46',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@13d13e; ]'),(5,'u12019837','2014-10-14 23:14:49',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d102c7; ]'),(6,'u12019837','2014-10-14 23:14:54',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@100220e; ]'),(7,'u12019837','2014-10-14 23:16:19',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1b35a8a; ]'),(8,'u12019837','2014-10-14 23:16:22',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1295187; ]'),(9,'u12019837','2014-10-14 23:18:40',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@aec19e; ]'),(10,'u12019837','2014-10-14 23:18:42',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@bfd325; ]'),(11,'u12019837','2014-10-14 23:20:07',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1646953; ]'),(12,'f14000001','2014-10-14 23:21:17','Created new user account'),(13,'f14000001','2014-10-14 23:21:17',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@8e32a4; ]'),(14,'f14000001','2014-10-14 23:21:22',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@13e3a8b; ]'),(15,'f14000002','2014-10-14 23:22:09','Created new user account'),(16,'f14000002','2014-10-14 23:22:09',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@990737; ]'),(17,'f14000002','2014-10-14 23:22:32',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@399e2b; ]'),(18,'u12019837','2014-10-14 23:23:18',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@73d4f1; ]'),(19,'u12019837','2014-10-14 23:23:20',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@410cdc; ]'),(20,'u12019837','2014-10-14 23:23:26',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@f3e60f; ]'),(21,'u12019837','2014-10-14 23:24:28','Created new user account'),(22,'u12019837','2014-10-14 23:24:28',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@dc8b39; ]'),(23,'u12019837','2014-10-14 23:24:56',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@dff15e; ]'),(24,'u12019837','2014-10-14 23:29:00',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@13bd7ef; ]'),(25,'u12019837','2014-10-14 23:29:02',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@6e26d6; ]'),(26,'u12019837','2014-10-14 23:30:19','Created new user account'),(27,'u12019837','2014-10-14 23:30:20',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@14fffbe; ]'),(28,'u12019837','2014-10-14 23:31:36','Created new user account'),(29,'u12019837','2014-10-14 23:31:36',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@19e5b05; ]'),(30,'u12019837','2014-10-14 23:32:03',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@14e75cf; ]'),(31,'u12019837','2014-10-14 23:33:28','Created new user account'),(32,'u12019837','2014-10-14 23:33:28',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@176c9d; ]'),(33,'u12019837','2014-10-14 23:35:09','Created new user account'),(34,'u12019837','2014-10-14 23:35:09',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@dfee8b; ]'),(35,'u12019837','2014-10-14 23:37:16','Created new user account'),(36,'u12019837','2014-10-14 23:37:16',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@10764cf; ]'),(37,'u12019837','2014-10-14 23:37:30',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@9f9ef4; ]'),(38,'u12019837','2014-10-14 23:38:07',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1deaade; ]'),(39,'u12019837','2014-10-14 23:38:29',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@f99964; ]'),(40,'u12019837','2014-10-14 23:40:23',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@58c231; ]'),(41,'u12019837','2014-10-14 23:40:31',' [ Method = getAllApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@a01534; ]'),(42,'u12019837','2014-10-14 23:40:39',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@15cdcd; ]'),(43,'f14000002','2014-10-14 23:55:09',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@10663c8; ] [ Exception = com.softserve.auxiliary.Exceptions.AuthenticationException: User\'s username or password does not match]'),(44,'f14000002','2014-10-14 23:55:23',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@43a029; ] [ Exception = com.softserve.auxiliary.Exceptions.AuthenticationException: User\'s username or password does not match]'),(45,'f14000002','2014-10-14 23:55:50',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1df1f71; ] [ Exception = com.softserve.auxiliary.Exceptions.AuthenticationException: User\'s username or password does not match]'),(46,'u12019837','2014-10-14 23:56:02',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@6240cb; ]'),(47,'u12019837','2014-10-14 23:56:09',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@bf1088; ]'),(48,'u12019837','2014-10-14 23:56:14',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1510400; ]'),(49,'u12019837','2014-10-14 23:56:17',' [ Method = viewAllUserAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@3e7b4c; ]'),(50,'u12019837','2014-10-14 23:56:39',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1370a6; ]'),(51,'f14000001','2014-10-14 23:57:23',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@170b771; ]'),(52,'f14000001','2014-10-14 23:57:27',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@bf1242; ]'),(53,'f14000001','2014-10-14 23:58:34',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1765fc4; com.softserve.DBEntities.Cv[ cvID=f14000001 ]; ]'),(54,'f14000001','2014-10-14 23:58:34','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1765fc4; com.softserve.DBEntities.Cv[ cvID=f14000001 ]; ]'),(55,'f14000001','2014-10-14 23:58:34','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1765fc4; com.softserve.DBEntities.Cv[ cvID=f14000001 ]; ]'),(56,'u12019837','2014-10-15 00:00:00',' [ Method = getAllActiveMeetingsForWhichUserIsToAttend ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1819a10; ]'),(57,'u12019837','2014-10-15 00:00:00',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@49b1e9; ]'),(58,'u12019837','2014-10-15 00:00:00',' [ Method = getRenewableApplicationsForFellow ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d9f9cd; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(59,'u12019837','2014-10-15 00:00:00',' [ Method = loadAllPendingOnDemandAccounts ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@47f153; ]'),(60,'u12019837','2014-10-15 00:00:53',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@a18ef8; ]'),(61,'u12019837','2014-10-15 00:00:56',' [ Method = loadAllAuditLogEntries ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@5c4ed1; ]'),(62,'u12019837','2014-10-15 00:01:18',' [ Method = loadAllAuditLogEntries ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1de4afa; ]'),(63,'u12019837','2014-10-15 00:01:30',' [ Method = loadAllAuditLogEntries ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@3ea7c5; ]'),(64,'u12019837','2014-10-15 00:03:18',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@ecb031; ]'),(65,'u12019837','2014-10-15 00:44:19',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@bc9ef3; ]'),(66,'u12019837','2014-10-15 00:44:24',' [ Method = getAllApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@75dede; ]'),(67,'u12019837','2014-10-15 00:44:32',' [ Method = allApplicationsWithPendingReportsForUser ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@d9746b; ]'),(68,'u12019837','2014-10-15 00:44:36',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@123fa94; ]'),(69,'f14000002','2014-10-15 00:44:47',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1408c2b; ]'),(70,'f14000002','2014-10-15 00:44:53',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@79221e; ]'),(71,'f14000002','2014-10-15 00:45:50',' [ Method = searchGoogleScholarUsing ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@6ef44f; com.softserve.auxiliary.HTTPScrapers.GoogleScholarQuery@1df3a2a; ]'),(72,'f14000002','2014-10-15 00:46:10',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@12d0ba3; com.softserve.DBEntities.Cv[ cvID=f14000002 ]; ]'),(73,'f14000002','2014-10-15 00:46:10','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@12d0ba3; com.softserve.DBEntities.Cv[ cvID=f14000002 ]; ]'),(74,'f14000002','2014-10-15 00:46:10','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@12d0ba3; com.softserve.DBEntities.Cv[ cvID=f14000002 ]; ]'),(75,'f14000002','2014-10-15 00:47:07','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1521ea8; com.softserve.DBEntities.Application[ applicationID=1 ]; ]'),(76,'f14000002','2014-10-15 00:47:48','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1787d4b; com.softserve.DBEntities.Application[ applicationID=1 ]; ]'),(77,'f14000002','2014-10-15 00:48:26','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@67178; com.softserve.DBEntities.Application[ applicationID=1 ]; ]'),(78,'f14000001','2014-10-15 00:50:21',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@b6dc28; ]'),(79,'f14000001','2014-10-15 00:50:26',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@101ea57; ]'),(80,'f14000001','2014-10-15 00:50:31',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@4a96d3; com.softserve.DBEntities.Cv[ cvID=f14000001 ]; ]'),(81,'f14000001','2014-10-15 00:50:31','Updated CV [ Method = updateCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@4a96d3; com.softserve.DBEntities.Cv[ cvID=f14000001 ]; ]'),(82,'f14000001','2014-10-15 00:50:31','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@4a96d3; com.softserve.DBEntities.Cv[ cvID=f14000001 ]; ]'),(83,'f14000001','2014-10-15 00:51:56',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@5dc54d; ]'),(84,'f14000001','2014-10-15 00:52:00',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1461ff8; ]'),(85,'f14000001','2014-10-15 00:52:06',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@69c0b8; com.softserve.DBEntities.Cv[ cvID=f14000001 ]; ]'),(86,'f14000001','2014-10-15 00:52:06','Updated CV [ Method = updateCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@69c0b8; com.softserve.DBEntities.Cv[ cvID=f14000001 ]; ]'),(87,'f14000001','2014-10-15 00:52:06','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@69c0b8; com.softserve.DBEntities.Cv[ cvID=f14000001 ]; ]'),(88,'f14000001','2014-10-15 00:53:01','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1848be1; com.softserve.DBEntities.Application[ applicationID=2 ]; ]'),(89,'f14000001','2014-10-15 00:53:16','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@fd35e6; com.softserve.DBEntities.Application[ applicationID=2 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(90,'f14000001','2014-10-15 00:53:16','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@d5feeb; com.softserve.DBEntities.Application[ applicationID=2 ]; ]'),(91,'f14000001','2014-10-15 00:55:21',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@15761bd; ]'),(92,'f14000003','2014-10-15 00:56:17','Created new user account'),(93,'f14000003','2014-10-15 00:56:17',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@116cdca; ]'),(94,'f14000003','2014-10-15 00:56:31',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1a95fa4; ]'),(95,'f14000003','2014-10-15 00:58:03',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1ae55af; com.softserve.DBEntities.Cv[ cvID=f14000003 ]; ]'),(96,'f14000003','2014-10-15 00:58:04','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1ae55af; com.softserve.DBEntities.Cv[ cvID=f14000003 ]; ]'),(97,'f14000003','2014-10-15 00:58:04','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1ae55af; com.softserve.DBEntities.Cv[ cvID=f14000003 ]; ]'),(98,'f14000003','2014-10-15 01:00:17',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@116408e; ]'),(99,'f14000003','2014-10-15 01:00:20',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@102a950; ]'),(100,'f14000003','2014-10-15 01:01:09',' [ Method = searchGoogleScholarUsing ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@126ab3a; com.softserve.auxiliary.HTTPScrapers.GoogleScholarQuery@14fa81c; ]'),(101,'f14000003','2014-10-15 01:01:10',' [ Method = searchGoogleScholarUsing ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@19fc289; com.softserve.auxiliary.HTTPScrapers.GoogleScholarQuery@33ce42; ]'),(102,'f14000003','2014-10-15 01:01:24',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1453d75; com.softserve.DBEntities.Cv[ cvID=f14000003 ]; ]'),(103,'f14000003','2014-10-15 01:01:24','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1453d75; com.softserve.DBEntities.Cv[ cvID=f14000003 ]; ] [ Exception = com.softserve.persistence.DBDAO.exceptions.RollbackFailureException: An error occurred attempting to roll back the transaction.]'),(104,'f14000003','2014-10-15 01:01:24','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1453d75; com.softserve.DBEntities.Cv[ cvID=f14000003 ]; ] [ Exception = com.softserve.persistence.DBDAO.exceptions.RollbackFailureException: An error occurred attempting to roll back the transaction.]'),(105,'f14000003','2014-10-15 01:02:23',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1cc1ae2; ]'),(106,'f14000003','2014-10-15 01:02:30',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@14b1537; ]'),(107,'f14000003','2014-10-15 01:02:33',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1ae31bf; com.softserve.DBEntities.Cv[ cvID=f14000003 ]; ]'),(108,'f14000003','2014-10-15 01:02:34','Updated CV [ Method = updateCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1ae31bf; com.softserve.DBEntities.Cv[ cvID=f14000003 ]; ]'),(109,'f14000003','2014-10-15 01:02:34','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1ae31bf; com.softserve.DBEntities.Cv[ cvID=f14000003 ]; ]'),(110,'f14000003','2014-10-15 01:04:04','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@10fb85e; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(111,'f14000003','2014-10-15 01:04:24','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@fc1f9b; com.softserve.DBEntities.Application[ applicationID=3 ]; com.softserve.DBEntities.Person[ systemID=u01234567 ]; ]'),(112,'f14000003','2014-10-15 01:04:24','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1f6afbe; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(113,'f14000003','2014-10-15 01:04:30',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@b3c172; ]'),(114,'u01234567','2014-10-15 01:04:52',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@6f9559; ]'),(115,'u01234567','2014-10-15 01:05:02',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1c8f3bd; 0; 2147483647; ]'),(116,'u01234567','2014-10-15 01:05:24',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@d89a0c; 0; 2147483647; ]'),(117,'u01234567','2014-10-15 01:05:32',' [ Method = getHODsOfApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@d84b29; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(118,'u01234567','2014-10-15 01:08:19',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@186472d; com.softserve.DBEntities.Cv[ cvID=u01234567 ]; ]'),(119,'u01234567','2014-10-15 01:08:19','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@186472d; com.softserve.DBEntities.Cv[ cvID=u01234567 ]; ]'),(120,'u01234567','2014-10-15 01:08:19',' [ Method = createGrantHolderCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@186472d; com.softserve.DBEntities.Cv[ cvID=u01234567 ]; ]'),(121,'u01234567','2014-10-15 01:08:28',' [ Method = getHODsOfApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@45d3b6; com.softserve.DBEntities.Application[ applicationID=3 ]; ]'),(122,'u01234567','2014-10-15 01:08:28',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1733f55; ]'),(123,'f14000004','2014-10-15 01:09:34','Created new user account'),(124,'f14000004','2014-10-15 01:09:34',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@40675a; ]'),(125,'f14000004','2014-10-15 01:09:49',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@2b9422; ]'),(126,'f14000004','2014-10-15 01:10:59',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@e013af; com.softserve.DBEntities.Cv[ cvID=f14000004 ]; ]'),(127,'f14000004','2014-10-15 01:11:00','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@e013af; com.softserve.DBEntities.Cv[ cvID=f14000004 ]; ]'),(128,'f14000004','2014-10-15 01:11:00','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@e013af; com.softserve.DBEntities.Cv[ cvID=f14000004 ]; ]'),(129,'f14000004','2014-10-15 01:12:55',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@9c24e2; ]'),(130,'f14000004','2014-10-15 01:12:58',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@cafe28; ]'),(131,'f14000004','2014-10-15 01:13:29',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@18f5180; ]'),(132,'f14000004','2014-10-15 01:13:35',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@dc001c; ]'),(133,'f14000004','2014-10-15 01:13:38',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@bee006; com.softserve.DBEntities.Cv[ cvID=f14000004 ]; ]'),(134,'f14000004','2014-10-15 01:13:38','Updated CV [ Method = updateCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@bee006; com.softserve.DBEntities.Cv[ cvID=f14000004 ]; ]'),(135,'f14000004','2014-10-15 01:13:38','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@bee006; com.softserve.DBEntities.Cv[ cvID=f14000004 ]; ]'),(136,'f14000004','2014-10-15 01:15:15','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@228154; com.softserve.DBEntities.Application[ applicationID=4 ]; ]'),(137,'f14000004','2014-10-15 01:15:31','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@7aa0e; com.softserve.DBEntities.Application[ applicationID=4 ]; com.softserve.DBEntities.Person[ systemID=u01234567 ]; ]'),(138,'f14000004','2014-10-15 01:15:31','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@104c3f8; com.softserve.DBEntities.Application[ applicationID=4 ]; ]'),(139,'f14000004','2014-10-15 01:15:35',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@10e4898; ]'),(140,'f14000005','2014-10-15 01:16:18','Created new user account'),(141,'f14000005','2014-10-15 01:16:18',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@e87c15; ]'),(142,'f14000005','2014-10-15 01:16:23',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1546a41; ]'),(143,'f14000005','2014-10-15 01:17:21',' [ Method = searchGoogleScholarUsing ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@167065; com.softserve.auxiliary.HTTPScrapers.GoogleScholarQuery@155a960; ]'),(144,'f14000005','2014-10-15 01:17:37',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@6a0c02; com.softserve.DBEntities.Cv[ cvID=f14000005 ]; ]'),(145,'f14000005','2014-10-15 01:17:38','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@6a0c02; com.softserve.DBEntities.Cv[ cvID=f14000005 ]; ]'),(146,'f14000005','2014-10-15 01:17:38','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@6a0c02; com.softserve.DBEntities.Cv[ cvID=f14000005 ]; ]'),(147,'f14000005','2014-10-15 01:18:35','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@194cc04; com.softserve.DBEntities.Application[ applicationID=5 ]; ]'),(148,'f14000005','2014-10-15 01:18:51','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@15d809a; com.softserve.DBEntities.Application[ applicationID=5 ]; com.softserve.DBEntities.Person[ systemID=u01234567 ]; ]'),(149,'f14000005','2014-10-15 01:18:52','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@bf04aa; com.softserve.DBEntities.Application[ applicationID=5 ]; ]'),(150,'f14000006','2014-10-15 01:21:53','Created new user account'),(151,'f14000006','2014-10-15 01:21:53',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1dcfa28; ]'),(152,'f14000006','2014-10-15 01:22:07',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1925a89; ]'),(153,'f14000006','2014-10-15 01:23:03',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@39bb82; com.softserve.DBEntities.Cv[ cvID=f14000006 ]; ]'),(154,'f14000006','2014-10-15 01:23:03','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@39bb82; com.softserve.DBEntities.Cv[ cvID=f14000006 ]; ]'),(155,'f14000006','2014-10-15 01:23:03','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@39bb82; com.softserve.DBEntities.Cv[ cvID=f14000006 ]; ]'),(156,'f14000006','2014-10-15 01:24:23','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@153ad80; com.softserve.DBEntities.Application[ applicationID=6 ]; ]'),(157,'f14000006','2014-10-15 01:24:34','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@d7979c; com.softserve.DBEntities.Application[ applicationID=6 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(158,'f14000006','2014-10-15 01:24:34','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1f065a; com.softserve.DBEntities.Application[ applicationID=6 ]; ]'),(159,'f14000006','2014-10-15 01:24:43',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@e06232; ]'),(160,'f14000007','2014-10-15 01:25:30','Created new user account'),(161,'f14000007','2014-10-15 01:25:30',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@bc974f; ]'),(162,'f14000007','2014-10-15 01:25:50',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@3decd0; ]'),(163,'f14000007','2014-10-15 01:26:47',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@12ce8d3; com.softserve.DBEntities.Cv[ cvID=f14000007 ]; ]'),(164,'f14000007','2014-10-15 01:26:47','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@12ce8d3; com.softserve.DBEntities.Cv[ cvID=f14000007 ]; ]'),(165,'f14000007','2014-10-15 01:26:47','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@12ce8d3; com.softserve.DBEntities.Cv[ cvID=f14000007 ]; ]'),(166,'f14000007','2014-10-15 01:27:54','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@5a454c; com.softserve.DBEntities.Application[ applicationID=7 ]; ]'),(167,'f14000007','2014-10-15 01:28:07','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@137447a; com.softserve.DBEntities.Application[ applicationID=7 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(168,'f14000007','2014-10-15 01:28:07','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1c0951f; com.softserve.DBEntities.Application[ applicationID=7 ]; ]'),(169,'f14000007','2014-10-15 01:28:11',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1b6b685; ]'),(170,'f14000008','2014-10-15 01:29:19','Created new user account'),(171,'f14000008','2014-10-15 01:29:19',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@b437fa; ]'),(172,'f14000008','2014-10-15 01:29:45',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@34ee7a; ]'),(173,'f14000008','2014-10-15 01:31:00',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@6f0208; com.softserve.DBEntities.Cv[ cvID=f14000008 ]; ]'),(174,'f14000008','2014-10-15 01:31:00','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@6f0208; com.softserve.DBEntities.Cv[ cvID=f14000008 ]; ]'),(175,'f14000008','2014-10-15 01:31:00','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@6f0208; com.softserve.DBEntities.Cv[ cvID=f14000008 ]; ]'),(176,'f14000008','2014-10-15 01:32:21','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@59d3a8; com.softserve.DBEntities.Application[ applicationID=8 ]; ]'),(177,'f14000008','2014-10-15 01:32:34','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@117c6ae; com.softserve.DBEntities.Application[ applicationID=8 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(178,'f14000008','2014-10-15 01:32:34','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@185f6b0; com.softserve.DBEntities.Application[ applicationID=8 ]; ]'),(179,'f14000008','2014-10-15 01:33:07',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@ec8e5c; ]'),(180,'f14000008','2014-10-15 01:33:19',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1a5204f; ]'),(181,'f14000008','2014-10-15 01:33:19',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1a51127; ]'),(182,'u12019837','2014-10-15 01:33:26',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@10e183d; ]'),(183,'u12019837','2014-10-15 01:33:31',' [ Method = getAllApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@13a2057; ]'),(184,'u12019837','2014-10-15 01:34:07',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@13a05a5; 0; 2147483647; ]'),(185,'u12019837','2014-10-15 01:34:49',' [ Method = getHODsOfApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d34ee8; com.softserve.DBEntities.Application[ applicationID=6 ]; ]'),(186,'u12019837','2014-10-15 01:35:32',' [ Method = searchGoogleScholarUsing ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@dc4a45; com.softserve.auxiliary.HTTPScrapers.GoogleScholarQuery@abbb91; ]'),(187,'u12019837','2014-10-15 01:35:53',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@4a4aa2; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(188,'u12019837','2014-10-15 01:35:53','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@4a4aa2; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(189,'u12019837','2014-10-15 01:35:53',' [ Method = createGrantHolderCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@4a4aa2; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(190,'u12019837','2014-10-15 01:35:56',' [ Method = saveChangesToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1149e4d; com.softserve.DBEntities.Application[ applicationID=6 ]; ]'),(191,'u12019837','2014-10-15 01:36:09',' [ Method = requestSpecificHODtoReview ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1ec5de0; com.softserve.DBEntities.Application[ applicationID=6 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(192,'u12019837','2014-10-15 01:36:10',' [ Method = finaliseApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1ec5de0; com.softserve.DBEntities.Application[ applicationID=6 ]; ]'),(193,'u12019837','2014-10-15 01:36:10',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@3a8401; 0; 2147483647; ]'),(194,'u12019837','2014-10-15 01:36:15',' [ Method = getHODsOfApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1ce79dc; com.softserve.DBEntities.Application[ applicationID=7 ]; ]'),(195,'u12019837','2014-10-15 01:37:28',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1e585; ]'),(196,'u12019837','2014-10-15 01:37:36',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1a9e328; 0; 2147483647; ]'),(197,'u12019837','2014-10-15 01:37:40',' [ Method = getHODsOfApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@14a4196; com.softserve.DBEntities.Application[ applicationID=7 ]; ]'),(198,'u12019837','2014-10-15 01:37:44',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@adc59e; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(199,'u12019837','2014-10-15 01:37:44','Updated CV [ Method = updateCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@adc59e; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(200,'u12019837','2014-10-15 01:37:44',' [ Method = createGrantHolderCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@adc59e; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(201,'u12019837','2014-10-15 01:37:46',' [ Method = saveChangesToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1905e6d; com.softserve.DBEntities.Application[ applicationID=7 ]; ]'),(202,'u12019837','2014-10-15 01:37:50',' [ Method = requestSpecificHODtoReview ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@369f3d; com.softserve.DBEntities.Application[ applicationID=7 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(203,'u12019837','2014-10-15 01:37:50',' [ Method = finaliseApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@369f3d; com.softserve.DBEntities.Application[ applicationID=7 ]; ]'),(204,'u12019837','2014-10-15 01:37:50',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1855117; 0; 2147483647; ]'),(205,'u12019837','2014-10-15 01:37:54',' [ Method = getHODsOfApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1c25b6c; com.softserve.DBEntities.Application[ applicationID=8 ]; ]'),(206,'u12019837','2014-10-15 01:37:58',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@be26ea; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(207,'u12019837','2014-10-15 01:37:58','Updated CV [ Method = updateCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@be26ea; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(208,'u12019837','2014-10-15 01:37:58',' [ Method = createGrantHolderCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@be26ea; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(209,'u12019837','2014-10-15 01:38:01',' [ Method = saveChangesToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@4b9dfd; com.softserve.DBEntities.Application[ applicationID=8 ]; ]'),(210,'u12019837','2014-10-15 01:38:05',' [ Method = requestSpecificHODtoReview ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@8b24; com.softserve.DBEntities.Application[ applicationID=8 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(211,'u12019837','2014-10-15 01:38:05',' [ Method = finaliseApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@8b24; com.softserve.DBEntities.Application[ applicationID=8 ]; ]'),(212,'u12019837','2014-10-15 01:38:05',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@5c8f32; 0; 2147483647; ]'),(213,'u12019837','2014-10-15 01:38:19',' [ Method = loadMovableApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@12565a7; ]'),(214,'u12019837','2014-10-15 01:38:38','Application forwared [ Method = forwardApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1633591; com.softserve.DBEntities.Application[ applicationID=2 ]; Endorsed; Test input; ]'),(215,'u12019837','2014-10-15 01:38:58',' [ Method = loadPendingEndorsedApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@17a2b3f; 0; 2147483647; ]'),(216,'u12019837','2014-10-15 01:38:58',' [ Method = loadPendingEligibleApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1eecf91; 0; 2147483647; ]'),(217,'u12019837','2014-10-15 01:38:58',' [ Method = loadFundedApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@32a8f2; 0; 2147483647; ]'),(218,'u12019837','2014-10-15 01:39:07',' [ Method = checkApplicationForEligiblity ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1501442; com.softserve.DBEntities.Application[ applicationID=2 ]; ]'),(219,'u12019837','2014-10-15 01:39:35',' [ Method = setApplicationEligibleStatus ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@19143b8; com.softserve.DBEntities.Application[ applicationID=2 ]; true; ]'),(220,'u12019837','2014-10-15 01:39:35',' [ Method = loadPendingEndorsedApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1c68c81; 0; 2147483647; ]'),(221,'u12019837','2014-10-15 01:39:35',' [ Method = loadPendingEligibleApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1e1a0a4; 0; 2147483647; ]'),(222,'u12019837','2014-10-15 01:39:35',' [ Method = loadFundedApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@10cd7c8; 0; 2147483647; ]'),(223,'u12019837','2014-10-15 01:42:57',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@eb174d; ]'),(224,'u12019837','2014-10-15 01:43:03',' [ Method = loadPendingEndorsedApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@c719f6; 0; 2147483647; ]'),(225,'u12019837','2014-10-15 01:43:03',' [ Method = loadPendingEligibleApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1c5c354; 0; 2147483647; ]'),(226,'u12019837','2014-10-15 01:43:03',' [ Method = loadFundedApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@c24e6f; 0; 2147483647; ]'),(227,'u12019837','2014-10-15 01:44:02',' [ Method = approveFunding ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1da8762; com.softserve.DBEntities.Application[ applicationID=2 ]; com.softserve.DBEntities.ResearchFellowInformation[ systemAssignedID=f14000001 ]; com.softserve.DBEntities.FundingReport[ reportID=2 ]; ; com.softserve.DBEntities.Notification[ notificationID=null ]; com.softserve.DBEntities.Notification[ notificationID=null ]; ]'),(228,'u12019837','2014-10-15 01:44:02',' [ Method = loadPendingEndorsedApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@4dc59; 0; 2147483647; ]'),(229,'u12019837','2014-10-15 01:44:02',' [ Method = loadPendingEligibleApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@17ae69b; 0; 2147483647; ]'),(230,'u12019837','2014-10-15 01:44:02',' [ Method = loadFundedApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@117144; 0; 2147483647; ]'),(231,'u12019837','2014-10-15 01:44:59',' [ Method = getAllApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@162df6a; ]'),(232,'u12019837','2014-10-15 01:45:14',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@dcb96e; ]'),(233,'f14000009','2014-10-15 01:46:35','Created new user account'),(234,'f14000009','2014-10-15 01:46:35',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1fcb196; ]'),(235,'f14000009','2014-10-15 01:46:59',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@163fc5c; ]'),(236,'f14000009','2014-10-15 01:47:54',' [ Method = searchGoogleScholarUsing ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@367ba5; com.softserve.auxiliary.HTTPScrapers.GoogleScholarQuery@ae527e; ]'),(237,'f14000009','2014-10-15 01:48:12',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d02bbc; com.softserve.DBEntities.Cv[ cvID=f14000009 ]; ]'),(238,'f14000009','2014-10-15 01:48:12','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d02bbc; com.softserve.DBEntities.Cv[ cvID=f14000009 ]; ]'),(239,'f14000009','2014-10-15 01:48:12','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d02bbc; com.softserve.DBEntities.Cv[ cvID=f14000009 ]; ]'),(240,'f14000009','2014-10-15 01:49:28','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@ce0db2; com.softserve.DBEntities.Application[ applicationID=9 ]; ]'),(241,'f14000009','2014-10-15 01:49:44','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@629202; com.softserve.DBEntities.Application[ applicationID=9 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(242,'f14000009','2014-10-15 01:49:44','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@add27a; com.softserve.DBEntities.Application[ applicationID=9 ]; ]'),(243,'f14000009','2014-10-15 01:49:50',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1e2b55; ]'),(244,'f14000010','2014-10-15 01:50:36','Created new user account'),(245,'f14000010','2014-10-15 01:50:36',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1aab092; ]'),(246,'f14000010','2014-10-15 01:50:59',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@127473; ]'),(247,'f14000010','2014-10-15 01:51:56',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@7aac1a; com.softserve.DBEntities.Cv[ cvID=f14000010 ]; ]'),(248,'f14000010','2014-10-15 01:51:56','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@7aac1a; com.softserve.DBEntities.Cv[ cvID=f14000010 ]; ]'),(249,'f14000010','2014-10-15 01:51:56','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@7aac1a; com.softserve.DBEntities.Cv[ cvID=f14000010 ]; ]'),(250,'f14000010','2014-10-15 01:52:59','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1a61149; com.softserve.DBEntities.Application[ applicationID=10 ]; ]'),(251,'f14000010','2014-10-15 01:53:08','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1b8311; com.softserve.DBEntities.Application[ applicationID=10 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(252,'f14000010','2014-10-15 01:53:08','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@11db5a9; com.softserve.DBEntities.Application[ applicationID=10 ]; ]'),(253,'f14000010','2014-10-15 01:53:12',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@db4f67; ]'),(254,'f14000011','2014-10-15 01:54:07','Created new user account'),(255,'f14000011','2014-10-15 01:54:08',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1069dc5; ]'),(256,'f14000011','2014-10-15 01:54:17',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@15a9530; ]'),(257,'f14000011','2014-10-15 01:55:12',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@109be5b; com.softserve.DBEntities.Cv[ cvID=f14000011 ]; ]'),(258,'f14000011','2014-10-15 01:55:12','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@109be5b; com.softserve.DBEntities.Cv[ cvID=f14000011 ]; ]'),(259,'f14000011','2014-10-15 01:55:12','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@109be5b; com.softserve.DBEntities.Cv[ cvID=f14000011 ]; ]'),(260,'f14000011','2014-10-15 01:56:28','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@d9e6fb; com.softserve.DBEntities.Application[ applicationID=11 ]; ]'),(261,'f14000011','2014-10-15 01:56:36','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@3b7ee5; com.softserve.DBEntities.Application[ applicationID=11 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(262,'f14000011','2014-10-15 01:56:36','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@e3c3b; com.softserve.DBEntities.Application[ applicationID=11 ]; ]'),(263,'u12019837','2014-10-15 01:58:27',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@191717d; ]'),(264,'u12019837','2014-10-15 01:58:34',' [ Method = getAllApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@dcd8a3; ]'),(265,'u12019837','2014-10-15 01:58:55',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@706b70; 0; 2147483647; ]'),(266,'u12019837','2014-10-15 01:59:05',' [ Method = getHODsOfApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@617bf3; com.softserve.DBEntities.Application[ applicationID=10 ]; ]'),(267,'u12019837','2014-10-15 01:59:08',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1355a87; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(268,'u12019837','2014-10-15 01:59:08','Updated CV [ Method = updateCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1355a87; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(269,'u12019837','2014-10-15 01:59:08',' [ Method = createGrantHolderCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1355a87; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(270,'u12019837','2014-10-15 01:59:36',' [ Method = saveChangesToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@179c311; com.softserve.DBEntities.Application[ applicationID=10 ]; ]'),(271,'u12019837','2014-10-15 02:00:19',' [ Method = requestSpecificHODtoReview ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1f3ddd1; com.softserve.DBEntities.Application[ applicationID=10 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(272,'u12019837','2014-10-15 02:00:20',' [ Method = finaliseApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1f3ddd1; com.softserve.DBEntities.Application[ applicationID=10 ]; ]'),(273,'u12019837','2014-10-15 02:00:20',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1be87e4; 0; 2147483647; ]'),(274,'u12019837','2014-10-15 02:00:26',' [ Method = getHODsOfApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d7681e; com.softserve.DBEntities.Application[ applicationID=9 ]; ]'),(275,'u12019837','2014-10-15 02:00:29',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@e9f6e5; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(276,'u12019837','2014-10-15 02:00:29','Updated CV [ Method = updateCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@e9f6e5; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(277,'u12019837','2014-10-15 02:00:29',' [ Method = createGrantHolderCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@e9f6e5; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(278,'u12019837','2014-10-15 02:00:31',' [ Method = saveChangesToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1b6a8d4; com.softserve.DBEntities.Application[ applicationID=9 ]; ]'),(279,'u12019837','2014-10-15 02:00:36',' [ Method = requestSpecificHODtoReview ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@19c02d4; com.softserve.DBEntities.Application[ applicationID=9 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(280,'u12019837','2014-10-15 02:00:36',' [ Method = finaliseApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@19c02d4; com.softserve.DBEntities.Application[ applicationID=9 ]; ]'),(281,'u12019837','2014-10-15 02:00:36',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d7c9f0; 0; 2147483647; ]'),(282,'u12019837','2014-10-15 02:00:40',' [ Method = getHODsOfApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1edae11; com.softserve.DBEntities.Application[ applicationID=11 ]; ]'),(283,'u12019837','2014-10-15 02:00:43',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1726132; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(284,'u12019837','2014-10-15 02:00:43','Updated CV [ Method = updateCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1726132; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(285,'u12019837','2014-10-15 02:00:43',' [ Method = createGrantHolderCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1726132; com.softserve.DBEntities.Cv[ cvID=u12019837 ]; ]'),(286,'u12019837','2014-10-15 02:00:45',' [ Method = saveChangesToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@747557; com.softserve.DBEntities.Application[ applicationID=11 ]; ]'),(287,'u12019837','2014-10-15 02:00:49',' [ Method = requestSpecificHODtoReview ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@134b92c; com.softserve.DBEntities.Application[ applicationID=11 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(288,'u12019837','2014-10-15 02:00:50',' [ Method = finaliseApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@134b92c; com.softserve.DBEntities.Application[ applicationID=11 ]; ]'),(289,'u12019837','2014-10-15 02:00:50',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@176ba0; 0; 2147483647; ]'),(290,'u12019837','2014-10-15 02:00:58',' [ Method = loadMovableApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@ecb272; ]'),(291,'u12019837','2014-10-15 02:01:22','Application forwared [ Method = forwardApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1e77c36; com.softserve.DBEntities.Application[ applicationID=10 ]; Recommended; Wonder; ]'),(292,'u12019837','2014-10-15 02:01:37',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@76ca2e; 0; 2147483647; ]'),(293,'u12019837','2014-10-15 02:01:47',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@421114; ]'),(294,'u12345678','2014-10-15 02:01:52',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@6bd71a; ]'),(295,'u12345678','2014-10-15 02:01:56',' [ Method = loadPendingApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@c8dd8; 0; 2147483647; ]'),(296,'u12345678','2014-10-15 02:01:59',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d08b5e; ]'),(297,'u12019837','2014-10-15 02:02:05',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1f3f61a; ]'),(298,'u12019837','2014-10-15 02:02:12',' [ Method = loadMovableApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@24a60b; ]'),(299,'u12019837','2014-10-15 02:02:23','Application forwared [ Method = forwardApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1b1dfc8; com.softserve.DBEntities.Application[ applicationID=9 ]; Recommended; Test; ]'),(300,'u12019837','2014-10-15 02:02:35','Application forwared [ Method = forwardApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1bf3efc; com.softserve.DBEntities.Application[ applicationID=11 ]; Recommended; Test; ]'),(301,'u12019837','2014-10-15 02:02:39',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@171aa45; ]'),(302,'f14000012','2014-10-15 02:17:47','Created new user account'),(303,'f14000012','2014-10-15 02:17:47',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@12fea4d; ]'),(304,'f14000012','2014-10-15 02:18:32',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1273b2d; ]'),(305,'f14000012','2014-10-15 02:19:34',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1da3698; com.softserve.DBEntities.Cv[ cvID=f14000012 ]; ]'),(306,'f14000012','2014-10-15 02:19:34','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1da3698; com.softserve.DBEntities.Cv[ cvID=f14000012 ]; ]'),(307,'f14000012','2014-10-15 02:19:34','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1da3698; com.softserve.DBEntities.Cv[ cvID=f14000012 ]; ]'),(308,'f14000012','2014-10-15 02:20:50','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1c10597; com.softserve.DBEntities.Application[ applicationID=12 ]; ]'),(309,'f14000012','2014-10-15 02:20:59','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d54e6c; com.softserve.DBEntities.Application[ applicationID=12 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(310,'f14000012','2014-10-15 02:20:59','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1382503; com.softserve.DBEntities.Application[ applicationID=12 ]; ]'),(311,'f14000012','2014-10-15 02:21:19',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@7159b; ]'),(312,'f14000013','2014-10-15 02:22:04','Created new user account'),(313,'f14000013','2014-10-15 02:22:04',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1daaf5b; ]'),(314,'f14000013','2014-10-15 02:22:08',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@a569e4; ]'),(315,'f14000013','2014-10-15 02:23:03',' [ Method = searchGoogleScholarUsing ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1c827d5; com.softserve.auxiliary.HTTPScrapers.GoogleScholarQuery@a80d53; ]'),(316,'f14000013','2014-10-15 02:23:17',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@11bc56f; com.softserve.DBEntities.Cv[ cvID=f14000013 ]; ]'),(317,'f14000013','2014-10-15 02:23:18','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@11bc56f; com.softserve.DBEntities.Cv[ cvID=f14000013 ]; ]'),(318,'f14000013','2014-10-15 02:23:18','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@11bc56f; com.softserve.DBEntities.Cv[ cvID=f14000013 ]; ]'),(319,'f14000013','2014-10-15 02:24:23','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@60aa33; com.softserve.DBEntities.Application[ applicationID=13 ]; ]'),(320,'f14000013','2014-10-15 02:24:31','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@d4ab76; com.softserve.DBEntities.Application[ applicationID=13 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(321,'f14000013','2014-10-15 02:24:32','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@17de60b; com.softserve.DBEntities.Application[ applicationID=13 ]; ]'),(322,'f14000013','2014-10-15 02:24:34',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@2ddcae; ]'),(323,'f14000014','2014-10-15 02:25:27','Created new user account'),(324,'f14000014','2014-10-15 02:25:27',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1810e8e; ]'),(325,'f14000014','2014-10-15 02:25:36',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@18be8e6; ]'),(326,'f14000014','2014-10-15 02:26:32',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1edbbb6; com.softserve.DBEntities.Cv[ cvID=f14000014 ]; ]'),(327,'f14000014','2014-10-15 02:26:32','Created CV [ Method = createCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1edbbb6; com.softserve.DBEntities.Cv[ cvID=f14000014 ]; ]'),(328,'f14000014','2014-10-15 02:26:32','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1edbbb6; com.softserve.DBEntities.Cv[ cvID=f14000014 ]; ]'),(329,'u12019837','2014-10-15 02:28:46',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@76fad8; ]'),(330,'f14000014','2014-10-15 02:29:05',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@fe094c; ]'),(331,'f14000014','2014-10-15 02:29:11',' [ Method = getOpenApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1de43ef; ]'),(332,'f14000014','2014-10-15 02:29:14',' [ Method = authenticateUserAsOwner ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d8efdf; com.softserve.DBEntities.Cv[ cvID=f14000014 ]; ]'),(333,'f14000014','2014-10-15 02:29:14','Updated CV [ Method = updateCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d8efdf; com.softserve.DBEntities.Cv[ cvID=f14000014 ]; ]'),(334,'f14000014','2014-10-15 02:29:14','Updated/Created CV [ Method = createProspectiveFellowCV ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1d8efdf; com.softserve.DBEntities.Cv[ cvID=f14000014 ]; ]'),(335,'f14000014','2014-10-15 02:30:25','Created/Updated a new application [ Method = createNewApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@312d4; com.softserve.DBEntities.Application[ applicationID=14 ]; ]'),(336,'f14000014','2014-10-15 02:30:33','Linked grant holder to new application [ Method = linkGrantHolderToApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@13ec43b; com.softserve.DBEntities.Application[ applicationID=14 ]; com.softserve.DBEntities.Person[ systemID=u12019837 ]; ]'),(337,'f14000014','2014-10-15 02:30:34','Submitted a new application [ Method = submitApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@e1dfa5; com.softserve.DBEntities.Application[ applicationID=14 ]; ]'),(338,'f14000014','2014-10-15 02:30:36',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@18276b5; ]'),(339,'u12019837','2014-10-15 02:30:40',' [ Method = login ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@2f560e; ]'),(340,'u12019837','2014-10-15 02:30:45',' [ Method = loadMovableApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1166e19; ]'),(341,'u12019837','2014-10-15 02:31:01','Application forwared [ Method = forwardApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@46c463; com.softserve.DBEntities.Application[ applicationID=12 ]; Endorsed; Testing; ]'),(342,'u12019837','2014-10-15 02:31:13','Application forwared [ Method = forwardApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@6dac84; com.softserve.DBEntities.Application[ applicationID=13 ]; Endorsed; Testing; ]'),(343,'u12019837','2014-10-15 02:31:25','Application forwared [ Method = forwardApplication ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@11cbc4e; com.softserve.DBEntities.Application[ applicationID=14 ]; Endorsed; Testing; ]'),(344,'u12019837','2014-10-15 02:31:33',' [ Method = loadPendingEndorsedApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@ed9c7c; 0; 2147483647; ]'),(345,'u12019837','2014-10-15 02:31:33',' [ Method = loadPendingEligibleApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@c8195a; 0; 2147483647; ]'),(346,'u12019837','2014-10-15 02:31:33',' [ Method = loadFundedApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@8d617d; 0; 2147483647; ]'),(347,'u12019837','2014-10-15 02:31:38',' [ Method = checkApplicationForEligiblity ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1a6d91b; com.softserve.DBEntities.Application[ applicationID=12 ]; ]'),(348,'u12019837','2014-10-15 02:31:45',' [ Method = setApplicationEligibleStatus ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@295361; com.softserve.DBEntities.Application[ applicationID=12 ]; true; ]'),(349,'u12019837','2014-10-15 02:31:45',' [ Method = loadPendingEndorsedApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@17ceb90; 0; 2147483647; ]'),(350,'u12019837','2014-10-15 02:31:45',' [ Method = loadPendingEligibleApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1e64ef; 0; 2147483647; ]'),(351,'u12019837','2014-10-15 02:31:45',' [ Method = loadFundedApplications ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@385483; 0; 2147483647; ]'),(352,'u12019837','2014-10-15 02:31:53',' [ Method = checkApplicationForEligiblity ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1952fe0; com.softserve.DBEntities.Application[ applicationID=13 ]; ]'),(353,'u12019837','2014-10-15 02:32:03',' [ Method = logout ] [ Parameters: com.softserve.auxiliary.requestresponseclasses.Session@1375aeb; ]');
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
  `_gender` varchar(40) DEFAULT NULL,
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
INSERT INTO `cv` VALUES ('f14000001','9309175578083','2014-10-03','Female','South African','N/A','Coloured',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Wonderfilled</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-01+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Fellow</information></additionalInformation>'),('f14000002','9309175578083','2014-10-01','Female','South African','N/A','White',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Wonderfilled</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-01+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Prospect</information></additionalInformation>'),('f14000003','9309175578083','2014-10-01','Male','South African','B1','Asian',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Wonderfilled</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-07+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Yes</information></additionalInformation>'),('f14000004','9309175578083','2014-10-14','Female','South African','C3','White',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Wonderfilled</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-07+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Chilled</information></additionalInformation>'),('f14000005','9309175578083','2014-10-01','Male','South African','C3','Asian',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Yizo</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-06+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>good</information></additionalInformation>'),('f14000006','9309175578083','2014-10-07','Female','South African','C3','Coloured',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Yizo</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-07+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Okay</information></additionalInformation>'),('f14000007','9309175578083','2014-10-14','Male','South African','B1','Coloured',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Yizo</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-14+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Wonders</information></additionalInformation>'),('f14000008','9309175578083','2014-10-07','Male','South African','B2','Black',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Yizo</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-07+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Pending ideas</information></additionalInformation>'),('f14000009','9309175578083','2014-10-07','Male','South African','C2','Black',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Wonderfilled</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-07+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Good</information></additionalInformation>'),('f14000010','9309175578083','2014-10-07','Female','South African','B2','White',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Yizo</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-07+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Random</information></additionalInformation>'),('f14000011','9309175578083','2014-10-08','Female','South African','C2','Black',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Yizo</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-06+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Okay</information></additionalInformation>'),('f14000012','9309175578083','2014-10-06','Male','South African','B2','Coloured',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Wonderfilled</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-07+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Fashion</information></additionalInformation>'),('f14000013','9309175578083','2014-10-06','Male','South African','B1','Coloured',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Wonderfilled</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-07+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Energy</information></additionalInformation>'),('f14000014','9309175578083','2014-10-06','Male','South African','A','Black',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Wonderfilled</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Became prospective</name><desciption>Miss being there</desciption><location>remained there</location><date>2014-10-06+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Respect</information></additionalInformation>'),('u01234567','9309175578083','2014-10-01','Male','South African','C1','Black',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Won something from it</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Fun studies</name><desciption>Chilled</desciption><location>My archive</location><date>2014-10-07+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>I\'m a grantholder</information></additionalInformation>'),('u12019837','9309175578083','2014-10-06','Male','South African','B1','Black',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><researchOutput><references><type>Publications in peer-reviewed/refereed journals</type><status>In progress</status><publicationName>Won something from it</publicationName></references></researchOutput>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><otherContributions><items><type>Participation in conferences, workshops and short courses</type><name>Fun studies</name><desciption>Chilled</desciption><location>My archive</location><date>2014-10-06+02:00</date></items></otherContributions>','<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><additionalInformation><information>Free</information></additionalInformation>');
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
INSERT INTO `eligiblity_report` VALUES (2,'2014-10-15 01:39:35','u12019837'),(12,'2014-10-15 02:31:45','u12019837');
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
INSERT INTO `employee_information` VALUES ('u01234567',6,'Lecturer','2014-10-01','Here',1),('u12019837',2,'HOD','2001-01-20','full time',1),('u12345678',10,'Lecturer','2014-10-02','Here',1),('u12453627',8,'Lecturer','2014-10-01','Here',1),('u21234567',12,'Lecturer','2014-10-06','Here',1),('u87654321',15,'Lecturer','2014-10-07','Here',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experience`
--

LOCK TABLES `experience` WRITE;
/*!40000 ALTER TABLE `experience` DISABLE KEYS */;
INSERT INTO `experience` VALUES (1,'f14000001','Endorsement','Endo','2014-10-02 02:00:00','2014-10-16 02:00:00'),(2,'f14000002','Tester','Test','2014-10-08 02:00:00','2014-10-29 02:00:00'),(3,'f14000003','Tester','Test','2014-10-08 02:00:00','2014-10-28 02:00:00'),(4,'u01234567','Lecture','School','2014-10-01 02:00:00','2014-10-29 02:00:00'),(5,'f14000004','Tester','Test','2014-10-07 02:00:00','2014-10-21 02:00:00'),(6,'f14000005','Tester','Test','2014-10-07 02:00:00','2014-10-23 02:00:00'),(7,'f14000006','Tester','Test','2014-10-07 02:00:00','2014-10-21 02:00:00'),(8,'f14000007','Tester','Test','2014-10-07 02:00:00','2014-10-28 02:00:00'),(9,'f14000008','Tester','Test','2014-10-07 02:00:00','2014-10-28 02:00:00'),(10,'u12019837','Lecture','School','2014-10-06 02:00:00','2014-10-22 02:00:00'),(11,'f14000009','Tester','Test','2014-10-07 02:00:00','2014-10-23 02:00:00'),(12,'f14000010','Tester','Test','2014-10-27 02:00:00','2014-10-31 02:00:00'),(13,'f14000011','Tester','Test','2014-10-14 02:00:00','2014-10-29 02:00:00'),(14,'f14000012','Tester','Test','2014-10-07 02:00:00','2014-10-22 02:00:00'),(15,'f14000013','Tester','Test','2014-10-14 02:00:00','2014-10-30 02:00:00'),(16,'f14000014','Tester','Test','2014-10-06 02:00:00','2014-10-29 02:00:00');
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
  `_type` varchar(40) NOT NULL,
  `_reason` text,
  `_fromStatus` varchar(40) DEFAULT NULL,
  `_toStatus` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`_reportID`),
  KEY `_application` (`_application`),
  KEY `_dris` (`_dris`),
  CONSTRAINT `forward_and_rewind_report_ibfk_1` FOREIGN KEY (`_application`) REFERENCES `application` (`_applicationID`),
  CONSTRAINT `forward_and_rewind_report_ibfk_2` FOREIGN KEY (`_dris`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forward_and_rewind_report`
--

LOCK TABLES `forward_and_rewind_report` WRITE;
/*!40000 ALTER TABLE `forward_and_rewind_report` DISABLE KEYS */;
INSERT INTO `forward_and_rewind_report` VALUES (1,2,'u12019837','2014-10-15 01:38:38','Forward','Test input','Referred','Endorsed'),(2,10,'u12019837','2014-10-15 02:01:22','Forward','Wonder','Finalised','Recommended'),(3,9,'u12019837','2014-10-15 02:02:23','Forward','Test','Finalised','Recommended'),(4,11,'u12019837','2014-10-15 02:02:35','Forward','Test','Finalised','Recommended'),(5,12,'u12019837','2014-10-15 02:31:01','Forward','Testing','Referred','Endorsed'),(6,13,'u12019837','2014-10-15 02:31:13','Forward','Testing','Referred','Endorsed'),(7,14,'u12019837','2014-10-15 02:31:25','Forward','Testing','Referred','Endorsed');
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
  `_type` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`_costID`),
  KEY `_fundingReport` (`_fundingReport`),
  CONSTRAINT `funding_cost_ibfk_1` FOREIGN KEY (`_fundingReport`) REFERENCES `funding_report` (`_reportID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funding_cost`
--

LOCK TABLES `funding_cost` WRITE;
/*!40000 ALTER TABLE `funding_cost` DISABLE KEYS */;
INSERT INTO `funding_cost` VALUES (1,2,5000,'School','');
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
INSERT INTO `funding_report` VALUES (2,'u12019837','2014-10-15 01:44:01');
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
-- Table structure for table `neural_network`
--

DROP TABLE IF EXISTS `neural_network`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neural_network` (
  `_neuralnetworkID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_timestamp` datetime NOT NULL,
  `_defaultNetwork` tinyint(1) DEFAULT NULL,
  `_name` varchar(100) DEFAULT NULL,
  `_type` varchar(100) DEFAULT NULL,
  `_learningRate` double DEFAULT NULL,
  `_momentum` double DEFAULT NULL,
  `_bias_threshold` double DEFAULT NULL,
  `_smoothingParameterT` double DEFAULT NULL,
  `_lowerCertaintyBound` double DEFAULT NULL,
  `_upperCertaintyBound` double DEFAULT NULL,
  PRIMARY KEY (`_neuralnetworkID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neural_network`
--

LOCK TABLES `neural_network` WRITE;
/*!40000 ALTER TABLE `neural_network` DISABLE KEYS */;
/*!40000 ALTER TABLE `neural_network` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `neuron`
--

DROP TABLE IF EXISTS `neuron`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neuron` (
  `_neuronID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_neuralnetwork` bigint(20) unsigned NOT NULL,
  `_neuronOrderIndex` bigint(20) unsigned DEFAULT NULL,
  `_value` double DEFAULT NULL,
  `_error` double DEFAULT NULL,
  `_biasNeuron` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`_neuronID`),
  KEY `_neuralnetwork` (`_neuralnetwork`),
  CONSTRAINT `neuron_ibfk_1` FOREIGN KEY (`_neuralnetwork`) REFERENCES `neural_network` (`_neuralnetworkID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neuron`
--

LOCK TABLES `neuron` WRITE;
/*!40000 ALTER TABLE `neuron` DISABLE KEYS */;
/*!40000 ALTER TABLE `neuron` ENABLE KEYS */;
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
  `_emailStatus` varchar(40) DEFAULT NULL,
  `_emailRetryCount` int(11) DEFAULT NULL,
  `_timestamp` datetime NOT NULL,
  `_sender` char(9) DEFAULT NULL,
  `_reciever` char(9) NOT NULL,
  PRIMARY KEY (`_notificationID`),
  KEY `_sender` (`_sender`),
  KEY `_reciever` (`_reciever`),
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`_sender`) REFERENCES `person` (`_systemID`),
  CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`_reciever`) REFERENCES `person` (`_systemID`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,'Created account','Account has been created for you','Sent',0,'2014-10-14 23:21:17','f14000001','f14000001'),(2,'Created account','Account has been created for you','Sent',0,'2014-10-14 23:22:09','f14000002','f14000002'),(3,'Created account','Account has been created for you','Sent',0,'2014-10-14 23:24:28','r14000001','r14000001'),(4,'Created account','Account has been created for you','Sent',0,'2014-10-14 23:30:19','u01234567','u01234567'),(5,'Created account','Account has been created for you','Sent',0,'2014-10-14 23:31:36','u12453627','u12453627'),(6,'Created account','Account has been created for you','Sent',0,'2014-10-14 23:33:28','u12345678','u12345678'),(7,'Created account','Account has been created for you','Sent',0,'2014-10-14 23:35:09','u21234567','u21234567'),(8,'Created account','Account has been created for you','Sent',0,'2014-10-14 23:37:16','u87654321','u87654321'),(9,'New application submitted','Please note that the new application \'Fellowship\' has been submitted for which you are the fellow of.','Sent',0,'2014-10-15 00:53:16',NULL,'f14000001'),(10,'Created account','Account has been created for you','Sent',0,'2014-10-15 00:56:17','f14000003','f14000003'),(11,'New application submitted','Please note that the new application \'Open(To Be Edited)\' has been submitted for which you are the fellow of.','Sent',0,'2014-10-15 01:04:24',NULL,'f14000003'),(12,'Created account','Account has been created for you','Sent',0,'2014-10-15 01:09:34','f14000004','f14000004'),(13,'New application submitted','Please note that the new application \'Open(To Be Ammended)\' has been submitted for which you are the fellow of.','Sent',0,'2014-10-15 01:15:31',NULL,'f14000004'),(14,'Created account','Account has been created for you','Sent',0,'2014-10-15 01:16:18','f14000005','f14000005'),(15,'New application submitted','Please note that the new application \'Open(To Be Finalised)\' has been submitted for which you are the fellow of.','Sent',0,'2014-10-15 01:18:52',NULL,'f14000005'),(16,'Created account','Account has been created for you','Sent',0,'2014-10-15 01:21:53','f14000006','f14000006'),(17,'New application submitted','Please note that the new application \'Recommend(To be approved)\' has been submitted for which you are the fellow of.','Sent',0,'2014-10-15 01:24:34',NULL,'f14000006'),(18,'Created account','Account has been created for you','Sent',0,'2014-10-15 01:25:30','f14000007','f14000007'),(19,'Created account','Account has been created for you','Queued',1,'2014-10-15 01:29:19','f14000008','f14000008'),(20,'New application submitted','Please note that the new application \'Recommend(To be Ammended)\' has been submitted for which you are the fellow of.','Sent',0,'2014-10-15 01:32:34',NULL,'f14000008'),(21,'Application finalised','The application Recommend(To be approved) has been finalised by Mr Test Tester. Please review the application for recommendation.','Sent',0,'2014-10-15 01:36:10','u12019837','u12019837'),(22,'Application finalised','Please note that the application \'Recommend(To be approved)\' has been finalised for which you are the fellow of. ','Sent',0,'2014-10-15 01:36:15',NULL,'f14000006'),(23,'Application finalised','Please note that the application \'Recommend(To be approved)\' has been finalised for which you are the grant holder of.','Sent',0,'2014-10-15 01:36:22',NULL,'u12019837'),(24,'Application finalised','The application Recommend(To be denied) has been finalised by Mr Test Tester. Please review the application for recommendation.','Sent',0,'2014-10-15 01:37:50','u12019837','u12019837'),(25,'Application finalised','Please note that the application \'Recommend(To be denied)\' has been finalised for which you are the fellow of. ','Sent',0,'2014-10-15 01:37:55',NULL,'f14000007'),(26,'Application finalised','The application Recommend(To be Ammended) has been finalised by Mr Test Tester. Please review the application for recommendation.','Sent',0,'2014-10-15 01:38:05','u12019837','u12019837'),(27,'Application finalised','Please note that the application \'Recommend(To be denied)\' has been finalised for which you are the grant holder of.','Sent',0,'2014-10-15 01:38:07',NULL,'u12019837'),(28,'Application finalised','Please note that the application \'Recommend(To be Ammended)\' has been finalised for which you are the fellow of. ','Sent',0,'2014-10-15 01:38:09',NULL,'f14000008'),(29,'Application finalised','Please note that the application \'Recommend(To be Ammended)\' has been finalised for which you are the grant holder of.','Sent',0,'2014-10-15 01:38:14',NULL,'u12019837'),(30,'Application forwarded','Please note that the application \'Fellowship\' has been forwarded to Endorsed for which you are the fellow of. The reason for this is as follows: Test input','Sent',0,'2014-10-15 01:38:38',NULL,'f14000001'),(31,'Application forwarded','Please note that the application \'Fellowship\' has been forwarded to Endorsed for which you are the grant holder of. The reason for this is as follows: Test input','Sent',0,'2014-10-15 01:38:42',NULL,'u12019837'),(32,'Application is eligible','Please note that the application \'Fellowship\' has been found to be eligible for funding consideration for which you are the fellow of. ','Sent',0,'2014-10-15 01:39:35',NULL,'f14000001'),(33,'Application is eligible','Please note that the application \'Fellowship\' has been found to be eligible for funding consideration for which you are the grant holder of.','Sent',0,'2014-10-15 01:39:40',NULL,'u12019837'),(34,'Application funding approved','The application \'Fellowship\' has been approved for funding by Mr Test Tester. ','Sent',0,'2014-10-15 01:44:02','u12019837','f14000001'),(35,'Application funding approved','The application \'Fellowship\' has been approved for funding by Mr Test Tester. ','Sent',0,'2014-10-15 01:44:06','u12019837','u12019837'),(36,'Created account','Account has been created for you','Sent',0,'2014-10-15 01:46:35','f14000009','f14000009'),(37,'New application submitted','Please note that the new application \'Dean Approval (To be approved)\' has been submitted for which you are the fellow of.','Sent',0,'2014-10-15 01:49:44',NULL,'f14000009'),(38,'Created account','Account has been created for you','Sent',0,'2014-10-15 01:50:36','f14000010','f14000010'),(39,'New application submitted','Please note that the new application \'Fellowship\' has been submitted for which you are the fellow of.','Sent',0,'2014-10-15 01:53:08',NULL,'f14000010'),(40,'Created account','Account has been created for you','Sent',0,'2014-10-15 01:54:08','f14000011','f14000011'),(41,'New application submitted','Please note that the new application \'Dean Approval (To be Ammended)\' has been submitted for which you are the fellow of.','Sent',0,'2014-10-15 01:56:36',NULL,'f14000011'),(42,'Application finalised','The application Fellowship has been finalised by Mr Test Tester. Please review the application for recommendation.','Sent',0,'2014-10-15 02:00:20','u12019837','u12019837'),(43,'Application finalised','Please note that the application \'Fellowship\' has been finalised for which you are the fellow of. ','Sent',0,'2014-10-15 02:00:24',NULL,'f14000010'),(44,'Application finalised','Please note that the application \'Fellowship\' has been finalised for which you are the grant holder of.','Sent',0,'2014-10-15 02:00:29',NULL,'u12019837'),(45,'Application finalised','The application Dean Approval (To be approved) has been finalised by Mr Test Tester. Please review the application for recommendation.','Sent',0,'2014-10-15 02:00:36','u12019837','u12019837'),(46,'Application finalised','Please note that the application \'Dean Approval (To be approved)\' has been finalised for which you are the fellow of. ','Sent',0,'2014-10-15 02:00:41',NULL,'f14000009'),(47,'Application finalised','Please note that the application \'Dean Approval (To be approved)\' has been finalised for which you are the grant holder of.','Sent',0,'2014-10-15 02:00:47',NULL,'u12019837'),(48,'Application finalised','The application Dean Approval (To be Ammended) has been finalised by Mr Test Tester. Please review the application for recommendation.','Sent',0,'2014-10-15 02:00:50','u12019837','u12019837'),(49,'Application finalised','Please note that the application \'Dean Approval (To be Ammended)\' has been finalised for which you are the fellow of. ','Sent',0,'2014-10-15 02:00:54',NULL,'f14000011'),(50,'Application finalised','Please note that the application \'Dean Approval (To be Ammended)\' has been finalised for which you are the grant holder of.','Sent',0,'2014-10-15 02:01:00',NULL,'u12019837'),(51,'Application forwarded','Please note that the application \'Fellowship\' has been forwarded to Recommended for which you are the fellow of. The reason for this is as follows: Wonder','Sent',0,'2014-10-15 02:01:22',NULL,'f14000010'),(52,'Application forwarded','Please note that the application \'Fellowship\' has been forwarded to Recommended for which you are the grant holder of. The reason for this is as follows: Wonder','Sent',0,'2014-10-15 02:01:26',NULL,'u12019837'),(53,'Application forwarded','Please note that the application \'Dean Approval (To be approved)\' has been forwarded to Recommended for which you are the fellow of. The reason for this is as follows: Test','Sent',0,'2014-10-15 02:02:23',NULL,'f14000009'),(54,'Application forwarded','Please note that the application \'Dean Approval (To be approved)\' has been forwarded to Recommended for which you are the grant holder of. The reason for this is as follows: Test','Sent',0,'2014-10-15 02:02:27',NULL,'u12019837'),(55,'Application forwarded','Please note that the application \'Dean Approval (To be Ammended)\' has been forwarded to Recommended for which you are the fellow of. The reason for this is as follows: Test','Sent',0,'2014-10-15 02:02:35',NULL,'f14000011'),(56,'Application forwarded','Please note that the application \'Dean Approval (To be Ammended)\' has been forwarded to Recommended for which you are the grant holder of. The reason for this is as follows: Test','Sent',0,'2014-10-15 02:02:39',NULL,'u12019837'),(57,'Created account','Account has been created for you','Sent',0,'2014-10-15 02:17:47','f14000012','f14000012'),(58,'New application submitted','Please note that the new application \'Eligibilty(To be approved)\' has been submitted for which you are the fellow of.','Sent',0,'2014-10-15 02:20:59',NULL,'f14000012'),(59,'Created account','Account has been created for you','Sent',0,'2014-10-15 02:22:04','f14000013','f14000013'),(60,'New application submitted','Please note that the new application \'Eligibilty(To be denied)\' has been submitted for which you are the fellow of.','Sent',0,'2014-10-15 02:24:32',NULL,'f14000013'),(61,'Created account','Account has been created for you','Sent',0,'2014-10-15 02:25:27','f14000014','f14000014'),(62,'New application submitted','Please note that the new application \'Eligibilty(To be given eligibilty)\' has been submitted for which you are the fellow of.','Sent',0,'2014-10-15 02:30:34',NULL,'f14000014'),(63,'Application forwarded','Please note that the application \'Eligibilty(To be approved)\' has been forwarded to Endorsed for which you are the fellow of. The reason for this is as follows: Testing','Sent',0,'2014-10-15 02:31:01',NULL,'f14000012'),(64,'Application forwarded','Please note that the application \'Eligibilty(To be approved)\' has been forwarded to Endorsed for which you are the grant holder of. The reason for this is as follows: Testing','Sent',0,'2014-10-15 02:31:06',NULL,'u12019837'),(65,'Application forwarded','Please note that the application \'Eligibilty(To be denied)\' has been forwarded to Endorsed for which you are the fellow of. The reason for this is as follows: Testing','Sent',0,'2014-10-15 02:31:13',NULL,'f14000013'),(66,'Application forwarded','Please note that the application \'Eligibilty(To be denied)\' has been forwarded to Endorsed for which you are the grant holder of. The reason for this is as follows: Testing','Sent',0,'2014-10-15 02:31:17',NULL,'u12019837'),(67,'Application forwarded','Please note that the application \'Eligibilty(To be given eligibilty)\' has been forwarded to Endorsed for which you are the fellow of. The reason for this is as follows: Testing','Sent',0,'2014-10-15 02:31:25',NULL,'f14000014'),(68,'Application forwarded','Please note that the application \'Eligibilty(To be given eligibilty)\' has been forwarded to Endorsed for which you are the grant holder of. The reason for this is as follows: Testing','Sent',0,'2014-10-15 02:31:30',NULL,'u12019837'),(69,'Application is eligible','Please note that the application \'Eligibilty(To be approved)\' has been found to be eligible for funding consideration for which you are the fellow of. ','Sent',0,'2014-10-15 02:31:45',NULL,'f14000012'),(70,'Application is eligible','Please note that the application \'Eligibilty(To be approved)\' has been found to be eligible for funding consideration for which you are the grant holder of.','Sent',0,'2014-10-15 02:31:49',NULL,'u12019837');
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
  `_password` varchar(130) NOT NULL,
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
  `_accountStatus` varchar(40) DEFAULT NULL,
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
INSERT INTO `person` VALUES ('f14000001','af89af045b8d4874f7debde6af9422b82af4b6afb658ab846532a0ccc91eb1febfef982a841beca050449aab67c2e9a1839f6cdc87ae4f1f6205f579ecd806e1','Mr.','Fellow','Tester','fellow@gmail.com','0123456789','0123456789','0123456789','0123456789',3,0,'Active'),('f14000002','93b7b5860e0cfda79970c2f6f0d5a7f0fb1d10c23e0302d06978eb69639a0eb27d89e9a7aae639bb5174f9c61ef16d49fce395c4d0a47d9da92fc2d4025237a1','Mr.','Prospective','Tester','prospective@gmail.com','0123456789','0123456789','0123456789','0123456789',4,0,'Active'),('f14000003','fbda8fe18e9761785da66d4839b02e9b71363bf9a432499e95c164bcc9586a7221075b93680286b56276d9559b783275c4122e3ce627f530c88bb3fee7b7d1cf','Mr.','Open','Tester','open@gmail.com','0123456789','0123456789','0123456789','0123456789',16,0,'Active'),('f14000004','bbbae4e30299e3080155c63b9a1e0daea1cb333e1e94776dddacb7c63012a9bb55062ee038b105446f9f923b9de0f0c8c0c7a4ccfd7ea8cbeecbba6db0725840','Mr.','Open2','Tester','open2@gmail.com','0123456789','0123456789','0123456789','0123456789',17,0,'Active'),('f14000005','105947f57bbe19b468bd8a08e162bd8749212e49a799bb17ae1e6b37f813c09f5eae792260b1f7279ec99fec9049875391335ee377654998189f8e7191e5f0f2','Mr.','Open3','Tester','open3@gmail.com','0123456789','0123456789','0123456789','0123456789',18,0,'Active'),('f14000006','8e45d47933ce78beea68f2e210b866ead363a062829b659e2707aa314b56384775ef1c357a7332b27718f3c0299797edcc8eb1972b0e384d493ebf3a69794b02','Mr.','Recommend1','Tester','recommend1@gmail.com','0123456789','0123456789','0123456789','0123456789',19,0,'Active'),('f14000007','b78f060f48e184a68a996cb9dd71ad51a05557988d9280b2de90ed6087bf0a1b81c8941d29405d7bfd312dd571383dfaa7a2267ceea081c0878629c02f1e8130','Mr.','Recommend2','Tester','recommend2@gmail.com','0123456789','0123456789','0123456789','0123456789',20,0,'Active'),('f14000008','9b7ac9a2c58b5a8edec734ddca783246e1edffbe4543f6184c08ceba57de6d42a5e2313ed95c6946db76c01257ac8c0d89cbdee3c021c7ecde593212883cd86c','Mr.','Recommend3','Tester','recommend3@gmail.com','0123456789','0123456789','0123456789','0123456789',21,0,'Active'),('f14000009','b5dc7e1f3dd52e0bce5c07e888683d4179a0fdcfd5bacd1e4510e524517a48ece5249d031ee13465bf2b35ad6bb3454ec830d5d35d6981b16793ac5ca0e6d517','Mr.','Approve1','Tester','approve1@gmail.com','0123456789','0123456789','0123456789','0123456789',22,0,'Active'),('f14000010','63f3ea0798490d8e8e1035169534a6b6337fcabf56d5dfad55fce3c896e9e6fcfc5203d9f3e01429c971bf7b7b92e01b71a13c8a57fb84d2011937bc4ccf03d2','Mr.','Approve2','Tester','approve2@gmail.com','0123456789','0123456789','0123456789','0123456789',23,0,'Active'),('f14000011','a29ba48794c7e5d0fceb6d352206a6b88a52fc5ac2d080f5ee7b8e51d6932b8df49c58d8b63cdc57d34b7e55fb149e32e20efe15ba46aba351e0d3f3bacbe765','Mr.','Approve3','Tester','approve3@gmail.com','0123456789','0123456789','0123456789','0123456789',24,0,'Active'),('f14000012','a1d4baa691140e7c64e98b36b8c85f5c72f687ac98355153fa6bf0e1445b579fbc95d1df4c486efbbd37a99ae37117c8e2b74a91511cfc30d9ab68b222a77119','Mr.','Eligible1','Tester','eligible1@gmail.com','0123456789','0123456789','0123456789','0123456789',25,0,'Active'),('f14000013','39b5e1cd99d61e2b1a221f71fc773db1bdf6200cc8f1dc90b75660aed3b7834adb020f3987ca70f142a1c7a0a8465f2d33840d461ab013504eb57242b89c415c','Mr.','Eligible2','Tester','eligible2@gmail.com','0123456789','0123456789','0123456789','0123456789',26,0,'Active'),('f14000014','e478e5aebbf84b1f15d13508ce54aafc899b98db958e733296e89ac286d27dea803e44246fda3ba53641259a6d9cedeeadd373a9fbd41fb0e854b3618b82d239','Mr.','Eligible3','Tester','eligible3@gmail.com','0123456789','0123456789','0123456789','0123456789',27,0,'Active'),('r14000001','d7741827945dbae8e47ef2854fa6d6e38d69f294c322badd57f87425230a51c4088dd3a9d2d16ab16c2f7286a8fa806502f4ffb6539a4deaf684006f0c7e72a6','Mr.','Referee','Tester','referee@gmail.com','0123456789','0123456789','0123456789','0123456789',5,0,'Active'),('u01234567','8367649758d83767f67c13047bec7cecfa54775463a466fb9e2dba4482497527d1237c958e740956a6589ad0f5f9f3286942e937018b8d2a3918655830256e0b','Mr.','GrantHolder','Tester','grantHolder@gmail.com','0123456789','0123456789','0123456789','0123456789',7,1,'Active'),('u12019837','ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff','Mr','Test','Tester','test@gmail.com',NULL,NULL,NULL,NULL,1,1,'Active'),('u12345678','335b6661c8cb4ad1849292ee81e0748fe4cdde9a040e4f6d0e0469b7a09c025acf4ef8f3c8c1efb8e21c70781b3c274d71cf208e7f19588143e4a22a62d4a67c','Mr.','Dean','Tester','dean@gmail.com','0123456789','0123456789','0123456789','0123456789',11,1,'Active'),('u12453627','9c088a949f74017b49acb1401d51ef674c1671b61781bc76ec094570791f704a2df9c3cbf985881549b10c9016e689a01e891fbe0dfe9b2b573e2a245a17c5ba','Mr.','HOD','Tester','hod@gmail.com','0123456789','0123456789','0123456789','0123456789',9,1,'Active'),('u21234567','34f0d826d737a379028c41155bf976506666c1fade06132a7dc24eb922e5c13a9090429f97fbe9139467767928f15ab0588d3f85b622bc4451046ec950d74877','Mr.','DRIS','Tester','dris@gmail.com','0123456789','0123456789','0123456789','0123456789',13,1,'Active'),('u87654321','ca965fb4776bd1b453e8b9b5d0a3173048ab159578047471fd4fe3cab4953127ea258ec6c52c8d02311fb93327e8ee56ac19a1c3646d56c8c47409cb668d427c','Mr.','PostDocMember','Tester','postdocmember@gmail.com','0123456789','0123456789','0123456789','0123456789',14,1,'Active');
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
INSERT INTO `person_security_role` VALUES ('f14000001',1),('f14000001',3),('f14000002',1),('f14000003',1),('f14000004',1),('f14000005',1),('f14000006',1),('f14000007',1),('f14000008',1),('f14000009',1),('f14000010',1),('f14000011',1),('f14000012',1),('f14000013',1),('f14000014',1),('r14000001',2),('u01234567',4),('u12019837',1),('u12019837',2),('u12019837',3),('u12019837',4),('u12019837',5),('u12019837',6),('u12019837',7),('u12019837',8),('u12019837',9),('u12345678',6),('u12453627',5),('u21234567',7),('u87654321',8);
/*!40000 ALTER TABLE `person_security_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pre_post_condition_method`
--

DROP TABLE IF EXISTS `pre_post_condition_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pre_post_condition_method` (
  `_prePostConditionMethodID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_methodName` varchar(250) DEFAULT NULL,
  `_methodClassName` varchar(250) DEFAULT NULL,
  `_methodParameters` text,
  `_scriptLangName` varchar(250) DEFAULT NULL,
  `_preConditionScript` text,
  `_postConditionScript` text,
  PRIMARY KEY (`_prePostConditionMethodID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pre_post_condition_method`
--

LOCK TABLES `pre_post_condition_method` WRITE;
/*!40000 ALTER TABLE `pre_post_condition_method` DISABLE KEYS */;
/*!40000 ALTER TABLE `pre_post_condition_method` ENABLE KEYS */;
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
INSERT INTO `research_fellow_information` VALUES ('f14000001','u12345678','u12345678@tuks.co.za',1);
/*!40000 ALTER TABLE `research_fellow_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourceentity`
--

DROP TABLE IF EXISTS `resourceentity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourceentity` (
  `il8n_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `i18n_key` varchar(250) NOT NULL,
  `i18n_value` varchar(250) NOT NULL,
  `i18n_locale` varchar(250) NOT NULL,
  PRIMARY KEY (`il8n_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourceentity`
--

LOCK TABLES `resourceentity` WRITE;
/*!40000 ALTER TABLE `resourceentity` DISABLE KEYS */;
INSERT INTO `resourceentity` VALUES (1,'welcome.title','You must know','en'),(2,'welcome.name','Master','en'),(3,'welcome.db','PostDoc','en'),(4,'welcome.language','English','en');
/*!40000 ALTER TABLE `resourceentity` ENABLE KEYS */;
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

--
-- Table structure for table `synapse`
--

DROP TABLE IF EXISTS `synapse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `synapse` (
  `_synapseID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_neuralnetwork` bigint(20) unsigned NOT NULL,
  `_srcNeuron` bigint(20) unsigned NOT NULL,
  `_destNeuron` bigint(20) unsigned NOT NULL,
  `_weight` double DEFAULT NULL,
  `_previousWeightChange` double DEFAULT NULL,
  PRIMARY KEY (`_synapseID`),
  KEY `_neuralnetwork` (`_neuralnetwork`),
  KEY `_srcNeuron` (`_srcNeuron`),
  KEY `_destNeuron` (`_destNeuron`),
  CONSTRAINT `synapse_ibfk_1` FOREIGN KEY (`_neuralnetwork`) REFERENCES `neural_network` (`_neuralnetworkID`),
  CONSTRAINT `synapse_ibfk_2` FOREIGN KEY (`_srcNeuron`) REFERENCES `neuron` (`_neuronID`),
  CONSTRAINT `synapse_ibfk_3` FOREIGN KEY (`_destNeuron`) REFERENCES `neuron` (`_neuronID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synapse`
--

LOCK TABLES `synapse` WRITE;
/*!40000 ALTER TABLE `synapse` DISABLE KEYS */;
/*!40000 ALTER TABLE `synapse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-15  2:32:39
