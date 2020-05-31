package com.adamkorzeniak.masterdata.features.product.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.product.model.Product;
import com.adamkorzeniak.masterdata.features.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v0/Product")
public class ProductController {

    private static final String BOOK_RESOURCE_NAME = "Product";

    private final ProductService productService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public ProductController(ProductService productService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.productService = productService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of products with 200 OK.
     * <p>
     * If there are no products it returns empty list with 204 No Content
     */
    @GetMapping("/products")
    public ResponseEntity<List<Map<String, Object>>> findProducts(@RequestParam Map<String, String> allRequestParams) {
        List<Product> products = productService.searchProducts(allRequestParams);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(products, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns product with given id with 200 OK.
     * <p>
     * If product with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable("productId") Long productId) {
        Optional<Product> product = productService.findProductById(productId);
        if (product.isEmpty()) {
            throw new NotFoundException(BOOK_RESOURCE_NAME, productId);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    /**
     * Creates a product in database.
     * Returns created product with 201 Created.
     * <p>
     * If provided product data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product) {
        Product newProduct = productService.addProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    /**
     * Updates a product with given id in database. Returns updated product with 200 OK.
     * <p>
     * If product with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided product data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product, @PathVariable Long productId) {
        boolean exists = productService.isProductExist(productId);
        if (!exists) {
            throw new NotFoundException(BOOK_RESOURCE_NAME, productId);
        }
        Product newProduct = productService.updateProduct(productId, product);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    /**
     * Deletes a product with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If product with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean exists = productService.isProductExist(productId);
        if (!exists) {
            throw new NotFoundException(BOOK_RESOURCE_NAME, productId);
        }
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
