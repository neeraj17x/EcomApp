package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.model.Product;
import com.example.demo.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	ProductRepo productRepo;
	
	public ResponseEntity<List<Product>> getAllProducts(String category) {
		List<Product> products;
		//products = productRepo.findAll();
		if(category == "") {
			products = productRepo.findAll();
		} else {
			products = productRepo.findByCategory(category);
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	public ResponseEntity<Product> getProduct(Integer id) {
		Product product = productRepo.findById(id).orElse(new Product());
		if(product.getId() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
		// Set Image info in product object
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		// Save product info along with image info
		return productRepo.save(product);
	}

	public Product getProductById(Integer productId) {
		Product product = productRepo.findById(productId).orElse(null);
		return product;
	}

	public Product updateProduct(Product product, MultipartFile imageFile, Integer productId) throws IOException {
		// Set Image info in product object
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		// Save product info along with image info
		return productRepo.save(product);
	}

	// Delete Product
	public void deleteProduct(Integer productId) {
		productRepo.deleteById(productId);
	}

	public List<Product> searchProductByKey(String keyword) {
		//System.out.println(keyword);
		return productRepo.searchProducts(keyword);
		//return null;
	}

}
