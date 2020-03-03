package com.tata.service.search;

import org.apache.solr.client.solrj.SolrQuery;

public class GenerateSolrQuery {



	public static SolrQuery generateSolrQuery(String searchQuery) {
		
		SolrQuery query = new SolrQuery();
		
		/* Add all the necessity filters , boost here ; Making query fields and phrase query part of client configuration
		 * OR can be added in solrconfig.xml request handler  */
		
		query.add("q", searchQuery);
		query.addFilterQuery("soldOut:false");
		query.addFilterQuery("visible:true");
		query.add("qf","title");
		query.add("wt","json");
		query.add("indent", "true");
		
		//facet query can be added here
		
		return query;
	}

}
