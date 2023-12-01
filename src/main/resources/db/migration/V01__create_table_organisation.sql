CREATE TABLE t_organisation (
  id               BIGSERIAL     NOT NULL,
  name             VARCHAR(255)  NOT NULL UNIQUE,
  PRIMARY KEY (id)
);
