package com.dms.etf.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class List {
	@JsonProperty("pagination")
	private Pagination pagination;
	@JsonProperty("entries")
	private java.util.List<Entry_> entries;

	@JsonProperty("pagination")
	public Pagination getPagination() {
		return pagination;
	}

	@JsonProperty("pagination")
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	@JsonProperty("entries")
	public java.util.List<Entry_> getEntries() {
		return entries;
	}

	@JsonProperty("entries")
	public void setEntries(java.util.List<Entry_> entries) {
		this.entries = entries;
	}
}