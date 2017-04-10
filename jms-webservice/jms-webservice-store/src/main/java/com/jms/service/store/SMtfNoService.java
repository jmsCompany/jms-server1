package com.jms.service.store;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jms.domain.Config;
import com.jms.domain.db.SMtfNo;
import com.jms.domain.db.SStkTypeDic;
import com.jms.domain.ws.s.WSStkType;
import com.jms.repositories.s.SMtfNoRepository;
import com.jms.repositories.s.SStkTypeDicRepository;
import com.jms.s.ISmtfNoService;


@Service
@Transactional
public class SMtfNoService implements ISmtfNoService{

	private static final Logger logger = LogManager.getLogger(SMtfNoService.class
			.getCanonicalName());
	@Autowired
	private SMtfNoRepository sMtfNoRepository;
	
	

	@Override
	public void loadSmtfNosByCompanyId(Long companyId) {
		
       for(int type=1;type<15;type++)
       {
   		SMtfNo smtfNo = new SMtfNo();
   		smtfNo.setCompanyId(companyId);
   		smtfNo.setCurrentVal(0l);
   		smtfNo.setType((long)type);
   		switch(type)
   		{
   		  case 1:
   		   {
   			smtfNo.setDes("来料入检");
   			smtfNo.setPrefix("RT");
   			break;
   		   }
   		  case 2:
  		   {
  			smtfNo.setDes("采购退货");
  			smtfNo.setPrefix("RTV");
  			break;
  		   }
   		  case 3:
  		   {
  			smtfNo.setDes("手动流转");
  			smtfNo.setPrefix("AA");
  			break;
  		   }
   		  case 4:
  		   {
  			smtfNo.setDes("工单流转");
  			smtfNo.setPrefix("WT");
  			break;
  		   }
   		  case 5:
  		   {
  			smtfNo.setDes("出货");
  			smtfNo.setPrefix("DO");
  			break;
  		   }
   		  case 6:
  		   {
  			smtfNo.setDes("销售退货");
  			smtfNo.setPrefix("RMA");
  			break;
  		   }
   		  case 7:
 		   {
 			smtfNo.setDes("发料单");
 			smtfNo.setPrefix("SW");
 			break;
 		   }
   		  case 8:
 		   {
 			smtfNo.setDes("检验入库");
 			smtfNo.setPrefix("IS");
 			break;
 		   }
   		  case 9:
		   {
			smtfNo.setDes("工单");
			smtfNo.setPrefix("WO");
			break;
		   }
   		  case 10:
		   {
			smtfNo.setDes("采购单");
			smtfNo.setPrefix("PO");
			break;
		   }
   		  case 11:
		   {
			smtfNo.setDes("销售单");
			smtfNo.setPrefix("SO");
			break;
		   }
   		  case 12:
		   {
			smtfNo.setDes("日计划");
			smtfNo.setPrefix("DP");
			break;
		   }
		  case 13:
		   {
			smtfNo.setDes("往来发货");
			smtfNo.setPrefix("WLFH");
			break;
		   }
		  case 14:
		   {
			smtfNo.setDes("往来发货");
			smtfNo.setPrefix("WLSH");
			break;
		   }
   		  
   		}
   		
   		sMtfNoRepository.save(smtfNo);
   		
       }
 

	
	}
	



}
