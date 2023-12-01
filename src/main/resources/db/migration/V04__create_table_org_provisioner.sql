CREATE TABLE t_org_provisioner (
  id               BIGSERIAL     NOT NULL,
  organisation_id  BIGINT        NOT NULL,
  provisioner_id   BIGINT        NOT NULL,
  secret           VARCHAR(255)  NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_org_provisioner_org_id  FOREIGN KEY (organisation_id) REFERENCES t_organisation(id),
  CONSTRAINT fk_org_provisioner_prov_id FOREIGN KEY (provisioner_id)  REFERENCES t_provisioner(id),
  CONSTRAINT uk_org_provisioner UNIQUE (organisation_id, provisioner_id)
);
