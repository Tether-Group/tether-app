-- Adminer 4.8.1 MySQL 5.5.5-10.6.12-MariaDB-0ubuntu0.22.04.1 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP DATABASE IF EXISTS `tether`;
CREATE DATABASE `tether` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `tether`;

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
                            `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                            `user_id` bigint(20) unsigned NOT NULL,
                            `post_id` bigint(20) unsigned NOT NULL,
                            `group_id` bigint(20) unsigned NOT NULL,
                            `content` text NOT NULL,
                            `comment_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                            PRIMARY KEY (`id`),
                            KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
                            KEY `FK4lwo91sigt2fg0w8xw2k2aukt` (`group_id`),
                            KEY `FKh4c7lvsc298whoyd4w9ta25cr` (`post_id`),
                            CONSTRAINT `FK4lwo91sigt2fg0w8xw2k2aukt` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
                            CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                            CONSTRAINT `FKh4c7lvsc298whoyd4w9ta25cr` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
                            CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                            CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
                            CONSTRAINT `comments_ibfk_3` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `comments` (`id`, `user_id`, `post_id`, `group_id`, `content`, `comment_date`) VALUES
                                                                                               (21,	8,	24,	27,	'please work',	'2023-04-19 16:50:05'),
                                                                                               (24,	8,	20,	31,	'Here is my test comment',	'2023-04-19 20:21:24');

