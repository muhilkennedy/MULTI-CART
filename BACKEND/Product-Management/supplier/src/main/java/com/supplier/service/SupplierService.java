package com.supplier.service;

import java.util.List;

import com.base.service.BaseService;
import com.supplier.entity.Supplier;
import com.supplier.messages.SupplierRequest;

/**
 * @author Muhil 
 */
public interface SupplierService extends BaseService {
	
	Supplier createSupplier(SupplierRequest request);

	List<Supplier> getAllSuppliers();
	
	List<Supplier> getMatchingSuppliers(String name);

}
