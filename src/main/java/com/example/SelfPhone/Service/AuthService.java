package com.example.SelfPhone.Service;

import com.example.SelfPhone.Db.Entity.BannedToken;
import com.example.SelfPhone.Db.Entity.User;
import com.example.SelfPhone.Db.Repository.AuthRepository;
import com.example.SelfPhone.Db.Repository.BannedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    AuthRepository authRepository;
    @Autowired
    BannedTokenRepository bannedTokenRepository;

    public User validateLogin(String username, String password) {
        return authRepository.findAll().stream()
                .filter(auth -> auth.getUsername().equals(username)
                        && auth.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void bannedToken(String token) {
        bannedTokenRepository.save(new BannedToken(token));
    }

    public boolean checkBannedToken(String token) {
        List<BannedToken> findAll = bannedTokenRepository.findAll();
        BannedToken bannedToken = findAll.stream().filter(b
                -> b.getToken().equals(token)).findFirst().orElse(null);
        return bannedToken != null;
    }
}
