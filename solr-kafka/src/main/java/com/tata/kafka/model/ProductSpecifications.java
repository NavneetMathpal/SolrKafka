package com.tata.kafka.model;

import java.util.Date;
import java.util.List;

public class ProductSpecifications {
	 
	@Override
	public String toString() {
		return "ProductSpecifications [product_id=" + product_id + ", sellerId=" + sellerId + ", title=" + title
				+ ", manufacturer=" + manufacturer + ", price=" + price + ", metaFields=" + metaFields + ", workFlow="
				+ workFlow + ", isLowQuantity=" + isLowQuantity + ", isSoldOut=" + isSoldOut + ", isBackorder="
				+ isBackorder + ", requiresShipping=" + requiresShipping + ", isVisible=" + isVisible + ", publishedAt="
				+ publishedAt + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	public ProductSpecifications() {
		super();
	}
	private long product_id;
	private long sellerId;
	private String title;;
	private String manufacturer;
	private Price price;
	private List<MetaFields> metaFields;
	private Workflow workFlow;
	private boolean isLowQuantity;
	private boolean isSoldOut;
	private boolean isBackorder;
	private boolean requiresShipping;
	private boolean isVisible;
	private String publishedAt;
	private String createdAt;
	private String updatedAt;
	
	
	public long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	public List<MetaFields> getMetaFields() {
		return metaFields;
	}
	public void setMetaFields(List<MetaFields> metaFields) {
		this.metaFields = metaFields;
	}
	public Workflow getWorkFlow() {
		return workFlow;
	}
	public void setWorkFlow(Workflow workFlow) {
		this.workFlow = workFlow;
	}
	public boolean isLowQuantity() {
		return isLowQuantity;
	}
	public void setLowQuantity(boolean isLowQuantity) {
		this.isLowQuantity = isLowQuantity;
	}
	public boolean isSoldOut() {
		return isSoldOut;
	}
	public void setSoldOut(boolean isSoldOut) {
		this.isSoldOut = isSoldOut;
	}
	public boolean isBackorder() {
		return isBackorder;
	}
	public void setBackorder(boolean isBackorder) {
		this.isBackorder = isBackorder;
	}
	public boolean isRequiresShipping() {
		return requiresShipping;
	}
	public void setRequiresShipping(boolean requiresShipping) {
		this.requiresShipping = requiresShipping;
	}
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public String getPublishedAt() {
		return publishedAt;
	}
	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
