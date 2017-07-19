--// case_people_trace
-- Migration SQL that makes the change goes here.

CREATE TABLE case_people_trace (id_ INTEGER NOT NULL, enter_time_ DATETIME, leave_time_ DATETIME, room_name_ VARCHAR(255), case_people_id_ INTEGER, PRIMARY KEY (id_));


--//@UNDO
-- SQL to undo the change goes here.


