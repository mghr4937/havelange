CREATE TABLE tournaments (
  id bigserial PRIMARY KEY,
  name varchar(255) NOT NULL,
  country varchar(255) NOT NULL,
  city varchar(255) NOT NULL,
  enabled boolean NOT NULL DEFAULT true
);

CREATE UNIQUE INDEX uq_tournaments_name ON tournaments (name);

CREATE TABLE tournament_teams (
  tournament_id bigint NOT NULL,
  team_id bigint NOT NULL,
  PRIMARY KEY (tournament_id, team_id),
  FOREIGN KEY (tournament_id) REFERENCES tournaments(id) ON DELETE CASCADE,
  FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE
);

CREATE TABLE locations (
  id bigserial PRIMARY KEY,
  name varchar(255) NOT NULL,
  address varchar(255) NOT NULL,
  tournament_id bigint NOT NULL,
  FOREIGN KEY (tournament_id) REFERENCES tournaments(id)
);