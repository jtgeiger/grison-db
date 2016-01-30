-- // Use larger datatype for uptime
-- Migration SQL that makes the change goes here.

ALTER TABLE cam_image MODIFY uptime BIGINT UNSIGNED NOT NULL;

-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE cam_image MODIFY uptime INT UNSIGNED NOT NULL;
