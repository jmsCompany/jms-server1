package com.jms.repositories.p;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PCppOp;
import com.jms.domain.db.PCppOpId;


@Repository
public interface PCppOpRepository extends JpaRepository<PCppOp, PCppOpId>{
    @Query("select p from PCppOp p where p.id.idUser=?1 order by p.id.idCpp desc")
	public List<PCppOp> getByUserId(Long userId);
    
    @Query("select p from PCppOp p where p.id.idCpp=?1")
   	public List<PCppOp> getByCppId(Long idCpp);
}
