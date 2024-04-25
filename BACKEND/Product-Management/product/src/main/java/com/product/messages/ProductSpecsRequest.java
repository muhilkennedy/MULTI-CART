package com.product.messages;

import java.io.Serializable;
import java.util.Map;

import com.google.gson.Gson;
import com.product.entity.ProductSpecs;

/**
 * @author Muhil
 */
public class ProductSpecsRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String model;
	private String weight;
	private String dimensions;
	private String colour;
	private String description;
	private String origin;
	private String manufacturer;
	private Map<String, String> details;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
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

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	public ProductSpecs getProductSpecificationEntity() {
		ProductSpecs specs = new ProductSpecs();
		specs.setColour(colour);
		specs.setDescription(description);
		specs.setDimensions(dimensions);
		specs.setManufacturer(manufacturer);
		specs.setModel(model);
		specs.setOrigin(origin);
		specs.setWeight(weight);
		Gson gson = new Gson();
		specs.setDetails(gson.toJson(details));
		return specs;
	}

}
