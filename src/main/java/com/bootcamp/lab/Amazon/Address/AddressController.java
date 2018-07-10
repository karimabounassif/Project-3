package com.bootcamp.lab.Amazon.Address;

import com.bootcamp.lab.Amazon.Account.Account;
import com.bootcamp.lab.Amazon.Account.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/address")
public class AddressController {

    AddressRepo addressrepo;
    AccountRepo accountRepo;

    public AddressController(AddressRepo addressRepo, AccountRepo accountRepo){
        this.addressrepo = addressRepo;
        this.accountRepo = accountRepo;
    }

    //Create
    @PostMapping("/{street}/{apt}/{city}/{state}/{postal}/{country}")
    public @ResponseBody String newAddress(@PathVariable(name="street") String street, @PathVariable(name="apt") String apt,
                      @PathVariable(name="city") String city, @PathVariable(name="state") String state,
                      @PathVariable(name="postal") String postal, @PathVariable(name="country") String country) {

        Address address = new Address(street, apt, city, state, postal, country);
        addressrepo.save(address);
        return "saved.";
    }

    //Get by id
    @GetMapping("/{id}")
    public @ResponseBody String findAddress(@PathVariable(name="id") Integer id){
        Address address = addressrepo.findById(id).get();
        return address.getStreet();
    }

    //Change street
    @PutMapping("/{id}/{street}")
    public @ResponseBody String updateAccount(@PathVariable(name = "id") Integer id, @PathVariable(name = "street") String street) {
        Address result = addressrepo.findById(id).get();
        result.setStreet(street);
        addressrepo.save(result);
        return "updated.";
    }

    //Delete by id
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteAccount(@PathVariable(name = "id") Integer id){
        Address result = addressrepo.findById(id).get();
        addressrepo.delete(result);
        return "deleted.";
    }
}
