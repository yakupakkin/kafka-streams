package com.kafka.streams;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String topic, String message) {
		kafkaTemplate.send(new ProducerRecord<String, String>(topic, message));
	}

	public void destroy() {
		kafkaTemplate.destroy();
	}
}
