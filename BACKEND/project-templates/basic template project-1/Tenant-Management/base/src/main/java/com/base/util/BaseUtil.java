package com.base.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.platform.util.FileUtil;

/**
 * @author Muhil
 *
 */
public class BaseUtil {

	public static File generateFileFromMutipartFile(MultipartFile multipartFile)
			throws IllegalStateException, IOException {
		File file = new File(FileUtil.getTempDirectory() + multipartFile.getOriginalFilename());
		file.mkdirs();
		multipartFile.transferTo(file);
		return file;
	}
	
	public static List<File> generateFileFromMultipartFile(MultipartFile[] multipartFiles)
			throws IllegalStateException, IOException {
		List<File> files = new ArrayList<>();
		for (MultipartFile picture : multipartFiles) {
			files.add(generateFileFromMutipartFile(picture));
		}
		return files;
	}
	
	public static File generateFileFromMutipartFile(MultipartFile multipartFile, File file)
			throws IllegalStateException, IOException {
		multipartFile.transferTo(file);
		return file;
	}
	
}
