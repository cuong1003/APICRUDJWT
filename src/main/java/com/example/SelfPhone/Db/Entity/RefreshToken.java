package com.example.SelfPhone.Db.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RefreshTokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name="user_id", unique = true)
    private Integer userId;
    @Column(name="refresh_token", unique = true)
    private String token;

    public RefreshToken(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
