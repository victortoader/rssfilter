CREATE DATABASE `rss`;

USE `rss`;

CREATE TABLE `feeds` (
  `guid` varchar(256) NOT NULL,
  `title` text NOT NULL,
  `description` text NOT NULL,
  `publication_date` timestamp NOT NULL,
  `image` blob,
  `image_url` varchar(256),
  PRIMARY KEY (`guid`),
  UNIQUE KEY `guid_UNIQUE` (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;