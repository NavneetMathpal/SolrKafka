# Assignment Kafka Solr

This Project covers how to use Spring Boot with Spring Kafka to Publish JSON/String message to a Kafka topic
## Start Zookeeper
- `bin/zookeeper-server-start.sh config/zookeeper.properties`

## Start Kafka Server
- `bin/kafka-server-start.sh config/server.properties`

## Create Kafka Topic
- `bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic Kafka_Example`

## Start Solr
- '{SolrInstallationDir}/bin/./solr start'

## Consume from the Kafka Topic via Console
- `bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic Kafka_Example --from-beginning`

## Publish message via csv commerce file
- `http://localhost:9999/publish

## Consume message via kafka message queue
- 'http://localhost:9999/consumer'
 
## Search request 
- 'http://localhost:9999/search/{searchQuery}
