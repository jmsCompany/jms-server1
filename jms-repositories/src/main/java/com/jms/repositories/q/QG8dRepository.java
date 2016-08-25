package com.jms.repositories.q;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.QCar;
import com.jms.domain.db.QG8d;





@Repository
public interface QG8dRepository extends JpaRepository<QG8d, Long>{


}
