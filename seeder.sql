-- Adminer 4.8.1 MySQL 5.5.5-10.6.12-MariaDB-0ubuntu0.22.04.1 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
                            `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                            `user_id` bigint(20) unsigned NOT NULL,
                            `post_id` bigint(20) unsigned NOT NULL,
                            `group_id` bigint(20) unsigned NOT NULL,
                            `content` text NOT NULL,
                            `comment_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                            PRIMARY KEY (`id`),
                            KEY `user_id` (`user_id`),
                            KEY `post_id` (`post_id`),
                            KEY `group_id` (`group_id`),
                            CONSTRAINT `comments_ibfk_4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                            CONSTRAINT `comments_ibfk_5` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE,
                            CONSTRAINT `comments_ibfk_6` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
                            CONSTRAINT `comments_ibfk_7` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                            CONSTRAINT `comments_ibfk_8` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
                            CONSTRAINT `comments_ibfk_9` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `comments` (`id`, `user_id`, `post_id`, `group_id`, `content`, `comment_date`) VALUES
                                                                                               (58,	54,	60,	52,	'I used to work in that area and I would go there for lunch all the time! I love love love it!',	'2023-04-30 18:49:31'),
                                                                                               (59,	55,	61,	52,	'I like the food from there but its always hot in the restaurant and the service isn\'t great. For sure worth checking out, just make sure to order to go',	'2023-04-30 19:11:38'),
(61,	56,	65,	45,	'Bro send me a message you can join my clan! ',	'2023-04-30 19:48:41'),
(62,	56,	60,	52,	'Im taking my family here this weekend. Hopefully it lives up to the hype',	'2023-04-30 19:51:08'),
(63,	57,	64,	52,	'Truthfully I\'m always a fan of the neighborhood Chillies but thats just me 🤷‍♂️',	'2023-04-30 20:22:48'),
                                                                                               (64,	57,	60,	52,	'Every time I come visit SA I always start craving this place',	'2023-04-30 20:36:34'),
                                                                                               (65,	59,	62,	53,	'mind blown',	'2023-04-30 21:02:02'),
                                                                                               (67,	31,	64,	52,	'↓ --- Chili\'s**, don\'t disrespect my favorite spot like that. And if you\'re looking for a great place, I would strongly recommend Cappy\'s Restaurant ',	'2023-04-30 23:55:08'),
                                                                                               (68,	61,	65,	45,	'Hey @jeremiahjp can I join too! i Just redownloaded the game after not playing for years so my old clan is pretty inactive',	'2023-05-01 14:36:48'),
                                                                                               (69,	54,	74,	58,	'I actually took a small group of students to a local park last year to go fishing. they got to relax by the water all day and we would talk about what they caught and where it fit into the ecosystem along with some of the plants and birds there too. What grade do you teach? ',	'2023-05-01 14:40:42'),
                                                                                               (70,	47,	81,	59,	'Wait there are tacos?! Save me one!!',	'2023-05-01 15:14:34'),
                                                                                               (71,	63,	67,	56,	'HA! This cracks me up!',	'2023-05-01 15:26:23'),
                                                                                               (72,	63,	78,	52,	'🙋‍♂️ I have! The food is pretty good if your in the mood for some pretty unique burritos, but youre right it is a little expensive for what it is. Good thing is its so much food that one meal can very comfortably feed two people',	'2023-05-01 15:28:58'),
                                                                                               (74,	64,	81,	59,	'He better have tacos!',	'2023-05-01 16:08:50'),
                                                                                               (76,	65,	81,	59,	'He changed it to Tuesday, so now he have Taco Tuesday!',	'2023-05-01 16:28:27');

DROP TABLE IF EXISTS `friendships`;
CREATE TABLE `friendships` (
                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                               `requester` bigint(20) unsigned NOT NULL,
                               `acceptor` bigint(20) unsigned NOT NULL,
                               `is_pending` bit(1) NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `acceptor` (`acceptor`),
                               KEY `requester` (`requester`),
                               CONSTRAINT `friendships_ibfk_4` FOREIGN KEY (`acceptor`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                               CONSTRAINT `friendships_ibfk_5` FOREIGN KEY (`requester`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                               CONSTRAINT `friendships_ibfk_6` FOREIGN KEY (`acceptor`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                               CONSTRAINT `friendships_ibfk_7` FOREIGN KEY (`requester`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                               CONSTRAINT `friendships_ibfk_8` FOREIGN KEY (`acceptor`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                               CONSTRAINT `friendships_ibfk_9` FOREIGN KEY (`requester`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `friendships` (`id`, `requester`, `acceptor`, `is_pending`) VALUES
                                                                            (48,	7,	26,	CONV('0', 2, 10) + 0),
                                                                            (49,	7,	25,	CONV('0', 2, 10) + 0),
                                                                            (64,	8,	26,	CONV('0', 2, 10) + 0),
                                                                            (72,	7,	8,	CONV('0', 2, 10) + 0),
                                                                            (79,	46,	8,	CONV('1', 2, 10) + 0),
                                                                            (80,	46,	31,	CONV('0', 2, 10) + 0),
                                                                            (81,	26,	25,	CONV('0', 2, 10) + 0),
                                                                            (82,	31,	48,	CONV('1', 2, 10) + 0),
                                                                            (83,	31,	7,	CONV('0', 2, 10) + 0),
                                                                            (84,	51,	7,	CONV('0', 2, 10) + 0),
                                                                            (85,	46,	59,	CONV('1', 2, 10) + 0),
                                                                            (87,	31,	59,	CONV('1', 2, 10) + 0),
                                                                            (88,	59,	57,	CONV('0', 2, 10) + 0),
                                                                            (89,	59,	55,	CONV('0', 2, 10) + 0),
                                                                            (90,	59,	56,	CONV('0', 2, 10) + 0),
                                                                            (91,	59,	54,	CONV('0', 2, 10) + 0),
                                                                            (92,	56,	57,	CONV('0', 2, 10) + 0),
                                                                            (93,	56,	55,	CONV('0', 2, 10) + 0),
                                                                            (95,	56,	53,	CONV('1', 2, 10) + 0),
                                                                            (96,	57,	54,	CONV('0', 2, 10) + 0),
                                                                            (97,	57,	55,	CONV('0', 2, 10) + 0),
                                                                            (98,	55,	54,	CONV('0', 2, 10) + 0),
                                                                            (99,	60,	57,	CONV('1', 2, 10) + 0),
                                                                            (100,	60,	55,	CONV('1', 2, 10) + 0),
                                                                            (101,	60,	54,	CONV('0', 2, 10) + 0),
                                                                            (102,	60,	53,	CONV('1', 2, 10) + 0),
                                                                            (103,	8,	64,	CONV('0', 2, 10) + 0),
                                                                            (104,	65,	47,	CONV('1', 2, 10) + 0),
                                                                            (105,	8,	31,	CONV('0', 2, 10) + 0);

DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                          `name` varchar(100) NOT NULL,
                          `description` varchar(1024) NOT NULL,
                          `is_private` bit(1) NOT NULL,
                          `admin_id` bigint(20) unsigned NOT NULL,
                          `group_photo_url` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `admin_id` (`admin_id`),
                          CONSTRAINT `groups_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `groups` (`id`, `name`, `description`, `is_private`, `admin_id`, `group_photo_url`) VALUES
                                                                                                    (45,	'Clash of Clans Fan Club',	'Hog ridAAAAAAAAA',	CONV('0', 2, 10) + 0,	46,	'https://cdn.filestackcontent.com/ErwLgxI2TQ6bbnuY3wEa'),
                                                                                                    (47,	'Flower Enthusiasts',	'Dedicated to the broad spectrum of floral beauty, this page encompasses everything great about flowers. No flower slander will be tolerated!!',	CONV('1', 2, 10) + 0,	31,	'https://cdn.filestackcontent.com/EHkK59sTxshFvu89YwL1'),
                                                                                                    (52,	'Best Food Spots SA',	'Post any new, unique, or just plain-up good food spots in San Antonio or the surrounding area. Make sure to post the type of food served and the price point so that others know what they are getting into! If you\'ve visited a spot that\'s been posted, leave a comment and let others know if you agree with what\'s been said!',	CONV('0', 2, 10) + 0,	53,	'https://cdn.filestackcontent.com/FKcyLKWoSDyy1jfuiF0O'),
