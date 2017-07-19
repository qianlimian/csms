--// update_casescore
-- Migration SQL that makes the change goes here.

ALTER TABLE case_score ADD COLUMN case_id_ INTEGER;

--//@UNDO
-- SQL to undo the change goes here.


