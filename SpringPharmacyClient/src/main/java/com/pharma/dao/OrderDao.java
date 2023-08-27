package com.pharma.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pharma.model.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Order> getAllOrders() {
        return entityManager.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }

    public Order getOrderById(Long id) {
    	return entityManager.find(Order.class, id);
    }

    public Order createOrder(Order order) {
        entityManager.persist(order);
        return order;
    }

    public Order updateOrder(Order order) {
        return entityManager.merge(order);
    }

    public void deleteOrder(Order order) {
        entityManager.remove(order);
    }
}