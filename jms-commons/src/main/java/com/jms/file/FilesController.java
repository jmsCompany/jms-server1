package com.jms.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	 private String filePath ="/Users/renhongtao/eme_files/";
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
	@RequestMapping(value = "/getFile/{fileName}/", method = RequestMethod.GET)
	public void getImage(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {
		FileInputStream fs = new FileInputStream(new File(filePath + fileName));
		ByteStreams.copy(fs, response.getOutputStream());
	}

}
