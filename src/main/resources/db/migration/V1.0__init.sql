--DROP DATABASE havelange_db;
--CREATE DATABASE  havelange_db;
CREATE TABLE users (
  id bigserial PRIMARY KEY,
  username varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL
);

ALTER TABLE users ADD CONSTRAINT uq_users_username UNIQUE (username);
ALTER TABLE users ADD CONSTRAINT uq_users_email UNIQUE (email);

CREATE TABLE teams (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  shortName VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  clubColors VARCHAR(255)
);

ALTER TABLE teams ADD CONSTRAINT uq_teams_name UNIQUE (name);
ALTER TABLE teams ADD CONSTRAINT uq_teams_email UNIQUE (email);

CREATE TABLE players (
id bigint PRIMARY KEY,
name varchar(255) NOT NULL,
identity_id varchar(255) NOT NULL,
date_of_birth date NOT NULL,
shirt_number integer NOT NULL,
team_id bigint NOT NULL,
FOREIGN KEY (team_id) REFERENCES teams(id)
);

ALTER TABLE players ADD CONSTRAINT uq_players_name UNIQUE (name);
ALTER TABLE players ADD CONSTRAINT uq_players_shirt_number UNIQUE (shirt_number);
ALTER TABLE players ADD CONSTRAINT chk_players_shirt_number CHECK (shirt_number > 0);
