
create table a_application
(
	id_application        integer not null,
	previous_application  integer null,
	id_approval_process   integer null,
	applicant             integer null,
	start_time            timestamp null,
	end_time              timestamp null,
	content               varchar(1024) null,
	status                integer null
)
;



alter table a_application
	add  primary key (id_application)
;



create table a_application_phase
(
	id_application        integer not null,
	id_approval_phase     integer not null,
	action_time           timestamp null,
	status                integer null,
	comment               varchar(512) null
)
;



alter table a_application_phase
	add  primary key (id_application,id_approval_phase)
;



create table a_approval_cc
(
	id_approval_phase     integer not null,
	id_user               integer not null,
	id_noti_method        integer null
)
;



alter table a_approval_cc
	add  primary key (id_approval_phase,id_user)
;



create table a_approval_phase
(
	id_approval_phase     integer not null,
	id_approval_process   integer null,
	name                  varchar(64) null,
	approver              integer null,
	seq                   integer null
)
;



alter table a_approval_phase
	add  primary key (id_approval_phase)
;



create table a_approval_process
(
	id_approval_process   integer not null,
	id_approval_type      integer null,
	name                  varchar(64) null,
	description           varchar(1024) null,
	id_document           integer null
)
;



alter table a_approval_process
	add  primary key (id_approval_process)
;



create table a_approval_type
(
	id_approval_type      integer not null,
	name                  varchar(20) null,
	description           varchar(256) null,
	creator               integer null,
	creation_time         date null,
	id_company            integer null
)
;



alter table a_approval_type
	add  primary key (id_approval_type)
;



create table apps
(
	id_app                integer not null,
	groups                varchar(64) null,
	app_name              varchar(20) null,
	description           varchar(128) null,
	scope                 integer null,
	app_key               varchar(64) null,
	app_secret            varchar(64) null,
	seq                   integer null,
	url                   varchar(512) null
)
;



alter table apps
	add  primary key (id_app)
;



create table buy_record
(
	id                    integer not null,
	id_service            integer null,
	buy_time              timestamp null,
	invalid_time          timestamp null,
	require_invoice       integer null,
	invoice_description   varchar(512) null,
	id_receipt            integer null,
	payment_numder        varchar(64) null,
	order_number          varchar(64) null,
	payment               float null,
	pay_method            integer null,
	id_company            integer null
)
;



alter table buy_record
	add  primary key (id)
;



create table c_repeats
(
	id_repeat             integer not null,
	repeat_type           integer null,
	end_time              date null,
	frequency             varchar(20) null
)
;



alter table c_repeats
	add  primary key (id_repeat)
;



create table c_schedule
(
	id_schedule           integer not null,
	title                 varchar(128) null,
	description           varchar(512) null,
	id_repeat             integer null,
	creator               integer null,
	creation_time         timestamp not null,
	start_time            timestamp null,
	end_time              timestamp null,
	is_wholeday           integer null,
	remind                timestamp null,
	lazy_set              timestamp null,
	schedule_type         integer null
)
;



alter table c_schedule
	add  primary key (id_schedule)
;



create table c_schedule_doc
(
	id_schedule           integer not null,
	id_document           integer not null,
	publish               integer null
)
;



alter table c_schedule_doc
	add  primary key (id_schedule,id_document)
;



create table c_schedule_watcher
(
	id_user               integer not null,
	id_schedule           integer not null,
	enabled               integer null
)
;



alter table c_schedule_watcher
	add  primary key (id_user,id_schedule)
;



create table city
(
	id_city               integer not null,
	city                  varchar(32) null,
	id_province           integer null,
	postcode              varchar(20) null
)
;



alter table city
	add  primary key (id_city)
;



create table company
(
	id_company            integer not null,
	company_name          varchar(64) not null,
	description           varchar(1024) null,
	creator               integer not null,
	creation_time         date null,
	company_type          integer null,
	modify_by             integer null,
	modify_time           date null,
	establish_person      varchar(20) null,
	establishment_time    date null,
	postcode              varchar(20) null,
	id_district           integer null,
	address               varchar(128) null,
	company_nature        integer null,
	email                 varchar(64) null,
	company_size          integer null,
	company_category      integer null,
	licence               integer null,
	verified              integer null,
	telephone             varchar(20) null,
	fax                   varchar(20) null,
	url                   varchar(128) null,
	locale                integer null,
	logo                  integer null,
	task_type             integer null,
	enabled               integer not null,
	scheme                integer null,
	used_space            float null,
	msg_used_num          integer null,
	msg_available_num     integer null,
	space                 float null
)
;



alter table company
	add  primary key (id_company)
;



create table district
(
	id_district           integer not null,
	district              varchar(32) null,
	id_city               integer null
)
;



alter table district
	add  primary key (id_district)
;



create table document
(
	id_document           integer not null,
	parent                integer null,
	creator               integer null,
	creation_time         date null,
	modify_by             integer null,
	modify_time           date null,
	relative_path         varchar(256) null,
	name                  varchar(64) null,
	file_name             varchar(256) null,
	description           varchar(256) null,
	size                  mediumint null
)
;



alter table document
	add  primary key (id_document)
;



create table feedback
(
	id                    integer not null,
	id_user               integer null,
	feedback_method       integer null,
	tel                   varchar(20) null,
	email                 varchar(128) null,
	name                  varchar(64) null,
	description           varchar(1024) null,
	status                integer null
)
;



alter table feedback
	add  primary key (id)
;



create table group_authorities
(
	id_group              integer not null,
	id_role               integer not null,
	enabled               integer null
)
;



alter table group_authorities
	add  primary key (id_group,id_role)
