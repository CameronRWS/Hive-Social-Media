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
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,3,1,'08/29/2020 10:56:43','title1','This is the new text...'),(2,3,2,'08/29/2020 10:56:43','title2','This is an epic post'),(3,3,1,'08/29/2020 10:56:43','title3','lawl'),(4,3,2,'08/29/2020 10:56:43','title4','lawl2'),(5,4,3,'08/29/2020 10:56:43','title5','this is some text2'),(6,3,3,'08/29/2020 10:56:43','title6','example post'),(7,3,3,'08/29/2020 10:56:43','title7','example post'),(10,3,1,'08/29/2020 10:56:43','New title!10','This is the new text...'),(11,3,1,'08/29/2020 17:28:09','This is a new post!11','This is the new text... hmmm'),(12,4,1,'08/30/2020 11:22:36','New title!12','This is the new text...'),(13,3,1,'09/18/2020 20:08:26','Algorithms','HELP ME IM SO CONFUSED???'),(14,5,1,'09/21/2020 11:36:00','Demo 2 Title!!!!','HELLO WORLD'),(15,3,1,'09/22/2020 21:52:59','',''),(16,3,1,'09/22/2020 21:53:28','Testing','itojawerjew;of'),(17,3,1,'09/22/2020 21:53:30','Testing','itojawerjew;of'),(18,3,1,'09/22/2020 22:41:11','Take Me Away','To the Home Screen'),(19,3,1,'09/22/2020 22:54:57','',''),(20,3,1,'09/22/2020 23:03:35','Hey there everyone.','Disregard me.'),(21,4,1,'09/22/2020 23:10:08','AAAAAAAAHGIWGE','AGIPHJA$IRT(P@'),(22,3,1,'09/22/2020 23:12:59','wheofhw','pfweijfpwejf'),(23,4,1,'09/23/2020 20:02:57','Hello Cameron','Sir hi.'),(24,3,1,'09/23/2020 20:03:28','This\'foiawejf','fiajwpe'),(25,3,1,'09/23/2020 21:37:55','Test','#1'),(26,3,1,'09/23/2020 21:38:01','Test','#1'),(27,3,1,'09/23/2020 21:38:50','Test','#1'),(28,3,1,'09/23/2020 21:39:10','Test','#2'),(29,3,1,'09/23/2020 21:39:43','Test','#3'),(30,3,1,'09/23/2020 21:40:33','Test','#3'),(31,3,1,'09/23/2020 21:44:01','Test','#4'),(32,4,1,'09/23/2020 23:03:45','Hello!','Who wants to meet me for lunch?'),(33,3,1,'09/24/2020 18:03:56','test math post','hello fellow math friends'),(34,3,1,'09/24/2020 18:24:54','Math 301','How hard is Math 301 (abstract algebra) ??'),(35,5,1,'09/25/2020 16:17:23','Hey swim team','hello swimmer friends'),(36,2,1,'09/27/2020 21:56:50','This is a new post!2 @NewGuy @NewGuy','Hi @CameronX @CameronX '),(37,2,1,'09/27/2020 21:59:34','This is a new post!3 @NewGuy @NewGuy','Hi @CameronX @CameronX'),(38,2,1,'09/27/2020 22:00:15','This is a new post!4 @NewGuy @NewGuy','Hi @CameronX @CameronX'),(39,2,1,'09/27/2020 22:03:13','This is a new post!5 @NewGuy @NewGuy','Hi @CameronX @CameronX'),(40,3,1,'09/27/2020 22:48:34','This is a new post!5 @Austin28','Hi @CameronX @CameronX'),(41,3,1,'09/27/2020 22:49:28','This is a new post!5 @NewGuy','Hi @CameronX @Matt123');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-04 15:18:48
