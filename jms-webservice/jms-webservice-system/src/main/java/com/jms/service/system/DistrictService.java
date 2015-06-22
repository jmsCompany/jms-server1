package com.jms.service.system;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.db.City;
import com.jms.domain.db.District;
import com.jms.domain.db.Module;
import com.jms.domain.db.Province;
import com.jms.domain.ws.WSCity;
import com.jms.domain.ws.WSDistrict;
import com.jms.domain.ws.WSProvince;
import com.jms.domainadapter.CityAdapter;
import com.jms.domainadapter.DistrictAdapter;
import com.jms.domainadapter.ProvinceAdapter;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.system.CityRepository;
import com.jms.repositories.system.DistrictRepository;
import com.jms.repositories.system.ModuleRepository;
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
	@Autowired
	private ProvinceAdapter provinceAdapter;
	@Autowired
	private CityAdapter cityAdapter;
	@Autowired
	private DistrictAdapter districtAdapter;
	
	public List<WSProvince> getProvinces() throws Exception
	{
		List<WSProvince> wsProvinces = new ArrayList<WSProvince>(0);
		for(Province province: provinceRepository.findAll())
		{
			wsProvinces.add(provinceAdapter.toWSProvince(province));
		}
		return wsProvinces;
	}
	
	public List<WSCity> getCites(Integer idProvince) throws Exception
	{
		List<WSCity> wsCities = new ArrayList<WSCity>(0);
		for(City c: provinceRepository.findOne(idProvince).getCities())
		{
			wsCities.add(cityAdapter.toWSCity(c));
		}
		return wsCities;
	}
	
	public List<WSDistrict> getDistricts(Integer idCity) throws Exception
	{
		List<WSDistrict> wsDistricts = new ArrayList<WSDistrict>(0);
		for(District d: cityRepository.findOne(idCity).getDistricts())
		{
			wsDistricts.add(districtAdapter.toWSDistrict(d));
		}
		return wsDistricts;
	}
	
	public void loadProvicesFromCSV(InputStream inputStream) throws IOException{
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
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
	
	public void loadCitiesFromCSV(InputStream inputStream) throws IOException{
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
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
	
	
	
	public void loadDistrictsFromCSV(InputStream inputStream) throws IOException{
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
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
