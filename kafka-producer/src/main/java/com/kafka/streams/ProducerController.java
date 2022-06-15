package com.kafka.streams;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

	@PostMapping("/publish")
	public void messageToTopic(@RequestParam("message") String message) {

		Producer<String, String> producer = new KafkaProducer<String, String>(defaultProperties());
		producer.send(new ProducerRecord<String, String>("custom-topic", "****************************"));
		for (int i = 0; i < 5; i++)
			producer.send(new ProducerRecord<String, String>("input-topic", Integer.toString(i) + " - " + message));

		String infoMessage = "Message sent successfully by Kafka";
		producer.send(new ProducerRecord<String, String>("info-topic", infoMessage));
		producer.close();
	}

	private Properties defaultProperties() {
		Properties props = new Properties();

		// Assign localhost id
		props.put("bootstrap.servers", "localhost:9092");

		// Set acknowledgements for producer requests.
		props.put("acks", "all");

		// If the request fails, the producer can automatically retry,
		props.put("retries", 0);

		// Specify buffer size in config
		props.put("batch.size", 16384);

		// Reduce the no of requests less than 0
		props.put("linger.ms", 1);

		// The buffer.memory controls the total amount of memory available to the
		// producer for buffering.
		props.put("buffer.memory", 33554432);

		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		return props;
	}
}
