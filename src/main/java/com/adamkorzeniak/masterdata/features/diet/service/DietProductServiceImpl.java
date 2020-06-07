package com.adamkorzeniak.masterdata.features.diet.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.diet.model.DietProduct;
import com.adamkorzeniak.masterdata.features.diet.repository.DietProductRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DietProductServiceImpl implements DietProductService {

    private final DietProductRepository dietProductRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public DietProductServiceImpl(DietProductRepository dietProductRepository,
                                  ApiQueryService apiQueryService) {
        this.dietProductRepository = dietProductRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<DietProduct> searchProducts(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<DietProduct> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return dietProductRepository.findAll(spec);
    }

    @Override
    public Optional<DietProduct> findProductById(Long id) {
        return dietProductRepository.findById(id);
    }

    @Override
    public DietProduct addProduct(DietProduct dietProduct) {
        dietProduct.setId(-1L);
        return dietProductRepository.save(dietProduct);
    }

    @Override
    public DietProduct updateProduct(Long id, DietProduct dietProduct) {
        dietProduct.setId(id);
        return dietProductRepository.save(dietProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        dietProductRepository.deleteById(id);
    }

    @Override
    public boolean isProductExist(Long id) {
        return dietProductRepository.existsById(id);
    }
}
