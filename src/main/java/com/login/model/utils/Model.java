package com.login.model.utils;

import java.util.HashMap;
import java.util.Map;

public class Model {

	Map<String, Object> data = new HashMap<>();

	public void addAttribute(String key, Object value) {
		data.put(key, value);
	}

	public Map<String, Object> asMap() {
		return this.data;
	}

	public Map<String, Object> asString() {
		return this.data;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}