package com.pharma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.dao.MedicineDao;
import com.pharma.exception.MedicineNotFoundException;
import com.pharma.model.Medicine;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicine")
public class MedicineController {

    private final MedicineDao medicineDao;

    @Autowired
    public MedicineController(MedicineDao medicineDao) {
        this.medicineDao = medicineDao;
    }

    @GetMapping
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        List<Medicine> medicines = medicineDao.getAllMedicines();
        return ResponseEntity.ok(medicines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Long id) {
        Medicine medicine = medicineDao.getMedicineById(id);
        if (medicine != null) {
            return ResponseEntity.ok(medicine);
        } else {
            throw new MedicineNotFoundException(null);
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Medicine> createMedicine(@Valid @RequestBody Medicine medicine) {
        Medicine createdMedicine = medicineDao.createMedicine(medicine);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMedicine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id, @Valid @RequestBody Medicine medicine) {
        Medicine existingMedicine = medicineDao.getMedicineById(id);
        if (existingMedicine != null) {
            existingMedicine.setName(medicine.getName());
            existingMedicine.setPrice(medicine.getPrice());
            existingMedicine.setStock(medicine.getStock());
            Medicine updatedMedicine = medicineDao.updateMedicine(existingMedicine);
            return ResponseEntity.ok(updatedMedicine);
        } else {
            throw new MedicineNotFoundException(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        Medicine medicine = medicineDao.getMedicineById(id);
        if (medicine != null) {
            medicineDao.deleteMedicine(medicine);
            return ResponseEntity.noContent().build();
        } else {
            throw new MedicineNotFoundException(null);
        }
    }
}