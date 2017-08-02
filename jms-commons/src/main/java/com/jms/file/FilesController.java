package com.jms.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.io.ByteStreams;

/**
 * @author 任宏涛， ren@ecust.edu.cn
 *
 * @created 2016年1月4日 下午9:38:36
 *
 */

@RestController
public class FilesController {

	private static final Log logger = LogFactory.getLog(FilesController.class);
    @Value("${filePath}")
	private String filePath;
	//private String filePath = "D:/eme_files/";
	@Autowired
	private FileUploadService fileUploadService;
/*
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public FileMeta uploadFile(MultipartHttpServletRequest request, HttpServletResponse response) {
		FileMeta fileMeta = new FileMeta();
		if (request.getFileNames().hasNext()) {
			fileMeta = fileUploadService.upload(request, response);
		}
		return fileMeta;
	}
*/
	@RequestMapping(value = "/getFile/{fileName}/{orgFileName}/", method = RequestMethod.GET)
	public void getFile(@PathVariable("fileName") String fileName,@PathVariable("orgFileName") String orgFileName,  HttpServletRequest request,  
          HttpServletResponse response) throws IOException {
		
		 response.setContentType("text/html;charset=UTF-8");  
	     request.setCharacterEncoding("UTF-8");
	     File f = new File(filePath + fileName);
	     long fileLength =f.length();  
	     response.setHeader("Content-disposition", "attachment; filename="  
	                + new String(orgFileName.getBytes("utf-8"), "ISO8859-1"));  
	     response.setHeader("Content-Length", String.valueOf(fileLength));
		FileInputStream fs = new FileInputStream(f);
		ByteStreams.copy(fs, response.getOutputStream());
	}
	
	
	@RequestMapping(value = "/getImage/{fileName}/", method = RequestMethod.GET)
	public void getImage(@PathVariable("fileName") String fileName,  HttpServletRequest request,  
          HttpServletResponse response) throws IOException {
		 String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		// response.setContentType("image/" +ext);  
		 if(ext.equals("jpg"))
			{
				response.setContentType("image/jpeg");
			}
			else
			{
				response.setContentType("image/" + ext);
			}
	  //   request.setCharacterEncoding("UTF-8");
	     File f = new File(filePath + fileName);
	     long fileLength =f.length();  
	     response.setHeader("Content-disposition", "attachment; filename="  
	                + new String(fileName.getBytes("utf-8"), "ISO8859-1"));  
	     response.setHeader("Content-Length", String.valueOf(fileLength));
		FileInputStream fs = new FileInputStream(f);
		ByteStreams.copy(fs, response.getOutputStream());
	}

}
