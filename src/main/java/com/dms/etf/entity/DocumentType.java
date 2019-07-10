package com.dms.etf.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "document_type")
public class DocumentType implements Serializable{
	private static final long serialVersionUID = -8464802555489012305L;
	@Id
    private String type;
    private String description;
       
	public DocumentType() {
		super();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
}
