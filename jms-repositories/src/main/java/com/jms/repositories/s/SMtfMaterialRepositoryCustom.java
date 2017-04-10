package com.jms.repositories.s;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfMaterial;


public interface SMtfMaterialRepositoryCustom {
	
	public List<SMtfMaterial> getCustomSMtf(Long companyId,
			Long typeId,
			String q,
			Long fromStkId,
		    Long toStkId,
			String fromDay,
			String toDay);
	
	public List<SMtfMaterial> getSmtfMaterialList(Long companyId,
			Long typeId,
			String q,
			String fromDay,
			String toDay);
		
	//往来公司 typeId = 9 公司流转 status=5接收
	public List<SMtfMaterial> getCustomCSMtf(
			Long companyId,
			Long companyId1,
			String q,
			String fromDay,
			String toDay);
	
	
	//往来公司 待接收，已接收，拒绝？
	public List<SMtfMaterial> getCustomCSMtfMaterials(
			Long companyId,
			Long statusId);
	
}