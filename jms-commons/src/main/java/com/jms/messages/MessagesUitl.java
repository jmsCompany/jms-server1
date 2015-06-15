package com.jms.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.locale.LocalUtil;

@Service
public class MessagesUitl {
	
	
	@Autowired
	private ResourceBundleMessageSource source;
	

	public  Message getMessage(String strMsgSrc , Object[] args ,MessageTypeEnum messageTypeEnum ){
		Message msg = new Message();
		
		String message = source.getMessage(strMsgSrc, args, LocalUtil.getLocal());
		msg.setCode(strMsgSrc);
		msg.setMessage(message);
		msg.setMessageTypeEnum(messageTypeEnum);
		return msg;
	}
}
