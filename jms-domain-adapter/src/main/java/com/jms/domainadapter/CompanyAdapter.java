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

@Service @Transactional(readOnly=true)
public class CompanyAdapter{
	
	@Autowired
	private UserAdapter userAdapter;
	
	public Company toDBCompany(WSCompany wsCompany) throws Exception
	{
		if(wsCompany==null)
			return null;
		Company c = (Company)BeanUtil.shallowCopy(wsCompany,Company.class);
		c.setEnabled(wsCompany.getEnabledEnum().getStatusCode());
		c.setFineTask(wsCompany.getFineTaskEnum().getStatusCode());
		c.setCompanyCatergory(wsCompany.getCompanyCategoryEnum().getStatusCode());
		//todo: company size, company type;
		
		//c.setSysDicDByCompanySize(sysDicDByCompanySize);
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
