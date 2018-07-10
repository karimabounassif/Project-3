package com.bootcamp.lab.Amazon.Account;

import com.bootcamp.lab.Amazon.Address.Address;
import com.bootcamp.lab.Amazon.Address.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    AccountRepo accountrepo;
    AddressRepo addressRepo;

    public AccountController(AccountRepo accountRepo, AddressRepo addressRepo){
        this.accountrepo = accountRepo;
        this.addressRepo = addressRepo;
    }

    //Create Account
    @PostMapping("/{fname}/{lname}/{email}")
    public @ResponseBody String newAccount(@PathVariable(name="fname") String firstName, @PathVariable(name="lname") String lastName, @PathVariable(name="email") String email){
        Account save = new Account(firstName, lastName, email);
        accountrepo.save(save);
        return "saved.";
    }

    //Find by id
    @GetMapping("/{id}")
    public @ResponseBody String getAccount(@PathVariable(name = "id") Long id) {
        Account result = accountrepo.findById(id).get();
        if(result.getAddress()!= null){
            return result.getFirstName() + " " + result.getAddress().iterator().next().getStreet();
        }
        return result.getFirstName() + " " + result.getLastName();
    }

    //Change address
    @PutMapping("/{id}/{address}")
    public @ResponseBody String updateAccount(@PathVariable(name = "id") Long id, @PathVariable(name = "address") Integer address_id) {
        Account result = accountrepo.findById(id).get();
        Address address = addressRepo.findById(address_id).get();
        result.setAddress(address);
        accountrepo.save(result);
        return "New street for: " + result.getFirstName() + ", " + address.getStreet();
    }

    //Delete by id
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteAccount(@PathVariable(name = "id") Long id){
        Account result = accountrepo.findById(id).get();
        accountrepo.delete(result);
        return "deleted.";
    }
}
