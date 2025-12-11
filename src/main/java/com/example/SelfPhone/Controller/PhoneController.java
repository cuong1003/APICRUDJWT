package com.example.SelfPhone.Controller;

import com.example.SelfPhone.Db.Entity.Phone;
import com.example.SelfPhone.Service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phones")
public class PhoneController {

    @Autowired
    PhoneService phoneService;

    @GetMapping
    public List<Phone> getAllPhone(){
        return phoneService.getAllPhone();
    }

    @GetMapping("/detail/{id}")
    public Phone getPhoneById(@PathVariable Integer id){
        return phoneService.findPhoneById(id);
    }

    @PostMapping("/add")
    public List<Phone> addPhone(@RequestBody Phone phone){
        phoneService.savePhone(phone);
        return phoneService.getAllPhone();
    }

    @PutMapping("/update/{id}")
    public List<Phone> updatePhone(@RequestBody Phone phone, @PathVariable Integer id){
        phoneService.updatePhone(phone);
        return phoneService.getAllPhone();
    }

    @DeleteMapping("/delete/{id}")
    public List<Phone> deletePhone(@PathVariable Integer id){
        phoneService.deletePhone(id);
        return phoneService.getAllPhone();
    }
}
