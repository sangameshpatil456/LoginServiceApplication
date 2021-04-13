package com.login.model.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginResponse<T> {
	
	@JsonProperty("data")
	private T data;

	@JsonProperty("is_error")
	private boolean isError;

	public LoginResponse() {

	}

	public LoginResponse(T data) {
		this(data, false);
	}

	public LoginResponse(T data, boolean isError) {
		this.data = data;
		this.isError = isError;
	}

}