;



create table group_members
(
	id_group              integer not null,
	id_user               integer not null,
	id_role               integer null
)
;



alter table group_members
	add  primary key (id_group,id_user)
;



create table group_type
(
	id_group_type         integer not null,
	group_type            varchar(20) null
)
;



alter table group_type
	add  primary key (id_group_type)
;



create table groups
(
	id_group              integer not null,
	group_name            varchar(64) not null,
	description           varchar(256) null,
	creator               integer null,
	creation_time         date null,
	id_group_type         integer null,
	seq                   integer null,
	id_company            integer null
)
;



alter table groups
	add  primary key (id_group)
;



create table jms_event
(
	id_event              integer not null,
	parent                integer null,
	class                 varchar(100) null,
	name                  varchar(64) null,
	description           varchar(128) null,
	template              varchar(64) null,
	id_app                integer null
)
;



alter table jms_event
	add  primary key (id_event)
;



create table noti_method
(
	id_noti_method        integer not null,
	method                varchar(20) null
)
;



alter table noti_method
	add  primary key (id_noti_method)
;



create table notice
(
	id_notice             integer not null,
	id_user               integer null,
	title                 varchar(128) null,
	notice                varchar(4096) null,
	remark                varchar(1024) null,
	publish_time          date null
)
;



alter table notice
	add  primary key (id_notice)
;



create table notification
(
	id_noti               integer not null,
	id_event              integer null,
	id_source             integer null,
	creator               integer null,
	creation_time         timestamp null,
	id_noti_method        integer null,
	id_company            integer null
)
;



alter table notification
	add  primary key (id_noti)
;



create table p_poll
(
	id_poll               integer not null,
	title                 varchar(64) null,
	description           varchar(1024) null,
	creator               integer null,
	max_items             integer null,
	creation_time         timestamp null,
	end_time              timestamp null,
	is_anonymous          integer null,
	show_results          integer null,
	conclusion            varchar(512) null,
	status                integer null,
	url                   varchar(512) null
)
;



alter table p_poll
	add  primary key (id_poll)
;



create table p_poll_items
(
	id_item               integer not null,
	id_poll               integer null,
	seq                   integer null,
	item                  varchar(512) null,
	pic                   integer null
)
;



alter table p_poll_items
	add  primary key (id_item)
;



create table p_poll_participant
(
	id                    integer not null,
	id_poll               integer null,
	participant           integer null,
	email                 varchar(128) null,
	tel                   varchar(128) null,
	comment               varchar(1024) null,
	status                integer null
)
;



alter table p_poll_participant
	add  primary key (id)
;



create table p_vote
(
	id                    integer not null,
	id_item               integer not null,
	degree                integer null
)
;



alter table p_vote
	add  primary key (id,id_item)
;



create table province
(
	id_province           integer not null,
	province              varchar(32) null
)
;



alter table province
	add  primary key (id_province)
;



create table receipt
(
	id_receipt            integer not null,
	title                 varchar(256) null,
	invoice_code          varchar(20) null,
	invoice_no            varchar(20) null,
	issued_by             varchar(20) null,
	issued_date           date null,
	payer                 varchar(20) null,
	content               varchar(512) null,
	amount                numeric null,
	id_company            integer not null
)
;



alter table receipt
	add  primary key (id_receipt)
;



create table receiver
(
	id_receiver           integer not null,
	id_noti               integer null,
	receive_group         integer null,
	unsubscribe           integer null
)
;



alter table receiver
	add  primary key (id_receiver)
;



create table roles
(
	id_role               integer not null,
	parent                integer null,
	role                  varchar(64) not null,
	description           varchar(256) null,
	level                 integer null,
	enabled               integer null,
	id_company            integer null
)
;



alter table roles
	add  primary key (id_role)
;



create table s_attachment
(
	id                    integer null,
	name                  varchar(64) null,
	des                   varchar(1024) null,
	filename              varchar(128) null,
	org_filename          varchar(128) null,
	upload_by             integer null
)
;



alter table s_attachment
	add  primary key (id)
;



create table s_bin
(
	id_stk                integer not null,
	bin                   varchar(64) null,
	status                integer null
)
;



alter table s_bin
	add  primary key (id_stk,bin)
;



create table s_company_co
(
	id                    integer null,
	code                  varchar(20) null,
	shart_name            varchar(20) null,
	name                  varchar(64) null,
	tel                   varchar(20) null,
	fax                   varchar(20) null,
	address_act           varchar(128) null,
	address_reg           varchar(128) null,
	artificial_person     varchar(20) null,
	tax_no                integer null,
	bank                  varchar(64) null,
	bank_acc_no           integer null,
	url                   varchar(512) null,
	type                  integer null,
	country               integer null,
	level                 integer null,
	freight_term          integer null,
	payment_term          integer null,
	audit_by              integer null,
	reamrk                varchar(1024) null,
	status                integer null
)
;



alter table s_company_co
	add  primary key (id)
;



create table s_company_co_attachment
(
	id                    integer null,
	attachment            integer null,
	company_co            integer null,
	order_by              integer null
)
;



alter table s_company_co_attachment
	add  primary key (id)
;



create table s_country_dic
(
	id                    integer null,
	name                  varchar(64) null
)
;



alter table s_country_dic
	add  primary key (id)
;



create table s_do
(
	id_do                 integer null,
	do_no                 varchar(64) null,
	id_so                 integer null,
	emp_delivery          integer null,
	date_delivery         date null,
	express_company       integer null,
	express_no            integer null,
	id_company            integer null
)
;



alter table s_do
	add  primary key (id_do)
;



