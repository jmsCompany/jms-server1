package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PLine;
import com.jms.domain.db.PWip;



@Repository
public interface PWipRepository extends JpaRepository<PWip, Long>{

	  @Query("select p from PWip p where p.company.idCompany=?1")
	  public List<PWip> getByCompanyId(Long companyId);
}
