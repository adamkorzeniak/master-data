### Setup databases for STAGE environment

CREATE DATABASE stage_authentication_stage;
CREATE DATABASE stage_error_stage;
CREATE DATABASE stage_movie_stage;

CREATE TABLE stage_authentication_stage.users (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE stage_error_stage.errors (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(70) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `error_id` varchar(100) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `stack` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE stage_movie_stage.genres (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE stage_movie_stage.movies (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `year` int(11) NOT NULL,
  `duration` int(11) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `plot_summary` varchar(1000) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `review` varchar(1000) DEFAULT NULL,
  `review_date` date DEFAULT NULL,
  `watch_priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE stage_movie_stage.movie_genres (
  `movie_id` bigint(20) NOT NULL,
  `genre_id` bigint(20) NOT NULL,
  PRIMARY KEY (`movie_id`,`genre_id`),
  CONSTRAINT `fk_movie_genres_1` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
  CONSTRAINT `fk_movie_genres_2` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

GRANT ALL PRIVILEGES ON stage_authentication_stage.* TO stage_rest_api@'%' IDENTIFIED BY 'PASSWORD';
GRANT ALL PRIVILEGES ON stage_error_stage.* TO stage_rest_api@'%' IDENTIFIED BY 'PASSWORD';
GRANT ALL PRIVILEGES ON stage_movie_stage.* TO stage_rest_api@'%' IDENTIFIED BY 'PASSWORD';
FLUSH PRIVILEGES;

