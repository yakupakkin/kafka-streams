package com.example.demo;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaProcessor {

    private static final Serde<String> STRING_SERDE = Serdes.String();

	@Autowired
	void buildPipeline(StreamsBuilder streamsBuilder) {
		KStream<String, String> messageStream = streamsBuilder.stream("input-topic",
				Consumed.with(STRING_SERDE, STRING_SERDE));

		messageStream.mapValues((ValueMapper<String, String>) String::toLowerCase).to("output-topic");
	}

}