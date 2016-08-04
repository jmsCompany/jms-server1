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
import com.jms.domain.db.EventReceiver;
import com.jms.domain.db.PMr;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SInventory;
import com.jms.domain.db.SMaterialBins;
import com.jms.domain.db.SMaterialBinsId;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SSo;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.s.WSSMtf;
import com.jms.domain.ws.s.WSSMtfMaterial;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PMrRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SInventoryRepository;
import com.jms.repositories.s.SMaterialBinsRepository;
import com.jms.repositories.s.SMaterialRepository;
import com.jms.repositories.s.SMtfMaterialRepository;
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.s.SMtfRepository;
import com.jms.repositories.s.SMtfTypeDicRepository;
import com.jms.repositories.s.SSoRepository;
import com.jms.repositories.s.SSpoMaterialRepository;
import com.jms.repositories.s.SSpoRepository;
import com.jms.repositories.s.SStatusDicRepository;
import com.jms.repositories.s.SStkRepository;
import com.jms.repositories.system.EventReceiverRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.system.INotificationService;
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
	
	@Autowired
	private SMaterialBinsRepository sMaterialBinsRepository;
	
	@Autowired
	private PBomRepository pBomRepository;
	@Autowired
	private SSpoRepository sSpoRepository;
	
	@Autowired
	private INotificationService notificationService;
	@Autowired
	private EventReceiverRepository eventReceiverRepository;
	
	@Autowired
	private  PCPpRepository pCPpRepository;
	@Autowired
	private PStatusDicRepository pStatusDicRepository;
	
	@Autowired
	private PMrRepository pMrRepository;
	
	
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


	public Valid saveMtf(WSSMtf wsSMtf) throws Exception {
		Valid valid = new Valid();
	
			Long smtfType = wsSMtf.getTypeId();
			SMtf sMtf = new SMtf();
		//	if(wsSMtf.getTypeId())

			sMtf = toDBMtf(wsSMtf, sMtf);
			
			sMtf = sMtfRepository.save(sMtf);
			boolean toIQC = false;
			boolean finishIQC=true;
			boolean savePmr = wsSMtf.isSavePmr();
		
            int i=0;
            SMtf orgSmtf=null;
			for (String k : wsSMtf.getSmtfItems().keySet()) {
				i++;

				WSSMtfMaterial wm = wsSMtf.getSmtfItems().get(k);
				switch (smtfType.intValue()) {
				case 1: // 来料入检
				{
					SBin sbin =sBinRepository.findOne(wm.getToBinId());
					if(i==1)
					{
						if(sbin.getBin().equals("IQC"))
						{
							toIQC=true;
						}
						else
						{
							wm.setQtyChecked(wm.getQty());
						}
					}
			
					SPoMaterial spoMaterial = sSpoMaterialRepository.getOne(wm.getPoMaterialId());
					//logger.debug("toStkId: " + wsSMtf.getToStkId());
					updateMaterialBins(spoMaterial.getSMaterial().getIdMaterial(),wm.getToBinId(),wsSMtf.getToStkId());
					SInventory sToInventory=null;
					SInventory sFromInventory=null;
					if (wm.getLotNo() != null&&!wm.getLotNo().isEmpty()) {
						sToInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
								wm.getToBinId(), wm.getLotNo());
						sFromInventory= sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
								wm.getFromBinId(), wm.getLotNo());
						
					} else {
						List<SInventory> sToInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
				        if(sToInventorys!=null&&!sToInventorys.isEmpty())
				        {
				        	sToInventory = sToInventorys.get(0);
				        }
				        
						List<SInventory> sFromInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getFromBinId());
				        if(sFromInventorys!=null&&!sFromInventorys.isEmpty())
				        {
				        	sFromInventory = sFromInventorys.get(0);
				        }
					}
					if (sToInventory == null) {
					
						sToInventory = new SInventory();
						sToInventory.setCreationTime(new Date());
						sToInventory.setLotNo(wm.getLotNo());
						sToInventory.setQty(wm.getQty());
						sToInventory.setUQty(wm.getUqty());
						sToInventory.setSBin(sbin);
						sToInventory.setSMaterial(spoMaterial.getSMaterial());
    					logger.debug(" save to new bin: " +sbin.getBin() +", qty:" + wm.getQty());
						sInventoryRepository.save(sToInventory);
	
					} else {
						sToInventory.setQty(sToInventory.getQty() + wm.getQty());
						logger.debug(" save to  bin: " +sbin.getBin() +", qty:" + wm.getQty());
						sInventoryRepository.save(sToInventory);
					}
//					
					
					if (sFromInventory == null) {
						sFromInventory = new SInventory();
						sFromInventory.setCreationTime(new Date());
						sFromInventory.setLotNo(wm.getLotNo());
						sFromInventory.setQty(0-wm.getQty());
						sFromInventory.setUQty(wm.getUqty());
						sFromInventory.setSBin(sBinRepository.findOne(wm.getFromBinId()));
						sFromInventory.setSMaterial(spoMaterial.getSMaterial());
					} else {
						sFromInventory.setQty(sFromInventory.getQty() - wm.getQty());
					}
					sInventoryRepository.save(sFromInventory);
				
					

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
					
					updateMaterialBins(wm.getMaterialId(),wm.getFromBinId(),wsSMtf.getFromStkId());
				//	logger.debug("采购退货。。。");
					SInventory sFromInventory=null;
					SInventory sToInventory=null;
				//	logger.debug("material Id: " + wm.getMaterialId() +", binId: " + wm.getFromBinId() +", " +wm.getLotNo());
					
					if(wm.getLotNo()!=null&&!wm.getLotNo().isEmpty())
					{
						
					//	logger.debug("mat no:" +wm.getMaterialId() +", lot no " + wm.getLotNo() + " wm qty: " + wm.getQty());
						sFromInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
								wm.getFromBinId(),wm.getLotNo());
						sFromInventory.setQty(sFromInventory.getQty() - wm.getQty());
						sInventoryRepository.save(sFromInventory);
						
						sToInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
								wm.getToBinId(),wm.getLotNo());
						if(sToInventory==null)
						{
							sToInventory = new SInventory();
							sToInventory.setCreationTime(new Date());
							sToInventory.setLotNo(wm.getLotNo());
							sToInventory.setQty(wm.getQty());
							sToInventory.setUQty(wm.getUqty());
							sToInventory.setSBin(sBinRepository.findOne(wm.getToBinId()));
							sToInventory.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
							sToInventory.setQty( wm.getQty());
							sInventoryRepository.save(sToInventory);
						}
						else
						{	
							sToInventory.setQty(sToInventory.getQty() + wm.getQty());
					    	sInventoryRepository.save(sToInventory);
							
						}
					
						
						
					}
					else
					{
					//	sInventory = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
					//			wm.getFromBinId());
						
						List<SInventory> sFromInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getFromBinId());
				        if(sFromInventorys!=null&&!sFromInventorys.isEmpty())
				        {
				        	
				        	for(SInventory s :sFromInventorys)
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
				        
				        
				    	List<SInventory> sToInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
				    	   if(sToInventorys!=null&&!sToInventorys.isEmpty())
					        {
				    		   sToInventory = sToInventorys.get(0);
				    		   sToInventory.setQty(sToInventory.getQty() + wm.getQty());
						       sInventoryRepository.save(sToInventory);
					        }
				    	   else{

								sToInventory = new SInventory();
								sToInventory.setCreationTime(new Date());
								sToInventory.setLotNo(wm.getLotNo());
								sToInventory.setQty(wm.getQty());
								sToInventory.setUQty(wm.getUqty());
								sToInventory.setSBin(sBinRepository.findOne(wm.getToBinId()));
								sToInventory.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
//								sToInventory.setQty(wm.getQty());
								sInventoryRepository.save(sToInventory);
				    		   
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
					wm.setIdMt(sMtf.getIdMt());
					mtfMaterialService.saveMtfMaterial(wm);
					updateMaterialBins(wm.getMaterialId(),wm.getFromBinId(),wsSMtf.getFromStkId());
					updateMaterialBins(wm.getMaterialId(),wm.getToBinId(),wsSMtf.getToStkId());
                    logger.debug(" case 3 or 4:  to:  wm.getToBinId(): " +  wm.getToBinId() +", from :" + wm.getFromBinId() +", material id: " + wm.getMaterialId() +", qty: " +wm.getQty());
					SInventory to=null;
					List<SInventory> sInventorys=null;
					if(wm.getLotNo()!=null)
					{
						logger.debug("lotNo: " + wm.getLotNo());
						SInventory sInventory=sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(), wm.getToBinId(), wm.getLotNo());
						if(sInventory!=null)
						{
							logger.debug("find from inv: " + sInventory.getIdInv());
							sInventorys = new ArrayList<SInventory>();
							sInventorys.add(sInventory);
						}
					}
					else
					{
						 sInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
					}
				
			        if(sInventorys!=null&&!sInventorys.isEmpty())
			        {
			        	to = sInventorys.get(0);
			        	logger.debug("set to: " +to.getQty() + wm.getQty() );
						to.setQty(to.getQty() + wm.getQty());
			        }

			        else{
						 logger.debug("can not find inventory, so create new one");
						to = new SInventory();
						to.setCreationTime(new Date());
						to.setLotNo(wm.getLotNo());
						to.setQty(wm.getQty());
						to.setUQty(wm.getUqty());
						to.setSBin(sBinRepository.findOne(wm.getToBinId()));
						to.setLotNo(wm.getLotNo());
						to.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
					} 
			       // logger.debug("save to bin: " + to.getQty());
					sInventoryRepository.save(to);
					
					
					SInventory from=null;
					List<SInventory> fromInventorys= new ArrayList<SInventory>();;
					
					if(wm.getLotNo()!=null)
					{
						
						SInventory sInventory=sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(), wm.getFromBinId(), wm.getLotNo());
						if(sInventory!=null)
						{
						    logger.debug("find from inv: "+ sInventory.getIdInv());	
							fromInventorys.add(sInventory);
						}
					}
					else
					{
						fromInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getFromBinId());
					}
					
					
			        if(fromInventorys!=null&&!fromInventorys.isEmpty())
			        {
			       
						for(SInventory s:fromInventorys)
						{
                         if(s.getQty()>0)
                         {
                        	 logger.debug("sqty: " +s.getQty() +", wmqty: " +wm.getQty());
                        	 if(s.getQty()>= wm.getQty())
 			        		{
 			        			s.setQty(s.getQty() - wm.getQty());
 								sInventoryRepository.save(s);
 								wm.setQty(0l);
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
						if(wm.getQty()>0)
						{
							SInventory first = fromInventorys.get(0);
							first.setQty(first.getQty()-wm.getQty());
							sInventoryRepository.save(first);
						}
					
			        }
			        else
					{
						logger.debug("找不到发料扣减仓位？？？？？");
						from = new SInventory();
						from.setCreationTime(new Date());
						from.setLotNo(wm.getLotNo());
						from.setQty(0-wm.getQty());
						from.setUQty(wm.getUqty());
						from.setSBin(sBinRepository.findOne(wm.getFromBinId()));
						// to.setSMaterial(spoMaterial.getSMaterial());
						from.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
						sInventoryRepository.save(from);
					}
					
		
					break;
				}

				case 5: // 出货
				{
					SInventory fromInventory =sInventoryRepository.findOne(wm.getInventoryId());

					
			        if(fromInventory!=null)
			        {
			        	Long fromBinId = fromInventory.getSBin().getIdBin();
			        	wm.setFromBinId(fromBinId);

					//	logger.debug("matId: " +wm.getMaterialId() +", fromBinId: " +wm.getFromBinId() +", stkId:"+wsSMtf.getFromStkId());
						updateMaterialBins(wm.getMaterialId(),wm.getFromBinId(),wsSMtf.getFromStkId());
			        	fromInventory.setQty(fromInventory.getQty()-wm.getQty());
						sInventoryRepository.save(fromInventory);
		       
						SSo sSo = sSoRepository.getOne(wm.getSoId());
						long rvd=0l;
						if (sSo.getQtyDelivered() != null) {
							rvd = sSo.getQtyDelivered() + wm.getQty();

						} else {
							rvd =wm.getQty();
						}
						sSo.setQtyDelivered(rvd);
						if(rvd>=sSo.getQtySo()) //关闭销售单
						{
							sSo.setSStatusDic(sStatusDicRepository.findOne(15l)); //结束销售订单
						}

						sSoRepository.save(sSo);
	
						wm.setIdMt(sMtf.getIdMt());
						wm.setFromBinId(wm.getFromBinId());
						mtfMaterialService.saveMtfMaterial(wm);
						   SInventory to=null;
							
							List<SInventory> sInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
					        if(sInventorys!=null&&!sInventorys.isEmpty())
					        {
					        	to = sInventorys.get(0);
								to.setQty(to.getQty() + wm.getQty());
					        }

					        else{
							//	 logger.debug("can not find inventory by tobin, so create new one");
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
					
			        }
			        else
					{
			        	valid.setMsg("无库存，不能出货");
			        	//logger.debug("无库存，不能出货");
						break;
					}
					break;
				}

				
				case 6: // 销售退货
				{
					//logger.debug("material: " + wm.getMaterialId());
					updateMaterialBins(wm.getMaterialId(),wm.getToBinId(),wsSMtf.getToStkId());
					SInventory sToInventory=null;
					List<SInventory> sToInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
			        if(sToInventorys!=null&&!sToInventorys.isEmpty())
			        {
			        	sToInventory = sToInventorys.get(0);
			        	sToInventory.setQty(sToInventory.getQty() + wm.getQty());
			        }

			        else {
			        	sToInventory = new SInventory();
			        	sToInventory.setCreationTime(new Date());
			        	sToInventory.setQty(wm.getQty());
			        	sToInventory.setUQty(wm.getUqty());
			        	sToInventory.setSBin(sBinRepository.findOne(wm.getToBinId()));
			        	sToInventory.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));

					}
					sInventoryRepository.save(sToInventory);
					
					
					SInventory from=null;
					List<SInventory> fromInventorys= sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getFromBinId());
			        if(fromInventorys!=null&&!fromInventorys.isEmpty())
			        {
			       
			        	from = fromInventorys.get(0);
			        	from.setQty(from.getQty()-wm.getQty());
			        }
			        else
					{
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
//					if(wm.getQty()==null)
//					{
//						continue;
//					}
					if(wm.getQty().equals(0l))
					{
						continue;
					}
				//	logger.debug(msg);
					updateMaterialBins(wm.getMaterialId(),wm.getFromBinId(),wsSMtf.getFromStkId());
				//	updateMaterialBins(wm.getMaterialId(),wm.getFromBinId(),wsSMtf.getFromStkId());
					wm.setIdMt(sMtf.getIdMt());
					mtfMaterialService.saveMtfMaterial(wm);
                    logger.debug(" case 7:  to:  wm.getToBinId(): " +  wm.getToBinId() +", from :" + wm.getFromBinId() +", material id: " + wm.getMaterialId() +", qty: " +wm.getQty());
					SInventory to=null;
					if(savePmr)
					{
						PMr pmr = new PMr();
						pmr.setFt(new Date());
						pmr.setSt(new Date());
						pmr.setPBom(pBomRepository.findOne(wm.getPwoBomId()));
						pmr.setPCPp(pCPpRepository.findOne(wm.getCppId()));
						pmr.setPStatusDic(pStatusDicRepository.findOne(10l));
						pmr.setQty(wm.getQty());
						pmr.setQtyDelivered(wm.getQty());
						pmr.setType(2l);
						pmr.setUser(securityUtils.getCurrentDBUser());
						pMrRepository.save(pmr);
					}
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

					
					updateMaterialBins(wm.getMaterialId(),wm.getFromBinId(),wsSMtf.getFromStkId());
					updateMaterialBins(wm.getMaterialId(),wm.getToBinId(),wsSMtf.getToStkId());
					
				//	logger.debug(" case 8:  to:  wm.getToBinId(): " +  wm.getToBinId() +", from :" + wm.getFromBinId() +", material id: " + wm.getMaterialId() +", qty: " +wm.getQty());
					SMtfMaterial orgsMtfMaterial = sMtfMaterialRepository.findOne(wm.getIdMtfMaterial());
					//orgsMtfMaterial.gets
					orgSmtf = orgsMtfMaterial.getSMtf();
					Long qtyChecked=(orgsMtfMaterial.getQty3417()==null)?0:orgsMtfMaterial.getQty3417(); //已检数量
					Long qty = orgsMtfMaterial.getQty(); //总数
					Long current_qty= wm.getQty();//此次检查数量
					orgsMtfMaterial.setQty3417(current_qty+qtyChecked);
				    if(current_qty+qtyChecked==qty)
				    {
				    	orgsMtfMaterial.setSStatusDic(sStatusDicRepository.findOne(2l));//已经检查(3是未检)
				    }
				    else
				    {
				    	finishIQC=false;
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
						throw new Exception("can not find inventory by  from bin, so create new one");
						//valid.setValid(false);
						//return valid;
					}
					sInventoryRepository.save(from);
					
					break;
				}
				

				}
			}
			
			
			if(smtfType.equals(1l)) //来料入检，检查SPO
			{
				//System.out.println("来料入检:");
				SPo spo = sSpoRepository.findOne(wsSMtf.getPoId());
				boolean isFinished = true;
				for(SPoMaterial sm:spo.getSPoMaterials())
				{
					long qtyPo = (sm.getQtyPo()==null)?0l:sm.getQtyPo();
					long qtyR= (sm.getQtyReceived()==null)?0l:sm.getQtyReceived();
					if(qtyPo>qtyR)
					{
						isFinished=false;
						break;
					}
				}
				
				if(isFinished)
				{
					spo.setSStatusDic(sStatusDicRepository.findOne(8l));//结束
					sSpoRepository.save(spo);
				}
				
				if(!toIQC)
				{
					if(sMtf!=null)
					{
						sMtf.setSStatusDic(sStatusDicRepository.findOne(5l));//完成
						sMtfRepository.save(sMtf);
					}
			
				}
				else //发消息
				{
					List<EventReceiver> eventReceivers = eventReceiverRepository.findByIdEventAndIdCompany(11l,securityUtils.getCurrentDBUser().getCompany().getIdCompany());
					if(eventReceivers!=null)
					{
						notificationService.createNotificationToReceivers(securityUtils.getCurrentDBUser().getCompany(), 11l, sMtf.getIdMt(), NotificationMethodEnum.sys, eventReceivers);
					}
			
				}
				
			}
			
			
			if(smtfType.equals(8l))
			{
				for(SMtfMaterial s: sMtfMaterialRepository.getBySmtfId(orgSmtf.getIdMt()))
				{
					if(s.getQty3417()==null||s.getQty3417()<s.getQty())
					{
						finishIQC=false;
						
						break;
					}
				}
				if(finishIQC)
				{
					orgSmtf.setSStatusDic(sStatusDicRepository.findOne(5l));//完成
					sMtfRepository.save(orgSmtf);
				}
				
			
			}
			
			valid.setValid(true);
			return valid;


	}

	public WSSMtf findSMtf(Long smtfId) throws Exception {
		SMtf sMtf = sMtfRepository.findOne(smtfId);
		return toWSSMtf(sMtf);
	}

	private SMtf toDBMtf(WSSMtf wsSMtf, SMtf sMtf) throws Exception {

		SMtf dbSMtf = (SMtf) BeanUtil.shallowCopy(wsSMtf, SMtf.class, sMtf);
		//logger.debug("mtNo: " +wsSMtf.getMtNo());
		if(wsSMtf.getMtNo()==null||wsSMtf.getMtNo().trim().isEmpty())
		{
			SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), wsSMtf.getTypeId());
		    long currentVal =smtfNo.getCurrentVal()+1;
		    smtfNo.setCurrentVal(currentVal);
		    sMtfNoRepository.save(smtfNo);
		    String mtNo = smtfNo.getPrefix()+String.format("%08d", currentVal);
		    dbSMtf.setMtNo(mtNo);
		}
				

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
	
	/***收货单列表 po是激活状态***/
	public List<WSSelectObj> getIncomeList(Long smtfType)
	{
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		List<SMtf> smtfs = sMtfRepository.getSMtfByType(smtfType, securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		for(SMtf smtf : smtfs)
		{
			    boolean active=false;
			    if(smtfType.equals(1l)) //来料入监
			    {
			    	
					if(smtf.getSStatusDic()==null)
		    		{
		    	   		WSSelectObj w = new WSSelectObj(smtf.getIdMt(),smtf.getMtNo());
						ws.add(w);
		    		}
			    }
//			    	if(!smtf.getSMtfMaterials().isEmpty())
//			    	{
//			    		SMtfMaterial sm = smtf.getSMtfMaterials().iterator().next();
//			    		if(sm.getSPoMaterial()!=null)
//			    		{
//			    			SPo spo = sm.getSPoMaterial().getSPo();
//			    			if(spo.getSStatusDic()!=null&&spo.getSStatusDic().getId().equals(11l))
//			    			{
//			    				active=true;
//			    			}
//			    		}
//			    	}
//			    	if(active)
//			    	{
//			    		if(smtf.getSStatusDic()==null)
//			    		{
//			    	   		WSSelectObj w = new WSSelectObj(smtf.getIdMt(),smtf.getMtNo());
//							ws.add(w);
//			    		}
//			 
//			    	}
//			    		
//			    }
		    else{
		    	  // System.out.println(" type: " + smtfType +"., companyId: " +  securityUtils.getCurrentDBUser().getCompany().getIdCompany());
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
		for (SMtfMaterial s : sMtfMaterialRepository.getBySmtfId(sMtf.getIdMt())) {
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
				wsSMtf.setCoOrderNo(s.getSSo().getCoOrderNo());
				//wsSMtf.set
			}
		}
	   // wsSMtf.setCoCompanyId(coCompanyId);
		// wsSMtf.setma
		//System.out.println("mtNo: " + wsSMtf.getMtNo());

		return wsSMtf;
	}
	
	private void updateMaterialBins(Long idMaterial,Long idBin,Long idStk)
	{
		
		SMaterialBinsId id = new SMaterialBinsId();
		id.setIdBin(idBin);
		id.setIdMaterial(idMaterial);
		SMaterialBins sMaterialBins =sMaterialBinsRepository.findOne(id);
		if(sMaterialBins==null)
		{
			sMaterialBins = new SMaterialBins();
			sMaterialBins.setId(id);
			sMaterialBins.setIdStk(idStk);
			sMaterialBins.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
			sMaterialBinsRepository.save(sMaterialBins);
			
		}
	}
	

}
