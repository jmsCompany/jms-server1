package com.jms.repositories.p;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PBom;


@Repository
public interface PBomRepository extends JpaRepository<PBom, Long>{


	@Query("delete from PBom p where p.PBomLabel.idBomLabel=?1")
	public void deleteByBomLabelId(Long idBomLabel);
	
}
