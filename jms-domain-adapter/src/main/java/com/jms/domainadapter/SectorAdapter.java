package com.jms.domainadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.Company;
import com.jms.domain.db.Sector;
import com.jms.domain.db.Users;
import com.jms.domain.ws.WSSector;
import com.jms.domain.ws.WSUser;
import com.jms.repositories.company.CompanyRepository;

@Service @Transactional(readOnly=true)
public class SectorAdapter {
	private @Autowired  CompanyRepository companyRepository;
	public Sector toDBSector(WSSector wsSector,Sector sector) throws Exception
	{
		Sector s = (Sector)BeanUtil.shallowCopy(wsSector,Sector.class,sector);
		Company company =companyRepository.findByCompanyName(wsSector.getCompanyName());
		s.setCompany(company);
		return s;
	}


	public WSSector toWSSector(Sector sector) throws Exception
	{
		WSSector wsSector = (WSSector)BeanUtil.shallowCopy(sector,WSSector.class,null);
		wsSector.setCompanyName(sector.getCompany().getCompanyName());
		return wsSector;
	}

}
