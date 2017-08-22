package com.jms.service.store;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.NotificationMethodEnum;
import com.jms.domain.db.Company;
import com.jms.domain.db.EventReceiver;
import com.jms.domain.db.PMr;
import com.jms.domain.db.PWo;
import com.jms.domain.db.PWoBom;
import com.jms.domain.db.SBin;
import com.jms.domain.db.SComCom;
import com.jms.domain.db.SInventory;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SMaterialBins;
import com.jms.domain.db.SMaterialBinsId;
import com.jms.domain.db.SMtf;
import com.jms.domain.db.SMtfMaterial;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.SPo;
import com.jms.domain.db.SPoMaterial;
import com.jms.domain.db.SSo;
import com.jms.domain.db.SStk;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.s.WSDoItem;
import com.jms.domain.ws.s.WSDoPrint;
import com.jms.domain.ws.s.WSIqcItem;
import com.jms.domain.ws.s.WSIqcPrint;
import com.jms.domain.ws.s.WSSMtf;
import com.jms.domain.ws.s.WSSMtfMaterial;
import com.jms.domainadapter.BeanUtil;
import com.jms.email.EmailSenderService;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.p.PBomRepository;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PMrRepository;
import com.jms.repositories.p.PStatusDicRepository;
import com.jms.repositories.p.PWoBomRepository;
import com.jms.repositories.p.PWoRepository;
import com.jms.repositories.s.SBinRepository;
import com.jms.repositories.s.SComComRepository;
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
	private SBinService sBinService;
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private SMaterialBinsRepository sMaterialBinsRepository;

	@Autowired
	private PBomRepository pBomRepository;
	
	@Autowired
	private PWoRepository pWoRepository;
	
	@Autowired
	private SSpoRepository sSpoRepository;

	@Autowired
	private INotificationService notificationService;
	@Autowired
	private EventReceiverRepository eventReceiverRepository;
	
	@Autowired
	private PWoBomRepository pWoBomRepository;

	@Autowired
	private PCPpRepository pCPpRepository;
	@Autowired
	private PStatusDicRepository pStatusDicRepository;

	@Autowired
	private PMrRepository pMrRepository;

	
	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private SComComRepository sComComRepository;

	// 校验
	public Valid updateMtfStatus(WSSMtf wsSMtf) {
		Valid valid = new Valid();
		if (wsSMtf.getTypeId().intValue() == 1) {
			for (String k : wsSMtf.getSmtfItems().keySet()) {
				WSSMtfMaterial wm = wsSMtf.getSmtfItems().get(k);
				SMtfMaterial sMtfMaterial = sMtfMaterialRepository.findOne(wm.getIdMtfMaterial());
				sMtfMaterial.setSStatusDic(sStatusDicRepository.findOne(wm.getStatusId()));
				sMtfMaterialRepository.save(sMtfMaterial);
			}

		} else {
			valid.setValid(false);
		}

		valid.setValid(true);
		return valid;

	}

	public Valid saveMtf(WSSMtf wsSMtf) throws Exception {

		Valid valid = new Valid();
		valid.setValid(true);
		Long smtfType = wsSMtf.getTypeId();

		// wsSMtf.get
		Company myCompany = securityUtils.getCurrentDBUser().getCompany();
		long companyId = myCompany.getIdCompany();
		boolean mcheck = true;
		String msg = "";
		if (smtfType.equals(10l)) {
			for (String k : wsSMtf.getSmtfItems().keySet()) {
				WSSMtfMaterial wm = wsSMtf.getSmtfItems().get(k);
				String pno = wm.getMaterialPno();
				SMaterial m = sMaterialRepository.getByCompanyIdAndPno(companyId, pno);
				if (m == null) {
					mcheck = false;
					msg = msg + "您公司无此物料信息，请新建物料：" + pno + "\r\n";
				}
			}
			valid.setValid(mcheck);
			valid.setMsg(msg);
			if (!mcheck)
				return valid;
		}

		if (smtfType.equals(1l)) // 来料入检，检查SPO
		{
			// System.out.println("来料入检:");
			// SPo spo = sSpoRepository.findOne(wsSMtf.getPoId());
			if(myCompany.getOverfulfilRecieve()==null||myCompany.getOverfulfilRecieve().equals(0l))
			{
				boolean check = true;
				for (String k : wsSMtf.getSmtfItems().keySet()) {
					WSSMtfMaterial wm = wsSMtf.getSmtfItems().get(k);
					SPoMaterial spoMaterial = sSpoMaterialRepository.getOne(wm.getPoMaterialId());
					long qtyR = (spoMaterial.getQtyReceived() == null) ? 0l : spoMaterial.getQtyReceived();
					if (spoMaterial.getQtyPo() < qtyR + wm.getQty()) {
						check = false;
						break;
					}

				}
				if (!check) {
					valid.setValid(check);
					valid.setMsg("新建来料大于采购数目！");
					// System.out.println("valid: " + valid);
					return valid;
				}
			}
			

		}

		SMtf sMtf = new SMtf();
		// if(wsSMtf.getTypeId())

		sMtf = toDBMtf(wsSMtf, sMtf);
		if (smtfType.equals(10l)) {
			sMtf.setIdSmtfC(wsSMtf.getIdMt());  // 发货的流转单ID
			sMtf.setCreationTime(new Date());
			sMtf.setDateMt(new Date());
			sMtf.setCoOrderNo(wsSMtf.getCoOrderNo());
			sMtf.setSStatusDic(sStatusDicRepository.findOne(5l));
		}

		if (smtfType.equals(2l)) {
			sMtf.setSStatusDic(sStatusDicRepository.findOne(6l));
		}

		sMtf = sMtfRepository.save(sMtf);

		boolean toIQC = false;
		boolean finishIQC = true;
		boolean savePmr = wsSMtf.isSavePmr();

		int i = 0;
		SMtf orgSmtf = null;

		for (String k : wsSMtf.getSmtfItems().keySet()) {
			i++;

			WSSMtfMaterial wm = wsSMtf.getSmtfItems().get(k);
			switch (smtfType.intValue()) {
			case 1: // 来料入检
			{
				SBin sbin = sBinRepository.findOne(wm.getToBinId());
				if (i == 1) {
					if (sbin.getBin().equals("IQC")) {
						toIQC = true;
					} else {
						wm.setQtyChecked(wm.getQty());
					}
				}

				SPoMaterial spoMaterial = sSpoMaterialRepository.getOne(wm.getPoMaterialId());
				// logger.debug("toStkId: " + wsSMtf.getToStkId());
				updateMaterialBins(spoMaterial.getSMaterial().getIdMaterial(), wm.getToBinId(), wsSMtf.getToStkId());
				SInventory sToInventory = null;
				SInventory sFromInventory = null;
				if (wm.getLotNo() != null && !wm.getLotNo().isEmpty()) {
					sToInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
							wm.getToBinId(), wm.getLotNo());
					sFromInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
							wm.getFromBinId(), wm.getLotNo());

				} else {
					List<SInventory> sToInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
							wm.getToBinId());
					if (sToInventorys != null && !sToInventorys.isEmpty()) {
						sToInventory = sToInventorys.get(0);
					}

					List<SInventory> sFromInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
							wm.getFromBinId());
					if (sFromInventorys != null && !sFromInventorys.isEmpty()) {
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
					// logger.debug(" save to new bin: " +sbin.getBin() +",
					// qty:" + wm.getQty());
					sInventoryRepository.save(sToInventory);

				} else {
					sToInventory.setQty(sToInventory.getQty() + wm.getQty());
					logger.debug(" save to  bin: " + sbin.getBin() + ", qty:" + wm.getQty());
					sInventoryRepository.save(sToInventory);
				}
				//

				if (sFromInventory == null) {
					sFromInventory = new SInventory();
					sFromInventory.setCreationTime(new Date());
					sFromInventory.setLotNo(wm.getLotNo());
					sFromInventory.setQty(0 - wm.getQty());
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

				updateMaterialBins(wm.getMaterialId(), wm.getFromBinId(), wsSMtf.getFromStkId());
				// logger.debug("采购退货。。。");
				SInventory sFromInventory = null;
				SInventory sToInventory = null;
				// logger.debug("material Id: " + wm.getMaterialId() +", binId:
				// " + wm.getFromBinId() +", " +wm.getLotNo());

				if (wm.getLotNo() != null && !wm.getLotNo().isEmpty()) {

					// logger.debug("mat no:" +wm.getMaterialId() +", lot no " +
					// wm.getLotNo() + " wm qty: " + wm.getQty());
					sFromInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
							wm.getFromBinId(), wm.getLotNo());
					sFromInventory.setQty(sFromInventory.getQty() - wm.getQty());
					sInventoryRepository.save(sFromInventory);

					if (wm.getToBinId() != null) {
						sToInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
								wm.getToBinId(), wm.getLotNo());
						if (sToInventory == null) {
							sToInventory = new SInventory();
							sToInventory.setCreationTime(new Date());
							sToInventory.setLotNo(wm.getLotNo());
							sToInventory.setQty(wm.getQty());
							sToInventory.setUQty(wm.getUqty());
							sToInventory.setSBin(sBinRepository.findOne(wm.getToBinId()));
							sToInventory.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
							sToInventory.setQty(wm.getQty());
							sInventoryRepository.save(sToInventory);
						} else {
							sToInventory.setQty(sToInventory.getQty() + wm.getQty());
							sInventoryRepository.save(sToInventory);

						}

					}

				} else {
					// sInventory =
					// sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
					// wm.getFromBinId());

					List<SInventory> sFromInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
							wm.getFromBinId());
					if (sFromInventorys != null && !sFromInventorys.isEmpty()) {

						for (SInventory s : sFromInventorys) {

							if (s.getQty() >= wm.getQty()) {
								s.setQty(s.getQty() - wm.getQty());
								sInventoryRepository.save(s);
								break;
							} else {
								wm.setQty(wm.getQty() - s.getQty());
								s.setQty(0l);
								sInventoryRepository.save(s);
							}

						}
					}

					if (wm.getToBinId() != null) {
						List<SInventory> sToInventorys = sInventoryRepository
								.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
						if (sToInventorys != null && !sToInventorys.isEmpty()) {
							sToInventory = sToInventorys.get(0);
							sToInventory.setQty(sToInventory.getQty() + wm.getQty());
							sInventoryRepository.save(sToInventory);
						} else {

							sToInventory = new SInventory();
							sToInventory.setCreationTime(new Date());
							sToInventory.setLotNo(wm.getLotNo());
							sToInventory.setQty(wm.getQty());
							sToInventory.setUQty(wm.getUqty());
							sToInventory.setSBin(sBinRepository.findOne(wm.getToBinId()));
							sToInventory.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
							// sToInventory.setQty(wm.getQty());
							sInventoryRepository.save(sToInventory);

						}
					}

				}
				
				//System.out.println("toStk: " + wsSMtf.getToStkId());
				
				if(wsSMtf.getToStkId()!=null)
				{
					SStk toStk = sStkRepository.findOne(wsSMtf.getToStkId());
					if(toStk!=null)
					{
						if(toStk.getSStkTypeDic().getIdStkType().equals(5l)) //RTV 就扣减
						{
							SPoMaterial sPoMaterial = sSpoMaterialRepository.getOne(wm.getPoMaterialId());
							if (sPoMaterial.getQtyReceived() != null) {
								sPoMaterial.setQtyReceived(sPoMaterial.getQtyReceived() - wm.getQty());
							}
							sSpoMaterialRepository.save(sPoMaterial);
						}
					}
				}
				
				if(wsSMtf.getFromStkId()!=null)
				{
					SStk fromStk = sStkRepository.findOne(wsSMtf.getFromStkId());
					SStk toStk = sStkRepository.findOne(wsSMtf.getToStkId());
					//不是 RTV的仓库到 PO， 扣减
				 
					if(!fromStk.getSStkTypeDic().getIdStkType().equals(5l)&&toStk.getSStkTypeDic().getIdStkType().equals(11l))
					{

							SPoMaterial sPoMaterial = sSpoMaterialRepository.getOne(wm.getPoMaterialId());
							if (sPoMaterial.getQtyReceived() != null) {
								sPoMaterial.setQtyReceived(sPoMaterial.getQtyReceived() - wm.getQty());
							}
							sSpoMaterialRepository.save(sPoMaterial);
						
					}
				}

			
				// update SPo
				wm.setIdMt(sMtf.getIdMt());
				mtfMaterialService.saveMtfMaterial(wm);
				break;
			}

			case 3: // 手动流转
			{

				wm.setIdMt(sMtf.getIdMt());
				mtfMaterialService.saveMtfMaterial(wm);
				updateMaterialBins(wm.getMaterialId(), wm.getFromBinId(), wsSMtf.getFromStkId());
				updateMaterialBins(wm.getMaterialId(), wm.getToBinId(), wsSMtf.getToStkId());
				logger.debug(" case 3 or 4:  to:  wm.getToBinId(): " + wm.getToBinId() + ", from :" + wm.getFromBinId()
						+ ", material id: " + wm.getMaterialId() + ", qty: " + wm.getQty());
				SInventory to = null;
				List<SInventory> sInventorys = null;
				if (wm.getLotNo() != null) {
					logger.debug("lotNo: " + wm.getLotNo());
					SInventory sInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
							wm.getToBinId(), wm.getLotNo());
					if (sInventory != null) {
						logger.debug("find from inv: " + sInventory.getIdInv());
						sInventorys = new ArrayList<SInventory>();
						sInventorys.add(sInventory);
					}
				} else {
					sInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
				}

				if (sInventorys != null && !sInventorys.isEmpty()) {
					to = sInventorys.get(0);
					logger.debug("set to: " + to.getQty() + wm.getQty());
					to.setQty(to.getQty() + wm.getQty());
				}

				else {
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

				SInventory from = null;
				List<SInventory> fromInventorys = new ArrayList<SInventory>();
				if (wm.getLotNo() != null) {

					SInventory sInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
							wm.getFromBinId(), wm.getLotNo());
					if (sInventory != null) {
						logger.debug("find from inv: " + sInventory.getIdInv());
						fromInventorys.add(sInventory);
					}
				} else {
					fromInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
							wm.getFromBinId());
				}

				if (fromInventorys != null && !fromInventorys.isEmpty()) {

					for (SInventory s : fromInventorys) {
						if (s.getQty() > 0) {
							logger.debug("sqty: " + s.getQty() + ", wmqty: " + wm.getQty());
							if (s.getQty() >= wm.getQty()) {
								s.setQty(s.getQty() - wm.getQty());
								sInventoryRepository.save(s);
								wm.setQty(0l);
								break;
							} else {
								wm.setQty(wm.getQty() - s.getQty());
								s.setQty(0l);
								sInventoryRepository.save(s);
							}
						}

					}
					if (wm.getQty() > 0) {
						SInventory first = fromInventorys.get(0);
						first.setQty(first.getQty() - wm.getQty());
						sInventoryRepository.save(first);
					}

				} else {
					logger.debug("找不到发料扣减仓位？？？？？");
					from = new SInventory();
					from.setCreationTime(new Date());
					from.setLotNo(wm.getLotNo());
					from.setQty(0 - wm.getQty());
					from.setUQty(wm.getUqty());
					from.setSBin(sBinRepository.findOne(wm.getFromBinId()));
					// to.setSMaterial(spoMaterial.getSMaterial());
					from.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
					sInventoryRepository.save(from);
				}

				break;
			
			}
			case 4: // 工单流转
			{
				wm.setIdMt(sMtf.getIdMt());
				System.out.println("idWo: " + wsSMtf.getIdWo());
				
				PWo wo =pWoRepository.findOne(wsSMtf.getIdWo());
				//产品
				if(wo.getSSo().getSMaterial().getIdMaterial().equals(wm.getMaterialId()))
				{
					Long actQty = wo.getActQty()==null?0:wo.getActQty();
					actQty = actQty+wm.getQty();
					wo.setActQty(actQty);
					pWoRepository.save(wo);
				}
				else //原料
				{
					PWoBom pWoBom = pWoBomRepository.findByIdWoAndIdMaterial(wsSMtf.getIdWo(),wm.getMaterialId());
					SStk stk = sStkRepository.findOne(wsSMtf.getToStkId());
					if(stk.getSStkTypeDic().getName().equals("车间")) //到车间已发数量变多
					{
						Long qtyRev = pWoBom.getQtyRev()==null?0l:pWoBom.getQtyRev();
						qtyRev =qtyRev+wm.getQty();
						pWoBom.setQtyRev(qtyRev);
						pWoBomRepository.save(pWoBom);
					}
				  
				}
				
				mtfMaterialService.saveMtfMaterial(wm);
				updateMaterialBins(wm.getMaterialId(), wm.getFromBinId(), wsSMtf.getFromStkId());
				updateMaterialBins(wm.getMaterialId(), wm.getToBinId(), wsSMtf.getToStkId());
				logger.debug(" case 3 or 4:  to:  wm.getToBinId(): " + wm.getToBinId() + ", from :" + wm.getFromBinId()
						+ ", material id: " + wm.getMaterialId() + ", qty: " + wm.getQty());
				SInventory to = null;
				List<SInventory> sInventorys = null;
				if (wm.getLotNo() != null) {
					logger.debug("lotNo: " + wm.getLotNo());
					SInventory sInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
							wm.getToBinId(), wm.getLotNo());
					if (sInventory != null) {
						logger.debug("find from inv: " + sInventory.getIdInv());
						sInventorys = new ArrayList<SInventory>();
						sInventorys.add(sInventory);
					}
				} else {
					sInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(), wm.getToBinId());
				}

				if (sInventorys != null && !sInventorys.isEmpty()) {
					to = sInventorys.get(0);
					logger.debug("set to: " + to.getQty() + wm.getQty());
					to.setQty(to.getQty() + wm.getQty());
				}

				else {
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

				SInventory from = null;
				List<SInventory> fromInventorys = new ArrayList<SInventory>();
				if (wm.getLotNo() != null) {

					SInventory sInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
							wm.getFromBinId(), wm.getLotNo());
					if (sInventory != null) {
						logger.debug("find from inv: " + sInventory.getIdInv());
						fromInventorys.add(sInventory);
					}
				} else {
					fromInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
							wm.getFromBinId());
				}

				if (fromInventorys != null && !fromInventorys.isEmpty()) {

					for (SInventory s : fromInventorys) {
						if (s.getQty() > 0) {
							logger.debug("sqty: " + s.getQty() + ", wmqty: " + wm.getQty());
							if (s.getQty() >= wm.getQty()) {
								s.setQty(s.getQty() - wm.getQty());
								sInventoryRepository.save(s);
								wm.setQty(0l);
								break;
							} else {
								wm.setQty(wm.getQty() - s.getQty());
								s.setQty(0l);
								sInventoryRepository.save(s);
							}
						}

					}
					if (wm.getQty() > 0) {
						SInventory first = fromInventorys.get(0);
						first.setQty(first.getQty() - wm.getQty());
						sInventoryRepository.save(first);
					}

				} else {
					logger.debug("找不到发料扣减仓位？？？？？");
					from = new SInventory();
					from.setCreationTime(new Date());
					from.setLotNo(wm.getLotNo());
					from.setQty(0 - wm.getQty());
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
				SInventory fromInventory = sInventoryRepository.findOne(wm.getInventoryId());

				if (fromInventory != null) {
					Long fromBinId = fromInventory.getSBin().getIdBin();
					wm.setFromBinId(fromBinId);

					// logger.debug("matId: " +wm.getMaterialId() +", fromBinId:
					// " +wm.getFromBinId() +", stkId:"+wsSMtf.getFromStkId());
					updateMaterialBins(wm.getMaterialId(), wm.getFromBinId(), wsSMtf.getFromStkId());
					fromInventory.setQty(fromInventory.getQty() - wm.getQty());
					sInventoryRepository.save(fromInventory);

					SSo sSo = sSoRepository.getOne(wm.getSoId());
					long rvd = 0l;
					if (sSo.getQtyDelivered() != null) {
						rvd = sSo.getQtyDelivered() + wm.getQty();

					} else {
						rvd = wm.getQty();
					}
					sSo.setQtyDelivered(rvd);
					if (rvd >= sSo.getQtySo()) // 关闭销售单
					{
						sSo.setSStatusDic(sStatusDicRepository.findOne(15l)); // 结束销售订单
					}

					sSoRepository.save(sSo);

					wm.setIdMt(sMtf.getIdMt());
					wm.setFromBinId(wm.getFromBinId());
					mtfMaterialService.saveMtfMaterial(wm);
					SInventory to = null;

					List<SInventory> sInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
							wm.getToBinId());
					if (sInventorys != null && !sInventorys.isEmpty()) {
						to = sInventorys.get(0);
						to.setQty(to.getQty() + wm.getQty());
					}

					else {
						// logger.debug("can not find inventory by tobin, so
						// create new one");
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

				} else {
					valid.setMsg("无库存，不能出货");
					// logger.debug("无库存，不能出货");
					// sMtfRepository.delete(sMtf);
					valid.setValid(false);
					break;
				}
				break;
			}

			case 6: // 销售退货
			{
				// logger.debug("material: " + wm.getMaterialId());
				updateMaterialBins(wm.getMaterialId(), wm.getToBinId(), wsSMtf.getToStkId());
				SInventory sToInventory = null;
				List<SInventory> sToInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
						wm.getToBinId());
				if (sToInventorys != null && !sToInventorys.isEmpty()) {
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

				SInventory from = null;
				List<SInventory> fromInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
						wm.getFromBinId());
				if (fromInventorys != null && !fromInventorys.isEmpty()) {

					from = fromInventorys.get(0);
					from.setQty(from.getQty() - wm.getQty());
				} else {
					from = new SInventory();
					from.setCreationTime(new Date());
					from.setLotNo(wm.getLotNo());
					from.setQty(0 - wm.getQty());
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
				// if(wm.getQty()==null)
				// {
				// continue;
				// }
				if (wm.getQty() == null || wm.getQty().equals(0l)) {
					continue;
				}
				// logger.debug(msg);
				updateMaterialBins(wm.getMaterialId(), wm.getFromBinId(), wsSMtf.getFromStkId());
				// updateMaterialBins(wm.getMaterialId(),wm.getFromBinId(),wsSMtf.getFromStkId());
				wm.setIdMt(sMtf.getIdMt());
				mtfMaterialService.saveMtfMaterial(wm);
				logger.debug(" case 7:  to:  wm.getToBinId(): " + wm.getToBinId() + ", from :" + wm.getFromBinId()
						+ ", material id: " + wm.getMaterialId() + ", qty: " + wm.getQty());
				SInventory to = null;
				if (savePmr) {
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
				List<SInventory> sInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
						wm.getToBinId());
				if (sInventorys != null && !sInventorys.isEmpty()) {
					to = sInventorys.get(0);
					to.setQty(to.getQty() + wm.getQty());
				}

				else {

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

				SInventory fromInventory = sInventoryRepository.findOne(wm.getInventoryId());
				if (fromInventory != null) {
					fromInventory.setQty(fromInventory.getQty() - wm.getQty());
					sInventoryRepository.save(fromInventory);
				} else {
				}

				break;
			}

			case 8: // 检验入库
			{

				//还需要判断是否结束销售订单。
				updateMaterialBins(wm.getMaterialId(), wm.getFromBinId(), wsSMtf.getFromStkId());
				updateMaterialBins(wm.getMaterialId(), wm.getToBinId(), wsSMtf.getToStkId());

				// logger.debug(" case 8: to: wm.getToBinId(): " +
				// wm.getToBinId() +", from :" + wm.getFromBinId() +", material
				// id: " + wm.getMaterialId() +", qty: " +wm.getQty());
				SMtfMaterial orgsMtfMaterial = sMtfMaterialRepository.findOne(wm.getIdMtfMaterial());
				
				SPoMaterial spom = orgsMtfMaterial.getSPoMaterial();
				if(spom!=null)
				{
					
					wsSMtf.setPoId(spom.getSPo().getIdPo());
				}
				// orgsMtfMaterial.gets
				orgSmtf = orgsMtfMaterial.getSMtf();
				Long qtyChecked = (orgsMtfMaterial.getQty3417() == null) ? 0 : orgsMtfMaterial.getQty3417(); // 已检数量
				Long qty = orgsMtfMaterial.getQty(); // 总数
				Long current_qty = wm.getQty();// 此次检查数量
				orgsMtfMaterial.setQty3417(current_qty + qtyChecked);
				if (current_qty + qtyChecked == qty) {
					orgsMtfMaterial.setSStatusDic(sStatusDicRepository.findOne(2l));// 已经检查(3是未检)
				} else {
					finishIQC = false;
				}
				sMtfMaterialRepository.save(orgsMtfMaterial);
				wm.setIdMt(sMtf.getIdMt());
				wm.setIdMtfMaterial(null);// 如果不设为空，就是修改原有记录了！
				mtfMaterialService.saveMtfMaterial(wm);

				SInventory to = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(wm.getMaterialId(),
						wm.getToBinId(), wm.getLotNo());

				if (to != null) {
					to.setQty(to.getQty() + wm.getQty());
				}

				else {

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

				if (from != null) {

					from.setQty(from.getQty() - wm.getQty());

				} else {
					valid.setValid(false);
					logger.debug("can not find inventory by from bin, so create new one");
					// throw new Exception("can not find inventory by from bin,
					// so create new one");

					// return valid;
				}
				sInventoryRepository.save(from);
				break;
			}

			case 9: // 往来公司出货
			{
				// System.out.println("invId: "+wm.getInventoryId());
				SInventory fromInventory = sInventoryRepository.findOne(wm.getInventoryId());
				if (fromInventory != null && fromInventory.getQty() - wm.getQty() >= 0l) {
					Long fromBinId = fromInventory.getSBin().getIdBin();
					wm.setFromBinId(fromBinId);

					
					// logger.debug("matId: " +wm.getMaterialId() +", fromBinId:
					// " +wm.getFromBinId() +", stkId:"+wsSMtf.getFromStkId());
					updateMaterialBins(wm.getMaterialId(), wm.getFromBinId(), wsSMtf.getFromStkId());
					fromInventory.setQty(fromInventory.getQty() - wm.getQty());
					sInventoryRepository.save(fromInventory);

					SBin conBin = sBinService.saveSystemBin(companyId, "CON");
					wm.setToBinId(conBin.getIdBin());
					updateMaterialBins(wm.getMaterialId(), conBin.getIdBin(), conBin.getSStk().getId());

					// SSo sSo = sSoRepository.getOne(wm.getSoId());
					// long rvd=0l;
					// if (sSo.getQtyDelivered() != null) {
					// rvd = sSo.getQtyDelivered() + wm.getQty();

					// } else {
					// rvd =wm.getQty();
					// }
					// sSo.setQtyDelivered(rvd);
					// if(rvd>=sSo.getQtySo()) //关闭销售单
					// {
					// sSo.setSStatusDic(sStatusDicRepository.findOne(15l));
					// //结束销售订单
					// }

					// sSoRepository.save(sSo);

					wm.setIdMt(sMtf.getIdMt());
					wm.setFromBinId(wm.getFromBinId());
					
					System.out.println("往来公司出货 qty：  " +wm.getQty() +", checked: " + wm.getQtyChecked());
					mtfMaterialService.saveMtfMaterial(wm);
					SInventory to = null;

					List<SInventory> sInventorys = sInventoryRepository.findByMaterialIdAndBinId(wm.getMaterialId(),
							conBin.getIdBin());
					if (sInventorys != null && !sInventorys.isEmpty()) {
						to = sInventorys.get(0);
						to.setQty(to.getQty() + wm.getQty());
					}

					else {
						// logger.debug("can not find inventory by tobin, so
						// create new one");
						to = new SInventory();
						to.setCreationTime(new Date());
						to.setLotNo(wm.getLotNo());
						to.setQty(wm.getQty());
						to.setUQty(wm.getUqty());
						to.setSBin(sBinRepository.findOne(conBin.getIdBin()));
						// to.setSMaterial(spoMaterial.getSMaterial());
						to.setSMaterial(sMaterialRepository.findOne(wm.getMaterialId()));
					}
					// logger.debug("save to bin: " + to.getQty());
					sInventoryRepository.save(to);

				} else {
				//	System.out.println("无库存或库存不足，不能出货");
					valid.setValid(false);
					valid.setMsg("无库存或库存不足，不能出货");
					// return valid;
					// logger.debug("无库存，不能出货");
					// break;
				}
				break;

			}

			case 10: // 往来公司收货
			{
				String pno = wm.getMaterialPno();

				SMaterial material = sMaterialRepository.getByCompanyIdAndPno(companyId, pno);
				SBin sbin = sBinRepository.findOne(wm.getToBinId());
				if (i == 1) {
					if (sbin.getBin().equals("IQC")) {
						toIQC = true;
					} else {
						wm.setQtyChecked(wm.getQty());
					}
				}

				SInventory sToInventory = null;
				if (wm.getLotNo() != null && !wm.getLotNo().isEmpty()) {
					sToInventory = sInventoryRepository.findByMaterialIdAndBinIdAndLotNo(material.getIdMaterial(),
							wm.getToBinId(), wm.getLotNo());

				} else {
					List<SInventory> sToInventorys = sInventoryRepository
							.findByMaterialIdAndBinId(material.getIdMaterial(), wm.getToBinId());
					if (sToInventorys != null && !sToInventorys.isEmpty()) {
						sToInventory = sToInventorys.get(0);
					}

				}
				if (sToInventory == null) {

					sToInventory = new SInventory();
					sToInventory.setCreationTime(new Date());
					/// sToInventory.setLotNo(wm.getLotNo());
					sToInventory.setQty(wm.getQty());
					/// sToInventory.setUQty(wm.getUqty());
					sToInventory.setSBin(sbin);
					sToInventory.setSMaterial(material);
					//logger.debug(" save to new bin: " + sbin.getBin() + ", qty:" + wm.getQty());
					sInventoryRepository.save(sToInventory);

				} else {
					Long cQty = sToInventory.getQty()==null?0l:sToInventory.getQty();
					sToInventory.setQty(cQty + wm.getQty());
					//logger.debug(" save to  bin: " + sbin.getBin() + ", qty:" + wm.getQty());
					sInventoryRepository.save(sToInventory);
				}
				// wm.setIdMt(sMtf.getIdMt());
				wm.setMaterialId(material.getIdMaterial());
				wm.setIdMt(sMtf.getIdMt());
				wm.setQtyChecked(wm.getQty());
				mtfMaterialService.saveMtfMaterial(wm);
				//System.out.println("收货 qty：" +wm.getQty() );

				break;

			}
			}
		}

		if (!valid.getValid()) {
			// remove smtf
			for (SMtfMaterial s : sMtf.getSMtfMaterials()) {
				sMtfMaterialRepository.delete(s);
			}
			sMtf.getSMtfMaterials().clear();
			sMtfRepository.delete(sMtf);
		}

		if (smtfType.equals(1l)) // 来料入检，检查SPO
		{
			
			if (!toIQC) {
		    System.out.println("来料入检:非IQC");
			SPo spo = sSpoRepository.findOne(wsSMtf.getPoId());
			boolean isFinished = true;
			for (SPoMaterial sm : spo.getSPoMaterials()) {
				long qtyPo = (sm.getQtyPo() == null) ? 0l : sm.getQtyPo();
				long qtyR = (sm.getQtyReceived() == null) ? 0l : sm.getQtyReceived();
				if (qtyPo > qtyR) {
					isFinished = false;
					break;
				}
			}

			if (isFinished) {
				spo.setSStatusDic(sStatusDicRepository.findOne(8l));// 结束
				sSpoRepository.save(spo);
			}

		
				if (sMtf != null) {
					sMtf.setSStatusDic(sStatusDicRepository.findOne(5l));// 完成
					sMtfRepository.save(sMtf);
				}

			} else // 发消息
			{
				
				 System.out.println("来料入检:IQC");
				List<EventReceiver> eventReceivers = eventReceiverRepository.findByIdEventAndIdCompany(11l,
						securityUtils.getCurrentDBUser().getCompany().getIdCompany());
				if (eventReceivers != null) {
					notificationService.createNotificationToReceivers(securityUtils.getCurrentDBUser().getCompany(),
							11l, sMtf.getIdMt(), NotificationMethodEnum.sys, eventReceivers, new Date());
				}

			}

		}

		if (smtfType.equals(8l)) {
		//	dd
			for (SMtfMaterial s : sMtfMaterialRepository.getBySmtfId(orgSmtf.getIdMt())) {
				if (s.getQty3417() == null || s.getQty3417() < s.getQty()) {
					finishIQC = false;
					break;
				}
			}
			if (finishIQC) {
				orgSmtf.setSStatusDic(sStatusDicRepository.findOne(5l));// 完成
				sMtfRepository.save(orgSmtf);
			}
			
			
			SPo spo = sSpoRepository.findOne(wsSMtf.getPoId());
			boolean isFinished = true;
			for (SPoMaterial sm : spo.getSPoMaterials()) {
				long qtyPo = (sm.getQtyPo() == null) ? 0l : sm.getQtyPo();
				long qtyR = (sm.getQtyReceived() == null) ? 0l : sm.getQtyReceived();
				if (qtyPo > qtyR) {
					isFinished = false;
					break;
				}
			}

			if (isFinished) {
				spo.setSStatusDic(sStatusDicRepository.findOne(8l));// 结束
				sSpoRepository.save(spo);
			}
			
			

		}

		// 往来公司发货
		if (smtfType.equals(9l) && (valid.getValid())) {
			SComCom sComCom1 = sComComRepository.findOne(wsSMtf.getIdToCompany());
			// SComCom sComCom2 =
			// sComComRepository.findOne(wsSMtf.getIdCustomerCompany());

			// 客户订单号
			// String orderNo = wsSMtf.getCoOrderNo();
			// List<SPo> spos =
			// sSpoRepository.findByCompanyIdAndCodePo(companyId, orderNo);
			Map<String, Object> model = new LinkedHashMap<String, Object>();
			Long myCompanyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
			String email1 = ""; // 往来公司邮件
			if (sComCom1.getIdCompany1().equals(myCompanyId)) {
				email1 = sComCom1.getEmail2();
			} else {
				email1 = sComCom1.getEmail1();
			}
			// String email2="";
			// if(sComCom2.getIdCompany1().equals(myCompanyId))
			// {
			// email2 = sComCom2.getEmail2();
			// }
			// else
			// {
			// email2 = sComCom2.getEmail1();
			// }
			//
			String[] emails = new String[] { email1 };
			// String[] emails = new String[]{email1,email2};
			// 采购订单
			// 公司：$!{company}
			// 订单编码: $!{poNo}, 总价： $!{totalPrice}
			// 条目：
			// $!{materials}
			// 备注：
			// $!{remark}
			String materials = "";
			for (String k : wsSMtf.getSmtfItems().keySet()) {
				{
					WSSMtfMaterial w = wsSMtf.getSmtfItems().get(k);
					SMaterial m = sMaterialRepository.findOne(w.getMaterialId());
					materials = materials + "物料: " + m.getPno() + "_" + m.getDes() + ", 数量: " + w.getQty() + ", 备注： "
							+ w.getRemark() + "\r\n";
				}

				model.put("company", securityUtils.getCurrentDBUser().getCompany().getCompanyName());
				model.put("poNo", wsSMtf.getCoOrderNo());

				model.put("materials", materials);
				try {
					emailSenderService.sendEmail(emails, "doTemplate.vm", "往来公司出货", model, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// 往来公司收货
		if (smtfType.equals(10l)) {

			long idMt = wsSMtf.getIdMt();
			// System.out.println("发货公司 B： idMt: " +idMt);
			SMtf smtf = sMtfRepository.findOne(idMt); // 公司B的往来发货
			//sMtfRepository.save(smtf);
			
			smtf.setSStatusDic(sStatusDicRepository.findOne(5l));  //更改为接收状态
			for(SMtfMaterial m:smtf.getSMtfMaterials())  //公司B全部发完
			{
				Long qty = m.getQty();
				m.setQty3417(qty);
				m.setQty(qty);
				sMtfMaterialRepository.save(m);
			//	System.out.println("id_mtf_m: " + m.getIdMtfMaterial() +", qty: " + m.getQty());
			}
			
			Long customerCompanyId = smtf.getIdCustomerCompany();  //A公司
			SMtf smtf1 = new SMtf();  //A 公司流转 A公司转入
			SBin conBin = sBinService.saveSystemBin(customerCompanyId, "CON");
			if(wsSMtf.getIdCustomerCompany()!=null&&!wsSMtf.getIdCustomerCompany().equals(0l))
			{
				Company customerCompany = companyRepository.findOne(customerCompanyId);
				
				System.out.println("A 公司转入到 CON： 公司Id: " + customerCompanyId +", 公司名： " +customerCompany.getCompanyName());
				smtf1.setCompany(customerCompany);
				smtf1.setCreationTime(new Date());
				smtf1.setDateMt(new Date());
				smtf1.setIdSmtfC(idMt);
				SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(customerCompanyId, 14l);
				if (smtfNo == null) {
					smtfNo = new SMtfNo();
					smtfNo.setDes("往来收货");
					smtfNo.setPrefix("WLSH");
					smtfNo.setCompanyId(customerCompanyId);
					smtfNo.setType(14l);
					smtfNo.setCurrentVal(1l);
					smtfNo = sMtfNoRepository.save(smtfNo);
				}
				long currentVal = smtfNo.getCurrentVal() + 1;
				smtfNo.setCurrentVal(currentVal);
				sMtfNoRepository.save(smtfNo);
				String mtNo = smtfNo.getPrefix() + String.format("%08d", currentVal);
				smtf1.setMtNo(mtNo);
				smtf1.setSMtfTypeDic(sMtfTypeDicRepository.findOne(10l));
				smtf1.setSStatusDic(sStatusDicRepository.findOne(5l));

			
				smtf1.setSStkByToStk(conBin.getSStk());

				smtf1 = sMtfRepository.save(smtf1);
				
				
				
				Map<String, Object> model = new LinkedHashMap<String, Object>();
				Long myCompanyId = securityUtils.getCurrentDBUser().getCompany().getIdCompany();
				String email1 = ""; // 往来公司邮件
				SComCom sComCom1 = sComComRepository.findByTwoCompanyId(smtf.getCompany().getIdCompany(), customerCompanyId);
				if (sComCom1.getIdCompany1().equals(myCompanyId)) {
					email1 = sComCom1.getEmail2();
				} else {
					email1 = sComCom1.getEmail1();
				}
				
			
				
				String[] emails = new String[] { email1 };
				// String[] emails = new String[]{email1,email2};
				// 采购订单
				// 公司：$!{company}
				// 订单编码: $!{poNo}, 总价： $!{totalPrice}
				// 条目：
				// $!{materials}
				// 备注：
				// $!{remark}
				String materials = "";
				for (String k : wsSMtf.getSmtfItems().keySet()) {
					{
						WSSMtfMaterial w = wsSMtf.getSmtfItems().get(k);
						SMaterial m = sMaterialRepository.findOne(w.getMaterialId());
						materials = materials + "物料: " + m.getPno() + "_" + m.getDes() + ", 数量: " + w.getQty() + ", 备注： "
								+ w.getRemark() + "\r\n";
					}

					model.put("company", securityUtils.getCurrentDBUser().getCompany().getCompanyName());
					model.put("poNo", wsSMtf.getCoOrderNo());

					model.put("materials", materials);
					try {
						emailSenderService.sendEmail(emails, "revTemplate.vm", "往来公司收货", model, null);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
				
			}
			
			
			
			
			
			
			//往来公司B手动流转从 con 转出
			System.out.println("B 公司转出 CON： 公司Id: " + smtf.getCompany().getIdCompany() +", 公司名： " +smtf.getCompany().getCompanyName());
			SMtf smtf2 = new SMtf();
			smtf2.setCompany(smtf.getCompany());
			smtf2.setCreationTime(new Date());
			smtf2.setDateMt(new Date());
			smtf2.setIdSmtfC(idMt);
			// 暂时设定为手动流转
			SMtfNo smtfNo2 = sMtfNoRepository.getByCompanyIdAndType(smtf.getCompany().getIdCompany(), 3l);
			if (smtfNo2 == null) {
				smtfNo2 = new SMtfNo();
				smtfNo2.setDes("手动流转");
				smtfNo2.setPrefix("AA");
				smtfNo2.setCompanyId(smtf.getCompany().getIdCompany());
				smtfNo2.setType(3l);
				smtfNo2.setCurrentVal(1l);
				smtfNo2 = sMtfNoRepository.save(smtfNo2);
			}
			long currentVal2 = smtfNo2.getCurrentVal() + 1;
			smtfNo2.setCurrentVal(currentVal2);
			sMtfNoRepository.save(smtfNo2);
			String mtNo2 = smtfNo2.getPrefix() + String.format("%08d", currentVal2);
			smtf2.setMtNo(mtNo2);
			smtf2.setSMtfTypeDic(sMtfTypeDicRepository.findOne(3l));
			smtf2.setSStatusDic(sStatusDicRepository.findOne(5l));

			SBin conBin2 = sBinService.saveSystemBin(smtf.getCompany().getIdCompany(), "CON");
			smtf2.setSStkByFromStk(conBin2.getSStk());
			smtf2 = sMtfRepository.save(smtf2);
			
			
			// A公司采购订单： 关闭采购订单？

			SPo mspo = null;
			if (customerCompanyId != null) //
			{
				// System.out.println(" customerId: "+customerCompanyId +",
				// coOrderNo: "+wsSMtf.getCoOrderNo());

				List<SPo> spoList = sSpoRepository.findByCompanyIdAndCodePo(customerCompanyId, wsSMtf.getCoOrderNo());
				 System.out.println(" customerId: "+customerCompanyId +",采购订单号： "+ wsSMtf.getCoOrderNo() +", 是否有多个采购订单？ " + spoList.size());
				if (!spoList.isEmpty()) {
					mspo = spoList.get(0);
					
				}
			}
			
			boolean closepo = true;
			//System.out.println(" 订单物料数：" +mspo.getSPoMaterials().size() +", 本次接收物料数: " +smtf.getSMtfMaterials().size());
		
			int sposize =0;
			for(SPoMaterial spom :mspo.getSPoMaterials())
			{
				if(spom.getQtyReceived()==null)
				{
					sposize = sposize+1;
				}
				else
				{
					if(spom.getQtyReceived().longValue()<spom.getQtyPo().longValue())
					{
						sposize = sposize+1;
					}
				}
			}
			if(sposize>smtf.getSMtfMaterials().size())
			{
				closepo=false;
			}
		
			for (SMtfMaterial sm : smtf.getSMtfMaterials()) {
				
				if(wsSMtf.getIdCustomerCompany()!=null&&!wsSMtf.getIdCustomerCompany().equals(0l))
				{
				//A公司转入
				SMtfMaterial sm1 = new SMtfMaterial();
				System.out.println("set Qty1 " + sm.getQty() +", id: " + sm.getIdMtfMaterial());
				sm1.setQty(sm.getQty());
				sm1.setQty3417(sm.getQty());
				sm1.setSBinByToBin(conBin);
				SMaterial sma = sMaterialRepository.getByCompanyIdAndPno(customerCompanyId, sm.getSMaterial().getPno());

				sm1.setSMaterial(sma);
				sm1.setSMtf(smtf1);
				sm1.setSStatusDic(sStatusDicRepository.findOne(2l));
				sMtfMaterialRepository.save(sm1);

				SInventory sToInventory = null;
				List<SInventory> sToInventorys = sInventoryRepository.findByMaterialIdAndBinId(sma.getIdMaterial(),
						conBin.getIdBin());
				if (sToInventorys != null && !sToInventorys.isEmpty()) {
					sToInventory = sToInventorys.get(0);

				}

				if (sToInventory == null) {

					sToInventory = new SInventory();
					sToInventory.setCreationTime(new Date());
					
					sToInventory.setQty(sm.getQty());

					sToInventory.setSBin(conBin);
					sToInventory.setSMaterial(sma);
					sInventoryRepository.save(sToInventory);

				} else {
					sToInventory.setQty(sToInventory.getQty() + sm.getQty());
					// logger.debug(" save to bin: " +sbin.getBin() +", qty:" +
					// wm.getQty());
					sInventoryRepository.save(sToInventory);
				}

				}
				//更改采购订单
				if(mspo!=null)
				{
				
					SPoMaterial sSpoMaterial = sSpoMaterialRepository.getByPnoAndPoId(mspo.getIdPo(), sm.getSMaterial().getPno());
					if(sSpoMaterial!=null)
					{
						Long recieved = sSpoMaterial.getQtyReceived()==null?0l:sSpoMaterial.getQtyReceived();
						sSpoMaterial.setQtyReceived(recieved + sm.getQty());
						System.out.println(" 已经接收：  " + recieved +", 本次接收: " + sm.getQty() +", 订单数： " + sSpoMaterial.getQtyPo());
						if(recieved + sm.getQty()<sSpoMaterial.getQtyPo())
						{
							System.out.println(" 不能关闭订单。");
							closepo =false;
						}
						sSpoMaterialRepository.save(sSpoMaterial);
					}
				}
				
				
				
				
				
		    //B公司销售订单
			if (sm.getSSo() != null) {
				SSo sso = sm.getSSo();
				System.out.println("关闭B公司销售订单，订单号： " +sso.getIdSo());
				Long qty = sso.getQtySo();
				Long delivered = sso.getQtyDelivered()==null?0l:sso.getQtyDelivered();
				
				if (qty <= delivered + sm.getQty()) {
					sso.setSStatusDic(sStatusDicRepository.findOne(15l));

				}
				sso.setQtyDelivered(delivered + sm.getQty());
				sSoRepository.save(sso);
			}

		

			//公司B流转物料
			SMtfMaterial sm2 = new SMtfMaterial();
			System.out.println("set Qty2 " + sm.getQty() +", id: " + sm.getIdMtfMaterial());
			sm2.setQty(sm.getQty());
			sm2.setQty3417(sm.getQty());
			sm2.setSBinByFromBin(conBin2);
			sm2.setSMaterial(sm.getSMaterial());
			sm2.setSMtf(smtf2);
			sm2.setSStatusDic(sStatusDicRepository.findOne(2l));
			sMtfMaterialRepository.save(sm2);
			//

			
			//公司B Con 库存减少
			List<SInventory> fromInventorys = sInventoryRepository.findByMaterialIdAndBinId(sm.getSMaterial().getIdMaterial(),
					conBin2.getIdBin());

			System.out.println("公司B inv size:  " + fromInventorys.size());

			Long qty = sm.getQty();
			if (fromInventorys != null && !fromInventorys.isEmpty()) {
				for (SInventory s : fromInventorys) {
					if (s.getQty() > 0) {
						if (s.getQty() >= qty) {
							s.setQty(s.getQty() - qty);
							sInventoryRepository.save(s);
							qty=0l;
							break;
						} else {
							//sm.setQty(sm.getQty() - s.getQty());
							qty = qty-s.getQty();
							s.setQty(0l);
							sInventoryRepository.save(s);
						}
					}

				}
				// 不应该发生！！！！！！！！！
				if (qty > 0) {
					System.out.println("公司B 无库存？ 不应该发生！");
					SInventory first = fromInventorys.get(0);
					first.setQty(first.getQty() - qty);
					sInventoryRepository.save(first);
				}
			}

			}

			if (closepo) {
				System.out.println("closepo:: ");
				if (mspo != null) {
					System.out.println("closepo: spoid: " + mspo.getIdPo());
					SPo spo = sSpoRepository.findOne(mspo.getIdPo());
					spo.setSStatusDic(sStatusDicRepository.findOne(8l));// 结束
					sSpoRepository.save(spo);
				}
			}
		
		}

		valid.setValid(true);
		return valid;
	}

	public WSSMtf findSMtf(Long smtfId) throws Exception {
		SMtf sMtf = sMtfRepository.findOne(smtfId);
		return toWSSMtf(sMtf);
	}
	
	public WSDoPrint printDoSmtf(Long smtfId) throws Exception {
		Company myCompany = securityUtils.getCurrentDBUser().getCompany();
		SMtf sMtf = sMtfRepository.findOne(smtfId);
		WSDoPrint w = new WSDoPrint();
		if(myCompany.getAddress()!=null)
		{
			w.setAddress(myCompany.getAddress());
		}
		else
		{
			w.setAddress("");
		}
		if(sMtf.getMtNo()!=null)
		{
			w.setDoCode(sMtf.getMtNo());
		}
		else
		{
			w.setDoCode("");
		}
		
		w.setCompanyName(myCompany.getCompanyName());
		if(sMtf.getCreationTime()!=null)
		{
			w.setDoDate(formatDate(sMtf.getCreationTime()));
		}
		w.setUrl(myCompany.getUrl());
	   List<WSDoItem> items = new ArrayList<WSDoItem>();
		for (SMtfMaterial s : sMtfMaterialRepository.getBySmtfId(sMtf.getIdMt())) {
		
			WSDoItem ws = new WSDoItem();
			if (s.getSSo() != null) {
				if (s.getSSo().getSCompanyCo() != null) {
					
					w.setCustomerName(s.getSSo().getSCompanyCo().getName());
				}

				
				ws.setCoOrderNo(s.getSSo().getCoOrderNo());
				ws.setPno(s.getSMaterial().getPno());
				ws.setName(s.getSMaterial().getDes());
				ws.setQty(""+s.getSSo().getQtySo());
				ws.setDoQty(""+s.getQty());
				
				if(s.getSSo().getQtyDelivered()!=null)
				{
					ws.setDoQtySum(""+s.getSSo().getQtyDelivered());
				}
				else
				{
					ws.setDoQtySum("");
				}
				
				ws.setRemark(s.getRemark());
				if(s.getSMaterial().getSUnitDicByUnitPur()!=null)
				{
					ws.setUnit(s.getSMaterial().getSUnitDicByUnitPur().getName());
				}
				ws.setWoNo("");
				
				items.add(ws);
			}
		}
		w.setItems(items);
	//	w.setCustomerName(customerName);
		return w;
	}
	
	
	


	
	public WSIqcPrint printIqcSmtf(Long smtfId) throws Exception {
		Company myCompany = securityUtils.getCurrentDBUser().getCompany();
		SMtf sMtf = sMtfRepository.findOne(smtfId);
		//System.out.println("smtfId: " + smtfId);
		Boolean lailiao =true;
		SMtf sm =null;
		SPo spo = null;
		if(sMtf.getSMtfTypeDic().getIdMtfType().equals(1l)) //来料
		{
			if(sMtf.getSMtfMaterials().iterator().hasNext())
			{
				 spo =  sMtf.getSMtfMaterials().iterator().next().getSPoMaterial().getSPo();
			}
		}
		else if(sMtf.getSMtfTypeDic().getIdMtfType().equals(8l))//入库
		{
			lailiao = false;
			//System.out.println("入库: 查找　 id ： " +  sMtf.getIdR());
			sm = sMtfRepository.findOne(sMtf.getIdR());
			if(sm.getSMtfMaterials().iterator().hasNext())
			{
				 spo =  sm.getSMtfMaterials().iterator().next().getSPoMaterial().getSPo();
				 System.out.println("spo : " + spo.getCodePo());
			}
		}
		else
		{
			WSIqcPrint w = new WSIqcPrint();
			return w;
		}
		WSIqcPrint w = new WSIqcPrint();
		if(myCompany.getAddress()!=null)
		{
			w.setAddress(myCompany.getAddress());
		}
		else
		{
			w.setAddress("");
		}
		if(sMtf.getMtNo()!=null)
		{
			w.setDoCode(sMtf.getMtNo());
		}
		else
		{
			w.setDoCode("");
		}
		w.setUrl(myCompany.getUrl());
		w.setCompanyName(myCompany.getCompanyName());
		if(sMtf.getCreationTime()!=null)
		{
			w.setDoDate(formatDate(sMtf.getCreationTime()));
		}
		
	   List<WSIqcItem> items = new ArrayList<WSIqcItem>();
	   
	 
	    	 int seq = 0;
	  		for (SMtfMaterial s : sMtfMaterialRepository.getBySmtfId(sMtf.getIdMt())) {
	  		
	  			WSIqcItem ws = new WSIqcItem();
	  			if (spo != null) {
	  			
	  				seq++;
	  			    w.setCustomerName(spo.getSCompanyCo().getName());
	  			
	  				w.setPoCode(spo.getCodePo());
	  				ws.setC1(""+seq);
	  				ws.setC2(s.getSMaterial().getPno());
	  				ws.setC3(s.getSMaterial().getDes());
	  				
	  				ws.setC4(""+s.getQty());
	  				if(s.getSMaterial().getSUnitDicByUnitPur()!=null)
	  				{
	  					ws.setC5(s.getSMaterial().getSUnitDicByUnitPur().getName());
	  				}
	  				else
	  				{
	  					ws.setC5("");
	  				}
	  				
	  				ws.setC6(""+s.getSBinByToBin().getBin());
	  				ws.setC7("");
	  				ws.setC8(s.getRemark()==null?"":s.getRemark());
	  				items.add(ws);
	  			}
	  		}
	  		w.setItems(items);
	 
	  
	//	w.setCustomerName(customerName);
		return w;
	}
	
	
	
	
	
	
	
	
	
	private String formatDate(Date d) {
		if (d == null)
			return "";
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt1.format(d);
	}

	private SMtf toDBMtf(WSSMtf wsSMtf, SMtf sMtf) throws Exception {

		SMtf dbSMtf = (SMtf) BeanUtil.shallowCopy(wsSMtf, SMtf.class, sMtf);
		dbSMtf.setIdMt(null);
		// logger.debug("mtNo: " +wsSMtf.getMtNo());
		if (wsSMtf.getTypeId().equals(9l) || wsSMtf.getTypeId().equals(10l)) {
			if (wsSMtf.getMtNo() == null || wsSMtf.getMtNo().trim().isEmpty()) {
				SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(
						securityUtils.getCurrentDBUser().getCompany().getIdCompany(), wsSMtf.getTypeId() + 4);

				if (smtfNo == null) {
					smtfNo = new SMtfNo();
					smtfNo.setDes("往来收发货");
					smtfNo.setPrefix("WLSH");
					smtfNo.setCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
					smtfNo.setType(wsSMtf.getTypeId() + 4);
					smtfNo.setCurrentVal(1l);
					smtfNo = sMtfNoRepository.save(smtfNo);
				}

				long currentVal = smtfNo.getCurrentVal() + 1;
				smtfNo.setCurrentVal(currentVal);
				sMtfNoRepository.save(smtfNo);
				String mtNo = smtfNo.getPrefix() + String.format("%08d", currentVal);
				dbSMtf.setMtNo(mtNo);
			}
		} else {
			if (wsSMtf.getMtNo() == null || wsSMtf.getMtNo().trim().isEmpty()) {
				SMtfNo smtfNo = sMtfNoRepository.getByCompanyIdAndType(
						securityUtils.getCurrentDBUser().getCompany().getIdCompany(), wsSMtf.getTypeId());
				long currentVal = smtfNo.getCurrentVal() + 1;
				smtfNo.setCurrentVal(currentVal);
				sMtfNoRepository.save(smtfNo);
				String mtNo = smtfNo.getPrefix() + String.format("%08d", currentVal);
				dbSMtf.setMtNo(mtNo);
			}

		}

		// System.out.println("mtNo: " + wsSMtf.getMtNo());
		dbSMtf.setCompany(securityUtils.getCurrentDBUser().getCompany());
		if (wsSMtf.getEmpMtUserId() != null) {
			dbSMtf.setUsersByEmpMt(usersRepository.findOne(wsSMtf.getEmpMtUserId()));
		} else {
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
		dbSMtf.setDateMt(new Date());

		if (wsSMtf.getIdToCompany() != null) {
			SComCom sComCom = sComComRepository.findOne(wsSMtf.getIdToCompany());
			if (sComCom.getIdCompany1().equals(securityUtils.getCurrentDBUser().getCompany().getIdCompany())) {
				dbSMtf.setIdToCompany(sComCom.getIdCompany2());
			} else {
				dbSMtf.setIdToCompany(sComCom.getIdCompany1());
			}

		}

		if (wsSMtf.getIdCustomerCompany() != null) {
			if(wsSMtf.getIdCustomerCompany().equals(0l))
			{
				dbSMtf.setIdCustomerCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
			}
			else
			{
				SComCom sComCom = sComComRepository.findOne(wsSMtf.getIdCustomerCompany());
				if (sComCom.getIdCompany1().equals(securityUtils.getCurrentDBUser().getCompany().getIdCompany())) {
					dbSMtf.setIdCustomerCompany(sComCom.getIdCompany2());
				} else {
					dbSMtf.setIdCustomerCompany(sComCom.getIdCompany1());
				}
			}
		

		}
		/*
		 * if(!wsSMtf.getSmtfItems().isEmpty()) { for(WSSMtfMaterial m:
		 * wsSMtf.getSmtfItems().values()) { m.setIdMt(wsSMtf.getIdMt());
		 * mtfMaterialService.saveMtfMaterial(m); } }
		 */

		return dbSMtf;
	}

	/*** 收货单列表 po是激活状态 ***/
	public List<WSSelectObj> getIncomeList(Long smtfType) {
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		List<SMtf> smtfs = sMtfRepository.getSMtfByType(smtfType,
				securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		for (SMtf smtf : smtfs) {
			boolean active = false;
			if (smtfType.equals(1l) || smtfType.equals(8l)) // 来料入检 和入库
			{

				if (!smtf.getSMtfMaterials().isEmpty()) {
					SMtfMaterial sm = smtf.getSMtfMaterials().iterator().next();
					if (sm.getSPoMaterial() != null) {
						SPo spo = sm.getSPoMaterial().getSPo();
						
						if (spo.getSStatusDic() != null && spo.getSStatusDic().getId().equals(11l)) {
							active = true;
						}
					}
				}
				if (active) {
					if (smtf.getSStatusDic() == null) {
						WSSelectObj w = new WSSelectObj(smtf.getIdMt(), smtf.getMtNo());
						ws.add(w);
					}

				}
			}
			// if(!smtf.getSMtfMaterials().isEmpty())
			// {
			// SMtfMaterial sm = smtf.getSMtfMaterials().iterator().next();
			// if(sm.getSPoMaterial()!=null)
			// {
			// SPo spo = sm.getSPoMaterial().getSPo();
			// if(spo.getSStatusDic()!=null&&spo.getSStatusDic().getId().equals(11l))
			// {
			// active=true;
			// }
			// }
			// }
			// if(active)
			// {
			// if(smtf.getSStatusDic()==null)
			// {
			// WSSelectObj w = new WSSelectObj(smtf.getIdMt(),smtf.getMtNo());
			// ws.add(w);
			// }
			//
			// }
			//
			// }
			else {
				// System.out.println(" type: " + smtfType +"., companyId: " +
				// securityUtils.getCurrentDBUser().getCompany().getIdCompany());
				WSSelectObj w = new WSSelectObj(smtf.getIdMt(), smtf.getMtNo());
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
			if (s.getSPoMaterial() != null) {

				wsSMtf.setCoCompanyId(s.getSPoMaterial().getSPo().getSCompanyCo().getId());
				wsSMtf.setPoId(s.getSPoMaterial().getSPo().getIdPo());
				wsSMtf.setPoCode(s.getSPoMaterial().getSPo().getCodePo());
				// break;
			}
			if (s.getSSo() != null) {
				if (s.getSSo().getSCompanyCo() != null) {
					wsSMtf.setCoCompanyId(s.getSSo().getSCompanyCo().getId());
				}

				wsSMtf.setCoOrderNo(s.getSSo().getCoOrderNo());
				// wsSMtf.set
			}
		}
		//客户
		if(sMtf.getIdCustomerCompany()!=null)
		{
			wsSMtf.setCustomerCompany(companyRepository.findOne(sMtf.getIdCustomerCompany()).getCompanyName());
		}
		//wanglai
		if(sMtf.getIdToCompany()!=null)
		{
			wsSMtf.setToCompany(companyRepository.findOne(sMtf.getIdToCompany()).getCompanyName());
		}
		// wsSMtf.setCoCompanyId(coCompanyId);
		// wsSMtf.setma
		// System.out.println("mtNo: " + wsSMtf.getMtNo());

		return wsSMtf;
	}

	private void updateMaterialBins(Long idMaterial, Long idBin, Long idStk) {

		SMaterialBinsId id = new SMaterialBinsId();
		id.setIdBin(idBin);
		id.setIdMaterial(idMaterial);
		SMaterialBins sMaterialBins = sMaterialBinsRepository.findOne(id);
		if (sMaterialBins == null) {
			sMaterialBins = new SMaterialBins();
			sMaterialBins.setId(id);
			sMaterialBins.setIdStk(idStk);
			sMaterialBins.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
			sMaterialBinsRepository.save(sMaterialBins);

		}
	}

}
