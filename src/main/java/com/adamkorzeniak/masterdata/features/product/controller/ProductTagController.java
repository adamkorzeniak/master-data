package com.adamkorzeniak.masterdata.features.product.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.product.model.ProductTag;
import com.adamkorzeniak.masterdata.features.product.service.ProductTagService;
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
public class ProductTagController {

    private static final String BOOK_TAG_RESOURCE_NAME = "ProductTag";

    private final ProductTagService productTagService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public ProductTagController(ProductTagService productTagService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.productTagService = productTagService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of productTags with 200 OK.
     * <p>
     * If there are no productTags it returns empty list with 204 No Content
     */
    @GetMapping("/tags")
    public ResponseEntity<List<Map<String, Object>>> findProductTags(@RequestParam Map<String, String> allRequestParams) {
        List<ProductTag> productTags = productTagService.searchProductTags(allRequestParams);
        if (productTags.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(productTags, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns productTag with given id with 200 OK.
     * <p>
     * If productTag with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/tags/{productTagId}")
    public ResponseEntity<ProductTag> findProductTagById(@PathVariable("productTagId") Long productTagId) {
        Optional<ProductTag> productTag = productTagService.findProductTagById(productTagId);
        if (productTag.isEmpty()) {
            throw new NotFoundException(BOOK_TAG_RESOURCE_NAME, productTagId);
        }
        return new ResponseEntity<>(productTag.get(), HttpStatus.OK);
    }

    /**
     * Creates a productTag in database.
     * Returns created productTag with 201 Created.
     * <p>
     * If provided productTag data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/tags")
    public ResponseEntity<ProductTag> addProductTag(@RequestBody @Valid ProductTag productTag) {
        ProductTag newProductTag = productTagService.addProductTag(productTag);
        return new ResponseEntity<>(newProductTag, HttpStatus.CREATED);
    }

    /**
     * Updates a productTag with given id in database. Returns updated productTag with 200 OK.
     * <p>
     * If productTag with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided productTag data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/tags/{productTagId}")
    public ResponseEntity<ProductTag> updateProductTag(@RequestBody @Valid ProductTag productTag, @PathVariable Long productTagId) {
        boolean exists = productTagService.isProductTagExist(productTagId);
        if (!exists) {
            throw new NotFoundException(BOOK_TAG_RESOURCE_NAME, productTagId);
        }
        ProductTag newProductTag = productTagService.updateProductTag(productTagId, productTag);
        return new ResponseEntity<>(newProductTag, HttpStatus.OK);
    }

    /**
     * Deletes a productTag with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If productTag with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/tags/{productTagId}")
    public ResponseEntity<Void> deleteProductTag(@PathVariable Long productTagId) {
        boolean exists = productTagService.isProductTagExist(productTagId);
        if (!exists) {
            throw new NotFoundException(BOOK_TAG_RESOURCE_NAME, productTagId);
        }
        productTagService.deleteProductTag(productTagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
