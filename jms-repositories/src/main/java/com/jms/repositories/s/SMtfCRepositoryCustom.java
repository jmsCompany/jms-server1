package com.jms.repositories.s;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfC;
import com.jms.domain.db.SMtfMaterial;


public interface SMtfCRepositoryCustom {
	
	public List<SMtfC> getCustomSMtfC(Long companyId,
			String q,
			String companyName,
			String fromDay,
			String toDay);
	
		
}