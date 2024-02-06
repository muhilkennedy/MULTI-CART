package com.supplier.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.service.HibernateSearchService;
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
	
	@Autowired
	private HibernateSearchService searchService;
	
	@Autowired
	private SupplierEmailService emailService;

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
		supplierDao.save(supplier);
		emailService.sendSupplierOnboardingMail(supplier);
		return supplier;
	}
	
	@Override
	public List<Supplier> getAllSuppliers(){
		return supplierDao.findAll();
	}

	@Override
	public List getMatchingSuppliers(String name) {
		return searchService.fuzzySearch(Supplier.class, name, Integer.MAX_VALUE, "name");
	}

}
