
CREATE TABLE APPLICATION
(
	ID_APPLICATION        INTEGER NOT NULL,
	PRIVOUS_APPLICATION   INTEGER NULL,
	ID_APPROVAL_PROCESS   INTEGER NULL,
	APPLICANT             INTEGER NULL,
	START_TIME            TIMESTAMP NULL,
	END_TIME              TIMESTAMP NULL,
	CONTENT               VARCHAR(1024) NULL,
	STATUS                INTEGER NULL
)
;



ALTER TABLE APPLICATION
	ADD  PRIMARY KEY (ID_APPLICATION)
;



CREATE TABLE APPLICATION_PHASE
(
	ID_APPLICATION        INTEGER NOT NULL,
	ID_APPROVAL_PHASE     INTEGER NOT NULL,
	ACTION_TIME           TIMESTAMP NULL,
	STATUS                INTEGER NULL,
	COMMENT               VARCHAR(512) NULL
)
;



ALTER TABLE APPLICATION_PHASE
	ADD  PRIMARY KEY (ID_APPLICATION,ID_APPROVAL_PHASE)
;



CREATE TABLE APPROVAL_CC
(
	ID_APPROVAL_PHASE     INTEGER NOT NULL,
	ID_USER               INTEGER NOT NULL,
	ID_NOTI_METHOD        INTEGER NULL
)
;



ALTER TABLE APPROVAL_CC
	ADD  PRIMARY KEY (ID_APPROVAL_PHASE,ID_USER)
;



CREATE TABLE APPROVAL_PHASE
(
	ID_APPROVAL_PHASE     INTEGER NOT NULL,
	ID_APPROVAL_PROCESS   INTEGER NULL,
	NAME                  VARCHAR(64) NULL,
	APPROVER              INTEGER NULL,
	SEQ                   INTEGER NULL
)
;



ALTER TABLE APPROVAL_PHASE
	ADD  PRIMARY KEY (ID_APPROVAL_PHASE)
;



CREATE TABLE APPROVAL_PROCESS
(
	ID_APPROVAL_PROCESS   INTEGER NOT NULL,
	ID_APPROVAL_TYPE      INTEGER NULL,
	NAME                  VARCHAR(64) NULL,
	DESCRIPTION           VARCHAR(1024) NULL,
	ID_DOCUMENT           INTEGER NULL
)
;



ALTER TABLE APPROVAL_PROCESS
	ADD  PRIMARY KEY (ID_APPROVAL_PROCESS)
;



CREATE TABLE APPROVAL_TYPE
(
	ID_APPROVAL_TYPE      INTEGER NOT NULL,
	NAME                  VARCHAR(20) NULL,
	DESCRIPTION           VARCHAR(256) NULL,
	ID_COMPANY            INTEGER NULL,
	CREATOR               INTEGER NULL,
	CREATION_TIME         DATE NULL
)
;



ALTER TABLE APPROVAL_TYPE
	ADD  PRIMARY KEY (ID_APPROVAL_TYPE)
;



CREATE TABLE APPS
(
	ID                    INTEGER NOT NULL,
	GROUPS                 VARCHAR(64) NULL,
	APP_NAME              VARCHAR(20) NULL,
	DESCRIPTION           VARCHAR(128) NULL,
	SCOPE                 INTEGER NULL,
	APP_KEY               VARCHAR(64) NULL,
	APP_SECRET            VARCHAR(64) NULL,
	SEQ                   INTEGER NULL,
	URL                   VARCHAR(512) NULL
)
;



ALTER TABLE APPS
	ADD  PRIMARY KEY (ID)
;



CREATE TABLE BUY_RECORD
(
	ID                    INTEGER NOT NULL,
	ID_COMPANY            INTEGER NULL,
	ID_SERVICE            INTEGER NULL,
	BUY_TIME              TIMESTAMP NULL,
	INVALID_TIME          TIMESTAMP NULL,
	ID_RECEIPT            INTEGER NULL,
	PAYMENT_NUMDER        VARCHAR(64) NULL,
	ORDER_NUMBER          VARCHAR(64) NULL,
	PAYMENT               FLOAT NULL,
	PAY_METHOD            INTEGER NULL
)
;



ALTER TABLE BUY_RECORD
	ADD  PRIMARY KEY (ID)
;



CREATE TABLE CITY
(
	ID_CITY               INTEGER NOT NULL,
	CITY                  VARCHAR(32) NULL,
	ID_PROVINCE           INTEGER NULL,
	POSTCODE              VARCHAR(20) NULL
)
;



ALTER TABLE CITY
	ADD  PRIMARY KEY (ID_CITY)
;



CREATE TABLE COMPANY
(
	ID_COMPANY            INTEGER NOT NULL,
	COMPANY_NAME          VARCHAR(64) NOT NULL,
	DESCRIPTION           VARCHAR(1024) NULL,
	CREATOR               INTEGER NOT NULL,
	CREATION_TIME         DATE NULL,
	COMPANY_TYPE          INTEGER NULL,
	MODIFY_BY             INTEGER NULL,
	MODIFY_TIME           DATE NULL,
	ESTABLISH_PERSON      VARCHAR(20) NULL,
	ESTABLISHMENT_TIME    DATE NULL,
	EMAIL                 VARCHAR(64) NULL,
	POSTCODE              VARCHAR(20) NULL,
	ID_DISTRICT           INTEGER NULL,
	ADDRESS               VARCHAR(128) NULL,
	Company_Nature        INTEGER NULL,
	COMPANY_SIZE          INTEGER NULL,
	COMPANY_CATORGORY     INTEGER NULL,
	LICENCE               INTEGER NULL,
	VERIFIED              INTEGER NULL,
	TELEPHONE             VARCHAR(20) NULL,
	FAX                   VARCHAR(20) NULL,
	URL                   VARCHAR(128) NULL,
	LOCALE                INTEGER NULL,
	LOGO                  INTEGER NULL,
	TASK_TYPE             INTEGER NULL,
	ENABLED               INTEGER NOT NULL,
	SCHEME                INTEGER NULL,
	USED_SPACE            FLOAT NULL,
	MSG_USED_NUM          INTEGER NULL,
	MSG_AVAILABLE_NUM     INTEGER NULL,
	SPACE                 FLOAT NULL
)
;



