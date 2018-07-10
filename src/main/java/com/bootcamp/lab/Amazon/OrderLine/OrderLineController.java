package com.bootcamp.lab.Amazon.OrderLine;

import com.bootcamp.lab.Amazon.Product.Product;
import com.bootcamp.lab.Amazon.Product.ProductRepo;
import com.bootcamp.lab.Amazon.Shipment.Shipment;
import com.bootcamp.lab.Amazon.Shipment.ShipmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orderline")
public class OrderLineController {

    OrderLineRepo orderLineRepo;
    ProductRepo productRepo;
    ShipmentRepo shipmentRepo;

    public OrderLineController(OrderLineRepo orderLineRepo, ProductRepo productRepo, ShipmentRepo shipmentRepo){
        this.orderLineRepo = orderLineRepo;
        this.productRepo = productRepo;
        this.shipmentRepo = shipmentRepo;
    }

    //Create new OrderLine
    @PostMapping("/{product_id}/{quantity}/{price}")
    public @ResponseBody String newOrderLine(@PathVariable(name="product_id") Integer product_id, @PathVariable(name="quantity") Double quantity,
                                             @PathVariable(name="price") Double price){
        Product product = productRepo.findById(product_id).get();
        OrderLineItems orderLineItems = new OrderLineItems(product, quantity, price, (quantity*price));
        orderLineRepo.save(orderLineItems);
        return "saved.";
    }

    //Get OrderLine
    @GetMapping("/{id}")
    public @ResponseBody String getOrderLine(@PathVariable(name="id") Integer id){
        OrderLineItems orderLineItems = orderLineRepo.findById(id).get();
        return orderLineItems.getProduct().getName() + " - " + orderLineItems.getQuantity().toString();
    }

    //Change shipment
    @PutMapping("/{id}/{shipment_id}")
    public @ResponseBody String updateQuantity(@PathVariable(name="id") Integer id, @PathVariable(name="shipment_id") Integer shipment_id){
        OrderLineItems orderLineItems = orderLineRepo.findById(id).get();
        Shipment shipment = shipmentRepo.findById(shipment_id).get();
        orderLineItems.setShipment(shipment);
        orderLineRepo.save(orderLineItems);
        return "New shipment, " + orderLineItems.getShipment().getAddress().getStreet();
    }

    //Delete OrderLine
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteOrderLine(@PathVariable(name="id") Integer id){
        OrderLineItems orderLineItems = orderLineRepo.findById(id).get();
        orderLineRepo.delete(orderLineItems);
        return "deleted.";
    }
}
