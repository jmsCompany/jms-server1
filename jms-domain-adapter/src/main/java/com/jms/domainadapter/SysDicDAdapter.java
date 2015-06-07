package com.jms.domainadapter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.SysDicD;
import com.jms.domain.ws.WSSysDicD;

@Service @Transactional(readOnly=true)
public class SysDicDAdapter {

	public WSSysDicD toWSSysDicD(SysDicD sysDicD) throws Exception
	{
		WSSysDicD wsSysDicD = (WSSysDicD)BeanUtil.shallowCopy(sysDicD,WSSysDicD.class);
		wsSysDicD.setType(sysDicD.getSysDic().getType());
		return wsSysDicD;
	}

}
