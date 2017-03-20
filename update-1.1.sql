ALTER TABLE `project_student` ADD `degree_name` VARCHAR(20) NOT NULL COMMENT '学历名称' AFTER `duty_name`;

ALTER TABLE `students` ADD `degree_name` VARCHAR(20) NOT NULL COMMENT '学历名称' AFTER `duty_name`


ALTER TABLE `students` ADD `edu_name` VARCHAR(20) NOT NULL COMMENT '学历名称' AFTER `degree_id`;


ALTER TABLE `project_student` ADD `edu_name` VARCHAR(20) NOT NULL COMMENT '学历名称' AFTER `degree_id`;


ALTER TABLE `projects` ADD `status_attach` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT ' 0 = 保存 1 = 提交 2= 退回' AFTER `update_time`, ADD `status_course` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT ' 0 = 保存 1 = 提交 2= 退回' AFTER `status_attach`, ADD `status_student` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT ' 0 = 保存 1 = 提交 2= 退回' AFTER `status_course`;


update `projects` set status_attach = 1 WHERE p_id in (select p_id from project_attach)