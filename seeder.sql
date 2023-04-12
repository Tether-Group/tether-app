-- Adminer 4.8.1 MySQL 5.5.5-10.6.12-MariaDB-0ubuntu0.22.04.1 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP DATABASE IF EXISTS `tether`;
CREATE DATABASE `tether` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `tether`;

DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends` (
                           `user_id1` bigint(20) unsigned NOT NULL,
                           `user_id2` bigint(20) unsigned NOT NULL,
                           KEY `FKg3b2tdvm0uswr1g1vq3t82nw` (`user_id2`),
                           KEY `FKtn3l6nxg55s8txw7ikp6ika5k` (`user_id1`),
                           CONSTRAINT `FKg3b2tdvm0uswr1g1vq3t82nw` FOREIGN KEY (`user_id2`) REFERENCES `users` (`id`),
                           CONSTRAINT `FKtn3l6nxg55s8txw7ikp6ika5k` FOREIGN KEY (`user_id1`) REFERENCES `users` (`id`),
                           CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`user_id1`) REFERENCES `users` (`id`),
                           CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`user_id2`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                          `name` varchar(100) NOT NULL,
                          `description` varchar(1024) NOT NULL,
                          `is_private` bit(1) NOT NULL,
                          `admin_id` bigint(20) unsigned NOT NULL,
                          PRIMARY KEY (`id`),
                          KEY `FKsnqhvirasbp2bh1ahns2iqeu` (`admin_id`),
                          CONSTRAINT `FKsnqhvirasbp2bh1ahns2iqeu` FOREIGN KEY (`admin_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `groups` (`id`, `name`, `description`, `is_private`, `admin_id`) VALUES
                                                                                 (3,	'The Pug Club',	'A place for all pugs in da world',	CONV('0', 2, 10) + 0,	7),
                                                                                 (4,	'test',	'testing',	CONV('0', 2, 10) + 0,	7),
                                                                                 (5,	'test',	'testing',	CONV('1', 2, 10) + 0,	7),
                                                                                 (9,	'test',	'tttt',	CONV('0', 2, 10) + 0,	7),
                                                                                 (10,	'Cool Math Games',	' A Group for people who like cool math games for kids.',	CONV('0', 2, 10) + 0,	7);

DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts` (
                         `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                         `user_id` bigint(20) unsigned NOT NULL,
                         `group_id` bigint(20) unsigned NOT NULL,
                         `header` varchar(100) NOT NULL,
                         `body` text NOT NULL,
                         `event_date` date DEFAULT NULL,
                         `event_address` varchar(256) DEFAULT NULL,
                         `price` double DEFAULT NULL,
                         `post_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE current_timestamp(),
                         `post_type_id` bigint(20) NOT NULL,
                         PRIMARY KEY (`id`),
                         KEY `FKm9ev48bvdgo25ypcy44mu5t8k` (`group_id`),
                         KEY `FKhf9pfkatuagm490t2jmphqbjx` (`post_type_id`),
                         KEY `FK5lidm6cqbc7u4xhqpxm898qme` (`user_id`),
                         CONSTRAINT `FK5lidm6cqbc7u4xhqpxm898qme` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                         CONSTRAINT `FKhf9pfkatuagm490t2jmphqbjx` FOREIGN KEY (`post_type_id`) REFERENCES `post_types` (`id`),
                         CONSTRAINT `FKm9ev48bvdgo25ypcy44mu5t8k` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
                         CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                         CONSTRAINT `posts_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
                         CONSTRAINT `posts_ibfk_3` FOREIGN KEY (`post_type_id`) REFERENCES `post_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `posts` (`id`, `user_id`, `group_id`, `header`, `body`, `event_date`, `event_address`, `price`, `post_date`, `post_type_id`) VALUES
                                                                                                                                             (2,	7,	3,	'April Pug Pool Party',	'We are having our first Pug Pool Party in April! ALL PUGS INVITED!!!',	'2023-04-12',	'New Braunfels, TX',	NULL,	'2023-04-10 21:02:51',	1),
                                                                                                                                             (3,	7,	3,	'CANCELLED Pug Pool Party',	'Nevermind, I decided I don\'t like pools',	'2023-04-12',	'Nowhere',	NULL,	'2023-04-10 21:11:30',	1),
                                                                                                                                             (4,	7,	3,	'test',	'testing',	NULL,	NULL,	NULL,	'2023-04-11 16:11:58',	1),
                                                                                                                                             (5,	7,	3,	'NEW pug pool party',	'PARTAYYY',	'2023-04-29',	'1234 mama mia',	NULL,	'2023-04-11 16:45:38',	2),
                                                                                                                                             (6,	7,	3,	'New Dog Toy',	'My mom bought it for me and I hate it ',	NULL,	'1234 mama mia',	10,	'2023-04-11 16:56:50',	2);

DROP TABLE IF EXISTS `post_types`;
CREATE TABLE `post_types` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `type` varchar(50) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `post_types` (`id`, `type`) VALUES
                                            (1,	'Post'),
                                            (2,	'Event'),
                                            (3,	'For Sale'),
                                            (4,	'Q&A');

DROP TABLE IF EXISTS `post_type_group`;
CREATE TABLE `post_type_group` (
                                   `group_id` bigint(20) unsigned NOT NULL,
                                   `post_type_id` bigint(20) NOT NULL,
                                   KEY `FKiv7if0raf42hudbykc4pd9x2d` (`group_id`),
                                   KEY `FKt279tqgng6ko5q89gaane2ur8` (`post_type_id`),
                                   CONSTRAINT `FKiv7if0raf42hudbykc4pd9x2d` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
                                   CONSTRAINT `FKt279tqgng6ko5q89gaane2ur8` FOREIGN KEY (`post_type_id`) REFERENCES `post_types` (`id`),
                                   CONSTRAINT `post_type_group_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
                                   CONSTRAINT `post_type_group_ibfk_2` FOREIGN KEY (`post_type_id`) REFERENCES `post_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `post_type_group` (`group_id`, `post_type_id`) VALUES
                                                               (9,	1),
                                                               (10,	1),
                                                               (10,	3);

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                         `username` varchar(100) NOT NULL,
                         `first_name` varchar(100) NOT NULL,
                         `last_name` varchar(100) NOT NULL,
                         `email` varchar(100) NOT NULL,
                         `password` varchar(100) NOT NULL,
                         `bio` varchar(1024) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `users` (`id`, `username`, `first_name`, `last_name`, `email`, `password`, `bio`) VALUES
                                                                                                  (7,	'candywandy',	'Candy',	'Wandy',	'candywandy@email.com',	'$2a$10$iFaN6PULT9d0VIH/W.oujOq7jZHAduzPJdwZS0qRq6Nkw3sEEY10i',	NULL),
                                                                                                  (8,	'1',	'1',	'1',	'1@email.com',	'$2a$10$9WLAfQsthzqf4wwZIbUhRONT.nqaHhhTgMHhoM0iGCLQVPdQo8Ske',	NULL),
                                                                                                  (12,	'test',	'test',	'tester',	'test@email.com',	'1',	NULL);

DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
                              `user_id` bigint(20) unsigned NOT NULL,
                              `group_id` bigint(20) unsigned NOT NULL,
                              KEY `user_id` (`user_id`),
                              KEY `group_id` (`group_id`),
                              CONSTRAINT `user_group_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                              CONSTRAINT `user_group_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `user_group` (`user_id`, `group_id`) VALUES
    (7,	3);

-- 2023-04-12 14:29:36