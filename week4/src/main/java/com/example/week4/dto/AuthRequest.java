package com.example.week4.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequest {
    private String name;
    private String email;
    private String password;
}
