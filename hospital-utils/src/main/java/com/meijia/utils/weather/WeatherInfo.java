package com.meijia.utils.weather;

import java.util.List;

public class WeatherInfo {
	private String error;
	private String status;
	private String date;
	private List<WeatherResult> results;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<WeatherResult> getResults() {
		return results;
	}

	public void setResults(List<WeatherResult> results) {
		this.results = results;
	}
}
