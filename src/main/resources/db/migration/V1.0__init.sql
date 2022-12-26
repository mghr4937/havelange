create sequence user_id_sequence;

CREATE TABLE users (
  id bigint NOT NULL CONSTRAINT pk_user PRIMARY KEY,
  username VARCHAR(256) NOT NULL
);

INSERT INTO users ('admin')