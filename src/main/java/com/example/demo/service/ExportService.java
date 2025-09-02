package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repo.ProductRepo;

@Service
public class ExportService {

	@Autowired
	ProductRepo productRepo;

	public byte[] generateExcel(List<Product> products) throws IOException {
		// Create a new workbook and sheet
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("products");
		
		// Create header row
		Row headerRow = sheet.createRow(0);
		//headerRow.setRowStyle(null);
		headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Category");
        headerRow.createCell(3).setCellValue("Description");
        headerRow.createCell(4).setCellValue("Price");
        headerRow.createCell(5).setCellValue("Quantity");
        
        // Populate the data rows
        int rowNum = 1;
        for (Product product  : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getCategory());
            row.createCell(3).setCellValue(product.getDescription());
            row.createCell(4).setCellValue(product.getPrice().doubleValue());
            row.createCell(5).setCellValue(product.getQuantity());
        }
        
        // Write the output to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
		return outputStream.toByteArray();
	}
}
