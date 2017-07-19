--// view_score_statistics
-- Migration SQL that makes the change goes here.

create or replace view view_case_score as 
select 
cs.case_id_, 
cs.total_score_, 
c.case_code_, 
c.case_name_, 
c.master_unit_id_,
m_unit.name_ as master_unit_name_,
m_unit.area_type_ as master_unit_area_type_,
c.master_police_id_,
m_user.name_ as master_police_name_, 
c.slave_unit_id_,
s_unit.name_ as slave_unit_name_, 
s_unit.area_type_ as slave_unit_area_type_, 
c.slave_police_id_,
s_user.name_ as slave_police_name_
from case_score cs
join caze c on c.id_ = cs.case_id_
join bdm_police_station m_unit on m_unit.id_ = c.master_unit_id_
join bdm_police_station s_unit on s_unit.id_ = c.slave_unit_id_
join bdm_police m_p on m_p.id_ = c.master_police_id_
join bdm_police s_p on s_p.id_ = c.slave_police_id_
join smart_users m_user on m_user.id_ = m_p.user_id_
join smart_users s_user on s_user.id_ = s_p.user_id_;

create or replace view view_police_score_statistics as 
select 
case_id_,
master_police_id_ as police_id_, 
master_police_name_ as police_name_, 
master_unit_name_ as police_station_name_,
total_score_ 
from view_case_score
union
select
case_id_,
slave_police_id_ as police_id_, 
slave_police_name_ as police_name_, 
slave_unit_name_ as police_station_name,
total_score_ 
from view_case_score;

create or replace view view_police_station_score_statistics as 
select 
case_id_,
master_unit_id_ as unit_id_,
master_unit_name_ as unit_name_, 
master_unit_area_type_ as unit_area_type_,
total_score_ 
from view_case_score
union
select
case_id_,
slave_unit_id_ as unit_id_, 
slave_unit_name_ as unit_name_, 
slave_unit_area_type_ as unit_area_type_,
total_score_ 
from view_case_score;

--//@UNDO
-- SQL to undo the change goes here.


