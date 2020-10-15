package com.hulkStore.application.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.hulkStore.application.Exception.ProductOrIdNotFound;
import com.hulkStore.application.entity.Product;
import com.hulkStore.application.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
	
	@Autowired
	ProductServiceImpl serviceImpl;
	
	@MockBean
	ProductRepository productRepository;
	
	@Test
	public void testGetAllProducts() {
		assertNotNull(serviceImpl.getAllProducts());
	}

	@Test
	public void testCreateProduct() throws Exception {
		Product product = new Product();
		product.setId((long) 1);
		product.setDescription("product1");
		product.setCategory("categoria1");
		product.setType("tipo1");
		product.setQuantity("10");
		product.setPrice("1000");
		when(productRepository.save(product)).thenReturn(product);
		assertEquals(product, serviceImpl.createProduct(product));
	}

	@Test
	public void testGetProductById() throws ProductOrIdNotFound {
		
		Long idProduct = (long) 1;
		
		Product product = new Product();
		product.setId((long) 1);
		product.setDescription("product1");
		product.setCategory("categoria1");
		product.setType("tipo1");
		product.setQuantity("10");
		product.setPrice("1000");
		Optional<Product> optionalProduct = Optional.of(product);
		when(productRepository.findById(idProduct)).thenReturn(optionalProduct);
		assertNotNull(serviceImpl.getProductById(idProduct));
	}

	@Test
	public void testUpdateProduct() throws Exception {
		Long idProduct = (long) 1;
		
		Product product = new Product();
		product.setId((long) 1);
		product.setDescription("product1");
		product.setCategory("categoria1");
		product.setType("tipo1");
		product.setQuantity("10");
		product.setPrice("1000");
		Optional<Product> optionalProduct = Optional.of(product);
		when(productRepository.findById(idProduct)).thenReturn(optionalProduct);
		when(productRepository.save(product)).thenReturn(product);
		assertNotNull(serviceImpl.updateProduct(product));
	}


	@Test
	public void testDeleteProduct() throws ProductOrIdNotFound {
		
		Long idProduct = (long) 1;
		
		Product product = new Product();
		product.setId((long) 1);
		product.setDescription("product1");
		product.setCategory("categoria1");
		product.setType("tipo1");
		product.setQuantity("10");
		product.setPrice("1000");
		Optional<Product> optionalProduct = Optional.of(product);
		when(productRepository.findById(idProduct)).thenReturn(optionalProduct);
		serviceImpl.deleteProduct(idProduct);
		verify(productRepository, times(1)).delete(product);
	}

}
