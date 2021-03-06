-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: ssm_safesystem
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `safesystem`
--

DROP TABLE IF EXISTS `safesystem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `safesystem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state` varchar(10) DEFAULT NULL,
  `temperature` varchar(10) DEFAULT NULL,
  `heartrate` varchar(10) DEFAULT NULL,
  `longitude` varchar(10) DEFAULT NULL,
  `latitude` varchar(10) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `safesystem`
--

LOCK TABLES `safesystem` WRITE;
/*!40000 ALTER TABLE `safesystem` DISABLE KEYS */;
INSERT INTO `safesystem` VALUES (1,'0','20.6','74','113.38500','23.13209','2017-12-13 13:49:38'),(2,'0','20.6','66','113.38500','23.13209','2017-12-13 13:49:42'),(3,'0','20.5','100','113.38501','23.13208','2017-12-13 13:49:46'),(4,'0','20.5','101','113.38501','23.13207','2017-12-13 13:49:50'),(5,'0','20.5','105','113.38500','23.13207','2017-12-13 13:49:54'),(6,'0','20.8','120','113.38508','23.13183','2017-12-13 15:15:55'),(7,'0','20.8','120','113.38508','23.13183','2017-12-13 15:15:59'),(8,'0','20.8','137','113.38508','23.13183','2017-12-13 15:16:03'),(9,'1','20.8','125','113.38508','23.13183','2017-12-13 15:16:07'),(10,'1','20.8','152','113.38506','23.13183','2017-12-13 15:16:11');
/*!40000 ALTER TABLE `safesystem` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-14 13:20:52
