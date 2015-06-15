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

import com.jms.domain.Config;
import com.jms.domain.db.Company;
import com.jms.domain.db.Document;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.system.DocumentRepository;

@RestController
@Transactional
public class FileUploadController {

	@Autowired private DocumentRepository documentRepository;
	@Autowired private CompanyRepository companyRepository;
	
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public Document handleFileUpload(
			@RequestParam("description") String description,
			@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {	
			String name =""+new Date().getTime();
			File source = new File(Config.docsRelativePath+name+"_"+file.getOriginalFilename());   
			file.transferTo(source);   
			 Document doc = new Document();
			 doc.setFileName(file.getOriginalFilename());
			 doc.setDescription(description);
			 doc.setName(name);
			 doc.setRelativePath(Config.docsRelativePath);
			 int size = (int) (file.getSize()/1024);
			 doc.setSize(size);
			 documentRepository.save(doc);
			 return doc; 
		}
        return null;
	}
	
	@RequestMapping(value = "/logoUpload", method = RequestMethod.POST)
	public Document handleLogoUpload(
			@RequestParam("idCompany") Integer idCompany,
			@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {	
			String name =""+new Date().getTime();
			File source = new File(Config.logoRelativePath+name+"_"+file.getOriginalFilename());   
			file.transferTo(source);   
			 Document doc = new Document();
			 doc.setFileName(file.getOriginalFilename());
			 doc.setDescription("logo");
			 doc.setName(name);
			 doc.setRelativePath(Config.docsRelativePath);
			 int size = (int) (file.getSize()/1024);
			 doc.setSize(size);
			 documentRepository.save(doc);
			 Company  company =  companyRepository.findOne(idCompany);

			 company.setDocumentByLogo(doc);
			 companyRepository.save(company);
			 return doc; 
		}
        return null;
	}
	@RequestMapping(value = "/licenseUpload", method = RequestMethod.POST)
	public Document handlicenseUpload(
			@RequestParam("idCompany") Integer idCompany,
			@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {	
			String name =""+new Date().getTime();
			File source = new File(Config.licenseRelativePath+name+"_"+file.getOriginalFilename());   
			file.transferTo(source);   
			 Document doc = new Document();
			 doc.setFileName(file.getOriginalFilename());
			 doc.setDescription("logo");
			 doc.setName(name);
			 doc.setRelativePath(Config.licenseRelativePath);
			 int size = (int) (file.getSize()/1024);
			 doc.setSize(size);
			 documentRepository.save(doc);
			 Company  company =  companyRepository.findOne(idCompany);
			 company.setDocumentByLicense(doc);
			 companyRepository.save(company);
			 return doc; 
		}
        return null;
	}
}