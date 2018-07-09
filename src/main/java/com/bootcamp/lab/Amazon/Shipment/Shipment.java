package com.bootcamp.lab.Amazon.Shipment;

import com.bootcamp.lab.Amazon.Account.Account;
import com.bootcamp.lab.Amazon.Address.Address;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shipment_id;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;
    private Date shippedDate;
    private Date deliveryDate;

    public Integer getId() {
        return shipment_id;
    }

    public void setId(Integer id) {
        this.shipment_id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

}
