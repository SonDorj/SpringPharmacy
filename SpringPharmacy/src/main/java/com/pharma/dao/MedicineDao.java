package com.pharma.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pharma.model.Medicine;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class MedicineDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Medicine> getAllMedicines() {
        return entityManager.createQuery("SELECT m FROM Medicine m", Medicine.class).getResultList();
    }

    public Medicine getMedicineById(Long id) {
        return entityManager.find(Medicine.class, id);
    }

    public Medicine createMedicine(Medicine medicine) {
        entityManager.persist(medicine);
        return medicine;
    }

    public Medicine updateMedicine(Medicine medicine) {
        return entityManager.merge(medicine);
    }

    public void deleteMedicine(Medicine medicine) {
        entityManager.remove(medicine);
    }
}