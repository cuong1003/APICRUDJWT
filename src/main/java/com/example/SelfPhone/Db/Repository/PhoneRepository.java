package com.example.SelfPhone.Db.Repository;

import com.example.SelfPhone.Db.Entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {
    List<Phone> findAll();

    Phone findPhoneById(Integer id);
}
