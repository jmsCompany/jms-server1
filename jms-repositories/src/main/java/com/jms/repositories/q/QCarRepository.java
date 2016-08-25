package com.jms.repositories.q;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.QCar;





@Repository
public interface QCarRepository extends JpaRepository<QCar, Long>{


}
