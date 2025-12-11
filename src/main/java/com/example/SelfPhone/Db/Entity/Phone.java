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
@Table(name = "Phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 50, nullable = false, unique = false)
    private String name;

    @Column(name = "brand", length = 50, nullable = false, unique = false)
    private String brand;

    @Column(name = "price", length = 50, nullable = false, unique = false)
    private Double price;
}
