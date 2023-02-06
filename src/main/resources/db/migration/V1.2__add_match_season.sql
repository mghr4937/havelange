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

CREATE TABLE seasons (
  id bigserial PRIMARY KEY,
  tournament_id BIGINT NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  FOREIGN KEY (tournament_id) REFERENCES tournaments(id)
);

CREATE TABLE seasons_matches (
  season_id BIGINT NOT NULL,
  match_id BIGINT NOT NULL,
  PRIMARY KEY (season_id, match_id),
  FOREIGN KEY (season_id) REFERENCES seasons(id) ON DELETE CASCADE,
  FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE
);