ALTER TABLE COMPANY
	ADD  PRIMARY KEY (ID_COMPANY)
;



CREATE TABLE DISTRICT
(
	ID_DISTRICT           INTEGER NOT NULL,
	DISTRICT              VARCHAR(32) NULL,
	ID_CITY               INTEGER NULL
)
;



ALTER TABLE DISTRICT
	ADD  PRIMARY KEY (ID_DISTRICT)
;



CREATE TABLE DOCUMENT
(
	ID_DOCUMENT           INTEGER NOT NULL,
	PARENT                INTEGER NULL,
	CREATOR               INTEGER NULL,
	CREATION_TIME         DATE NULL,
	MODIFY_BY             INTEGER NULL,
	MODIFY_TIME           DATE NULL,
	RELATIVE_PATH         VARCHAR(256) NULL,
	NAME                  VARCHAR(64) NULL,
	FILE_NAME             VARCHAR(256) NULL,
	DESCRIPTION           VARCHAR(256) NULL,
	SIZE                  MEDIUMINT NULL
)
;



ALTER TABLE DOCUMENT
	ADD  PRIMARY KEY (ID_DOCUMENT)
;



CREATE TABLE FEEDBACK
(
	ID                    INTEGER NOT NULL,
	ID_USER               INTEGER NULL,
	FEEDBACK_METHOD       INTEGER NULL,
	TEL                   VARCHAR(20) NULL,
	EMAIL                 VARCHAR(128) NULL,
	NAME                  VARCHAR(64) NULL,
	DESCRIPTION           VARCHAR(1024) NULL,
	STATUS                INTEGER NULL
)
;



ALTER TABLE FEEDBACK
	ADD  PRIMARY KEY (ID)
;



CREATE TABLE GROUP_AUTHORITIES
(
	ID_GROUP              INTEGER NOT NULL,
	ID_ROLE               INTEGER NOT NULL,
	ENABLED               INTEGER NULL
)
;



ALTER TABLE GROUP_AUTHORITIES
	ADD  PRIMARY KEY (ID_GROUP,ID_ROLE)
;



CREATE TABLE GROUP_MEMBERS
(
	ID_GROUP              INTEGER NOT NULL,
	ID_USER               INTEGER NOT NULL,
	ID_ROLE               INTEGER NULL
)
;



ALTER TABLE GROUP_MEMBERS
	ADD  PRIMARY KEY (ID_GROUP,ID_USER)
;



CREATE TABLE GROUP_TYPE
(
	ID_GROUP_TYPE         INTEGER NOT NULL,
	GROUP_TYPE            VARCHAR(20) NULL
)
;



ALTER TABLE GROUP_TYPE
	ADD  PRIMARY KEY (ID_GROUP_TYPE)
;



CREATE TABLE GROUPS
(
	ID_GROUP              INTEGER NOT NULL,
	ID_COMPANY            INTEGER NULL,
	GROUP_NAME            VARCHAR(64) NOT NULL,
	DESCRIPTION           VARCHAR(256) NULL,
	CREATOR               INTEGER NULL,
	CREATION_TIME         DATE NULL,
	ID_GROUP_TYPE         INTEGER NULL,
	SEQ                   INTEGER NULL
)
;



ALTER TABLE GROUPS
	ADD  PRIMARY KEY (ID_GROUP)
;



CREATE TABLE ISSUE
(
	ID_ISSUE              INTEGER NOT NULL,
	TITLE                 VARCHAR(64) NULL,
	DESCRIPTION           VARCHAR(256) NULL,
	CREATOR               INTEGER NULL,
	CREATION_TIME         DATE NULL,
	PRIORITY              INTEGER NULL,
	SOURCE_TYPE           INTEGER NULL,
	SOURCE                INTEGER NULL,
	STATUS                INTEGER NULL
)
;



ALTER TABLE ISSUE
	ADD  PRIMARY KEY (ID_ISSUE)
;



CREATE TABLE ISSUE_COMMENT
(
	ID_ISSUE_COMMENT      INTEGER NOT NULL,
	PARENT                INTEGER NULL,
	CREATOR               INTEGER NULL,
	CREATION_TIME         DATE NULL,
	COMMENT               VARCHAR(1024) NULL,
	MODIFICATION_TIME     DATE NULL,
	ID_ISSUE              INTEGER NULL
)
;



ALTER TABLE ISSUE_COMMENT
	ADD  PRIMARY KEY (ID_ISSUE_COMMENT)
;



CREATE TABLE ISSUE_DOC
(
	ID_ISSUE              INTEGER NOT NULL,
	ID_DOCUMENT           INTEGER NOT NULL,
	PUBLISH               INTEGER NULL,
	ID_ISSUE_COMMENT      INTEGER NULL
)
;



