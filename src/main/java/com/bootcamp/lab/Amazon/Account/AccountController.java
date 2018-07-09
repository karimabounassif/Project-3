package com.bootcamp.lab.Amazon.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountRepo accountrepo;

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
        return result.getFirstName() + " " + result.getLastName();
    }

    //Change email
    @PutMapping("/{id}/{email}")
    public @ResponseBody String updateAccount(@PathVariable(name = "id") Long id, @PathVariable(name = "email") String email) {
        Account result = accountrepo.findById(id).get();
        result.setEmail(email);
        accountrepo.save(result);
        return "updated.";
    }

    //Delete by id
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteAccount(@PathVariable(name = "id") Long id){
        Account result = accountrepo.findById(id).get();
        accountrepo.delete(result);
        return "deleted.";
    }
}
