CREATE TABLE AUTHORITIES
(
	ID_ROLE               INTEGER NOT NULL,
	ID_USER               INTEGER NOT NULL
)
;



ALTER TABLE AUTHORITIES
	ADD  PRIMARY KEY (ID_ROLE,ID_USER)
;



CREATE TABLE COMPANY
(
	ID_COMPANY            INTEGER NOT NULL,
	COMPANY_NAME          VARCHAR(64) NOT NULL,
	DESCRIPTION           VARCHAR(1024) NULL,
	CREATOR               INTEGER NOT NULL,
	CREATION_TIME         DATE NOT NULL,
	FINE_TASK             INTEGER NOT NULL,
	ENABLED               INTEGER NOT NULL,
	USED_SPACE            FLOAT NULL,
	COMPANY_TYPE          INTEGER NULL,
	COMPANY_SIZE          INTEGER NULL,
	COMPANY_CATERGORY     INTEGER NOT NULL
)
;



ALTER TABLE COMPANY
	ADD  PRIMARY KEY (ID_COMPANY)
;



CREATE TABLE DOCUMENTS
(
	ID_DOCUMENT           INTEGER NOT NULL,
	PARENT                INTEGER NULL,
	FILE_NAME             VARCHAR(256) NULL,
	NAME                  VARCHAR(64) NULL,
	DESCRIPTION           VARCHAR(256) NULL,
	RELATIVE_PATH         VARCHAR(256) NULL,
	SIZE                  INTEGER NULL
)
;



ALTER TABLE DOCUMENTS
	ADD  PRIMARY KEY (ID_DOCUMENT)
;



CREATE TABLE GROUP_AUTHORITIES
(
	ID_GROUP              INTEGER NOT NULL,
	ID_ROLE               INTEGER NOT NULL
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



CREATE TABLE GROUPS
(
	ID_GROUP              INTEGER NOT NULL,
	GROUP_NAME            VARCHAR(64) NOT NULL,
	DESCRIPTION           VARCHAR(256) NULL,
	CREATOR               INTEGER NULL,
	CREATION_TIME         DATE NULL
)
;



ALTER TABLE GROUPS
	ADD  PRIMARY KEY (ID_GROUP)
;



CREATE TABLE MODULES
(
	ID_MODULE             INTEGER NOT NULL,
	PARENT                INTEGER NULL,
	NAME                  VARCHAR(64) NULL,
	DESCRIPTION           VARCHAR(128) NULL
)
;



ALTER TABLE MODULES
	ADD  PRIMARY KEY (ID_MODULE)
;



CREATE TABLE PERSISTENT_LOGIN
(
	SERIES                VARCHAR(64) NOT NULL,
	TOKEN                 VARCHAR(64) NOT NULL,
	LAST_UESED            TIMESTAMP NOT NULL,
	ID_USER               INTEGER NULL
)
;



ALTER TABLE PERSISTENT_LOGIN
	ADD  PRIMARY KEY (SERIES)
;



CREATE TABLE PROJECT
(
	ID_PROJECT            INTEGER NOT NULL,
	ID_COMPANY            INTEGER NOT NULL,
	PROJECT_NAME          VARCHAR(64) NOT NULL,
	PROJECT_NUMBER        VARCHAR(64) NULL,
	PROCESS               INTEGER NULL,
	DESCRIPTION           VARCHAR(256) NULL,
	EXPECT_START_TIME     DATE NULL,
	EXPECT_END_TIME       DATE NULL,
	START_TIME            DATE NULL,
	END_TIME              DATE NULL,
	ENABLED               INTEGER NOT NULL
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
	ID_ROLE               INTEGER NULL
)
;



ALTER TABLE PROJECT_PARTICIPANT
	ADD  PRIMARY KEY (ID_PROJECT,ID_USER)
;



CREATE TABLE ROLE_PRIV
(
	ID_MODULE             INTEGER NOT NULL,
	ID_ROLE               INTEGER NOT NULL,
	PRIV                  INTEGER NULL
)
;



ALTER TABLE ROLE_PRIV
	ADD  PRIMARY KEY (ID_MODULE,ID_ROLE)
;



CREATE TABLE ROLES
(
	ID_ROLE               INTEGER NOT NULL,
	ROLE                  VARCHAR(64) NOT NULL,
	DESCRIPTION           VARCHAR(256) NULL,
	ID_COMPANY            INTEGER NOT NULL,
	LEVEL                 INTEGER NULL
)
;



ALTER TABLE ROLES
	ADD  PRIMARY KEY (ID_ROLE)
;



CREATE TABLE SECTOR
(
	ID_SECTOR             INTEGER NOT NULL,
	SECTOR                VARCHAR(64) NULL,
	Description           VARCHAR(256) NULL,
	ID_COMPANY            INTEGER NOT NULL
)
;



ALTER TABLE SECTOR
	ADD  PRIMARY KEY (ID_SECTOR)
;



CREATE TABLE SECTOR_MEMBER
(
	ID_SECTOR             INTEGER NOT NULL,
	ID_USER               INTEGER NOT NULL,
	ID_ROLE               INTEGER NULL
)
;



ALTER TABLE SECTOR_MEMBER
	ADD  PRIMARY KEY (ID_SECTOR,ID_USER)
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
	DESCRIPTION           VARCHAR(128) NOT NULL
)
;



