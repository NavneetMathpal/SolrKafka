package com.tata.service.search;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tata.kafka.model.Product;
import com.tata.kafka.model.SearchResponse;

import junit.framework.Assert;

public class ProductSearchTest {
	ProductSearch ProductSearchTest;
	String query = "POSTAGE";
	String searchQuery = "*:*";
	SearchResponse seareachResponse;
	SearchResponse seareachResponse1;
	
	//Search query to test WDF filter
	String searchWdfQueryActual = "T-light";
	String searchWdfQueryModified = "T light";
	
	//Search query to test WDF filter
	String searchSingualrQuery = "light";
	String searchPluralQuery = "lights";

	@Before
	public void setUp() {

		ProductSearchTest = new ProductSearch();

	}

	
	//test for basic search functionality
	@Test
	public void testSearchProduct() {
		seareachResponse = ProductSearch.searchProduct(query);
		Assert.assertEquals(10, seareachResponse.getProducts().size());
		Assert.assertEquals("POSTAGE", seareachResponse.getProducts().get(1).getTitle());

		seareachResponse = ProductSearch.searchProduct(searchQuery);
		Assert.assertEquals(10, seareachResponse.getProducts().size());
		

	}
	
	// Tests for WDF filter ( Actual query and modified query should provide the same results)
	@Test
	public void testWDFFilterSearch() {
		seareachResponse = ProductSearch.searchProduct(searchWdfQueryActual);
		seareachResponse1 = ProductSearch.searchProduct(searchWdfQueryModified);
		Assert.assertEquals(seareachResponse.getNoOfFound(), seareachResponse1.getNoOfFound());

	}
	
	// Tests for stemming  filter 
	@Test	
	public void testStemming() {
			seareachResponse = ProductSearch.searchProduct(searchSingualrQuery);
			seareachResponse1 = ProductSearch.searchProduct(searchPluralQuery);
			Assert.assertEquals(seareachResponse.getNoOfFound(), seareachResponse1.getNoOfFound());

		}
	

}
