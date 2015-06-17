package com.jms.domainadapter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.Province;
import com.jms.domain.ws.WSProvince;

@Service @Transactional(readOnly=true)
public class ProvinceAdapter{
	
	public WSProvince toWSProvince(Province province) throws Exception
	{
		if(province==null)
			return null;
		WSProvince wsp = (WSProvince)BeanUtil.shallowCopy(province,WSProvince.class,null);
		return wsp;
	}
	

}
