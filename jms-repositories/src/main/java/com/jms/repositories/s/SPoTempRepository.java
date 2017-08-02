package com.jms.repositories.s;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SPoTemp;

@Repository
public interface SPoTempRepository  extends JpaRepository<SPoTemp, Long>{
	

	@Query("select s from SPoTemp s where s.idCompany=?1 and s.status=0")
	public List<SPoTemp> findByIdCompany(Long idCompany);
	
	@Query("select s from SPoTemp s where s.idCompany=?1 and s.idMat=?2 and s.status=0")
	public List<SPoTemp> findByIdCompanyAndIdMat(Long idCompany,Long idMat);
	
		
}