ALTER TABLE ISSUE_DOC
	ADD  PRIMARY KEY (ID_ISSUE,ID_DOCUMENT)
;



CREATE TABLE JMS_EVENT
(
	ID_EVENT              INTEGER NOT NULL,
	PARENT                INTEGER NULL,
	NAME                  VARCHAR(64) NULL,
	DESCRIPTION           VARCHAR(128) NULL,
	TEMPLATE              VARCHAR(64) NULL,
	ID_APP                INTEGER NULL
)
;



ALTER TABLE JMS_EVENT
	ADD  PRIMARY KEY (ID_EVENT)
;



CREATE TABLE NOTI_INSTANCE
(
	ID_NOTI_INSTANCE      INTEGER NOT NULL,
	ID_SOURCE             INTEGER NULL,
	ID_RECEIVER           INTEGER NULL,
	ID_NOTIFICATION       INTEGER NULL,
	CREATION_TIME         TIMESTAMP NULL,
	STATUS                INTEGER NULL
)
;



ALTER TABLE NOTI_INSTANCE
	ADD  PRIMARY KEY (ID_NOTI_INSTANCE)
;



CREATE TABLE NOTI_METHOD
(
	ID_NOTI_METHOD        INTEGER NOT NULL,
	METHOD                VARCHAR(20) NULL
)
;



ALTER TABLE NOTI_METHOD
	ADD  PRIMARY KEY (ID_NOTI_METHOD)
;



CREATE TABLE NOTI_TYPE
(
	ID_NOTI_TYPE          INTEGER NOT NULL,
	NOTI_TYPE             VARCHAR(64) NULL,
	DESCRIPTION           VARCHAR(128) NULL
)
;



ALTER TABLE NOTI_TYPE
	ADD  PRIMARY KEY (ID_NOTI_TYPE)
;



CREATE TABLE NOTICE
(
	ID_NOTICE             INTEGER NOT NULL,
	ID_USER               INTEGER NULL,
	TITLE                 VARCHAR(128) NULL,
	NOTICE                VARCHAR(4096) NULL,
	REMARK                VARCHAR(1024) NULL,
	PUBLISH_TIME          DATE NULL
)
;



ALTER TABLE NOTICE
	ADD  PRIMARY KEY (ID_NOTICE)
;



CREATE TABLE NOTIFICATION
(
	ID_NOTIFICATION       INTEGER NOT NULL,
	ID_COMPANY            INTEGER NULL,
	ID_NOTI_METHOD        INTEGER NULL,
	ID_NOTI_TYPE          INTEGER NULL,
	ID_EVENT              INTEGER NULL
)
;



ALTER TABLE NOTIFICATION
	ADD  PRIMARY KEY (ID_NOTIFICATION)
;



CREATE TABLE POLL
(
	ID_POLL               INTEGER NOT NULL,
	TITLE                 VARCHAR(64) NULL,
	DESCRIPTION           VARCHAR(1024) NULL,
	CREATOR               INTEGER NULL,
	MAX_ITEMS             INTEGER NULL,
	CREATION_TIME         TIMESTAMP NULL,
	END_TIME              TIMESTAMP NULL,
	IS_ANONYMOUS          INTEGER NULL,
	SHOW_RESULTS          INTEGER NULL,
	CONCLUSION            VARCHAR(512) NULL,
	STATUS                INTEGER NULL,
	URL                   VARCHAR(512) NULL
)
;



ALTER TABLE POLL
	ADD  PRIMARY KEY (ID_POLL)
;



CREATE TABLE POLL_ITEMS
(
	ID_ITEM               INTEGER NOT NULL,
	ID_POLL               INTEGER NULL,
	SEQ                   INTEGER NULL,
	ITEM                  VARCHAR(512) NULL,
	PIC                   INTEGER NULL
)
;



ALTER TABLE POLL_ITEMS
	ADD  PRIMARY KEY (ID_ITEM)
;



CREATE TABLE POLL_PARTICIPANT
(
	ID_PARTICIPANT        INTEGER NOT NULL,
	ID_POLL               INTEGER NULL,
	PARTICIPANT           INTEGER NULL,
	EMAIL                 VARCHAR(128) NULL,
	TEL                   VARCHAR(128) NULL,
	COMMENT               VARCHAR(1024) NULL,
	STATUS                INTEGER NULL
)
;



ALTER TABLE POLL_PARTICIPANT
	ADD  PRIMARY KEY (ID_PARTICIPANT)
;



CREATE TABLE PROJECT
(
	ID_PROJECT            INTEGER NOT NULL,
	PARENT                INTEGER NULL,
	PROJECT_NAME          VARCHAR(64) NOT NULL,
	DESCRIPTION           VARCHAR(256) NULL,
	PROJECT_NUMBER        VARCHAR(64) NULL,
	PLAN_START_TIME       DATE NULL,
	PLAN_END_TIME         DATE NULL,
	ID_COMPANY            INTEGER NOT NULL,
	START_TIME            DATE NULL,
	END_TIME              DATE NULL,
	LEADER                INTEGER NULL,
	PRIORITY              INTEGER NULL,
	PROCESS               INTEGER NULL,
	STATUS                INTEGER NULL
)
;



ALTER TABLE PROJECT
	ADD  PRIMARY KEY (ID_PROJECT)
;



CREATE TABLE PROJECT_DOC
(
	ID_PROJECT            INTEGER NOT NULL,
	ID_DOCUMENT           INTEGER NOT NULL,
	PUBLISH               INTEGER NULL
)
;



