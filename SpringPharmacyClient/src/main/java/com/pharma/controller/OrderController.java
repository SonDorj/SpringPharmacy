package com.pharma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.dao.OrderDao;
import com.pharma.exception.OrderNotFoundException;
import com.pharma.model.Order;
import com.pharma.service.PharmacyServiceImpl;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final PharmacyServiceImpl pharmacyServiceImpl;
    @Autowired
    public OrderController(OrderDao orderDao, PharmacyServiceImpl pharmacyServiceImpl) {
        this.pharmacyServiceImpl=pharmacyServiceImpl;
    }
    
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = pharmacyServiceImpl.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam(name="patientId") Long patientId,@RequestParam(name="medicineId") Long medicineId,@RequestParam(name="quantity") int quantity) {
        Order createdOrder = pharmacyServiceImpl.createOrder(patientId,medicineId,quantity);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = pharmacyServiceImpl.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
        	throw new OrderNotFoundException("No order with id: "+id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestParam(name="patientId") Long patientId,@RequestParam(name="medicineId") Long medicineId,@RequestParam(name="quanttiy") int quantity) {
        Order existingOrder = pharmacyServiceImpl.getOrderById(id);
        if (existingOrder != null) {
            Order updatedOrder = pharmacyServiceImpl.updateOrder(id,patientId,medicineId,quantity);
            return ResponseEntity.ok(updatedOrder);
        } else {
        	throw new OrderNotFoundException("No order with id: "+id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Order order = pharmacyServiceImpl.deleteOrder(id);
        if (order != null) {
            return ResponseEntity.noContent().build();
        } else {
        	throw new OrderNotFoundException("No order with id: "+id);
        }
    }
}