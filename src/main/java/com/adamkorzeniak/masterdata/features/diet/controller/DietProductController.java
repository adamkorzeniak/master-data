package com.adamkorzeniak.masterdata.features.diet.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.diet.model.DietProduct;
import com.adamkorzeniak.masterdata.features.diet.service.DietProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v0/Diet")
public class DietProductController {

    private static final String PRODUCT_RESOURCE_NAME = "Product";

    private final DietProductService dietProductService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public DietProductController(DietProductService dietProductService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.dietProductService = dietProductService;
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
        List<DietProduct> dietProducts = dietProductService.searchProducts(allRequestParams);
        if (dietProducts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(dietProducts, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns product with given id with 200 OK.
     * <p>
     * If product with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<DietProduct> findProductById(@PathVariable("productId") Long productId) {
        Optional<DietProduct> product = dietProductService.findProductById(productId);
        if (product.isEmpty()) {
            throw new NotFoundException(PRODUCT_RESOURCE_NAME, productId);
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
    public ResponseEntity<DietProduct> addProduct(@RequestBody @Valid DietProduct dietProduct) {
        DietProduct newDietProduct = dietProductService.addProduct(dietProduct);
        return new ResponseEntity<>(newDietProduct, HttpStatus.CREATED);
    }

    /**
     * Updates a product with given id in database. Returns updated product with 200 OK.
     * <p>
     * If product with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided product data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/products/{productId}")
    public ResponseEntity<DietProduct> updateProduct(@RequestBody @Valid DietProduct dietProduct, @PathVariable Long productId) {
        boolean exists = dietProductService.isProductExist(productId);
        if (!exists) {
            throw new NotFoundException(PRODUCT_RESOURCE_NAME, productId);
        }
        DietProduct newDietProduct = dietProductService.updateProduct(productId, dietProduct);
        return new ResponseEntity<>(newDietProduct, HttpStatus.OK);
    }

    /**
     * Deletes a product with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If product with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean exists = dietProductService.isProductExist(productId);
        if (!exists) {
            throw new NotFoundException(PRODUCT_RESOURCE_NAME, productId);
        }
        dietProductService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
