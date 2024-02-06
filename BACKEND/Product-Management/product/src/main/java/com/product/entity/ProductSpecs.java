package com.product.entity;

import java.io.Serializable;

/**
 * @author Muhil
 */
public class ProductSpecs implements Serializable {

	private static final long serialVersionUID = 1L;
	private String size;
	private String dimensions;
	private String colour;
	private String description;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
