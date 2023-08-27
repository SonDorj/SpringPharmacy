package com.pharma.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.pharma.model.Patient;


public interface IPatientService {
	  ResponseEntity<List<Patient>> getAllPatients();

	    ResponseEntity<Patient> getPatientById(Long id);

	    ResponseEntity<Patient> createPatient(Patient patient);

	    ResponseEntity<Patient> updatePatient(Long id, Patient patient);

	    ResponseEntity<Void> deletePatient(Long id);
}
