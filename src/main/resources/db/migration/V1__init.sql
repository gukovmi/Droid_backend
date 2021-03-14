SET NAMES utf8;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT 'USER',
  PRIMARY KEY (`id`)
);