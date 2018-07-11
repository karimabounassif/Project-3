package com.bootcamp.lab.Amazon.Orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepo extends JpaRepository<Orders, Integer>{
}
