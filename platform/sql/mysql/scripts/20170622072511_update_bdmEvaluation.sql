--// update_bdmEvaluation
-- Migration SQL that makes the change goes here.

drop table bdm_evaluation;
CREATE TABLE `bdm_evaluation` (
  `id_` int(11) NOT NULL,
  `insert_date_` datetime DEFAULT NULL,
  `note_` varchar(255) DEFAULT NULL,
  `operator_id_` int(11) DEFAULT NULL,
  `score_` decimal(38,0) DEFAULT NULL,
  `standard_` varchar(255) DEFAULT NULL,
  `update_date_` datetime DEFAULT NULL,
  `parent_` int(11) DEFAULT NULL,
  `displayOrder_` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--//@UNDO
-- SQL to undo the change goes here.


