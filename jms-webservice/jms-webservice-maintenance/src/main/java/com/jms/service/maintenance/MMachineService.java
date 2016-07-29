package com.jms.service.maintenance;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.jms.domain.db.MMachine;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.m.WSMachine;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.m.MMachineGroupRepository;
import com.jms.repositories.m.MMachineRepository;
import com.jms.repositories.m.MStatusDicRepository;
import com.jms.repositories.p.PLineRepository;
import com.jms.repositories.p.PWipRepository;
import com.jms.repositories.p.PWorkCenterRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class MMachineService {

	private static final Logger logger = LogManager.getLogger(MMachineService.class);


	@Autowired
	private MMachineRepository mMachineRepository;
	@Autowired
	private MMachineGroupRepository mMachineGroupRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private  MStatusDicRepository mStatusDicRepository;
	@Autowired
	private  PLineRepository pLineRepository;
	@Autowired
	private PWipRepository pWipRepository;
	@Autowired
	private PWorkCenterRepository pWorkCenterRepository;
	@Autowired
	private SBinRepository sBinRepository;
	@Autowired private SecurityUtils securityUtils;

	
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> getMachinesObj()
	{
		List<WSSelectObj> wss = new ArrayList<WSSelectObj>();
		for(MMachine m :mMachineRepository.getActiveMachinesByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
		{
			WSSelectObj ws = new WSSelectObj();
			ws.setId(m.getIdMachine());
			ws.setName(m.getCode()+"_" +m.getSpec());
			wss.add(ws);
		}
		return wss;
	}
	
	
	@Transactional(readOnly=true)
	public List<WSSelectObj> getMachinesObjByLineId(Long lineId)
	{
		List<WSSelectObj> wss = new ArrayList<WSSelectObj>();
		//for(MMachine m :mMachineRepository.getByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany()))
		for(MMachine m :mMachineRepository.getByLineId(lineId))
		{
			WSSelectObj ws = new WSSelectObj();
			ws.setId(m.getIdMachine());
			ws.setName(m.getCode()+"_" +m.getSpec());
			wss.add(ws);
		}
		return wss;
	}
	
	
	public void loadMachinesForSandVik(InputStream inputStream, Long companyId)throws IOException {
		
		CsvReader reader = new CsvReader(inputStream,',', Charset.forName("UTF-8"));
		//reader.readHeaders();
		while(reader.readRecord())
		{
          MMachine m = new MMachine();
          m.setCode(reader.get(0));
          m.setSpec(reader.get(1));
          if(reader.get(2)!=null&&!reader.get(2).isEmpty())
          {
        	  m.setTotalKwa(Long.parseLong(reader.get(2)));
          }
          m.setMMachineGroup(mMachineGroupRepository.findByGroupNameAndIdCompany(reader.get(3),securityUtils.getCurrentDBUser().getCompany().getIdCompany()));
          m.setCompany(companyRepository.findOne(companyId));
	
          m.setMStatusDic(mStatusDicRepository.getBySourceAndName("m_machine", reader.get(4)));
          mMachineRepository.save(m);
		}
		
	}
	@Transactional(readOnly=true)
	public WSMachine findMachine(Long machineId) throws Exception{	
		MMachine m =mMachineRepository.findOne(machineId);
		return toWSMachine(m);
	}
	public WSMachine saveMachine(WSMachine wsMachine) throws Exception
	{
		MMachine mMachine;
		if(wsMachine.getIdMachine()!=null&&!wsMachine.getIdMachine().equals(0l))
		{
			mMachine = mMachineRepository.findOne(wsMachine.getIdMachine());
		}
		else
		{
			mMachine = new MMachine();
	
		}
		MMachine dbMMachine = toDBMMachine(wsMachine, mMachine);
		dbMMachine = mMachineRepository.save(dbMMachine);
		wsMachine.setIdMachine(dbMMachine.getIdMachine());
		return wsMachine;
		
	}
	
	
	@Transactional(readOnly=false)
	public Valid deleteMachine(Long idMachine)
	{
		Valid valid = new Valid();
		MMachine mMachine = mMachineRepository.findOne(idMachine);
		if(!mMachine.getPCPps().isEmpty())
		{
			valid.setValid(false);
			valid.setMsg("该设备被使用过，故不能被删除，可以选择停用该设备！");
			return valid;
		}
		else
		{
			mMachineRepository.delete(idMachine);
			valid.setValid(true);
			return valid;
		}
	}
	protected MMachine toDBMMachine(WSMachine wsMachine,MMachine mMachine) throws Exception
	{
		MMachine dbMMachine= (MMachine)BeanUtil.shallowCopy(wsMachine, MMachine.class, mMachine);
		if(wsMachine.getIdGroup()!=null)
		{
			dbMMachine.setMMachineGroup(mMachineGroupRepository.findOne(wsMachine.getIdGroup()));
		}
		if(wsMachine.getIdLine()!=null)
		{
			dbMMachine.setPLine(pLineRepository.findOne(wsMachine.getIdLine()));
		}
		if(wsMachine.getIdLocation()!=null)
		{
			dbMMachine.setSBin(sBinRepository.findOne(wsMachine.getIdLocation()));	
		}
		if(wsMachine.getIdStatus()!=null)
		{
			dbMMachine.setMStatusDic(mStatusDicRepository.findOne(wsMachine.getIdStatus()));
		}
		if(wsMachine.getIdWip()!=null)
		{
			dbMMachine.setPWip(pWipRepository.findOne(wsMachine.getIdWip()));
		}
		if(wsMachine.getIdWc()!=null)
		{
			dbMMachine.setPWorkCenter(pWorkCenterRepository.findOne(wsMachine.getIdWc()));
		}
	    return dbMMachine;
	}
	protected WSMachine toWSMachine(MMachine mMachine) throws Exception
	{
		WSMachine pc = (WSMachine)BeanUtil.shallowCopy(mMachine, WSMachine.class, null);
		if(mMachine.getMMachineGroup()!=null)
		{
			pc.setGroup(mMachine.getMMachineGroup().getGroupName());
			pc.setIdGroup(mMachine.getMMachineGroup().getIdGroup());
		}
		if(mMachine.getMStatusDic()!=null)
		{
			pc.setStatus(mMachine.getMStatusDic().getName());
			pc.setIdStatus(mMachine.getMStatusDic().getIdMstatusDic());
		}
		if(mMachine.getPLine()!=null)
		{
			pc.setIdLine(mMachine.getPLine().getIdPline());
			pc.setLine(mMachine.getPLine().getPline());
		}
		if(mMachine.getPWip()!=null)
		{
			pc.setWip(mMachine.getPWip().getWip());
			pc.setIdWip(mMachine.getPWip().getIdWip());
		}
		if(mMachine.getPWorkCenter()!=null)
		{
			pc.setIdWc(mMachine.getPWorkCenter().getIdWc());
			pc.setWc(mMachine.getPWorkCenter().getWorkCenter());
		}
		if(mMachine.getSBin()!=null)
		{
			pc.setIdLocation(mMachine.getSBin().getIdBin());
			pc.setLocation(mMachine.getSBin().getBin());
		}
		
	    return pc;
	}
}
