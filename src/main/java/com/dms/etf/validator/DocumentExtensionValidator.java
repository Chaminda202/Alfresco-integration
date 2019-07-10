package com.dms.etf.validator;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dms.etf.entity.DocumentExtension;
import com.dms.etf.repository.DocumentExtensionRepository;
import com.dms.etf.util.CommonMessage;

@Component
public class DocumentExtensionValidator {
	private Logger logger;
	private DocumentExtensionRepository documentExtensionRepository;

	public DocumentExtensionValidator(DocumentExtensionRepository documentExtensionRepository) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.documentExtensionRepository = documentExtensionRepository;
	}

	public boolean validateDocumentExtension(MultipartFile file) {
		String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		logger.info("Start Validate document extension {}", extension);
		List<DocumentExtension> docTypeList = documentExtensionRepository.findAll();
		List<String> docList = docTypeList.stream().map(t -> t.getExtention().toUpperCase()).collect(Collectors.toList());
		logger.info("End Validate document extension {} -> {}", extension, CommonMessage.STATUS_SUCCESS);
		return docList.contains(extension.toUpperCase());
	}
}
