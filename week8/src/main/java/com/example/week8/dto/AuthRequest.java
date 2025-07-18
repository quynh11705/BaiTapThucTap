package com.example.week7.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequest {
    private String name;
    private String email;
    private String password;
}
