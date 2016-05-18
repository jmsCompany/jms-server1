package com.jms.service.store;

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
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.store.WSSMtf;
import com.jms.domain.ws.store.WSSMtfMaterial;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMtfMaterialRepository;
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
			SMtf sMtf;
			// create
			if (wsSMtf.getIdMt() == null || wsSMtf.getIdMt().equals(0l)) {
				sMtf = new SMtf();
			}
			// update 不允许被编辑。！！！！！！
			else {
				sMtf = sMtfRepository.findOne(wsSMtf.getIdMt());
			/*	for(SMtfMaterial s:sMtf.getSMtfMaterials())
				{
					sMtfMaterialRepository.delete(s);
				}
			
				sMtf.getSMtfMaterials().clear();
				*/
			}

			sMtf = toDBMtf(wsSMtf, sMtf);
			
			SMtf sMtf1 = sMtfRepository.save(sMtf);

			for (String k : wsSMtf.getSmtfItems().keySet()) {

				logger.debug("smtf ");
				
				WSSMtfMaterial wm = wsSMtf.getSmtfItems().get(k);
				wm.setIdMt(sMtf1.getIdMt());
				mtfMaterialService.saveMtfMaterial(wm);
	
				switch (smtfType.intValue()) {
				case 1: // 来料入库
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
						sInventory.setBox(wm.getBox());
						sInventory.setLotNo(wm.getLotNo());
						sInventory.setQty(wm.getQty());
						sInventory.setUQty(wm.getUqty());
						sInventory.setSBin(sBinRepository.findOne(wm.getToBinId()));
						sInventory.setSMaterial(spoMaterial.getSMaterial());

					} else {
						if (sInventory.getBox() != null)
							sInventory.setBox(sInventory.getBox() + wm.getBox());
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
					break;
				}

				case 2: // 采购退货
				{
					logger.debug("采购退货。。。");
					SInventory sInventory=null;
					logger.debug("material Id: " + wm.getMaterialId() +", binId: " + wm.getFromBinId() +", " +wm.getLotNo());
					
					if(wm.getLotNo()!=null&&!wm.getLotNo().isEmpty())
					{
						sInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
								wm.getFromBinId(),wm.getLotNo());
						logger.debug("找到库存 id：" + sInventory.getIdInv() );
						if (sInventory.getBox() != null && wm.getBox()!=null)
							sInventory.setBox(sInventory.getBox() - wm.getBox());
						sInventory.setQty(sInventory.getQty() - wm.getQty());

						sInventoryRepository.save(sInventory);
					}
					else
					{
					//	sInventory = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
					//			wm.getFromBinId());
						
						List<SInventory> sInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
				        if(sInventorys!=null&&!sInventorys.isEmpty())
				        {
				        	
				        	for(SInventory s :sInventorys)
				        	{
				        	
				        		if(s.getQty()>= wm.getQty())
				        		{
				        			if (s.getBox() != null && wm.getBox()!=null)
										s.setBox(s.getBox() - wm.getBox());
				        			s.setQty(s.getQty() - wm.getQty());
									sInventoryRepository.save(s);
									break;
				        		}
				        		else
				        		{	
				        			wm.setQty(wm.getQty()-s.getQty());
				        			if (s.getBox() != null && wm.getBox()!=null)
				        			{
				        				wm.setBox(wm.getBox()-s.getBox());
										s.setBox(0l);
				        			}
			
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
					break;
				}

			    case 3: // 手动流转
				case 4: // 工单流转
				{
                    logger.debug(" case 3 or 4:  to:  wm.getToBinId()" +  wm.getToBinId() +", from :" + wm.getFromBinId() +", material id: " + wm.getMaterialId());
					SInventory to=null;
					
					List<SInventory> sInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
			        if(sInventorys!=null&&!sInventorys.isEmpty())
			        {
			        	to = sInventorys.get(0);
			        	if (to.getBox() != null)
							to.setBox(to.getBox() + wm.getBox());
						to.setQty(to.getQty() + wm.getQty());
			        }

			        else{
						 logger.debug("can not find inventory by tobin, so create new one");
						to = new SInventory();
						to.setCreationTime(new Date());
						to.setBox(wm.getBox());
						to.setLotNo(wm.getLotNo());
						to.setQty(wm.getQty());
						to.setUQty(wm.getUqty());
						to.setSBin(sBinRepository.findOne(wm.getToBinId()));
						// to.setSMaterial(spoMaterial.getSMaterial());
						to.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
					} 
					sInventoryRepository.save(to);
					SInventory from=null;
					List<SInventory> fromInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
			        if(fromInventorys!=null&&!fromInventorys.isEmpty())
			        {
			        	from = fromInventorys.get(0);
						logger.debug( "find inventory by  from bin, qty: " +from.getQty() );
						if (from.getBox() != null) {
							from.setBox(from.getBox() - wm.getBox());
						}
						from.setQty(from.getQty() - wm.getQty());
					
			        }
			        else
					{
						logger.debug("can not find inventory by  from bin, so create new one");
						from = new SInventory();
						from.setCreationTime(new Date());
						//from.setBox(0-wm.getBox());
						if (from.getBox() != null) {
							from.setBox(from.getBox() - wm.getBox());
						}
						from.setLotNo(wm.getLotNo());
						from.setQty(0-wm.getQty());
						from.setUQty(wm.getUqty());
						from.setSBin(sBinRepository.findOne(wm.getFromBinId()));
						// to.setSMaterial(spoMaterial.getSMaterial());
						from.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
					}
					sInventoryRepository.save(from);
					break;
				}

				case 5: // 出货
				{
			
				
					List<SInventory> sInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
			        if(sInventorys!=null&&!sInventorys.isEmpty())
			        {

			        	
			        	for(SInventory s :sInventorys)
			        	{
			        	
			        		if(s.getQty()>= wm.getQty())
			        		{
			        			if (s.getBox() != null && wm.getBox()!=null)
									s.setBox(s.getBox() - wm.getBox());
			        			s.setQty(s.getQty() - wm.getQty());
								sInventoryRepository.save(s);
								break;
			        		}
			        		else
			        		{	
			        			wm.setQty(wm.getQty()-s.getQty());
			        			if (s.getBox() != null && wm.getBox()!=null)
			        			{
			        				wm.setBox(wm.getBox()-s.getBox());
									s.setBox(0l);
			        			}
		
			        			s.setQty(0l);
								sInventoryRepository.save(s);
			        		}
							
			        	}
			        
			        }
			        
			        else {
						valid.setMsg("无库存，不能出货");
						break;
					}
					

					SSo sSo = sSoRepository.getOne(wm.getSoId());
					if (sSo.getQtyDelivered() != null) {
						sSo.setQtyDelivered(sSo.getQtyDelivered() + wm.getQty());

					} else {
						sSo.setQtyDelivered(wm.getQty());
					}

					sSoRepository.save(sSo);
					// update SSo
					break;
				}

				case 6: // 销售退货
				{
				
					
					SInventory sInventory=null;
					List<SInventory> sInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
			        if(sInventorys!=null&&!sInventorys.isEmpty())
			        {
			        	sInventory = sInventorys.get(0);
			        	if (sInventory.getBox() != null)
							sInventory.setBox(sInventory.getBox() + wm.getBox());
						sInventory.setQty(sInventory.getQty() + wm.getQty());
			        }

			        else {
						sInventory = new SInventory();
						sInventory.setCreationTime(new Date());
						sInventory.setBox(wm.getBox());

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
		dbSMtf.setMtNo(wsSMtf.getMtNo());
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
			wsSMtf.setFromStk(sMtf.getSStkByFromStk().getDes());
			wsSMtf.setFromStkId(sMtf.getSStkByFromStk().getId());
		}
		if (sMtf.getSStkByToStk() != null) {
			wsSMtf.setToStk(sMtf.getSStkByToStk().getDes());
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
		}
	
		// wsSMtf.setma
		//System.out.println("mtNo: " + wsSMtf.getMtNo());

		return wsSMtf;
	}

}
