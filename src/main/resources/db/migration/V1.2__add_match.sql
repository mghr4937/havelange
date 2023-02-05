CREATE TABLE matches (
  id bigserial PRIMARY KEY,
  tournament_id BIGINT NOT NULL,
  location_id BIGINT NOT NULL,
  home_team_id BIGINT NOT NULL,
  away_team_id BIGINT NOT NULL,
  start_time date NOT NULL,
  home_score INT DEFAULT 0,
  away_score INT DEFAULT 0,
  FOREIGN KEY (tournament_id) REFERENCES tournaments(id),
  FOREIGN KEY (location_id) REFERENCES locations(id),
  FOREIGN KEY (home_team_id) REFERENCES teams(id),
  FOREIGN KEY (away_team_id) REFERENCES teams(id)
);

CREATE TABLE tournaments_matches (
  tournaments_id bigint NOT NULL,
  matches_id bigint NOT NULL,
  PRIMARY KEY (tournaments_id, matches_id),
  FOREIGN KEY (tournaments_id) REFERENCES tournaments(id) ON DELETE CASCADE,
  FOREIGN KEY (matches_id) REFERENCES matches(id) ON DELETE CASCADE
);