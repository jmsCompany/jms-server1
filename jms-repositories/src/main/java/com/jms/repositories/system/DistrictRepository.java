package com.jms.repositories.system;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.City;
import com.jms.domain.db.District;
import com.jms.domain.db.Province;

@Repository
public interface DistrictRepository extends CrudRepository<District, Long>{


}
