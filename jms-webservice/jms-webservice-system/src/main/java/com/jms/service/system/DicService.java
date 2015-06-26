package com.jms.service.system;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.Config;
import com.jms.domain.db.SysDic;
import com.jms.domain.db.SysDicD;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.repositories.system.SysDicRepository;

@Service
@Transactional
public class DicService {

	private static final Logger logger = LogManager.getLogger(DicService.class
			.getCanonicalName());
	@Autowired
	private SysDicRepository sysDicRepository;
	@Autowired
	private SysDicDRepository sysDicDRepository;
	
	public List<SysDicD> getSysDicDsByDic(String type) {
		
		return sysDicDRepository.findDicsByType(type);
		
	}

	public void loadDics() {

		SysDic companySizeDic = new SysDic();
		companySizeDic.setType(Config.companySize);
		companySizeDic.setDescription(Config.companySizeDis);
		sysDicRepository.save(companySizeDic);

		String[] companySizeArray = new String[] { "少于50人", "50-150人",
				"150-500人", "500-1000人", "1000-5000人", "5000-10000人",
				"10000人以上" };
		for (String size : companySizeArray) {
			SysDicD s = new SysDicD();
			s.setName(size);
			s.setDescription(size);
			s.setEnabled(1l);
			s.setSysDic(companySizeDic);
			sysDicDRepository.save(s);
		}

		SysDic degreeDic = new SysDic();
		degreeDic.setType(Config.degree);
		degreeDic.setDescription(Config.degreeDis);
		sysDicRepository.save(degreeDic);

		String[] degreeArray = new String[] { "初中", "高中", "中技", "中专", "大专",
				"本科", "硕士", "博士", "其它" };
		for (String degree : degreeArray) {
			SysDicD s = new SysDicD();
			s.setName(degree);
			s.setDescription(degree);
			s.setEnabled(1l);
			s.setSysDic(degreeDic);
			sysDicDRepository.save(s);
		}

		SysDic typeDic = new SysDic();
		typeDic.setType(Config.companyType);
		typeDic.setDescription(Config.companyTypeDis);
		sysDicRepository.save(typeDic);

		String[] typeArray = new String[] { "计算机/互联网/通信/电子", "会计/金融/银行/保险",
				"贸易/消费/制造/营运", "制药/医疗", "广告/媒体", "房地产/建筑", "专业服务/教育/培训", "服务业",
				"物流/运输", "能源/原材料", "政府/非营利机构/其它" };
		for (String type : typeArray) {
			SysDicD s = new SysDicD();
			s.setName(type);
			s.setDescription(type);
			s.setEnabled(1l);
			s.setSysDic(typeDic);
			sysDicDRepository.save(s);
		}

		SysDic natureDic = new SysDic();
		natureDic.setType(Config.companyNature);
		natureDic.setDescription(Config.companyNatureDis);
		sysDicRepository.save(natureDic);

		String[] natureArray = new String[] { "外资（欧美）", "外资（非欧美）", "合资", "国企",
				"民营公司", "国内上市公司", "外企代表处", "政府机关", "事业单位", "非营利机构", "其它性质" };
		for (String nature : natureArray) {
			SysDicD s = new SysDicD();
			s.setName(nature);
			s.setDescription(nature);
			s.setEnabled(1l);
			s.setSysDic(natureDic);
			sysDicDRepository.save(s);
		}

		SysDic langDic = new SysDic();
		langDic.setType(Config.lang);
		langDic.setDescription(Config.langDis);
		sysDicRepository.save(langDic);

		
		
		
		SysDicD s = new SysDicD();
		s.setName("zh_CN");
		s.setDescription("汉语，简体中文");
		s.setEnabled(1l);
		s.setSysDic(langDic);
		sysDicDRepository.save(s);
		
		SysDicD s0 = new SysDicD();
		s0.setName("zh_TW");
		s0.setDescription("汉语，繁体中文");
		s0.setEnabled(1l);
		s0.setSysDic(langDic);
		sysDicDRepository.save(s0);
		
		
		SysDicD s1 = new SysDicD();
		s1.setName("en_US");
		s1.setDescription("英语");
		s1.setEnabled(1l);
		s1.setSysDic(langDic);
		sysDicDRepository.save(s1);
		
		
		SysDic cataDic = new SysDic();
		cataDic.setType(Config.companyCatergory);
		cataDic.setDescription(Config.companyCatergoryDis);
		sysDicRepository.save(cataDic);

		SysDicD s3 = new SysDicD();
		s3.setName("NORMAL_COMPANY");
		s3.setDescription("正规企业");
		s3.setEnabled(1l);
		s3.setSysDic(cataDic);
		sysDicDRepository.save(s3);
		
		SysDicD s4 = new SysDicD();
		s4.setName("SYSTEM_COMPANY");
		s4.setDescription("平台企业");
		s4.setEnabled(1l);
		s4.setSysDic(cataDic);
		sysDicDRepository.save(s4);
		
		
		SysDicD s5 = new SysDicD();
		s5.setName("TEMPLATE_COMPANY");
		s5.setDescription("模版企业");
		s5.setEnabled(1l);
		s5.setSysDic(cataDic);
		sysDicDRepository.save(s5);
		
		
		SysDic taskDic = new SysDic();
		taskDic.setType(Config.taskType);
		taskDic.setDescription(Config.taskTypeDis);
		sysDicRepository.save(taskDic);

		SysDicD s6 = new SysDicD();
		s6.setName("FINE_TASK");
		s6.setDescription("精细任务");
		s6.setEnabled(1l);
		s6.setSysDic(taskDic);
		sysDicDRepository.save(s6);
		
		SysDicD s7 = new SysDicD();
		s7.setName("NORMAL_TASK");
		s7.setDescription("正常任务");
		s7.setEnabled(1l);
		s7.setSysDic(taskDic);
		sysDicDRepository.save(s7);
		

		SysDic colorDic = new SysDic();
		colorDic.setType(Config.colorType);
		colorDic.setDescription(Config.colorTypeDis);
		sysDicRepository.save(colorDic);

		SysDicD s8 = new SysDicD();
		s8.setName("0");
		s8.setDescription("默认");
		s8.setEnabled(1l);
		s8.setSysDic(colorDic);
		sysDicDRepository.save(s8);
		
		
		
		SysDic gradeDic = new SysDic();
		gradeDic.setType(Config.gradeType);
		gradeDic.setDescription(Config.gradeTypeDis);
		sysDicRepository.save(gradeDic);

		SysDicD s9 = new SysDicD();
		s9.setName("0");
		s9.setDescription("不指定");
		s9.setEnabled(1l);
		s9.setSysDic(gradeDic);
		sysDicDRepository.save(s9);
	
		
		SysDicD s10 = new SysDicD();
		s10.setName("1");
		s10.setDescription("1级别");
		s10.setEnabled(1l);
		s10.setSysDic(gradeDic);
		sysDicDRepository.save(s10);
		
		
		SysDicD s11= new SysDicD();
		s11.setName("2");
		s11.setDescription("2级别");
		s11.setEnabled(1l);
		s11.setSysDic(gradeDic);
		sysDicDRepository.save(s11);
		
		SysDicD s12 = new SysDicD();
		s12.setName("3");
		s12.setDescription("3级别");
		s12.setEnabled(1l);
		s12.setSysDic(gradeDic);
		sysDicDRepository.save(s12);
		
		
		SysDicD s13 = new SysDicD();
		s13.setName("4");
		s13.setDescription("4级别");
		s13.setEnabled(1l);
		s13.setSysDic(gradeDic);
		sysDicDRepository.save(s13);
		
		SysDicD s14 = new SysDicD();
		s14.setName("5");
		s14.setDescription("5级别");
		s14.setEnabled(1l);
		s14.setSysDic(gradeDic);
		sysDicDRepository.save(s14);
		
		
		
		SysDic genderDic = new SysDic();
		genderDic.setType(Config.genderType);
		genderDic.setDescription(Config.genderTypeDis);
		sysDicRepository.save(genderDic);

		SysDicD s15 = new SysDicD();
		s15.setName("0");
		s15.setDescription("不指定");
		s15.setEnabled(1l);
		s15.setSysDic(genderDic);
		sysDicDRepository.save(s15);
		

		SysDicD s16 = new SysDicD();
		s16.setName("1");
		s16.setDescription("男");
		s16.setEnabled(1l);
		s16.setSysDic(genderDic);
		sysDicDRepository.save(s16);
		
		

		SysDicD s17 = new SysDicD();
		s17.setName("2");
		s17.setDescription("女");
		s17.setEnabled(1l);
		s17.setSysDic(genderDic);
		sysDicDRepository.save(s17);
		
		
		
		
		SysDic employeeStatus = new SysDic();
		employeeStatus.setType(Config.employeeStatus);
		employeeStatus.setDescription(Config.employeeStatusDis);
		sysDicRepository.save(employeeStatus);

		SysDicD s18 = new SysDicD();
		s18.setName("0");
		s18.setDescription("在职");
		s18.setEnabled(1l);
		s18.setSysDic(employeeStatus);
		sysDicDRepository.save(s18);
		

		SysDicD s19 = new SysDicD();
		s19.setName("1");
		s19.setDescription("离职");
		s19.setEnabled(1l);
		s19.setSysDic(employeeStatus);
		sysDicDRepository.save(s19);
		
		
		
		SysDic generalStatus = new SysDic();
		generalStatus.setType(Config.generalStatus);
		generalStatus.setDescription(Config.generalStatusDis);
		sysDicRepository.save(generalStatus);

		SysDicD s20 = new SysDicD();
		s20.setName("0");
		s20.setDescription("有效");
		s20.setEnabled(1l);
		s20.setSysDic(generalStatus);
		sysDicDRepository.save(s20);
		

		SysDicD s21 = new SysDicD();
		s21.setName("1");
		s21.setDescription("有效");
		s21.setEnabled(1l);
		s21.setSysDic(generalStatus);
		sysDicDRepository.save(s21);
		
		
		
		SysDic projectStatus = new SysDic();
		projectStatus.setType(Config.projectStatus);
		projectStatus.setDescription(Config.projectStatusDis);
		sysDicRepository.save(projectStatus);

		SysDicD s22 = new SysDicD();
		s22.setName("0");
		s22.setDescription("进行");
		s22.setEnabled(1l);
		s22.setSysDic(projectStatus);
		sysDicDRepository.save(s22);
		

		SysDicD s23 = new SysDicD();
		s23.setName("1");
		s23.setDescription("结束");
		s23.setEnabled(1l);
		s23.setSysDic(projectStatus);
		sysDicDRepository.save(s23);
		
		SysDicD s24 = new SysDicD();
		s24.setName("2");
		s24.setDescription("撤销");
		s24.setEnabled(1l);
		s24.setSysDic(projectStatus);
		sysDicDRepository.save(s24);
		
		
		
		
		SysDic taskStatus = new SysDic();
		taskStatus.setType(Config.taskStatus);
		taskStatus.setDescription(Config.taskStatusDis);
		sysDicRepository.save(taskStatus);

		SysDicD s25 = new SysDicD();
		s25.setName("0");
		s25.setDescription("发布");
		s25.setEnabled(1l);
		s25.setSysDic(taskStatus);
		sysDicDRepository.save(s25);
		
		SysDicD s26 = new SysDicD();
		s26.setName("1");
		s26.setDescription("接单");
		s26.setEnabled(1l);
		s26.setSysDic(taskStatus);
		sysDicDRepository.save(s26);
		
		
		SysDicD s27 = new SysDicD();
		s27.setName("2");
		s27.setDescription("进行中");
		s27.setEnabled(1l);
		s27.setSysDic(taskStatus);
		sysDicDRepository.save(s27);
		
		
		SysDicD s28 = new SysDicD();
		s28.setName("3");
		s28.setDescription("作废");
		s28.setEnabled(1l);
		s28.setSysDic(taskStatus);
		sysDicDRepository.save(s28);
		
		SysDicD s29 = new SysDicD();
		s29.setName("4");
		s29.setDescription("再进行");
		s29.setEnabled(1l);
		s29.setSysDic(taskStatus);
		sysDicDRepository.save(s29);
		
		SysDicD s30 = new SysDicD();
		s30.setName("5");
		s30.setDescription("审核");
		s30.setEnabled(1l);
		s30.setSysDic(taskStatus);
		sysDicDRepository.save(s30);
		
		
		SysDicD s31 = new SysDicD();
		s31.setName("6");
		s31.setDescription("完成");
		s31.setEnabled(1l);
		s31.setSysDic(taskStatus);
		sysDicDRepository.save(s31);
		
		
		SysDic yesOrNo = new SysDic();
		yesOrNo.setType(Config.yesOrNo);
		yesOrNo.setDescription(Config.yesOrNoDis);
		sysDicRepository.save(yesOrNo);

		SysDicD s32 = new SysDicD();
		s32.setName("0");
		s32.setDescription("否");
		s32.setEnabled(1l);
		s32.setSysDic(yesOrNo);
		sysDicDRepository.save(s32);
		
		SysDicD s33 = new SysDicD();
		s33.setName("1");
		s33.setDescription("是");
		s33.setEnabled(1l);
		s33.setSysDic(yesOrNo);
		sysDicDRepository.save(s33);
		
	}

}
