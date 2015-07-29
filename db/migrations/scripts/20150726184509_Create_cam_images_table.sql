-- // Create cam_images table
-- Migration SQL that makes the change goes here.


CREATE TABLE cam_image (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  image_name VARCHAR(255) NOT NULL,
  cam_timestamp TIMESTAMP NOT NULL,
  server_timestamp TIMESTAMP NOT NULL,
  uptime INT UNSIGNED NOT NULL,
  cam_session INT UNSIGNED NOT NULL,
  CONSTRAINT fk_cam_image_cam_session FOREIGN KEY (cam_session) REFERENCES cam_session (id),
  PRIMARY KEY (id)
);


-- //@UNDO
-- SQL to undo the change goes here.


ALTER TABLE cam_image
  DROP FOREIGN KEY fk_cam_image_cam_session;

DROP INDEX fk_cam_image_cam_session ON cam_image;

DROP TABLE cam_image;

