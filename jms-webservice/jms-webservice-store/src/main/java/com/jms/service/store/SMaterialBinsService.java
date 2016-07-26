package com.jms.service.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.SMaterialBins;
import com.jms.domain.db.SMaterialBinsId;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.s.WSMaterialBin;
import com.jms.repositories.s.SMaterialBinsRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class SMaterialBinsService {

	private static final Logger logger = LogManager.getLogger(SMaterialBinsService.class.getCanonicalName());

	@Autowired private  SMaterialBinsRepository sMaterialBinsRepository;
	@Autowired private SecurityUtils securityUtils;

	public Valid saveWSMaterialBin(WSMaterialBin wsMaterialBin) {

		SMaterialBins sMaterialBins =null;
		SMaterialBinsId id = new SMaterialBinsId();
		id.setIdBin(wsMaterialBin.getIdBin());
		id.setIdMaterial(wsMaterialBin.getIdMaterial());
		sMaterialBins =	sMaterialBinsRepository.findOne(id);
		if(sMaterialBins==null)
		{
			sMaterialBins = new SMaterialBins();
			sMaterialBins.setId(id);
		}
		sMaterialBins.setIdStk(wsMaterialBin.getIdStk());
		sMaterialBins.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		sMaterialBinsRepository.save(sMaterialBins);
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}

	
	
	@Transactional(readOnly = false)
	public Valid deleteMaterialBins(Long binId,Long materialId) {
		Valid valid = new Valid();
		SMaterialBinsId id = new SMaterialBinsId();
		id.setIdBin(binId);
		id.setIdMaterial(materialId);
		sMaterialBinsRepository.delete(id);
	     valid.setValid(true);
		return valid;
	}

}
