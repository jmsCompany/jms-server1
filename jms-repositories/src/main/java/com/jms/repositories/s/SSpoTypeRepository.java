package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoType;


@Repository
public interface SSpoTypeRepository  extends JpaRepository<SPoType, Long>{
	

}