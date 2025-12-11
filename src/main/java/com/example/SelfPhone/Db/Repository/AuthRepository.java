package com.example.SelfPhone.Db.Repository;

import com.example.SelfPhone.Db.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRepository extends JpaRepository<User,Integer> {
    List<User> findAll();
}
