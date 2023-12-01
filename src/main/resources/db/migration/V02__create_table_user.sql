CREATE TABLE t_user (
  id               BIGSERIAL     NOT NULL,
  email            VARCHAR(255)  NOT NULL UNIQUE,
  password_hash    VARCHAR(255)      NULL,
  first_name       VARCHAR(255)  NOT NULL,
  last_name        VARCHAR(255)  NOT NULL,
  organisation_id  BIGINT        NOT NULL,
  external_id      VARCHAR(255)      NULL,
  is_active        BOOLEAN       NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_user_org_id  FOREIGN KEY (organisation_id) REFERENCES t_organisation(id),
  CONSTRAINT uk_user_org_external_id UNIQUE (organisation_id, external_id)
);
