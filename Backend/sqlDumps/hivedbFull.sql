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
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `comment_id` int unsigned NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL,
  `user_id` int NOT NULL,
  `date_created` varchar(100) NOT NULL,
  `text_content` varchar(200) NOT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,1,1,'08/29/2020 11:24:12','EPIC'),(2,1,1,'08/29/2020 11:24:41','Nah not so epic'),(3,2,1,'08/30/2020 22:31:11','This is the new text...'),(5,2,1,'09/04/2020 19:41:25','Such a great post lol.');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hive_interests`
--

DROP TABLE IF EXISTS `hive_interests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hive_interests` (
  `hive_id` int NOT NULL,
  `interest_id` int NOT NULL,
  `date_created` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`hive_id`,`interest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hive_interests`
--

LOCK TABLES `hive_interests` WRITE;
/*!40000 ALTER TABLE `hive_interests` DISABLE KEYS */;
INSERT INTO `hive_interests` VALUES (3,1,'date'),(3,3,'date'),(3,5,'date'),(3,9,'09/11/2020 17:13:31'),(4,2,'date'),(5,6,'date');
/*!40000 ALTER TABLE `hive_interests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hives`
--

DROP TABLE IF EXISTS `hives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hives` (
  `hive_id` int unsigned NOT NULL AUTO_INCREMENT,
  `date_created` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `type` varchar(9) DEFAULT NULL,
  `latitude` decimal(10,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  PRIMARY KEY (`hive_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hives`
--

LOCK TABLES `hives` WRITE;
/*!40000 ALTER TABLE `hives` DISABLE KEYS */;
INSERT INTO `hives` VALUES (3,'08/29/2020 17:14:50','ISU Math Nerds','Mathy Guys','protected',0.00000000,0.00000000),(4,'08/29/2020 17:15:05','ISU Sports Guys','desc','public',0.00000000,0.00000000),(5,'09/02/2020 15:58:47','ISU Swim Team','desc','private',0.00000000,0.00000000);
/*!40000 ALTER TABLE `hives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interests`
--

DROP TABLE IF EXISTS `interests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interests` (
  `interest_id` int NOT NULL AUTO_INCREMENT,
  `interest_text` varchar(30) DEFAULT NULL,
  `date_created` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`interest_id`),
  UNIQUE KEY `interestText` (`interest_text`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interests`
--

LOCK TABLES `interests` WRITE;
/*!40000 ALTER TABLE `interests` DISABLE KEYS */;
INSERT INTO `interests` VALUES (1,'Chess','date'),(2,'Golf','date'),(3,'Math','date'),(4,'Science','date'),(5,'Funny','date'),(6,'Swimming','date'),(7,'Bowling','09/11/2020 16:46:32'),(9,'Equations','09/11/2020 17:10:22');
/*!40000 ALTER TABLE `interests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `post_id` int unsigned NOT NULL,
  `user_id` int unsigned NOT NULL,
  `date_created` varchar(100) NOT NULL,
  PRIMARY KEY (`post_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES (1,1,'08/29/2020 13:34:25'),(2,1,'08/29/2020 13:34:38');
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `hive_id` int unsigned NOT NULL,
  `user_id` int unsigned NOT NULL,
  `date_created` varchar(100) NOT NULL,
  `is_moderator` tinyint(1) NOT NULL,
  PRIMARY KEY (`hive_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (3,1,'08/29/2020 16:33:54',0),(3,2,'08/29/2020 15:54:18',0),(3,3,'08/29/2020 15:59:48',0);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `post_id` int unsigned NOT NULL AUTO_INCREMENT,
  `hive_id` int DEFAULT NULL,
  `user_id` int unsigned DEFAULT NULL,
  `date_created` varchar(500) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `text_content` varchar(2000) NOT NULL,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,3,1,'08/29/2020 10:56:43','title1','This is the new text...'),(2,3,2,'08/29/2020 10:56:43','title2','This is an epic post'),(3,3,1,'08/29/2020 10:56:43','title3','lawl'),(4,3,2,'08/29/2020 10:56:43','title4','lawl2'),(5,4,3,'08/29/2020 10:56:43','title5','this is some text2'),(6,3,3,'08/29/2020 10:56:43','title6','example post'),(7,3,3,'08/29/2020 10:56:43','title7','example post'),(10,3,1,'08/29/2020 10:56:43','New title!10','This is the new text...'),(11,3,1,'08/29/2020 17:28:09','This is a new post!11','This is the new text... hmmm'),(12,4,1,'08/30/2020 11:22:36','New title!12','This is the new text...');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_registrations`
--

DROP TABLE IF EXISTS `user_registrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_registrations` (
  `user_id` int NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_reg_unique` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_registrations`
--

LOCK TABLES `user_registrations` WRITE;
/*!40000 ALTER TABLE `user_registrations` DISABLE KEYS */;
INSERT INTO `user_registrations` VALUES (1,'cameron@gmail.com','thisisGood'),(2,'a@b.com','2'),(3,'b@a.com','3'),(4,'c@g.com','4'),(5,'her2e@here.com','ok'),(6,'her3e@here.com','ok'),(9,'kindawantnewEmail@here.com','newPassword');
/*!40000 ALTER TABLE `user_registrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int unsigned NOT NULL AUTO_INCREMENT,
  `date_created` varchar(100) NOT NULL,
  `user_name` varchar(15) NOT NULL,
  `display_name` varchar(25) NOT NULL,
  `birthday` varchar(10) NOT NULL,
  `biography` varchar(500) DEFAULT NULL,
  `location` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'08/28/2020 23:28:23','CameronX','Cameron Smith','12/28/1998','a nerd','Ames, Iowa'),(2,'09/02/2020 17:18:20','NewGuy','New_Guy','12/28/2020','yea','a, a'),(3,'08/29/2020 10:55:33','Matt123','Matt Guy','01/03/1983','an old guy','s, a'),(4,'08/29/2020 17:18:22','Bob','Bobby Man','11/23/1993','a good guy','s, f'),(5,'09/17/2020 22:38:55','somethingdif','Greg tom?','11/23/2020','a very very very young person','g, s'),(6,'09/17/2020 22:38:55','ffffff','dddd','11/23/2020','ok','f, f'),(9,'09/17/2020 23:27:06','someUserName','OK','11/23/2020','not sure',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'hivedb'
--
/*!50003 DROP PROCEDURE IF EXISTS `getLikeCountByUserId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getLikeCountByUserId`(IN userId INT, OUT likeCountByUserId INT)
BEGIN
	SELECT count(*) into likeCountByUserId 
    FROM posts p, likes l
	WHERE p.user_id = userId AND l.post_id = p.post_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-17 23:48:03
