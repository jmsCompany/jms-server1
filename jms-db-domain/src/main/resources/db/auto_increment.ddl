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

alter table `s_bin` 
modify `id_bin` int(11) not null auto_increment;

alter table `s_company_co` 
modify `id` int(11) not null auto_increment;

alter table `s_company_co_attachment` 
modify `id` int(11) not null auto_increment;

alter table `s_country_dic` 
modify `id` int(11) not null auto_increment;

alter table `s_currency_type` 
modify `id_currency_type` int(11) not null auto_increment;

alter table `s_gender_dic` 
modify `id` int(11) not null auto_increment;

alter table `s_inventory` 
modify `id_inv` int(11) not null auto_increment;

alter table `s_level_dic` 
modify `id` int(11) not null auto_increment;

alter table `s_linkman` 
modify `id_linkman` int(11) not null auto_increment;

alter table `s_material` 
modify `id_material` int(11) not null auto_increment;

alter table `s_material_attachment` 
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
modify `id_mtf_material` int(11) not null auto_increment;

alter table `s_mtf_residual` 
modify `id_mtf_residual` int(11) not null auto_increment;

alter table `s_mtf_type_dic` 
modify `id_mtf_type` int(11) not null auto_increment;

alter table `s_pic` 
modify `id` int(11) not null auto_increment;

alter table `s_pic_setting` 
modify `id` int(11) not null auto_increment;

alter table `s_po` 
modify `id_po` int(11) not null auto_increment;

alter table `s_po_material` 
modify `id_po_material` int(11) not null auto_increment;

alter table `s_po_type` 
modify `id_po_type_dic` int(11) not null auto_increment;

alter table `s_so` 
modify `id_so` int(11) not null auto_increment;

alter table `s_status_dic` 
modify `id` int(11) not null auto_increment;

alter table `s_stk` 
modify `id` int(11) not null auto_increment;

alter table `s_stk_type_dic` 
modify `id_stk_type` int(11) not null auto_increment;

alter table `s_term_dic` 
modify `id` int(11) not null auto_increment;

alter table `s_type_dic` 
modify `id` int(11) not null auto_increment;

alter table `s_unit_dic` 
modify `id` int(11) not null auto_increment;

alter table `s_yes_or_no_dic` 
modify `id` int(11) not null auto_increment;


alter table `p_act_stops` 
modify `id_act_stops` int(11) not null auto_increment;

alter table `p_actual_setup` 
modify `id_actual_setup` int(11) not null auto_increment;

alter table `p_att_draw` 
modify `id_att_draw` int(11) not null auto_increment;

alter table `p_bom` 
modify `id_bom` int(11) not null auto_increment;

alter table `p_bom_label` 
modify `id_bom_label` int(11) not null auto_increment;

alter table `p_c_pp` 
modify `id_c_pp` int(11) not null auto_increment;

alter table `p_check_plan` 
modify `id_check` int(11) not null auto_increment;

alter table `p_check_time` 
modify `id_check_time` int(11) not null auto_increment;

alter table `p_draw` 
modify `id_draw` int(11) not null auto_increment;

alter table `p_line` 
modify `id_pline` int(11) not null auto_increment;

alter table `p_mr` 
modify `id_mr` int(11) not null auto_increment;

alter table `p_routine` 
modify `id_routine` int(11) not null auto_increment;

alter table `p_routine_d` 
modify `id_routine_d` int(11) not null auto_increment;

alter table `p_routine_d_att` 
modify `id_routine_d_att` int(11) not null auto_increment;

alter table `p_routine_d_category` 
modify `id` int(11) not null auto_increment;

alter table `p_s_pp` 
modify `id_s_pp` int(11) not null auto_increment;

alter table `p_shift_plan` 
modify `id_shift_plan` int(11) not null auto_increment;

alter table `p_shift_plan_d` 
modify `id_shift_d` int(11) not null auto_increment;

alter table `p_status_dic` 
modify `id_pstatus` int(11) not null auto_increment;

alter table `p_stops_code` 
modify `id_stops_code` int(11) not null auto_increment;

alter table `p_stops_plan` 
modify `id_stops_plan` int(11) not null auto_increment;

alter table `p_sub_code` 
modify `id_sub_code` int(11) not null auto_increment;

alter table `p_unplanned_stops` 
modify `id_unplanned_stops` int(11) not null auto_increment;

