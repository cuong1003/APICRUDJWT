package com.example.SelfPhone.Service;

import com.example.SelfPhone.Db.Entity.Phone;
import com.example.SelfPhone.Db.Repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService {
    @Autowired
    PhoneRepository phoneRepository;

    @Cacheable(value="phones", key="'all'")
    public List<Phone> getAllPhone() {
        return phoneRepository.findAll();
    }

    @Cacheable(value="phones", key="#id")
    public Phone findPhoneById(Integer id) {
        return phoneRepository.findPhoneById(id);
    }
    @CachePut(value="phones", key="#id")
    public void savePhone(Phone phone) {
        phoneRepository.save(phone);
    }
    @CachePut(value="phones", key="#id")
    public void updatePhone(Phone phone) {
        phoneRepository.save(phone);
    }

    @CacheEvict(value="phones", key="#id")
    public void deletePhone(Integer id) {
        phoneRepository.delete(findPhoneById(id));
    }

}
