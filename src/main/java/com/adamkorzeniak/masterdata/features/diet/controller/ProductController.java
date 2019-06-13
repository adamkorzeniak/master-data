package com.adamkorzeniak.masterdata.features.diet.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.diet.model.Product;
import com.adamkorzeniak.masterdata.features.diet.model.dto.ProductDTO;
import com.adamkorzeniak.masterdata.features.diet.service.ProductService;
import com.adamkorzeniak.masterdata.features.diet.service.ProductServiceHelper;

@RestController
@RequestMapping("/v0/Diet")
public class ProductController {

	private static final String RESOURCE_NAME = "Product";
	
	@Autowired
	private ProductService productService;

	/**
	 * Returns list of products with 200 OK.
	 * <p>
	 * If there are no products it returns empty list with 204 No Content
	 * 
	 * @return List of products
	 */
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> findProducts(@RequestParam Map<String, String> allRequestParams) {

		List<ProductDTO> dtos = productService.searchProducts(allRequestParams).stream().map(ProductServiceHelper::convertToDTO)
				.collect(Collectors.toList());

		if (dtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	/**
	 * Returns product with given id with 200 OK.
	 * <p>
	 * If product with given id does not exist it returns error response with 404 Not
	 * Found
	 * 
	 * @param productId - Id of movie
	 * @return Product for given id
	 */
	@GetMapping("/products/{productId}")
	public ResponseEntity<ProductDTO> findProductById(@PathVariable("productId") Long productId) {
		Optional<Product> result = productService.findProductById(productId);
		if (!result.isPresent()) {
			throw new NotFoundException(RESOURCE_NAME, productId);
		}
		ProductDTO dto = ProductServiceHelper.convertToDTO(result.get());
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * Creates a product in database. Returns created product with 201 Created.
	 * <p>
	 * If provided product data is invalid it returns 400 Bad Request.
	 * 
	 * @param product - Product to be created
	 * @return Created product
	 */
	@PostMapping("/products")
	public ResponseEntity<ProductDTO> addProduct(@RequestBody @Valid ProductDTO dto) {
		Product product = ProductServiceHelper.convertToEntity(dto);
		Product newProduct = productService.addProduct(product);
		return new ResponseEntity<>(ProductServiceHelper.convertToDTO(newProduct), HttpStatus.CREATED);
	}

	/**
	 * Updates a product with given id in database. Returns updated product with 200 OK.
	 * <p>
	 * If product with given id does not exist it returns error response with 404 Not
	 * Found
	 * <p>
	 * If provided product data is invalid it returns 400 Bad Request.
	 * 
	 * @param productId - Id of product
	 * @param product   - Product to be updated
	 * @return Updated product
	 */
	@PutMapping("/products/{productId}")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody @Valid ProductDTO dto, @PathVariable Long productId) {
		boolean exists = productService.isProductExist(productId);
		if (!exists) {
			throw new NotFoundException(RESOURCE_NAME, productId);
		}
		Product product = ProductServiceHelper.convertToEntity(dto);
		Product newProduct = productService.updateProduct(productId, product);
		return new ResponseEntity<>(ProductServiceHelper.convertToDTO(newProduct), HttpStatus.OK);
	}

	/**
	 * Deletes a product with given id. Returns empty response with 204 No Content.
	 * <p>
	 * If product with given id does not exist it returns error response with 404 Not
	 * Found
	 * 
	 * @param productId - Id of product
	 * @return Empty response
	 */
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
		boolean exists = productService.isProductExist(productId);
		if (!exists) {
			throw new NotFoundException(RESOURCE_NAME, productId);
		}
		productService.deleteProduct(productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
