package com.product.entity;

import java.io.Serializable;

/**
 * @author Muhil
 */
public class ProductSpecs implements Serializable {

	private static final long serialVersionUID = 1L;
	private String model;
	private String weight;
	private String dimensions;
	private String colour;
	private String description;
	private String origin;
	private String manufacturer;
	/*attributes selected based on product type
	 * ex.Cloths -> Material,Pattern,Fit Type, Collar Style, Sleeve Type
	 * */
	private String details;

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

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

}