create table s_do_material
(
	id                    integer null,
	id_do                 integer null,
	material              integer null,
	qty_so                numeric null,
	qty_delivery          numeric null,
	qty                   numeric null,
	stk_delivery          integer null,
	status_inspection     integer null
)
;



alter table s_do_material
	add  primary key (id)
;



create table s_gender_dic
(
	id                    integer null,
	name                  varchar(20) null
)
;



alter table s_gender_dic
	add  primary key (id)
;



create table s_income
(
	id_r                  integer null,
	r_no                  varchar(20) null,
	id_po                 integer null,
	emp_receiving         integer null,
	data_receiving        date null,
	express_company       integer null,
	express_no            integer null,
	id_company            integer null
)
;



alter table s_income
	add  primary key (id_r)
;



create table s_income_material
(
	id                    integer null,
	id_r                  integer null,
	material              integer null,
	qty_po                numeric null,
	qty_received          numeric null,
	qty                   numeric null,
	stk                   integer null,
	status_inspection     integer null
)
;



alter table s_income_material
	add  primary key (id)
;



create table s_inventory
(
	id                    integer null,
	id_mat                integer null,
	id_stk                integer null,
	bin                   varchar(64) null,
	qty                   numeric null
)
;



alter table s_inventory
	add  primary key (id)
;



create table s_level_dic
(
	id                    integer null,
	name                  varchar(20) null
)
;



alter table s_level_dic
	add  primary key (id)
;



create table s_linkman
(
	id                    integer not null,
	name                  varchar(20) null,
	gender                integer null,
	position              varchar(64) null,
	phone_no              integer null,
	e_mail                varchar(64) null,
	qq                    integer null,
	we_chat               varchar(32) null,
	wangwang              varchar(32) null,
	remark                varchar(1024) null,
	status                integer null
)
;



alter table s_linkman
	add  primary key (id,name)
;



create table s_material
(
	id                    integer null,
	pno                   varchar(64) null,
	rev                   integer null,
	des                   varchar(200) null,
	type                  integer null,
	category              integer null,
	unit_inf              integer null,
	unit_pur              integer null,
	moq                   integer null,
	brand                 varchar(20) null,
	remark                varchar(1024) null,
	status                integer null,
	auto_remark           varchar(8000) null
)
;



alter table s_material
	add  primary key (id)
;



create table s_material_attachment
(
	id                    integer null,
	id_attachment         integer null,
	id_material           integer null,
	order_by              integer null
)
;



alter table s_material_attachment
	add  primary key (id)
;



create table s_material_bin
(
	id                    integer null,
	id_stk                integer null,
	bin                   varchar(64) null,
	material              integer null,
	order_by              integer null
)
;



alter table s_material_bin
	add  primary key (id)
;



create table s_material_category
(
	id                    integer null,
	parent                integer null,
	name                  varchar(128) null,
	des                   varchar(1024) null,
	order_by              integer null,
	status                integer null,
	id_company            integer null
)
;



alter table s_material_category
	add  primary key (id)
;



create table s_material_category_pic
(
	id                    integer null,
	id_material_category  integer not null,
	id_pic                integer not null,
	order_by              integer null
)
;



alter table s_material_category_pic
	add  primary key (id)
;



create table s_material_pic
(
	id                    integer null,
	material              integer null,
	id_pic                integer null,
	order_by              integer null
)
;



alter table s_material_pic
	add  primary key (id)
;



create table s_material_type_dic
(
	id                    integer null,
	name                  varchar(20) null,
	des                   varchar(128) null
)
;



alter table s_material_type_dic
	add  primary key (id)
;



create table s_mrb
(
	id_mrb                integer null,
	code_mrb              varchar(64) null,
	code_co               integer null,
	date_order            date null,
	emp_order             integer null,
	freight_term          integer null,
	payment_term          integer null,
	stk                   integer null,
	total_amount          numeric null,
	tax_rate              numeric null,
	exchange              numeric null,
	remark                varchar(1024) null,
	status                integer null,
	attachment            integer null,
	id_company            integer null
)
;



alter table s_mrb
	add  primary key (id_mrb)
;



create table s_mrb_material
(
	id                    integer null,
	id_mrb                integer null,
	line                  integer null,
	pno                   integer null,
	qty                   numeric null,
	u_price               numeric null,
	total_price           numeric null,
	remark                varchar(1024) null,
	delivery_date         date null,
	qty_received          numeric null,
	qty_rejected          numeric null,
	status                integer null
)
;



alter table s_mrb_material
	add  primary key (id)
;



create table s_mtf
(
	id_mt                 integer null,
	mt_no                 varchar(20) null,
	emp_mt                integer null,
	date_mt               date null,
	stk_from              integer null,
	stk_bin_from          varchar(64) null,
	stk_to                integer null,
	stk_bin_to            varchar(64) null,
	id_company            integer null
)
;



alter table s_mtf
	add  primary key (id_mt)
;



create table s_mtf_material
(
	id                    integer null,
	id_mt                 integer null,
	material              integer null,
	qty                   numeric null,
	qty__3805             integer null
)
;



alter table s_mtf_material
	add  primary key (id)
;



create table s_pic
(
	id                    integer null,
	org_filename          varchar(256) null,
	filename              varchar(256) null,
	upload_by             integer null,
	des                   varchar(1024) null
)
;



alter table s_pic
	add  primary key (id)
;



create table s_pic_setting
(
	id                    integer null,
	type                  varchar(20) null,
	id_company            integer null
)
;



alter table s_pic_setting
	add  primary key (id)
;



