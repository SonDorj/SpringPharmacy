package com.pharma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.pharma.dao.OrderDao;
import com.pharma.exception.MedicineNotFoundException;
import com.pharma.exception.OrderNotFoundException;
import com.pharma.exception.PatientNotFoundException;
import com.pharma.model.Medicine;
import com.pharma.model.Order;
import com.pharma.model.Patient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
@Component
public class PharmacyServiceImpl implements IPharmacyService {

	private final RestTemplate restTemplate;
	private final OrderDao orderDao;

	@Autowired
	public PharmacyServiceImpl(RestTemplate restTemplate, OrderDao orderdao) {
		this.restTemplate = restTemplate;
		this.orderDao = orderdao;
	}
	
	@Autowired
    private DiscoveryClient discoveryClient;
    
    private String  getBaseUrlPatient(){
    	List<ServiceInstance> instances = discoveryClient.getInstances("PharmacyService");
    	String url = instances.get(0).getUri().toString();
        return url+"/patient"; 
    }

	private String getBaseUrlMedicine() {
		List<ServiceInstance> instances = discoveryClient.getInstances("PharmacyService");
    	String url = instances.get(0).getUri().toString();
        return url+"/medicine"; 
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
			throw new OrderNotFoundException("No order with id: "+id);
		}
	}

	@Override
	@CircuitBreaker(name="pharmacyCircuitBreaker",fallbackMethod="fallbackMethod")
	public Order createOrder(Long patientId, Long medicineId, int quantity) {
		ResponseEntity<Patient> patient = restTemplate.getForEntity(getBaseUrlPatient() + "/" + patientId,
				Patient.class);
		ResponseEntity<Medicine> medicine = restTemplate.getForEntity(getBaseUrlMedicine() + "/" + medicineId,
				Medicine.class);
		if (patient != null && medicine != null) {
			Order order = new Order();
			order.setPatient(patient.getBody());
			order.setMedicine(medicine.getBody());
			order.setQuantity(quantity);
			Order createdOrder = orderDao.createOrder(order);
			return createdOrder;
		} else if(patient!=null) {
			throw new PatientNotFoundException("patient doesn't exist");
		} else {
			throw new MedicineNotFoundException("Medicine doesn't exist");
		}
	}

	@Override
	@CircuitBreaker(name="pharmacyCircuitBreaker",fallbackMethod="fallbackMethod")
	public Order updateOrder(Long id, Long patientId, Long medicineId, int quantity) {
		ResponseEntity<Patient> patient = restTemplate.getForEntity(getBaseUrlPatient() + "/" + patientId,
				Patient.class);
		ResponseEntity<Medicine> medicine = restTemplate.getForEntity(getBaseUrlMedicine() + "/" + medicineId,
				Medicine.class);
		if (patient != null && medicine != null) {
			Order order = new Order();
			order.setPatient(patient.getBody());
			order.setMedicine(medicine.getBody());
			order.setQuantity(quantity);
			order.setId(id);
			Order updatedOrder = orderDao.updateOrder(order);
			return updatedOrder;
		} else if(patient!=null) {
			throw new PatientNotFoundException("patient doesn't exist");
		} else {
			throw new MedicineNotFoundException("Medicine doesn't exist");
		}
	}

	@Override
	public Order deleteOrder(Long id) {
		Order order = orderDao.getOrderById(id);
		if (order != null) {
			orderDao.deleteOrder(order);
			return order;
		} else {
			throw new OrderNotFoundException("No order with id: "+id);
		}
	}
	
	public String fallbackMethod(Exception e) {
		return "Fallback: Service not available";
	}

}