(53,	'Shower Thoughts ',	'A catalog of all the weird, strange, or eye-opening thoughts that come to you at the oddest times like in the shower or right before you fall asleep',	CONV('0', 2, 10) + 0,	55,	'https://cdn.filestackcontent.com/4UEwgK2GQMWf31D1kQCL'),
(56,	'New Moms',	'Being a new mom is tough. Restless nights, constant crying, and loads of unanswered questions don\'t make it any easier. Am I doing the right thing? Is this what\'s best for the baby? How in the world do you properly swaddle a baby? This is a group for new moms to share their questions, advice, and anything they need to about their babies',	CONV('0', 2, 10) + 0,	59,	'https://cdn.filestackcontent.com/pLeKgaFJQ3m769VDleNF'),
(58,	'Teachers of America',	'Teacher advice, questions, cool lesson plans funny stories, and anything else that involves teaching the future leaders of America.',	CONV('0', 2, 10) + 0,	57,	'https://cdn.filestackcontent.com/BeVaGwVfRdy1od0oT0JT'),
(59,	'Ymir Students',	'A Group For all Ymir Students to share their thoughts, comments, and questions, as long as they are \"Safe For Work\".',	CONV('1', 2, 10) + 0,	62,	'https://cdn.filestackcontent.com/hpmBuuISPOM5BYLDOMdg');

                                                                                                     DROP TABLE IF EXISTS `memberships`;
