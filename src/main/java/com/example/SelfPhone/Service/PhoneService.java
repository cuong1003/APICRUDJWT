package com.example.SelfPhone.Service;

import com.example.SelfPhone.Db.Entity.Phone;
import com.example.SelfPhone.Db.Repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService {
    @Autowired
    PhoneRepository phoneRepository;

    public List<Phone> getAllPhone() {
        return phoneRepository.findAll();
    }

    public Phone findPhoneById(Integer id) {
        return phoneRepository.findPhoneById(id);
    }

    public void savePhone(Phone phone) {
        phoneRepository.save(phone);
    }

    public void updatePhone(Phone phone) {
        phoneRepository.save(phone);
    }

    public void deletePhone(Integer id) {
        phoneRepository.delete(findPhoneById(id));
    }

}
