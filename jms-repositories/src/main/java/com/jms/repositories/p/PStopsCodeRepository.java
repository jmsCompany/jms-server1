package com.jms.repositories.p;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PStopsCode;


@Repository
public interface PStopsCodeRepository extends JpaRepository<PStopsCode, Long>{

}
