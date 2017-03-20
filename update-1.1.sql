ALTER TABLE `project_student` ADD `degree_name` VARCHAR(20) NOT NULL COMMENT '学历名称' AFTER `duty_name`;

ALTER TABLE `students` ADD `degree_name` VARCHAR(20) NOT NULL COMMENT '学历名称' AFTER `duty_name`