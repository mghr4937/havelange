CREATE TABLE tournaments (
  id bigserial PRIMARY KEY,
  name varchar(255) NOT NULL,
  country varchar(255) NOT NULL,
  city varchar(255) NOT NULL,
  enabled boolean NOT NULL DEFAULT true
);

CREATE TABLE tournaments_teams (
  tournaments_id bigint NOT NULL,
  teams_id bigint NOT NULL,
  PRIMARY KEY (tournaments_id, teams_id),
  FOREIGN KEY (tournaments_id) REFERENCES tournaments(id) ON DELETE CASCADE,
  FOREIGN KEY (teams_id) REFERENCES teams(id) ON DELETE CASCADE
);

CREATE TABLE locations (
  id bigserial PRIMARY KEY,
  name varchar(255) NOT NULL,
  address varchar(255) NOT NULL,
  tournaments_id bigint NOT NULL,
  FOREIGN KEY (tournaments_id) REFERENCES tournaments(id)
);

CREATE TABLE tournaments_locations (
  tournaments_id bigint NOT NULL,
  locations_id bigint NOT NULL,
  PRIMARY KEY (tournaments_id, locations_id),
  FOREIGN KEY (tournaments_id) REFERENCES tournaments(id) ON DELETE CASCADE,
  FOREIGN KEY (locations_id) REFERENCES locations(id) ON DELETE CASCADE
);