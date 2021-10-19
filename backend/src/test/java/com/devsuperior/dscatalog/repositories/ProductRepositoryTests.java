package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	private long exintigId;
	private long nonExintingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		exintigId = 1L;
		nonExintingId = 1000L;
		countTotalProducts = 25L;
	
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		
		Product product = Factory.createProduct();
		product.setId(null); 
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts +1, product.getId());
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		repository.deleteById(exintigId);

		Optional<Product> result = repository.findById(exintigId);
		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteSholdThrowEmptyResultDataAcessExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExintingId);
		});
	}
		
	@Test
	public void findByIdShouldReturnNowEmptyOptionalWhenIdExists () {
		
		Optional<Product> result = repository.findById(exintigId);
		
		Assertions.assertTrue(result.isPresent());
	
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists () {
		
		Optional<Product> result = repository.findById(nonExintingId);
		
		Assertions.assertTrue(result.isEmpty());
	
	}
}
