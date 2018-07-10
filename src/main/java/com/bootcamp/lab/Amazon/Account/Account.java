package com.bootcamp.lab.Amazon.Account;
import com.bootcamp.lab.Amazon.Address.Address;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany
    @JoinColumn(name="address_id")
    private Set<Address> address;

    public Account(){}

    public Account(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return account_id;
    }

    public void setId(Long id) {
        this.account_id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address.add(address);
    }






}
