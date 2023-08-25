package com.pharma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.dao.PatientDao;
import com.pharma.model.Patient;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientController {
	private final PatientDao patientDao;
	@Autowired
	public PatientController(PatientDao patientDao) {
		this.patientDao=patientDao;
	}
	@GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients= patientDao.getAllPatients();
        return ResponseEntity.ok(patients);
    }
	@PostMapping
    @Transactional
    public ResponseEntity<Patient> createTask(@Valid @RequestBody Patient patient) {
        Patient createdPatient= patientDao.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }
}
