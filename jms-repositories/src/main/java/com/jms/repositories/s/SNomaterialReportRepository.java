package com.jms.repositories.s;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SNomaterialReport;

@Repository
public interface SNomaterialReportRepository  extends JpaRepository<SNomaterialReport, Long>{
	

	public List<SNomaterialReport> findByIdCompany(Long idCompany);
	
	public List<SNomaterialReport> findByIdCompanyAndIdMat(Long idCompany,Long idMat);
	public List<SNomaterialReport> findByIdCompanyAndIdMatAndType(Long idCompany,Long idMat,Long type);
	public List<SNomaterialReport> findByIdCompanyAndType(Long idCompany,Long type);
	
	public List<SNomaterialReport> findByIdCompanyAndTypeOrderByProDateAsc(Long idCompany,Long type);
	
		
}