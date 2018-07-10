package com.bootcamp.lab.Amazon.Orders;

import com.bootcamp.lab.Amazon.Account.Account;
import com.bootcamp.lab.Amazon.Address.Address;
import com.bootcamp.lab.Amazon.OrderLine.OrderLineItems;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer order_id;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
    private Integer orderNumber;
    private Date orderDate;
    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;
    @OneToOne
    @JoinColumn(name="orderline_id")
    private OrderLineItems orderLine;
    private Double totalPrice;

    public Orders(){}

    public Orders(Date orderDate, Double totalPrice){
        this.orderNumber = this.order_id;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return order_id;
    }

    public void setId(Integer id) {
        this.order_id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public OrderLineItems getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(OrderLineItems orderLine) {
        this.orderLine = orderLine;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
