package com.ssu.spec.repository;

public interface RepositConstants {
	
	/* es info */
	String ES_CLUSTER_NAME_01 = "ssu-ss-cluster-01"; // cluster name
	String IP = "123.123.123.123"; // chage to db server access ip
	int DONE =  0;
	int PORT = 9300;
	
	int DEFAULT_SIZE = 50;
	
	// indices
	String ES_INDEX_TRAIN = "myindex";
	String ES_INDEX_SPEC = "specification";
	String ES_INDEX_IOT_DATA = "silverrobot";
	String ES_INDEX_SILVERROBOT = "silverrobot";
	
	// document type
	String TRAIN_DOCTYPE_DEFAULT = "trainingset";
	String IOT_DATA_DOCTYPE_BIO = "gear";
	String IOT_DATA_DOCTYPE_VOICE = "voice";
	String IOT_DATA_DOCTYPE_VISION = "vision";
	
	// add data key
	String FILE_NAME = "name";
	String FILE_PATH = "path";
	String RECODING_TIME = "regtime";
	String DESCRIPTION = "description";
	
	// silver robot query message type(from broker)
	String MORNING_MSG = "broker_101"; // scenario 1
	String DAILY_ROUTINE = "broker_132"; // scenario 2
	String CALLING_ROBOT = "broker_109"; // scenario 3
	String EMERGENCY_0 = "broker_1"; // scenario 4_2
	String EMERGENCY_1 = "broker_0"; // scenario 4_3
	String DAILY_CHECK = "broker_2"; // scenario 5_1
	String EN_APP = "broker_108"; // scenario 5_2
	String ENTIRE_REPORT = "broker_4"; // scenario 6

	/* spec file info */
	
	// path
	String SPEC_FILE_PATH = "/home/sslab-data/storage/specification";
	
	// module engine names
	String ANALYSIS_MODULE = "analysis";
	String COLLECTOR_MODULE = "collector";
	String CONTEXT_MODULE = "context";
	String CONTEXT_ENGINE = "contextEngine";
	
	// File Type
	int ALL_TYPE = 1;
	int FILE_TYPE = 2;
	int DIR_TYPE = 3;
	
	/* query */
	String EMPTY = "";
	
	// sort type
	String SORT_ASC = "ASC";
	String SORT_DESC = "DESC";
}
