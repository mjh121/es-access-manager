package com.ssu.spec;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssu.spec.repository.ElasticsearchDAO;
import com.ssu.spec.repository.FileAccessor;
import com.ssu.spec.repository.RepositConstants;
import com.ssu.spec.service.ProcessingDataSearchService;

@Controller
public class searchController {
	
	@Autowired
	private ElasticsearchDAO elasticsearchDAO;
	
	@Autowired
	private ProcessingDataSearchService processingSearchService;
	
	FileAccessor fileAccessor;
	
	// // methods for providing dataset information
	@RequestMapping(value = "/storage/get/contents", method = RequestMethod.POST)
	@ResponseBody
	String getContent(String docName) {
		JSONArray jsonArray = null;
		ArrayList<JSONObject> searchingData = null;
		JSONObject tmpObject = null;
		String docStr = "none";
		searchingData = elasticsearchDAO.search(RepositConstants.ES_INDEX_TRAIN, RepositConstants.TRAIN_DOCTYPE_DEFAULT,
				RepositConstants.DEFAULT_SIZE);
		if(searchingData != null) {
			jsonArray = new JSONArray();
			for(JSONObject col : searchingData) {
				tmpObject = new JSONObject();
				tmpObject.put(RepositConstants.FILE_NAME, col.get(RepositConstants.FILE_NAME));
				tmpObject.put(RepositConstants.DESCRIPTION, col.get(RepositConstants.DESCRIPTION));
				tmpObject.put(RepositConstants.FILE_PATH, col.get(RepositConstants.FILE_PATH));
				jsonArray.put(tmpObject);
			}
		}
		
		for (int i=0; i<jsonArray.length(); i++) {
			tmpObject = jsonArray.getJSONObject(i);
			System.out.println("[SearchController] tmpObject"+"["+i+"] : "+ tmpObject.toString());
			if(docName.equals(tmpObject.get(RepositConstants.FILE_NAME))) {
				FileAccessor fileAccessor = new FileAccessor(tmpObject.getString(RepositConstants.FILE_PATH));
				docStr = fileAccessor.getFileString(tmpObject.getString(RepositConstants.FILE_NAME));
				break;
			}
		}
		return docStr;
	}
	
	@RequestMapping(value = "/storage/get/datasetList", method = RequestMethod.POST)
	@ResponseBody
	public String getDatasetList() {
		JSONArray jsonArray = null;
		ArrayList<JSONObject> searchingData = null;
		JSONObject tmpObject = null;
		
		searchingData = elasticsearchDAO.search(RepositConstants.ES_INDEX_TRAIN, RepositConstants.TRAIN_DOCTYPE_DEFAULT,
				RepositConstants.DEFAULT_SIZE);
		if(searchingData != null) {
			jsonArray = new JSONArray();
			for(JSONObject col : searchingData) {
				tmpObject = new JSONObject();
				tmpObject.put(RepositConstants.FILE_NAME, col.get(RepositConstants.FILE_NAME));
				tmpObject.put(RepositConstants.DESCRIPTION, col.get(RepositConstants.DESCRIPTION));
				jsonArray.put(tmpObject);
			}
		}
		
		return jsonArray.toString();
	}
	
	// search methods for silverRobot
	@RequestMapping(value = "/storage/get/origData", method = RequestMethod.POST)
	public String getOrigData(String index, String docType, String size) {
		/* use to coding : pause*/
		ArrayList<JSONObject> searchingData = null;
		searchingData = elasticsearchDAO.search(index, docType, Integer.parseInt(size));
		return searchingData.toString();
	}
	
	@RequestMapping(value = "/storage/get/procData", method = RequestMethod.POST)
	public String getProcData(String msgId) {
		String result = processingSearchService.getProcData(msgId);
		return result;
	}
}
