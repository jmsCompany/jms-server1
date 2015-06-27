package com.jms.repositories.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.SysDic;

@Repository
public interface SysDicRepository extends JpaRepository<SysDic, Long>{


}
