package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String hello() {
		return "Welcome Admin ! ...";
	}
	
	@GetMapping("products")
	public ResponseEntity<List<Product>> allProducts() {
		return productService.getAllProducts("");
	}
	
	@GetMapping("product/category/{cat}")
	public ResponseEntity<List<Product>> allProductByCategory(@PathVariable String cat) {
		return productService.getAllProducts(cat);
	}
	
	@GetMapping("product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
		return productService.getProduct(id);
	}
	
	// Add New Product
	@PostMapping("product")
	public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
		try {
			Product productSaved = productService.addProduct(product, imageFile);
			return new ResponseEntity<>(productSaved, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Get Product Image
	@GetMapping("product/{productId}/image")
	public ResponseEntity<byte[]> getImageById(@PathVariable Integer productId) {
		Product product = productService.getProductById(productId);
		byte[] imageFile = product.getImageData();
		return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
	}
	
	// Update existing Product
	@PutMapping("product/{productId}")
	public ResponseEntity<String> updateProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile, @PathVariable Integer productId) {
		try {
			Product productUpdated = productService.updateProduct(product, imageFile, productId);
			if(productUpdated != null) {
				return new ResponseEntity<>("Updated", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("product/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) {
		Product product = productService.getProductById(productId);
		if(product != null) {
			productService.deleteProduct(productId);
			return new ResponseEntity<String>("Deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Product not Found", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("products/search")
	public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {
		//System.out.println(keyword);
		List<Product> products = productService.searchProductByKey(keyword);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
}
