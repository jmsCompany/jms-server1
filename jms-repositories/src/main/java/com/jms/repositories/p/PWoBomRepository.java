package com.jms.repositories.p;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PWoBom;

@Repository
public interface PWoBomRepository extends JpaRepository<PWoBom, Long>{


}
