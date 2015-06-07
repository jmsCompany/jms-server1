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

	private static final String companySize = "CompanySize";
	private static final String companySizeDis = "公司规模";

	private static final String degree = "Degree";
	private static final String degreeDis = "学历";

	private static final String companyType = "CompanyType";
	private static final String companyTypeDis = "行业";

	private static final String companyNature = "CompanyNature";
	private static final String companyNatureDis = "行业性质";

	public void loadDics() {
		
		  SysDic companySizeDic = new SysDic();
		  companySizeDic.setType(companySize);
		  companySizeDic.setDescription(companySizeDis);
		  sysDicRepository.save(companySizeDic);
		  
		  String[] companySizeArray = new
		  String[]{"少于50人","50-150人","150-500人",
		  "500-1000人","1000-5000人","5000-10000人","10000人以上"}; for(String
		  size:companySizeArray) { SysDicD s = new SysDicD(); s.setName(size);
		  s.setDescription(size); s.setEnabled(1); s.setSysDic(companySizeDic);
		  sysDicDRepository.save(s); }
		
		SysDic degreeDic = new SysDic();
		degreeDic.setType(degree);
		degreeDic.setDescription(degreeDis);
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
		typeDic.setType(companyType);
		typeDic.setDescription(companyTypeDis);
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
		natureDic.setType(companyNature);
		natureDic.setDescription(companyNatureDis);
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

	}

}
