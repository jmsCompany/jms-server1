package com.jms.repositories.p;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PStopsCode;
import com.jms.domain.db.PSubCode;


@Repository
public interface PSubCodeRepository extends JpaRepository<PSubCode, Long>{

}
