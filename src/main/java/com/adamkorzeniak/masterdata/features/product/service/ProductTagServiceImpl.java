package com.adamkorzeniak.masterdata.features.product.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.product.model.ProductTag;
import com.adamkorzeniak.masterdata.features.product.repository.ProductTagRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductTagServiceImpl implements ProductTagService {

    private final ProductTagRepository productTagRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public ProductTagServiceImpl(ProductTagRepository productTagRepository,
                              ApiQueryService apiQueryService) {
        this.productTagRepository = productTagRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<ProductTag> searchProductTags(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<ProductTag> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return productTagRepository.findAll(spec);
    }

    @Override
    public Optional<ProductTag> findProductTagById(Long id) {
        return productTagRepository.findById(id);
    }

    @Override
    public ProductTag addProductTag(ProductTag productTag) {
        productTag.setId(-1L);
        return productTagRepository.save(productTag);
    }

    @Override
    public ProductTag updateProductTag(Long id, ProductTag productTag) {
        productTag.setId(id);
        return productTagRepository.save(productTag);
    }

    @Override
    public void deleteProductTag(Long id) {
        productTagRepository.deleteById(id);
    }

    @Override
    public boolean isProductTagExist(Long id) {
        return productTagRepository.existsById(id);
    }
}
