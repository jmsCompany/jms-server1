package com.jms.service.system;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.db.City;
import com.jms.domain.db.District;
import com.jms.domain.db.Modules;
import com.jms.domain.db.Province;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.system.CityRepository;
import com.jms.repositories.system.DistrictRepository;
import com.jms.repositories.system.ModulesRepository;
import com.jms.repositories.system.ProvinceRepository;


@Service
@Transactional
public class DistrictService {
	
	private static final Logger logger = LogManager.getLogger(DistrictService.class.getCanonicalName());
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private ProvinceRepository provinceRepository;
	
	public void loadProvicesFromCSV(String fileName) throws IOException{
		CsvReader reader = new CsvReader(fileName,',', Charset.forName("UTF-8"));
      //  reader.readHeaders();  //Parent,  Name,     Description
		while(reader.readRecord())
		{
			Province p = new Province();
			p.setIdProvince(Integer.parseInt(reader.get(0).trim()));
		    p.setProvince(reader.get(1).trim());
			logger.debug("Province: " + reader.get(1));
			provinceRepository.save(p); 
	
		}
	}
	
	public void loadCitiesFromCSV(String fileName) throws IOException{
		CsvReader reader = new CsvReader(fileName,',', Charset.forName("UTF-8"));
      //  reader.readHeaders();  //Parent,  Name,     Description
		while(reader.readRecord())
		{
			int idCity = Integer.parseInt(reader.get(0).trim());
			String scity = reader.get(1).trim();
			String postcode =reader.get(2).trim();
			int idProvince = Integer.parseInt(reader.get(3).trim());
			Province province = provinceRepository.findOne(idProvince);
			City  city = new City();
			city.setCity(scity);
			city.setIdCity(idCity);
			city.setPostcode(postcode);
			city.setProvince(province);
			cityRepository.save(city); 
	
		}
	}
	
	
	
	public void loadDistrictsFromCSV(String fileName) throws IOException{
		CsvReader reader = new CsvReader(fileName,',', Charset.forName("UTF-8"));
      //  reader.readHeaders();  //Parent,  Name,     Description
		while(reader.readRecord())
		{
			int idDistrict = Integer.parseInt(reader.get(0).trim());
			String sdistrict = reader.get(1).trim();
			
			int idCity = Integer.parseInt(reader.get(2).trim());
			City city = cityRepository.findOne(idCity);
			District  district = new District();
			district.setIdDistrict(idDistrict);
			district.setDistrict(sdistrict);
			district.setCity(city);
			districtRepository.save(district); 
	
		}
	}
}
