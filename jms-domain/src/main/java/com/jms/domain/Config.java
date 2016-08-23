package com.jms.domain;

public class Config {
	public static final String companySize = "CompanySize";
	public static final String companySizeDis = "公司规模";

	public static final String companyType = "CompanyType";
	public static final String companyTypeDis = "行业";

	public static final String companyNature = "CompanyNature";
	public static final String companyNatureDis = "行业性质";
	
	public static final String companyCatergory = "companyCatergory";
	public static final String companyCatergoryDis = "公司类型";
	public static final String degree = "Degree";
	public static final String degreeDis = "学历";

	public static final String lang = "Language";
	public static final String langDis = "语言";
	
	public static final String taskType = "taskType";
	public static final String taskTypeDis = "任务类型";
	
	
	public static final String colorType = "Color";
	public static final String colorTypeDis = "颜色";
	
	
	public static final String gradeType = "Grade";
	public static final String gradeTypeDis = "级别";
	
	public static final String genderType = "Gender";
	public static final String genderTypeDis = "性别";
	
	public static final String employeeStatus = "employeeStatus";
	public static final String employeeStatusDis = "员工状态";
	
	public static final String generalStatus = "generalStatus";
	public static final String generalStatusDis = "一般状态";
	
	
	public static final String projectStatus = "projectStatus";
	public static final String projectStatusDis = "项目状态";
	

	public static final String taskStatus = "taskStatus";
	public static final String taskStatusDis = "任务状态";
	
	public static final String yesOrNo = "yesOrNo";
	public static final String yesOrNoDis = "是否";
	
	public static final String logoRelativePath = "C:/jms_file/logo/";
	public static final String licenseRelativePath = "C:/jms_file/license/";
	public static final String docsRelativePath = "C:/jms_file/docs/";
	public static final String cvRelativePath = "C:/jms_file/cv/";
	public static final String picRelativePath = "C:/jms_file/pic/";
	
	//仓库类别
	
	public static final String[] stkTypes = {"原材料仓","车间","成品仓","来料检验仓","原料退货仓","不合格品仓","成品退货仓","帐号","其它"};

	public static final String[] stkTypeCodes = {"RM","WIP","FG","IQC","RTV","MRB","MRA","ACC","OTC"};
	
	//合作公司类型
	public static final String[] sTypes = {"供应商","客户","二者都是","其它"};
	
	//合作公司级别
     public static final String[] sLevels = {"A","B","C"};
 	//物流流转类型
     public static final String[] sMtfTypes = {"来料清单","采购退货","手动流转","工单流转","出货","销售退货","发料","检验入库"};
     
     
 	//时间单位
     public static final String[] puTimes = {"分钟","小时","天"};
     
   //时间单位
     public static final String[] workCategories = {"车","铣","钳"};
     
     
     //非计划停机
     public static final String[] subCodes = {"设备","缺料","工人","质量","工程","其它"};
     
     //Qtester type
     public static final String[] testerTypes = {"作业员","检查员"};
     
     //Item type
     public static final String[] itemTypes = {"作业","检查"};
     
     //设备保养
     public static final String[] mainCycles = {"日保养","周保养", "月保养", "季度保养","半年保养", "年度保养"};
     
     //保养类型
     public static final String[] depts = {"生产部","设备部"};
     
     
     
     //保养类型
     public static final String[] mresults = {"是","否"};


}
