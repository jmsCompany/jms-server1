package com.jms.repositories.p;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.jms.domain.db.PWo;
import com.jms.domain.db.QFileManagent;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.db.SPoMaterial;


public interface PWoRepositoryCustom {
	
	public List<PWo> getPwos(Long companyId,
			String q,
			Long materialId);
		
}