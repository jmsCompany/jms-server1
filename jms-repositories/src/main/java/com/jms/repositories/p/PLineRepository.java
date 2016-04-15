package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PLine;



@Repository
public interface PLineRepository extends JpaRepository<PLine, Long>{

	  @Query("select p from PLine p where p.company.idCompany=?1")
	  public List<PLine> getByCompanyId(Long companyId);
}
