package com.ssu.spec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssu.spec.repository.ElasticsearchDAO;
import com.ssu.spec.repository.RepositConstants;

@Controller
public class InputController {
	
	@Autowired
	private ElasticsearchDAO elasticsearchDAO;

	@RequestMapping(value = "/storage/load", method = RequestMethod.POST)
	@ResponseBody
	public String loadData(String index, String docType, String value) {
		/* this method use for sensor and processed data load.
		 * write log file using mapping table 
		 * 1. mapping table search
		 * 2. write data to mapping log file */

		return "done";
	}
	
	@RequestMapping(value = "/storage/load/training", method = RequestMethod.POST)
	@ResponseBody
	public String loadTrainset(String name, String path, String description) {
		// formatting JSON for load
		JSONObject jsonData = new JSONObject();
		jsonData.put(RepositConstants.FILE_NAME, name);
		jsonData.put(RepositConstants.FILE_PATH, path);
		jsonData.put(RepositConstants.RECODING_TIME, System.currentTimeMillis());
		jsonData.put(RepositConstants.DESCRIPTION, description);
		
		String loadingResult = load(RepositConstants.ES_INDEX_TRAIN, RepositConstants.TRAIN_DOCTYPE_DEFAULT, jsonData.toString());
		
		return loadingResult;
	}
	
	private String load(String index, String index2, String data) {
		
		if(RepositConstants.DONE == elasticsearchDAO.load(index, index2, data.toString()))
			return "done";
		else
			return "fail";
	}
}
