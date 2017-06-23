package com.jms.repositories.m;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.MDept;


@Repository
public interface MDeptRepository extends JpaRepository<MDept, Long>{

	@Query("select m from MDept m where m.company.idCompany=?1")
	public List<MDept> findByCompanyId(Long companyId);
}
