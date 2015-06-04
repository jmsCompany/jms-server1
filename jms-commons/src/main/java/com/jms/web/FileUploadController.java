package com.jms.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jms.domain.db.Documents;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.system.DocumentRepository;

@RestController
@Transactional
public class FileUploadController {
	
	@Autowired private MessagesUitl messagesUitl;
	@Autowired private DocumentRepository documentRepository;
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public Message handleFormUpload(
			@RequestParam("description") String description,
			@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			//todo: think what relative path we should use???
			//todo rename the column name to timestamp, size int to long
			String relativePath ="tmp/";
			String name =""+new Date().getTime();
			File source = new File("E:/"+relativePath+name+"_"+file.getOriginalFilename());   
			file.transferTo(source);   
			 Documents doc = new Documents();
			 doc.setFileName(file.getOriginalFilename());
			 doc.setDescription(description);
			 doc.setName(name);
			 doc.setRelativePath(relativePath);
			 int size = (int) (file.getSize()/1024);
			 doc.setSize(size);
			 documentRepository.save(doc);
			 return messagesUitl.getMessage("system.uploadSuccess", null, MessageTypeEnum.INFOMATION);
		}
		return messagesUitl.getMessage("system.uploadFailure", null, MessageTypeEnum.ERROR);
	}
}