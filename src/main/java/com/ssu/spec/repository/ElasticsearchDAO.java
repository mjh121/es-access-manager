package com.ssu.spec.repository;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.annotation.PreDestroy;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

@Repository
public class ElasticsearchDAO {
	private TransportClient esClient;
	
	public ElasticsearchDAO() {
		initDao();
	}
	
	private void initDao() {
		try {
			Settings settings = Settings.builder().
					put("cluster.name", RepositConstants.ES_CLUSTER_NAME_01).build();
			
			esClient = new PreBuiltTransportClient(settings).
					addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(RepositConstants.IP), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void CloseESClient() {
		esClient.close();
		esClient = null;
	}
	
	public int load(String index, String docType, String dataStr) {
		
		System.out.println("[ElasticsearhDAO] data : "+dataStr);
		System.out.println("[ElasticsearhDAO] index1 : "+ index);
		System.out.println("[ElasticsearhDAO] docType : "+ docType);
		
		IndexResponse res = esClient.prepareIndex(index, docType)
				.setSource(dataStr)
				.get();
		
		System.out.println("load res : "+res);
		
		return RepositConstants.DONE;
	}
	
	public int update(String index, String docType, String key, String dataStr) {
		return RepositConstants.DONE;
	}

	
	/* change search method */
	public ArrayList<String> searchForKey(String index, String docType, String key) {
		/* if key is not empty, this method use */
		return this.searchForKey(index, docType, key, RepositConstants.DEFAULT_SIZE);
	}
	
	public ArrayList<String> searchForKey(String index, String docType, String key, int size) {
		
		ArrayList<String> valueList = null;
		
		System.out.println("[ElasticsearhDAO] index1 : "+ index);
		System.out.println("[ElasticsearhDAO] docType : "+ docType);
		
		SearchResponse res = queryDefault(index, docType, size);
		
		if(res.getHits().totalHits() < 1) {}
		else {
			valueList = new ArrayList<String>();
			for(SearchHit hit : res.getHits()) {
				// extract value from each dataMap.
				System.out.println("[ElasticsearhDAO] hit info : "+ hit.getSource().get(key));
				valueList.add((String)hit.getSource().get(key));
			}
		}
		return valueList;
	}
	
	private SearchResponse queryDefault(String index, String docType, int size) {
		/* default Elasticsearch search method */
		SearchResponse res = esClient.prepareSearch(index)
					.setTypes(docType)
					.setQuery(QueryBuilders.matchAllQuery())
					.setSize(size).execute().actionGet();
		
		return res;
	}
	
	// add 2018.04.11
	public ArrayList<JSONObject> search(String index, String docType, int size) {
		
		SearchRequestBuilder defaultBuilder = null;
		SearchRequestBuilder sizeSetBuilder = null;
		SearchResponse res = null;
		ArrayList<JSONObject> valueList = null;
		
		defaultBuilder = queryDefault(index, docType);
		sizeSetBuilder = setQuerySize(defaultBuilder, size);
		res = executeQuery(sizeSetBuilder);
		
		valueList = extractHitSource(res);
		
		return valueList;
	}
	
	public ArrayList<JSONObject> search(String index, String docType, int size, String order) {
		SearchRequestBuilder defaultBuilder = null;
		SearchRequestBuilder sizeSetBuilder = null;
		SearchRequestBuilder sortSetBuilder = null;
		SearchResponse res = null;
		ArrayList<JSONObject> valueList = null;
		
		defaultBuilder = queryDefault(index, docType);
		sortSetBuilder = setQuerySort(defaultBuilder, RepositConstants.RECODING_TIME, order);
		sizeSetBuilder = setQuerySize(sortSetBuilder, size);
		res = executeQuery(sizeSetBuilder);
		
		valueList = extractHitSource(res);
		
		return valueList;  
	}
	/* this method have to writing for code. */ 
	public ArrayList<JSONObject> search(String index, String docType, int size, String order, String[] selectionAttribute, String key, String value) {
		return null;
	}
	
	private ArrayList<JSONObject> extractHitSource(SearchResponse res) {
		ArrayList<JSONObject> valueList = null;
		
		if(res.getHits().totalHits() < 1) {}
		else {
			valueList = new ArrayList<JSONObject>();
			for(SearchHit hit : res.getHits()) {
				// extract value from each dataMap.
				System.out.println("[ElasticsearhDAO] hit info : "+ hit.getSource());
				valueList.add(new JSONObject(hit.getSource()));
			}
		}
		
		return valueList;
	}
	
	// query method
	private SearchRequestBuilder queryDefault(String index, String docType) {
		SearchRequestBuilder queryBuilder = esClient.prepareSearch(index)
				.setTypes(docType);
		return queryBuilder;
	}
	
	private SearchRequestBuilder setQuerySort(SearchRequestBuilder queryBuilder, String key, String orderType) {
		SortOrder sortOrder = null;
		
		if(orderType.equals(RepositConstants.SORT_ASC)) {
			sortOrder = SortOrder.ASC;
		} else {
			sortOrder = SortOrder.DESC;
		}
		return queryBuilder.addSort(key, sortOrder);
	}
	
	private SearchRequestBuilder setQuerySize(SearchRequestBuilder queryBuilder, int size) {
		return queryBuilder.setSize(size);
	}
	
	/* this method have to writing for code. */ 
	private SearchRequestBuilder setQuerySelection(SearchRequestBuilder queryBuilder, String[] selectionVariable) {
		return null;
	}
	
	/* this method have to writing for code. */ 
	private SearchRequestBuilder setQueryCondition(SearchRequestBuilder queryBuilder, String key, String value) {
		return null;
	}
	
	private SearchResponse executeQuery(SearchRequestBuilder queryBuilder) {
		return queryBuilder.execute().actionGet();
	}
	
	public int delete(String index, String docType, String key, String dataStr) {
		return 0;
	}
	
	@PreDestroy
	public void destroy() {
		CloseESClient();
	}
}
