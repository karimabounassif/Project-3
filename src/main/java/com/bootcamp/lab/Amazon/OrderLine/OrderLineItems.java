package com.bootcamp.lab.Amazon.OrderLine;

import com.bootcamp.lab.Amazon.Product.Product;
import com.bootcamp.lab.Amazon.Shipment.Shipment;

import javax.persistence.*;
import java.util.Set;

@Entity
public class OrderLineItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderline_id;
    @OneToOne
    @JoinColumn(name="product_id")
    private Product product;
    private Double quantity;
    private Double price;
    private Double totalPrice;
    @OneToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    public OrderLineItems(){}

    public OrderLineItems(Product product, Double quantity, Double price, Double totalPrice){
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return orderline_id;
    }

    public void setId(Integer id) {
        this.orderline_id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

}
