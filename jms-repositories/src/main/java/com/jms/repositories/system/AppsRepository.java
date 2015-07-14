package com.jms.repositories.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Apps;
import com.jms.domain.db.City;

@Repository
public interface AppsRepository extends JpaRepository<Apps, Long>{


}
