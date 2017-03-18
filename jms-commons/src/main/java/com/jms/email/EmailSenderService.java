package com.jms.email;

import java.util.Map;



public interface EmailSenderService {

	public void sendEmail(String[] toEmailAddresses, String template,String subject, Map<String,Object> model
		,String[] files );

}
