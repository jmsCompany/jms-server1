package com.jms.repositories.s;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SNomaterialReportSum;

@Repository
public interface SNomaterialReportSumRepositoryCustom {
	

	public List<SNomaterialReportSum> findByIdCompany(Long idCompany,String fromDay,String toDay, Long materialId);
	
	
		
}