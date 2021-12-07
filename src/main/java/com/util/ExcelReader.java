package com.util;

import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	
	public ExcelReader(String path, String sheet) {
		try {
			workbook = new XSSFWorkbook(path);
			this.sheet = workbook.getSheet(sheet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getRowCount(String sheet) {
		int index = workbook.getSheetIndex(sheet);
		if(index == -1) {
			return 0;
		}else {
			this.sheet = workbook.getSheetAt(index);
			int number = this.sheet.getPhysicalNumberOfRows();
			return number;
		}
	}
	
	public int getColCount(String sheet) {
		int index = workbook.getSheetIndex(sheet);
		if(index == -1) {
			return 0;
		}else {
			this.sheet = workbook.getSheetAt(index);
			int number = this.sheet.getRow(0).getLastCellNum();
			return number;
		}
	}
	
	public String getStringCellData(int row, int col) {
		DataFormatter formatter = new DataFormatter();
		int index = workbook.getSheetIndex(sheet);
		if(index == -1) {
			return "";
		}else {
			String cellData = formatter.formatCellValue(sheet.getRow(row).getCell(col));
			return cellData;
		}
	}
	
	public Object[][] getTestData(String sheet){
		int row = getRowCount(sheet);
		int col = getColCount(sheet);
		
		Object[][] data = new Object[row-1][col];
		for(int i=1; i<row; i++) {
			for(int j=0; j<col; j++) {
				String value = getStringCellData(i, j);
				data[i-1][j] = value;
			}
		}
		return data;
	}
}
