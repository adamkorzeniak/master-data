package com.adamkorzeniak.masterdata.features.diet.service;

import com.adamkorzeniak.masterdata.features.diet.model.DietProduct;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DietProductService {

    List<DietProduct> searchProducts(Map<String, String> allRequestParams);

    Optional<DietProduct> findProductById(Long id);

    DietProduct addProduct(DietProduct dietProduct);

    DietProduct updateProduct(Long id, DietProduct dietProduct);

    void deleteProduct(Long id);

    boolean isProductExist(Long id);
}
