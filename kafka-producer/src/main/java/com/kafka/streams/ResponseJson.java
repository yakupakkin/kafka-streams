package com.kafka.streams;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;

@Builder
@JsonPropertyOrder({ "id", "message" })
public class ResponseJson {
	@Builder.Default
	@JsonProperty("id")
	private Integer id;
	@Builder.Default
	@JsonProperty("message")
	private String message;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseJson(Integer id, String message) {
		this.id = id;
		this.message = message;
	}

	public ResponseJson() {

	}
}

