drop database `kudos_db`;
CREATE DATABASE  IF NOT EXISTS `kudos_db`;
USE `kudos_db`;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL unique,
  `password` varchar(68) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `groups_of_user`;
CREATE TABLE `groups_of_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupname` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `kudos`;
CREATE TABLE `kudos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(50) NOT NULL,
  `user_id` int(11) NOT NULL,
  foreign key (`user_id`) references users (`id`) on delete cascade on update cascade,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `users_groups`;
CREATE TABLE `users_groups` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  primary key (`user_id`, `group_id`),
  foreign key (`user_id`) references users (`id`) on delete cascade on update cascade,
  foreign key (`group_id`) references `groups_of_user` (`id`) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `users_kudos`;
CREATE TABLE `users_kudos` (
  `user_id` int(11) NOT NULL,
  `kudo_id` int(11) NOT NULL,
  primary key (`user_id`, `kudo_id`),
  foreign key (`user_id`) references users (`id`) on delete cascade on update cascade,
  foreign key (`kudo_id`) references kudos (`id`) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `groups_kudos`;
CREATE TABLE `groups_kudos` (
  `group_id` int(11) NOT NULL,
  `kudo_id` int(11) NOT NULL,
  primary key (`group_id`, `kudo_id`),
  foreign key (`group_id`) references `groups_of_user` (`id`) on delete cascade on update cascade,
  foreign key (`kudo_id`) references kudos (`id`) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
