package com.product.messages;

import java.util.List;

/**
 * @author Muhil
 */
public class FlattenedCategory {

	private String name;
	private Long rootId;
	private List<FlattenedCategory> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getRootId() {
		return rootId;
	}

	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}

	public List<FlattenedCategory> getChildren() {
		return children;
	}

	public void setChildren(List<FlattenedCategory> children) {
		this.children = children;
	}

}
