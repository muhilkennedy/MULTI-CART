package com.platform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Muhil
 * Excel manipulation using apache poi
 */
public class ExcelUtil {

	public static Workbook getExcelXSSWorkbook(File file) throws IOException {
		FileInputStream fileStream = new FileInputStream(file);
		return new XSSFWorkbook(fileStream);
	}
	
	
}
