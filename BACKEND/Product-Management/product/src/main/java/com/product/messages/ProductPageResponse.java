package com.product.messages;

import java.util.List;

import org.springframework.data.domain.Pageable;

/**
 * @author Muhil
 * @param <T>
 */
public class ProductPageResponse<T> {

	List<T> content;
	Pageable pageable;
	long totalElements;
	long totalPages;
	int numberOfElements;
	int number;

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
	
	public int getSize() {
		return content != null? content.size() : 0;
	}

}
