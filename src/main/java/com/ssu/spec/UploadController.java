package com.ssu.spec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssu.spec.repository.ElasticsearchDAO;
import com.ssu.spec.repository.RepositConstants;

@Controller
public class UploadController {
	@Autowired
	private ElasticsearchDAO elasticsearchDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("this page is", "upload");
		model.addAttribute("serverTime", formattedDate );
		
		return "upload";
	}
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST) 
	@ResponseBody
	public String upload(MultipartFile uploadFile, String moduleList) {
		String fileName = "";
		String path = "";
		File inputFile = null;
		BufferedWriter writer = null;
		
		try {
			// file generate or update
			fileName = uploadFile.getOriginalFilename();
			path = RepositConstants.SPEC_FILE_PATH+"/"+moduleList+"/";
			byte[] bytes = uploadFile.getBytes();
			System.out.println("[UploadController] input file path : " +path);
			System.out.println("[UploadController] input file name : " +fileName);
			String bytesToString = new String(bytes);
			inputFile = new File(path+fileName);
			writer = new BufferedWriter(new FileWriter(inputFile));
			writer.write(bytesToString);
			
			// storage load log
			JSONObject strData = new JSONObject();
			strData.put(RepositConstants.FILE_NAME, fileName);
			strData.put(RepositConstants.FILE_PATH, path);
			strData.put(RepositConstants.RECODING_TIME, System.currentTimeMillis());
			elasticsearchDAO.load(RepositConstants.ES_INDEX_SPEC, moduleList, strData.toString());
			
			writer.flush();
			writer.close();
			
			System.out.println("[UploadController] file success uploading!");
			
		} catch (IOException ioe) {
			// TODO: handle exception
			ioe.printStackTrace();
		}
		return fileName+" upload!";
	}
}
