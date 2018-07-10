package com.bootcamp.lab.Amazon.Shipment;

import com.bootcamp.lab.Amazon.Account.Account;
import com.bootcamp.lab.Amazon.Account.AccountRepo;
import com.bootcamp.lab.Amazon.Address.Address;
import com.bootcamp.lab.Amazon.Address.AddressRepo;
import com.bootcamp.lab.Amazon.OrderLine.OrderLineItems;
import com.bootcamp.lab.Amazon.OrderLine.OrderLineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/shipment")
public class ShipmentController {

    ShipmentRepo shipmentRepo;
    AccountRepo accountRepo;
    AddressRepo addressRepo;
    OrderLineRepo orderLineRepo;

    public ShipmentController(ShipmentRepo shipmentRepo, AccountRepo accountRepo, AddressRepo addressRepo, OrderLineRepo orderLineRepo){
        this.shipmentRepo = shipmentRepo;
        this.accountRepo = accountRepo;
        this.addressRepo = addressRepo;
        this.orderLineRepo = orderLineRepo;
    }

    //New shipment
    @PostMapping("/{account_id}/{address_id}/{ol_id}/{shipped_date}/{delivery_date}")
    public @ResponseBody String newShipment(@PathVariable(name="account_id") Long account_id, @PathVariable(name="address_id") Integer address_id,
                                            @PathVariable(name="ol_id") Integer ol_id, @PathVariable(name="shipped_date") String shipped,
                                            @PathVariable(name="delivery_date") String delivery) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Account account = accountRepo.findById(account_id).get();
        Address address = addressRepo.findById(address_id).get();
        OrderLineItems orderLineItems = orderLineRepo.findById(ol_id).get();
        Date shippedDate = sdf.parse(shipped);
        Date deliveryDate = sdf.parse(delivery);
        Shipment shipment = new Shipment(shippedDate, deliveryDate);
        shipment.setAccount(account);
        shipment.setAddress(address);
        shipment.setOrderline(orderLineItems);
        shipmentRepo.save(shipment);
        return "saved.";
    }

    //Get Shipment
    @GetMapping("/{id}")
    public @ResponseBody String getShipment(@PathVariable(name="id") Integer id){
        Shipment shipment = shipmentRepo.findById(id).get();
        return shipment.getAccount().getFirstName() + " - " + shipment.getAddress().getStreet();
    }

    //Change address
    @PutMapping("/{id}/{address_id}")
    public @ResponseBody String changeAddress(@PathVariable(name="id") Integer shipment_id, @PathVariable(name="address_id") Integer address_id){
        Shipment shipment = shipmentRepo.findById(shipment_id).get();
        Address address = addressRepo.findById(address_id).get();
        shipment.setAddress(address);
        shipmentRepo.save(shipment);
        return "New address: " + address.getStreet();
    }

    //Delete
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteShipment(@PathVariable(name="id") Integer id){
        Shipment shipment = shipmentRepo.findById(id).get();
        shipmentRepo.delete(shipment);
        return "deleted.";
    }
}
