package com.pharma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.pharma.dao.OrderDao;
import com.pharma.model.Medicine;
import com.pharma.model.Order;
import com.pharma.model.Patient;
@Component
public class PharmacyServiceImpl implements IPharmacyService {

	private final RestTemplate restTemplate;
	private final OrderDao orderDao;

	@Autowired
	public PharmacyServiceImpl(RestTemplate restTemplate, OrderDao orderdao) {
		this.restTemplate = restTemplate;
		this.orderDao = orderdao;
	}

	private String getBaseUrlPatient() {
		return "http://localhost:8080/patient";
	}

	private String getBaseUrlMedicine() {
		return "http://localhost:8080/medicine";
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> orders = orderDao.getAllOrders();
		return orders;
	}

	@Override
	public Order getOrderById(Long id) {
		Order order = orderDao.getOrderById(id);
		if (order != null) {
			return order;
		} else {
			return null;
		}
	}

	@Override
	public Order createOrder(Long patientId, Long medicineId) {
		ResponseEntity<Patient> patient = restTemplate.getForEntity(getBaseUrlPatient() + "/" + patientId,
				Patient.class);
		ResponseEntity<Medicine> medicine = restTemplate.getForEntity(getBaseUrlMedicine() + "/" + medicineId,
				Medicine.class);
		if (patient != null && medicine != null) {
			Order order = new Order();
			order.setPatient(patient.getBody());
			order.setMedicine(medicine.getBody());
			Order createdOrder = orderDao.createOrder(order);
			return createdOrder;
		} else {
			return null;
		}
	}

	@Override
	public Order updateOrder(Long id, Long patientId, Long medicineId) {
		ResponseEntity<Patient> patient = restTemplate.getForEntity(getBaseUrlPatient() + "/" + patientId,
				Patient.class);
		ResponseEntity<Medicine> medicine = restTemplate.getForEntity(getBaseUrlMedicine() + "/" + medicineId,
				Medicine.class);
		if (patient != null && medicine != null) {
			Order order = new Order();
			order.setPatient(patient.getBody());
			order.setMedicine(medicine.getBody());
			order.setId(id);
			Order updatedOrder = orderDao.updateOrder(order);
			return updatedOrder;
		} else {
			return null;
		}
	}

	@Override
	public Order deleteOrder(Long id) {
		Order order = orderDao.getOrderById(id);
		if (order != null) {
			orderDao.deleteOrder(order);
			return order;
		} else {
			return null;
		}
	}

}
