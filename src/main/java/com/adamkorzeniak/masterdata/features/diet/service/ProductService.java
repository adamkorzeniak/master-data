package com.adamkorzeniak.masterdata.features.diet.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.adamkorzeniak.masterdata.features.diet.model.Product;

public interface ProductService {

    List<Product> searchProducts(Map<String, String> allRequestParams);

    /**
     * Returns optional of product for given id.
     * If product not found empty optional
     *
     * @param id Must not be null
     * @return Optional of product for given id
     */
    Optional<Product> findProductById(Long id);

    /**
     * Create product in database.
     * Returns created product
     *
     * @param product Must not be null
     * @return Created product
     */
    Product addProduct(Product product);

    /**
     * Update product for given id in database.
     * Returns updated product
     *
     * @param id      Must not be null
     * @param product Must not be null
     * @return Updated product
     */
    Product updateProduct(Long id, Product product);

    /**
     * Delete product for given id in database.
     *
     * @param id Must not be null
     */
    void deleteProduct(Long id);

    /**
     * Returns if product exists in database
     *
     * @param id Must not be null
     * @return If product exists
     */
    boolean isProductExist(Long id);
}
