    USE `lab_reporting_db`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Kullanılan şifre : 1234
--

INSERT INTO `users`
VALUES
('standart','{bcrypt}$2a$10$DJrjpequQOb3XkBmpKL..u4Xeh3YtUR2ii7iqooQLVP296s6Zs8Ta',1),
('moderator','{bcrypt}$2a$10$DJrjpequQOb3XkBmpKL..u4Xeh3YtUR2ii7iqooQLVP296s6Zs8Ta',1),
('admin','{bcrypt}$2a$10$DJrjpequQOb3XkBmpKL..u4Xeh3YtUR2ii7iqooQLVP296s6Zs8Ta',1);


CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities4_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities4_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `authorities`
VALUES
('standart','ROLE_STANDART'),
('moderator','ROLE_STANDART'),
('moderator','ROLE_MODERATOR'),
('admin','ROLE_STANDART'),
('admin','ROLE_MODERATOR'),
('admin','ROLE_ADMIN');