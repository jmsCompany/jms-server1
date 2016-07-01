package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SBin;
import com.jms.domain.db.SMtfMaterial;



@Repository
public interface SMtfMaterialRepository  extends JpaRepository<SMtfMaterial, Long>{
	
	@Query("select s from SMtfMaterial s where s.SMtf.company.idCompany=?1 and s.SMtf.SMtfTypeDic.idMtfType=?2")
	public List<SMtfMaterial> getByCompanyIdAndTypeId(Long companyId,Long typeId);
	
	@Query("select s from SMtfMaterial s where s.SMtf.company.idCompany=?1 and s.SMtf.SMtfTypeDic.idMtfType=?2 and s.SMaterial.idMaterial=?3")
	public List<SMtfMaterial> getByCompanyIdAndTypeIdAndMaterialId(Long companyId,Long typeId,Long materialId);
	
	@Query("select s from SMtfMaterial s where s.SMtf.company.idCompany=?1 and s.SMaterial.idMaterial=?2")
	public List<SMtfMaterial> getByCompanyIdAndMaterialId(Long companyId,Long materialId);
	
	@Query("select s from SMtfMaterial s where s.SMtf.company.idCompany=?1")
	public List<SMtfMaterial> getByCompanyId(Long companyId);
	
	@Query("select s from SMtfMaterial s where s.SPoMaterial.SPo.idPo=?1 and s.SBinByToBin is not null")
	public List<SMtfMaterial> getBySpoId(Long spoId);
	
	//@Modifying
	//@Query("delete from SMtfMaterial s where s.SMtf.idMt=?1")
	//@Query(value="delete from qa_concern_user where user_id=?1 and concern_user_id=?2)",nativeQuery=true)
	//public void deleteByMtId(Long idMt);
	
	
	@Query("select distinct s.lotNo from SMtfMaterial s where s.SPoMaterial.SPo.idPo=?1 and s.SPoMaterial.SMaterial.idMaterial=?2")
	public List<String> getLotNosBySpoIdAndMaterialId(Long spoId,Long materialId);
	
	
	@Query("select distinct s.SBinByToBin from SMtfMaterial s where s.SPoMaterial.SPo.idPo=?1 and s.SPoMaterial.SMaterial.idMaterial=?2")
	public List<SBin> getToBinsBySpoIdAndMaterialId(Long spoId,Long materialId);
	
	
	@Query("select s from SMtfMaterial s where s.SMtf.idWo=?1 and s.SMaterial.idMaterial=?2")
	public List<SMtfMaterial> getByWoIdAndMaterialId(Long woId,Long materialId);
		
}