create table s_po
(
	id_po                 integer null,
	code_po               varchar(64) null,
	code_co               integer null,
	date_order            date null,
	emp_order             integer null,
	freight_term          integer null,
	payment_term          integer null,
	stk_receiving         integer null,
	total_amount          numeric null,
	tax_rate              numeric null,
	exchange              numeric null,
	remark                varchar(1024) null,
	status                integer null,
	attachment            integer null,
	id_company            integer null
)
;



alter table s_po
	add  primary key (id_po)
;



create table s_po_material
(
	id                    integer null,
	id_po                 integer null,
	line                  integer null,
	pno                   integer null,
	qty                   numeric null,
	u_price               numeric null,
	total_price           numeric null,
	remark                varchar(1024) null,
	delivery_date         date null,
	status                integer null,
	qty_peceived          numeric null
)
;



alter table s_po_material
	add  primary key (id)
;



create table s_rma
(
	id_rma                integer null,
	code_rma              varchar(64) null,
	code_co               integer null,
	date_order            date null,
	emp_oeder             integer null,
	freight_term          integer null,
	payment_term          integer null,
	stk                   integer null,
	total_amount          numeric null,
	tax_rate              numeric null,
	exchange              numeric null,
	remark                varchar(1024) null,
	status                integer null,
	attachment            integer null,
	id_company            integer null
)
;



alter table s_rma
	add  primary key (id_rma)
;



create table s_rma_material
(
	id                    integer null,
	id_rma                integer null,
	line                  integer null,
	_default_             char(18) null,
	pno                   integer null,
	qty                   numeric null,
	u_price               numeric null,
	total_price           numeric null,
	reamrk                varchar(1024) null,
	delivery_date         date null,
	qty_delivered         numeric null,
	qty_rejected          numeric null,
	status                integer null
)
;



alter table s_rma_material
	add  primary key (id)
;



create table s_so
(
	id_so                 integer null,
	code_so               varchar(64) null,
	code_co               integer null,
	date_order            date null,
	emp_order             integer null,
	freight_term          integer null,
	payment_term          integer null,
	stk                   integer null,
	total_amount          numeric null,
	tax_rate              numeric null,
	exchange              numeric null,
	remark                varchar(1024) null,
	status                integer null,
	attachment            integer null,
	id_company            integer null
)
;



alter table s_so
	add  primary key (id_so)
;



create table s_so_material
(
	id                    integer null,
	id_so                 integer null,
	line                  integer null,
	material              integer null,
	qty                   numeric null,
	u_price               numeric null,
	total_price           numeric null,
	remark                varchar(1024) null,
	date_delivery         date null,
	status                integer null,
	qty_delivery          numeric null
)
;



alter table s_so_material
	add  primary key (id)
;



create table s_status_dic
(
	id                    integer null,
	name                  varchar(64) null,
	des                   varchar(1024) null,
	source                varchar(20) null
)
;



alter table s_status_dic
	add  primary key (id)
;



create table s_stk
(
	id                    integer null,
	name                  varchar(64) null,
	des                   varchar(1024) null,
	address               varchar(256) null,
	id_user               integer null,
	status                integer null,
	id_company            integer null
)
;



alter table s_stk
	add  primary key (id)
;



create table s_term_dic
(
	id                    integer null,
	name                  varchar(20) null,
	des                   varchar(64) null,
	source                varchar(20) null
)
;



alter table s_term_dic
	add  primary key (id)
;



create table s_type_dic
(
	id                    integer null,
	name                  varchar(20) null
)
;



alter table s_type_dic
	add  primary key (id)
;



create table s_unit_dic
(
	id                    integer null,
	name                  varchar(20) null,
	des                   varchar(128) null
)
;



alter table s_unit_dic
	add  primary key (id)
;



create table service
(
	id_service            integer not null,
	id_service_type       integer null,
	description           varchar(20) null,
	payment               float null
)
;



alter table service
	add  primary key (id_service)
;



create table service_type
(
	id_service_type       integer null,
	type                  varchar(64) null,
	description           varchar(20) null
)
;



alter table service_type
	add  primary key (id_service_type)
;



create table sys_dic
(
	type                  varchar(64) not null,
	description           varchar(128) not null
)
;



alter table sys_dic
	add  primary key (type)
;



create table sys_dic_d
(
	id_dic                integer not null,
	type                  varchar(64) null,
	name                  varchar(64) not null,
	enabled               integer not null,
	description           varchar(128) not null,
	is_default            integer null
)
;



alter table sys_dic_d
	add  primary key (id_dic)
;



create table users
(
	id_user               integer not null,
	creator               integer null,
	creation_time         date null,
	username              varchar(64) null,
	mobile                varchar(64) null,
	email                 varchar(64) null,
	password              varchar(64) not null,
	idcard                varchar(18) null,
	name                  varchar(64) null,
	address               varchar(128) null,
	ext                   varchar(20) null,
	e_no                  varchar(20) null,
	school                varchar(128) null,
	grad_time             date null,
	major                 varchar(64) null,
	degree                varchar(64) null,
	emergency_hp          varchar(64) null,
	locale                integer null,
	gender                integer null,
	scheme                integer null,
	enabled               integer not null,
	status                integer null,
	description           varchar(256) null,
	birthday              date null,
	id_district           integer null,
	last_login            timestamp null,
	token                 varchar(128) null,
	user_type             integer null,
	cv                    integer null,
	pic                   integer null,
	id_company            integer null
)
;



alter table users
	add  primary key (id_user)
;



create table w_issue
(
	id_issue              integer not null,
	title                 varchar(64) null,
	description           varchar(256) null,
	creator               integer null,
	creation_time         date null,
	priority              integer null,
	source_type           integer null,
	source                integer null,
	status                integer null
)
;



