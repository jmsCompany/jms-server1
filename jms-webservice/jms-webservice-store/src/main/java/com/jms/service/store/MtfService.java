package com.jms.service.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.SInventory;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.store.WSSMtf;
import com.jms.domain.ws.store.WSSMtfMaterial;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.s.SMtfRepository;
import com.jms.repositories.s.SMtfTypeDicRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStkRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class MtfService {

	private static final Logger logger = LogManager.getLogger(MtfService.class.getCanonicalName());

	@Autowired
	private SStatusDicRepository sStatusDicRepository;
	@Autowired
	private SMtfMaterialRepository sMtfMaterialRepository;
	@Autowired
	private MtfMaterialService mtfMaterialService;

	@Autowired
	private SMtfRepository sMtfRepository;
	@Autowired
	private SecurityUtils securityUtils;

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private SStkRepository sStkRepository;
	@Autowired
	private SMtfTypeDicRepository sMtfTypeDicRepository;
	@Autowired
	private SInventoryRepository sInventoryRepository;
	@Autowired
	private SBinRepository sBinRepository;
	@Autowired
	private SMaterialRepository sMaterialRepository;
	@Autowired
	private SSpoMaterialRepository sSpoMaterialRepository;

	@Autowired
	private SSoRepository sSoRepository;
	
	@Autowired
	private SMtfNoRepository sMtfNoRepository;
	@Autowired
	private SMtfNoService sMtfNoService;
	
	
	//校验
	public Valid updateMtfStatus(WSSMtf wsSMtf)
	{
		Valid valid = new Valid();
		if(wsSMtf.getTypeId().intValue()==1) 
		{
			for (String k : wsSMtf.getSmtfItems().keySet()) {
				WSSMtfMaterial wm = wsSMtf.getSmtfItems().get(k);
				SMtfMaterial sMtfMaterial =sMtfMaterialRepository.findOne(wm.getIdMtfMaterial());
				sMtfMaterial.setSStatusDic(sStatusDicRepository.findOne(wm.getStatusId()));
				sMtfMaterialRepository.save(sMtfMaterial);
			}
	
		}
		else
		{
			valid.setValid(false);
		}
	
		valid.setValid(true);
		return valid;
		
	}


	public Valid saveMtf(WSSMtf wsSMtf) {
		Valid valid = new Valid();
		try {
			Long smtfType = wsSMtf.getTypeId();
			SMtf sMtf = new SMtf();

			sMtf = toDBMtf(wsSMtf, sMtf);
			
			sMtf = sMtfRepository.save(sMtf);

			for (String k : wsSMtf.getSmtfItems().keySet()) {

				WSSMtfMaterial wm = wsSMtf.getSmtfItems().get(k);
				switch (smtfType.intValue()) {
				case 1: // 来料入检
				{
					SPoMaterial spoMaterial = sSpoMaterialRepository.getOne(wm.getPoMaterialId());
					SInventory sInventory=null;
					if (wm.getLotNo() != null) {
						sInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
								wm.getToBinId(), wm.getLotNo());
					} else {
						List<SInventory> sInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
				        if(sInventorys!=null&&!sInventorys.isEmpty())
				        {
				        	sInventory = sInventorys.get(0);
				        }
					}
					if (sInventory == null) {
						sInventory = new SInventory();
						sInventory.setCreationTime(new Date());
						sInventory.setLotNo(wm.getLotNo());
						sInventory.setQty(wm.getQty());
						sInventory.setUQty(wm.getUqty());
						sInventory.setSBin(sBinRepository.findOne(wm.getToBinId()));
						sInventory.setSMaterial(spoMaterial.getSMaterial());
					} else {
						sInventory.setQty(sInventory.getQty() + wm.getQty());
					}
					sInventoryRepository.save(sInventory);

					SPoMaterial sPoMaterial = sSpoMaterialRepository.getOne(wm.getPoMaterialId());
					if (sPoMaterial.getQtyReceived() != null) {
						sPoMaterial.setQtyReceived(sPoMaterial.getQtyReceived() + wm.getQty());

					} else {
						sPoMaterial.setQtyReceived(wm.getQty());
					}
					sSpoMaterialRepository.save(sPoMaterial);
					// update SPo
					wm.setIdMt(sMtf.getIdMt());
					mtfMaterialService.saveMtfMaterial(wm);
					
					break;
				}

				case 2: // 采购退货
				{
					logger.debug("采购退货。。。");
					SInventory sInventory=null;
					logger.debug("material Id: " + wm.getMaterialId() +", binId: " + wm.getFromBinId() +", " +wm.getLotNo());
					
					if(wm.getLotNo()!=null&&!wm.getLotNo().isEmpty())
					{
						
						logger.debug("mat no:" +wm.getMaterialId() +", lot no " + wm.getLotNo() + " wm qty: " + wm.getQty());
						sInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
								wm.getFromBinId(),wm.getLotNo());
						
						
						sInventory.setQty(sInventory.getQty() - wm.getQty());
						sInventoryRepository.save(sInventory);
					}
					else
					{
					//	sInventory = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
					//			wm.getFromBinId());
						
						List<SInventory> sInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getFromBinId());
				        if(sInventorys!=null&&!sInventorys.isEmpty())
				        {
				        	
				        	for(SInventory s :sInventorys)
				        	{
				        	
				        		if(s.getQty()>= wm.getQty())
				        		{
				        			s.setQty(s.getQty() - wm.getQty());
									sInventoryRepository.save(s);
									break;
				        		}
				        		else
				        		{	
				        			wm.setQty(wm.getQty()-s.getQty());
				        			s.setQty(0l);
									sInventoryRepository.save(s);
				        		}
								
				        	}
				        }
						
					}
				
					SPoMaterial sPoMaterial = sSpoMaterialRepository.getOne(wm.getPoMaterialId());
					if (sPoMaterial.getQtyReceived() != null) {
						sPoMaterial.setQtyReceived(sPoMaterial.getQtyReceived() - wm.getQty());

					}

					sSpoMaterialRepository.save(sPoMaterial);
					// update SPo
					
					wm.setIdMt(sMtf.getIdMt());
					mtfMaterialService.saveMtfMaterial(wm);
					break;
				}

			    case 3: // 手动流转
				case 4: // 工单流转
				{
                    logger.debug(" case 3 or 4:  to:  wm.getToBinId(): " +  wm.getToBinId() +", from :" + wm.getFromBinId() +", material id: " + wm.getMaterialId() +", qty: " +wm.getQty());
					SInventory to=null;
					
					List<SInventory> sInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
			        if(sInventorys!=null&&!sInventorys.isEmpty())
			        {
			        	to = sInventorys.get(0);
						to.setQty(to.getQty() + wm.getQty());
			        }

			        else{
						 logger.debug("can not find inventory by tobin, so create new one");
						to = new SInventory();
						to.setCreationTime(new Date());
						to.setLotNo(wm.getLotNo());
						to.setQty(wm.getQty());
						to.setUQty(wm.getUqty());
						to.setSBin(sBinRepository.findOne(wm.getToBinId()));
						// to.setSMaterial(spoMaterial.getSMaterial());
						to.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
					} 
			        logger.debug("save to bin: " + to.getQty());
					sInventoryRepository.save(to);
					SInventory from=null;
					List<SInventory> fromInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getFromBinId());
			        if(fromInventorys!=null&&!fromInventorys.isEmpty())
			        {
			        	from = fromInventorys.get(0);
						from.setQty(from.getQty() - wm.getQty());
					
			        }
			        else
					{
						//logger.debug("can not find inventory by  from bin, so create new one");
						from = new SInventory();
						from.setCreationTime(new Date());
						from.setLotNo(wm.getLotNo());
						from.setQty(0-wm.getQty());
						from.setUQty(wm.getUqty());
						from.setSBin(sBinRepository.findOne(wm.getFromBinId()));
						// to.setSMaterial(spoMaterial.getSMaterial());
						from.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
					}
					sInventoryRepository.save(from);
					wm.setIdMt(sMtf.getIdMt());
					mtfMaterialService.saveMtfMaterial(wm);
					break;
				}

				case 5: // 出货
				{

					SInventory fromInventory= sInventoryRepository.findOne(wm.getInventoryId());
			        if(fromInventory!=null)
			        {
						fromInventory.setQty(fromInventory.getQty() - wm.getQty());
						sInventoryRepository.save(fromInventory);
						SSo sSo = sSoRepository.getOne(wm.getSoId());
						if (sSo.getQtyDelivered() != null) {
							sSo.setQtyDelivered(sSo.getQtyDelivered() + wm.getQty());

						} else {
							sSo.setQtyDelivered(wm.getQty());
						}

						sSoRepository.save(sSo);
	
						wm.setIdMt(sMtf.getIdMt());
						wm.setFromBinId(fromInventory.getSBin().getIdBin());
						mtfMaterialService.saveMtfMaterial(wm);
						break;
						
						
			        }
			        else
					{
			        	valid.setMsg("无库存，不能出货");
						break;
					}
			   
				}

				
				case 6: // 销售退货
				{
					//logger.debug("material: " + wm.getMaterialId());
					SInventory sInventory=null;
					List<SInventory> sInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
			        if(sInventorys!=null&&!sInventorys.isEmpty())
			        {
			        	sInventory = sInventorys.get(0);
						sInventory.setQty(sInventory.getQty() + wm.getQty());
			        }

			        else {
						sInventory = new SInventory();
						sInventory.setCreationTime(new Date());
						sInventory.setQty(wm.getQty());
						sInventory.setUQty(wm.getUqty());
						sInventory.setSBin(sBinRepository.findOne(wm.getToBinId()));
						sInventory.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));

					}

					sInventoryRepository.save(sInventory);
					SSo sSo = sSoRepository.getOne(wm.getSoId());
					if (sSo.getQtyDelivered() != null) {
						sSo.setQtyDelivered(sSo.getQtyDelivered() - wm.getQty());

					}

					sSoRepository.save(sSo);
					// update SSo
					
					
					wm.setIdMt(sMtf.getIdMt());
					mtfMaterialService.saveMtfMaterial(wm);
					break;
				}
				
				
				
				case 7: // 发料
				{
					
					
					wm.setIdMt(sMtf.getIdMt());
					mtfMaterialService.saveMtfMaterial(wm);
                    logger.debug(" case 7:  to:  wm.getToBinId(): " +  wm.getToBinId() +", from :" + wm.getFromBinId() +", material id: " + wm.getMaterialId() +", qty: " +wm.getQty());
					SInventory to=null;
					
					List<SInventory> sInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
			        if(sInventorys!=null&&!sInventorys.isEmpty())
			        {
			        	to = sInventorys.get(0);
						to.setQty(to.getQty() + wm.getQty());
			        }

			        else{
						 
						to = new SInventory();
						to.setCreationTime(new Date());
						to.setLotNo(wm.getLotNo());
						to.setQty(wm.getQty());
						to.setUQty(wm.getUqty());
						to.setSBin(sBinRepository.findOne(wm.getToBinId()));
						// to.setSMaterial(spoMaterial.getSMaterial());
						to.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
					} 
			      //  logger.debug("save to bin: " + to.getQty());
					sInventoryRepository.save(to);
				
					
					SInventory fromInventory= sInventoryRepository.findOne(wm.getInventoryId());
			        if(fromInventory!=null)
			        {
		
						fromInventory.setQty(fromInventory.getQty() - wm.getQty());
						sInventoryRepository.save(fromInventory);
			        }
			        else
					{
					}
					
					break;
				}

				
				case 8: //检验入库
				{

				//	logger.debug(" case 8:  to:  wm.getToBinId(): " +  wm.getToBinId() +", from :" + wm.getFromBinId() +", material id: " + wm.getMaterialId() +", qty: " +wm.getQty());
					SMtfMaterial orgsMtfMaterial = sMtfMaterialRepository.findOne(wm.getIdMtfMaterial());
					Long qtyChecked=(orgsMtfMaterial.getQty3417()==null)?0:orgsMtfMaterial.getQty3417(); //已检数量
					Long qty = orgsMtfMaterial.getQty(); //总数
					Long current_qty= wm.getQty();//此次检查数量
					orgsMtfMaterial.setQty3417(current_qty+qtyChecked);
				    if(current_qty+qtyChecked==qty)
				    {
				    	orgsMtfMaterial.setSStatusDic(sStatusDicRepository.findOne(2l));//已经检查(3是未检)
				    }
				    sMtfMaterialRepository.save(orgsMtfMaterial);
					wm.setIdMt(sMtf.getIdMt());
				    wm.setIdMtfMaterial(null);//如果不设为空，就是修改原有记录了！
					mtfMaterialService.saveMtfMaterial(wm);
    
					SInventory to = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
							wm.getToBinId(), wm.getLotNo());
					
					
			        if(to!=null)
			        {

						to.setQty(to.getQty() + wm.getQty());
			        }

			        else{
						 
						to = new SInventory();
						to.setCreationTime(new Date());

						to.setLotNo(wm.getLotNo());
						to.setQty(wm.getQty());
						to.setUQty(wm.getUqty());
						to.setSBin(sBinRepository.findOne(wm.getToBinId()));
						// to.setSMaterial(spoMaterial.getSMaterial());
						to.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
					} 
			       // logger.debug("save to bin: " + to.getQty());
					sInventoryRepository.save(to);
				
					
					SInventory from = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
							wm.getFromBinId(), wm.getLotNo());
					
			        if(from!=null)
			        {
			    
						from.setQty(from.getQty() - wm.getQty());
					
			        }
			        else
					{
						logger.debug("can not find inventory by  from bin, so create new one");
						valid.setValid(false);
						return valid;
					}
					sInventoryRepository.save(from);
					
					break;
				}
				

				}
			}
			valid.setValid(true);
			return valid;
		} catch (Exception e) {

			valid.setValid(false);
			e.printStackTrace();
			// valid.setMsg(e.getLocalizedMessage());
			return valid;
		}

	}

	public WSSMtf findSMtf(Long smtfId) throws Exception {
		SMtf sMtf = sMtfRepository.findOne(smtfId);
		return toWSSMtf(sMtf);
	}

	private SMtf toDBMtf(WSSMtf wsSMtf, SMtf sMtf) throws Exception {

		SMtf dbSMtf = (SMtf) BeanUtil.shallowCopy(wsSMtf, SMtf.class, sMtf);
		
		//change to get smtf no automatically
		
		//dbSMtf.setMtNo(wsSMtf.getMtNo());
		
		SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), wsSMtf.getTypeId());
	    if(smtfNo==null)
	    {
	    	sMtfNoService.loadSmtfNosByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
	    	smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), wsSMtf.getTypeId());
	    }
	    long currentVal =smtfNo.getCurrentVal()+1;
	    smtfNo.setCurrentVal(currentVal);
	    sMtfNoRepository.save(smtfNo);
		
	    String mtNo = smtfNo.getPrefix()+String.format("%08d", currentVal);
	    dbSMtf.setMtNo(mtNo);
		// System.out.println("mtNo: " + wsSMtf.getMtNo());
		dbSMtf.setCompany(securityUtils.getCurrentDBUser().getCompany());
		if (wsSMtf.getEmpMtUserId() != null) {
			dbSMtf.setUsersByEmpMt(usersRepository.findOne(wsSMtf.getEmpMtUserId()));
		}
		else
		{
			dbSMtf.setUsersByEmpMt(securityUtils.getCurrentDBUser());
		}
		
		if (wsSMtf.getFromStkId() != null) {
			dbSMtf.setSStkByFromStk(sStkRepository.findOne(wsSMtf.getFromStkId()));
		}
		if (wsSMtf.getToStkId() != null) {
			dbSMtf.setSStkByToStk(sStkRepository.findOne(wsSMtf.getToStkId()));
		}
	
		dbSMtf.setUsersByRecMt(securityUtils.getCurrentDBUser());
		
		if (wsSMtf.getStatusId() != null) {
			dbSMtf.setSStatusDic(sStatusDicRepository.findOne(wsSMtf.getStatusId()));
		}
		if (wsSMtf.getTypeId() != null) {
			dbSMtf.setSMtfTypeDic(sMtfTypeDicRepository.findOne(wsSMtf.getTypeId()));
		}
		dbSMtf.setCreationTime(new Date());
		/*
		 * if(!wsSMtf.getSmtfItems().isEmpty()) { for(WSSMtfMaterial m:
		 * wsSMtf.getSmtfItems().values()) { m.setIdMt(wsSMtf.getIdMt());
		 * mtfMaterialService.saveMtfMaterial(m); } }
		 */

		return dbSMtf;
	}
	
	/***收货单列表***/
	public List<WSSelectObj> getIncomeList(Long smtfType)
	{
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		List<SMtf> smtfs = sMtfRepository.getSMtfByType(smtfType, securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		for(SMtf smtf : smtfs)
		{
			boolean add=false;
			for(SMtfMaterial sm:smtf.getSMtfMaterials())
			{
				if(sm.getSStatusDic()==null||sm.getSStatusDic().getId().equals(3l))
				{
					 add=true;
				     break;
				}
			}
			if(add)
			{
				WSSelectObj w = new WSSelectObj(smtf.getIdMt(),smtf.getMtNo());
				ws.add(w);
			}
		}
		
		
		return ws;
	}

	private WSSMtf toWSSMtf(SMtf sMtf) throws Exception {
		WSSMtf wsSMtf = (WSSMtf) BeanUtil.shallowCopy(sMtf, WSSMtf.class, null);

		if (sMtf.getSMtfTypeDic() != null) {
			wsSMtf.setType(sMtf.getSMtfTypeDic().getName());
			wsSMtf.setTypeId(sMtf.getSMtfTypeDic().getIdMtfType());

		}
		if (sMtf.getSStatusDic() != null) {
			wsSMtf.setStatus(sMtf.getSStatusDic().getName());
			wsSMtf.setStatusId(sMtf.getSStatusDic().getId());
		}
		if (sMtf.getSStkByFromStk() != null) {
			wsSMtf.setFromStk(sMtf.getSStkByFromStk().getStkName());
			wsSMtf.setFromStkId(sMtf.getSStkByFromStk().getId());
		}
		if (sMtf.getSStkByToStk() != null) {
			wsSMtf.setToStk(sMtf.getSStkByToStk().getStkName());
			wsSMtf.setToStkId(sMtf.getSStkByToStk().getId());
		}
		if (sMtf.getUsersByEmpMt() != null) {
			wsSMtf.setEmpMtUser(sMtf.getUsersByEmpMt().getName());
			wsSMtf.setEmpMtUserId(sMtf.getUsersByEmpMt().getIdUser());
		}
		if (sMtf.getUsersByRecMt() != null) {
			wsSMtf.setRecMtUser(sMtf.getUsersByRecMt().getName());
			wsSMtf.setRecMtUserId(sMtf.getUsersByRecMt().getIdUser());
		}
		for (SMtfMaterial s : sMtf.getSMtfMaterials()) {
			wsSMtf.getSmtfItems().put("item" + s.getIdMtfMaterial(), mtfMaterialService.toWSSMtfMaterial(s));
			if(s.getSPoMaterial()!=null)
			{
				
				wsSMtf.setCoCompanyId(s.getSPoMaterial().getSPo().getSCompanyCo().getId());
				wsSMtf.setPoId(s.getSPoMaterial().getSPo().getIdPo());
				//break;
			}
			if(s.getSSo()!=null)
			{
				wsSMtf.setCoCompanyId(s.getSSo().getSCompanyCo().getId());
				//wsSMtf.set
			}
		}
	   // wsSMtf.setCoCompanyId(coCompanyId);
		// wsSMtf.setma
		//System.out.println("mtNo: " + wsSMtf.getMtNo());

		return wsSMtf;
	}

}
