package com.kafka.streams.listeners;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;


@Configuration
public class KafkaConsumers {

	public static final String INFO_TOPIC = "info-topic";
	public static final String INPUT_TOPIC = "input-topic";
	public static final String OUTPUT_TOPIC = "output-topic";
	public static final String CUSTOM_TOPIC = "custom-topic";

	@KafkaListener(topics = OUTPUT_TOPIC)
	public void consumeMessage(String message) {
		System.out.println("Kafka streams output message: " + message);
	}

	@KafkaListener(topics = INPUT_TOPIC)
	public void consumedMessage(String message) {
		System.out.println("Consumed message: " + message);
	}

	@KafkaListener(topics = INFO_TOPIC)
	public void infoMessage(String message) {
		System.out.println("Consumed info message: " + message);
	}

	@KafkaListener(topics = CUSTOM_TOPIC)
	public void customMessage(String message) {
		System.out.println("Word counter: " + message);
	}

	@KafkaListener(topics = { "greeting-topic" })
	public void optionalListener(ConsumerRecord<?, ?> record) {

		Optional<?> messages = Optional.ofNullable(record.value());

		if (messages.isPresent()) {
			System.out.println("This is the optional-topic send message: " + messages.get());
		}

	}
}
