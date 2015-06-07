package com.jms.domainadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.City;
import com.jms.domain.ws.WSCity;

@Service @Transactional(readOnly=true)
public class CityAdapter{
	
	@Autowired private ProvinceAdapter provinceAdapter;
	public WSCity toWSCity(City city) throws Exception
	{
		if(city==null)
			return null;
		WSCity wsc = (WSCity)BeanUtil.shallowCopy(city,WSCity.class);
		wsc.setWsProvince(provinceAdapter.toWSProvince(city.getProvince()));
		return wsc;
	}
	

}
