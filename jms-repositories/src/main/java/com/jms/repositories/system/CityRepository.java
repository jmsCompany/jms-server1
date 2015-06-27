package com.jms.repositories.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{


}
