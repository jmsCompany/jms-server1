package com.jms.service.store;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.Config;
import com.jms.domain.GroupTypeEnum;
import com.jms.domain.SystemRoleEnum;
import com.jms.domain.db.Company;
import com.jms.domain.db.Groups;
import com.jms.domain.db.Roles;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SStk;
import com.jms.domain.db.SStkTypeDic;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.s.WSStk;
import com.jms.domain.ws.s.WSStkType;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStkRepository;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class SStkService {

	private static final Logger logger = LogManager.getLogger(SStkService.class
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
	private CompanyRepository companyRepository;
	

	public Valid saveStk(WSStk wsStk) {
		
		SStk stk;
		//create
		if(wsStk.getIdStk()==null||wsStk.getIdStk().equals(0l))
		{
			 stk=new SStk();
		}
		//update
		else
		{
			//System.out.println("stkId" +wsStk.getIdStk());
			 stk = sStkRepository.findOne(wsStk.getIdStk());	
		}
		stk.setAddress(wsStk.getAddress());
		stk.setDes(wsStk.getDes());
		stk.setStkName(wsStk.getStkName());
		stk.setSStatusDic(sStatusDicRepository.findOne(wsStk.getStatus()));
		stk.setSStkTypeDic(sStkTypeDicRepository.findOne(wsStk.getIdStkType()));
		stk.setUsers(securityUtils.getCurrentDBUser());
		stk.setCompany(securityUtils.getCurrentDBUser().getCompany());
		sStkRepository.save(stk);
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}
	
	@Transactional(readOnly=true)
	public List<WSStk> findStks(Long statusId)
	{
		List<WSStk> wsStkList = new ArrayList<WSStk>();
		for(SStk stk: sStkRepository.findByIdCompanyAndStatus(securityUtils.getCurrentDBUser().getCompany().getIdCompany(),statusId))
		{
			WSStk wsStk = new WSStk();
			wsStk.setAddress(stk.getAddress());
			wsStk.setDes(stk.getDes());
			wsStk.setIdStk(stk.getId());
			wsStk.setIdStkType(stk.getSStkTypeDic().getIdStkType());
			wsStk.setStatus(stk.getSStatusDic().getId());
			wsStk.setStkName(stk.getStkName());
			wsStkList.add(wsStk);
		}
		
		return wsStkList;
	}

	
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> findStksSelectObj(Long statusId)
	{
		List<WSSelectObj> wsStkList = new ArrayList<WSSelectObj>();
		for(SStk stk: sStkRepository.findByIdCompanyAndStatus(securityUtils.getCurrentDBUser().getCompany().getIdCompany(),statusId))
		{
			WSSelectObj o = new WSSelectObj(stk.getId(),stk.getStkName());
			wsStkList.add(o);
		}
		
		return wsStkList;
	}
	
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> findStksSelectObjByTypes(Long statusId,List<Long> types)
	{
		List<WSSelectObj> wsStkList = new ArrayList<WSSelectObj>();
		for(SStk stk: sStkRepository.findByIdCompanyAndStatusAndTypes(securityUtils.getCurrentDBUser().getCompany().getIdCompany(),statusId,types))
		{
			WSSelectObj o = new WSSelectObj(stk.getId(),stk.getStkName());
			wsStkList.add(o);
		}
		
		return wsStkList;
	}
	
	
	@Transactional(readOnly=true)
	public WSStk findStk(Long stkId)
	{
		
		SStk stk = sStkRepository.findOne(stkId);
		
			WSStk wsStk = new WSStk();
			if(stk==null)
				return wsStk;
			wsStk.setAddress(stk.getAddress());
			wsStk.setDes(stk.getDes());
			wsStk.setIdStk(stk.getId());
			wsStk.setIdStkType(stk.getSStkTypeDic().getIdStkType());
			wsStk.setStatus(stk.getSStatusDic().getId());
			wsStk.setStkName(stk.getStkName());
		
		
		return wsStk;
	}

	
	
	
	@Transactional(readOnly=false)
	public Valid invalidateStk(Long stkId)
	{
		
		SStk stk = sStkRepository.findOne(stkId);
		stk.setSStatusDic(sStatusDicRepository.getBySourceAndName("s_stk","无效"));
		sStkRepository.save(stk);
		Valid valid = new Valid();
		valid.setValid(true);
		return valid;
	}

	@Transactional(readOnly=false)
	public Valid deleteStk(Long stkId)
	{
		Valid valid = new Valid();
		SStk stk = sStkRepository.findOne(stkId);
		if(!stk.getSMtfsForFromStk().isEmpty()||!stk.getSMtfsForToStk().isEmpty()||!stk.getSBins().isEmpty())
		{
			valid.setValid(false);
		}
		else
		{
			sStkRepository.delete(stkId);
			valid.setValid(true);
		}
		
		return valid;
	}
	
	public void loadStksByCompanyId(InputStream inputStream, Long companyId) throws IOException {

		Company company = companyRepository.findOne(companyId);
		
		CsvReader reader = new CsvReader(inputStream, ',', Charset.forName("UTF-8"));
		reader.readHeaders();
		while (reader.readRecord()) {
			SStk s = new SStk();
			s.setStkName(reader.get(0));
			s.setDes(reader.get(1));
			s.setCompany(company);
			s.setSStatusDic(sStatusDicRepository.findOne(27l));//有效
			s.setSStkTypeDic(sStkTypeDicRepository.findOne(Long.parseLong(reader.get(2))));
			sStkRepository.save(s);
		}
	}
	
	
	@Transactional(readOnly=true)
	public Boolean checkStkName(String stkName, Long idStk) {

		String dbStkName = "";
		// 已有仓库修改
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		if (idStk != null) {

			SStk stk =sStkRepository.findOne(idStk);
			dbStkName= stk.getStkName();
			if (stkName != null && !stkName.isEmpty()) {
				if (sStkRepository.findByIdCompanyAndStkName(companyId, stkName) == null
						|| stkName.equals(dbStkName)) {
					return true;
				} else {
					return false;
				}
			} else
				return true;
		} else {
			if (stkName == null || stkName.isEmpty())
				return false;
			else {
				if (sStkRepository.findByIdCompanyAndStkName(companyId, stkName) == null) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	
	
	
	@Transactional(readOnly=true)
	public WSStk findByStkName(String stkName)
	{
		
		    List<SStk> stks = sStkRepository.findByIdCompanyAndStkName(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), stkName);
		
			WSStk wsStk = new WSStk();
			if(stks.isEmpty())
				return wsStk;
			SStk stk = stks.get(0);
			wsStk.setAddress(stk.getAddress());
			wsStk.setDes(stk.getDes());
			wsStk.setIdStk(stk.getId());
			wsStk.setIdStkType(stk.getSStkTypeDic().getIdStkType());
			wsStk.setStatus(stk.getSStatusDic().getId());
			wsStk.setStkName(stk.getStkName());
		
		
		return wsStk;
	}
}