alter table `p_wip` 
modify `id_wip` int(11) not null auto_increment;

alter table `p_wo` 
modify `id_wo` int(11) not null auto_increment;

alter table `p_wo_bom` 
modify `id_wo_bom` int(11) not null auto_increment;

alter table `p_wo_route` 
modify `id_wo_route` int(11) not null auto_increment;

alter table `p_work_center` 
modify `id_wc` int(11) not null auto_increment;


alter table `e_category_item` 
modify `id_category_item` int(11) not null auto_increment;

alter table `e_check_item_dic` 
modify `id_check_item` int(11) not null auto_increment;

alter table `e_ehs` 
modify `id_ehs` int(11) not null auto_increment;

alter table `e_ehs_d` 
modify `id_ehs_d` int(11) not null auto_increment;

alter table `e_level_dic` 
modify `id_level` int(11) not null auto_increment;

alter table `e_result` 
modify `id_result` int(11) not null auto_increment;

alter table `e_status` 
modify `id_estatus` int(11) not null auto_increment;

alter table `e_type` 
modify `id_type` int(11) not null auto_increment;

alter table `e_work_category_dic` 
modify `id_work_category` int(11) not null auto_increment;


alter table `f_cost_center` 
modify `id_cost_center` int(11) not null auto_increment;


alter table `m_dept` 
modify `id_dept` int(11) not null auto_increment;

alter table `m_history_part` 
modify `id_history_part` int(11) not null auto_increment;

alter table `m_machine` 
modify `id_machine` int(11) not null auto_increment;

alter table `m_machine_group` 
modify `id_group` int(11) not null auto_increment;

alter table `m_main_cycle` 
modify `id_main_cycle` int(11) not null auto_increment;

alter table `m_main_item` 
modify `id_main_item` int(11) not null auto_increment;

alter table `m_main_record` 
modify `id_main_record` int(11) not null auto_increment;

alter table `m_remark` 
modify `id_remark` int(11) not null auto_increment;

alter table `m_repair_history` 
modify `id_repair_history` int(11) not null auto_increment;

alter table `m_result` 
modify `id_result` int(11) not null auto_increment;

alter table `m_spare_part` 
modify `id_part` int(11) not null auto_increment;

alter table `m_status_dic` 
modify `id_mstatus_dic` int(11) not null auto_increment;


alter table `q_att` 
modify `id_q_att` int(11) not null auto_increment;

alter table `q_att_type` 
modify `id_att_type` int(11) not null auto_increment;

alter table `q_check_list` 
modify `id_check_list` int(11) not null auto_increment;

alter table `q_conclusion_dic` 
modify `id_conclusion` int(11) not null auto_increment;

alter table `q_dimension_report` 
modify `id_report` int(11) not null auto_increment;

alter table `q_disposal_type` 
modify `id_disposal_type` int(11) not null auto_increment;

alter table `q_fai_in_final` 
modify `id_fai_in_final` int(11) not null auto_increment;

alter table `q_fai_pulley_d` 
modify `id_fai_pulley_d` int(11) not null auto_increment;

alter table `q_item_type` 
modify `id_item_type` int(11) not null auto_increment;

alter table `q_location_dic` 
modify `id_location` int(11) not null auto_increment;

alter table `q_ncp` 
modify `id_ncp` int(11) not null auto_increment;

alter table `q_ncp_disposal` 
modify `id_ncp_disposal` int(11) not null auto_increment;

alter table `q_ncr` 
modify `id_ncr` int(11) not null auto_increment;

alter table `q_ncr_pic` 
modify `id_ncr_pic` int(11) not null auto_increment;

alter table `q_ng_mode` 
modify `id_ng_mode` int(11) not null auto_increment;

alter table `q_report_record` 
modify `id_report_record` int(11) not null auto_increment;

alter table `q_report_type` 
modify `id_report_type` int(11) not null auto_increment;

alter table `q_roller_final_d` 
modify `id_roller_final_d` int(11) not null auto_increment;

alter table `q_status_dic` 
modify `id_qstatus` int(11) not null auto_increment;

alter table `q_tester` 
modify `id_tester` int(11) not null auto_increment;

alter table `q_un_roller_d` 
modify `id_un_roller_d` int(11) not null auto_increment;

set foreign_key_checks =1;
