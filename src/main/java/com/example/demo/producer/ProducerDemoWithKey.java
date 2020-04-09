package com.example.demo.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemoWithKey {

	private static Logger logger = LoggerFactory.getLogger(ProducerDemoWithKey.class);

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Hello World!");

		String bootstrapServer = "127.0.0.1:9092";

		// Create producer properties
		Properties prop = new Properties();

		prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// Create kafka producer
		KafkaProducer<String, String> producer = new KafkaProducer<>(prop);

		for (int i = 0; i < 15; i++) {
			String topicName = "first_topic";
			String value = "Hello World" + Integer.toString(i);
			String key = "Key ID : " + Integer.toString(i);
			
			// Create a producer record
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topicName, key, value);

			logger.info("Key" + key);
			
			// Send data - asynchronous
			producer.send(producerRecord, new Callback() {
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if (exception == null) {
						logger.info("Received new metadata. \n " + "Topic: " + metadata.topic() + "\n" + "Partition: "
								+ metadata.partition() + "\n" + "Offset: " + metadata.offset() + "\n" + "Timestamp: "
								+ metadata.timestamp());

					} else {
						logger.error("Error while sending messages", exception);
					}
				}
			}).get(); // Block the .send() to make it synchronous -- dont do this in PRODUCTION
		}

		// flush data to send data to the topic
		producer.flush();
		producer.close();
	}

}
