package com.jms.repositories.q;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.jms.domain.db.QFileManagent;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.db.SPoMaterial;


public interface QFileManagementRepositoryCustom {
	
	public List<QFileManagent> getQFiles(Long companyId,
			String fromDate,
			String toDate,
			Long materialId,
			Long woId,
			Long routineDId,
			Long filetypeId,
			Long creatorId);
		
}