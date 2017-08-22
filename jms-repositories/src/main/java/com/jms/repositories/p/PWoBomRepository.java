package com.jms.repositories.p;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PWoBom;

@Repository
public interface PWoBomRepository extends JpaRepository<PWoBom, Long>{

   @Query("select p from PWoBom p where p.PWo.idWo=?1")
	public List<PWoBom> findByIdWo(Long idWo);
   
   @Query("select p from PWoBom p where p.PWo.idWo=?1 and p.idMat=?2")
 	public PWoBom findByIdWoAndIdMaterial(Long idWo,Long idMaterial);
  
    @Query("select p from PWoBom p where p.idMat=?1")
 	public List<PWoBom> findByIdMaterial(Long idMaterial);
}
