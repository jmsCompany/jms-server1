package com.jms.service.production;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.db.EhsItem;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.ehs.WSEHSItem;
import com.jms.domainadapter.BeanUtil;
import com.jms.repositories.p.EhsItemRepository;
import com.jms.web.security.SecurityUtils;

@Service
@Transactional
public class EhsItemService {

	private static final Logger logger = LogManager.getLogger(EhsItemService.class
			.getCanonicalName());
	@Autowired
	private EhsItemRepository ehsItemRepository;
	
	
	@Autowired private SecurityUtils securityUtils;
	
	@Transactional(readOnly=false)
	public WSEHSItem saveWSEHSItem(WSEHSItem wsEHSItem) throws Exception {
		EhsItem ehsItem;
		if(wsEHSItem.getIdEhs()!=null&&!wsEHSItem.getIdEhs().equals(0l))
		{
			ehsItem = ehsItemRepository.findOne(wsEHSItem.getIdEhs());
		}
		else
		{
			ehsItem = new EhsItem();
	
		}
		EhsItem dbEhsItem= toDBEhsItem(wsEHSItem,ehsItem);
		dbEhsItem = ehsItemRepository.save(dbEhsItem);
		return wsEHSItem;		
		
	}

	@Transactional(readOnly=false)
	public Valid deleteWSEHSItem(Long idEhs)
	{
		Valid valid = new Valid();
		ehsItemRepository.delete(idEhs);
		valid.setValid(true);
		
		return valid;
	}

	
	@Transactional(readOnly=true) 
	public WSEHSItem findWSEHSItem(Long idEhs) throws Exception
	{	
		EhsItem ehsItem= ehsItemRepository.findOne(idEhs);
		return  toWSEHSItem(ehsItem);
		
	}
	
	protected EhsItem toDBEhsItem(WSEHSItem wsEHSItem,EhsItem ehsItem) throws Exception
	{
	
		EhsItem dbEhsItem = (EhsItem)BeanUtil.shallowCopy(wsEHSItem, EhsItem.class, ehsItem);

        
		
		dbEhsItem.setIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		return dbEhsItem;
	}
	
	private WSEHSItem toWSEHSItem(EhsItem ehsItem) throws Exception
	{
		WSEHSItem pc = (WSEHSItem)BeanUtil.shallowCopy(ehsItem, WSEHSItem.class, null);

		if(ehsItem.getType().equals(1l))
		{
			pc.setStype("不重要");
		}else
		{
			pc.setStype("重要");
		}
		return pc;
	}
}
