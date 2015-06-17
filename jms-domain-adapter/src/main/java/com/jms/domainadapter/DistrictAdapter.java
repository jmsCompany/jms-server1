package com.jms.domainadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.District;
import com.jms.domain.ws.WSDistrict;

@Service @Transactional(readOnly=true)
public class DistrictAdapter{
	
	@Autowired private CityAdapter cityAdapter;
	public WSDistrict toWSDistrict(District district) throws Exception
	{
		if(district==null)
			return null;
		WSDistrict wsd = (WSDistrict)BeanUtil.shallowCopy(district,WSDistrict.class,null);
		wsd.setWsCity(cityAdapter.toWSCity(district.getCity()));
		return wsd;
	}
	

}
