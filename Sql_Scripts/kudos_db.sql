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


DROP TABLE IF EXISTS `rooms_of_user`;
CREATE TABLE `rooms_of_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roomname` varchar(50) NOT NULL unique,
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

DROP TABLE IF EXISTS `users_rooms`;
CREATE TABLE `users_rooms` (
  `user_id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  primary key (`user_id`, `room_id`),
  foreign key (`user_id`) references users (`id`) on delete cascade on update cascade,
  foreign key (`room_id`) references `rooms_of_user` (`id`) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `users_kudos`;
CREATE TABLE `users_kudos` (
  `user_id` int(11) NOT NULL,
  `kudo_id` int(11) NOT NULL,
  primary key (`user_id`, `kudo_id`),
  foreign key (`user_id`) references users (`id`) on delete cascade on update cascade,
  foreign key (`kudo_id`) references kudos (`id`) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `rooms_kudos`;
CREATE TABLE `rooms_kudos` (
  `room_id` int(11) NOT NULL,
  `kudo_id` int(11) NOT NULL,
  primary key (`room_id`, `kudo_id`),
  foreign key (`room_id`) references `rooms_of_user` (`id`) on delete cascade on update cascade,
  foreign key (`kudo_id`) references kudos (`id`) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
