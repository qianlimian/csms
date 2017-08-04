--// test
-- Migration SQL that makes the change goes here.

INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (3, '案件管理', 'MODULE', 'BUSINESS', '/caseRecords.htm', NULL, 3);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (4, '法律法规', 'MODULE', 'BUSINESS', '/laws.htm', NULL, 4);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (5, '律师管理', 'MODULE', 'BUSINESS', '/lawyers.htm', NULL, 5);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (6, '投诉管理', 'MODULE', 'BUSINESS', '/complaints.htm', NULL, 6);
INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (7, '统计分析', 'MODULE', 'BUSINESS', '/statics.htm', NULL, 7);

INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (31, '案件公开', 'LEAF', 'BUSINESS', '/caseRecords.htm', 3, 1);

INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (41, '法律法规', 'LEAF', 'BUSINESS', '/laws.htm', 4, 1);

INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (51, '律师维护', 'LEAF', 'BUSINESS', '/lawyers.htm', 5, 1);

INSERT INTO smart_menus (id_, name_, type_, plugin_, url_, parent_id_, display_order_) VALUES (61, '投诉管理', 'LEAF', 'BUSINESS', '/complaints.htm', 6, 1);
--//@UNDO
-- SQL to undo the change goes here.