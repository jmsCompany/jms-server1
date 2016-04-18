package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SPoMaterial;


@Repository
public interface SSpoMaterialRepository  extends JpaRepository<SPoMaterial, Long>{
	
	@Query("delete from SPoMaterial s where s.SPo.idPo=?1")
	public void deleteBySpoId(Long spoId);
	
	
	@Query("select s from SPoMaterial s where s.SPo.company.idCompany=?1")
	public List<SPoMaterial> getByCompanyId(Long companyId);
	
	@Query("select s from SPoMaterial s where s.SPo.idPo=?1")
	public List<SPoMaterial> getBySpoId(Long spoId);
	
	
	@Query("select s.SMaterial from SPoMaterial s where s.idPoMaterial=?1")
	public SMaterial getBySpoMaterialId(Long spoMaterialId);
	
	@Query("select s from SPoMaterial s where s.SPo.idPo=?1 and s.SMaterial.idMaterial=?2")
	public SPoMaterial getByMaterialIdAndPoId(Long spoId,Long materialId);
	

}