package com.jms.repositories.p;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.MSparePart;
import com.jms.domain.db.PStopsPlan;


@Repository
public interface PSparePartRepository extends JpaRepository<MSparePart, Long>{
	

}
