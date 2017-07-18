--// init_tables
-- Migration SQL that makes the change goes here.

CREATE TABLE ID_Sequence (KEY_ID_ VARCHAR(150) NOT NULL, GEN_VALUE_ DECIMAL(38), PRIMARY KEY (KEY_ID_));

--//---------------------框架相关------------------------
CREATE TABLE smart_users (id_ INTEGER NOT NULL, name_ VARCHAR(50), login_name_ VARCHAR(50) UNIQUE, password_ VARCHAR(50), last_login_date_ DATETIME, operator_ VARCHAR(50), insert_date_ DATETIME, update_date_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE smart_groups (id_ INTEGER NOT NULL, name_ VARCHAR(50) UNIQUE, desc_ VARCHAR(128), operator_ VARCHAR(50), insert_date_ DATETIME, update_date_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE smart_roles (id_ INTEGER NOT NULL, name_ VARCHAR(50) UNIQUE, desc_ VARCHAR(128), operator_ VARCHAR(50), insert_date_ DATETIME, update_date_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE smart_menus (id_ INTEGER NOT NULL, code_ VARCHAR(127) NULL UNIQUE, name_ VARCHAR(100) NULL, type_ VARCHAR(50) NULL, plugin_ VARCHAR(50) NULL, url_ VARCHAR(256) NULL, parent_id_ INTEGER NULL, desc_ VARCHAR(128) NULL, display_order_ INTEGER NULL, PRIMARY KEY (id_));
CREATE TABLE smart_plugins (id_ INTEGER NOT NULL, code_ VARCHAR(127) NULL UNIQUE, name_ VARCHAR(50) NULL, desc_ VARCHAR(128) NULL, display_or_not_ TINYINT(1) default 0, insert_date_ DATETIME, update_date_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE smart_modules (id_ INTEGER NOT NULL, code_ VARCHAR(50), name_ VARCHAR(50), parent_id_ INTEGER NULL, operator_ VARCHAR(50), insert_date_ DATETIME, update_date_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE smart_operates (id_ INTEGER NOT NULL, code_ VARCHAR(50), name_ VARCHAR(50), module_id_ INTEGER NULL, operator_ VARCHAR(50), insert_date_ DATETIME, update_date_ DATETIME, PRIMARY KEY (id_));
CREATE TABLE smart_user_settings (id_ INTEGER NOT NULL, user_id_ INTEGER NULL, menu_position_ VARCHAR(50), page_width_ VARCHAR(50), insert_date_ DATETIME, update_date_ DATETIME, PRIMARY KEY (id_));

CREATE TABLE smart_user_group (group_id_ INTEGER NOT NULL, user_id_ INTEGER NOT NULL, PRIMARY KEY (group_id_, user_id_));
CREATE TABLE smart_group_role (group_id_ INTEGER NOT NULL, role_id_ INTEGER NOT NULL, PRIMARY KEY (group_id_, role_id_));
CREATE TABLE smart_group_menu (group_id_ INTEGER NOT NULL, menu_id_ INTEGER NOT NULL, PRIMARY KEY (group_id_, menu_id_));
CREATE TABLE smart_group_operate (group_id_ INTEGER NOT NULL, operate_id_ INTEGER NOT NULL, PRIMARY KEY (group_id_, operate_id_));

INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) VALUES ('org.smartframework.entity.User', 1000);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) VALUES ('org.smartframework.entity.Group', 1000);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) VALUES ('org.smartframework.entity.Role', 1000);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) VALUES ('org.smartframework.entity.Menu', 1000);
INSERT INTO ID_Sequence(KEY_ID_, GEN_VALUE_) VALUES ('org.smartframework.entity.Plugin', 1000);
INSERT INTO ID_Sequence (KEY_ID_, GEN_VALUE_) VALUES ('org.smartframework.entity.Module', 0);
INSERT INTO ID_Sequence (KEY_ID_, GEN_VALUE_) VALUES ('org.smartframework.entity.Operate', 0);
INSERT INTO ID_Sequence (KEY_ID_, GEN_VALUE_) VALUES ('org.smartframework.entity.UserSetting', 0);

--//------------------用户、组、角色--------------------
INSERT INTO smart_users (id_, login_name_, name_, password_, operator_, last_login_date_, insert_date_, update_date_) VALUES ('1', 'admin', '系统管理员', 'e10adc3949ba59abbe56e057f20f883e', NULL, '2016-10-08 17:06:41', '2016-09-29 13:40:45', '2016-09-29 13:40:45');
INSERT INTO smart_groups (id_, name_, desc_) VALUES (1, 'ADMIN_GROUP', '管理员组');
INSERT INTO smart_groups (id_, name_, desc_) VALUES (2, 'USER_GROUP', '普通用户组');
INSERT INTO smart_roles (id_, name_, desc_) VALUES (1, 'ROLE_ADMIN', '管理员');
INSERT INTO smart_roles (id_, name_, desc_) VALUES (2, 'ROLE_USER', '普通用户');

INSERT INTO smart_user_group (group_id_,user_id_) VALUES (1,1);

INSERT INTO smart_group_role (group_id_,role_id_) VALUES (1,1);
INSERT INTO smart_group_role (group_id_,role_id_) VALUES (1,2);

--//--------------------插件------------------------
INSERT INTO smart_plugins (id_, code_, name_, display_or_not_) VALUES (1, 'BUSINESS', '监管系统', FALSE);
INSERT INTO smart_plugins (id_, code_, name_, display_or_not_) VALUES (2, 'PLATFORM', '系统管理', TRUE);
INSERT INTO smart_plugins (id_, code_, name_, display_or_not_) VALUES (3, 'TUTORIAL', '参考文档', TRUE);

--//--------------------菜单------------------------
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (1, '权限管理', 'MODULE', 'PLATFORM', '/smart/users.htm', NULL, 999);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (11, '用户管理', 'LEAF', 'PLATFORM', '/smart/users.htm', 1, 1);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (12, '菜单管理', 'LEAF', 'PLATFORM', '/smart/menus.htm', 1, 2);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (13, '角色管理', 'LEAF', 'PLATFORM', '/smart/roles.htm', 1, 3);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (14, '用户组管理', 'LEAF', 'PLATFORM', '/smart/groups.htm', 1, 4);

INSERT INTO smart_group_menu (group_id_,menu_id_) VALUES (1,1);
INSERT INTO smart_group_menu (group_id_,menu_id_) VALUES (1,11);
INSERT INTO smart_group_menu (group_id_,menu_id_) VALUES (1,12);
INSERT INTO smart_group_menu (group_id_,menu_id_) VALUES (1,13);
INSERT INTO smart_group_menu (group_id_,menu_id_) VALUES (1,14);

--//---------------------示例相关------------------------
CREATE TABLE demo_student (id_ INTEGER NOT NULL, name_ VARCHAR(50), age_ INTEGER, birthday_ DATETIME, address_ VARCHAR(200), PRIMARY KEY (id_));
CREATE TABLE demo_teacher (id_ INTEGER NOT NULL, name_ VARCHAR(50), gender_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE demo_grade (id_ INTEGER NOT NULL, grade_name_ VARCHAR(50), teacher_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE demo_course (id_ INTEGER NOT NULL, course_name_ VARCHAR(50), grade_id_ INTEGER, teacher_id_ INTEGER, PRIMARY KEY (id_));
CREATE TABLE demo_score (id_ INTEGER NOT NULL, course_id_ INTEGER, student_id_ INTEGER, mark_ DECIMAL, PRIMARY KEY (id_));

INSERT INTO ID_Sequence (KEY_ID_, GEN_VALUE_) VALUES ('com.bycc.demo.entity.Student', '1000');
INSERT INTO ID_Sequence (KEY_ID_, GEN_VALUE_) VALUES ('com.bycc.demo.entity.Teacher', '1000');
INSERT INTO ID_Sequence (KEY_ID_, GEN_VALUE_) VALUES ('com.bycc.demo.entity.Grade', '1000');
INSERT INTO ID_Sequence (KEY_ID_, GEN_VALUE_) VALUES ('com.bycc.demo.entity.Course', '1000');
INSERT INTO ID_Sequence (KEY_ID_, GEN_VALUE_) VALUES ('com.bycc.demo.entity.Score', '1000');

INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (2, '示例', 'MODULE', 'TUTORIAL', '/smart/students.htm', NULL, 1000);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (21, '学生', 'LEAF', 'TUTORIAL', '/smart/students.htm', 2, 1);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (22, '教师', 'LEAF', 'TUTORIAL', '/smart/teachers.htm', 2, 2);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (23, '班级', 'LEAF', 'TUTORIAL', '/smart/grades.htm', 2, 3);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (24, '课程', 'LEAF', 'TUTORIAL', '/smart/courses.htm', 2, 4);

INSERT INTO smart_group_menu (group_id_, menu_id_) VALUES (1, 2);
INSERT INTO smart_group_menu (group_id_, menu_id_) VALUES (1, 21);
INSERT INTO smart_group_menu (group_id_, menu_id_) VALUES (1, 22);
INSERT INTO smart_group_menu (group_id_, menu_id_) VALUES (1, 23);
INSERT INTO smart_group_menu (group_id_, menu_id_) VALUES (1, 24);
--//菜单
--//@UNDO
-- SQL to undo the change goes here.





