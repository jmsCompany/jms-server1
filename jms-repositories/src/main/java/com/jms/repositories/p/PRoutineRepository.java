package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PRoutine;



@Repository
public interface PRoutineRepository extends JpaRepository<PRoutine, Long>{

@Query("select p from PRoutine p where p.company.idCompany=?1")
public List<PRoutine> getByCompanyId(Long companyId);

}