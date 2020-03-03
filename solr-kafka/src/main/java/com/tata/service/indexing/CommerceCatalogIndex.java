package com.tata.service.indexing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.json.JSONObject;

public class CommerceCatalogIndex {
	
	public static void indexCommerceCatalog(Collection<SolrInputDocument> commerceDocList) throws SolrServerException, IOException {
		
		
		//Collection docList = new ArrayList();
		
		//Indexing through SolrInputDocument as we will do the batch indexing (if not batch indexing we can just push the json data)
		
		String solrUrl = "http://localhost:8983"; // Adding default solrUrl
		String commerceCollection = "commerce_products_catalog";
		
		//if required some Pre-process of query
		try (InputStream configurationProperties = new FileInputStream("../../../../../resources/application.properties")) {

            Properties prop = new Properties();
            prop.load(configurationProperties);
            
            solrUrl = prop.getProperty("solrUrl");
            commerceCollection= prop.getProperty("commerceProductCollection");
            

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
		//Creating Solr client for searching
		SolrClient client = new HttpSolrClient.Builder(solrUrl+"/"+commerceCollection).build();
		
		System.out.println("size is" + commerceDocList.size());
		
		 UpdateResponse resp = client.add(commerceDocList);
		 
		
		 client.commit();
		 //commerceDocList.clear();
		
	}



}
