package com.jms.repositories.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long>{


}
