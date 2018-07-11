package com.bootcamp.lab.Amazon.Address;

import com.bootcamp.lab.Amazon.Account.AccountRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Address> newAddress(@PathVariable(name="street") String street, @PathVariable(name="apt") String apt,
                                              @PathVariable(name="city") String city, @PathVariable(name="state") String state,
                                              @PathVariable(name="postal") String postal, @PathVariable(name="country") String country) {

        Address address = new Address(street, apt, city, state, postal, country);
        return new ResponseEntity<>(addressrepo.save(address), HttpStatus.CREATED);
    }

    //Get by id
    @GetMapping("/{id}")
    public ResponseEntity<Address> findAddress(@PathVariable(name="id") Integer id){
        Address address = addressrepo.findById(id).get();
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    //Change street
    @PutMapping("/{id}/{street}")
    public ResponseEntity<Address> updateAccount(@PathVariable(name = "id") Integer id, @PathVariable(name = "street") String street) {
        Address result = addressrepo.findById(id).get();
        result.setStreet(street);
        return new ResponseEntity<>(addressrepo.save(result), HttpStatus.OK);
    }

    //Delete by id
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteAccount(@PathVariable(name = "id") Integer id){
        Address result = addressrepo.findById(id).get();
        addressrepo.delete(result);
        return "deleted.";
    }
}