ALTER TABLE PROJECT_DOC
	ADD  PRIMARY KEY (ID_PROJECT,ID_DOCUMENT)
;



CREATE TABLE PROJECT_PARTICIPANT
(
	ID_PROJECT            INTEGER NOT NULL,
	ID_USER               INTEGER NOT NULL,
	IS_CHARGE             INTEGER NULL
)
;



ALTER TABLE PROJECT_PARTICIPANT
	ADD  PRIMARY KEY (ID_PROJECT,ID_USER)
;



CREATE TABLE PROVINCE
(
	ID_PROVINCE           INTEGER NOT NULL,
	PROVINCE              VARCHAR(32) NULL
)
;



ALTER TABLE PROVINCE
	ADD  PRIMARY KEY (ID_PROVINCE)
;



CREATE TABLE RECEIPT
(
	ID_RECEIPT            INTEGER NOT NULL,
	ID_COMPANY            INTEGER NOT NULL,
	TITLE                 VARCHAR(256) NULL,
	Contact_PERSON        VARCHAR(20) NULL,
	CONTACT_TEL           VARCHAR(20) NULL,
	ADDRESS               VARCHAR(512) NULL,
	IS_PRIMARY            INTEGER NULL,
	BANK                  VARCHAR(128) NULL,
	BANK_NUMBER           VARCHAR(64) NULL
)
;



ALTER TABLE RECEIPT
	ADD  PRIMARY KEY (ID_RECEIPT)
;



CREATE TABLE REPEATS
(
	ID_REPEAT             INTEGER NOT NULL,
	REPEAT_TYPE           INTEGER NULL,
	END_TIME              DATE NULL,
	FREQUENCY             VARCHAR(20) NULL
)
;



ALTER TABLE REPEATS
	ADD  PRIMARY KEY (ID_REPEAT)
;



CREATE TABLE ROLES
(
	ID_ROLE               INTEGER NOT NULL,
	ID_COMPANY            INTEGER NOT NULL,
	PARENT                INTEGER NULL,
	ROLE                  VARCHAR(64) NOT NULL,
	DESCRIPTION           VARCHAR(256) NULL,
	LEVEL                 INTEGER NULL,
	ENABLED               INTEGER NULL
)
;



ALTER TABLE ROLES
	ADD  PRIMARY KEY (ID_ROLE)
;



CREATE TABLE SCHEDULE
(
	ID_SCHEDULE           INTEGER NOT NULL,
	NAME                  VARCHAR(256) NULL,
	DESCRIPTION           VARCHAR(20) NULL,
	ID_REPEAT             INTEGER NULL,
	CREATOR               INTEGER NULL,
	CREATION_TIME         TIMESTAMP NOT NULL,
	START_TIME            TIMESTAMP NULL,
	END_TIME              TIMESTAMP NULL,
	IS_WHOLEDAY           INTEGER NULL,
	REMIND                TIMESTAMP NULL,
	LAZY_SET              TIMESTAMP NULL,
	SCHEDULE_TYPE         INTEGER NULL
)
;



ALTER TABLE SCHEDULE
	ADD  PRIMARY KEY (ID_SCHEDULE)
;



CREATE TABLE SCHEDULE_DOC
(
	ID_SCHEDULE           INTEGER NOT NULL,
	ID_DOCUMENT           INTEGER NOT NULL,
	PUBLISH               INTEGER NULL
)
;



ALTER TABLE SCHEDULE_DOC
	ADD  PRIMARY KEY (ID_SCHEDULE,ID_DOCUMENT)
;



CREATE TABLE SCHEDULE_WATCHER
(
	ID_USER               INTEGER NOT NULL,
	ID_SCHEDULE           INTEGER NOT NULL,
	ENABLED               INTEGER NULL
)
;



ALTER TABLE SCHEDULE_WATCHER
	ADD  PRIMARY KEY (ID_USER,ID_SCHEDULE)
;



CREATE TABLE SERVICE
(
	ID_SERVICE            INTEGER NOT NULL,
	ID_SERVICE_TYPE       INTEGER NULL,
	DESCRIPTION           VARCHAR(20) NULL,
	PAYMENT               FLOAT NULL
)
;



ALTER TABLE SERVICE
	ADD  PRIMARY KEY (ID_SERVICE)
;



CREATE TABLE Service_Type
(
	ID_SERVICE_TYPE       INTEGER NULL,
	TYPE                  VARCHAR(64) NULL,
	Description           VARCHAR(20) NULL
)
;



ALTER TABLE Service_Type
	ADD  PRIMARY KEY (ID_SERVICE_TYPE)
;



CREATE TABLE SYS_DIC
(
	TYPE                  VARCHAR(64) NOT NULL,
	DESCRIPTION           VARCHAR(128) NOT NULL
)
;



ALTER TABLE SYS_DIC
	ADD  PRIMARY KEY (TYPE)
;



CREATE TABLE SYS_DIC_D
(
	ID_DIC                INTEGER NOT NULL,
	TYPE                  VARCHAR(64) NULL,
	NAME                  VARCHAR(64) NOT NULL,
	ENABLED               INTEGER NOT NULL,
	DESCRIPTION           VARCHAR(128) NOT NULL,
	IS_DEFAULT            INTEGER NULL
)
;



ALTER TABLE SYS_DIC_D
	ADD  PRIMARY KEY (ID_DIC)
;



