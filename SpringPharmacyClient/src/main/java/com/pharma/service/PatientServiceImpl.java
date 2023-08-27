package com.pharma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.pharma.model.Patient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
@Component
public class PatientServiceImpl implements IPatientService {
	
	private final RestTemplate restTemplate;

    @Autowired
    public PatientServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Autowired
    private DiscoveryClient discoveryClient;
    
    private String getBaseUrl(){
    	List<ServiceInstance> instances = discoveryClient.getInstances("PharmacyService");
    	String url = instances.get(0).getUri().toString();
        return url+"/patient"; // Change this URL based on your service endpoint
    }
    
	@Override
	@CircuitBreaker(name="pharmacyCircuitBreaker",fallbackMethod="fallbackMethod")
	public ResponseEntity<List<Patient>> getAllPatients() {
		ParameterizedTypeReference<List<Patient>> responseType = new ParameterizedTypeReference<List<Patient>>() {};
        ResponseEntity<List<Patient>> responseEntity = restTemplate.exchange(getBaseUrl(), HttpMethod.GET, null, responseType);
        return responseEntity;
	}

	@Override
	@CircuitBreaker(name="pharmacyCircuitBreaker",fallbackMethod="fallbackMethod")
	public ResponseEntity<Patient> getPatientById(Long id) {
        return restTemplate.getForEntity(getBaseUrl() + "/" + id, Patient.class);
	}

	@Override
	@CircuitBreaker(name="pharmacyCircuitBreaker",fallbackMethod="fallbackMethod")
	public ResponseEntity<Patient> createPatient(Patient patient) {
        return restTemplate.postForEntity(getBaseUrl(), patient, Patient.class);
	}

	@Override
	@CircuitBreaker(name="pharmacyCircuitBreaker",fallbackMethod="fallbackMethod")
	public ResponseEntity<Patient> updatePatient(Long id, Patient patient) {
        return restTemplate.exchange(getBaseUrl() + "/" + id, HttpMethod.PUT, new HttpEntity<>(patient), Patient.class);
	}

	@Override
	@CircuitBreaker(name="pharmacyCircuitBreaker",fallbackMethod="fallbackMethod")
	public ResponseEntity<Void> deletePatient(Long id) {
        restTemplate.delete(getBaseUrl() + "/" + id);
        return ResponseEntity.ok().build();
	}
	
	public ResponseEntity<Void> fallbackMethod(Throwable t) {
		return ResponseEntity.notFound().build();
	}

}
