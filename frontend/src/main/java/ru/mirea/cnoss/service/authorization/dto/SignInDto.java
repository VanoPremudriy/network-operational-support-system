package ru.mirea.cnoss.service.authorization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    private String login;
    private String password;
}