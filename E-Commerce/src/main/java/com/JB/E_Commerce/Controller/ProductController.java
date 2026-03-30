package com.JB.E_Commerce.Controller;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.JB.E_Commerce.Model.Product;
import com.JB.E_Commerce.Service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	ProductService service;
	
	@RequestMapping("/")
	public void greet() {
		System.out.println("WEL-COME TO JB COMPANY....");
	}

	@RequestMapping("/products")
	public List<Product> getProduct() {
		return service.getProduct();
	}
	
	@GetMapping("/products/{proId}")
	public Product getProdId(@PathVariable int proId) {
		return service.getProId(proId);
	}
	
	@PostMapping("/products")
	public void addProduct(@RequestBody Product prod) {
		service.addProd(prod);
	}
	
	@PutMapping("/products")
	public void updateProd(@RequestBody Product prod) {
		service.getUpdateProd(prod);
	}
	
	@DeleteMapping("/products/{proId}")
	public void DeleteProd(@PathVariable int proId) {
		service.DeleteProd(proId);
	}
	
}
