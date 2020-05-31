package com.adamkorzeniak.masterdata.features.product.service;

import com.adamkorzeniak.masterdata.features.product.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    List<Product> searchProducts(Map<String, String> allRequestParams);

    Optional<Product> findProductById(Long id);

    Product addProduct(Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    boolean isProductExist(Long id);
}
