package com.jms.domainadapter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.EnabledEnum;
import com.jms.domain.FineTaskEnum;
import com.jms.domain.db.Company;
import com.jms.domain.ws.WSCompany;

@Service @Transactional(readOnly=true)
public class CompanyAdapter {
	
	@Autowired
	private UserAdapter userAdapter;
	
	public Company toDBCompany(WSCompany wsCompany) throws Exception
	{
		Company c = (Company)BeanUtil.shallowCopy(wsCompany,Company.class);
		c.setEnabled(wsCompany.getEnabledEnum().getStatusCode());
		c.setFineTask(wsCompany.getFineTaskEnum().getStatusCode());
		return c;
			
	}


	public WSCompany toWSCompany(Company company) throws Exception
	{
		WSCompany wsc = (WSCompany)BeanUtil.shallowCopy(company,WSCompany.class);
		wsc.setEnabledEnum(findEnabledEnumByStatusCode(company.getEnabled()));
		wsc.setFineTaskEnum(findFineTaskEnumByStatusCode(company.getFineTask()));
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
}
