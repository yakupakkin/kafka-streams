package com.kafka.streams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.streams.KafkaProducer;

@RestController
public class ProducerController {
	@Autowired
	KafkaProducer kafkaProducer;

	@PostMapping("/publish")
	public void messageToTopic(@RequestParam("message") String message) {

		for (int i = 0; i < 5; i++)
			kafkaProducer.send("input-topic", Integer.toString(i) + " - " + message);
		kafkaProducer.destroy();
	}

	@PostMapping("/info")
	public void messageToInfoTopic(@RequestParam("message") String infoMessage) {

		kafkaProducer.send("info-topic", infoMessage);
		kafkaProducer.send("optional-topic", infoMessage);
		kafkaProducer.destroy();
	}

}
