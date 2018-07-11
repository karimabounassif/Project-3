package com.bootcamp.lab.Amazon.Account;

import com.bootcamp.lab.Amazon.Address.Address;
import com.bootcamp.lab.Amazon.Address.AddressRepo;
import com.bootcamp.lab.Amazon.OrderLine.OrderLineItems;
import com.bootcamp.lab.Amazon.Orders.Orders;
import com.bootcamp.lab.Amazon.Orders.OrdersRepo;
import com.bootcamp.lab.Amazon.Shipment.ShipmentRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    AccountRepo accountrepo;
    AddressRepo addressRepo;
    OrdersRepo ordersRepo;
    ShipmentRepo shipmentRepo;


    public AccountController(AccountRepo accountRepo, AddressRepo addressRepo, OrdersRepo ordersRepo, ShipmentRepo shipmentRepo){
        this.accountrepo = accountRepo;
        this.addressRepo = addressRepo;
        this.ordersRepo = ordersRepo;
        this.shipmentRepo = shipmentRepo;
    }

    //Create Account
    @PostMapping("/{fname}/{lname}/{email}")
    public ResponseEntity<Account> newAccount(@PathVariable(name="fname") String firstName, @PathVariable(name="lname") String lastName, @PathVariable(name="email") String email){
        Account save = new Account(firstName, lastName, email);
        return new ResponseEntity<>(accountrepo.save(save), HttpStatus.OK);
    }

    //Find by id
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable(name = "id") Long id) {
        Account result = accountrepo.findById(id).get();
        ResponseEntity<Account> ret = new ResponseEntity<Account>(result, HttpStatus.CREATED);
        return ret;
    }

    //Find order details
    @GetMapping("/{id}/{order_id}")
    public @ResponseBody String getOrderDetail(@PathVariable(name="id") Long id, @PathVariable(name="order_id") Integer order_id){

        Orders order = ordersRepo.findById(order_id).get();
        String ret = "Order #" + order.getId().toString() + " to " + order.getAccount().getFirstName() + ", " + order.getAccount().getLastName();
        List<OrderLineItems> ol = order.getOrderLine();
        Iterator<OrderLineItems> it = ol.iterator();
        while(it.hasNext()){
            OrderLineItems current = it.next();
            ret += "\n" + current.getProduct() + " x " + current.getQuantity();
        }
        ret += "\nDelivered to: " + order.getAddress().getStreet();
        return ret;
    }

    //Change address
    @PutMapping("/{id}/{address}")
    public ResponseEntity<Account> updateAccount(@PathVariable(name = "id") Long id, @PathVariable(name = "address") Integer address_id) {
        Account result = accountrepo.findById(id).get();
        Address address = addressRepo.findById(address_id).get();
        result.setAddress(address);
        return new ResponseEntity<>(accountrepo.save(result), HttpStatus.OK);
    }

    //Delete by id
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteAccount(@PathVariable(name = "id") Long id){
        Account result = accountrepo.findById(id).get();
        accountrepo.delete(result);
        return "deleted.";
    }
}
