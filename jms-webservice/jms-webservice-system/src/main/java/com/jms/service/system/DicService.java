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
			s.setEnabled(1);
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
			s.setEnabled(1);
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
			s.setEnabled(1);
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
			s.setEnabled(1);
			s.setSysDic(natureDic);
			sysDicDRepository.save(s);
		}

		SysDic langDic = new SysDic();
		langDic.setType(Config.lang);
		langDic.setDescription(Config.langDis);
		sysDicRepository.save(langDic);

		SysDicD s = new SysDicD();
		s.setName("zh_CN");
		s.setDescription("汉语");
		s.setEnabled(1);
		s.setSysDic(langDic);
		sysDicDRepository.save(s);
		
		SysDicD s1 = new SysDicD();
		s1.setName("en_US");
		s1.setDescription("英语");
		s1.setEnabled(1);
		s1.setSysDic(langDic);
		sysDicDRepository.save(s1);
		
		
		SysDic cataDic = new SysDic();
		cataDic.setType(Config.companyCatergory);
		cataDic.setDescription(Config.companyCatergoryDis);
		sysDicRepository.save(cataDic);

		SysDicD s3 = new SysDicD();
		s3.setName("NORMAL_COMPANY");
		s3.setDescription("正规企业");
		s3.setEnabled(1);
		s3.setSysDic(cataDic);
		sysDicDRepository.save(s3);
		
		SysDicD s4 = new SysDicD();
		s4.setName("SYSTEM_COMPANY");
		s4.setDescription("平台企业");
		s4.setEnabled(1);
		s4.setSysDic(cataDic);
		sysDicDRepository.save(s4);
		
		
		SysDicD s5 = new SysDicD();
		s5.setName("TEMPLATE_COMPANY");
		s5.setDescription("模版企业");
		s5.setEnabled(1);
		s5.setSysDic(cataDic);
		sysDicDRepository.save(s5);
		
		
		SysDic taskDic = new SysDic();
		taskDic.setType(Config.taskType);
		taskDic.setDescription(Config.taskTypeDis);
		sysDicRepository.save(taskDic);

		SysDicD s6 = new SysDicD();
		s6.setName("FINE_TASK");
		s6.setDescription("精细任务");
		s6.setEnabled(1);
		s6.setSysDic(taskDic);
		sysDicDRepository.save(s6);
		
		SysDicD s7 = new SysDicD();
		s7.setName("NORMAL_TASK");
		s7.setDescription("正常任务");
		s7.setEnabled(1);
		s7.setSysDic(taskDic);
		sysDicDRepository.save(s7);
		
		
		
	}

}
