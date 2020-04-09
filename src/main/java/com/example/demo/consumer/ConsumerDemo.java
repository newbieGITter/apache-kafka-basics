package com.example.demo.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerDemo {

	public static void main(String[] args) throws InterruptedException {
		Logger logger = LoggerFactory.getLogger(ConsumerDemo.class);

		String bootstrapServer = "127.0.0.1:9092";
		String consumerGroupId = "my-second-application";
		String topicName = "first_topic";

		// Create producer properties
		Properties prop = new Properties();

		// Old way of setting properties
		// prop.setProperty("bootstrap.servers", bootstrapServer);
		// prop.setProperty("key.serializer", StringSerializer.class.getName());
		// prop.setProperty("value.serializer",
		// StringSerializer.class.getName());

		// New way
		prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		prop.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		prop.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
		prop.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		// Create a consumer
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop);

		// Subscribe a consumer to our topic
		consumer.subscribe(Collections.singleton(topicName));

		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

			for (ConsumerRecord<String, String> record : records) {
				logger.info("Key:" + record.key() + ", " + "Value: " + record.value());
				logger.info("Partition:" + record.partition() + ", " + "Offset:" + record.offset());
			}
			Thread.sleep(5000);
			logger.info("Sleeping for a while............");
			break;
		}

		// closing consumer
	}

}
