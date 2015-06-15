package com.jms.service.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.SysDicD;
import com.jms.domain.ws.WSSysDicD;
import com.jms.domainadapter.SysDicDAdapter;
import com.jms.repositories.system.SysDicDRepository;
import com.jms.system.IDicDService;

@Service
@Qualifier("dicDService")
@Transactional(readOnly=true)
public class DicDService implements IDicDService{

	@Autowired
	private SysDicDAdapter sysDicDAdapter;
	@Autowired
	private SysDicDRepository sysDicDRepository;

	
	public List<WSSysDicD> getDicsByType(String type) throws Exception {
		List<WSSysDicD> wsDics = new ArrayList<WSSysDicD>(0);
		
		for(SysDicD sysDicD: sysDicDRepository.findDicsByType(type))
		{
			wsDics.add(sysDicDAdapter.toWSSysDicD(sysDicD));
		}
		return wsDics;
		
	}

}
