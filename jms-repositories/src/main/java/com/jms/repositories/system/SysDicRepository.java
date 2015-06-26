package com.jms.repositories.system;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.City;
import com.jms.domain.db.District;
import com.jms.domain.db.Province;
import com.jms.domain.db.SysDic;

@Repository
public interface SysDicRepository extends CrudRepository<SysDic, Long>{


}
