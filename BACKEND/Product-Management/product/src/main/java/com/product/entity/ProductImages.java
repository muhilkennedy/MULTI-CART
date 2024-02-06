package com.product.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Muhil
 */
public class ProductImages implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> images;

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

}
