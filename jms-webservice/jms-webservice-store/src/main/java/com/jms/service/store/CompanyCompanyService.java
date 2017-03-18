package com.jms.service.store;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.Company;
import com.jms.domain.db.SComCom;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.s.WSSComCom;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.s.SComComRepository;
import com.jms.web.security.SecurityUtils;


@Service
@Transactional
public class CompanyCompanyService {

	private static final Logger logger = 
			LogManager.getLogger(
			CompanyCompanyService.class
			.getCanonicalName());
	
	@Autowired private SComComRepository sComComRepository;
	@Autowired private CompanyRepository companyRepository;
	@Autowired private  SecurityUtils securityUtils;
	@Autowired private SBinService sBinService;
	
	
	@Transactional(readOnly=true)
	public List<WSSComCom> findMyApplied() throws Exception {
		Long idCompany = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SComCom> coCompanies= sComComRepository.findMyApplies(idCompany);
	    List<WSSComCom> wsSCompanyCos = new ArrayList<WSSComCom>();
		for(SComCom dbc:coCompanies)
		{
			wsSCompanyCos.add(toWSComCom(dbc));
		}
		return wsSCompanyCos;
		
	}
	

	
	@Transactional(readOnly=true)
	public WSSComCom findCompanyCompany(Long id) throws Exception {
		SComCom dbc= sComComRepository.findOne(id);
	    return  toWSComCom(dbc);		
	}
	
	
	@Transactional(readOnly=true)
	public List<WSSComCom> findMyAudits() throws Exception {
		Long idCompany = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SComCom> coCompanies= sComComRepository.findMyAudits(idCompany);
	    List<WSSComCom> wsSCompanyCos = new ArrayList<WSSComCom>();
		for(SComCom dbc:coCompanies)
		{
			wsSCompanyCos.add(toWSComCom(dbc));
		}
		return wsSCompanyCos;
	}
	
	
	
	@Transactional(readOnly=true)
	public List<WSSComCom> findMyAgrees() throws Exception {
		Long idCompany = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SComCom> coCompanies= sComComRepository.findAgrees(idCompany);
	    List<WSSComCom> wsSCompanyCos = new ArrayList<WSSComCom>();
		for(SComCom dbc:coCompanies)
		{
			wsSCompanyCos.add(toWSComCom(dbc));
		}
		return wsSCompanyCos;
	}
	
	
	
	@Transactional(readOnly=true)
	public List<WSSComCom> findDegrees() throws Exception {
		Long idCompany = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SComCom> coCompanies= sComComRepository.findDegrees(idCompany);
	    List<WSSComCom> wsSCompanyCos = new ArrayList<WSSComCom>();
		for(SComCom dbc:coCompanies)
		{
			wsSCompanyCos.add(toWSComCom(dbc));
		}
		return wsSCompanyCos;
		
	}
	
	
	@Transactional(readOnly=false)
	public Valid updateWSComComStatus(WSSComCom wsComCom) throws Exception {
		SComCom companyCo = sComComRepository.findOne(wsComCom.getId());
		companyCo.setStatus(wsComCom.getStatus());
		sComComRepository.save(companyCo);
		Valid v = new Valid();
		v.setValid(true);
		return v;
	}
	

	@Transactional(readOnly=false)
	public WSSComCom saveWSComCom(WSSComCom wsComCom) throws Exception {
		SComCom companyCo;
		
		if(wsComCom.getId()!=null&&!wsComCom.getId().equals(0l))
		{
			companyCo = sComComRepository.findOne(wsComCom.getId());
		}
		else
		{
			companyCo = new SComCom();
		}
		SComCom dbCompanyCo = toDBComCom(wsComCom,companyCo);
		
		Company c2 = companyRepository.findByCompanyName(wsComCom.getCompany2());
		dbCompanyCo.setIdCompany2(c2.getIdCompany());
		
		dbCompanyCo.setCompany1(securityUtils.getCurrentDBUser().getCompany().getCompanyName());
		dbCompanyCo.setIdCompany1(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		
		
		//create new con stk
	
	     sBinService.saveSystemBin(companyId, "CON");
	    
	     sBinService.saveSystemBin(c2.getIdCompany(), "CON");
		
//		SMaterial m = sMaterialRepository.getOne(wsComCom.getMaterialId());
//		dbCompanyCo.setPno(m.getPno());
//		dbCompanyCo.setRev(m.getRev());
//		dbCompanyCo.setDes(m.getDes());
		
		sComComRepository.save(dbCompanyCo);
		wsComCom.setId(dbCompanyCo.getId());
		return wsComCom;		
		
	}
	
	
	
	private SComCom toDBComCom(WSSComCom wsComCom,SComCom companyCo) throws Exception
	{
		SComCom dbCompanyCo = (SComCom)BeanUtil.shallowCopy(wsComCom, SComCom.class, companyCo);
		return dbCompanyCo;
	}
	
	
	
	private WSSComCom toWSComCom(SComCom dbc) throws Exception
	{
		WSSComCom fc = (WSSComCom)BeanUtil.shallowCopy(dbc, WSSComCom.class, null);
	    return fc;
	}

	
	
}
