package ru.mirea.cnoss.service.client.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientViewDto {
    private String id;
    private String login;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
}