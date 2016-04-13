package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PWo;
import com.jms.domain.db.PWorkCenter;

@Repository
public interface PWoRepository extends JpaRepository<PWo, Long>{


}
