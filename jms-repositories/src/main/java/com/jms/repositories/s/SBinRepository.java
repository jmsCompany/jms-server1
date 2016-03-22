package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SBin;

@Repository
public interface SBinRepository  extends JpaRepository<SBin, Long>{
	
	@Query("select s from SBin s where s.SStk.id=?1")
	public List<SBin> getByIdStk(Long idStk);
		
}