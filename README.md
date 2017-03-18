# jms-server
JMS web 服务器端




ALTER TABLE `jms5`.`s_company_co` 
ADD COLUMN `id_company1` INT(11) NULL COMMENT '合作公司ID' AFTER `audit_by`,
ADD COLUMN `audit_status` INT(11) NULL COMMENT '合作公司审计状态' AFTER `id_company1`;
ALTER TABLE `jms5`.`s_company_co` 
ADD COLUMN `email` VARCHAR(128) NULL AFTER `audit_status`;



CREATE TABLE s_com_com
(
    id                    INTEGER NOT NULL,
    id_company1           INTEGER NULL,
    company1              VARCHAR(128) NULL,
    id_company2           INTEGER NULL,
    company2              VARCHAR(128) NULL,
    type                  INTEGER NULL,
    linkman1              VARCHAR(128) NULL,
    linkman2              VARCHAR(128) NULL,
    email1                VARCHAR(128) NULL,
    email2                VARCHAR(128) NULL,
    status                INTEGER NULL,
    pno                   VARCHAR(128) NULL,
    rev                   VARCHAR(128) NULL,
    des                   VARCHAR(256) NULL
)
;



ALTER TABLE s_com_com
    ADD  PRIMARY KEY (id)
;

set foreign_key_checks =0;
alter table `s_com_com` 
modify `id` int(11) not null auto_increment;
set foreign_key_checks =1;


CREATE TABLE s_mtf_c
(
    id                    INTEGER NOT NULL,
    mt_no                 VARCHAR(64) NULL,
    id_company1           INTEGER NULL,
    id_company2           INTEGER NULL,
    pno                   VARCHAR(64) NULL,
    lot_no                VARCHAR(64) NULL,
    material_name         VARCHAR(128) NULL,
    creation_date         DATE NULL,
    audit_date            DATE NULL,
    qty                   INTEGER NULL,
    type                  INTEGER NULL,
    status                INTEGER NULL,
    id_smtf1              INTEGER NULL,
    id_smtf2              INTEGER NULL
)
;



ALTER TABLE s_mtf_c
    ADD  PRIMARY KEY (id)
;
 
 
set foreign_key_checks =0;
alter table `s_mtf_c` 
modify `id` int(11) not null auto_increment;
set foreign_key_checks =1;

INSERT INTO `jms5`.`s_mtf_type_dic` (`id_mtf_type`, `name`, `name_en`) VALUES ('9', '公司流转', 'C2C');




ALTER TABLE `jms5`.`s_mtf` 
ADD COLUMN `id_smtf_c` INT(11) NULL AFTER `id_wo`;

ALTER TABLE `jms5`.`s_mtf_c` 
ADD COLUMN `company1` VARCHAR(128) NULL AFTER `p_no`,
ADD COLUMN `company2` VARCHAR(128) NULL AFTER `company1`;


ALTER TABLE `jms5`.`s_po` 
ADD COLUMN `id_company1` INT(11) NULL AFTER `id_currency_type`,
ADD COLUMN `id_so` INT(11) NULL AFTER `id_company1`,
ADD COLUMN `so_no` VARCHAR(64) NULL AFTER `id_so`;


ALTER TABLE `jms5`.`event_receiver` 
ADD COLUMN `email` VARCHAR(256) NULL AFTER `delay`;


CREATE TABLE s_mt
(
    id                    INTEGER NOT NULL,
    mt_no                 VARCHAR(64) NULL,
    id_company1           INTEGER NULL,
    id_company2           INTEGER NULL,
    company2               VARCHAR(256) NULL,
    id_company3           INTEGER NULL,
	company3               VARCHAR(256) NULL,
    from_stk               INTEGER NULL,
    creation_date         DATE NULL,
    audit_date            DATE NULL,
    p_no                   VARCHAR(256) NULL,
     p_id                 INTEGER NULL,
    status                INTEGER NULL
)
;


ALTER TABLE s_mt
    ADD  PRIMARY KEY (id)
;
 
 
set foreign_key_checks =0;
alter table `s_mt` 
modify `id` int(11) not null auto_increment;
set foreign_key_checks =1;
