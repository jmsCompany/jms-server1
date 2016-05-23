package com.jms.repositories.q;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.QTester;



@Repository
public interface QTesterRepository extends JpaRepository<QTester, Long>{


}
