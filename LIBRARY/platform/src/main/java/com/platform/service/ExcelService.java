package com.platform.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Muhil impl for XLSX format.
 *
 */
//TODO: create Excel factory and have 2 diff impl for XSSF and HSSF
public class ExcelService {

	private File _sourceFile;
	private Workbook _workBook;
	private Map<String, Sheet> _sheetMap = new HashMap<String, Sheet>();

	public ExcelService(File file) throws IOException {
		FileInputStream fileStream = new FileInputStream(file);
		_workBook = new XSSFWorkbook(fileStream);
		_sourceFile = file;
		loadSheets();
	}

	public File get_sourceFile() {
		return _sourceFile;
	}

	public Workbook get_workBook() {
		return _workBook;
	}

	public Map<String, Sheet> get_sheetMap() {
		return _sheetMap;
	}

	public Sheet getSheetByName(String name) {
		return _sheetMap.get(name);
	}

	public List<Sheet> getAllSheets() {
		List<Sheet> sheets = new ArrayList<Sheet>();
		for (int index = 0; index < getTotalSheetsCount(); index++) {
			sheets.add(_workBook.getSheetAt(index));
		}
		return sheets;
	}

	public void loadSheets() {
		for (int index = 0; index < getTotalSheetsCount(); index++) {
			Sheet currSheet = _workBook.getSheetAt(index);
			_sheetMap.put(currSheet.getSheetName(), currSheet);
		}
	}

	public int getTotalSheetsCount() {
		return _workBook.getNumberOfSheets();
	}

}
