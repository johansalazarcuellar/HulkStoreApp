package com.hulkStore.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulkStore.application.Exception.ProductOrIdNotFound;
import com.hulkStore.application.entity.Product;
import com.hulkStore.application.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public Iterable<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product createProduct(Product product) throws Exception {
		product = productRepository.save(product);
		return product;
	}

	@Override
	public Product getProductById(Long id) throws ProductOrIdNotFound {
		return productRepository.findById(id).orElseThrow(() -> new ProductOrIdNotFound("El Id del producto no existe"));
	}

	@Override
	public Product updateProduct(Product fromProduct) throws Exception {
		Product toProduct = getProductById(fromProduct.getId());
		mapProduct(fromProduct, toProduct);
		return productRepository.save(toProduct);
	}
	
	protected void mapProduct(Product from,Product to) {
		to.setDescription(from.getDescription());
		to.setCategory(from.getCategory());
		to.setType(from.getType());
		to.setQuantity(from.getQuantity());
		to.setPrice(from.getPrice());
	}

	@Override
	public void deleteProduct(Long id) throws ProductOrIdNotFound {
		Product product = getProductById(id);
		productRepository.delete(product);
	}

}