ALTER TABLE SYS_DIC_D
	ADD  PRIMARY KEY (ID_DIC)
;



CREATE TABLE TASK
(
	ID_TASK               INTEGER NOT NULL,
	NAME                  VARCHAR(64) NOT NULL,
	DESCRIPTION           VARCHAR(1024) NULL,
	CREATOR               INTEGER NULL,
	CREATION_TIME         DATE NULL,
	MODIFICATION_TIME     DATE NULL,
	ASSIGNEE              INTEGER NULL,
	EXPECT_START_TIME     DATE NOT NULL,
	EXPECT_END_TIME       DATE NOT NULL,
	EXPECT_DURATION       INTEGER NULL,
	START_TIME            DATE NULL,
	END_TIME              DATE NULL,
	DURATION              INTEGER NULL,
	PRIORITY              VARCHAR(20) NOT NULL,
	STATUS                VARCHAR(20) NOT NULL,
	SEQ                   INTEGER NULL,
	ID_DOCUMENT           INTEGER NULL,
	ID_PROJECT            INTEGER NULL
)
;



ALTER TABLE TASK
	ADD  PRIMARY KEY (ID_TASK)
;



CREATE TABLE TASK_COMMENT
(
	ID_COMMENT            INTEGER NULL,
	ID_TASK               INTEGER NULL,
	ID_USER               INTEGER NULL,
	COMMENT               VARCHAR(512) NULL,
	CREATION_TIME         DATE NULL,
	MODIFICATION_TIME     DATE NULL,
	ID_DOCUMENT           INTEGER NULL
)
;



ALTER TABLE TASK_COMMENT
	ADD  PRIMARY KEY (ID_COMMENT)
;



CREATE TABLE TRACE
(
	ID_TRACE              INTEGER NOT NULL,
	ACTION_TYPE           VARCHAR(20) NOT NULL,
	TRACE_TIME            DATE NOT NULL,
	IP                    VARCHAR(64) NULL,
	GPS                   VARCHAR(128) NULL,
	ID_USER               INTEGER NOT NULL
)
;



ALTER TABLE TRACE
	ADD  PRIMARY KEY (ID_TRACE)
;



CREATE TABLE USERS
(
	ID_USER               INTEGER NOT NULL,
	USERNAME              VARCHAR(64) NULL,
	MOBILE                VARCHAR(64) NULL,
	EMAIL                 VARCHAR(64) NULL,
	PASSWORD              VARCHAR(64) NOT NULL,
	NAME                  VARCHAR(64) NULL,
	ADDRESS               VARCHAR(128) NULL,
	IDCARD                VARCHAR(18) NULL,
	CREATION_TIME         DATE NULL,
	ENABLED               INTEGER NOT NULL,
	CV                    INTEGER NULL,
	ID_COMPANY            INTEGER NULL,
	LOCALE                VARCHAR(20) NOT NULL
)
;



ALTER TABLE USERS
	ADD  PRIMARY KEY (ID_USER)
;



ALTER TABLE AUTHORITIES
	ADD FOREIGN KEY R_4 (ID_USER) REFERENCES USERS(ID_USER)
;


ALTER TABLE AUTHORITIES
	ADD FOREIGN KEY R_5 (ID_ROLE) REFERENCES ROLES(ID_ROLE)
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



ALTER TABLE DOCUMENTS
	ADD FOREIGN KEY R_58 (PARENT) REFERENCES DOCUMENTS(ID_DOCUMENT)
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



ALTER TABLE MODULES
	ADD FOREIGN KEY R_37 (PARENT) REFERENCES MODULES(ID_MODULE)
