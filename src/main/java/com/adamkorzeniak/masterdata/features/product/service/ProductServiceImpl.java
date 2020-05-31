package com.adamkorzeniak.masterdata.features.product.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.product.model.Product;
import com.adamkorzeniak.masterdata.features.product.repository.ProductRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                           ApiQueryService apiQueryService) {
        this.productRepository = productRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<Product> searchProducts(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<Product> spec = new GenericSpecification<>(filterExpression, orderExpression);
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
