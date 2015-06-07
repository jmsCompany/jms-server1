package com.jms.repositories.system;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.City;

@Repository
public interface CityRepository extends CrudRepository<City, Integer>{


}
