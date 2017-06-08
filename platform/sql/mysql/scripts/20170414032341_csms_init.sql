--// csms_init
-- Migration SQL that makes the change goes here.

INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (3, '办案区管控', 'MODULE', 'BUSINESS', '/overviews.htm', NULL, 3);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (4, '案件管理', 'MODULE', 'BUSINESS', '/caseRecords.htm', NULL, 4);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (5, '案件监督', 'MODULE', 'BUSINESS', '/caseWarnings.htm', NULL, 5);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (6, '录音录像', 'MODULE', 'BUSINESS', '/caseMedias.htm', NULL, 6);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (7, '基础数据', 'MODULE', 'BUSINESS', '/bdmPoliceStations.htm', NULL, 7);

INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (31, '案件监控', 'LEAF', 'BUSINESS', '/overviews.htm', 3, 1);

INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (41, '案件办理', 'GROUP', 'BUSINESS', NULL, 4, 1);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (411, '现场调节', 'LEAF', 'BUSINESS', '/cases/dispute.htm', 41, 1);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (412, '行政案件', 'LEAF', 'BUSINESS', '/cases/civil.htm', 41, 2);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (413, '刑事案件', 'LEAF', 'BUSINESS', '/cases/criminal.htm', 41, 3);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (42, '办案记录', 'LEAF', 'BUSINESS', '/caseRecords.htm', 4, 2);

INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (51, '积分管理', 'GROUP', 'BUSINESS', NULL, 5, 1);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (511, '积分排名', 'LEAF', 'BUSINESS', '/caseScores/rank.htm', 51, 1);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (512, '我的积分', 'LEAF', 'BUSINESS', '/caseScores/detail.htm', 51, 2);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (513, '评价标准', 'LEAF', 'BUSINESS', '/bdmEvaluations.htm', 51, 3);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (52, '风险等级', 'LEAF', 'BUSINESS', '/bdmClassifications.htm', 5, 2);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (53, '受立案监督', 'LEAF', 'BUSINESS', '/caseWarnings.htm', 5, 3);

INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (61, '音视频列表', 'LEAF', 'BUSINESS', '/caseMedias.htm', 6, 1);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (62, '音视频类别', 'LEAF', 'BUSINESS', '/bdmVideoCategories.htm', 6, 2);

INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (71, '公安机关', 'LEAF', 'BUSINESS', '/bdmPoliceStations.htm', 7, 1);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (72, '办案区维护', 'LEAF', 'BUSINESS', '/bdmHandlingAreas.htm', 7, 2);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (73, '房间维护', 'LEAF', 'BUSINESS', '/bdmRooms.htm', 7, 3);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (74, '房间布局', 'LEAF', 'BUSINESS', '/bdmRoomLayout.htm', 7, 4);

--//@UNDO
-- SQL to undo the change goes here.


