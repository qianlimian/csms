--// bycc_init
-- Migration SQL that makes the change goes here

CREATE TABLE case_record (id_ INTEGER NOT NULL, accept_date_ DATETIME, case_code_ VARCHAR(255), case_handle_ VARCHAR(255), case_name_ VARCHAR(255), case_status_ VARCHAR(255), case_summary_ VARCHAR(255), case_type_ VARCHAR(255), open_ VARCHAR(255), close_date_ DATETIME, insert_date_ DATETIME, master_police_name_ VARCHAR(255), master_unit_name_ VARCHAR(255), note_ VARCHAR(255), register_date_ DATETIME, slave_police_name_ VARCHAR(255), slave_unit_name_ VARCHAR(255), start_date_ DATETIME, suspect_ VARCHAR(255), update_date_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE case_people (id_ INTEGER NOT NULL, address_ VARCHAR(255), birthday_ DATETIME, certificate_num_ VARCHAR(255), certificate_type_ VARCHAR(255), gender_ VARCHAR(255), insert_date_ DATETIME, name_ VARCHAR(255), people_type_ VARCHAR(255), tel_num_ VARCHAR(255), update_date_ DATETIME, case_record_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE complaint (id_ INTEGER NOT NULL, content_ VARCHAR(255), insertDate_ DATETIME, result_ VARCHAR(255), status_ VARCHAR(255), title_ VARCHAR(100), type_ VARCHAR(255), updateDate_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE complainant (id_ INTEGER NOT NULL, email_ VARCHAR(255), name_ VARCHAR(255), tel_ VARCHAR(255), complaint_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE law (id_ INTEGER NOT NULL, chapter_ VARCHAR(255), chapter_name_ VARCHAR(255), content_ VARCHAR(255), insertDate_ DATETIME, num_ VARCHAR(255), section_ VARCHAR(255), section_name_ VARCHAR(255), type_ VARCHAR(255), updateDate_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE lawyer (id_ INTEGER NOT NULL, domain_ VARCHAR(255), insertDate_ DATETIME, lawyerOffice_ VARCHAR(255), name_ VARCHAR(255), operatorId_ INTEGER, registerDate_ DATETIME, registrationNum_ VARCHAR(255), status_ VARCHAR(255), updateDate_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE case_record_law (case_record_id_ INTEGER NOT NULL, law_id_ INTEGER NOT NULL, PRIMARY KEY (case_record_id_, law_id_));
CREATE TABLE case_record_lawyer (case_record_id_ INTEGER NOT NULL, lawyer_id_ INTEGER NOT NULL, PRIMARY KEY (case_record_id_, lawyer_id_));

INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.CaseRecord', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.CasePeople', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.Complaint', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.Complainant', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.Law', 0);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) values ('com.bycc.entity.Lawyer', 0);

--//@UNDO
-- SQL to undo the change goes here.