CREATE TABLE TASK
(
	ID_TASK               INTEGER NOT NULL,
	ID_PROJECT            INTEGER NULL,
	NAME                  VARCHAR(64) NOT NULL,
	DESCRIPTION           VARCHAR(1024) NULL,
	CREATION_TIME         DATE NULL,
	MODIFICATION_TIME     DATE NULL,
	PLAN_START_TIME       DATE NOT NULL,
	PLAN_END_TIME         DATE NOT NULL,
	PLAN_DURATION         INTEGER NULL,
	START_TIME            DATE NULL,
	END_TIME              DATE NULL,
	DURATION              INTEGER NULL,
	SEQ                   INTEGER NULL,
	PRIORITY              INTEGER NULL,
	CREATOR               INTEGER NULL,
	STATUS                INTEGER NULL
)
;



ALTER TABLE TASK
	ADD  PRIMARY KEY (ID_TASK)
;



CREATE TABLE TASK_COMMENT
(
	ID_COMMENT            INTEGER NOT NULL,
	PARENT                INTEGER NULL,
	ID_TASK               INTEGER NULL,
	ID_USER               INTEGER NULL,
	COMMENT               VARCHAR(1024) NULL,
	CREATION_TIME         DATE NULL,
	MODIFICATION_TIME     DATE NULL
)
;



ALTER TABLE TASK_COMMENT
	ADD  PRIMARY KEY (ID_COMMENT)
;



CREATE TABLE TASK_DOC
(
	ID_TASK               INTEGER NOT NULL,
	ID_DOCUMENT           INTEGER NOT NULL,
	PUBLISH               INTEGER NULL
)
;



ALTER TABLE TASK_DOC
	ADD  PRIMARY KEY (ID_TASK,ID_DOCUMENT)
;



CREATE TABLE TASK_PARTICIPANT
(
	ID_TASK               INTEGER NOT NULL,
	ID_USER               INTEGER NOT NULL,
	IS_CHARGE             INTEGER NULL
)
;



ALTER TABLE TASK_PARTICIPANT
	ADD  PRIMARY KEY (ID_TASK,ID_USER)
;



CREATE TABLE USERS
(
	ID_USER               INTEGER NOT NULL,
	CREATOR               INTEGER NULL,
	CREATION_TIME         DATE NULL,
	USERNAME              VARCHAR(64) NULL,
	MOBILE                VARCHAR(64) NULL,
	EMAIL                 VARCHAR(64) NULL,
	PASSWORD              VARCHAR(64) NOT NULL,
	IDCARD                VARCHAR(18) NULL,
	NAME                  VARCHAR(64) NULL,
	ADDRESS               VARCHAR(128) NULL,
	ID_COMPANY            INTEGER NULL,
	EXT                   VARCHAR(20) NULL,
	E_NO                  VARCHAR(20) NULL,
	SCHOOL                VARCHAR(128) NULL,
	GRAD_TIME             DATE NULL,
	MAJOR                 VARCHAR(64) NULL,
	DEGREE                VARCHAR(64) NULL,
	EMERGENCY_HP          VARCHAR(64) NULL,
	LOCALE                INTEGER NULL,
	GENDER                INTEGER NULL,
	SCHEME                INTEGER NULL,
	ENABLED               INTEGER NOT NULL,
	STATUS                INTEGER NULL,
	DESCRIPTION           VARCHAR(256) NULL,
	BIRTHDAY              DATE NULL,
	ID_DISTRICT           INTEGER NULL,
	LAST_LOGIN            TIMESTAMP NULL,
	TOKEN                 VARCHAR(128) NULL,
	USER_TYPE             INTEGER NULL,
	CV                    INTEGER NULL,
	PIC                   INTEGER NULL
)
;



ALTER TABLE USERS
	ADD  PRIMARY KEY (ID_USER)
;



CREATE TABLE VOTE
(
	ID_PARTICIPANT        INTEGER NOT NULL,
	ID_ITEM               INTEGER NOT NULL,
	DEGREE                INTEGER NULL
)
;



ALTER TABLE VOTE
	ADD  PRIMARY KEY (ID_PARTICIPANT,ID_ITEM)
;



ALTER TABLE APPLICATION
	ADD FOREIGN KEY R_168 (ID_APPROVAL_PROCESS) REFERENCES APPROVAL_PROCESS(ID_APPROVAL_PROCESS)
;


ALTER TABLE APPLICATION
	ADD FOREIGN KEY R_169 (APPLICANT) REFERENCES USERS(ID_USER)
;


ALTER TABLE APPLICATION
	ADD FOREIGN KEY R_170 (STATUS) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE APPLICATION
	ADD FOREIGN KEY R_176 (PRIVOUS_APPLICATION) REFERENCES APPLICATION(ID_APPLICATION)
;



ALTER TABLE APPLICATION_PHASE
	ADD FOREIGN KEY R_172 (ID_APPLICATION) REFERENCES APPLICATION(ID_APPLICATION)
;


ALTER TABLE APPLICATION_PHASE
	ADD FOREIGN KEY R_173 (ID_APPROVAL_PHASE) REFERENCES APPROVAL_PHASE(ID_APPROVAL_PHASE)
;


ALTER TABLE APPLICATION_PHASE
	ADD FOREIGN KEY R_174 (STATUS) REFERENCES SYS_DIC_D(ID_DIC)
;



ALTER TABLE APPROVAL_CC
	ADD FOREIGN KEY R_166 (ID_APPROVAL_PHASE) REFERENCES APPROVAL_PHASE(ID_APPROVAL_PHASE)
;


ALTER TABLE APPROVAL_CC
	ADD FOREIGN KEY R_167 (ID_USER) REFERENCES USERS(ID_USER)
;


