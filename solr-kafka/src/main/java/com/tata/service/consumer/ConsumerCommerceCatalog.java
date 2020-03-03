package com.tata.service.consumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.tata.kafka.config.KakfaConfigurationProducer;
import com.tata.service.indexing.CommerceCatalogIndex;
import com.tata.service.search.FlatJsonDocument;

@Service
public class ConsumerCommerceCatalog {

	public static void consumeCommerceData(ConsumerFactory<String, String> consumerFactory, String topic) {

		Consumer<String, String> consumer = consumerFactory.createConsumer();

		Thread kafkaConsumerThread = new Thread(() -> {
			consumer.subscribe(Arrays.asList(topic));
			try {
				runSingleWorker(consumer);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		// Thread starting
		kafkaConsumerThread.start();
	}

	// Consumer<String, String> consumer = consumerFactory.createConsumer();
	// consumer.subscribe(Arrays.asList(topic));

	public static void runSingleWorker(Consumer<String, String> consumer) throws SolrServerException, IOException {

		Collection<SolrInputDocument> commerceDocList = new ArrayList<SolrInputDocument>();

		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					SolrInputDocument doc = new SolrInputDocument();
					String message = record.value();

					// Flat Json string and change date to Solr compatible date
					JSONObject commerceData = FlatJsonDocument.processJsonString(message);

					commerceData.keySet().forEach(keyStr -> {
						Object keyvalue = commerceData.get(keyStr);
						doc.addField(keyStr, keyvalue);
					});
					
					//System.out.println("doc is" + doc);
					commerceDocList.add(doc);

				}
				
				//Batch indexing in solr ( Indexing the records in a batch of 100 )
				
				
				CommerceCatalogIndex.indexCommerceCatalog(commerceDocList);
				commerceDocList.clear();

			}

		} catch (WakeupException e) {
			e.printStackTrace();
		}

		finally {
			consumer.close();
		}
	}
}
