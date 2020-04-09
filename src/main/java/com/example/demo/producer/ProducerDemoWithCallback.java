package com.example.demo.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemoWithCallback {

	private static Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class);

	public static void main(String[] args) {
		System.out.println("Hello World!");
		
		String bootstrapServer = "127.0.0.1:9092";

		// Create producer properties
		Properties prop = new Properties();
		
		prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		// Create a producer record
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>
														("first_topic", "3nd Message from Java");
		
		// Create kafka producer and send
		KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
		
		producer.send(producerRecord, new Callback() {
			

			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if(exception == null) {
					logger.info("Received new metadata. \n " + 
								"Topic: " + metadata.topic() + "\n" +
								"Partition: " + metadata.partition() + "\n" +
								"Offset: " + metadata.offset() +"\n" +
								"Timestamp: " + metadata.timestamp());
					
				} else {
					logger.error("Error while sending messages", exception);
				}
				 
			}
		});
		
		// flush data to send data to the topic
		producer.flush();
		
		producer.close();
	}

}
