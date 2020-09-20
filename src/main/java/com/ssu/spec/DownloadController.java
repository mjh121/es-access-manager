package com.ssu.spec;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssu.spec.repository.ElasticsearchDAO;
import com.ssu.spec.repository.FileAccessor;
import com.ssu.spec.repository.RepositConstants;

@Controller
public class DownloadController {

	@Autowired
	ElasticsearchDAO elasticsearchDAO;
	
	FileAccessor fileAccessor;
	public DownloadController() {
		// TODO Auto-generated constructor stub
		fileAccessor = new FileAccessor();
	}
	
	@RequestMapping(value = "/specrequest", method = RequestMethod.POST)
	@ResponseBody
	public String download(String docType, String docName) {
		ArrayList<String> requestData = null;
		String docStr = null;
		
		// 1.  check file to exist
		requestData = elasticsearchDAO.searchForKey(RepositConstants.ES_INDEX_SPEC, docType, RepositConstants.FILE_NAME);
		System.out.println("[DownloadController] requstData : " +requestData);
		
		if(requestData == null || requestData.contains(docName) == false) {
			docStr = "none";
		} else {
			docStr = fileAccessor.getSpecFileString(docType, docName);
		}
		return docStr;
	}
}
