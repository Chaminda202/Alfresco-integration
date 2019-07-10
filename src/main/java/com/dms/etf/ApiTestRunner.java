package com.dms.etf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.dms.etf.service.DocumentService;

//@Component
public class ApiTestRunner implements CommandLineRunner {
	@Autowired
	private DocumentService alfrescoService;
	@Override
	public void run(String... args) throws Exception {
		alfrescoService.getAuthTicket();
	}
}
