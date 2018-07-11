package com.bootcamp.lab.Amazon.Orders;

import com.bootcamp.lab.Amazon.Account.Account;
import com.bootcamp.lab.Amazon.Account.AccountRepo;
import com.bootcamp.lab.Amazon.Address.Address;
import com.bootcamp.lab.Amazon.Address.AddressRepo;
import com.bootcamp.lab.Amazon.OrderLine.OrderLineItems;
import com.bootcamp.lab.Amazon.OrderLine.OrderLineRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    OrdersRepo ordersRepo;
    AccountRepo accountRepo;
    OrderLineRepo orderLineRepo;
    AddressRepo addressRepo;

    public OrdersController(OrdersRepo ordersRepo, AccountRepo accountRepo, OrderLineRepo orderLineRepo, AddressRepo addressRepo){
        this.ordersRepo = ordersRepo;
        this.accountRepo = accountRepo;
        this.orderLineRepo = orderLineRepo;
        this.addressRepo = addressRepo;
    }

    //New Order
    @PostMapping("/{account_id}/{date}/{address_id}/{ol_id}")
    public ResponseEntity<Orders> newOrder(@PathVariable(name = "account_id") Long account_id, @PathVariable(name="date") String date,
                                   @PathVariable(name="address_id") Integer address_id, @PathVariable(name="ol_id") Integer ol_id) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Orders order = new Orders(sdf.parse(date));
        Account account = accountRepo.findById(account_id).get();
        Address address = addressRepo.findById(address_id).get();
        OrderLineItems orderLineItems = orderLineRepo.findById(ol_id).get();
        List<OrderLineItems> ol = new ArrayList<>();
        ol.add(orderLineItems);

        order.setTotalPrice(orderLineItems.getTotalPrice());
        order.setAccount(account);
        order.setAddress(address);
        order.setOrderLine(ol);
        order.setOrderNumber(order.getId());

        return new ResponseEntity<>(ordersRepo.save(order), HttpStatus.CREATED);
    }

    //Get order
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrder(@PathVariable(name="id") Integer id){
        Orders order = ordersRepo.findById(id).get();
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //Get all orders for one account
    @GetMapping("/account/{account_id}")
    public ResponseEntity<List<Orders>> getAllOrder(@PathVariable(name="account_id") Long account_id){
        Iterator<Orders> it = ordersRepo.findAll().iterator();
        Account account = accountRepo.findById(account_id).get();
        List<Orders> orders = new ArrayList<>();
        while(it.hasNext()){
            Orders current = it.next();
            if(current.getAccount() == account){
                orders.add(current);
            }
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    //Add orderline
    @PutMapping("/{id}/{ol_id}")
    public ResponseEntity<Orders> updateProduct(@PathVariable(name="id") Integer id, @PathVariable(name="ol_id") Integer ol_id) {
        Orders order = ordersRepo.findById(id).get();
        OrderLineItems orderLineItems = orderLineRepo.findById(ol_id).get();
        List<OrderLineItems> ol = order.getOrderLine();
        ol.add(orderLineItems);
        order.setOrderLine(ol);
        return new ResponseEntity<>(ordersRepo.save(order), HttpStatus.OK);
    }

    //Delete Order
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteOrder(@PathVariable(name="id") Integer id){
        Orders order = ordersRepo.findById(id).get();
        ordersRepo.delete(order);
        return "deleted.";
    }
}