ALTER TABLE APPROVAL_CC
	ADD FOREIGN KEY R_180 (ID_NOTI_METHOD) REFERENCES NOTI_METHOD(ID_NOTI_METHOD)
;



ALTER TABLE APPROVAL_PHASE
	ADD FOREIGN KEY R_163 (ID_APPROVAL_PROCESS) REFERENCES APPROVAL_PROCESS(ID_APPROVAL_PROCESS)
;


ALTER TABLE APPROVAL_PHASE
	ADD FOREIGN KEY R_164 (APPROVER) REFERENCES USERS(ID_USER)
;



ALTER TABLE APPROVAL_PROCESS
	ADD FOREIGN KEY R_161 (ID_APPROVAL_TYPE) REFERENCES APPROVAL_TYPE(ID_APPROVAL_TYPE)
;


ALTER TABLE APPROVAL_PROCESS
	ADD FOREIGN KEY R_162 (ID_DOCUMENT) REFERENCES DOCUMENT(ID_DOCUMENT)
;



ALTER TABLE APPROVAL_TYPE
	ADD FOREIGN KEY R_158 (ID_COMPANY) REFERENCES COMPANY(ID_COMPANY)
;


ALTER TABLE APPROVAL_TYPE
	ADD FOREIGN KEY R_159 (CREATOR) REFERENCES USERS(ID_USER)
;



ALTER TABLE BUY_RECORD
	ADD FOREIGN KEY R_120 (ID_COMPANY) REFERENCES COMPANY(ID_COMPANY)
;


ALTER TABLE BUY_RECORD
	ADD FOREIGN KEY R_124 (ID_RECEIPT) REFERENCES RECEIPT(ID_RECEIPT)
;


ALTER TABLE BUY_RECORD
	ADD FOREIGN KEY R_126 (ID_SERVICE) REFERENCES SERVICE(ID_SERVICE)
;



ALTER TABLE CITY
	ADD FOREIGN KEY R_70 (ID_PROVINCE) REFERENCES PROVINCE(ID_PROVINCE)
;



ALTER TABLE COMPANY
	ADD FOREIGN KEY R_92 (SCHEME) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE COMPANY
	ADD FOREIGN KEY R_99 (COMPANY_CATORGORY) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE COMPANY
	ADD FOREIGN KEY R_115 (MODIFY_BY) REFERENCES USERS(ID_USER)
;


ALTER TABLE COMPANY
	ADD FOREIGN KEY R_12 (CREATOR) REFERENCES USERS(ID_USER)
;


ALTER TABLE COMPANY
	ADD FOREIGN KEY R_65 (COMPANY_TYPE) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE COMPANY
	ADD FOREIGN KEY R_67 (COMPANY_SIZE) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE COMPANY
	ADD FOREIGN KEY R_69 (Company_Nature) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE COMPANY
	ADD FOREIGN KEY R_72 (ID_DISTRICT) REFERENCES DISTRICT(ID_DISTRICT)
;


ALTER TABLE COMPANY
	ADD FOREIGN KEY R_85 (LOGO) REFERENCES DOCUMENT(ID_DOCUMENT)
;


ALTER TABLE COMPANY
	ADD FOREIGN KEY R_86 (LICENCE) REFERENCES DOCUMENT(ID_DOCUMENT)
;


ALTER TABLE COMPANY
	ADD FOREIGN KEY R_90 (LOCALE) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE COMPANY
	ADD FOREIGN KEY R_91 (TASK_TYPE) REFERENCES SYS_DIC_D(ID_DIC)
;



ALTER TABLE DISTRICT
	ADD FOREIGN KEY R_71 (ID_CITY) REFERENCES CITY(ID_CITY)
;



ALTER TABLE DOCUMENT
	ADD FOREIGN KEY R_58 (PARENT) REFERENCES DOCUMENT(ID_DOCUMENT)
;


ALTER TABLE DOCUMENT
	ADD FOREIGN KEY R_116 (CREATOR) REFERENCES USERS(ID_USER)
;


ALTER TABLE DOCUMENT
	ADD FOREIGN KEY R_117 (MODIFY_BY) REFERENCES USERS(ID_USER)
;



ALTER TABLE FEEDBACK
	ADD FOREIGN KEY R_127 (ID_USER) REFERENCES USERS(ID_USER)
;



ALTER TABLE GROUP_AUTHORITIES
	ADD FOREIGN KEY R_6 (ID_GROUP) REFERENCES GROUPS(ID_GROUP)
;


ALTER TABLE GROUP_AUTHORITIES
	ADD FOREIGN KEY R_7 (ID_ROLE) REFERENCES ROLES(ID_ROLE)
;



ALTER TABLE GROUP_MEMBERS
	ADD FOREIGN KEY R_1 (ID_USER) REFERENCES USERS(ID_USER)
;


ALTER TABLE GROUP_MEMBERS
	ADD FOREIGN KEY R_2 (ID_GROUP) REFERENCES GROUPS(ID_GROUP)
;


ALTER TABLE GROUP_MEMBERS
	ADD FOREIGN KEY R_3 (ID_ROLE) REFERENCES ROLES(ID_ROLE)
;



ALTER TABLE GROUPS
	ADD FOREIGN KEY R_33 (CREATOR) REFERENCES USERS(ID_USER)
;


ALTER TABLE GROUPS
	ADD FOREIGN KEY R_110 (ID_GROUP_TYPE) REFERENCES GROUP_TYPE(ID_GROUP_TYPE)
;


