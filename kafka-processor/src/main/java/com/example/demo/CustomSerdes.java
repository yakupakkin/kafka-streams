package com.example.demo;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public final class CustomSerdes {
	public CustomSerdes() {
	}

	public static Serde<RequestMessage> MessageSend() {
		JsonSerializer<RequestMessage> serializer = new JsonSerializer<>();
		JsonDeserializer<RequestMessage> deserializer = new JsonDeserializer<>(RequestMessage.class);
		return Serdes.serdeFrom(serializer, deserializer);
	}

}