package com.jms.repositories.p;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.PCPp;
import com.jms.domain.db.PCheckTime;


@Repository
public interface PCPpRepository extends JpaRepository<PCPp, Long>{

    @Query("select p from PCPp p where p.company.idCompany=?1 order by planSt desc")
	public List<PCPp> getByCompanyId(Long companyId);
}
