package com.kafka.streams;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "id", "message" })
public class ResponseJson {
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("message")
	private String message;

}

