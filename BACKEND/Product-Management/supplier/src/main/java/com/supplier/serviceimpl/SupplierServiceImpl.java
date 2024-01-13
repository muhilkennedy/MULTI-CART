package com.supplier.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.supplier.dao.SupplierDao;
import com.supplier.entity.Supplier;
import com.supplier.messages.SupplierRequest;
import com.supplier.service.SupplierService;

/**
 * @author Muhil
 */
@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierDao supplierDao;

	@Override
	public BaseEntity findById(Long rootId) {
		return supplierDao.findById(rootId);
	}

	@Override
	public Supplier createSupplier(SupplierRequest request) {
		Supplier supplier = new Supplier();
		supplier.setName(request.getName());
		supplier.setEmailid(request.getEmailId());
		supplier.setDescription(request.getDescription());
		supplier.setContact(request.getContact());
		supplier.setSecondarycontact(request.getSecondarycontact());
		//TODO: notify supplier email
		return (Supplier) supplierDao.save(supplier);
	}
	
	@Override
	public List<Supplier> getAllSuppliers(){
		return supplierDao.findAll();
	}

}
