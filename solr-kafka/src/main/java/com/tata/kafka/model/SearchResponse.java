package com.tata.kafka.model;

import java.util.List;

public class SearchResponse {
	
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public long getNoOfFound() {
		return noOfFound;
	}
	public void setNoOfFound(long noOfFound) {
		this.noOfFound = noOfFound;
	}
	List<Product> products;
	long noOfFound;
	

}
