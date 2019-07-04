package com.adamkorzeniak.masterdata.features.diet.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.api.GenericSpecification;
import com.adamkorzeniak.masterdata.api.SearchFilterParam;
import com.adamkorzeniak.masterdata.api.SearchFilterService;
import com.adamkorzeniak.masterdata.features.diet.model.Product;
import com.adamkorzeniak.masterdata.features.diet.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SearchFilterService searchFilterService;

	@Override
	public List<Product> searchProducts(Map<String, String> requestParams) {
		List<SearchFilterParam> filters = searchFilterService.buildFilters(requestParams, "diet.products");
		Specification<Product> spec = new GenericSpecification<>(filters);
		return productRepository.findAll(spec);
	}

	@Override
	public Optional<Product> findProductById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Product addProduct(Product product) {
		product.setId(-1L);
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Long id, Product product) {
		product.setId(id);
		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public boolean isProductExist(Long id) {
		return productRepository.existsById(id);
	}
}
