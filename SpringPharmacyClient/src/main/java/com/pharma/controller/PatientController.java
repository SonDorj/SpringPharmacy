package com.pharma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.model.Patient;
import com.pharma.service.PatientServiceImpl;


@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientServiceImpl patientServiceImpl;

    @Autowired
    public PatientController(PatientServiceImpl patientServiceImpl) {
        this.patientServiceImpl = patientServiceImpl;
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        return patientServiceImpl.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        return patientServiceImpl.getPatientById(id);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Patient patient) {
        return patientServiceImpl.createPatient(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Patient patient) {
        return patientServiceImpl.updatePatient(id, patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        return patientServiceImpl.deletePatient(id);
    }
}
