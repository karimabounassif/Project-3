package com.bootcamp.lab.Amazon.OrderLine;

import com.bootcamp.lab.Amazon.Product.Product;
import com.bootcamp.lab.Amazon.Product.ProductRepo;
import com.bootcamp.lab.Amazon.Shipment.Shipment;
import com.bootcamp.lab.Amazon.Shipment.ShipmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

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
    public ResponseEntity<OrderLineItems> newOrderLine(@PathVariable(name="product_id") Integer product_id, @PathVariable(name="quantity") Double quantity,
                                 @PathVariable(name="price") Double price){
        Product product = productRepo.findById(product_id).get();
        OrderLineItems orderLineItems = new OrderLineItems(product, quantity, price, (quantity*price));
        return new ResponseEntity<>(orderLineRepo.save(orderLineItems), HttpStatus.CREATED);
    }

    //Get OrderLine
    @GetMapping("/{id}")
    public ResponseEntity<OrderLineItems> getOrderLine(@PathVariable(name="id") Integer id){
        OrderLineItems orderLineItems = orderLineRepo.findById(id).get();
        return new ResponseEntity<>(orderLineItems, HttpStatus.OK);
    }

    //Change shipment
    @PutMapping("/{id}/{shipment_id}")
    public ResponseEntity<OrderLineItems> updateQuantity(@PathVariable(name="id") Integer id, @PathVariable(name="shipment_id") Integer shipment_id){
        OrderLineItems orderLineItems = orderLineRepo.findById(id).get();
        Shipment shipment = shipmentRepo.findById(shipment_id).get();
        orderLineItems.setShipment(shipment);
        return new ResponseEntity<>(orderLineRepo.save(orderLineItems), HttpStatus.OK);
    }

    //Delete OrderLine
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteOrderLine(@PathVariable(name="id") Integer id){
        OrderLineItems orderLineItems = orderLineRepo.findById(id).get();
        orderLineRepo.delete(orderLineItems);
        return "deleted.";
    }
}
