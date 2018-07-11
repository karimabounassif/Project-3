package com.bootcamp.lab.Amazon.Address;

import com.bootcamp.lab.Amazon.Account.Account;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer address_id;
    private String street;
    private String apt;
    private String city;
    private String state;
    private String postal;
    private String country;

    public Address() {}

    public Address(String street, String apt, String city, String state, String postal, String country){
        this.street = street;
        this.apt=apt;
        this.city=city;
        this.state=state;
        this.postal=postal;
        this.country=country;
    }

    public Integer getId() {
        return address_id;
    }

    public void setId(Integer id) {
        this.address_id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApt() {
        return apt;
    }

    public void setApt(String apt) {
        this.apt = apt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
