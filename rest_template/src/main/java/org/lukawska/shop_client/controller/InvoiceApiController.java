package org.lukawska.shop_client.controller;

import lombok.RequiredArgsConstructor;
import org.lukawska.shop_client.service.InvoiceApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceApiController {

	private final InvoiceApiService invoiceApiService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		String response = invoiceApiService.uploadFile(file);
		return ResponseEntity.ok(response);
	}
}
