package com.example.demo.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerDemo {

	public static void main(String[] args) {
		String bootstrapServer = "127.0.0.1:9092";

		// Create producer properties
		Properties prop = new Properties();
		
		// Old way of setting properties
		//prop.setProperty("bootstrap.servers", bootstrapServer);
		//prop.setProperty("key.serializer", StringSerializer.class.getName());
		//prop.setProperty("value.serializer", StringSerializer.class.getName());
		
		// New way
		prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		// Create a producer record
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>
														("first_topic", "3nd Message from Java");
		
		// Create kafka producer and send
		KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
		
		producer.send(producerRecord);
		
		// flush data to send data to the topic
		producer.flush();
		producer.close();
	}

}
