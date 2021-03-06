package com.jms.email;

import java.io.File;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service("emailSenderService")
public class EmailSenderServiceImpl implements EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	// @Value("#{email_prop['template']}")
	private String template = "pmrTemplate.vm";
	// @Value("#{email_prop['from']}")
	private String from = "cloud.system@vqingyun.com";

	@Override
	public void sendEmail(final String[] toEmailAddresses, final String template,final String subject, final Map<String, Object> model,
			final String[] files) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) {
				MimeMessageHelper message;
				try {
					message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					message.setTo(toEmailAddresses);
					
					message.setFrom(new InternetAddress(from));
					message.setSubject(subject);
					velocityEngine.addProperty("resource.loader", "class");
					velocityEngine.addProperty("class.resource.loader.class",
							"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
					String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
					//VelocityEngineUtils.
					System.out.println("body: " + body);
					message.setText(body, true);
				//	message.sett
					if (files != null) {
						for (String f : files) {
							FileSystemResource file = new FileSystemResource(new File(f));
							message.addAttachment(f, file);
						}
					}

				}
		    catch (MessagingException e) {
					e.printStackTrace();
				}
		}
		};
				
		
		this.mailSender.send(preparator);
		
	

	}

}
