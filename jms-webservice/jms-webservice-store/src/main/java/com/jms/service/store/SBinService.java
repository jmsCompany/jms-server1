package com.jms.service.store;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.Config;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SStk;
import com.jms.domain.db.SStkTypeDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.store.WSBin;
import com.jms.domain.ws.store.WSStk;
import com.jms.domain.ws.store.WSStkType;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStkRepository;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.repositories.s.SYesOrNoDicRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class SBinService {

	private static final Logger logger = LogManager.getLogger(SBinService.class
			.getCanonicalName());
	@Autowired
	private SStkRepository sStkRepository;
	@Autowired
	private SStatusDicRepository  sStatusDicRepository;
	@Autowired
	private SStkTypeDicRepository sStkTypeDicRepository;

	@Autowired 
	private  SecurityUtils securityUtils;
	
	@Autowired 
	private  SYesOrNoDicRepository sYesOrNoDicRepository;
	
	@Autowired 
	private  SBinRepository sBinRepository;
	

	public Valid saveBin(WSBin wsBin) {
		
		SBin bin;
		//create
		if(wsBin.getIdBin()==null||wsBin.getIdBin().equals(0l))
		{
			 bin=new SBin();
		}
		//update
		else
		{
			 bin = sBinRepository.findOne(wsBin.getIdBin());	
		}
		bin.setBin(wsBin.getBin());
		bin.setSStk(sStkRepository.findOne(wsBin.getIdStk()));
		bin.setSYesOrNoDic(sYesOrNoDicRepository.findOne(wsBin.getIsReturnShelf()));
		bin.setSStatusDic(sStatusDicRepository.findOne(wsBin.getStatus()));
		
		sBinRepository.save(bin);
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	
	@Transactional(readOnly=true)
	public List<WSBin> findBins(Long idStk)
	{
		List<WSBin> wsBinList = new ArrayList<WSBin>();
		for(SBin bin: sBinRepository.getByIdStk(idStk))
		{
			WSBin wsBin = new WSBin();
			wsBin.setBin(bin.getBin());
			wsBin.setIdBin(bin.getIdBin());
			wsBin.setIsReturnShelf(bin.getSYesOrNoDic().getId());
			wsBin.setIsReturnShelfName(bin.getSYesOrNoDic().getName());
			wsBin.setStatusName(bin.getSStatusDic().getName());
			wsBin.setStatus(bin.getSStatusDic().getId());
			wsBinList.add(wsBin);
		}
		
		return wsBinList;
	}

	
	@Transactional(readOnly=true)
	public List<WSSelectObj> findBinsObjs(Long idStk)
	{
		List<WSSelectObj> wsBinList = new ArrayList<WSSelectObj>();
		for(SBin bin: sBinRepository.getByIdStk(idStk))
		{
			WSSelectObj o = new WSSelectObj(bin.getIdBin(),bin.getBin());
			wsBinList.add(o);
		}
		
		return wsBinList;
	}
	
	
	@Transactional(readOnly=true)
	public WSBin findBin(Long binId)
	{	
		    SBin bin = sBinRepository.findOne(binId);
			WSBin wsBin = new WSBin();
			if(bin==null)
				return wsBin;
			wsBin.setBin(bin.getBin());
			wsBin.setIdBin(bin.getIdBin());
			wsBin.setIsReturnShelf(bin.getSYesOrNoDic().getId());
			wsBin.setIsReturnShelfName(bin.getSYesOrNoDic().getName());
			wsBin.setStatusName(bin.getSYesOrNoDic().getName());
			wsBin.setStatus(bin.getSStatusDic().getId());
		    return wsBin;
	}

	
	
	/*
	@Transactional(readOnly=false)
	public Valid invalidateBin(Long stkId)
	{
		
		SStk stk = sStkRepository.findOne(stkId);
		stk.setSStatusDic(sStatusDicRepository.getBySourceAndName("s_stk","无效"));
		sStkRepository.save(stk);
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
     */
	@Transactional(readOnly=false)
	public Valid deleteBin(Long binId)
	{
		Valid valid = new Valid();
		SBin bin = sBinRepository.findOne(binId);
		if(!bin.getSInventories().isEmpty())
		{
			valid.setValid(false);
		}
		else
		{
			sBinRepository.delete(binId);
			valid.setValid(true);
		}
		
		return valid;
	}
	


}
