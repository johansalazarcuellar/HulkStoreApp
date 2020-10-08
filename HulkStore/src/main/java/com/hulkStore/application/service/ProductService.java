package com.hulkStore.application.service;

import com.hulkStore.application.Exception.ProductOrIdNotFound;
import com.hulkStore.application.entity.Product;

public interface ProductService {
	
	public Iterable<Product> getAllProducts();

	public Product createProduct(Product product) throws Exception;
	
	public Product getProductById(Long id) throws ProductOrIdNotFound;
	
	public Product updateProduct(Product product) throws Exception;
	
	public void deleteProduct(Long id) throws ProductOrIdNotFound;
}
