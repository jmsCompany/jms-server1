package com.jms.email;

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
		String[] to = new String[] { "hongtao.ren@vqingyun.com" };
		String subject = "测试邮件";
		//String[] files = new String[] { "c:/ImbaMallLog.txt" };
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("woNo", "任宏涛");
		model.put("pNo", "dd");
		model.put("machine", "machine");
		//emailSenderService.sendEmail(to, subject, model, files);
		emailSenderService.sendEmail(to, "mrTemplate.vm",subject, model, null);
		
	}
}
