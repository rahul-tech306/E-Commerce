package com.JB.E_Commerce.Model;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
@JsonPropertyOrder({"productId", "productName", "price"})
public class Product {
	
	@Id  //primary key
    private int productId;
    private String productName;
    private int price;

   
    public Product() {
    }

      public Product(int productId, String productName, int price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    
    @Override
    public String toString() {
        return "Product{" +
                "productId= " + productId +
                ",productName= " + productName +
                ",price= '" + price +'\'' +
                '}';
    }
}