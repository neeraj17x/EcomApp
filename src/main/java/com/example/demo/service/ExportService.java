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

/*
 * @Author: Neeraj Kumar
 */
@Service
public class ExportService {

	@Autowired
	ProductRepo productRepo;
	private Workbook workbook;
	private CellStyle headerStyle;
	private CellStyle unlockedCellStyle;
	private CellStyle lockedCellStyle;

	public byte[] generateExcel(List<Product> products) throws IOException {
		// Create a new workbook and sheet
		workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("products");
		
		// Create header row
		Row headerRow = sheet.createRow(0);
		this.setHeaderStyle();
		//headerRow.setRowStyle(new XSSFCellStyle(null));
		List<String> headers = List.of("Id", "Product Name", "Product Code", "Category", "Description", "Price", "Quantity");
		this.setProductsHeader(0, headerRow, headers);
        
		this.setRowStyle();
        // Populate the data rows
        int rowNum = 1;
        rowNum = this.setProductsRows(rowNum, sheet, products);
        //sheet.autoSizeColumn(rowNum);
        for (int i = 1; i < sheet.getRow(0).getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
        
        // Protect sheet (password optional)
        sheet.protectSheet("admin");

        // Write the output to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
		return outputStream.toByteArray();
	}
	
	private void setHeaderStyle() {
		// Create a CellStyle for header
		headerStyle = workbook.createCellStyle();
		
		// Set background color
		headerStyle.setFillForegroundColor(IndexedColors.OLIVE_GREEN.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		// Create font for header
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		headerStyle.setFont(headerFont);
		headerStyle.setLocked(true);
	}

	private void setProductsHeader(int rowNumber, Row headerRow, List<String> headers) {
		int cellNum = 0;
		for (String header : headers) {
			Cell cell = headerRow.createCell(cellNum++);
			cell.setCellValue(header);
			cell.setCellStyle(headerStyle);
        }
	}
	
	private void setRowStyle() {
		// Create a CellStyle for header
		unlockedCellStyle = workbook.createCellStyle();
		unlockedCellStyle.setLocked(false);
		lockedCellStyle = workbook.createCellStyle();
		lockedCellStyle.setLocked(true);
	}
	
	private int setProductsRows(int rowNumber, Sheet sheet, List<Product> products) {
		for (Product product  : products) {
            Row row = sheet.createRow(rowNumber++);
            Cell idCell = row.createCell(0);
            idCell.setCellStyle(lockedCellStyle);
            idCell.setCellValue(product.getId());
            Cell nameCell = row.createCell(1);
            nameCell.setCellStyle(unlockedCellStyle);
            nameCell.setCellValue(product.getName());
            Cell codeCell = row.createCell(2);
            codeCell.setCellStyle(lockedCellStyle);
            codeCell.setCellValue(product.getCode());
            Cell categoryCell = row.createCell(3);
            categoryCell.setCellValue(product.getCategory());
            Cell descriptionCell = row.createCell(4);
            descriptionCell.setCellValue(product.getDescription());
            //row.createCell(5).setCellValue(product.getPrice().doubleValue());
            Cell priceCell = row.createCell(5);
            priceCell.setCellValue("\u20B9"+ product.getPrice());	// UNICODE for ₹
            Cell quantityCell = row.createCell(6);
            quantityCell.setCellValue(product.getQuantity());
        }
		return rowNumber;
	}
}
