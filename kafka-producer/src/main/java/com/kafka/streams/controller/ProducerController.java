package com.kafka.streams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kafka.streams.RequestMessage;
import com.kafka.streams.ResponseJson;
import com.kafka.streams.KafkaProducer;


@RestController
@RequestMapping("/api")
public class ProducerController {

	private final KafkaProducer kafkaProducer;

	private RestTemplate restTemplate;

	@Autowired
	public ProducerController(KafkaProducer kafkaProducer, RestTemplate restTemplate) {
		this.kafkaProducer = kafkaProducer;
		this.restTemplate = restTemplate;
	}

	@PostMapping("/publish")
	public void messageToTopic(@RequestParam("message") String message) {

		for (int i = 0; i < 5; i++)
			kafkaProducer.send("input-topic", Integer.toString(i), message);
		kafkaProducer.destroy();
	}

	@GetMapping("/info")
	public ResponseEntity<ResponseJson> messageToInfoTopic() {
		String baseUrl = "http://localhost:8080/microservice1";
		ResponseEntity<ResponseJson> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null,
				ResponseJson.class);
		kafkaProducer.send("info-topic", Integer.toString(response.getBody().getId()), response.getBody().getMessage());
		kafkaProducer.destroy();

		return response;
	}

	@PostMapping("/greeting")
	public ResponseEntity<ResponseJson> messageToGreetingTopic(@RequestBody RequestMessage requestMessage) {

		String baseUrl = "http://localhost:8083/microservice4";
		ResponseEntity<ResponseJson> response = restTemplate.postForEntity(baseUrl, requestMessage, ResponseJson.class);
		kafkaProducer.send("greeting-topic",Integer.toString(response.getBody().getId()), response.getBody().getMessage());
		kafkaProducer.destroy();
		return response;
	}
}
