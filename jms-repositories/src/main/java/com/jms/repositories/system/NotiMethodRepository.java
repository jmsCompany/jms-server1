package com.jms.repositories.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.NotiMethod;

@Repository
public interface NotiMethodRepository extends JpaRepository<NotiMethod, Long>{

	public NotiMethod findByMethod(String method);
}
