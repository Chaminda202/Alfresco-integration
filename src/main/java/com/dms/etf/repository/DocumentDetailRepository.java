package com.dms.etf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dms.etf.entity.DocumentDetail;

public interface DocumentDetailRepository extends JpaRepository<DocumentDetail, Long>{
	Optional<DocumentDetail> findByDocumentReferenceAndDocumentTypeType(String docReference, String docType);
	@Query("SELECT count(*) FROM DocumentDetail WHERE documentType.type = ?1")
	long countByDocumentType(String documentType);
	Optional<DocumentDetail> findByDocumentReference(String docReference);
}
