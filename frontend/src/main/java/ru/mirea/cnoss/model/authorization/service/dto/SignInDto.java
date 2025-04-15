package ru.mirea.cnoss.model.authorization.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    private String login;
    private String password;
}