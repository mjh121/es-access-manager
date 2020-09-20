package com.ssu.spec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssu.spec.repository.ElasticsearchDAO;
import com.ssu.spec.repository.RepositConstants;

@Service
public class ProcessingDataSearchService {

	@Autowired
	ElasticsearchDAO esDAO;
	
	public String getProcData(String msgType) {
		String queryRes = "";
		if(msgType.equals(RepositConstants.MORNING_MSG)) {
			// scenario 1
			queryRes = getMorningMessage();
		} else if(msgType.equals(RepositConstants.DAILY_ROUTINE)) {
			// scenario 2
			queryRes = getDailyRoutine();
		} else if(msgType.equals(RepositConstants.CALLING_ROBOT)) {
			// scenario 3
			queryRes = getCallingRobot();
		} else if(msgType.equals(RepositConstants.EMERGENCY_0)) {
			// scenario 4-2
			queryRes = getEmergency0();
		} else if(msgType.equals(RepositConstants.EMERGENCY_1)) {
			// scenario 4-3
			queryRes = getEmergency1();
		} else if(msgType.equals(RepositConstants.DAILY_CHECK)) {
			// scenario 5-1
			queryRes = getDailyCheck();
		} else if(msgType.equals(RepositConstants.EN_APP)) {
			// scenario 5-2
			queryRes = getEnApp();
		} else if(msgType.equals(RepositConstants.ENTIRE_REPORT)) {
			// scenario 6
			queryRes = getEntireReport();
		}
		
		return queryRes;
	}
	
	public String getMorningMessage() {
		// scenario 1
		return "";
	}
	
	public String getDailyRoutine() {
		// scenario 2
		return "";
	}
	
	public String getCallingRobot() {
		// scenario 3
		return "";
	}
	
	public String getEmergency0() {
		// scenario 4-2
		return "";
	}
	
	public String getEmergency1() {
		// scenario 4-3
		return "";
	}
	
	public String getDailyCheck() {
		// scenario 5-1
		return "";
	}
	
	public String getEnApp() {
		// scenario 5-2
		return "";
	}
	
	public String getEntireReport() {
		// scenario 6
		return "";
	}	
	
	
}
