package com.jms.repositories.q;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.QCar;
import com.jms.domain.db.QFileManagent;





@Repository
public interface QFileManagentRepository extends JpaRepository<QFileManagent, Long>{


}