DROP TABLE IF EXISTS `friendships`;
CREATE TABLE `friendships` (
                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                               `requester` bigint(20) unsigned NOT NULL,
                               `acceptor` bigint(20) unsigned NOT NULL,
                               `is_pending` bit(1) NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FKmuddhduq4ai68orcp03wneai9` (`acceptor`),
                               KEY `FK19uvyhwfk29te3bmuuefd4mwj` (`requester`),
                               CONSTRAINT `FK19uvyhwfk29te3bmuuefd4mwj` FOREIGN KEY (`requester`) REFERENCES `users` (`id`),
                               CONSTRAINT `FKg3b2tdvm0uswr1g1vq3t82nw` FOREIGN KEY (`acceptor`) REFERENCES `users` (`id`),
                               CONSTRAINT `FKmuddhduq4ai68orcp03wneai9` FOREIGN KEY (`acceptor`) REFERENCES `users` (`id`),
                               CONSTRAINT `FKtn3l6nxg55s8txw7ikp6ika5k` FOREIGN KEY (`requester`) REFERENCES `users` (`id`),
                               CONSTRAINT `friendships_ibfk_1` FOREIGN KEY (`requester`) REFERENCES `users` (`id`),
                               CONSTRAINT `friendships_ibfk_2` FOREIGN KEY (`acceptor`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `friendships` (`id`, `requester`, `acceptor`, `is_pending`) VALUES
                                                                            (34,	8,	25,	CONV('0', 2, 10) + 0),
                                                                            (41,	8,	7,	CONV('0', 2, 10) + 0),
                                                                            (42,	8,	26,	CONV('1', 2, 10) + 0),
                                                                            (44,	24,	26,	CONV('1', 2, 10) + 0),
                                                                            (45,	24,	25,	CONV('1', 2, 10) + 0),
                                                                            (46,	24,	13,	CONV('0', 2, 10) + 0),
                                                                            (47,	24,	8,	CONV('0', 2, 10) + 0),
                                                                            (48,	7,	26,	CONV('1', 2, 10) + 0),
                                                                            (49,	7,	25,	CONV('1', 2, 10) + 0),
                                                                            (50,	7,	13,	CONV('0', 2, 10) + 0),
                                                                            (51,	7,	24,	CONV('0', 2, 10) + 0);

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
                                                                                 (24,	'Jo Jo Bears',	'This is the official group for all of the jo\'s in the world',	CONV('0', 2, 10) + 0,	24),
                                                                                 (27,	'The Private Group',	'This group is to test the private group functionality',	CONV('1', 2, 10) + 0,	8),
                                                                                 (28,	'Secret Spy Group',	'*CLASSIFIED*',	CONV('1', 2, 10) + 0,	8),
                                                                                 (29,	'American Bulldogs',	'160lbs of sweet, sweet love!  Be nice, or we\'ll eat your face.',	CONV('0', 2, 10) + 0,	26),
                                                                                 (30,	'Costco Lovers',	'We post deals, sales, and everything hot at Costco',	CONV('0', 2, 10) + 0,	25),
                                                                                 (31,	'13',	'13',	CONV('0', 2, 10) + 0,	27),
                                                                                 (32,	'beach ball group ',	'a group about beach balls ',	CONV('0', 2, 10) + 0,	25);

DROP TABLE IF EXISTS `memberships`;
CREATE TABLE `memberships` (
                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                               `user_id` bigint(20) unsigned NOT NULL,
                               `group_id` bigint(20) unsigned NOT NULL,
                               `is_pending` bit(1) NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FKpt6r69tdax6f92k7p7a4w8m6` (`group_id`),
                               KEY `FKdjormybfoo7f4i4d4r803qohb` (`user_id`),
                               CONSTRAINT `FKdjormybfoo7f4i4d4r803qohb` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                               CONSTRAINT `FKpt6r69tdax6f92k7p7a4w8m6` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
                               CONSTRAINT `memberships_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                               CONSTRAINT `memberships_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `memberships` (`id`, `user_id`, `group_id`, `is_pending`) VALUES
                                                                          (9,	13,	3,	CONV('0', 2, 10) + 0),
                                                                          (18,	8,	3,	CONV('0', 2, 10) + 0),
                                                                          (24,	24,	24,	CONV('0', 2, 10) + 0),
                                                                          (28,	24,	27,	CONV('0', 2, 10) + 0),
                                                                          (29,	24,	3,	CONV('0', 2, 10) + 0),
                                                                          (31,	25,	27,	CONV('0', 2, 10) + 0),
                                                                          (33,	26,	29,	CONV('0', 2, 10) + 0),
                                                                          (34,	25,	30,	CONV('0', 2, 10) + 0),
                                                                          (35,	26,	3,	CONV('0', 2, 10) + 0),
                                                                          (36,	13,	30,	CONV('0', 2, 10) + 0),
                                                                          (37,	26,	30,	CONV('0', 2, 10) + 0),
                                                                          (38,	26,	24,	CONV('0', 2, 10) + 0),
                                                                          (42,	24,	29,	CONV('0', 2, 10) + 0),
                                                                          (45,	25,	3,	CONV('0', 2, 10) + 0),
                                                                          (46,	25,	32,	CONV('0', 2, 10) + 0),
                                                                          (49,	7,	24,	CONV('0', 2, 10) + 0),
                                                                          (53,	7,	29,	CONV('0', 2, 10) + 0),
                                                                          (54,	8,	31,	CONV('0', 2, 10) + 0),
                                                                          (55,	13,	24,	CONV('0', 2, 10) + 0),
                                                                          (56,	29,	24,	CONV('0', 2, 10) + 0);

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
                                                                                                                                             (4,	7,	3,	'Took the best walk of my life',	'I LOOOOOOVE WALKS',	NULL,	NULL,	NULL,	'2023-04-11 16:11:58',	1),
                                                                                                                                             (5,	7,	3,	'NEW pug pool party',	'PARTAYYY WOOO',	'2023-04-29',	'1234 mama mia',	NULL,	'2023-04-11 16:45:38',	2),
                                                                                                                                             (6,	7,	3,	'New Dog Toy!!!',	'My mom bought it for me and I hate it ',	NULL,	'1234 mama mia',	13,	'2023-04-14 14:45:41',	3),
                                                                                                                                             (11,	13,	3,	'Please come to my cat\'s birthday party',	'My cat is sickly and needs friends before I put her down. Please join me in celebrating her wonderful, yet lazy, life.',	'2023-04-20',	'44 sesame street',	NULL,	'2023-04-14 18:36:38',	2),
                                                                                                                                             (12,	25,	30,	'NEW COSTCO OPENING',	'This brand new costco is opening by my house and is going to have SICK deals',	'2023-05-25',	'4455 Costco Street',	NULL,	'2023-04-14 18:39:09',	2),
                                                                                                                                             (13,	26,	29,	'Durable toys for AB?',	'Does anyone have any used vehicle tires?',	NULL,	NULL,	NULL,	'2023-04-14 18:44:34',	1),
                                                                                                                                             (16,	8,	3,	'I want a pug, but I am allergic',	'So sad :(',	NULL,	NULL,	NULL,	'2023-04-17 20:56:39',	1),
                                                                                                                                             (17,	25,	3,	'Why are pugs so wrinkly',	'they have enough skin to be a bigger dog but they are smol',	NULL,	NULL,	NULL,	'2023-04-18 20:40:28',	4),
                                                                                                                                             (18,	8,	3,	'testing mapbox',	'this is a test',	'2023-04-19',	'austin, tx',	NULL,	'2023-04-19 03:16:44',	2),
                                                                                                                                             (19,	8,	3,	'Please come to my cat\'s birthday party',	'hehe',	'2023-04-20',	'austin, tx',	NULL,	'2023-04-19 03:20:33',	2),
                                                                                                                                             (20,	8,	31,	'testing mapbox',	'testing',	'2023-04-21',	'austin, tx',	NULL,	'2023-04-19 03:25:22',	2),
                                                                                                                                             (23,	13,	24,	'I am not a Jo',	'please help',	'2023-04-27',	'Windcrest, TX',	NULL,	'2023-04-19 16:18:42',	2),
                                                                                                                                             (24,	25,	27,	'Im making a post in a private group ',	'ooooo so secret shhhhhh',	NULL,	NULL,	NULL,	'2023-04-19 16:19:07',	1);

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
                                                               (27,	2),
                                                               (27,	3),
                                                               (27,	4),
                                                               (27,	1),
                                                               (24,	2),
                                                               (24,	4),
                                                               (24,	1),
                                                               (28,	4),
                                                               (28,	1),
                                                               (30,	2),
                                                               (30,	4),
                                                               (30,	1),
                                                               (29,	1),
                                                               (29,	3),
                                                               (31,	2),
                                                               (31,	4),
                                                               (31,	1),
                                                               (32,	2),
                                                               (32,	1),
                                                               (3,	2),
                                                               (3,	3),
                                                               (3,	4),
                                                               (3,	1);

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
                                                                                                  (8,	'1',	'1',	'1',	'1@email.com',	'$2a$10$60.JD2gmgTYL5oFiWJbihOV/oGfn9gPRUVqS0x3ywQkJyLT/ZIJku',	NULL),
                                                                                                  (13,	'StephenAdmin',	'Stephen',	'Admin',	'StephenAdmin@example.com',	'$2a$10$MhQwcxzHEVme6tRLOq36BOC26dKT6dOk6WH1HB5yJ1akaTCp9fH9q',	NULL),
                                                                                                  (15,	'2',	'2',	'2',	'2@email.com',	'$2a$10$cX4QMytdY3rdzry5YD4lFeEohZ7H3WRv3k3PvWPatBfe5HwLaBgDS',	NULL),
                                                                                                  (20,	'Alex',	'alex',	'shmerbs',	'@email',	'$2a$10$CZFCtc1q69tZtSbjX4Nqc.A8HEOW1lMFnotRjz7yAl5e1N1vYruIm',	NULL),
                                                                                                  (24,	'joe',	'joe',	'joe',	'joe@email.com',	'$2a$10$JOgv8fJXJP9w39Nsr8OvX.WlIooS87E45976QRbOdvBrUvtymVza.',	NULL),
                                                                                                  (25,	'Matt',	'Matt',	'Guardiola',	'matt.g.guardiola@gmail.com',	'$2a$10$r.jh0JnKPWTyKJc6AXUcn./vtrBibELM2Dmb2SX0UfvEyHg2UEaPu',	NULL),
                                                                                                  (26,	'Joseph',	'Joseph',	'2',	'joseph.mccomas430@gmail.com',	'$2a$10$90xXXcZKDDQIX9u9I3D8c.T87c8VpP0IoMTY0uua/jFaSgkFwWe8e',	NULL),
                                                                                                  (27,	'12',	'12',	'12',	'12@example.com',	'$2a$10$9WcKK/NoYE2h2gN2LxyTTuYT9A4UTZniAonGHI4PSjs8qSW8MGnJu',	NULL),
                                                                                                  (29,	'tester',	'Test',	'Testing',	'test@email.com',	'$2a$10$dJy9XZMM5R/DuH1O8eSwM.MQ0OWpIzTdN.N8Llrb5Nh./0WX4aV9.',	NULL),
                                                                                                  (31,	'stalbot0',	'Stephen',	'Talbot',	'stevet727@gmail.com',	'$2a$10$jlNVC.gCcs10YTzoJ6sq1O48TOrRy9VdKiWNJE/yItykWB0jQvYkO',	NULL);

-- 2023-04-19 21:02:59