ALTER TABLE GROUPS
	ADD FOREIGN KEY R_111 (ID_COMPANY) REFERENCES COMPANY(ID_COMPANY)
;



ALTER TABLE ISSUE
	ADD FOREIGN KEY R_95 (PRIORITY) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE ISSUE
	ADD FOREIGN KEY R_96 (STATUS) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE ISSUE
	ADD FOREIGN KEY R_132 (CREATOR) REFERENCES USERS(ID_USER)
;


ALTER TABLE ISSUE
	ADD FOREIGN KEY R_171 (SOURCE_TYPE) REFERENCES SYS_DIC_D(ID_DIC)
;



ALTER TABLE ISSUE_COMMENT
	ADD FOREIGN KEY R_80 (PARENT) REFERENCES ISSUE_COMMENT(ID_ISSUE_COMMENT)
;


ALTER TABLE ISSUE_COMMENT
	ADD FOREIGN KEY R_81 (ID_ISSUE) REFERENCES ISSUE(ID_ISSUE)
;


ALTER TABLE ISSUE_COMMENT
	ADD FOREIGN KEY R_130 (CREATOR) REFERENCES USERS(ID_USER)
;



ALTER TABLE ISSUE_DOC
	ADD FOREIGN KEY R_82 (ID_ISSUE) REFERENCES ISSUE(ID_ISSUE)
;


ALTER TABLE ISSUE_DOC
	ADD FOREIGN KEY R_83 (ID_DOCUMENT) REFERENCES DOCUMENT(ID_DOCUMENT)
;


ALTER TABLE ISSUE_DOC
	ADD FOREIGN KEY R_131 (ID_ISSUE_COMMENT) REFERENCES ISSUE_COMMENT(ID_ISSUE_COMMENT)
;



ALTER TABLE JMS_EVENT
	ADD FOREIGN KEY R_179 (ID_APP) REFERENCES APPS(ID)
;


ALTER TABLE JMS_EVENT
	ADD FOREIGN KEY R_181 (PARENT) REFERENCES JMS_EVENT(ID_EVENT)
;



ALTER TABLE NOTI_INSTANCE
	ADD FOREIGN KEY R_106 (ID_NOTIFICATION) REFERENCES NOTIFICATION(ID_NOTIFICATION)
;



ALTER TABLE NOTICE
	ADD FOREIGN KEY R_135 (ID_USER) REFERENCES USERS(ID_USER)
;



ALTER TABLE NOTIFICATION
	ADD FOREIGN KEY R_102 (ID_EVENT) REFERENCES JMS_EVENT(ID_EVENT)
;


ALTER TABLE NOTIFICATION
	ADD FOREIGN KEY R_103 (ID_NOTI_TYPE) REFERENCES NOTI_TYPE(ID_NOTI_TYPE)
;


ALTER TABLE NOTIFICATION
	ADD FOREIGN KEY R_104 (ID_COMPANY) REFERENCES COMPANY(ID_COMPANY)
;


ALTER TABLE NOTIFICATION
	ADD FOREIGN KEY R_105 (ID_NOTI_METHOD) REFERENCES NOTI_METHOD(ID_NOTI_METHOD)
;



ALTER TABLE POLL
	ADD FOREIGN KEY R_137 (CREATOR) REFERENCES USERS(ID_USER)
;


ALTER TABLE POLL
	ADD FOREIGN KEY R_140 (STATUS) REFERENCES SYS_DIC_D(ID_DIC)
;



ALTER TABLE POLL_ITEMS
	ADD FOREIGN KEY R_138 (ID_POLL) REFERENCES POLL(ID_POLL)
;


ALTER TABLE POLL_ITEMS
	ADD FOREIGN KEY R_139 (PIC) REFERENCES DOCUMENT(ID_DOCUMENT)
;



ALTER TABLE POLL_PARTICIPANT
	ADD FOREIGN KEY R_141 (ID_POLL) REFERENCES POLL(ID_POLL)
;


ALTER TABLE POLL_PARTICIPANT
	ADD FOREIGN KEY R_144 (PARTICIPANT) REFERENCES USERS(ID_USER)
;


ALTER TABLE POLL_PARTICIPANT
	ADD FOREIGN KEY R_145 (STATUS) REFERENCES SYS_DIC_D(ID_DIC)
;



ALTER TABLE PROJECT
	ADD FOREIGN KEY R_26 (ID_COMPANY) REFERENCES COMPANY(ID_COMPANY)
;


ALTER TABLE PROJECT
	ADD FOREIGN KEY R_77 (LEADER) REFERENCES USERS(ID_USER)
;


ALTER TABLE PROJECT
	ADD FOREIGN KEY R_97 (PRIORITY) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE PROJECT
	ADD FOREIGN KEY R_118 (PARENT) REFERENCES PROJECT(ID_PROJECT)
;


ALTER TABLE PROJECT
	ADD FOREIGN KEY R_119 (STATUS) REFERENCES SYS_DIC_D(ID_DIC)
;



ALTER TABLE PROJECT_DOC
	ADD FOREIGN KEY R_56 (ID_PROJECT) REFERENCES PROJECT(ID_PROJECT)
;


ALTER TABLE PROJECT_DOC
	ADD FOREIGN KEY R_57 (ID_DOCUMENT) REFERENCES DOCUMENT(ID_DOCUMENT)
;



ALTER TABLE PROJECT_PARTICIPANT
	ADD FOREIGN KEY R_21 (ID_PROJECT) REFERENCES PROJECT(ID_PROJECT)
;


