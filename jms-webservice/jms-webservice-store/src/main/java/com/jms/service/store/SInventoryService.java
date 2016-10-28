package com.jms.service.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.NotificationMethodEnum;
import com.jms.domain.db.Groups;
import com.jms.domain.db.MMachine;
import com.jms.domain.db.NotiMethod;
import com.jms.domain.db.Notification;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PStopsPlan;
import com.jms.domain.db.Receiver;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SInventory;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SStk;
import com.jms.domain.db.Users;
import com.jms.domain.ws.s.WSInventory;
import com.jms.domain.ws.s.WSInventoryInfo;
import com.jms.email.EmailSenderService;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStkRepository;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.repositories.s.SYesOrNoDicRepository;
import com.jms.repositories.system.NotiMethodRepository;
import com.jms.repositories.user.GroupMemberRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class SInventoryService {

	private static final Logger logger = LogManager.getLogger(SInventoryService.class.getCanonicalName());
	
	@Autowired
	private SInventoryRepository sInventoryRepository;
	
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
	private NotiMethodRepository notiMethodRepository;
	
	@Autowired
	private SMaterialRepository sMaterialRepository;
	

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	@Autowired
	private EmailSenderService emailSenderService;

	@Transactional(readOnly=true)
	public List<WSInventoryInfo> findInventorySummaryByMaterialAndStk(Long idMaterial,Long stkId)
	{
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SInventory> ls;
		if(idMaterial==null)
		{
			if(stkId==null)
			{
				ls = new ArrayList<SInventory>();
			}
			else
			{
				ls = sInventoryRepository.findInventorySummaryByStk(companyId, stkId);
			}
			
		}
		else
		{
			if(stkId==null)
			{
				ls = sInventoryRepository.findInventorySummaryByMaterial(idMaterial, companyId);
			}
			else
			{
				ls = sInventoryRepository.findInventorySummaryByMaterialAndStk(idMaterial, companyId, stkId);
			}
		}
		List<WSInventoryInfo> infos = new ArrayList<WSInventoryInfo>();
		Long currentStkId=0l,currentMaterialId=0l,currentQty=0l;
		SInventory before=null;
		int num=0;
	    for(SInventory s: ls)
	    {
	    	Long sId = s.getSBin().getSStk().getId();
	    	Long mId = s.getSMaterial().getIdMaterial();
	    	if(sId.equals(currentStkId)&&mId.equals(currentMaterialId))
	    	{
	    		currentQty =currentQty + s.getQty();
	    	}
	    	else
	    	{
	    		currentStkId = sId;
	    		currentMaterialId = mId;
	    	//	currentQty=s.getQty();
	    		if(!currentQty.equals(0l))
	    		{
	    			WSInventoryInfo i = new WSInventoryInfo();
	    			i.setDes(before.getSMaterial().getDes());
	    			i.setPno(before.getSMaterial().getPno());
	    			i.setRev(before.getSMaterial().getRev());
	    			i.setIdMaterial(before.getSMaterial().getIdMaterial());
	    			i.setStkId(before.getSBin().getSStk().getId());
	    			i.setStkName(before.getSBin().getSStk().getStkName());
	    			i.setQty(currentQty);
	    			infos.add(i);
	    		}
	    		currentQty = s.getQty();
	    	}
	    	
	    	before = s;
	    	num++;
	    	if(num==ls.size()&&!currentQty.equals(0l))
	    	{
	    		WSInventoryInfo i = new WSInventoryInfo();
    			i.setDes(before.getSMaterial().getDes());
    			i.setPno(before.getSMaterial().getPno());
    			i.setRev(before.getSMaterial().getRev());
    			i.setIdMaterial(before.getSMaterial().getIdMaterial());
    			i.setStkId(before.getSBin().getSStk().getId());
    			i.setStkName(before.getSBin().getSStk().getStkName());
    			i.setQty(currentQty);
    			infos.add(i);
	    	}
	    }
	    return infos;
	}
	
	
	
	
	@Transactional(readOnly=true)
	public List<WSInventory> findInventoryDetailByMaterialAndStk(Long idMaterial,Long stkId)
	{		
	//	logger.debug("material id: " + idMaterial +", stkId: " + stkId);
		Long companyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
		List<SInventory> ls;
		if(idMaterial==null)
		{
			if(stkId==null)
			{
				ls = new ArrayList<SInventory>();
			}
			else
			{
				ls = sInventoryRepository.findInventorySummaryByStk(companyId, stkId);
			}
			
		}
		else
		{
			if(stkId==null)
			{
				ls = sInventoryRepository.findInventorySummaryByMaterial(idMaterial, companyId);
			}
			else
			{
				ls = sInventoryRepository.findInventorySummaryByMaterialAndStk(idMaterial, companyId, stkId);
			}
		}
		List<WSInventory> infos = new ArrayList<WSInventory>();
	
	    for(SInventory s: ls)
	    {
	    	if(!s.getQty().equals(0l))
	    	{

    			WSInventory i = new WSInventory();
    			i.setInventoryId(s.getIdInv());
    			i.setDes(s.getSMaterial().getDes());
    			i.setPno(s.getSMaterial().getPno());
    			i.setRev(s.getSMaterial().getRev());
    			i.setIdMaterial(s.getSMaterial().getIdMaterial());
    			i.setStkId(s.getSBin().getSStk().getId());
    			i.setStkName(s.getSBin().getSStk().getStkName());
    			i.setBinName(s.getSBin().getBin());
    			i.setBinId(s.getSBin().getIdBin());
    			i.setQty(s.getQty());
    			i.setBox(s.getBox());
    			i.setLotNo(s.getLotNo());
    			infos.add(i);
	    		
	    	}
	    	
	    	
	    	
	    	
	    }
	    return infos;
	}
	
	
	
	@Transactional(readOnly=false) 
	public void emailAlert()
	{
		Long companyId =8l;
		//NotiMethod method = notiMethodRepository.findByMethod(NotificationMethodEnum.sys.name());
		List<Long> types = new ArrayList<Long>();
		types.add(4l);
		types.add(5l);
		List<SMaterial> materials = sMaterialRepository.getByCompanyIdAndMaterialTypes(8l, types);
		
		int sendNum=0;
		for(SMaterial material : materials)
		{
			if(material.getSafetyInv()!=null&&material.getSafetyInv()>0)
			{
				 Long invQty = 0l;
				 SStk stk =sStkRepository.findByIdCompanyAndStkName(companyId, "IDI");
					
	             if(stk!=null)
	             {
	            	List<SInventory> s =  sInventoryRepository.findInventoryByMaterialAndStk(material.getIdMaterial(), companyId, stk.getId());
	 		        for(SInventory i:s)
	 		        {
	 		        	invQty =invQty+i.getQty();
	 		        }
	             
	             }
				if(material.getEmailSended()==null) //未发邮件
				{
					if(invQty<material.getSafetyInv()) //发邮件
					{
						if(sendNum>0)
							break;
						sendNum=1;
						System.out.println("sendNum: " + sendNum);
						String title = "物料低于安全库存";
						String emailTemplate = "materialTemplate.vm";
						List<Users> whs = groupMemberRepository.findWarehouseByCompanyId(companyId);
						  Map<String,Object> model =new HashMap<String, Object>();
						  model.put("pno",material.getPno());
						  List<String> ems = new ArrayList<String>();
						for(Users u:whs)
						{
							if(u.getEmail()!=null&&!u.getEmail().isEmpty())
							{
								ems.add(u.getEmail());
								System.out.println(u.getEmail());
							}
						
						}
						String[] emails = new String[ems.size()];
						ems.toArray(emails); // fill the array
						
						
						try{
							
							emailSenderService.sendEmail(emails,emailTemplate,title, model, null);
							
						}catch(Exception e){
							
							e.printStackTrace();
						}
					
						material.setEmailSended(1l);
						sMaterialRepository.save(material);
					}
				}
				else // 已发邮件
				{
					if(invQty>material.getSafetyInv()) //发邮件
					{
						material.setEmailSended(null);
						sMaterialRepository.save(material);
					}
				}
			}
            
            
         
		
		}
	}
	
	
  
}
