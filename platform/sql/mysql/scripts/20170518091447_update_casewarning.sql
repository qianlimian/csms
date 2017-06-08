--// update_casewarning
-- Migration SQL that makes the change goes here.
drop table case_warning;
CREATE TABLE case_warning (id_ INTEGER NOT NULL, case_process_ VARCHAR(255), code_ VARCHAR(255), insert_date_ DATETIME, name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, update_date_ DATETIME, case_id_ INTEGER, PRIMARY KEY (id_));


--//@UNDO
-- SQL to undo the change goes here.