alter table w_issue
	add  primary key (id_issue)
;



create table w_issue_comment
(
	id_issue_comment      integer not null,
	parent                integer null,
	creator               integer null,
	creation_time         date null,
	comment               varchar(1024) null,
	modification_time     date null,
	id_issue              integer null
)
;



alter table w_issue_comment
	add  primary key (id_issue_comment)
;



create table w_issue_doc
(
	id_issue              integer not null,
	id_document           integer not null,
	publish               integer null,
	id_issue_comment      integer null
)
;



alter table w_issue_doc
	add  primary key (id_issue,id_document)
;



create table w_project
(
	id_project            integer not null,
	parent                integer null,
	project_name          varchar(64) not null,
	description           varchar(256) null,
	project_number        varchar(64) null,
	plan_start_time       date null,
	plan_end_time         date null,
	start_time            date null,
	end_time              date null,
	leader                integer null,
	priority              integer null,
	process               integer null,
	status                integer null,
	id_company            integer null
)
;



alter table w_project
	add  primary key (id_project)
;



create table w_project_doc
(
	id_project            integer not null,
	id_document           integer not null,
	publish               integer null
)
;



alter table w_project_doc
	add  primary key (id_project,id_document)
;



create table w_project_participant
(
	id_project            integer not null,
	id_user               integer not null,
	is_charge             integer null
)
;



alter table w_project_participant
	add  primary key (id_project,id_user)
;



create table w_task
(
	id_task               integer not null,
	id_project            integer null,
	name                  varchar(64) not null,
	description           varchar(1024) null,
	creation_time         date null,
	modification_time     date null,
	plan_start_time       date not null,
	plan_end_time         date not null,
	plan_duration         integer null,
	start_time            date null,
	end_time              date null,
	duration              integer null,
	seq                   integer null,
	priority              integer null,
	creator               integer null,
	status                integer null
)
;



alter table w_task
	add  primary key (id_task)
;



create table w_task_comment
(
	id_comment            integer not null,
	parent                integer null,
	id_task               integer null,
	id_user               integer null,
	comment               varchar(1024) null,
	creation_time         date null,
	modification_time     date null
)
;



alter table w_task_comment
	add  primary key (id_comment)
;



create table w_task_doc
(
	id_task               integer not null,
	id_document           integer not null,
	publish               integer null
)
;



alter table w_task_doc
	add  primary key (id_task,id_document)
;



create table w_task_participant
(
	id_task               integer not null,
	id_user               integer not null,
	is_charge             integer null
)
;



alter table w_task_participant
	add  primary key (id_task,id_user)
;



alter table a_application
	add foreign key r_168 (id_approval_process) references a_approval_process(id_approval_process)
;


alter table a_application
	add foreign key r_169 (applicant) references users(id_user)
;


alter table a_application
	add foreign key r_170 (status) references sys_dic_d(id_dic)
;


alter table a_application
	add foreign key r_176 (previous_application) references a_application(id_application)
;



alter table a_application_phase
	add foreign key r_172 (id_application) references a_application(id_application)
;


alter table a_application_phase
	add foreign key r_173 (id_approval_phase) references a_approval_phase(id_approval_phase)
;


alter table a_application_phase
	add foreign key r_174 (status) references sys_dic_d(id_dic)
;



alter table a_approval_cc
	add foreign key r_166 (id_approval_phase) references a_approval_phase(id_approval_phase)
;


alter table a_approval_cc
	add foreign key r_167 (id_user) references users(id_user)
;


alter table a_approval_cc
	add foreign key r_180 (id_noti_method) references noti_method(id_noti_method)
;



alter table a_approval_phase
	add foreign key r_163 (id_approval_process) references a_approval_process(id_approval_process)
;


alter table a_approval_phase
	add foreign key r_164 (approver) references users(id_user)
;



alter table a_approval_process
	add foreign key r_161 (id_approval_type) references a_approval_type(id_approval_type)
;


alter table a_approval_process
	add foreign key r_162 (id_document) references document(id_document)
;



alter table a_approval_type
	add foreign key r_158 (id_company) references company(id_company)
;


alter table a_approval_type
	add foreign key r_159 (creator) references users(id_user)
;



alter table buy_record
	add foreign key r_120 (id_company) references company(id_company)
;


alter table buy_record
	add foreign key r_124 (id_receipt) references receipt(id_receipt)
;


alter table buy_record
	add foreign key r_126 (id_service) references service(id_service)
;



alter table c_repeats
	add foreign key r_154 (repeat_type) references sys_dic_d(id_dic)
;



alter table c_schedule
	add foreign key r_149 (creator) references users(id_user)
;


alter table c_schedule
	add foreign key r_150 (schedule_type) references sys_dic_d(id_dic)
;


alter table c_schedule
	add foreign key r_153 (id_repeat) references c_repeats(id_repeat)
;



alter table c_schedule_doc
	add foreign key r_156 (id_schedule) references c_schedule(id_schedule)
;


alter table c_schedule_doc
	add foreign key r_157 (id_document) references document(id_document)
;



alter table c_schedule_watcher
	add foreign key r_151 (id_user) references users(id_user)
;


alter table c_schedule_watcher
	add foreign key r_152 (id_schedule) references c_schedule(id_schedule)
;



alter table city
	add foreign key r_70 (id_province) references province(id_province)
;



alter table company
	add foreign key r_92 (scheme) references sys_dic_d(id_dic)
;


alter table company
	add foreign key r_99 (company_category) references sys_dic_d(id_dic)
;