CREATE TABLE `memberships` (
                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                               `user_id` bigint(20) unsigned NOT NULL,
                               `group_id` bigint(20) unsigned NOT NULL,
                               `is_pending` bit(1) NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `user_id` (`user_id`),
                               KEY `group_id` (`group_id`),
                               CONSTRAINT `memberships_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                               CONSTRAINT `memberships_ibfk_4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                               CONSTRAINT `memberships_ibfk_5` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE,
                               CONSTRAINT `memberships_ibfk_6` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `memberships` (`id`, `user_id`, `group_id`, `is_pending`) VALUES
                                                                          (88,	25,	45,	CONV('0', 2, 10) + 0),
                                                                          (91,	48,	45,	CONV('0', 2, 10) + 0),
                                                                          (97,	8,	45,	CONV('0', 2, 10) + 0),
                                                                          (99,	46,	47,	CONV('0', 2, 10) + 0),
                                                                          (100,	25,	47,	CONV('0', 2, 10) + 0),
                                                                          (109,	54,	52,	CONV('0', 2, 10) + 0),
                                                                          (110,	55,	52,	CONV('0', 2, 10) + 0),
                                                                          (111,	55,	45,	CONV('0', 2, 10) + 0),
                                                                          (113,	8,	52,	CONV('0', 2, 10) + 0),
                                                                          (114,	8,	53,	CONV('0', 2, 10) + 0),
                                                                          (115,	56,	45,	CONV('0', 2, 10) + 0),
                                                                          (116,	56,	52,	CONV('0', 2, 10) + 0),
                                                                          (117,	57,	52,	CONV('0', 2, 10) + 0),
                                                                          (118,	57,	53,	CONV('0', 2, 10) + 0),
                                                                          (120,	59,	53,	CONV('0', 2, 10) + 0),
                                                                          (122,	7,	53,	CONV('0', 2, 10) + 0),
                                                                          (123,	31,	52,	CONV('0', 2, 10) + 0),
                                                                          (125,	60,	56,	CONV('0', 2, 10) + 0),
                                                                          (126,	60,	58,	CONV('0', 2, 10) + 0),
                                                                          (127,	61,	52,	CONV('0', 2, 10) + 0),
                                                                          (128,	61,	45,	CONV('0', 2, 10) + 0),
                                                                          (129,	54,	58,	CONV('0', 2, 10) + 0),
                                                                          (130,	46,	59,	CONV('0', 2, 10) + 0),
                                                                          (131,	47,	59,	CONV('0', 2, 10) + 0),
                                                                          (132,	53,	53,	CONV('0', 2, 10) + 0),
                                                                          (133,	63,	56,	CONV('0', 2, 10) + 0),
                                                                          (134,	63,	52,	CONV('0', 2, 10) + 0),
                                                                          (137,	64,	47,	CONV('0', 2, 10) + 0),
                                                                          (138,	26,	52,	CONV('0', 2, 10) + 0),
                                                                          (139,	26,	53,	CONV('0', 2, 10) + 0),
                                                                          (140,	65,	59,	CONV('0', 2, 10) + 0),
                                                                          (141,	31,	53,	CONV('0', 2, 10) + 0),
                                                                          (142,	8,	47,	CONV('0', 2, 10) + 0),
                                                                          (143,	62,	47,	CONV('0', 2, 10) + 0),
                                                                          (144,	26,	47,	CONV('0', 2, 10) + 0);

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
                         KEY `FKhf9pfkatuagm490t2jmphqbjx` (`post_type_id`),
                         KEY `user_id` (`user_id`),
                         KEY `group_id` (`group_id`),
                         CONSTRAINT `FKhf9pfkatuagm490t2jmphqbjx` FOREIGN KEY (`post_type_id`) REFERENCES `post_types` (`id`),
                         CONSTRAINT `posts_ibfk_3` FOREIGN KEY (`post_type_id`) REFERENCES `post_types` (`id`),
                         CONSTRAINT `posts_ibfk_4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                         CONSTRAINT `posts_ibfk_5` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE,
                         CONSTRAINT `posts_ibfk_6` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `posts` (`id`, `user_id`, `group_id`, `header`, `body`, `event_date`, `event_address`, `price`, `post_date`, `post_type_id`) VALUES
                                                                                                                                             (60,	53,	52,	'Piranha Izakaya',	'Great place for ramen bowls & sushi. Not much of a sushi guy but their ramen is sooo good. At a pretty reasonable price too. come for their lunch specials or later for happy hour.',	NULL,	NULL,	NULL,	'2023-04-30 18:41:11',	1),
                                                                                                                                             (61,	54,	52,	'Chaba Thai Restaurant',	'A little hole in the wall place on the south side of town. I always get the thai fried rice from there and let me tell you 🤤. Plates are normally around 10-12 dollars.',	NULL,	NULL,	NULL,	'2023-04-30 18:53:59',	1),
                                                                                                                                             (62,	55,	53,	'I have a horrible memory',	'How do our brains remember that we forgot something, but we can\'t remember what that thing was?',	NULL,	NULL,	NULL,	'2023-04-30 19:09:54',	1),
(64,	55,	52,	'Anyone know a good place to go on a date? ',	'Trying to impress her without spending a crazy amount. fancy but not too fancy',	NULL,	NULL,	NULL,	'2023-04-30 19:12:44',	4),
(65,	55,	45,	'Searching For a New Clan',	'Searching for a new clan that is pretty active in clan battles. The clan that Im part of now never seems to join any and when they do I\'m never placed. I have a fully maxed out level 9 town hall',	NULL,	NULL,	NULL,	'2023-04-30 19:34:01',	1),
                                                                                                                                             (66,	57,	53,	'Graduations',	'Watching a graduation ceremony is like sitting through a movie that\'s entirely end credits.',	NULL,	NULL,	NULL,	'2023-04-30 20:34:48',	1),
(67,	59,	56,	'\"why mom?\"',	'Having a toddler in their \"Why?\" phase makes you realize how much you know and don\'t know about things.',	NULL,	NULL,	NULL,	'2023-04-30 21:00:59',	1),
(74,	60,	58,	'Field Trip Ideas',	'Thinking about taking my kids on a field trip where they can have fun AND learn about biomes and ecosystems. Any ideas?',	NULL,	NULL,	NULL,	'2023-05-01 03:00:15',	4),
                                                                                                                                              (77,	7,	53,	'I hate showers....',	'*bbbbrrrrrrrrrrrr*',	NULL,	NULL,	NULL,	'2023-05-01 14:24:16',	1),
                                                                                                                                              (78,	61,	52,	'Anyone Tried \"Stuffed\"',	'There\'s a burrito place called \"Stuffed\" near my house. I\'ve been interested to try it but it seems a little pricy for a burrito $$$. Has anyone else tried it?',	NULL,	NULL,	NULL,	'2023-05-01 14:34:33',	4),
(80,	53,	53,	'typos',	'Making a typo in an online argument is the equivalent of voice cracking in a verbal argument.',	NULL,	NULL,	NULL,	'2023-05-01 15:08:44',	1),
                                                                                                                                               (81,	46,	59,	'Kinda Tired',	'Didn\'t really feel like getting out of bed this morning but DocRob messaged in Slack and said he brought tacos... So I got here early',	NULL,	NULL,	NULL,	'2023-05-01 15:13:37',	1),
(82,	63,	52,	'Torchy\'s Tacos Grand Opening',	'Come visit The new Torchies restaurant for their grand opening! The first 100 people who order get free queso for a year!',	'2023-05-26',	'11654 Bandera Rd Suite 111, San Antonio, TX 78250',	NULL,	'2023-05-01 15:32:58',	2),
                                                                                                                                               (85,	26,	52,	'Big Aloha\'s Ali\'i Cove - UCTX',	'Great Hawaiian food!  Limited hours, but the Musubi and spicy pork are a must-try!!',	NULL,	NULL,	NULL,	'2023-05-01 19:09:45',	1),
                                                                                                                                               (88,	62,	59,	'Review Panel #3',	'Don\'t forget! Review panel #3 is today!',	'2023-05-01',	'1 Fanatical Pl',	NULL,	'2023-05-01 19:26:01',	2);

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
  KEY `FKt279tqgng6ko5q89gaane2ur8` (`post_type_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `FKt279tqgng6ko5q89gaane2ur8` FOREIGN KEY (`post_type_id`) REFERENCES `post_types` (`id`),
  CONSTRAINT `post_type_group_ibfk_2` FOREIGN KEY (`post_type_id`) REFERENCES `post_types` (`id`),
  CONSTRAINT `post_type_group_ibfk_3` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE,
  CONSTRAINT `post_type_group_ibfk_4` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `post_type_group` (`group_id`, `post_type_id`) VALUES
(45,	2),
(45,	4),
(45,	1),
(47,	1),
(52,	2),
(52,	4),
(52,	1),
(53,	4),
(53,	1),
(56,	2),
(56,	3),
(56,	4),
(56,	1),
(58,	1),
(58,	3),
(58,	4),
(59,	1),
(59,	2),
(59,	4);

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `bio` varchar(1024) DEFAULT NULL,
  `profile_photo_url` varchar(255) DEFAULT NULL,
  `reset_password_token` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `users` (`id`, `username`, `first_name`, `last_name`, `email`, `password`, `bio`, `profile_photo_url`, `reset_password_token`) VALUES
(7,	'candywandy',	'Candy',	'Wandy',	'candywandy@email.com',	'$2a$10$iFaN6PULT9d0VIH/W.oujOq7jZHAduzPJdwZS0qRq6Nkw3sEEY10i',	'Im a pampered pug. kinda smol. owned by joey and lorena but my favorite is joey',	'https://cdn.filestackcontent.com/6dT8SNkRSVWfOy3kd0Ab',	NULL),
(8,	'1',	'1',	'1',	'1@email.com',	'$2a$10$60.JD2gmgTYL5oFiWJbihOV/oGfn9gPRUVqS0x3ywQkJyLT/ZIJku',	'',	'https://cdn.filestackcontent.com/9HsDzVvKQEuyUYcEyc3Q',	NULL),
(25,	'Matt',	'Matt',	'Guardiola',	'matt.g.guardiola@gmail.com',	'$2a$10$r.jh0JnKPWTyKJc6AXUcn./vtrBibELM2Dmb2SX0UfvEyHg2UEaPu',	NULL,	'https://cdn.filestackcontent.com/3sZeOctDQBSXLNQJcuTY',	NULL),
(26,	'Joseph',	'Joseph',	'2',	'joseph.mccomas430@gmail.com',	'$2a$10$90xXXcZKDDQIX9u9I3D8c.T87c8VpP0IoMTY0uua/jFaSgkFwWe8e',	'This bio is super-rad!',	'https://cdn.filestackcontent.com/0fDgqXH8RhqYDLbF5apw',	NULL),
(31,	'stalbot0',	'Stephen',	'Talbot',	'stevet727@gmail.com',	'$2a$10$ePD4F.vi09Jns44TxLhlNOs/NnJ4G4ky1TDQUxoWsa6Ma7JbJhZBC',	'Just an ordinary person who has done a few extraordinary things',	'https://cdn.filestackcontent.com/2bcniiE7RwmefkQIfnl3',	NULL),
(46,	'schmerbs46',	'Alex',	'Schmerber',	'schmerbs@email.com',	'$2a$10$WZt4MquBy20bUf4Pytgvruzy.RISxlSQZvksua48psujD3n7UsEtm',	NULL,	'https://cdn.filestackcontent.com/JqYxr09ST6bOKRG0jMvU',	NULL),
(47,	'xXbanana-breadXx',	'Shawn',	'Hardin',	'bananas@email.com',	'$2a$10$W.8b1YF5Di82ppnOHmMCmekmEO/2Pg505.e8Q0TT8x.MIbBSAdA4O',	NULL,	'https://cdn.filestackcontent.com/QTTGUevASAgHNJGKWv3A',	NULL),
(48,	'joe-vandzura',	'Joe',	'Vandzura',	'vandzura.joe.e@gmail.com',	'$2a$10$YsTx3NIGLYy7VA4A40BFUu2wa9t4IkOGfttJvnP3TOGM30E7BlfQq',	NULL,	'https://cdn.filestackcontent.com/tAUE8GO4SmKxwJ7SLc67',	NULL),
(51,	'kvandzura',	'Kelly',	'VanDzura',	'kvandzura31@gmail.com',	'$2a$10$M0rFBscZwO51WQr8YIFqVeWEFq1Mcmc3z3Q.7cVkWDGoI2JBKHWP2',	NULL,	'https://cdn.filestackcontent.com/U0pASIufSFCXR5AZi4dq',	NULL),
(53,	'bakerusa1776',	'Evan',	'Baker',	'evan@email.com',	'$2a$10$SW9zhi2ZJgUseuAd62e4z.UF6NT5QFERfzeZp8k8K5q1/rT722vs2',	NULL,	'https://cdn.filestackcontent.com/3DNzkCWHSia0a5c3TeZK',	NULL),
(54,	'gabygabbss15',	'Gaby',	'Villanueva',	'Gaby@email.com',	'$2a$10$Ne3dKoFW2tFafcHv4Y4BKOIHsgcwp64NT8w1pJGg/eRDtV6js3e8S',	NULL,	'https://cdn.filestackcontent.com/ERadmeAKTAlvCjkXHF7F',	NULL),
(55,	'jonathanmv31',	'John',	'VanDzura',	'John@email.com',	'$2a$10$1jTB7bX5pgAi0M7WDekHXOI5Tor7w1bM5sz9bC9/LieJW/cFakBfW',	NULL,	'https://cdn.filestackcontent.com/16xt7PtTZKw9I6VNEKBL',	NULL),
(56,	'jeremiahjp',	'Jeremiah',	'Pierce',	'Jeremiah@email.com',	'$2a$10$Vf0Dd3syBEqfRO.cyRvsPuMIAOJHaktYEFehDDwJA1RJ9m08iTwi2',	NULL,	'https://cdn.filestackcontent.com/QTKKRpKQpmfgZHyjWizR',	NULL),
(57,	'LivinLikeLauren',	'Lauren',	'Flores',	'Lauren@email.com ',	'$2a$10$z2Eg.P8bCTm/5RxZCi2HGOV8532m5yL.AArw44DVMP2KsYRiaVQB.',	NULL,	'https://cdn.filestackcontent.com/AYolWIcSOyps8Z5SIiO4',	NULL),
(59,	'JensensMom',	'Andrea',	'Pierce',	'Andrea@email.com',	'$2a$10$GG../Lt9XR7V9QxnNCG8PO8Ek5y54WrVqT3wIQFkzBjXoVlrw/Rjq',	NULL,	'https://cdn.filestackcontent.com/ydwiS0rRQLSKDfFFgQ2t',	NULL),
(60,	'selenne_mdz',	'Selenne',	'Mendoza',	'Selenne@email.com',	'$2a$10$9Y.cKLtQuJRIaFKCIMez3OgjRsRvlbwq83/S8e3KRg/da7UywSwoy',	NULL,	'https://cdn.filestackcontent.com/3SmMQubjQ2eXqvkKGSkH',	NULL),
(61,	'BigGreg44',	'Gregorio',	'Garcia',	'greg@email.com',	'$2a$10$zA9DMvPgL71yHVbSY6.gYeTRGFAoi79WQmazF18.GLX5OBbMCJpje',	NULL,	'https://cdn.filestackcontent.com/7cmZSXcBRb2XCPd2VkM0',	NULL),
(62,	'DocRob',	'Mark',	'Robinson',	'mark@gmail.com',	'$2a$10$vsYUrCDvf4J2gUpV7dIYYeV7A7KlXtG/6h8N3GewbePCoFPHsUC9y',	NULL,	'https://cdn.filestackcontent.com/cAKZyoMSlmIMuV1nlCXg',	NULL),
(63,	'tuna',	'Tina',	'Grogan Monga',	'tina@email.com',	'$2a$10$zE7hSgL13nfLH9xkqT0dKe7wl52awcbqtm6twGkqPaED0HDYplfcW',	NULL,	'https://cdn.filestackcontent.com/hbZSn1M0RSq1eVFpe1G4',	NULL),
(64,	'Jsmith',	'Jennie',	'Smith',	'Jsmith9spiral@gmail.com',	'$2a$10$TMVQ5cwvOe6kx17lS./cHOp31EJn5XOZ2SkUoqShP.bG78pOYS3.a',	'',	'https://cdn.filestackcontent.com/UxMxs93cSnSMKVQrjMZO',	NULL),
(65,	'Kevin',	'Kevin',	'Fowler',	'kevin-m-fowler17@gmail.com',	'$2a$10$/8pYtEfCyYkPidjC4qPetOYA.A72YcjqqauBXRJJWDesvHweZxsZS',	'',	'https://cdn.filestackcontent.com/J5HM0UahRpKBmLrSw4Qy',	NULL),
(67,	'FoodLover44',	'Lorena',	'Martinez',	'Lorena@email.com',	'$2a$10$yslp85C6dRjPwthA1VkicuKGwYLY55Fxv9800Qx6ad4rY6UhNeYPa',	'I looooooooove food',	'https://cdn.filestackcontent.com/rEnZPf2DQh4QKlMUNp3Z',	NULL);

-- 2023-05-02 16:55:26