package com.product.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.base.entity.BaseEntity;
import com.base.service.HibernateSearchService;
import com.base.util.Log;
import com.platform.service.StorageService;
import com.platform.util.ImageUtil;
import com.product.dao.ProductDao;
import com.product.dao.ProductInventoryDao;
import com.product.entity.Category;
import com.product.entity.Product;
import com.product.entity.ProductInfo;
import com.product.entity.ProductInventory;
import com.product.messages.ProductInfoRequest;
import com.product.messages.ProductInventoryRequest;
import com.product.messages.ProductPageResponse;
import com.product.messages.ProductRequest;
import com.product.messages.ProductResponse;
import com.product.service.CategoryService;
import com.product.service.ProductService;
import com.product.util.ProductUtil;
import com.supplier.entity.Supplier;
import com.supplier.service.SupplierService;

import jakarta.ws.rs.NotFoundException;

/**
 * @author muhil
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private HibernateSearchService searchService;

	@Autowired
	private ProductInventoryDao inventoryDao;

	@Override
	public BaseEntity findById(Long rootId) {
		return productDao.findById(rootId);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productDao.findAll(pageable);
	}
	
	@Override
	public Page<Product> findAllActive(Pageable pageable) {
		return productDao.findAllActive(pageable);
	}
	
	@Override
	public ProductPageResponse<ProductResponse> findAllAdvanced(Pageable pageable, boolean includeInactive,
			Long categoryId, Long supplierId) {
		ProductPageResponse<ProductResponse> resp = new ProductPageResponse<ProductResponse>();
		Page<Product> pages = productDao.findAll(pageable, includeInactive, categoryId, supplierId);
		if (pages.getSize() > 0) {
			resp.setPageable(pageable);
			resp.setTotalElements(pages.getTotalElements());
			resp.setTotalPages(pages.getTotalPages());
			resp.setNumberOfElements(pages.getNumberOfElements());
			resp.setContent(pages.getContent().stream().map(product -> generateProductResponse(product)).toList());
		}
		return resp;
	}
	
	private ProductResponse generateProductResponse(Product product) {
		ProductResponse prodResponse = new ProductResponse(product);
		prodResponse.setCategory((Category) categoryService.findById(product.getCategoryid()));
		prodResponse.setSupplierid((Supplier) supplierService.findById(product.getSupplierid()));
		prodResponse.getInfos().stream().forEach(info -> {
			info.setInventory(getAllProductInventory(info.getRootid()));
		});
		return prodResponse;
	}

	@Override
	public Product createProduct(ProductRequest request) {
		Product prod = new Product();
		prod.setName(request.getName());
		if (request.getCategoryId() != null) {
			prod.setCategoryid(request.getCategoryId());
		}
		prod.setMeasurement(request.getMeasurement());
		prod.setDescription(request.getDescription());
		if (request.getSupplierId() != null) {
			Supplier supplier = (Supplier) supplierService.findById(request.getSupplierId());
			Assert.notNull(supplier, "Invalid supplier");
			prod.setSupplierid(supplier.getRootid());
		}
		return (Product) productDao.save(prod);
	}

	@Override
	public ProductInfo createProductInfo(ProductInfoRequest request) {
		Product prod = (Product) findById(request.getProductId());
		if (prod == null) {
			throw new NotFoundException("Product Not Found");
		}
		ProductInfo info = new ProductInfo();
		info.setDiscount(request.getDiscount());
		info.setMrp(request.getMrp());
		info.setPrice(request.getPrice());
		info.setSize(request.getSize());
		info.setProduct(prod);
		productDao.saveProductInfo(info);
		return info;
	}

	@Override
	public ProductInventory addOrUpdateProductInventory(ProductInventoryRequest request) {
		ProductInfo prodInfo = productDao.findProductInfoById(request.getProductInfoId());
		if (prodInfo == null) {
			throw new NotFoundException("Product information Not Found");
		}
		ProductInventory inventory = inventoryDao.findByBarCode(request.getBarcode());
		if (inventory == null) {
			inventory = new ProductInventory();
		}
		inventory.setBarcode(request.getBarcode());
		inventory.setExpiry(request.getExpiry());
		inventory.setAvailablequantity(request.getAvailableQuantity());
		inventory.setMinimumstocklevel(request.getMinimumQuantity());
		inventory.setAutoPurchase(request.isAutoPurchase());
		inventory.setProductinfo(prodInfo);
		inventoryDao.save(inventory);
		return inventory;
	}

	@Override
	public List<ProductInventory> getAllProductInventory(long infoId) {
		return inventoryDao.getAllProductInventory(infoId);
	}

	@Override
	public List<ProductInventory> getActiveProductInventory(long infoId) {
		return inventoryDao.getActiveProductInventory(infoId);
	}
	
	@Override
	public List searchProductByName(String text) {
		return searchService.fuzzySearch(Product.class, text, Integer.MAX_VALUE, Product.KEY_NAME);
	}
	
	@Override
	public Product updateProductImage(Long productId, Long infoId, File image) throws IOException {
		Product product = (Product) findById(productId);
		ProductInfo info = product.getInfos().stream().filter(pInfo -> pInfo.getRootid() == infoId).findAny().get();
		String fileUrl = StorageService.getStorage().saveFile(ImageUtil.getPNGThumbnailImage(image, true),
				ProductUtil.PRODUCT_IMAGE_DIR + File.separator + productId + File.separator + infoId);
		Log.product.debug("Picture url : {}", fileUrl);
		info.setImage(fileUrl);
		productDao.saveProductInfo(info);
		return product;
	}

	@Override
	public ProductResponse findProductByBarcode(String barcode) {
		Product product = productDao.findProductByBarcode(barcode);
		return generateProductResponse(product);
	}
	
	@Override
	public Product toggleProductState(Long productId) {
		Product product = (Product) findById(productId);
		product.setActive(!product.isActive());
		return (Product) productDao.save(product);
	}

}
