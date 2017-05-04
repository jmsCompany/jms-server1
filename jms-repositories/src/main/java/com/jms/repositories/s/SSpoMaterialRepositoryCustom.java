package com.jms.repositories.s;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.db.SPoMaterial;


public interface SSpoMaterialRepositoryCustom {
	
	public List<SPoMaterial> getCustomSpoMaterials(Long companyId,
			Long type,
			String q,
			String fromDay,
			String toDay);
	
	public List<SPoMaterial> getComComSpoMaterials(Long companyId,
			Long type,
			String q,
			String fromDay,
			String toDay);
		
}