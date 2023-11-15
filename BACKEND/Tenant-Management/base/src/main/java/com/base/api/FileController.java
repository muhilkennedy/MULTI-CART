package com.base.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.entity.FileStore;
import com.base.service.FileStoreService;
import com.base.util.BaseUtil;
import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.ConfigurationType;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.messages.StoreType;
import com.platform.user.Permissions;
import com.platform.util.FileUtil;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("admin/file")
@ValidateUserToken
public class FileController {

	@Autowired
	private FileStoreService fileService;

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.MANAGE_PROMOTIONS })
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> getFile(@RequestParam("fileId") Long fileId) throws IOException {
		File file = null;
		try {
			file = fileService.getFileById(fileId);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", file.getName()));
			return ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
		} finally {
			if (file != null) {
				FileUtil.deleteDirectoryOrFile(file);
			}
		}
	}

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Page<FileStore>> getFileDetails(@RequestParam("pageNumber") int pageNumber,
			@RequestParam("pageSize") int pageSize) throws IOException {
		GenericResponse<Page<FileStore>> response = new GenericResponse<>();
		String sortBy = "fileName";
		String sortDir = "ASC";
		Page<FileStore> page = fileService
				.getAllFilesStored(BaseUtil.getPageable(sortBy, sortDir, pageNumber, pageSize));
		if (page.getContent() != null && page.getContent().size() > 0) {
			return response.setStatus(Response.Status.OK).setData(page).build();
		}
		return response.setStatus(Response.Status.NO_CONTENT).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(value = "/gcp/configurationkeys", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<ConfigurationType> getGcpStorageConfigurationProperties() {
		GenericResponse<ConfigurationType> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(ConfigurationType.STORAGE)
				.setDataList(StoreType.GCP_CONSTANTS.stream().toList()).build();
	}

}
