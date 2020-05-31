package com.adamkorzeniak.masterdata.features.product.service;

import com.adamkorzeniak.masterdata.features.product.model.ProductTag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductTagService {

    List<ProductTag> searchProductTags(Map<String, String> allRequestParams);

    Optional<ProductTag> findProductTagById(Long id);

    ProductTag addProductTag(ProductTag productTag);

    ProductTag updateProductTag(Long id, ProductTag productTag);

    void deleteProductTag(Long id);

    boolean isProductTagExist(Long id);
}
