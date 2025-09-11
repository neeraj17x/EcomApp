package com.example.demo.util;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.example.demo.model.Product;

public class ExcelHelper {
	
	public static List<Product> excelToProducts(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<Product> products = new ArrayList<Product>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Product product = new Product();
                int col = 0;
                col++;
                product.setName(currentRow.getCell(col).getStringCellValue());
                col++;
                product.setCode(currentRow.getCell(col).getStringCellValue());
                col++;
                product.setBrand(currentRow.getCell(col).getStringCellValue());
                col++;
                product.setCategory(currentRow.getCell(col).getStringCellValue());
                col++;
                product.setDescription(currentRow.getCell(col).getStringCellValue());
                col++;
                // String priceString = currentRow.getCell(5).getStringCellValue();
                //Double priceDouble = Double.parseDouble(priceString.substring(1).trim());
                BigDecimal price = BigDecimal.valueOf(currentRow.getCell(col).getNumericCellValue());
                product.setPrice(price);
                // product.setPrice(currentRow.getCell(col).getNumericCellValue());
                // Double testDouble = currentRow.getCell(col).getNumericCellValue();
                // System.out.println(testDouble);
                col++;
                product.setQuantity((int) currentRow.getCell(col).getNumericCellValue());
                //product.setReleaseDate(currentRow.getCell(7).getLocalDateTimeCellValue().toLocalDate());

                products.add(product);
            }
            workbook.close();
            System.out.println(products);
            return products;
        } catch (Exception e) {
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }
}
