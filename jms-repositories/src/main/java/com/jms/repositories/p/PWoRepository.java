package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PWo;
import com.jms.domain.db.SMaterial;


@Repository
public interface PWoRepository extends JpaRepository<PWo, Long>{

 @Query("select p from PWo p where p.SSo.company.idCompany=?1")
  public List<PWo> getByCompanyId(Long companyId);
 
 
 @Query("select s.SSo.SMaterial from PWo s where s.idWo=?1")
	public SMaterial findByWoId(Long woId);
}