ALTER TABLE PROJECT_PARTICIPANT
	ADD FOREIGN KEY R_22 (ID_USER) REFERENCES USERS(ID_USER)
;



ALTER TABLE RECEIPT
	ADD FOREIGN KEY R_123 (ID_COMPANY) REFERENCES COMPANY(ID_COMPANY)
;



ALTER TABLE REPEATS
	ADD FOREIGN KEY R_154 (REPEAT_TYPE) REFERENCES SYS_DIC_D(ID_DIC)
;



ALTER TABLE ROLES
	ADD FOREIGN KEY R_34 (ID_COMPANY) REFERENCES COMPANY(ID_COMPANY)
;


ALTER TABLE ROLES
	ADD FOREIGN KEY R_84 (PARENT) REFERENCES ROLES(ID_ROLE)
;



ALTER TABLE SCHEDULE
	ADD FOREIGN KEY R_149 (CREATOR) REFERENCES USERS(ID_USER)
;


ALTER TABLE SCHEDULE
	ADD FOREIGN KEY R_150 (SCHEDULE_TYPE) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE SCHEDULE
	ADD FOREIGN KEY R_153 (ID_REPEAT) REFERENCES REPEATS(ID_REPEAT)
;



ALTER TABLE SCHEDULE_DOC
	ADD FOREIGN KEY R_156 (ID_SCHEDULE) REFERENCES SCHEDULE(ID_SCHEDULE)
;


ALTER TABLE SCHEDULE_DOC
	ADD FOREIGN KEY R_157 (ID_DOCUMENT) REFERENCES DOCUMENT(ID_DOCUMENT)
;



ALTER TABLE SCHEDULE_WATCHER
	ADD FOREIGN KEY R_151 (ID_USER) REFERENCES USERS(ID_USER)
;


ALTER TABLE SCHEDULE_WATCHER
	ADD FOREIGN KEY R_152 (ID_SCHEDULE) REFERENCES SCHEDULE(ID_SCHEDULE)
;



ALTER TABLE SERVICE
	ADD FOREIGN KEY R_125 (ID_SERVICE_TYPE) REFERENCES Service_Type(ID_SERVICE_TYPE)
;



ALTER TABLE SYS_DIC_D
	ADD FOREIGN KEY R_64 (TYPE) REFERENCES SYS_DIC(TYPE)
;



ALTER TABLE TASK
	ADD FOREIGN KEY R_29 (ID_PROJECT) REFERENCES PROJECT(ID_PROJECT)
;


ALTER TABLE TASK
	ADD FOREIGN KEY R_100 (PRIORITY) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE TASK
	ADD FOREIGN KEY R_101 (STATUS) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE TASK
	ADD FOREIGN KEY R_129 (CREATOR) REFERENCES USERS(ID_USER)
;



ALTER TABLE TASK_COMMENT
	ADD FOREIGN KEY R_46 (ID_TASK) REFERENCES TASK(ID_TASK)
;


ALTER TABLE TASK_COMMENT
	ADD FOREIGN KEY R_47 (ID_USER) REFERENCES USERS(ID_USER)
;


ALTER TABLE TASK_COMMENT
	ADD FOREIGN KEY R_48 (PARENT) REFERENCES TASK_COMMENT(ID_COMMENT)
;



ALTER TABLE TASK_DOC
	ADD FOREIGN KEY R_75 (ID_TASK) REFERENCES TASK(ID_TASK)
;


ALTER TABLE TASK_DOC
	ADD FOREIGN KEY R_76 (ID_DOCUMENT) REFERENCES DOCUMENT(ID_DOCUMENT)
;



ALTER TABLE TASK_PARTICIPANT
	ADD FOREIGN KEY R_133 (ID_TASK) REFERENCES TASK(ID_TASK)
;


ALTER TABLE TASK_PARTICIPANT
	ADD FOREIGN KEY R_134 (ID_USER) REFERENCES USERS(ID_USER)
;



ALTER TABLE USERS
	ADD FOREIGN KEY R_41 (CV) REFERENCES DOCUMENT(ID_DOCUMENT)
;


ALTER TABLE USERS
	ADD FOREIGN KEY R_61 (ID_COMPANY) REFERENCES COMPANY(ID_COMPANY)
;


ALTER TABLE USERS
	ADD FOREIGN KEY R_74 (CREATOR) REFERENCES USERS(ID_USER)
;


ALTER TABLE USERS
	ADD FOREIGN KEY R_87 (LOCALE) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE USERS
	ADD FOREIGN KEY R_88 (GENDER) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE USERS
	ADD FOREIGN KEY R_89 (SCHEME) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE USERS
	ADD FOREIGN KEY R_93 (STATUS) REFERENCES SYS_DIC_D(ID_DIC)
;


ALTER TABLE USERS
	ADD FOREIGN KEY R_94 (ID_DISTRICT) REFERENCES DISTRICT(ID_DISTRICT)
;


ALTER TABLE USERS
	ADD FOREIGN KEY R_182 (PIC) REFERENCES DOCUMENT(ID_DOCUMENT)
;



ALTER TABLE VOTE
	ADD FOREIGN KEY R_146 (ID_PARTICIPANT) REFERENCES POLL_PARTICIPANT(ID_PARTICIPANT)
;


ALTER TABLE VOTE
	ADD FOREIGN KEY R_147 (ID_ITEM) REFERENCES POLL_ITEMS(ID_ITEM)
;


ALTER TABLE VOTE
	ADD FOREIGN KEY R_148 (DEGREE) REFERENCES SYS_DIC_D(ID_DIC)
;


