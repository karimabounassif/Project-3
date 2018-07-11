package com.bootcamp.lab.Amazon.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    ProductRepo productrepo;

    public ProductController(ProductRepo productRepo){
        this.productrepo = productRepo;
    }

    //Get by id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(name="id") Integer id){
        Product product = productrepo.findById(id).get();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    //New product
    @PostMapping("/{name}/{description}/{image}/{price}")
    public ResponseEntity<Product> newProduct(@PathVariable(name="name") String name, @PathVariable(name="description") String description,
                                           @PathVariable(name="image") String image, @PathVariable(name="price") Double price){

        Product product = new Product(name, image, description, price);
        return new ResponseEntity<>(productrepo.save(product), HttpStatus.OK);
    }

    //Change product name
    @PutMapping("/{id}/{name}")
    public ResponseEntity<Product> changeProduct(@PathVariable(name="id") Integer id, @PathVariable(name="name") String name){
        Product product = productrepo.findById(id).get();
        product.setName(name);
        return new ResponseEntity<>(productrepo.save(product), HttpStatus.OK);
    }

    //Delete product
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteProduct(@PathVariable(name="id") Integer id){
        Product product = productrepo.findById(id).get();
        productrepo.delete(product);
        return "deleted";
    }
}
