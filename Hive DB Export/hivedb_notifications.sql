-- MySQL dump 10.13  Distrib 8.0.21, for macos10.15 (x86_64)
--
-- Host: 127.0.0.1    Database: hivedb
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `noti_id` int NOT NULL AUTO_INCREMENT,
  `owner_user_id` int DEFAULT NULL,
  `entity_id` int DEFAULT NULL,
  `noti_type` varchar(100) DEFAULT NULL,
  `noti_text` varchar(100) DEFAULT NULL,
  `date_created` varchar(50) DEFAULT NULL,
  `is_new` tinyint DEFAULT NULL,
  PRIMARY KEY (`noti_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (6,1,3,'like','@CameronX liked your buzz!','09/25/2020 22:02:53',1),(8,1,2,'comment','@CameronX commented on your buzz!','09/25/2020 22:08:56',1),(9,9,5,'request-accepted','Your request to join ISU Swim Team was accepted','09/25/2020 22:25:17',1),(10,9,5,'request-declined','Your request to join ISU Swim Team was declined','09/25/2020 22:25:55',0),(11,1,2,'comment','@CameronX commented on your buzz!','09/27/2020 21:50:43',1),(12,1,2,'comment-mention','You were mentioned in a comment on a post.','09/27/2020 21:50:43',1),(13,2,2,'comment-mention','You were mentioned in a comment on a post.','09/27/2020 21:50:43',1),(14,2,36,'post-mention','You were mentioned in a post.','09/27/2020 21:56:50',1),(15,2,36,'post-mention','You were mentioned in a post.','09/27/2020 21:56:50',1),(16,2,39,'post-mention','You were mentioned in a post.','09/27/2020 22:03:13',1),(17,1,39,'post-mention','You were mentioned in a post.','09/27/2020 22:03:13',1),(18,1,3,'hive-role','You are now a beekeeper of ISU Math Nerds.','09/27/2020 22:12:32',1),(19,1,3,'hive-role','You are no longer a beekeeper of ISU Math Nerds.','09/27/2020 22:13:10',1),(20,2,41,'post-mention','You were mentioned in a post.','09/27/2020 22:49:28',1),(21,3,41,'post-mention','You were mentioned in a post.','09/27/2020 22:49:28',1);
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-04 15:18:49