;



ALTER TABLE PERSISTENT_LOGIN
	ADD FOREIGN KEY R_32 (ID_USER) REFERENCES USERS(ID_USER)
;



ALTER TABLE PROJECT
	ADD FOREIGN KEY R_26 (ID_COMPANY) REFERENCES COMPANY(ID_COMPANY)
;



ALTER TABLE PROJECT_DOC
	ADD FOREIGN KEY R_56 (ID_PROJECT) REFERENCES PROJECT(ID_PROJECT)
;


ALTER TABLE PROJECT_DOC
	ADD FOREIGN KEY R_57 (ID_DOCUMENT) REFERENCES DOCUMENTS(ID_DOCUMENT)
;



ALTER TABLE PROJECT_PARTICIPANT
	ADD FOREIGN KEY R_21 (ID_PROJECT) REFERENCES PROJECT(ID_PROJECT)
;


ALTER TABLE PROJECT_PARTICIPANT
	ADD FOREIGN KEY R_22 (ID_USER) REFERENCES USERS(ID_USER)
;


ALTER TABLE PROJECT_PARTICIPANT
	ADD FOREIGN KEY R_23 (ID_ROLE) REFERENCES ROLES(ID_ROLE)
;



ALTER TABLE ROLE_PRIV
	ADD FOREIGN KEY R_38 (ID_MODULE) REFERENCES MODULES(ID_MODULE)
;


ALTER TABLE ROLE_PRIV
	ADD FOREIGN KEY R_39 (ID_ROLE) REFERENCES ROLES(ID_ROLE)
;



ALTER TABLE ROLES
	ADD FOREIGN KEY R_34 (ID_COMPANY) REFERENCES COMPANY(ID_COMPANY)
;



ALTER TABLE SECTOR
	ADD FOREIGN KEY R_13 (ID_COMPANY) REFERENCES COMPANY(ID_COMPANY)
;



ALTER TABLE SECTOR_MEMBER
	ADD FOREIGN KEY R_18 (ID_SECTOR) REFERENCES SECTOR(ID_SECTOR)
;


ALTER TABLE SECTOR_MEMBER
	ADD FOREIGN KEY R_20 (ID_ROLE) REFERENCES ROLES(ID_ROLE)
;


ALTER TABLE SECTOR_MEMBER
	ADD FOREIGN KEY R_62 (ID_USER) REFERENCES USERS(ID_USER)
;



ALTER TABLE SYS_DIC_D
	ADD FOREIGN KEY R_64 (TYPE) REFERENCES SYS_DIC(TYPE)
;



ALTER TABLE TASK
	ADD FOREIGN KEY R_27 (CREATOR) REFERENCES USERS(ID_USER)
;


ALTER TABLE TASK
	ADD FOREIGN KEY R_28 (CREATOR) REFERENCES USERS(ID_USER)
;


ALTER TABLE TASK
	ADD FOREIGN KEY R_29 (ID_PROJECT) REFERENCES PROJECT(ID_PROJECT)
;


ALTER TABLE TASK
	ADD FOREIGN KEY R_40 (ASSIGNEE) REFERENCES USERS(ID_USER)
;


ALTER TABLE TASK
	ADD FOREIGN KEY R_53 (ID_DOCUMENT) REFERENCES DOCUMENTS(ID_DOCUMENT)
;



ALTER TABLE TASK_COMMENT
	ADD FOREIGN KEY R_46 (ID_TASK) REFERENCES TASK(ID_TASK)
;


ALTER TABLE TASK_COMMENT
	ADD FOREIGN KEY R_47 (ID_USER) REFERENCES USERS(ID_USER)
;


ALTER TABLE TASK_COMMENT
	ADD FOREIGN KEY R_48 (ID_COMMENT) REFERENCES TASK_COMMENT(ID_COMMENT)
;


ALTER TABLE TASK_COMMENT
	ADD FOREIGN KEY R_54 (ID_DOCUMENT) REFERENCES DOCUMENTS(ID_DOCUMENT)
;



ALTER TABLE TRACE
	ADD FOREIGN KEY R_8 (ID_USER) REFERENCES USERS(ID_USER)
;



ALTER TABLE USERS
	ADD FOREIGN KEY R_41 (CV) REFERENCES DOCUMENTS(ID_DOCUMENT)
;


ALTER TABLE USERS
	ADD FOREIGN KEY R_61 (ID_COMPANY) REFERENCES COMPANY(ID_COMPANY)
;


