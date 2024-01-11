package com.supplier.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;
import com.supplier.entity.Supplier;
import com.supplier.messages.SupplierRequest;
import com.supplier.service.SupplierService;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("supplier")
@ValidateUserToken
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Supplier> addSupplier(@RequestBody SupplierRequest request) {
		GenericResponse<Supplier> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(supplierService.createSupplier(request)).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Supplier> getAllSuppliers() {
		GenericResponse<Supplier> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(supplierService.getAllSuppliers()).build();
	}

}
