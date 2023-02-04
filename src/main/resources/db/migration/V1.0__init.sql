CREATE TABLE users (
  id bigserial PRIMARY KEY,
  username varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  token varchar(255),
  enabled boolean NOT NULL DEFAULT true,
  last_request_date timestamp
);

CREATE UNIQUE INDEX uq_users_username ON users (username);
CREATE UNIQUE INDEX uq_users_email ON users (email);

CREATE TABLE teams (
  id bigserial PRIMARY KEY ,
  name VARCHAR(255) NOT NULL,
  shortname VARCHAR(255),
  city VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  club_colors VARCHAR(255),
  enabled boolean NOT NULL DEFAULT true
);

CREATE UNIQUE INDEX uq_teams_name ON teams (name);
CREATE UNIQUE INDEX uq_teams_email ON teams (email);

CREATE TABLE players (
id bigserial PRIMARY KEY,
name varchar(255) NOT NULL,
last_name varchar(255) NOT NULL,
identity_id varchar(255) NOT NULL,
gender varchar(255),
date_of_birth date NOT NULL,
shirt_number integer NOT NULL,
team_id bigint NOT NULL,
enabled boolean NOT NULL DEFAULT true,
FOREIGN KEY (team_id) REFERENCES teams(id)
);

ALTER TABLE players ADD CONSTRAINT chk_players_shirt_number CHECK (shirt_number > 0);

CREATE TABLE teams_players (
    teams_id bigint NOT NULL,
    players_id bigint NOT NULL,
    PRIMARY KEY (teams_id, players_id),
    FOREIGN KEY (teams_id) REFERENCES teams(id) ON DELETE CASCADE,
    FOREIGN KEY (players_id) REFERENCES players(id) ON DELETE CASCADE
);