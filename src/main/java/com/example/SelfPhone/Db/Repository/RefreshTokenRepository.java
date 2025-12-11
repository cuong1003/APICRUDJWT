package com.example.SelfPhone.Db.Repository;

import com.example.SelfPhone.Db.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {

    RefreshToken findByUserId(Integer userId);
    Optional<RefreshToken> findAllByUserIdAndToken(Integer userId, String token);
}
