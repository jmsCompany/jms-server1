package com.jms.domainadapter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.CompanyCategoryEnum;
import com.jms.domain.EnabledEnum;
import com.jms.domain.FineTaskEnum;
import com.jms.domain.db.Company;
import com.jms.domain.ws.WSCompany;
import com.jms.domain.ws.WSSysDicD;
import com.jms.repositories.system.DistrictRepository;
import com.jms.repositories.system.SysDicDRepository;

@Service @Transactional(readOnly=true)
public class CompanyAdapter{
	
	@Autowired
	private UserAdapter userAdapter;
	
	@Autowired
	private SysDicDRepository sysDicDRepository;
	
	@Autowired
	private SysDicDAdapter sysDicDAdapter;
	
	@Autowired
	private ProvinceAdapter provinceAdapter;
	
	@Autowired
	private CityAdapter cityAdapter;
	@Autowired
	private DistrictAdapter districtAdapter;
	@Autowired
	private DistrictRepository districtRepository;
	
	public Company toDBCompany(WSCompany wsCompany) throws Exception
	{
		if(wsCompany==null)
			return null;
		Company c = (Company)BeanUtil.shallowCopy(wsCompany,Company.class);
		c.setEnabled(wsCompany.getEnabledEnum().getStatusCode());
		c.setFineTask(wsCompany.getFineTaskEnum().getStatusCode());
		c.setCompanyCatergory(wsCompany.getCompanyCategoryEnum().getStatusCode());
		
		c.setSysDicDByCompanyNature(sysDicDRepository.findOne(wsCompany.getCompanyNature().getIdDic()));
		c.setSysDicDByCompanySize(sysDicDRepository.findOne(wsCompany.getCompanySize().getIdDic()));
		c.setSysDicDByCompanyType(sysDicDRepository.findOne(wsCompany.getCompanyType().getIdDic()));
		if(wsCompany.getWsDistrict()!=null)
		{
			c.setDistrict(districtRepository.findOne(wsCompany.getWsDistrict().getIdDistrict()));
		}
		return c;
			
	}


	public WSCompany toWSCompany(Company company) throws Exception
	{
		if(company==null)
			return null;
		WSCompany wsc = (WSCompany)BeanUtil.shallowCopy(company,WSCompany.class);
		wsc.setEnabledEnum(findEnabledEnumByStatusCode(company.getEnabled()));
		wsc.setFineTaskEnum(findFineTaskEnumByStatusCode(company.getFineTask()));
		wsc.setCompanyCategoryEnum(findCompanyCategoryEnumByStatusCode(company.getCompanyCatergory()));
		wsc.setWsUsers(userAdapter.toWSUser(company.getUsers()));
	  
		
		WSSysDicD companySize = sysDicDAdapter.toWSSysDicD(company.getSysDicDByCompanySize());
		WSSysDicD companyNature = sysDicDAdapter.toWSSysDicD(company.getSysDicDByCompanyNature());
		WSSysDicD companyType = sysDicDAdapter.toWSSysDicD(company.getSysDicDByCompanyType());
		
		wsc.setCompanyNature(companyNature);
		wsc.setCompanySize(companySize);
		wsc.setCompanyType(companyType);
		if(company.getDistrict()!=null)
	    {
		   wsc.setWsProvince(provinceAdapter.toWSProvince(company.getDistrict().getCity().getProvince()));
		   wsc.setWsCity(cityAdapter.toWSCity(company.getDistrict().getCity()));
		   wsc.setWsDistrict(districtAdapter.toWSDistrict(company.getDistrict()));
	     }
	
		return wsc;
	}
	
	private FineTaskEnum findFineTaskEnumByStatusCode(int statusCode)
	{
		for(FineTaskEnum e:FineTaskEnum.values())
	    {
	    	if(e.getStatusCode()==statusCode)
	    		return e;
	    }
		return null;
	}
	private EnabledEnum findEnabledEnumByStatusCode(int statusCode)
	{
		for(EnabledEnum e:EnabledEnum.values())
	    {
	    	if(e.getStatusCode()==statusCode)
	    		return e;
	    }
		return null;
	}
	private CompanyCategoryEnum findCompanyCategoryEnumByStatusCode(int statusCode)
	{
		for(CompanyCategoryEnum e :CompanyCategoryEnum.values())
	    {
	    	if(e.getStatusCode()==statusCode)
	    		return e;
	    }
		return null;
	}
}
