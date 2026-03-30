package com.JB.E_Commerce.Service;

import com.JB.E_Commerce.Model.Product;
import com.JB.Repo.ProductRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
//	List<Product> products = new ArrayList<>(Arrays.asList(
//			new Product(101,"Iphone",50000),
//			new Product(102,"camera",52000),
//			new Product(103,"Laptop",60000)
//	));
	
	@Autowired
	ProductRepository repo;
	
	public List<Product> getProduct(){
		return repo.findAll();
	}
	
	public Product getProId(int proId) {
		return repo.findById(proId).orElse(new Product());
	}
	
	public void addProd(Product prod) {
		repo.save(prod);
	}

	public void getUpdateProd(Product prod) {
		repo.save(prod);
	}

	public void DeleteProd(int prod) {
		repo.deleteById(prod);
	}
	
}
