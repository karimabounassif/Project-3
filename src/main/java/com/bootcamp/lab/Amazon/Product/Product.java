package com.bootcamp.lab.Amazon.Product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {

    public Product() {}

    public Product(String name, String image, String description, Double price){
        this.name= name;
        this.image=image;
        this.price=price;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer product_id;
    private String name;
    private String image;
    private Double price;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return product_id;
    }

    public void setId(Integer id) {
        this.product_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }



}
