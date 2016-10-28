package com.jms.repositories.q;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.QCar;
import com.jms.domain.db.QFileTemplate;
import com.jms.domain.db.QNcr2;





@Repository
public interface QFileTemplateRepository extends JpaRepository<QFileTemplate, Long>{

	
	public List<QFileTemplate> findByIdCompany(Long idCompany);
	
	public QFileTemplate findByIdFileType(Long idFileType);

}
