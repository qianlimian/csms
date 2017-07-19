--// update_bdmstation_and_bdmstrap
-- Migration SQL that makes the change goes here.

DROP TABLE bdm_station;
DROP TABLE bdm_strap;
CREATE TABLE bdm_station (id_ INTEGER NOT NULL, code_ INTEGER, insert_date_ DATETIME, ip_ VARCHAR(255), name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, status_ VARCHAR(255), update_date_ DATETIME, room_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE bdm_strap (id_ INTEGER NOT NULL, code_ INTEGER, insert_date_ DATETIME, name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, status_ VARCHAR(255), update_date_ DATETIME, handling_area_id_ INTEGER, PRIMARY KEY (id_));

--//@UNDO
-- SQL to undo the change goes here.


