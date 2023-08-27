package com.pharma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.dao.PatientDao;
import com.pharma.exception.PatientNotFoundException;
import com.pharma.model.Patient;
import com.pharma.service.KafkaSender;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientController {
	private final PatientDao patientDao;
	@Autowired
	KafkaSender kafkaSender;

	@Autowired
	public PatientController(PatientDao patientDao) {
		this.patientDao = patientDao;
	}

	@GetMapping
	public ResponseEntity<List<Patient>> getAllPatients() {
		List<Patient> patients = patientDao.getAllPatients();
		return ResponseEntity.ok(patients);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
		Patient patient = patientDao.getPatientById(id);
		if (patient != null)
			return ResponseEntity.ok(patient);
		else
			throw new PatientNotFoundException("No patient exist with id " + id);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<Patient> createTask(@Valid @RequestBody Patient patient) {
		Patient createdPatient = patientDao.createPatient(patient);
		kafkaSender.send(patient.getFirstName()+" is created");
		return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @Valid @RequestBody Patient patient) {
		Patient existingPatient = patientDao.getPatientById(id);
		if (existingPatient != null) {
			patient.setId(id);
			Patient updatedPatient = patientDao.updatePatient(patient);
			kafkaSender.send(updatedPatient.getFirstName()+" is updated");
			return ResponseEntity.ok(updatedPatient);
		} else {
			throw new PatientNotFoundException("No patient exist with id " + id);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
		Patient patient = patientDao.getPatientById(id);
		if (patient != null) {
			kafkaSender.send(patient.getFirstName()+" is deleted");
			patientDao.deletePatient(patient);
			return ResponseEntity.noContent().build();
		} else {
			throw new PatientNotFoundException("No patient exist with id " + id);
		}
	}
}
