package com.example.SelfPhone.Db.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="username", length=50, nullable=false, unique=false)
    private String username;

    @Column(name="password", length=50, nullable=false, unique=false)
    private String password;

    @Column(name="role", length=50, nullable=false, unique=false)
    private String role;
}
