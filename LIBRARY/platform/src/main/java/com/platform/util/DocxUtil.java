package com.platform.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.convert.out.FopReflective;
import org.docx4j.model.fields.FieldUpdater;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class DocxUtil {
	
	/**
	 * @param docFile
	 * @param pdfFile
	 * @throws Exception - Docx4jException
	 */
	public static void convertDocToPDF(File docFile, File pdfFile) throws Exception {
		Path tempPath = Files.createTempDirectory(null);
		try (OutputStream os = new FileOutputStream(pdfFile)) {
			WordprocessingMLPackage word = WordprocessingMLPackage.load(docFile);
			FieldUpdater updater = new FieldUpdater(word);
			updater.update(true);
			FOSettings fset = Docx4J.createFOSettings();
			fset.setImageDirPath(tempPath.toString());
			fset.setOpcPackage(word);
			fset.setApacheFopMime(FOSettings.MIME_PDF);
			FopReflective.invokeFORendererApacheFOP(fset);
			Docx4J.toFO(fset, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
		} finally {
			// flush Image Directory
			FileUtil.deleteDirectoryOrFile(tempPath.toFile());
		}
	}

	public static File convertDocToPDF(File docFile) throws Exception {
		return convertDocToPDF(docFile, false);
	}
	
	public static File convertDocToPDF(File docFile, boolean accelerate) throws Exception {
		File pdfFile = File.createTempFile("Temp", ".pdf");
		Path tempPath = Files.createTempDirectory(null);
		try (OutputStream os = new FileOutputStream(pdfFile)) {
			WordprocessingMLPackage word = WordprocessingMLPackage.load(docFile);
			FieldUpdater updater = new FieldUpdater(word);
			updater.update(true);// Update DOCPROPERTY
			FOSettings fset = Docx4J.createFOSettings();
			fset.setImageDirPath(tempPath.toString());
			fset.setOpcPackage(word);
			fset.setApacheFopMime(FOSettings.MIME_PDF);
			FopReflective.invokeFORendererApacheFOP(fset);
			Docx4J.toFO(fset, os, accelerate ? Docx4J.FLAG_EXPORT_PREFER_NONXSL : Docx4J.FLAG_EXPORT_PREFER_XSL);
		} finally {
			// flush Image Directory
			FileUtil.deleteDirectoryOrFile(tempPath.toFile());
		}
		return pdfFile;
	}
	
	/*public static File convertDocToPDFUsingPoi(File docFile) throws XWPFConverterException, IOException {
		File pdf = File.createTempFile(docFile.getName(), ".pdf");
		InputStream doc = new FileInputStream(docFile);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XWPFDocument document = new XWPFDocument(doc);
		PdfOptions options = PdfOptions.create();
		PdfConverter.getInstance().convert(document, baos, options);
		Files.write(pdf.toPath(), baos.toByteArray());
		return pdf;
	}

	public static void convertDocToPDFUsingPoi(File docFile, File pdfFile) throws XWPFConverterException, IOException {
		InputStream doc = new FileInputStream(docFile);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XWPFDocument document = new XWPFDocument(doc);
		PdfOptions options = PdfOptions.create();
		PdfConverter.getInstance().convert(document, baos, options);
		Files.write(pdfFile.toPath(), baos.toByteArray());
	}*/
	
	//need to take care of custom fonts (only tamil font is added for now)
			/*Mapper fontMapper = new IdentityPlusMapper();
			String fontFamily = "Arima Madurai";
			ClassPathResource classPathResource = new ClassPathResource("fonts/ArimaMadurai-Regular.ttf");
			URL simsunUrl = classPathResource.getURL();
			PhysicalFonts.addPhysicalFonts(fontFamily, simsunUrl);
			PhysicalFont simsunFont = PhysicalFonts.get(fontFamily);
			fontMapper.put(fontFamily, simsunFont);
			word.setFontMapper(fontMapper);*/

}
