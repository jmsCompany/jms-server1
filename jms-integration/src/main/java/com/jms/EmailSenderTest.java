package com.jms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jms.email.EmailSenderService;


@Service
public class EmailSenderTest {

	@Autowired
	private EmailSenderService emailSenderService;
	
	public void testSendEmail() throws Exception {
		String[] to = new String[] { "conceit_conceit@163.com" };
		String subject = "测试邮件";
		String[] files = new String[] { "c:/ImbaMallLog.txt" };
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", "任宏涛");
		emailSenderService.sendEmail(to, subject, model, files);
		
	}
}
