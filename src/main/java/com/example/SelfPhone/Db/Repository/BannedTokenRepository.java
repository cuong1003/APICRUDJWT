package com.example.SelfPhone.Db.Repository;

import com.example.SelfPhone.Db.Entity.BannedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannedTokenRepository extends JpaRepository<BannedToken,Integer> {
}
