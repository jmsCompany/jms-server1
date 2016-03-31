package com.jms.repositories.f;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.FCostCenter;

@Repository
public interface FCostCenterRepository extends JpaRepository<FCostCenter, Long>{

	@Query("select f from FCostCenter f where f.company.idCompany=?1")
	public List<FCostCenter> getByCompanyId(Long idCompany);
}
