package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PActualSetup;



@Repository
public interface PActualSetupRepository extends JpaRepository<PActualSetup, Long>{

	@Query("select p from PActualSetup p where p.PCPp.idCPp=?1 order by p.actSt desc")
	public List<PActualSetup> findByCppId(Long cppId);
	
	
	@Query("select p from PActualSetup p where p.PCPp.company.idCompany=?1 order by p.actSt desc")
	public List<PActualSetup> findByCompanyId(Long companyId);
	

}