alter table company
	add foreign key r_115 (modify_by) references users(id_user)
;



alter table company
	add foreign key r_12 (creator) references users(id_user)
;


alter table company
	add foreign key r_65 (company_type) references sys_dic_d(id_dic)
;


alter table company
	add foreign key r_67 (company_size) references sys_dic_d(id_dic)
;


alter table company
	add foreign key r_69 (company_nature) references sys_dic_d(id_dic)
;


alter table company
	add foreign key r_72 (id_district) references district(id_district)
;


alter table company
	add foreign key r_85 (logo) references document(id_document)
;


alter table company
	add foreign key r_86 (licence) references document(id_document)
;


alter table company
	add foreign key r_90 (locale) references sys_dic_d(id_dic)
;


alter table company
	add foreign key r_91 (task_type) references sys_dic_d(id_dic)
;



alter table district
	add foreign key r_71 (id_city) references city(id_city)
;



alter table document
	add foreign key r_58 (parent) references document(id_document)
;


alter table document
	add foreign key r_116 (creator) references users(id_user)
;


alter table document
	add foreign key r_117 (modify_by) references users(id_user)
;



alter table feedback
	add foreign key r_127 (id_user) references users(id_user)
;



alter table group_authorities
	add foreign key r_6 (id_group) references groups(id_group)
;


alter table group_authorities
	add foreign key r_7 (id_role) references roles(id_role)
;



alter table group_members
	add foreign key r_1 (id_user) references users(id_user)
;


alter table group_members
	add foreign key r_2 (id_group) references groups(id_group)
;


alter table group_members
	add foreign key r_3 (id_role) references roles(id_role)
;



alter table groups
	add foreign key r_33 (creator) references users(id_user)
;


alter table groups
	add foreign key r_110 (id_group_type) references group_type(id_group_type)
;


alter table groups
	add foreign key r_111 (id_company) references company(id_company)
;



alter table jms_event
	add foreign key r_179 (id_app) references apps(id_app)
;


alter table jms_event
	add foreign key r_181 (parent) references jms_event(id_event)
;



alter table notice
	add foreign key r_135 (id_user) references users(id_user)
;



alter table notification
	add foreign key r_184 (creator) references users(id_user)
;


alter table notification
	add foreign key r_185 (id_event) references jms_event(id_event)
;


alter table notification
	add foreign key r_186 (id_noti_method) references noti_method(id_noti_method)
;


alter table notification
	add foreign key r_187 (id_company) references company(id_company)
;



alter table p_poll
	add foreign key r_137 (creator) references users(id_user)
;


alter table p_poll
	add foreign key r_140 (status) references sys_dic_d(id_dic)
;



alter table p_poll_items
	add foreign key r_138 (id_poll) references p_poll(id_poll)
;


alter table p_poll_items
	add foreign key r_139 (pic) references document(id_document)
;



alter table p_poll_participant
	add foreign key r_141 (id_poll) references p_poll(id_poll)
;


alter table p_poll_participant
	add foreign key r_144 (participant) references users(id_user)
;


alter table p_poll_participant
	add foreign key r_145 (status) references sys_dic_d(id_dic)
;



alter table p_vote
	add foreign key r_146 (id) references p_poll_participant(id)
;


alter table p_vote
	add foreign key r_147 (id_item) references p_poll_items(id_item)
;


alter table p_vote
	add foreign key r_148 (degree) references sys_dic_d(id_dic)
;



alter table receipt
	add foreign key r_123 (id_company) references company(id_company)
;



alter table receiver
	add foreign key r_188 (id_noti) references notification(id_noti)
;


alter table receiver
	add foreign key r_190 (receive_group) references groups(id_group)
;



alter table roles
	add foreign key r_34 (id_company) references company(id_company)
;


alter table roles
	add foreign key r_84 (parent) references roles(id_role)
;



alter table s_attachment
	add foreign key r_223 (upload_by) references users(id_user)
;



alter table s_bin
	add foreign key r_208 (status) references s_status_dic(id)
;


alter table s_bin
	add foreign key r_211 (id_stk) references s_stk(id)
;



alter table s_company_co
	add foreign key r_226 (status) references s_status_dic(id)
;


alter table s_company_co
	add foreign key r_230 (audit_by) references users(id_user)
;


alter table s_company_co
	add foreign key r_285 (freight_term) references s_term_dic(id)
;


alter table s_company_co
	add foreign key r_286 (payment_term) references s_term_dic(id)
;


alter table s_company_co
	add foreign key r_287 (country) references s_country_dic(id)
;


alter table s_company_co
	add foreign key r_288 (type) references s_type_dic(id)
;


alter table s_company_co
	add foreign key r_289 (level) references s_level_dic(id)
;



alter table s_company_co_attachment
	add foreign key r_290 (attachment) references s_attachment(id)
;


alter table s_company_co_attachment
	add foreign key r_291 (company_co) references s_company_co(id)
;



alter table s_do
	add foreign key r_256 (id_so) references s_so(id_so)
;


alter table s_do
	add foreign key r_257 (emp_delivery) references users(id_user)
;


alter table s_do
	add foreign key r_258 (express_company) references s_company_co(id)
;


alter table s_do
	add foreign key r_259 (id_company) references company(id_company)
;



alter table s_do_material
	add foreign key r_265 (id_do) references s_do(id_do)
;


alter table s_do_material
	add foreign key r_266 (material) references s_material(id)
;


alter table s_do_material
	add foreign key r_267 (stk_delivery) references s_stk(id)
;


alter table s_do_material
	add foreign key r_268 (status_inspection) references s_status_dic(id)
;



