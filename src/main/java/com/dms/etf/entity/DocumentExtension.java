package com.dms.etf.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "document_extension")
public class DocumentExtension implements Serializable{
	private static final long serialVersionUID = -2525860844599874153L;
	@Id
    private String extention;
    private String description;
       
	public DocumentExtension() {
		super();
	}
	
	public String getExtention() {
		return extention;
	}
	public void setExtention(String extention) {
		this.extention = extention;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
}
