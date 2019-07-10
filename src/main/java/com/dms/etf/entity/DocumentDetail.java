package com.dms.etf.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "document_detail")
public class DocumentDetail implements Serializable{
	private static final long serialVersionUID = -3362639033953576145L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "original_file_name")
    private String originalFileName;
	@Column(name = "user_reference")
	private String userReference;
	@Column(name = "document_reference")
	private String documentReference;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_type")
	private DocumentType documentType;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_extension")
	private DocumentExtension documentExtension;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getUserReference() {
		return userReference;
	}
	public void setUserReference(String userReference) {
		this.userReference = userReference;
	}
	public String getDocumentReference() {
		return documentReference;
	}
	public void setDocumentReference(String documentReference) {
		this.documentReference = documentReference;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public DocumentType getDocumentType() {
		return documentType;
	}
	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}
	public DocumentExtension getDocumentExtension() {
		return documentExtension;
	}
	public void setDocumentExtension(DocumentExtension documentExtension) {
		this.documentExtension = documentExtension;
	}
}
