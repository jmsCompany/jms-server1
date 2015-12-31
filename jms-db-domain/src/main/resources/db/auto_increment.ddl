set foreign_key_checks =0;



alter table `a_application` 
modify `id_application` int(11) not null auto_increment;

alter table `a_approval_phase` 
modify `id_approval_phase` int(11) not null auto_increment;

alter table `a_approval_process` 
modify `id_approval_process` int(11) not null auto_increment;

alter table `a_approval_type` 
modify `id_approval_type` int(11) not null auto_increment;

alter table `apps` 
modify `id_app` int(11) not null auto_increment;

alter table `buy_record` 
modify `id` int(11) not null auto_increment;

alter table `company` 
modify `id_company` int(11) not null auto_increment;

alter table `document` 
modify `id_document` int(11) not null auto_increment;

alter table `feedback` 
modify `id` int(11) not null auto_increment;

alter table `group_type` 
modify `id_group_type` int(11) not null auto_increment;

alter table `groups` 
modify `id_group` int(11) not null auto_increment;

alter table `w_issue` 
modify `id_issue` int(11) not null auto_increment;

alter table `w_issue_comment` 
modify `id_issue_comment` int(11) not null auto_increment;

alter table `jms_event` 
modify `id_event` int(11) not null auto_increment;

alter table `notification` 
modify `id_noti` int(11) not null auto_increment;

alter table `noti_method` 
modify `id_noti_method` int(11) not null auto_increment;

alter table `receiver` 
modify `id_receiver` int(11) not null auto_increment;


alter table `p_poll` 
modify `id_poll` int(11) not null auto_increment;


alter table `p_poll_items` 
modify `id_item` int(11) not null auto_increment;

alter table `w_project` 
modify `id_project` int(11) not null auto_increment;

alter table `receipt` 
modify `id_receipt` int(11) not null auto_increment;

alter table `c_repeats` 
modify `id_repeat` int(11) not null auto_increment;

alter table `roles` 
modify `id_role` int(11) not null auto_increment;

alter table `c_schedule` 
modify `id_schedule` int(11) not null auto_increment;


alter table `service` 
modify `id_service` int(11) not null auto_increment;

alter table `service_type` 
modify `id_service_type` int(11) not null auto_increment;


alter table `sys_dic_d` 
modify `id_dic` int(11) not null auto_increment;

alter table `w_task` 
modify `id_task` int(11) not null auto_increment;

alter table `w_task_comment` 
modify `id_comment` int(11) not null auto_increment;


alter table `users` 
modify `id_user` int(11) not null auto_increment;


alter table `s_attachment` 
modify `id` int(11) not null auto_increment;


alter table `s_company_co` 
modify `id` int(11) not null auto_increment;


alter table `s_company_co_attachment` 
modify `id` int(11) not null auto_increment;


alter table `s_country_dic` 
modify `id` int(11) not null auto_increment;


alter table `s_do` 
modify `id_do` int(11) not null auto_increment;


alter table `s_do_material` 
modify `id` int(11) not null auto_increment;


alter table `s_gender_dic` 
modify `id` int(11) not null auto_increment;


alter table `s_income` 
modify `id_r` int(11) not null auto_increment;


alter table `s_income_material` 
modify `id` int(11) not null auto_increment;


alter table `s_inventory` 
modify `id` int(11) not null auto_increment;


alter table `s_level_dic` 
modify `id` int(11) not null auto_increment;


alter table `s_linkman` 
modify `id` int(11) not null auto_increment;


alter table `s_material` 
modify `id` int(11) not null auto_increment;


alter table `s_material_attachment` 
modify `id` int(11) not null auto_increment;


alter table `s_material_bin` 
modify `id` int(11) not null auto_increment;


alter table `s_material_category` 
modify `id` int(11) not null auto_increment;


alter table `s_material_category_pic` 
modify `id` int(11) not null auto_increment;


alter table `s_material_pic` 
modify `id` int(11) not null auto_increment;


alter table `s_material_type_dic` 
modify `id` int(11) not null auto_increment;


alter table `s_mtf` 
modify `id_mt` int(11) not null auto_increment;


alter table `s_mtf_material` 
modify `id` int(11) not null auto_increment;


alter table `s_pic` 
modify `id` int(11) not null auto_increment;


alter table `s_pic_setting` 
modify `id` int(11) not null auto_increment;


alter table `s_po` 
modify `id_po` int(11) not null auto_increment;


alter table `s_po_material` 
modify `id` int(11) not null auto_increment;


alter table `s_so` 
modify `id_so` int(11) not null auto_increment;


alter table `s_so_material` 
modify `id` int(11) not null auto_increment;


alter table `s_status_dic` 
modify `id` int(11) not null auto_increment;


alter table `s_stk` 
modify `id` int(11) not null auto_increment;


alter table `s_term_dic` 
modify `id` int(11) not null auto_increment;


alter table `s_type_dic` 
modify `id` int(11) not null auto_increment;


alter table `s_unit_dic` 
modify `id` int(11) not null auto_increment;


alter table `s_mrb` 
modify `id_mrb` int(11) not null auto_increment;


alter table `s_mrb_material` 
modify `id` int(11) not null auto_increment;


alter table `s_rma` 
modify `id_rma` int(11) not null auto_increment;


alter table `s_rma_material` 
modify `id` int(11) not null auto_increment;

set foreign_key_checks =1;
