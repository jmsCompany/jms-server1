
    create table REVINFO (
        REV integer not null auto_increment,
        REVTSTMP bigint,
        primary key (REV)
    );

    create table jms.acl_class (
        id bigint not null auto_increment,
        class varchar(100) not null,
        primary key (id)
    );

    create table jms.acl_entry (
        id bigint not null auto_increment,
        ace_order bigint not null,
        audit_failure bit not null,
        audit_success bit not null,
        granting bit not null,
        mask bigint not null,
        acl_object_identity bigint not null,
        sid bigint not null,
        primary key (id)
    );

    create table jms.acl_object_identity (
        id bigint not null auto_increment,
        entries_inheriting bit not null,
        object_id_identity bigint not null,
        object_id_class bigint not null,
        parent_object bigint,
        owner_sid bigint,
        primary key (id)
    );

    create table jms.acl_sid (
        id bigint not null auto_increment,
        principal bit not null,
        sid varchar(100) not null,
        primary key (id)
    );

    create table jms.authorities (
        ID_ROLE bigint not null,
        ID_USER bigint not null,
        primary key (ID_ROLE, ID_USER)
    );

    create table jms.authorities_AUD (
        REV integer not null,
        ID_ROLE bigint not null,
        ID_USER bigint not null,
        REVTYPE tinyint,
        primary key (REV, ID_ROLE, ID_USER)
    );

    create table jms.city (
        ID_CITY bigint not null,
        CITY varchar(32),
        POSTCODE varchar(20),
        ID_PROVINCE bigint,
        primary key (ID_CITY)
    );

    create table jms.city_AUD (
        ID_CITY bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        CITY varchar(32),
        POSTCODE varchar(20),
        ID_PROVINCE bigint,
        primary key (ID_CITY, REV)
    );

    create table jms.company (
        ID_COMPANY bigint not null auto_increment,
        ADDRESS varchar(128),
        COMPANY_NAME varchar(64) not null,
        CREATION_TIME date not null,
        DESCRIPTION varchar(1024),
        EMAIL varchar(64),
        ENABLED bigint not null,
        ESTABLISH_PERSON varchar(20),
        ESTABLISHMENT_TIME date,
        FAX varchar(20),
        MSG_AVAILABLE_NUM bigint,
        MSG_USED_NUM bigint,
        POSTCODE varchar(20),
        SPACE float,
        TELEPHONE varchar(20),
        URL varchar(128),
        USED_SPACE float,
        VERIFIED bigint,
        ID_DISTRICT bigint,
        LICENSE bigint,
        LOGO bigint,
        COMPANY_CATORGORY bigint,
        Company_Nature bigint,
        COMPANY_SIZE bigint,
        COMPANY_TYPE bigint,
        LOCALE bigint,
        SCHEME bigint,
        TASK_TYPE bigint,
        CREATOR bigint not null,
        primary key (ID_COMPANY)
    );

    create table jms.company_AUD (
        ID_COMPANY bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        ADDRESS varchar(128),
        COMPANY_NAME varchar(64),
        CREATION_TIME date,
        DESCRIPTION varchar(1024),
        EMAIL varchar(64),
        ENABLED bigint,
        ESTABLISH_PERSON varchar(20),
        ESTABLISHMENT_TIME date,
        FAX varchar(20),
        MSG_AVAILABLE_NUM bigint,
        MSG_USED_NUM bigint,
        POSTCODE varchar(20),
        SPACE float,
        TELEPHONE varchar(20),
        URL varchar(128),
        USED_SPACE float,
        VERIFIED bigint,
        ID_DISTRICT bigint,
        LICENSE bigint,
        LOGO bigint,
        COMPANY_CATORGORY bigint,
        Company_Nature bigint,
        COMPANY_SIZE bigint,
        COMPANY_TYPE bigint,
        LOCALE bigint,
        SCHEME bigint,
        TASK_TYPE bigint,
        CREATOR bigint,
        primary key (ID_COMPANY, REV)
    );

    create table jms.district (
        ID_DISTRICT bigint not null,
        DISTRICT varchar(32),
        ID_CITY bigint,
        primary key (ID_DISTRICT)
    );

    create table jms.district_AUD (
        ID_DISTRICT bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        DISTRICT varchar(32),
        ID_CITY bigint,
        primary key (ID_DISTRICT, REV)
    );

    create table jms.document (
        ID_DOCUMENT bigint not null auto_increment,
        DESCRIPTION varchar(256),
        FILE_NAME varchar(256),
        NAME varchar(64),
        RELATIVE_PATH varchar(256),
        SIZE bigint,
        PARENT bigint,
        primary key (ID_DOCUMENT)
    );

    create table jms.document_AUD (
        ID_DOCUMENT bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        DESCRIPTION varchar(256),
        FILE_NAME varchar(256),
        NAME varchar(64),
        RELATIVE_PATH varchar(256),
        SIZE bigint,
        PARENT bigint,
        primary key (ID_DOCUMENT, REV)
    );

    create table jms.group_authorities (
        ID_GROUP bigint not null,
        ID_ROLE bigint not null,
        primary key (ID_GROUP, ID_ROLE)
    );

    create table jms.group_authorities_AUD (
        REV integer not null,
        ID_GROUP bigint not null,
        ID_ROLE bigint not null,
        REVTYPE tinyint,
        primary key (REV, ID_GROUP, ID_ROLE)
    );

    create table jms.group_members (
        ID_GROUP bigint not null,
        ID_USER bigint not null,
        ID_ROLE bigint,
        primary key (ID_GROUP, ID_USER)
    );

    create table jms.group_members_AUD (
        ID_GROUP bigint not null,
        ID_USER bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        ID_ROLE bigint,
        primary key (ID_GROUP, ID_USER, REV)
    );

    create table jms.groups (
        ID_GROUP bigint not null auto_increment,
        CREATION_TIME date,
        DESCRIPTION varchar(256),
        GROUP_NAME varchar(64) not null,
        CREATOR bigint,
        primary key (ID_GROUP)
    );

    create table jms.groups_AUD (
        ID_GROUP bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        CREATION_TIME date,
        DESCRIPTION varchar(256),
        GROUP_NAME varchar(64),
        CREATOR bigint,
        primary key (ID_GROUP, REV)
    );

    create table jms.issue (
        ID_ISSUE bigint not null auto_increment,
        DESCRIPTION varchar(256),
        NAME varchar(64),
        ID_PROJECT bigint,
        PRIORITY bigint,
        STATUS bigint,
        primary key (ID_ISSUE)
    );

    create table jms.issue_AUD (
        ID_ISSUE bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        DESCRIPTION varchar(256),
        NAME varchar(64),
        ID_PROJECT bigint,
        PRIORITY bigint,
        STATUS bigint,
        primary key (ID_ISSUE, REV)
    );

    create table jms.issue_comment (
        ID_ISSUE_COMMENT bigint not null auto_increment,
        COMMENT varchar(1024),
        CREATION_TIME date,
        MODIFICATION_TIME date,
        ID_ISSUE bigint,
        PARENT bigint,
        primary key (ID_ISSUE_COMMENT)
    );

    create table jms.issue_comment_AUD (
        ID_ISSUE_COMMENT bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        COMMENT varchar(1024),
        CREATION_TIME date,
        MODIFICATION_TIME date,
        ID_ISSUE bigint,
        PARENT bigint,
        primary key (ID_ISSUE_COMMENT, REV)
    );

    create table jms.issue_doc (
        ID_DOCUMENT bigint not null,
        ID_ISSUE bigint not null,
        PUBLISH bigint,
        primary key (ID_DOCUMENT, ID_ISSUE)
    );

    create table jms.issue_doc_AUD (
        ID_DOCUMENT bigint not null,
        ID_ISSUE bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        PUBLISH bigint,
        primary key (ID_DOCUMENT, ID_ISSUE, REV)
    );

    create table jms.module (
        ID_MODULE bigint not null auto_increment,
        DESCRIPTION varchar(128),
        NAME varchar(64),
        PARENT bigint,
        primary key (ID_MODULE)
    );

    create table jms.module_AUD (
        ID_MODULE bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        DESCRIPTION varchar(128),
        NAME varchar(64),
        PARENT bigint,
        primary key (ID_MODULE, REV)
    );

    create table jms.project (
        ID_PROJECT bigint not null auto_increment,
        DESCRIPTION varchar(256),
        ENABLED bigint not null,
        END_TIME date,
        PLAN_END_TIME date,
        PLAN_START_TIME date,
        PROCESS bigint,
        PROJECT_NAME varchar(64) not null,
        PROJECT_NUMBER varchar(64),
        START_TIME date,
        ID_COMPANY bigint not null,
        PARENT bigint,
        PRIORITY bigint,
        LEADER bigint,
        primary key (ID_PROJECT)
    );

    create table jms.project_AUD (
        ID_PROJECT bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        DESCRIPTION varchar(256),
        ENABLED bigint,
        END_TIME date,
        PLAN_END_TIME date,
        PLAN_START_TIME date,
        PROCESS bigint,
        PROJECT_NAME varchar(64),
        PROJECT_NUMBER varchar(64),
        START_TIME date,
        ID_COMPANY bigint,
        PARENT bigint,
        PRIORITY bigint,
        LEADER bigint,
        primary key (ID_PROJECT, REV)
    );

    create table jms.project_doc (
        ID_DOCUMENT bigint not null,
        ID_PROJECT bigint not null,
        PUBLISH bigint,
        primary key (ID_DOCUMENT, ID_PROJECT)
    );

    create table jms.project_doc_AUD (
        ID_DOCUMENT bigint not null,
        ID_PROJECT bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        PUBLISH bigint,
        primary key (ID_DOCUMENT, ID_PROJECT, REV)
    );

    create table jms.project_participant (
        ID_PROJECT bigint not null,
        ID_USER bigint not null,
        IS_CHARGE bigint,
        primary key (ID_PROJECT, ID_USER)
    );

    create table jms.project_participant_AUD (
        ID_PROJECT bigint not null,
        ID_USER bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        IS_CHARGE bigint,
        primary key (ID_PROJECT, ID_USER, REV)
    );

    create table jms.province (
        ID_PROVINCE bigint not null,
        PROVINCE varchar(32),
        primary key (ID_PROVINCE)
    );

    create table jms.province_AUD (
        ID_PROVINCE bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        PROVINCE varchar(32),
        primary key (ID_PROVINCE, REV)
    );

    create table jms.role_priv (
        ID_MODULE bigint not null,
        ID_ROLE bigint not null,
        PRIV bigint,
        primary key (ID_MODULE, ID_ROLE)
    );

    create table jms.role_priv_AUD (
        ID_MODULE bigint not null,
        ID_ROLE bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        PRIV bigint,
        primary key (ID_MODULE, ID_ROLE, REV)
    );

    create table jms.roles (
        ID_ROLE bigint not null auto_increment,
        DESCRIPTION varchar(256),
        ENABLED bigint,
        LEVEL bigint,
        ROLE varchar(64) not null,
        ID_COMPANY bigint not null,
        PARENT bigint,
        ID_SECTOR bigint,
        primary key (ID_ROLE)
    );

    create table jms.roles_AUD (
        ID_ROLE bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        DESCRIPTION varchar(256),
        ENABLED bigint,
        LEVEL bigint,
        ROLE varchar(64),
        ID_COMPANY bigint,
        PARENT bigint,
        ID_SECTOR bigint,
        primary key (ID_ROLE, REV)
    );

    create table jms.sector (
        ID_SECTOR bigint not null auto_increment,
        Description varchar(256),
        ENABLED bigint,
        SECTOR varchar(64),
        SEQ bigint,
        ID_COMPANY bigint not null,
        primary key (ID_SECTOR)
    );

    create table jms.sector_AUD (
        ID_SECTOR bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        Description varchar(256),
        ENABLED bigint,
        SECTOR varchar(64),
        SEQ bigint,
        ID_COMPANY bigint,
        primary key (ID_SECTOR, REV)
    );

    create table jms.sector_member (
        ID_SECTOR bigint not null,
        ID_USER bigint not null,
        ISPRIMARY bigint,
        ID_ROLE bigint,
        primary key (ID_SECTOR, ID_USER)
    );

    create table jms.sector_member_AUD (
        ID_SECTOR bigint not null,
        ID_USER bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        ISPRIMARY bigint,
        ID_ROLE bigint,
        primary key (ID_SECTOR, ID_USER, REV)
    );

    create table jms.sys_dic (
        TYPE varchar(64) not null,
        DESCRIPTION varchar(128) not null,
        primary key (TYPE)
    );

    create table jms.sys_dic_AUD (
        TYPE varchar(64) not null,
        REV integer not null,
        REVTYPE tinyint,
        DESCRIPTION varchar(128),
        primary key (TYPE, REV)
    );

    create table jms.sys_dic_d (
        ID_DIC bigint not null auto_increment,
        DESCRIPTION varchar(128) not null,
        ENABLED bigint not null,
        IS_DEFAULT bigint,
        NAME varchar(64) not null,
        TYPE varchar(64),
        primary key (ID_DIC)
    );

    create table jms.sys_dic_d_AUD (
        ID_DIC bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        DESCRIPTION varchar(128),
        ENABLED bigint,
        IS_DEFAULT bigint,
        NAME varchar(64),
        TYPE varchar(64),
        primary key (ID_DIC, REV)
    );

    create table jms.task (
        ID_TASK bigint not null auto_increment,
        CREATION_TIME date,
        DESCRIPTION varchar(1024),
        DURATION bigint,
        END_TIME date,
        MODIFICATION_TIME date,
        NAME varchar(64) not null,
        PLAN_DURATION bigint,
        PLAN_END_TIME date not null,
        PLAN_START_TIME date not null,
        SEQ bigint,
        START_TIME date,
        ID_PROJECT bigint,
        PRIORITY bigint,
        STATUS bigint,
        ASSIGNEE bigint,
        CREATOR bigint,
        primary key (ID_TASK)
    );

    create table jms.task_AUD (
        ID_TASK bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        CREATION_TIME date,
        DESCRIPTION varchar(1024),
        DURATION bigint,
        END_TIME date,
        MODIFICATION_TIME date,
        NAME varchar(64),
        PLAN_DURATION bigint,
        PLAN_END_TIME date,
        PLAN_START_TIME date,
        SEQ bigint,
        START_TIME date,
        ID_PROJECT bigint,
        PRIORITY bigint,
        STATUS bigint,
        ASSIGNEE bigint,
        CREATOR bigint,
        primary key (ID_TASK, REV)
    );

    create table jms.task_comment (
        ID_COMMENT bigint not null auto_increment,
        COMMENT varchar(1024),
        CREATION_TIME date,
        MODIFICATION_TIME date,
        ID_TASK bigint,
        PARENT bigint,
        ID_USER bigint,
        primary key (ID_COMMENT)
    );

    create table jms.task_comment_AUD (
        ID_COMMENT bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        COMMENT varchar(1024),
        CREATION_TIME date,
        MODIFICATION_TIME date,
        ID_TASK bigint,
        PARENT bigint,
        ID_USER bigint,
        primary key (ID_COMMENT, REV)
    );

    create table jms.task_doc (
        ID_DOCUMENT bigint not null,
        ID_TASK bigint not null,
        PUBLISH bigint,
        primary key (ID_DOCUMENT, ID_TASK)
    );

    create table jms.task_doc_AUD (
        ID_DOCUMENT bigint not null,
        ID_TASK bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        PUBLISH bigint,
        primary key (ID_DOCUMENT, ID_TASK, REV)
    );

    create table jms.trace (
        ID_TRACE bigint not null auto_increment,
        ACTION_TYPE varchar(20) not null,
        GPS varchar(128),
        IP varchar(64),
        TRACE_TIME date not null,
        ID_USER bigint not null,
        primary key (ID_TRACE)
    );

    create table jms.trace_AUD (
        ID_TRACE bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        ACTION_TYPE varchar(20),
        GPS varchar(128),
        IP varchar(64),
        TRACE_TIME date,
        ID_USER bigint,
        primary key (ID_TRACE, REV)
    );

    create table jms.users (
        ID_USER bigint not null auto_increment,
        E_NO varchar(20),
        ADDRESS varchar(128),
        BIRTHDAY date,
        CREATION_TIME date,
        DEGREE varchar(64),
        DESCRIPTION varchar(256),
        EMAIL varchar(64),
        EMERGENCY_HP varchar(64),
        ENABLED bigint not null,
        EXT varchar(20),
        GRAD_TIME date,
        IDCARD varchar(18),
        LAST_LOGIN datetime,
        MAJOR varchar(64),
        MOBILE varchar(64),
        NAME varchar(64),
        PASSWORD varchar(64) not null,
        SCHOOL varchar(128),
        TOKEN varchar(128),
        USERNAME varchar(64),
        ID_COMPANY bigint,
        ID_DISTRICT bigint,
        CV bigint,
        GENDER bigint,
        LOCALE bigint,
        SCHEME bigint,
        STATUS bigint,
        CREATOR bigint,
        primary key (ID_USER)
    );

    create table jms.users_AUD (
        ID_USER bigint not null,
        REV integer not null,
        REVTYPE tinyint,
        E_NO varchar(20),
        ADDRESS varchar(128),
        BIRTHDAY date,
        CREATION_TIME date,
        DEGREE varchar(64),
        DESCRIPTION varchar(256),
        EMAIL varchar(64),
        EMERGENCY_HP varchar(64),
        ENABLED bigint,
        EXT varchar(20),
        GRAD_TIME date,
        IDCARD varchar(18),
        LAST_LOGIN datetime,
        MAJOR varchar(64),
        MOBILE varchar(64),
        NAME varchar(64),
        PASSWORD varchar(64),
        SCHOOL varchar(128),
        TOKEN varchar(128),
        USERNAME varchar(64),
        ID_COMPANY bigint,
        ID_DISTRICT bigint,
        CV bigint,
        GENDER bigint,
        LOCALE bigint,
        SCHEME bigint,
        STATUS bigint,
        CREATOR bigint,
        primary key (ID_USER, REV)
    );

    alter table jms.acl_class 
        add constraint UK_iy7ua5fso3il3u3ymoc4uf35w  unique (class);

    alter table jms.acl_entry 
        add constraint UK_gh5egfpe4gaqokya6s0567b0l  unique (acl_object_identity, ace_order);

    alter table jms.acl_object_identity 
        add constraint UK_ehrgv7ffpt0jenafv0o1bu5tm  unique (object_id_class, object_id_identity);

    alter table jms.acl_sid 
        add constraint UK_meabypi3cnm8604op6qvd517v  unique (sid, principal);

    alter table jms.acl_entry 
        add constraint FK_fhuoesmjef3mrv0gpja4shvcr 
        foreign key (acl_object_identity) 
        references jms.acl_object_identity (id);

    alter table jms.acl_entry 
        add constraint FK_i6xyfccd4y3wlwhgwpo4a9rm1 
        foreign key (sid) 
        references jms.acl_sid (id);

    alter table jms.acl_object_identity 
        add constraint FK_6c3ugmk053uy27bk2sred31lf 
        foreign key (object_id_class) 
        references jms.acl_class (id);

    alter table jms.acl_object_identity 
        add constraint FK_6oap2k8q5bl33yq3yffrwedhf 
        foreign key (parent_object) 
        references jms.acl_object_identity (id);

    alter table jms.acl_object_identity 
        add constraint FK_nxv5we2ion9fwedbkge7syoc3 
        foreign key (owner_sid) 
        references jms.acl_sid (id);

    alter table jms.authorities 
        add constraint FK_s8jjakle3t9nfhg1veoh6sadu 
        foreign key (ID_USER) 
        references jms.users (ID_USER);

    alter table jms.authorities 
        add constraint FK_1or7fw31mi2aor6uvcbmqisp0 
        foreign key (ID_ROLE) 
        references jms.roles (ID_ROLE);

    alter table jms.authorities_AUD 
        add constraint FK_79yw8u32sgsk0ba1cbmr4f82g 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.city 
        add constraint FK_e3gs5k6qmcs3mmisuoxtw5wr4 
        foreign key (ID_PROVINCE) 
        references jms.province (ID_PROVINCE);

    alter table jms.city_AUD 
        add constraint FK_8n8rbif8pa5ooxg4c6bnelpav 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.company 
        add constraint FK_bogy3wqdc2n55sgmwbkr1ifxh 
        foreign key (ID_DISTRICT) 
        references jms.district (ID_DISTRICT);

    alter table jms.company 
        add constraint FK_c53japxti88wg910g5qr0lv2j 
        foreign key (LICENSE) 
        references jms.document (ID_DOCUMENT);

    alter table jms.company 
        add constraint FK_771hgoxvmtw0o6618c7i4kek5 
        foreign key (LOGO) 
        references jms.document (ID_DOCUMENT);

    alter table jms.company 
        add constraint FK_fps9ggysd6gf1hcamsq5ft75g 
        foreign key (COMPANY_CATORGORY) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.company 
        add constraint FK_bf28nkhywxvk1bhjd6dwmk7v6 
        foreign key (Company_Nature) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.company 
        add constraint FK_b8um1fjrhydaixu1ar8jof5c8 
        foreign key (COMPANY_SIZE) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.company 
        add constraint FK_rg4pej4k6kurrsjv67x5syaus 
        foreign key (COMPANY_TYPE) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.company 
        add constraint FK_hqla198kv96gjvqtqynfagwbl 
        foreign key (LOCALE) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.company 
        add constraint FK_i2r8yg26o8gqpcw5rod7u1usm 
        foreign key (SCHEME) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.company 
        add constraint FK_9ch6hxf6l9pqr333g0dighfce 
        foreign key (TASK_TYPE) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.company 
        add constraint FK_67cjjjubek4edtvkfnl4kqko0 
        foreign key (CREATOR) 
        references jms.users (ID_USER);

    alter table jms.company_AUD 
        add constraint FK_5kaep2deeow802kjduf8ba8m8 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.district 
        add constraint FK_yj4ysbxayxh1ulrt7kt4ksfv 
        foreign key (ID_CITY) 
        references jms.city (ID_CITY);

    alter table jms.district_AUD 
        add constraint FK_qyw10ishcxc9uuwtct4m8kqfr 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.document 
        add constraint FK_rerkw14mn8x7ouuc2dtg3slyh 
        foreign key (PARENT) 
        references jms.document (ID_DOCUMENT);

    alter table jms.document_AUD 
        add constraint FK_rivvuguvsg36j70fr3ihfbxei 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.group_authorities 
        add constraint FK_5l13hpvqjnw0hdy2m1x9agsw1 
        foreign key (ID_ROLE) 
        references jms.roles (ID_ROLE);

    alter table jms.group_authorities 
        add constraint FK_11ou4r4q1p8frvorixo010xy9 
        foreign key (ID_GROUP) 
        references jms.groups (ID_GROUP);

    alter table jms.group_authorities_AUD 
        add constraint FK_kb81c5yoll7dgpfql5073blu0 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.group_members 
        add constraint FK_gs2vgmfu8il835bddv5mb87un 
        foreign key (ID_GROUP) 
        references jms.groups (ID_GROUP);

    alter table jms.group_members 
        add constraint FK_9e7tned89rc1526m33j1aitvh 
        foreign key (ID_ROLE) 
        references jms.roles (ID_ROLE);

    alter table jms.group_members 
        add constraint FK_bw7arlx5waerwk0jg70t1yqqf 
        foreign key (ID_USER) 
        references jms.users (ID_USER);

    alter table jms.group_members_AUD 
        add constraint FK_ateknjgxrcn8yukn8syyrjekd 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.groups 
        add constraint FK_prfpexwkeux6fx64cbpidkb2u 
        foreign key (CREATOR) 
        references jms.users (ID_USER);

    alter table jms.groups_AUD 
        add constraint FK_fvnv9ar2nqhjrsfrd71txlheq 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.issue 
        add constraint FK_oln3qtltxd5jwf9enpwie311k 
        foreign key (ID_PROJECT) 
        references jms.project (ID_PROJECT);

    alter table jms.issue 
        add constraint FK_fep7gedcowu0o0yhv7u6d80u9 
        foreign key (PRIORITY) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.issue 
        add constraint FK_sgu14elq91ras16xiblsu0ovg 
        foreign key (STATUS) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.issue_AUD 
        add constraint FK_99r0ko1ns7art19gyloh1tsrc 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.issue_comment 
        add constraint FK_n906x41iilyv5ly45xd9gcbnq 
        foreign key (ID_ISSUE) 
        references jms.issue (ID_ISSUE);

    alter table jms.issue_comment 
        add constraint FK_o3tp5atacdm9g7ka3vf8y6uyq 
        foreign key (PARENT) 
        references jms.issue_comment (ID_ISSUE_COMMENT);

    alter table jms.issue_comment_AUD 
        add constraint FK_a4bmw9n4vq2831clp7cw0ap74 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.issue_doc 
        add constraint FK_scdkphc4mgcemseajnqelbgs 
        foreign key (ID_DOCUMENT) 
        references jms.document (ID_DOCUMENT);

    alter table jms.issue_doc 
        add constraint FK_gpctmjg5y30ow7q8abh6qfn7w 
        foreign key (ID_ISSUE) 
        references jms.issue (ID_ISSUE);

    alter table jms.issue_doc_AUD 
        add constraint FK_5ilf8rn6j11odh4bqkf4x3oax 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.module 
        add constraint FK_rgpur9vf26ea4uqsubqlfrkkn 
        foreign key (PARENT) 
        references jms.module (ID_MODULE);

    alter table jms.module_AUD 
        add constraint FK_bonwo5k7od7lfoa49rgymi37y 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.project 
        add constraint FK_fslighb5wfbsx9vb0qctomy1m 
        foreign key (ID_COMPANY) 
        references jms.company (ID_COMPANY);

    alter table jms.project 
        add constraint FK_idm0j0bs1b2hqmddbw0u1d3n 
        foreign key (PARENT) 
        references jms.project (ID_PROJECT);

    alter table jms.project 
        add constraint FK_brgv1jyf8p12pxmlu1yws1wm5 
        foreign key (PRIORITY) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.project 
        add constraint FK_3s7bbedgch8t2agrtqyafpkrl 
        foreign key (LEADER) 
        references jms.users (ID_USER);

    alter table jms.project_AUD 
        add constraint FK_n2xwgs7hevwt0ha611ftf085v 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.project_doc 
        add constraint FK_mlkilm0f19fcuytnuri5a3b8y 
        foreign key (ID_DOCUMENT) 
        references jms.document (ID_DOCUMENT);

    alter table jms.project_doc 
        add constraint FK_bpf2rn7pklcau0ls7y8efbfur 
        foreign key (ID_PROJECT) 
        references jms.project (ID_PROJECT);

    alter table jms.project_doc_AUD 
        add constraint FK_i0yd76iqeu4alc99trxn2d0i1 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.project_participant 
        add constraint FK_9psr43gjty44bhfjgd9ksbua8 
        foreign key (ID_PROJECT) 
        references jms.project (ID_PROJECT);

    alter table jms.project_participant 
        add constraint FK_lofl5wgvxn2ujrilb23se1lku 
        foreign key (ID_USER) 
        references jms.users (ID_USER);

    alter table jms.project_participant_AUD 
        add constraint FK_ncj642dtspw5k207pnabrrcgi 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.province_AUD 
        add constraint FK_40ttny3exqygsfnpoxf2frjxp 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.role_priv 
        add constraint FK_1yw9oqyrcva1qsm6aq7imvrlv 
        foreign key (ID_MODULE) 
        references jms.module (ID_MODULE);

    alter table jms.role_priv 
        add constraint FK_dcs3qnapouopug42ajcli2ni8 
        foreign key (ID_ROLE) 
        references jms.roles (ID_ROLE);

    alter table jms.role_priv_AUD 
        add constraint FK_rpicqp746wcikrvwdsf84jvq 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.roles 
        add constraint FK_q2ywptktjba6j8vn89lfygl6v 
        foreign key (ID_COMPANY) 
        references jms.company (ID_COMPANY);

    alter table jms.roles 
        add constraint FK_7yavc11e768hkl0l258ax8ub9 
        foreign key (PARENT) 
        references jms.roles (ID_ROLE);

    alter table jms.roles 
        add constraint FK_4axrk0ied6co5d2t0myh9x2lg 
        foreign key (ID_SECTOR) 
        references jms.sector (ID_SECTOR);

    alter table jms.roles_AUD 
        add constraint FK_869jp78jf9np07l9qouohk0e3 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.sector 
        add constraint FK_fo9kikbq6g59ypwm0ra8ojpvv 
        foreign key (ID_COMPANY) 
        references jms.company (ID_COMPANY);

    alter table jms.sector_AUD 
        add constraint FK_ibnor0s5hnoxoif9fj5qf2mne 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.sector_member 
        add constraint FK_4mgto6q7b9j8rojcffv2ttiw1 
        foreign key (ID_ROLE) 
        references jms.roles (ID_ROLE);

    alter table jms.sector_member 
        add constraint FK_eqrd61cad1i6u9639yqe0pjms 
        foreign key (ID_SECTOR) 
        references jms.sector (ID_SECTOR);

    alter table jms.sector_member 
        add constraint FK_45eim0e1e807o44nl1q4dtqie 
        foreign key (ID_USER) 
        references jms.users (ID_USER);

    alter table jms.sector_member_AUD 
        add constraint FK_dg6bi47rv70vv7ecavm996dg5 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.sys_dic_AUD 
        add constraint FK_pgvelg4jliwv2vfahuruw11bg 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.sys_dic_d 
        add constraint FK_nko23xqevl24p57os4fujhv8o 
        foreign key (TYPE) 
        references jms.sys_dic (TYPE);

    alter table jms.sys_dic_d_AUD 
        add constraint FK_jysl66kj8ok8vispi6esnyurf 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.task 
        add constraint FK_l6i2c2kptol5f47ypr2qg3cic 
        foreign key (ID_PROJECT) 
        references jms.project (ID_PROJECT);

    alter table jms.task 
        add constraint FK_r6updw3xo45dn49bjcit5y2go 
        foreign key (PRIORITY) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.task 
        add constraint FK_p602wt9c35wa4csuovyfle1bo 
        foreign key (STATUS) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.task 
        add constraint FK_t91nqkj0jg3pq3gykb5fuw52v 
        foreign key (ASSIGNEE) 
        references jms.users (ID_USER);

    alter table jms.task 
        add constraint FK_dl4vklg14kauhgcorx4whgne0 
        foreign key (CREATOR) 
        references jms.users (ID_USER);

    alter table jms.task_AUD 
        add constraint FK_7vvlkvbnjr7jf5nq2g12ojjxp 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.task_comment 
        add constraint FK_5ho23jj68bbvf58419x54lm4k 
        foreign key (ID_TASK) 
        references jms.task (ID_TASK);

    alter table jms.task_comment 
        add constraint FK_t6biiw2rmbdxh7fcy758sof4f 
        foreign key (PARENT) 
        references jms.task_comment (ID_COMMENT);

    alter table jms.task_comment 
        add constraint FK_fhfvx7xogg6fm5ifhki2w68wb 
        foreign key (ID_USER) 
        references jms.users (ID_USER);

    alter table jms.task_comment_AUD 
        add constraint FK_fra0blpa63d1lsq71ai9df6yr 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.task_doc 
        add constraint FK_i0jc2uem3j5rqwbmys508okul 
        foreign key (ID_DOCUMENT) 
        references jms.document (ID_DOCUMENT);

    alter table jms.task_doc 
        add constraint FK_g3ih24nghp60um0qudcjsj9ja 
        foreign key (ID_TASK) 
        references jms.task (ID_TASK);

    alter table jms.task_doc_AUD 
        add constraint FK_fonup6r9em7cbn3b6bgquok3e 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.trace 
        add constraint FK_rebr43w5xn0ow9amyobsif74w 
        foreign key (ID_USER) 
        references jms.users (ID_USER);

    alter table jms.trace_AUD 
        add constraint FK_t556gv8qpth3v4u42iit5n85e 
        foreign key (REV) 
        references REVINFO (REV);

    alter table jms.users 
        add constraint FK_7imv6h7yni4ym0amouk81etcr 
        foreign key (ID_COMPANY) 
        references jms.company (ID_COMPANY);

    alter table jms.users 
        add constraint FK_rduyds7qtgkuqmrae3kq058au 
        foreign key (ID_DISTRICT) 
        references jms.district (ID_DISTRICT);

    alter table jms.users 
        add constraint FK_1bqjs7tx6yndq1au3acfwdg7x 
        foreign key (CV) 
        references jms.document (ID_DOCUMENT);

    alter table jms.users 
        add constraint FK_tgm53tl6u9cdtbpj2l2mxcrgg 
        foreign key (GENDER) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.users 
        add constraint FK_j6v9bnpcja2pjayji2ncxsx5q 
        foreign key (LOCALE) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.users 
        add constraint FK_r6pgv3hvhyuq46xvbq3kf3ioi 
        foreign key (SCHEME) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.users 
        add constraint FK_t9wueucxecrwxc716vdfen7fg 
        foreign key (STATUS) 
        references jms.sys_dic_d (ID_DIC);

    alter table jms.users 
        add constraint FK_auis29qghxpwv3yqkemdx1xn7 
        foreign key (CREATOR) 
        references jms.users (ID_USER);

    alter table jms.users_AUD 
        add constraint FK_e8r4q22dh138c5f4mvlsieff5 
        foreign key (REV) 
        references REVINFO (REV);
