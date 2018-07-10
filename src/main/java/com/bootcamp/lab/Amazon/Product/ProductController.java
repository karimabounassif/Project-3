package com.bootcamp.lab.Amazon.Product;

import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody String getProduct(@PathVariable(name="id") Integer id){
        Product product = productrepo.findById(id).get();
        return product.getName();
    }

    //New product
    @PostMapping("/{name}/{description}/{image}/{price}")
    public @ResponseBody String newProduct(@PathVariable(name="name") String name, @PathVariable(name="description") String description,
                                           @PathVariable(name="image") String image, @PathVariable(name="price") Double price){

        Product product = new Product(name, image, description, price);
        productrepo.save(product);
        return "saved.";
    }

    //Change product name
    @PutMapping("/{id}/{name}")
    public @ResponseBody String changeProduct(@PathVariable(name="id") Integer id, @PathVariable(name="name") String name){
        Product product = productrepo.findById(id).get();
        product.setName(name);
        productrepo.save(product);
        return "New name: " + product.getName();
    }

    //Delete product
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteProduct(@PathVariable(name="id") Integer id){
        Product product = productrepo.findById(id).get();
        productrepo.delete(product);
        return "deleted";
    }
}
