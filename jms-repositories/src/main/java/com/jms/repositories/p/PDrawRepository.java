package com.jms.repositories.p;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PDraw;


@Repository
public interface PDrawRepository extends JpaRepository<PDraw, Long>{


}
