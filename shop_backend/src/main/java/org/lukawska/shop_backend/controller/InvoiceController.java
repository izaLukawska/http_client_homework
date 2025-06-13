package org.lukawska.shop_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

	@PostMapping("/upload")
	public ResponseEntity<String> uploadInvoice(@RequestParam("file") MultipartFile file) throws IOException {
		Path uploadDir = Paths.get("uploads");
		if (Files.notExists(uploadDir)) {
			Files.createDirectories(uploadDir);
		}

		Path filePath = uploadDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		System.out.println("Received file: " + file.getOriginalFilename());
		return ResponseEntity.ok("File " + file.getOriginalFilename() + " uploaded successfully");
	}
}
