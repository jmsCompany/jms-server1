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
import com.jms.domain.db.Province;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domainadapter.CityAdapter;
import com.jms.domainadapter.DistrictAdapter;
import com.jms.domainadapter.ProvinceAdapter;
import com.jms.repositories.system.CityRepository;
import com.jms.repositories.system.DistrictRepository;
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
	
	public List<WSSelectObj> getProvinces() throws Exception
	{
		List<WSSelectObj> wsProvinces = new ArrayList<WSSelectObj>(0);
		for(Province province: provinceRepository.findAll())
		{
			WSSelectObj so = new WSSelectObj();
			so.setId(province.getIdProvince());
			so.setName(province.getProvince());
			wsProvinces.add(so);
		}
		return wsProvinces;
	}
	
	public List<WSSelectObj> getCites(Long idProvince) throws Exception
	{
		List<WSSelectObj> wsCities = new ArrayList<WSSelectObj>(0);
		for(City c: provinceRepository.findOne(idProvince).getCities())
		{
			WSSelectObj so = new WSSelectObj();
			so.setId(c.getIdCity());
			so.setName(c.getCity());
			wsCities.add(so);
		}
		return wsCities;
	}
	
	public List<WSSelectObj> getDistricts(Long idCity) throws Exception
	{
		List<WSSelectObj> wsDistricts = new ArrayList<WSSelectObj>(0);
		for(District d: cityRepository.findOne(idCity).getDistricts())
		{
			WSSelectObj so = new WSSelectObj();
			so.setId(d.getIdDistrict());
			so.setName(d.getDistrict());
			wsDistricts.add(so);
		}
		return wsDistricts;
	}
	
	public void loadProvicesFromCSV(InputStream inputStream) throws IOException{
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
      //  reader.readHeaders();  //Parent,  Name,     Description
		while(reader.readRecord())
		{
			Province p = new Province();
			p.setIdProvince(Long.parseLong(reader.get(0).trim()));
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
			Long idCity = Long.parseLong(reader.get(0).trim());
			String scity = reader.get(1).trim();
			String postcode =reader.get(2).trim();
			Long idProvince = Long.parseLong(reader.get(3).trim());
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
			Long idDistrict = Long.parseLong(reader.get(0).trim());
			String sdistrict = reader.get(1).trim();
			
			Long idCity = Long.parseLong(reader.get(2).trim());
			City city = cityRepository.findOne(idCity);
			District  district = new District();
			district.setIdDistrict(idDistrict);
			district.setDistrict(sdistrict);
			district.setCity(city);
			districtRepository.save(district); 
	
		}
	}
}
