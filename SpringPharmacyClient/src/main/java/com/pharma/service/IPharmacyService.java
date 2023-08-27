package com.pharma.service;

import java.util.List;

import com.pharma.model.Order;


public interface IPharmacyService {
	List<Order> getAllOrders();

    Order getOrderById(Long id);

    Order createOrder(Long patientId, Long medicineId);

    Order updateOrder(Long id, Long patientId, Long medicineId);

    Order deleteOrder(Long id);
}
