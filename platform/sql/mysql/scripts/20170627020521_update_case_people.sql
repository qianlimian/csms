--// update_case_people
-- Migration SQL that makes the change goes here.

ALTER TABLE `case_people` ADD COLUMN `photo_`  TEXT NULL;

--//@UNDO
-- SQL to undo the change goes here.


