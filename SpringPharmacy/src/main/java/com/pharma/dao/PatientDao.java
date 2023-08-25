package com.pharma.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pharma.model.Patient;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class PatientDao{
	@PersistenceContext
    private EntityManager entityManager;

    public List<Patient> getAllPatients() {
        return entityManager.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }
    
    public Patient createPatient(Patient patient) {
        entityManager.persist(patient);
        return patient;
    }

}
