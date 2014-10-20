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

LOCK TABLES `employee_information` WRITE;
/*!40000 ALTER TABLE `employee_information` DISABLE KEYS */;
INSERT INTO `employee_information` VALUES ('u14000001',2,'GrantHolder','2001-01-20','full time',1)
,('u14000002',2,'HOD','2001-01-20','full time',1)
,('u14000003',2,'Dean','2001-01-20','full time',1)
,('u14000004',2,'DRIS','2001-01-20','full time',1)
,('u14000005',2,'GrantHolder','2001-01-20','full time',1)
,('u14000006',2,'HOD','2001-01-20','full time',1)
,('u14000007',2,'Dean','2001-01-20','full time',1)
,('u14000008',2,'DRIS','2001-01-20','full time',1);
/*!40000 ALTER TABLE `employee_information` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES ('u14000001','grantholder','Mr','GrantHolder','Tester','grantholder@gmail.com',NULL,NULL,NULL,NULL,1,1,'Active')
,('u14000002','hod','Mr','HOD','Tester','hod@gmail.com',NULL,NULL,NULL,NULL,1,1,'Active')
,('u14000003','dean','Mr','Dean','Tester','dean@gmail.com',NULL,NULL,NULL,NULL,1,1,'Active')
,('u14000004','dris','Mr','DRIS','Tester','dris@gmail.com',NULL,NULL,NULL,NULL,1,1,'Active')
,('u14000005','grantholder1','Mr','GrantHolder1','Tester','grantholder1@gmail.com',NULL,NULL,NULL,NULL,1,1,'Active')
,('u14000006','hod1','Mr','HOD1','Tester','hod1@gmail.com',NULL,NULL,NULL,NULL,1,1,'Active')
,('u14000007','dean1','Mr','Dean1','Tester','dean1@gmail.com',NULL,NULL,NULL,NULL,1,1,'Active')
,('u14000008','dris1','Mr','DRIS1','Tester','dris1@gmail.com',NULL,NULL,NULL,NULL,1,1,'Active');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `person_security_role` WRITE;
/*!40000 ALTER TABLE `person_security_role` DISABLE KEYS */;
INSERT INTO `person_security_role` VALUES ('u14000001',3),('u14000002',4),('u14000003',5),('u14000004',6)
,('u14000005',3),('u14000006',4),('u14000007',5),('u14000008',6);
/*!40000 ALTER TABLE `person_security_role` ENABLE KEYS */;
UNLOCK TABLES;
