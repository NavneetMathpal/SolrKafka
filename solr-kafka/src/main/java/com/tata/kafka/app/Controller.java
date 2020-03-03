package com.tata.kafka.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tata.kafka.config.KakfaConfigurationProducer;
import com.tata.kafka.model.MetaFields;
import com.tata.kafka.model.Price;
import com.tata.kafka.model.Product;
import com.tata.kafka.model.ProductSpecifications;
import com.tata.kafka.model.SearchResponse;
import com.tata.service.consumer.ConsumerCommerceCatalog;
import com.tata.service.producer.KafkaComerceCatalogProducer;
import com.tata.service.search.ProductSearch;


@RestController
public class Controller {
	
	@Autowired
    private KafkaTemplate<String, String>  kakfaConfiguration;
	
	@Autowired
	private ConsumerFactory<String, String>  consumerFactory;

    private static final String TOPIC = "product_details_search";
	
	 @RequestMapping("/publish")
	    public ResponseEntity<String> produceCommerceData() throws IOException 
	    {
	    	KafkaComerceCatalogProducer.produceCommerceCatalog(kakfaConfiguration , TOPIC);

	        return ResponseEntity.ok("Commerce data has been produced to => " + TOPIC );
	    }

	 @RequestMapping(value="/search/{query}")
	    public ResponseEntity<SearchResponse> getSearchResult(@PathVariable String query) throws IOException 
	    {
		System.out.print("query is"+query);
		 
	        return ResponseEntity.ok(ProductSearch.searchProduct(query));
	    }
	 
	 @RequestMapping("/consumer")
	    public ResponseEntity<String> productCatalogConsumer() throws IOException 
	    {
		 	
		 ConsumerCommerceCatalog.consumeCommerceData(consumerFactory,TOPIC);
		 
		return null;
	    }

	 
	 	
}
