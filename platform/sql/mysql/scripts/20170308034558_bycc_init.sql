--// bycc_init
-- Migration SQL that makes the change goes here

CREATE TABLE bdm_cabinet (id_ INTEGER NOT NULL, code_ VARCHAR(255), insert_date_ DATETIME, name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, status_ VARCHAR(255), update_date_ DATETIME, handling_area_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE bdm_camera (id_ INTEGER NOT NULL, insert_date_ DATETIME, ip_ VARCHAR(255), name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, status_ VARCHAR(255), update_date_ DATETIME, room_id_ INTEGER, userName_ VARCHAR(255), password_ VARCHAR(255), PRIMARY KEY (id_));
CREATE TABLE bdm_classification (id_ INTEGER NOT NULL, case_type_ VARCHAR(255), insert_date_ DATETIME, keyword_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, risk_level_ VARCHAR(255), update_date_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE bdm_evaluation (id_ INTEGER NOT NULL, eval_type_ VARCHAR(255), insert_date_ DATETIME, note_ VARCHAR(255), operator_id_ INTEGER, score_ DECIMAL(38), score_type_ VARCHAR(255), standard_ VARCHAR(255), update_date_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE bdm_police_station (id_ INTEGER NOT NULL, address_ VARCHAR(255), area_type_ VARCHAR(255), code_ VARCHAR(255), insert_date_ DATETIME, ip_ VARCHAR(255), name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, update_date_ DATETIME, police_station_type_ VARCHAR(255), PRIMARY KEY (id_));
CREATE TABLE bdm_police (id_ INTEGER NOT NULL, user_id_ INTEGER, duty_type_ VARCHAR(255), insert_date_ DATETIME, note_ VARCHAR(255), operator_id_ INTEGER, police_station_id_ INTEGER, update_date_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE bdm_handling_area (id_ INTEGER NOT NULL, address_ VARCHAR(255), area_type_ VARCHAR(255), code_ VARCHAR(255), insert_date_ DATETIME, name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, update_date_ DATETIME, police_station_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE bdm_room (id_ INTEGER NOT NULL, code_ VARCHAR(255), insert_date_ DATETIME, name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, room_type_ VARCHAR(255), status_ VARCHAR(255), update_date_ DATETIME, handling_area_id_ INTEGER, position_ varchar(255), PRIMARY KEY (id_));
CREATE TABLE bdm_station (id_ INTEGER NOT NULL, insert_date_ DATETIME, ip_ VARCHAR(255), name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, status_ VARCHAR(255), update_date_ DATETIME, room_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE bdm_strap (id_ INTEGER NOT NULL, code_ VARCHAR(255), insert_date_ DATETIME, name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, status_ VARCHAR(255), update_date_ DATETIME, handling_area_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE bdm_video_category (id_ INTEGER NOT NULL, code_ VARCHAR(255), insert_date_ DATETIME, name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, update_date_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE caze (id_ INTEGER NOT NULL, accept_date_ DATETIME, accept_police_id_ INTEGER, alarm_code_ VARCHAR(255), case_code_ VARCHAR(255), case_name_ VARCHAR(255), case_status_ VARCHAR(255), case_summary_ VARCHAR(255), case_type_ VARCHAR(255), risk_level_ VARCHAR(255), close_date_ DATETIME, insert_date_ DATETIME, master_police_id_ INTEGER, note_ VARCHAR(255), occur_date_ DATETIME, operator_id_ INTEGER, register_date_ DATETIME, slave_police_id_ INTEGER, suspect_ VARCHAR(255), update_date_ DATETIME, accept_unit_id_ INTEGER, master_unit_id_ INTEGER, slave_unit_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE case_media (id_ INTEGER NOT NULL, case_people_id_ INTEGER, case_record_id_ INTEGER, category_id_ INTEGER, insert_date_ DATETIME, note_ VARCHAR(255), operator_ VARCHAR(255), title_ VARCHAR(255), update_date_ DATETIME, url_ VARCHAR(255), PRIMARY KEY (id_));
CREATE TABLE case_people (id_ INTEGER NOT NULL, address_ VARCHAR(255), birthday_ DATETIME, certificate_num_ VARCHAR(255), certificate_type_ VARCHAR(255), enter_date_ DATETIME, enter_reason_ VARCHAR(255), other_enter_reason_ VARCHAR(255), gender_ VARCHAR(255), insert_date_ DATETIME, leave_date_ DATETIME, leave_reason_ VARCHAR(255), name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, people_type_ VARCHAR(255), tel_num_ VARCHAR(255), update_date_ DATETIME, case_record_id_ INTEGER, strap_id_ INTEGER, all_belongs_return_ TINYINT(1) default 0, PRIMARY KEY (id_));
CREATE TABLE case_people_belongs (id_ INTEGER NOT NULL, back_date_ DATETIME, back_or_not_ TINYINT(1) default 0, count_ INTEGER, description_ VARCHAR(255), insert_date_ DATETIME, involved_or_not_ TINYINT(1) default 0, name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, store_type_ VARCHAR(255), unit_ VARCHAR(255), update_date_ DATETIME, cabinet_id_ INTEGER, case_people_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE case_people_inspect (id_ INTEGER NOT NULL, collect_or_not_ TINYINT(1) default 0, insert_date_ DATETIME, inspection_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, statement_ VARCHAR(255), store_or_not_ TINYINT(1) default 0, update_date_ DATETIME, verify_or_not_ TINYINT(1) default 0, case_people_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE case_inspect_collect_item (inspect_id_ INTEGER, collect_item_ VARCHAR(255));
CREATE TABLE case_record (id_ INTEGER NOT NULL, accept_date_ DATETIME, accept_police_id_ INTEGER, alarm_code_ VARCHAR(255), case_code_ VARCHAR(255), case_name_ VARCHAR(255), case_handle_ varchar(255), case_summary_ VARCHAR(255), case_type_ VARCHAR(255), close_date_ DATETIME, insert_date_ DATETIME, master_police_id_ INTEGER, note_ VARCHAR(255), occur_date_ DATETIME, operator_id_ INTEGER, register_date_ DATETIME, slave_police_id_ INTEGER, start_date_ DATETIME, suspect_ VARCHAR(255), update_date_ DATETIME, accept_unit_id_ INTEGER, master_unit_id_ INTEGER, slave_unit_id_ INTEGER, case_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE case_score (id_ INTEGER NOT NULL, insert_date_ DATETIME, note_ VARCHAR(255), operator_id_ INTEGER, total_score_ DECIMAL(38), update_date_ DATETIME, case_record_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE case_score_item (id_ INTEGER NOT NULL, insert_date_ DATETIME, note_ VARCHAR(255), operator_id_ INTEGER, score_ DECIMAL(38), update_date_ DATETIME, case_score_id_ INTEGER, eval_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE case_warning (id_ INTEGER NOT NULL, case_process_ VARCHAR(255), code_ VARCHAR(255), insert_date_ DATETIME, name_ VARCHAR(255), note_ VARCHAR(255), operator_id_ INTEGER, update_date_ DATETIME, case_record_id_ INTEGER, PRIMARY KEY (id_));

INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.BdmCabinet', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.BdmCamera', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.BdmClassification', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.BdmEvaluation', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.BdmPoliceStation', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.BdmPolice', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.BdmHandlingArea', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.BdmRoom', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.BdmStation', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.BdmStrap', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.BdmVideoCategory', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.Case', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.CaseMedia', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.CasePeople', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.CasePeopleBelongs', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.CasePeopleInspect', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.CaseRecord', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.CaseScore', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.CaseScoreItem', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.CaseWarning', 0);

--//@UNDO
-- SQL to undo the change goes here.


