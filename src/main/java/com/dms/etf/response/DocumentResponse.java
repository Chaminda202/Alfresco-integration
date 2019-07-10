package com.dms.etf.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentResponse {

	@JsonProperty("entry")
	private Entry entry;

	@JsonProperty("entry")
	public Entry getEntry() {
		return entry;
	}

	@JsonProperty("entry")
	public void setEntry(Entry entry) {
		this.entry = entry;
	}
}