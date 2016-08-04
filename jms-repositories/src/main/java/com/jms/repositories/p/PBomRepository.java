package com.jms.repositories.p;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PBom;


@Repository
public interface PBomRepository extends JpaRepository<PBom, Long>{

	@Modifying
	@Query(value="delete from p_bom where id_bom_label=?1 and pid is null",nativeQuery=true)
	public void deleteByBomLabelIdAnPidIdNull(Long idBomLabel);
	@Modifying
	@Query(value="delete from p_bom where id_bom_label=?1 and pid is not null",nativeQuery=true)
	public void deleteByBomLabelIdAnPidIdNotNull(Long idBomLabel);
	
	
	@Query("select p from PBom p where p.SMaterial.idMaterial=?1 and p.PBom is null ")
	public PBom findProductByMaterialId(Long materialId);
	
	
	@Query("select p from PBom p where p.SMaterial.idMaterial=?1 and p.PBom is null ")
	public List<PBom> findProductsByMaterialId(Long materialId);
	
	
	@Query("select p from PBom p where p.PBom.idBom=?1 order by p.idBom")
	public List<PBom> findMaterialsByProductId(Long producId);
	
	@Query("select p from PBom p where p.PBomLabel.idBomLabel=?1 ")
	public List<PBom> findByBomLabelId(Long bomLabelId);
	
	
	
}
