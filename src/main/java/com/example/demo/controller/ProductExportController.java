package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Product;
import com.example.demo.service.ExportService;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("api/export")
public class ProductExportController {
	
	@Autowired
	ProductService productService;
	@Autowired
	private ExportService exportService;

	@GetMapping("excel")
	public ResponseEntity<byte[]> exportProducts() throws IOException {
		
		List<Product> products = productService.getAllProducts("");
		// Get Excel file from Export Service
		byte[] excelFile = exportService.generateExcel(products);
		
		// Set response headers to indicate it's an Excel file
		HttpHeaders headers = new HttpHeaders();
		
		// Create timestamped filename
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "products_list_" + timestamp + ".xlsx";
		headers.add("Content-Disposition", "attachment; filename=" + fileName);
		
		// Return the Excel file as a response
        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
	}
}
