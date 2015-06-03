package com.jms.web;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public String handleFormUpload(@RequestParam("relativePath") String relativePath,
			@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			System.out.println(relativePath +", "+file.getOriginalFilename());
			File source = new File("D:/1.txt");   
			file.transferTo(source);   
			return "uploadSuccess";
		}
		return "uploadFailure";
	}
}