alter table s_income
	add foreign key r_241 (id_po) references s_po(id_po)
;


alter table s_income
	add foreign key r_242 (emp_receiving) references users(id_user)
;


alter table s_income
	add foreign key r_243 (express_company) references s_company_co(id)
;


alter table s_income
	add foreign key r_244 (id_company) references company(id_company)
;



alter table s_income_material
	add foreign key r_248 (stk) references s_stk(id)
;


alter table s_income_material
	add foreign key r_249 (status_inspection) references s_status_dic(id)
;


alter table s_income_material
	add foreign key r_297 (material) references s_material(id)
;


alter table s_income_material
	add foreign key r_298 (id_r) references s_income(id_r)
;



alter table s_inventory
	add foreign key r_263 (id_stk,bin) references s_bin(id_stk,bin)
;


alter table s_inventory
	add foreign key r_264 (id_mat) references s_material(id)
;



alter table s_linkman
	add foreign key r_227 (id) references s_company_co(id)
;


alter table s_linkman
	add foreign key r_233 (status) references s_status_dic(id)
;


alter table s_linkman
	add foreign key r_284 (gender) references s_gender_dic(id)
;



alter table s_material
	add foreign key r_213 (category) references s_material_category(id)
;


alter table s_material
	add foreign key r_214 (type) references s_material_type_dic(id)
;


alter table s_material
	add foreign key r_215 (unit_inf) references s_unit_dic(id)
;


alter table s_material
	add foreign key r_216 (unit_pur) references s_unit_dic(id)
;


alter table s_material
	add foreign key r_217 (status) references s_status_dic(id)
;



alter table s_material_attachment
	add foreign key r_224 (id_attachment) references s_attachment(id)
;


alter table s_material_attachment
	add foreign key r_225 (id_material) references s_material(id)
;



alter table s_material_bin
	add foreign key r_293 (id_stk,bin) references s_bin(id_stk,bin)
;


alter table s_material_bin
	add foreign key r_294 (material) references s_material(id)
;



alter table s_material_category
	add foreign key r_191 (parent) references s_material_category(id)
;


alter table s_material_category
	add foreign key r_192 (id_company) references company(id_company)
;


alter table s_material_category
	add foreign key r_195 (status) references s_status_dic(id)
;



alter table s_material_category_pic
	add foreign key r_200 (id_material_category) references s_material_category(id)
;


alter table s_material_category_pic
	add foreign key r_201 (id_pic) references s_pic(id)
;



alter table s_material_pic
	add foreign key r_221 (material) references s_material(id)
;


alter table s_material_pic
	add foreign key r_222 (id_pic) references s_pic(id)
;



alter table s_mrb
	add foreign key r_299 (code_co) references s_company_co(id)
;


alter table s_mrb
	add foreign key r_300 (emp_order) references users(id_user)
;


alter table s_mrb
	add foreign key r_301 (freight_term) references s_term_dic(id)
;


alter table s_mrb
	add foreign key r_302 (payment_term) references s_term_dic(id)
;


alter table s_mrb
	add foreign key r_303 (stk) references s_stk(id)
;


alter table s_mrb
	add foreign key r_304 (status) references s_status_dic(id)
;


alter table s_mrb
	add foreign key r_305 (attachment) references s_attachment(id)
;


alter table s_mrb
	add foreign key r_306 (id_company) references company(id_company)
;



alter table s_mrb_material
	add foreign key r_307 (id_mrb) references s_mrb(id_mrb)
;


alter table s_mrb_material
	add foreign key r_308 (pno) references s_material(id)
;


alter table s_mrb_material
	add foreign key r_309 (status) references s_status_dic(id)
;



alter table s_mtf
	add foreign key r_250 (emp_mt) references users(id_user)
;


alter table s_mtf
	add foreign key r_252 (stk_from,stk_bin_from) references s_bin(id_stk,bin)
;


alter table s_mtf
	add foreign key r_254 (stk_to,stk_bin_to) references s_bin(id_stk,bin)
;


alter table s_mtf
	add foreign key r_255 (id_company) references company(id_company)
;



alter table s_mtf_material
	add foreign key r_280 (material) references s_material(id)
;


alter table s_mtf_material
	add foreign key r_281 (id_mt) references s_mtf(id_mt)
;



alter table s_pic
	add foreign key r_198 (upload_by) references users(id_user)
;



alter table s_pic_setting
	add foreign key r_197 (id_company) references company(id_company)
;



alter table s_po
	add foreign key r_229 (code_co) references s_company_co(id)
;


alter table s_po
	add foreign key r_232 (emp_order) references users(id_user)
;


alter table s_po
	add foreign key r_234 (stk_receiving) references s_stk(id)
;


alter table s_po
	add foreign key r_235 (status) references s_status_dic(id)
;


alter table s_po
	add foreign key r_236 (attachment) references s_attachment(id)
;


alter table s_po
	add foreign key r_238 (id_company) references company(id_company)
;


alter table s_po
	add foreign key r_282 (freight_term) references s_term_dic(id)
;


alter table s_po
	add foreign key r_283 (payment_term) references s_term_dic(id)
;



alter table s_po_material
	add foreign key r_240 (status) references s_status_dic(id)
;


alter table s_po_material
	add foreign key r_295 (pno) references s_material(id)
;


alter table s_po_material
	add foreign key r_296 (id_po) references s_po(id_po)
;



alter table s_rma
	add foreign key r_310 (code_co) references s_company_co(id)
;


alter table s_rma
	add foreign key r_311 (emp_oeder) references users(id_user)
;


alter table s_rma
	add foreign key r_312 (freight_term) references s_term_dic(id)
