-- // First migration.
-- Migration SQL that makes the change goes here.

CREATE TABLE cam_session (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  camera_id VARCHAR(255) NOT NULL,
  firmware_version_major INT UNSIGNED NOT NULL,
  firmware_version_minor INT UNSIGNED NOT NULL,
  firmware_version_patch INT UNSIGNED NOT NULL,
  firmware_version_build INT UNSIGNED NOT NULL,
  connect_time TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE cam_alarm (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  alarm_type INT UNSIGNED NOT NULL,
  alarm_time TIMESTAMP NOT NULL,
  cam_session INT UNSIGNED NOT NULL,
  CONSTRAINT fk_cam_session FOREIGN KEY (cam_session) REFERENCES cam_session (id),
  PRIMARY KEY (id)
);

-- //@UNDO
-- SQL to undo the change goes here.


DROP TABLE cam_alarm;

DROP TABLE cam_session;
