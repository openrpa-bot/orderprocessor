package com.nigam.controller;


import com.nigam.entity.Orders;
import com.nigam.repository.OrdersRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RestController
@Tag(name = "Incoming Order API", description = "Incoming Order API")
@RequestMapping("/controller/v1/orders")

public class OrdersController {
    @Autowired
    OrdersRepository ordersRepository;
    @GetMapping
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }
    @Cacheable(value = "orders", key = "#id")
    @GetMapping("/{id}")
    public Orders getOrder(@PathVariable Long id) {
        return ordersRepository.findById(id).get();
    }
    @PostMapping
    public Orders postOrder(@RequestBody Orders orders) {
        return ordersRepository.save(orders);
    }
    @CachePut(value = "orders", key = "#id")
    @PutMapping("/{id}")
    public Orders putOrder(@PathVariable Long id, @RequestBody Orders order) {
        Orders orders_to_save = ordersRepository.findById(id).get();
        if(orders_to_save!= null){
            orders_to_save.setType(order.getType());
            ordersRepository.save(orders_to_save);
            return orders_to_save;
        }else{
            return null;
        }
    }
    @CacheEvict(value = "orders", key = "#id")
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        ordersRepository.deleteById(id);
        return "Order deleted successfully";
    }
}
