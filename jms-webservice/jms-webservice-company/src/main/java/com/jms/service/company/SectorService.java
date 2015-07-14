package com.jms.service.company;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.GroupTypeEnum;
import com.jms.domain.db.Company;
import com.jms.domain.db.GroupMembers;
import com.jms.domain.db.GroupMembersId;
import com.jms.domain.db.Groups;
import com.jms.domain.db.Sector;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.domain.ws.WSSector;
import com.jms.domainadapter.SectorAdapter;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.company.SectorsRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.repositories.user.GroupTypeRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class SectorService {
	
	private static final Logger logger = LogManager.getLogger(SectorService.class.getCanonicalName());
	@Autowired
	private SectorsRepository sectorRepository;
	@Autowired
	private CompanyRepository companyRepository ;
	@Autowired
	private SectorAdapter sectorAdapter;
	@Autowired
	private MessagesUitl messagesUitl;
	@Autowired private GroupRepository groupRepository;
	@Autowired private SecurityUtils securityUtils;
	@Autowired private GroupTypeRepository groupTypeRepository;
	public void loadSectorsFromCSV(InputStream inputStream) throws IOException{
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
        reader.readHeaders();  //CompanyName Role	Description
        long seq=1;
		while(reader.readRecord())
		{
			Sector s = new Sector();
			s.setSector(reader.get("Sector"));
			s.setDescription(reader.get("Description"));
			s.setCompany(companyRepository.findByCompanyName(reader.get("CompanyName")));
			s.setEnabled(1l);
			s.setSeq(seq);
			logger.debug("sector: " + s.getSector() +", description: " + s.getDescription());
			sectorRepository.save(s);
			seq++;
	
		}
	}
	
	public void  createDefaultSectors(Company company)
	{
		 createSector("全公司","全公司",1l, company);
		 createSector("财务部","财务部",2l, company);
		 createSector("总裁办","总裁办",3l, company);
		 createSector("项目办","项目办",4l, company);
	}

	
	public void createSector(String sector,String description,Long seq, Company company)
	{
		Sector s = new Sector();
		s.setSector(sector);
		s.setDescription(description);
		s.setCompany(company);
		s.setEnabled(1l);
		s.setSeq(seq);
		sectorRepository.save(s);
		Groups g = new Groups();
		g.setCompany(company);
		g.setUsers(securityUtils.getCurrentDBUser());
		g.setCreationTime(new Date());
		g.setDescription(description);
		g.setGroupName(sector);
		if(sector.equals("全公司"))
		{
			 g.setGroupType(groupTypeRepository.findByGroupType(GroupTypeEnum.Company.name()));
		}
		else
		{
			g.setGroupType(groupTypeRepository.findByGroupType(GroupTypeEnum.Sector.name()));
		}
	    groupRepository.save(g);

	}
	public WSSector save(WSSector wsSector) throws Exception
	{
	    //新建部门
		if(wsSector.getIdSector()==null)
		{
			Sector sector =sectorRepository.findBySectorAndCompanyName(wsSector.getSector(), wsSector.getCompanyName());
		    if(sector!=null)
		    	throw new Exception("该部门已经存在！");
		    else
		    {
		    	sectorRepository.save(sectorAdapter.toDBSector(wsSector,null));
		    }
			
		}
		else //修改部门
		{
			Sector sector =sectorRepository.findOne(wsSector.getIdSector());
			Sector sector1 =sectorRepository.findBySectorAndCompanyName(wsSector.getSector(), wsSector.getCompanyName());
		   if(sector1!=null&&sector1.getIdSector()!=sector.getIdSector())
		   {
			   throw new Exception("该部门已经存在！");
		   }
		   else
		   {
			   sectorRepository.save(sectorAdapter.toDBSector(wsSector,sector)); 
		   }
		}
		

		return wsSector;
	}
	public List<WSSector> getSectorsByIdCompany(Long idCompany) throws Exception
	{
		List<WSSector> sectors = new ArrayList<WSSector>(0);
		for(Sector s: sectorRepository.findByIdCompany(idCompany))
		{
			sectors.add(sectorAdapter.toWSSector(s));
		}
		return sectors;
	}
	
	public WSSector getSector(Long idSector) throws Exception
	{
		Sector sector= sectorRepository.findOne(idSector);
		return sectorAdapter.toWSSector(sector);

	}
	
	public Message checkSector(WSSector wsSector)
	{
		if( companyRepository.findByCompanyName(wsSector.getCompanyName())==null)
			return messagesUitl.getMessage("company.doesnotexist",null,MessageTypeEnum.ERROR);
		
		if(sectorRepository.findBySectorAndCompanyName(wsSector.getSector(), wsSector.getCompanyName())==null)
			return messagesUitl.getMessage("company.sector.avaible",null,MessageTypeEnum.INFOMATION);
		 return messagesUitl.getMessage("company.sector.alreadyexist",null,MessageTypeEnum.ERROR);
	}
}