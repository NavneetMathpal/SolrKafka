package com.tata.service.search;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tata.kafka.model.Product;
import com.tata.kafka.model.SearchResponse;

public class ProductSearch {

	public static SearchResponse searchProduct(String searchQuery) {

		// Either use zookeeper client OR Solr java client ; as running on single
		// machine so using Solr client instead of solrCloud
		String solrUrl = "http://localhost:8983"; // Adding default solrUrl
		String commerceCollection = "commerce_products_catalog";
		String requestHandler ="select/"; // default request handler ; will be replaced later with properties file
		byte[] data = null;
		SolrDocumentList results = null;
		List<Product> products = new ArrayList<Product>();
		SearchResponse searchReponse = new SearchResponse();

		// if required some Pre-process of query
		try (InputStream configurationProperties = new FileInputStream(
				"/Users/nmathpa/Desktop/solr-kafka/src/main/resources/application.properties")) {

			Properties prop = new Properties();
			prop.load(configurationProperties);

			solrUrl = prop.getProperty("solrUrl");
			commerceCollection = prop.getProperty("commerceProductCollection");
			requestHandler = prop.getProperty("requestHandler");

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Creating Solr client for searching
		SolrClient client = new HttpSolrClient.Builder(solrUrl + "/" + commerceCollection).build();
		SolrQuery query = GenerateSolrQuery.generateSolrQuery(searchQuery);
		query.setRequestHandler(requestHandler);
		

		try {
			QueryResponse response = client.query(query);
			

			for (int i = 0; i < response.getResults().size(); i++) {
				
				Product productResponse = new Product();
				productResponse.setTitle(response.getResults().get(i).get("title").toString());
				products.add(productResponse);
			}
			searchReponse.setNoOfFound(response.getResults().getNumFound());
			searchReponse.setProducts(products);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return searchReponse;

	}

}
