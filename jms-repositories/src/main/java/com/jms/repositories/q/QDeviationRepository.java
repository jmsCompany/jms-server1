package com.jms.repositories.q;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.QCar;
import com.jms.domain.db.QDeviation;
import com.jms.domain.db.QNcr2;





@Repository
public interface QDeviationRepository extends JpaRepository<QDeviation, Long>{


}