;


alter table s_rma
	add foreign key r_313 (payment_term) references s_term_dic(id)
;


alter table s_rma
	add foreign key r_314 (stk) references s_stk(id)
;


alter table s_rma
	add foreign key r_315 (status) references s_status_dic(id)
;


alter table s_rma
	add foreign key r_316 (attachment) references s_attachment(id)
;


alter table s_rma
	add foreign key r_317 (id_company) references company(id_company)
;



alter table s_rma_material
	add foreign key r_318 (id_rma) references s_rma(id_rma)
;


alter table s_rma_material
	add foreign key r_319 (pno) references s_material(id)
;


alter table s_rma_material
	add foreign key r_320 (status) references s_status_dic(id)
;



alter table s_so
	add foreign key r_269 (code_co) references s_company_co(id)
;


alter table s_so
	add foreign key r_270 (emp_order) references users(id_user)
;


alter table s_so
	add foreign key r_271 (stk) references s_stk(id)
;


alter table s_so
	add foreign key r_272 (status) references s_status_dic(id)
;


alter table s_so
	add foreign key r_273 (attachment) references s_attachment(id)
;


alter table s_so
	add foreign key r_274 (id_company) references company(id_company)
;


alter table s_so
	add foreign key r_275 (freight_term) references s_term_dic(id)
;


alter table s_so
	add foreign key r_276 (payment_term) references s_term_dic(id)
;



alter table s_so_material
	add foreign key r_277 (id_so) references s_so(id_so)
;


alter table s_so_material
	add foreign key r_278 (material) references s_material(id)
;


alter table s_so_material
	add foreign key r_279 (status) references s_status_dic(id)
;



alter table s_stk
	add foreign key r_203 (status) references s_status_dic(id)
;


alter table s_stk
	add foreign key r_206 (id_company) references company(id_company)
;


alter table s_stk
	add foreign key r_207 (id_user) references users(id_user)
;



alter table service
	add foreign key r_125 (id_service_type) references service_type(id_service_type)
;



alter table sys_dic_d
	add foreign key r_64 (type) references sys_dic(type)
;



alter table users
	add foreign key r_41 (cv) references document(id_document)
;


alter table users
	add foreign key r_61 (id_company) references company(id_company)
;


alter table users
	add foreign key r_74 (creator) references users(id_user)
;


alter table users
	add foreign key r_87 (locale) references sys_dic_d(id_dic)
;


alter table users
	add foreign key r_88 (gender) references sys_dic_d(id_dic)
;


alter table users
	add foreign key r_89 (scheme) references sys_dic_d(id_dic)
;


alter table users
	add foreign key r_93 (status) references sys_dic_d(id_dic)
;


alter table users
	add foreign key r_94 (id_district) references district(id_district)
;


alter table users
	add foreign key r_182 (pic) references document(id_document)
;



alter table w_issue
	add foreign key r_95 (priority) references sys_dic_d(id_dic)
;


alter table w_issue
	add foreign key r_96 (status) references sys_dic_d(id_dic)
;


alter table w_issue
	add foreign key r_132 (creator) references users(id_user)
;


alter table w_issue
	add foreign key r_171 (source_type) references sys_dic_d(id_dic)
;



alter table w_issue_comment
	add foreign key r_80 (parent) references w_issue_comment(id_issue_comment)
;


alter table w_issue_comment
	add foreign key r_81 (id_issue) references w_issue(id_issue)
;


alter table w_issue_comment
	add foreign key r_130 (creator) references users(id_user)
;



alter table w_issue_doc
	add foreign key r_82 (id_issue) references w_issue(id_issue)
;


alter table w_issue_doc
	add foreign key r_83 (id_document) references document(id_document)
;


alter table w_issue_doc
	add foreign key r_131 (id_issue_comment) references w_issue_comment(id_issue_comment)
;



alter table w_project
	add foreign key r_26 (id_company) references company(id_company)
;


alter table w_project
	add foreign key r_77 (leader) references users(id_user)
;


alter table w_project
	add foreign key r_97 (priority) references sys_dic_d(id_dic)
;


alter table w_project
	add foreign key r_118 (parent) references w_project(id_project)
;


alter table w_project
	add foreign key r_119 (status) references sys_dic_d(id_dic)
;



alter table w_project_doc
	add foreign key r_56 (id_project) references w_project(id_project)
;


alter table w_project_doc
	add foreign key r_57 (id_document) references document(id_document)
;



alter table w_project_participant
	add foreign key r_21 (id_project) references w_project(id_project)
;


alter table w_project_participant
	add foreign key r_22 (id_user) references users(id_user)
;



alter table w_task
	add foreign key r_29 (id_project) references w_project(id_project)
;


alter table w_task
	add foreign key r_100 (priority) references sys_dic_d(id_dic)
;


alter table w_task
	add foreign key r_101 (status) references sys_dic_d(id_dic)
;


alter table w_task
	add foreign key r_129 (creator) references users(id_user)
;



alter table w_task_comment
	add foreign key r_46 (id_task) references w_task(id_task)
;


alter table w_task_comment
	add foreign key r_47 (id_user) references users(id_user)
;


alter table w_task_comment
	add foreign key r_48 (parent) references w_task_comment(id_comment)
;



alter table w_task_doc
	add foreign key r_75 (id_task) references w_task(id_task)
;


alter table w_task_doc
	add foreign key r_76 (id_document) references document(id_document)
;



alter table w_task_participant
	add foreign key r_133 (id_task) references w_task(id_task)
;


alter table w_task_participant
	add foreign key r_134 (id_user) references users(id_user)
;

