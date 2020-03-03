package com.tata.service.producer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;

import com.tata.kafka.model.MetaFields;
import com.tata.kafka.model.Price;
import com.tata.kafka.model.ProductSpecifications;
import com.tata.kafka.model.Workflow;


public class KafkaComerceCatalogProducer {
	
	public static void produceCommerceCatalog(KafkaTemplate<String, String> kakfaConfiguration, String topic) throws IOException {	
		
		long incrementalId=1234;  //Random id which will get incremented by each product
		long sellerId=5678;
		String manufacturer="manfac";  
		Float minPrice =new Float(10);
		Float maxPrice=new Float(100);
		Float range = new Float(50);
	
		//Date is constant , we will change String date to Date format when indexing to Solr
		String publishedAt="2020-02-12T08:05:39.743Z";
		String createdAt="2010-08-23T05:53:16.134Z";
		String updatedAt="2019-08-23T05:53:16.134Z";
		
		Resource resource = new ClassPathResource("productAttributes.csv");
		File file = resource.getFile();
	    BufferedReader bufferReader = null;
	    String line = "";
	    String csvSeperator = ",";
	    
	    try {
        	bufferReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
        	while ((line = bufferReader.readLine()) != null) {
        		
                String[] productTitle = line.split(csvSeperator);
                ProductSpecifications productSpecifications=new ProductSpecifications(); //ProductSpecification object
                
                //Setting productSpecification
                productSpecifications.setProduct_id(++incrementalId);
                productSpecifications.setSellerId(++sellerId);
                productSpecifications.setTitle(productTitle[0]);
                productSpecifications.setManufacturer(manufacturer+"_"+sellerId);
                productSpecifications.setLowQuantity(false);
                productSpecifications.setSoldOut(false);
                productSpecifications.setBackorder(false);
                productSpecifications.setRequiresShipping(true);
                productSpecifications.setVisible(true);
                productSpecifications.setPublishedAt(publishedAt);
                productSpecifications.setCreatedAt(createdAt);
                productSpecifications.setUpdatedAt(updatedAt);
                
                //Price 
                Price price= new Price();
                price.setMinPrice(++minPrice);
                price.setMaxPrice(++maxPrice);
                price.setRange(++range);
                productSpecifications.setPrice(price);
                
                //MetaField ( Assuming only 2 fields , as we do not have commerce data available ;if we get the data this can be dynamic (generating random Interger)
                List<MetaFields>  metaFields= new ArrayList<MetaFields>();
                MetaFields metaField1 = new MetaFields();
                metaField1.setKey("capacity");
                metaField1.setValue(new Random().nextInt(100)+ "ton");
               
                MetaFields metaField2 = new MetaFields();
                metaField2.setKey("size");
                metaField2.setValue(new Random().nextInt(100) + "inch");
        
                metaFields.add(metaField1);
                metaFields.add(metaField2);
                productSpecifications.setMetaFields(metaFields);
                
                //workFlow
                Workflow workFlow = new Workflow();
                workFlow.setStatus("New");
                productSpecifications.setWorkFlow(workFlow);
                
                //make object to Json string
                String productAsJsonString=ObjectToJsonString.changeObjectToJsonString(productSpecifications); 
                
                //Send  continuous data stream to Kafka topic 
            	kakfaConfiguration.send(topic,productAsJsonString);
            	
               

            }
        }
    	catch (Exception e) {
			e.printStackTrace();
		}
	    
		
	}

}
