set foreign_key_checks =0;

ALTER TABLE `users` 
MODIFY `ID_USER` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `company` 
MODIFY `ID_COMPANY` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `documents` 
MODIFY `ID_DOCUMENT` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `project` 
MODIFY `ID_PROJECT` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `groups` 
MODIFY `ID_GROUP` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `roles` 
MODIFY `ID_ROLE` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `task` 
MODIFY `ID_TASK` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `task_comment` 
MODIFY `ID_COMMENT` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `roles` 
MODIFY `ID_ROLE` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `sector` 
MODIFY `ID_SECTOR` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `trace` 
MODIFY `ID_TRACE` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `sys_dic_d` 
MODIFY `ID_DIC` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `province` 
MODIFY `ID_PROVINCE` INT(11) NOT NULL AUTO_INCREMENT;


set foreign_key_checks =1;
