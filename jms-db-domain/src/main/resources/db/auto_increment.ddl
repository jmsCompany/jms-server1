set foreign_key_checks =0;



ALTER TABLE `APPLICATION` 
MODIFY `ID_APPLICATION` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `APPROVAL_PHASE` 
MODIFY `ID_APPROVAL_PHASE` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `APPROVAL_PROCESS` 
MODIFY `ID_APPROVAL_PROCESS` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `APPROVAL_TYPE` 
MODIFY `ID_APPROVAL_TYPE` INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `APPS` 
MODIFY `ID_APP` INT(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `BUY_RECORD` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `COMPANY` 
MODIFY `ID_COMPANY` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `DOCUMENT` 
MODIFY `ID_DOCUMENT` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `FEEDBACK` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `GROUP_TYPE` 
MODIFY `ID_GROUP_TYPE` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `GROUPS` 
MODIFY `ID_GROUP` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `ISSUE` 
MODIFY `ID_ISSUE` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `ISSUE_COMMENT` 
MODIFY `ID_ISSUE_COMMENT` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `JMS_EVENT` 
MODIFY `ID_EVENT` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `NOTIFICATION` 
MODIFY `ID_NOTI` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `NOTI_METHOD` 
MODIFY `ID_NOTI_METHOD` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `RECEIVER` 
MODIFY `ID_RECEIVER` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `POLL` 
MODIFY `ID_POLL` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `POLL_ITEMS` 
MODIFY `ID_ITEM` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `PROJECT` 
MODIFY `ID_PROJECT` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `RECEIPT` 
MODIFY `ID_RECEIPT` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `REPEATS` 
MODIFY `ID_REPEAT` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `ROLES` 
MODIFY `ID_ROLE` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `SCHEDULE` 
MODIFY `ID_SCHEDULE` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `SERVICE` 
MODIFY `ID_SERVICE` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `SERVICE_TYPE` 
MODIFY `ID_SERVICE_TYPE` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `SYS_DIC_D` 
MODIFY `ID_DIC` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `TASK` 
MODIFY `ID_TASK` INT(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `TASK_COMMENT` 
MODIFY `ID_COMMENT` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `USERS` 
MODIFY `ID_USER` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_ATTACHMENT` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_COMPANY_CO` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_COMPANY_CO_ATTACHMENT` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_COUNTRY_DIC` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_DO` 
MODIFY `ID_DO` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_DO_MATERIAL` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_GENDER_DIC` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_INCOME` 
MODIFY `ID_R` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_INCOME_MATERIAL` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_INVENTORY` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_LEVEL_DIC` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_LINKMAN` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_MATERIAL` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_MATERIAL_ATTACHMENT` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_MATERIAL_BIN` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_MATERIAL_CATEGORY` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_MATERIAL_CATEGORY_PIC` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_MATERIAL_PIC` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_MATERIAL_TYPE_PIC` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_MTF` 
MODIFY `ID_MT` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_MTF_MATERIAL` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_PIC` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_PIC_SETTING` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_PO` 
MODIFY `ID_PO` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_PO_MATERIAL` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_SO` 
MODIFY `ID_SO` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_SO_MATERIAL` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_STATUS_DIC` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_STK` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_TERM_DIC` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_TYPE_DIC` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `S_UNIT_DIC` 
MODIFY `ID` INT(11) NOT NULL AUTO_INCREMENT;


set foreign_key_checks =1;
