-- --------------------------------------------------------
-- Host:                         vm1.private
-- Server version:               10.3.28-MariaDB - MariaDB Server
-- Server OS:                    Linux
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for dm
CREATE DATABASE IF NOT EXISTS `dm` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `dm`;

-- Dumping structure for table dm.api_key
CREATE TABLE IF NOT EXISTS `api_key` (
  `api_key` char(48) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `user` int(11) NOT NULL,
  `key_name` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `last_key_use` datetime DEFAULT NULL,
  PRIMARY KEY (`api_key`),
  KEY `FK__user` (`user`),
  CONSTRAINT `FK__user` FOREIGN KEY (`user`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Data exporting was unselected.

-- Dumping structure for table dm.project
CREATE TABLE IF NOT EXISTS `project` (
  `project_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `project_path` varchar(128) COLLATE utf8_bin NOT NULL,
  `project_name` varchar(1024) COLLATE utf8_bin NOT NULL,
  `mode` enum('master','mirror') COLLATE utf8_bin NOT NULL DEFAULT 'master',
  `description` text COLLATE utf8_bin DEFAULT NULL,
  `owner` int(11) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `project_path` (`project_path`),
  KEY `FK_project_user` (`owner`),
  CONSTRAINT `FK_project_user` FOREIGN KEY (`owner`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Data exporting was unselected.

-- Dumping structure for table dm.project_user
CREATE TABLE IF NOT EXISTS `project_user` (
  `project_id` int(11) unsigned NOT NULL,
  `user_id` int(11) NOT NULL,
  `read` tinyint(1) NOT NULL DEFAULT 1,
  `modify` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`project_id`,`user_id`),
  KEY `FK_project_user_user` (`user_id`),
  CONSTRAINT `FK_project_user_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_project_user_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Data exporting was unselected.

-- Dumping structure for table dm.role
CREATE TABLE IF NOT EXISTS `role` (
  `role_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `role_desc` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Data exporting was unselected.

-- Dumping structure for table dm.system_parameter
CREATE TABLE IF NOT EXISTS `system_parameter` (
  `parameter_id` varchar(50) COLLATE utf8_bin NOT NULL,
  `parameter_value` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`parameter_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Data exporting was unselected.

-- Dumping structure for table dm.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(200) CHARACTER SET ascii NOT NULL,
  `password` varchar(200) CHARACTER SET ascii NOT NULL,
  `email` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `password_date` datetime DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `pwd_err_cnt` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Data exporting was unselected.

-- Dumping structure for table dm.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` int(11) NOT NULL,
  `role_name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`,`role_name`),
  KEY `FK_user_role_role` (`role_name`),
  CONSTRAINT `FK_user_role_role` FOREIGN KEY (`role_name`) REFERENCES `role` (`role_name`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
