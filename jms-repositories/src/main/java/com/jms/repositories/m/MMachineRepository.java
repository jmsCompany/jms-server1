package com.jms.repositories.m;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MMachine;


@Repository
public interface MMachineRepository extends JpaRepository<MMachine, Long>{


	 @Query("select m from MMachine m where m.company.idCompany=?1")
	  public List<MMachine> getByCompanyId(Long companyId);
}
