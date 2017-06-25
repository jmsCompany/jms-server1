package com.jms.repositories.s;

import java.util.List;
import com.jms.domain.db.SSo;


public interface SSoRepositoryCustom {
	
	public List<SSo> getCustomSsos(Long companyId,
			Long status,
			String q,
			String fromDay,
			String toDay);
		
}