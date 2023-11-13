package com.base.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	public static File generateFileFromMutipartFile(MultipartFile multipartFile, String targetName,
			String targetExtension) throws IllegalStateException, IOException {
		File file = new File(FileUtil.getTempDirectory() + targetName + targetExtension);
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
	
	public static Pageable getPageable(int pageNumber, int pageSize) {
		return getPageable(null, null, pageNumber, pageSize);
	}

	public static Pageable getPageable(String sortBy, int pageNumber, int pageSize) {
		return getPageable(sortBy, null, pageNumber, pageSize);
	}

	public static Pageable getPageable(String sortBy, String sortOrder, int pageNumber, int pageSize) {
		String sortByField = StringUtils.isAllBlank(sortBy) ? "timUpdated" : sortBy;
		String sortDir = StringUtils.isAllBlank(sortOrder) ? "ASC" : sortOrder;
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortByField).ascending()
				: Sort.by(sortByField).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		return pageable;
	}

}
