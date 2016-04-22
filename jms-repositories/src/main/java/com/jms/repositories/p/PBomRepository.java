package com.jms.repositories.p;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PBom;


@Repository
public interface PBomRepository extends JpaRepository<PBom, Long>{


	@Query("delete from PBom p where p.PBomLabel.idBomLabel=?1")
	public void deleteByBomLabelId(Long idBomLabel);
	
	
	@Query("select p from PBom p where p.SMaterial.idMaterial=?1 and p.PBom is null ")
	public PBom findProductByMaterialId(Long materialId);
	
	
	@Query("select p from PBom p where p.SMaterial.idMaterial=?1 and p.PBom is null ")
	public List<PBom> findProductsByMaterialId(Long materialId);
	
}
