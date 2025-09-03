package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
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
		CellStyle headerCellStyle = this.setHeaderStyle(workbook, headerRow);
		//headerRow.setRowStyle(new XSSFCellStyle(null));
		List<String> headers = List.of("Id", "Name", "Category", "Description", "Price", "Quantity");
		this.setProductsHeader(0, headerRow, headers, headerCellStyle);
        
        // Populate the data rows
        int rowNum = 1;
        rowNum = this.setProductsRows(rowNum, sheet, products);
        
        // Write the output to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
		return outputStream.toByteArray();
	}
	
	private CellStyle setHeaderStyle(Workbook workbook, Row headerRow) {
		// Create a CellStyle for header
		CellStyle headerStyle = workbook.createCellStyle();
		
		// Set background color
		headerStyle.setFillForegroundColor(IndexedColors.OLIVE_GREEN.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		// Create font for header
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		headerStyle.setFont(headerFont);
		return headerStyle;
	}

	private void setProductsHeader(int rowNumber, Row headerRow, List<String> headers, CellStyle headerCellStyle) {
		int cellNum = 0;
		for (String header : headers) {
			Cell cell = headerRow.createCell(cellNum++);
			cell.setCellValue(header);
			cell.setCellStyle(headerCellStyle);
        }
	}
	
	private int setProductsRows(int rowNumber, Sheet sheet, List<Product> products) {
		for (Product product  : products) {
            Row row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getCategory());
            row.createCell(3).setCellValue(product.getDescription());
            row.createCell(4).setCellValue("\u20B9"+ product.getPrice().doubleValue());	// UNICODE for ₹
            row.createCell(5).setCellValue(product.getQuantity());
        }
		return rowNumber;
	}
}
