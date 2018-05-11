CREATE TABLE user (
  id       BIGINT NOT NULL AUTO_INCREMENT,
  name     VARCHAR(255),
  password VARCHAR(255),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET utf8;

ALTER TABLE user
  ADD CONSTRAINT UK_gj2fy3dcix7ph7k8684gka40c UNIQUE (name);
alter table adminlte.user add column email varchar(255);
alter table user drop index UK_ob8kqyqqgmefl0aco34akdtpe;
alter table user add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);