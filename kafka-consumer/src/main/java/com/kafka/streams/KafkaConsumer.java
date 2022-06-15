package com.kafka.streams;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;


@Configuration
public class KafkaConsumer {

	public static final String INFO_TOPIC = "info-topic";
	public static final String INPUT_TOPIC = "input-topic";
	public static final String OUTPUT_TOPIC = "output-topic";
	public static final String CUSTOM_TOPIC = "custom-topic";
	public static final String GROUP_ID = "group_id";

	@KafkaListener(topics = OUTPUT_TOPIC, groupId = GROUP_ID)
	public void consumeMessage(String message) {
		System.out.println("Kafka streams output message: " + message);
	}

	@KafkaListener(topics = INPUT_TOPIC, groupId = GROUP_ID)
	public void consumedMessage(String message) {
		System.out.println("Consumed message: " + message);
	}

	@KafkaListener(topics = INFO_TOPIC, groupId = GROUP_ID)
	public void infoMessage(String message) {
		System.out.println("Consumed info message: " + message);
	}

	@KafkaListener(topics = CUSTOM_TOPIC, groupId = GROUP_ID)
	public void customMessage(String message) {
		System.out.println(message);
	}
}
