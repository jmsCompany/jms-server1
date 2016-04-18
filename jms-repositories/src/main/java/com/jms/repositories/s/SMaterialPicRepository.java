package com.jms.repositories.s;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMaterialPic;


@Repository
public interface SMaterialPicRepository  extends JpaRepository<SMaterialPic, Long>{
	@Query("select  s from SMaterialPic s where s.SMaterial.idMaterial=?1")
	public List<SMaterialPic> findBySMaterialId(Long materialId);
	
}