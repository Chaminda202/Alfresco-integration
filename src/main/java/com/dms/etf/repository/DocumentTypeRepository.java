package com.dms.etf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dms.etf.entity.DocumentType;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, String>{
}
