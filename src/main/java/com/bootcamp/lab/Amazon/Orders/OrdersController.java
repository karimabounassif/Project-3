package com.bootcamp.lab.Amazon.Orders;

import com.bootcamp.lab.Amazon.Account.Account;
import com.bootcamp.lab.Amazon.Account.AccountRepo;
import com.bootcamp.lab.Amazon.Address.Address;
import com.bootcamp.lab.Amazon.Address.AddressRepo;
import com.bootcamp.lab.Amazon.OrderLine.OrderLineItems;
import com.bootcamp.lab.Amazon.OrderLine.OrderLineRepo;
import com.bootcamp.lab.Amazon.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Order;
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
    @PostMapping("/{account_id}/{date}/{address_id}/{ol_id}/{totalPrice}")
    public @ResponseBody String newOrder(@PathVariable(name = "account_id") Long account_id, @PathVariable(name="date") String date,
                                         @PathVariable(name="address_id") Integer address_id, @PathVariable(name="ol_id") Integer ol_id,
                                         @PathVariable(name="totalPrice") Double totalPrice) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Orders order = new Orders(sdf.parse(date), totalPrice);
        Account account = accountRepo.findById(account_id).get();
        Address address = addressRepo.findById(address_id).get();
        OrderLineItems orderLineItems = orderLineRepo.findById(ol_id).get();
        order.setAccount(account);
        order.setAddress(address);
        order.setOrderLine(orderLineItems);
        ordersRepo.save(order);
        return "saved.";
    }

    //Get order
    @GetMapping("/{id}")
    public @ResponseBody String getOrder(@PathVariable(name="id") Integer id){
        Orders order = ordersRepo.findById(id).get();
        return "Order of " + order.getOrderLine().getProduct().getName() + "x" + order.getOrderLine().getQuantity().toString() + " to: "
                + order.getAccount().getFirstName() + " at: " + order.getAddress().getStreet();
    }

    //Get all orders for one account
    @GetMapping("/account/{account_id}")
    public @ResponseBody String getAllOrder(@PathVariable(name="account_id") Long account_id){
        Iterator<Orders> it = ordersRepo.findAll().iterator();
        Account account = accountRepo.findById(account_id).get();
        String ret = "The following are orders for account # " + account.getId() + ":";
        List<Orders> orders = new ArrayList<>();
        while(it.hasNext()){
            Orders current = it.next();
            if(current.getAccount() == account){
                orders.add(current);
                ret += ", " + current.getId();
            }
        }
        return ret;
    }

    //Update OrderLine
    @PutMapping("/{id}/{ol_id}")
    public @ResponseBody String updateProduct(@PathVariable(name="id") Integer id, @PathVariable(name="ol_id") Integer ol_id){
        Orders order = ordersRepo.findById(id).get();
        OrderLineItems orderLineItems = orderLineRepo.findById(ol_id).get();
        order.setOrderLine(orderLineItems);
        ordersRepo.save(order);
        return "New Order: " + order.getOrderLine().getProduct() + "x" + order.getOrderLine().getQuantity().toString();
    }

    //Delete Order
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteOrder(@PathVariable(name="id") Integer id){
        Orders order = ordersRepo.findById(id).get();
        ordersRepo.delete(order);
        return "deleted.";
    }
}
