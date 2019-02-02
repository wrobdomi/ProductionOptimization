DROP SCHEMA IF EXISTS `production3`;

CREATE SCHEMA `production3`;

use `production3`;

SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `tasks`;

CREATE TABLE `tasks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `details` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `machines`;

CREATE TABLE `machines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `detail_cost` int(11) DEFAULT NULL,
  `detail_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `tasks_machines`;

CREATE TABLE `tasks_machines` (
  `task_id` int(11) NOT NULL,
  `machine_id` int(11) NOT NULL,
  
  PRIMARY KEY (`task_id`,`machine_id`),
  
  KEY `FK_MACHINE_idx` (`machine_id`),
  
  CONSTRAINT `FK_TASK_05` FOREIGN KEY (`task_id`) 
  REFERENCES `tasks` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_MACHINE` FOREIGN KEY (`machine_id`) 
  REFERENCES `machines` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
