
CREATE TABLE users (
  id bigserial PRIMARY KEY,
  username varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL
);

ALTER TABLE users ADD CONSTRAINT uq_users_username UNIQUE (username);
ALTER TABLE users ADD CONSTRAINT uq_users_email UNIQUE (email);

CREATE TABLE teams (
  id bigserial PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  shortName VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  clubColors VARCHAR(255)
);

ALTER TABLE teams ADD CONSTRAINT uq_teams_name UNIQUE (name);
ALTER TABLE teams ADD CONSTRAINT uq_teams_email UNIQUE (email);


