package com.jms.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jms.domain.Config;
import com.jms.domain.db.Company;
import com.jms.domain.db.Document;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Message;
import com.jms.domain.ws.MessageTypeEnum;
import com.jms.messages.MessagesUitl;
import com.jms.repositories.company.CompanyRepository;
import com.jms.repositories.system.DocumentRepository;
import com.jms.repositories.user.UsersRepository;

@RestController
@Transactional
public class FileController {

	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UsersRepository usersRepository;
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public Document handleFileUpload(
			@RequestParam("description") String description,
			@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			String name = "" + new Date().getTime();
			File source = new File(Config.docsRelativePath + name + "_"
					+ file.getOriginalFilename());
			file.transferTo(source);
			Document doc = new Document();
			doc.setFileName(file.getOriginalFilename());
			doc.setDescription(description);
			doc.setName(name);
			doc.setRelativePath(Config.docsRelativePath);
			Long size =(file.getSize() / 1024);
			doc.setSize(size);
			documentRepository.save(doc);
			return doc;
		}
		return null;
	}

	@RequestMapping(value = "/logoUpload", method = RequestMethod.POST)
	public ResponseEntity<InputStreamResource> handleLogoUpload(
			@RequestParam("idCompany") Long idCompany,
			@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			String name = "" + new Date().getTime();
			File source = new File(Config.logoRelativePath + name + "_"
					+ file.getOriginalFilename());
			file.transferTo(source);
			Document doc = new Document();
			doc.setFileName(file.getOriginalFilename());
			doc.setDescription("logo");
			doc.setName(name);
			doc.setRelativePath(Config.logoRelativePath);
			Long size = (file.getSize() / 1024);
			doc.setSize(size);
			documentRepository.save(doc);
			Company company = companyRepository.findOne(idCompany);
			company.setDocumentByLogo(doc);
			companyRepository.save(company);
			FileSystemResource logo = new FileSystemResource(
					Config.logoRelativePath + name + "_"
							+ file.getOriginalFilename());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			return ResponseEntity
					.ok()
					.headers(headers)
					.contentLength(logo.contentLength())
					.contentType(
							MediaType
									.parseMediaType("application/octet-stream"))
					.body(new InputStreamResource(logo.getInputStream()));
		}
		return null;
	}

	@RequestMapping(value = "/licenseUpload", method = RequestMethod.POST)
	public ResponseEntity<InputStreamResource> handlicenseUpload(
			@RequestParam("idCompany") Long idCompany,
			@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			String name = "" + new Date().getTime();
			File source = new File(Config.licenseRelativePath + name + "_"
					+ file.getOriginalFilename());
			file.transferTo(source);
			Document doc = new Document();
			doc.setFileName(file.getOriginalFilename());
			doc.setDescription("logo");
			doc.setName(name);
			doc.setRelativePath(Config.licenseRelativePath);
			Long size = (file.getSize() / 1024);
			doc.setSize(size);
			documentRepository.save(doc);
			Company company = companyRepository.findOne(idCompany);
			company.setDocumentByLicence(doc);
			companyRepository.save(company);
			FileSystemResource license = new FileSystemResource(
					Config.licenseRelativePath + name + "_"
							+ file.getOriginalFilename());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			return ResponseEntity
					.ok()
					.headers(headers)
					.contentLength(license.contentLength())
					.contentType(
							MediaType
									.parseMediaType("application/octet-stream"))
					.body(new InputStreamResource(license.getInputStream()));
		}
		return null;
	}
	
	@RequestMapping(value = "/download/{idDocument}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> download(@PathVariable("idDocument") Long idDocument) throws IOException
	{
		
		Document d = documentRepository.findOne(idDocument);
		
		FileSystemResource file = new FileSystemResource(
				d.getRelativePath() + d.getName() + "_"
						+ d.getFileName());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentLength(file.contentLength())
				.contentType(
						MediaType
								.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(file.getInputStream()));
	}
	
	
	@RequestMapping(value = "/cvUpload", method = RequestMethod.POST)
	public Document handleCVUpload(
			@RequestParam("idUser") Long idUser,
			@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			String name = "" + new Date().getTime();
			File source = new File(Config.cvRelativePath + name + "_"
					+ file.getOriginalFilename());
			file.transferTo(source);
			Document doc = new Document();
			doc.setFileName(file.getOriginalFilename());
			doc.setDescription("CV");
			doc.setName(name);
			doc.setRelativePath(Config.cvRelativePath);
			Long size =  (file.getSize() / 1024);
			doc.setSize(size);
			documentRepository.save(doc);
			Users user = usersRepository.findOne(idUser);
			user.setDocument(doc);
			usersRepository.save(user);
			return doc;
		}
		return null;
	}
	
}