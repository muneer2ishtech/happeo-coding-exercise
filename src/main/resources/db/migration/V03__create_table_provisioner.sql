CREATE TABLE t_provisioner (
  id               BIGSERIAL     NOT NULL,
  name             VARCHAR(255)  NOT NULL UNIQUE,
  PRIMARY KEY (id)
);
