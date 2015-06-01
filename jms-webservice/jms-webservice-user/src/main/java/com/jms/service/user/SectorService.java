package com.jms.service.user;


import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.db.Sector;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.company.SectorsRepository;

@Service
@Transactional
public class SectorService {
	
	private static final Logger logger = LogManager.getLogger(SectorService.class.getCanonicalName());
	@Autowired
	private SectorsRepository sectorRepository;
	@Autowired
	private CompanyRepository companyRepository ;
	public void loadSectorsFromCSV(String fileName) throws IOException{
		CsvReader reader = new CsvReader(fileName,',', Charset.forName("UTF-8"));
        reader.readHeaders();  //CompanyName Role	Description
		while(reader.readRecord())
		{
			Sector s = new Sector();
			s.setSector(reader.get("Sector"));
			s.setDescription(reader.get("Description"));
			s.setCompany(companyRepository.findByCompanyName(reader.get("CompanyName")));
			logger.debug("sector: " + s.getSector() +", description: " + s.getDescription());
			save(s);
	
		}
	}
	
	public Sector save(Sector sec)
	{
		Sector s =sectorRepository.findBySectorAndCompanyName(sec.getSector(), sec.getCompany().getCompanyName());
		if(s == null){
			sectorRepository.save(sec);  //create new 
		}
		else
		{
			sec.setIdSector(s.getIdSector());  //update by Id
			sectorRepository.save(sec);
		}
		return s;
	}
}