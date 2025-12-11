package com.example.SelfPhone.Db.Repository;

import com.example.SelfPhone.Db.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserIdByUsername(String username);
    User findRoleByUsername(String username);
}
