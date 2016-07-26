package com.jms.repositories.s;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SMaterialBins;
import com.jms.domain.db.SMaterialBinsId;

@Repository
public interface SMaterialBinsRepository  extends JpaRepository<SMaterialBins, SMaterialBinsId>{
	
	@Query("select s from SMaterialBins s where s.id.idMaterial=?1")
	public List<SMaterialBins> getByMaterialId(Long materialId);	
	
	
	@Query("select s from SMaterialBins s where s.id.idMaterial=?1 and s.idStk=?2")
	public List<SMaterialBins> getByMaterialIdAndStkId(Long materialId,Long stkId);	
	
	@Query("select s from SMaterialBins s where s.idCompany=?1 order by s.id.idMaterial")
	public List<SMaterialBins> getByIdCompany(Long idCompany);	
}