package com.jms.service.store;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.Config;
import com.jms.domain.db.Company;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SInventory;
import com.jms.domain.db.SMaterialBins;
import com.jms.domain.db.SStk;
import com.jms.domain.db.SStkTypeDic;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.s.WSBin;
import com.jms.domain.ws.s.WSInventory;
import com.jms.domain.ws.s.WSStk;
import com.jms.domain.ws.s.WSStkType;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMaterialBinsRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStkRepository;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.repositories.s.SYesOrNoDicRepository;
import com.jms.s.ISBinService;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class SBinService implements ISBinService {

	private static final Logger logger = LogManager.getLogger(SBinService.class.getCanonicalName());
	@Autowired
	private SStkRepository sStkRepository;
	@Autowired
	private SStatusDicRepository sStatusDicRepository;
	@Autowired
	private SStkTypeDicRepository sStkTypeDicRepository;

	@Autowired
	private SecurityUtils securityUtils;

	@Autowired
	private SYesOrNoDicRepository sYesOrNoDicRepository;

	@Autowired
	private SBinRepository sBinRepository;
	@Autowired
	private  SMaterialBinsRepository sMaterialBinsRepository;
	@Autowired
	private  SInventoryRepository sInventoryRepository;
	
	@Autowired
	private  CompanyRepository companyRepository;
	
	@Autowired  private ResourceLoader resourceLoader;

	public Valid saveBin(WSBin wsBin) {

		SBin bin;
		// create
		if (wsBin.getIdBin() == null || wsBin.getIdBin().equals(0l)) {
			bin = new SBin();
		}
		// update
		else {
			bin = sBinRepository.findOne(wsBin.getIdBin());
		}
		bin.setBin(wsBin.getBin());
		bin.setDes(wsBin.getDes());
		bin.setSStk(sStkRepository.findOne(wsBin.getIdStk()));
		bin.setSYesOrNoDic(sYesOrNoDicRepository.findOne(wsBin.getIsReturnShelf()));
		bin.setSStatusDic(sStatusDicRepository.findOne(wsBin.getStatus()));

		sBinRepository.save(bin);
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}

	@Transactional(readOnly = true)
	public List<WSBin> findBins(Long idStk) {
		List<WSBin> wsBinList = new ArrayList<WSBin>();
		for (SBin bin : sBinRepository.getByIdStk(idStk)) {
			WSBin wsBin = new WSBin();
			wsBin.setBin(bin.getBin());
			wsBin.setDes(bin.getDes());
			wsBin.setIdBin(bin.getIdBin());
			wsBin.setIsReturnShelf(bin.getSYesOrNoDic().getId());
			wsBin.setIsReturnShelfName(bin.getSYesOrNoDic().getName());
			wsBin.setStatusName(bin.getSStatusDic().getName());
			wsBin.setStatus(bin.getSStatusDic().getId());
			wsBinList.add(wsBin);
		}

		return wsBinList;
	}

	@Transactional(readOnly = true)
	public List<WSSelectObj> findBinsObjs(Long idStk) {
		List<WSSelectObj> wsBinList = new ArrayList<WSSelectObj>();
		for (SBin bin : sBinRepository.getByIdStk(idStk)) {
			WSSelectObj o = new WSSelectObj(bin.getIdBin(), bin.getBin());
			//System.out.println("bin: " + bin.getBin());
			wsBinList.add(o);
		}

		return wsBinList;
	}
	
	@Transactional(readOnly = true)
	public List<WSSelectObj> getBinsByStkIdAndMaterialIdAMethod(Long idStk,Long idMaterial) {
		List<WSSelectObj> wsBinList = new ArrayList<WSSelectObj>();
		List<SMaterialBins> sms =sMaterialBinsRepository.getByMaterialIdAndStkId(idMaterial, idStk);
		if(sms==null||sms.isEmpty())
		{
			for (SBin bin : sBinRepository.getByIdStk(idStk)) {
				WSSelectObj o = new WSSelectObj(bin.getIdBin(), bin.getBin());
				wsBinList.add(o);
			}
		}
		else
		{
			for(SMaterialBins s:sms)
			{
				Long binId = s.getId().getIdBin();
				SBin sbin=sBinRepository.findOne(binId);
				WSSelectObj o = new WSSelectObj(sbin.getIdBin(), sbin.getBin());
				wsBinList.add(o);
	
			}
			
		}
	
		return wsBinList;
	}
	

	@Transactional(readOnly = true)
	public List<WSSelectObj> getBinsByStkIdAndMaterialIdBMethod(Long idStk,Long idMaterial) {
		// System.out.println("idStk: " + idStk +", idMat: " +idMaterial );
	    List<WSSelectObj> wsBinList = new ArrayList<WSSelectObj>();
		    SStk stk=sStkRepository.findOne(idStk);
		    if(stk.getSStkTypeDic().getIdStkType().equals(8l)) //CCA帐号
		    {
		    	for(SBin sbin:sBinRepository.getByIdStk(idStk))
		    	{
		    		WSSelectObj w = new WSSelectObj(sbin.getIdBin(),sbin.getBin());
		    		//System.out.println("idSBin: " + s.getSBin().getIdBin());
			    	wsBinList.add(w);
		    	}
		    	return wsBinList;
		    }
	
			Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
			List<SInventory> ls = sInventoryRepository.findInventoryByMaterialAndStk(idMaterial, companyId, idStk);
			Map<Long,Long> binMap = new HashMap<Long,Long>();
		    for(SInventory s: ls)
		    {
		    	if(!binMap.containsKey(s.getSBin().getIdBin()))
		    	{
		    		WSSelectObj w = new WSSelectObj(s.getSBin().getIdBin(),s.getSBin().getBin());
		    		//System.out.println("idSBin: " + s.getSBin().getIdBin());
			    	wsBinList.add(w);
			    	binMap.put(s.getSBin().getIdBin(), s.getSBin().getIdBin());
		    	}
		    
	
		    }
		return wsBinList;
	}
	
	
	@Transactional(readOnly = true)
	public List<WSSelectObj> getBinsByStkIdAndMaterialIdCMethod(Long idStk,Long idMaterial) {
		List<WSSelectObj> wsBinList = new ArrayList<WSSelectObj>();
		List<SMaterialBins> sms =sMaterialBinsRepository.getByMaterialIdAndStkId(idMaterial, idStk);
		if(sms==null||sms.isEmpty())
		{
//			logger.debug("get bins by stkId: " + idStk);
			for (SBin bin : sBinRepository.getByIdStk(idStk)) {
//				if(bin.getSYesOrNoDic().getId().equals(1l))
//				{
//			    	logger.debug("add bin: " + bin.getIdBin());
					WSSelectObj o = new WSSelectObj(bin.getIdBin(), bin.getBin());
					wsBinList.add(o);
//				}
			
			}
		}
		else
		{
			for(SMaterialBins s:sms)
			{
				Long binId = s.getId().getIdBin();
				SBin sbin=sBinRepository.findOne(binId);
//				if(sbin.getSYesOrNoDic().getId().equals(1l))
//				{
					WSSelectObj o = new WSSelectObj(sbin.getIdBin(), sbin.getBin());
					wsBinList.add(o);
//				}
	
			}
			
		}
	
		return wsBinList;
	}
	

	@Transactional(readOnly = true)
	public WSBin findBin(Long binId) {
		SBin bin = sBinRepository.findOne(binId);
		WSBin wsBin = new WSBin();
		if (bin == null)
			return wsBin;
		wsBin.setBin(bin.getBin());
		wsBin.setIdBin(bin.getIdBin());
		wsBin.setIsReturnShelf(bin.getSYesOrNoDic().getId());
		wsBin.setIsReturnShelfName(bin.getSYesOrNoDic().getName());
		wsBin.setStatusName(bin.getSYesOrNoDic().getName());
		wsBin.setStatus(bin.getSStatusDic().getId());
		wsBin.setDes(bin.getDes());
		return wsBin;
	}
	
	
//	@Transactional(readOnly = true)
//	public SBin findSystemBinByStkTypeId(Long stkTypeId) {
//		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
//		return sBinRepository.getByCompanyIdAndStkType(companyId, stkTypeId);
//	}

	
	@Transactional(readOnly=true)
	public Boolean checkBinName(String binName, Long idBin) {

		//System.out.println("idBin: " + idBin);
		String dbBinName = "";
		// 已有修改
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		if (idBin != null) {

			SBin bin =sBinRepository.findOne(idBin);
			dbBinName= bin.getBin();
			//System.out.println("dbBinName: " + dbBinName);
			if (binName != null && !binName.isEmpty()) {
				if (sBinRepository.getByCompanyIdAndBinName(companyId, binName) == null
						|| binName.equals(dbBinName)) {
					return true;
				} else {
					return false;
				}
			} else
				return true;
		} else {
			if (binName == null || binName.isEmpty())
				return false;
			else {
				if (sBinRepository.getByCompanyIdAndBinName(companyId, binName) == null) {
					return true;
				} else {
					return false;
				}
			}
		}
	}
	
	
	
	@Transactional(readOnly = true)
	public WSBin getSystemBinByStkTypeIdAndBinName(Long stkTypeId,String binName) {
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		SBin bin = sBinRepository.getByCompanyIdAndStkTypeAndBinName(companyId, stkTypeId,binName);
		WSBin wsBin = new WSBin();
		if (bin == null)
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
	 * @Transactional(readOnly=false) public Valid invalidateBin(Long stkId) {
	 * 
	 * SStk stk = sStkRepository.findOne(stkId);
	 * stk.setSStatusDic(sStatusDicRepository.getBySourceAndName("s_stk","无效"));
	 * sStkRepository.save(stk); Valid valid = new Valid();
	 * valid.setValid(true); return valid; }
	 */
	
	@Transactional(readOnly = false)
	public Valid deleteBin(Long binId) {
		Valid valid = new Valid();
		SBin bin = sBinRepository.findOne(binId);
		if (!bin.getSInventories().isEmpty()) {
			valid.setValid(false);
		} else {
			sBinRepository.delete(binId);
			valid.setValid(true);
		}

		return valid;
	}
	
	
	
	
	@Transactional(readOnly = false)
	public SBin saveSystemBin(Long companyId,String name) {
	
		
		List<SStk> stks = sStkRepository.findByIdCompanyAndStkName(companyId, name);
		//System.out.println("company id: " + companyId +", stkname: " + name);
		if(stks.isEmpty())
		{
			//System.out.println("can not find company id: " + companyId +", stkname: " + name);
			SStk stk = new SStk();
			stk.setCompany(companyRepository.findOne(companyId));
			stk.setDes(name);
			stk.setSStatusDic(sStatusDicRepository.findOne(27l));
			stk.setSStkTypeDic(sStkTypeDicRepository.findOne(9l));
			stk.setStkName(name);
			stk = sStkRepository.save(stk);
			SBin bin = new SBin();
			bin.setBin(name);
			bin.setSStk(stk);
			bin.setDes(name);

			bin.setSStatusDic(sStatusDicRepository.findOne(25l));
			bin.setSYesOrNoDic(sYesOrNoDicRepository.findOne(2l));
			bin = sBinRepository.save(bin);
			return bin;
		}
		else
		{
			SStk stk = stks.get(0);
			if(stk.getSBins().isEmpty())
			{
				SBin bin = new SBin();
				bin.setBin(name);
				bin.setSStk(stk);
				bin.setDes(name);

				bin.setSStatusDic(sStatusDicRepository.findOne(25l));
				bin.setSYesOrNoDic(sYesOrNoDicRepository.findOne(2l));
				bin = sBinRepository.save(bin);
				return bin;
			}
			else
			{
				return stk.getSBins().iterator().next();
			}
		}

		
	}

	@Override
	@Transactional(readOnly = false)
	public void loadSystemBins(Long companyId) {

//		Company company = companyRepository.findOne(companyId);
//		 Resource resource = resourceLoader.getResource("classpath:data/sysstks.csv");
//		CsvReader reader;
//		try {
//			reader = new CsvReader(resource.getInputStream(), ',', Charset.forName("UTF-8"));
//			reader.readHeaders();
//			while (reader.readRecord()) {
//				SStk s = new SStk();
//				s.setStkName(reader.get(0));
//				s.setDes(reader.get(1));
//				s.setCompany(company);
//				s.setSStatusDic(sStatusDicRepository.findOne(27l));//有效
//				s.setSStkTypeDic(sStkTypeDicRepository.findOne(Long.parseLong(reader.get(2))));
//				s = sStkRepository.save(s);
//				SBin bin = new SBin();
//				bin.setBin(reader.get(0));
//				bin.setSStk(s);
//				bin.setDes(reader.get(1));
//
//				bin.setSStatusDic(sStatusDicRepository.findOne(25l));
//				bin.setSYesOrNoDic(sYesOrNoDicRepository.findOne(2l));
//				bin = sBinRepository.save(bin);
//			}
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		Company company = companyRepository.findOne(companyId);
		//IQC,来料检验仓,5
		SStk s = new SStk();
		s.setStkName("IQC");
		s.setDes("来料检验仓");
		s.setCompany(company);
		s.setSStatusDic(sStatusDicRepository.findOne(27l));//有效
		s.setSStkTypeDic(sStkTypeDicRepository.findOne(5l));
		s = sStkRepository.save(s);
		SBin bin = new SBin();
		bin.setBin("IQC");
		bin.setSStk(s);
		bin.setDes("来料检验仓");

		bin.setSStatusDic(sStatusDicRepository.findOne(25l));
		bin.setSYesOrNoDic(sYesOrNoDicRepository.findOne(2l));
		bin = sBinRepository.save(bin);
		
	
	}

}
