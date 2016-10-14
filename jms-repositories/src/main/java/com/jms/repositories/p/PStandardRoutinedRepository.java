package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.EhsItem;
import com.jms.domain.db.PStandardRoutined;

@Repository
public interface PStandardRoutinedRepository extends JpaRepository<PStandardRoutined, Long>{
	public List<PStandardRoutined> findByIdCompany(Long idCompany);
}
