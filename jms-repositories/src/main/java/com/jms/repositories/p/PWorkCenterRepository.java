package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PWorkCenter;

@Repository
public interface PWorkCenterRepository extends JpaRepository<PWorkCenter, Long>{

@Query("select p from PWorkCenter p where p.company.idCompany=?1")
public List<PWorkCenter> getByCompanyId(Long companyId);
}
