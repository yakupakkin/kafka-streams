package com.example.demo;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
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
		
		// In this case we can rely on the default serializers for keys and values because their data
	    // types did not change, i.e. we only need to provide the name of the output topic.
		messageStream.mapValues((ValueMapper<String, String>) String::toLowerCase).to("output-topic");

		// Variant 2: using `map`, modify value only (equivalent to variant 1)
		messageStream.map((key, value) -> new KeyValue<>(key, value.toUpperCase()))
				.peek((key, value) -> System.out.println("Variant 2 - key " + key + " value " + value))
				.to("output-topic");
		// Variant 3: using `map`, modify both key and value
	    //
	    // Note: Whether, in general, you should follow this artificial example and store the original
	    //       value in the key field is debatable and depends on your use case.  If in doubt, don't
	    //       do it.
		messageStream.map((key, value) -> KeyValue.pair(value, value.substring(4)))
				.peek((key, value) -> System.out.println("Variant 3 - key " + key + " value " + value))
				.to("output-topic");
	}

	@Autowired
	void buildPipelineForInfo(StreamsBuilder streamsBuilder) {
		KStream<String, String> infoStream = streamsBuilder.stream("info-topic");

		final Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
		infoStream.mapValues((ValueMapper<String, String>) String::toLowerCase)
				.flatMapValues(value -> Arrays.asList(pattern.split(value.toLowerCase())))
				.peek((key, value) -> System.out.println("Outgoing record - key " + key + " value " + value))
				.groupBy((key, word) -> word).count(Materialized.as("counts")).toStream()
				.peek((key, value) -> System.out.println("Outgoing record - key " + key + " value " + value))
				.to("custom-topic", Produced.with(STRING_SERDE, Serdes.Long()));
	}

}