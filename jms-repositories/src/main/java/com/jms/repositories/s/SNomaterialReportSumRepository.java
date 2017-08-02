package com.jms.repositories.s;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SNomaterialReportSum;

@Repository
public interface SNomaterialReportSumRepository  extends JpaRepository<SNomaterialReportSum, Long>{
	

	public List<SNomaterialReportSum> findByIdCompany(Long idCompany);
	
	public List<SNomaterialReportSum> findByIdCompanyAndIdMatAndStatus(Long idCompany,Long idMat,Long status);
	
	public List<SNomaterialReportSum> findByIdCompanyAndStatus(Long idCompany,Long status);
	
		
}