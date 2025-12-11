package com.example.SelfPhone.Db.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRespone {
    private String username;
    private String accessToken;
    private String refreshToken;
    private String message;
    private boolean status;
}
