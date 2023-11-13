package com.platform.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.xmlbeans.XmlException;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.convert.out.FopReflective;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;

public class DocxUtil {
	
	/**
	 * @param docFile
	 * @param pdfFile
	 * @throws Exception - Docx4jException
	 */
	public static void convertDocToPDF(File docFile, File pdfFile) throws Exception {
		WordprocessingMLPackage word = WordprocessingMLPackage.load(docFile);
		OutputStream os = new FileOutputStream(pdfFile);
		FOSettings fset = Docx4J.createFOSettings();
		Path tempPath = Files.createTempDirectory(null);
		fset.setImageDirPath(tempPath.toString());
		fset.setOpcPackage(word);
		fset.setApacheFopMime(FOSettings.MIME_PDF);
		FopReflective.invokeFORendererApacheFOP(fset);
		Docx4J.toFO(fset, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
		// flush Image Directory
		FileUtil.deleteDirectoryOrFile(tempPath.toFile());
	}

	public static File convertDocToPDF(File docFile) throws Exception {
		return convertDocToPDF(docFile, false);
	}
	
	public static File convertDocToPDF(File docFile, boolean accelerate) throws Exception {
		File pdfFile = File.createTempFile("Temp", ".pdf");
		WordprocessingMLPackage word = WordprocessingMLPackage.load(docFile);
		OutputStream os = new FileOutputStream(pdfFile);
		FOSettings fset = Docx4J.createFOSettings();
		Path tempPath = Files.createTempDirectory(null);
		fset.setImageDirPath(tempPath.toString());
		fset.setOpcPackage(word);
		fset.setApacheFopMime(FOSettings.MIME_PDF);
		FopReflective.invokeFORendererApacheFOP(fset);
		Docx4J.toFO(fset, os, accelerate ? Docx4J.FLAG_EXPORT_PREFER_NONXSL : Docx4J.FLAG_EXPORT_PREFER_XSL);
		// flush Image Directory
		FileUtil.deleteDirectoryOrFile(tempPath.toFile());
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
	
	public static void split(String FilePath, long splitlen) {
	    long leninfile = 0, leng = 0;
	    int count = 1, data;
	    try {
	        File filename = new File(FilePath);
	        //RandomAccessFile infile = new RandomAccessFile(filename, "r");
	        InputStream infile = new BufferedInputStream(new FileInputStream(filename));
	        data = infile.read();
	        while (data != -1) {
	            filename = new File(FilePath + count + ".docx");
	            //RandomAccessFile outfile = new RandomAccessFile(filename, "rw");
	            OutputStream outfile = new BufferedOutputStream(new FileOutputStream(filename));
	            while (data != -1 && leng < splitlen) {
	                outfile.write(data);
	                leng++;
	                data = infile.read();
	                
	            }
	            leninfile += leng;
	            leng = 0;
	            outfile.close();
	            count++;
	            convertDocToPDF(filename);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void join(String FilePath) {
	    long leninfile = 0, leng = 0;
	    int count = 1, data = 0;
	    try {
	        File filename = new File(FilePath);
	        //RandomAccessFile outfile = new RandomAccessFile(filename,"rw");

	        OutputStream outfile = new BufferedOutputStream(new FileOutputStream(filename));
	        while (true) {
	            filename = new File(FilePath + count + ".docx");
	            if (filename.exists()) {
	                //RandomAccessFile infile = new RandomAccessFile(filename,"r");
	                InputStream infile = new BufferedInputStream(new FileInputStream(filename));
	                data = infile.read();
	                while (data != -1) {
	                    outfile.write(data);
	                    data = infile.read();
	                }
	                leng++;
	                infile.close();
	                count++;
	            } else {
	                break;
	            }
	        }
	        outfile.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void copyFile(String filePath) {
		try {
		    FileInputStream in = new FileInputStream(filePath);
		    XWPFDocument oldDoc = new XWPFDocument(in);
		    XWPFDocument newDoc = new XWPFDocument();
		    
		    
		    XWPFDocument newDoc1 = new XWPFDocument();
		    List<IBodyElement> ele = oldDoc.getBodyElements();
		    int eleCount = 0;
		    for(IBodyElement el : ele) {
		    	newDoc1.getBodyElements().add(el);
		    }
		    FileOutputStream out1 = new FileOutputStream(new File(filePath + "ele" + ".docx"));
	        newDoc.write(out1);
	        out1.close();
		    
		    // Copy styles from template to new doc
		    XWPFStyles newXStyles = newDoc.createStyles();
		    newXStyles.setStyles(oldDoc.getStyle());

		    
		    List<XWPFParagraph> oldDocParagraphs = oldDoc.getParagraphs();
		    int counter = 0, count =0;
		    for (XWPFParagraph oldPar : oldDocParagraphs) {
		    	counter ++;
		        // Create new paragraph and set it style of old paragraph
		        XWPFParagraph newPar = newDoc.createParagraph();
		        newPar.setStyle(oldPar.getStyle());
		        // Loop in runs of old paragraphs.
		        for (XWPFRun oldRun : oldPar.getRuns()) { // Paragrafın sitillere göre parçalanmış stringleri
		            // Create a run for the new paragraph
		            XWPFRun newParRun = newPar.createRun();
		            // Set old run's text of old paragraph to the run of new paragraph
		            String runText = oldRun.text();
		            newParRun.setText(runText);
		            // Set old run's style of old paragraph to the run of new paragraph
		            CTRPr oldCTRPr = oldRun.getCTR().getRPr();
		            if (oldCTRPr != null) {
//		                if (oldCTRPr. sizeOfRStyleArray() != 0){
//		                    String carStyle = oldRun.getCTR();
//		                    
//		                }
		            	newParRun.getCTR().setRPr(oldRun.getCTR().getRPr());
		            }
		            // Add the new run to the new paragraph
		            newPar.addRun(newParRun);
		        }
		        if(counter >= 10000) {
		        	// Write to file and close.
			        FileOutputStream out = new FileOutputStream(new File(filePath + count + ".docx"));
			        newDoc.write(out);
			        out.close();
			        count++;
			        counter = 0;
		        }
		        
		    }
		} catch (IOException | XmlException e) {
		    e.printStackTrace();
		}
	}

	
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
