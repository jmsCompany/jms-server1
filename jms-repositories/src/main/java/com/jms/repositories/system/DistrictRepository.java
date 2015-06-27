package com.jms.repositories.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long>{


}
